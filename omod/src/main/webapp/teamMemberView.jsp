<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<script src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9" type="text/javascript"></script>


<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css"/>

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>

<script>
	var members = [];
	var headerArray = ["edit", "Identifier", "Name", "Role", "Team", "Report To", "Locations", "Sub Ordinate Roles", "Sub Ordinate Teams", "History", "Patients"];
	function GenerateTable() {
		document.getElementById("example").innerHTML = "";
		var table = document.getElementById("example");
	    
	    var thead = document.createElement("THEAD")
	    var row = thead.insertRow(-1);
	    for (var i = 0; i < headerArray.length; i++) {
	        var headerCell = document.createElement("TH");
	        headerCell.innerHTML = headerArray[i];
	        row.appendChild(headerCell);
	    }
	    
	    var tbody = document.createElement("TBODY")
	    for (var i = 0; i < members.length; i++) {
	    	row = tbody.insertRow(-1);

	        /* Edit */
	    	var cell = row.insertCell(-1);
	        cell.innerHTML = "...";

	        /* Identifier */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].identifier;

	        /* Name */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].person.person.display;

	        /* Role */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].teamHierarchy;

	        /* Team */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].team.teamName;

	        /* Report To */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].reportTo;
	        
	        /* Locations */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].location[0].name;
	        
	        /* Sub Ordinate Roles */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].subTeamHierarchy;

	        /* Sub Ordinate Teams */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].subTeam;

	        /* History */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = '<a href="/openmrs/module/teammodule/memberHistory.form?personId='+members[i].personId+'">History</a>';

	        /* Patients */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].patients.length;
		}
	    table.appendChild(thead);
	    table.appendChild(tbody);
	}
	
	$(document).ready(function() {
		$.ajax({
			url: "/openmrs/ws/rest/v1/team/teammember?get=all&v=full",
			success : function(result) { 
				console.log("SUCCESS-ALL"); 
				console.log(result); 
				members = result.results;
				GenerateTable();
				$('#example').DataTable({
					"language": { "search": "Filter records:" }
				});
			}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-ALL"); console.log(jqXHR); }
		});

		$("#submitBtn").bind("click", function() {
			var url = "/openmrs/ws/rest/v1/team/teammember?get=filter&v=full";
			var id = document.getElementById("filterById").value;
			var supervisor = document.getElementById("filterBySupervisor").value;
			var role = document.getElementById("filterByTeamHierarchy").value;
			var team = document.getElementById("filterByTeam").value;
			var location = document.getElementById("filterByLocation").value;
			
			if(id != "") { url += "&identifier=" + id; }
			if(supervisor != "") { url += "&supervisor=" + supervisor; }
			if(role != "") { url += "&role=" + role; }
			if(team != "") { url += "&team=" + team; }
			if(location != "") { url += "&location=" + location; }

			$.ajax({
				url: url,
				success : function(result) { 
					console.log("SUCCESS-FILTER"); 
					console.log(result); 
					members = result.results;
					GenerateTable();
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
			});
		});
		
		
	});
</script>

<h1>Team Members</h1>

<table>
	<form:form ><!-- commandName="filterTeamMember" method="post" -->
		<tr>
			<td>Filter By: </td>
			<td>
				<input id="filterById" name="filterById" placeholder="name, id, supervisor"/>
			</td>
			<td>
				<select id="filterBySupervisor" name="filterBySupervisor">
					<option value="" selected>Select Supervisor</option>
				   	<c:forEach items="${allSupervisorIds}" var="supervisor" varStatus="loop">
				    	<option value="${supervisor}" >${allSupervisorNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeamHierarchy" name="filterByTeamHierarchy">
					<option value="" selected>Select Team Role</option>
				   	<c:forEach items="${allTeamHierarchyIds}" var="teamHierarchy" varStatus="loop">
				    	<option value="${teamHierarchy}" >${allTeamHierarchyNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeam" name="filterByTeam">
					<option value="" selected>Select Team</option>
				   	<c:forEach items="${allTeamIds}" var="team" varStatus="loop">
				    	<option value="${team}" >${allTeamNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByLocation" name="filterByLocation">
					<option value="" selected>Select Location</option>
				   	<c:forEach items="${allLocationIds}" var="location" varStatus="loop">
				    	<option value="${location}" >${allLocationNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<button type="button" id="submitBtn" name="submitBtn">Filter</button>
			</td>
		</tr>
	</form:form>
</table>

<br/>

<table id="example" class="general" cellspacing="0" width="100%"></table>

<%@ include file="/WEB-INF/template/footer.jsp"%>

