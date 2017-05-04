<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<script src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9" type="text/javascript"></script>


<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css"/>


<!-- <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css"> -->
<!-- <link rel="stylesheet" href="/resources/demos/style.css"> -->

<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
	var members = [];
	var headerArray = ["edit", "Identifier", "Name", "Role", "Team", "Report To", "Locations", "Sub Ordinate Roles", "Sub Ordinate Teams", "History", "Patients"];
	
	$(document).ready(function() {
		$.ajax({
			url: "/openmrs/ws/rest/v1/team/teammember?get=all&v=full",
			success : function(result) { 
				console.log("SUCCESS-ALL"); 
				console.log(result); 
				members = result.results;
				GenerateTable();
				$('#example').DataTable({
			        "bFilter": false
					//"language": { "search": "Filter records:" },
					//"paging":   true,
			        //"ordering": true,
			        //"info":     true,
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
					console.log(url);
					console.log("SUCCESS-FILTER"); 
					console.log(result); 
					members = result.results;
					GenerateTable();
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
			});
		});
	});
	
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
	    	row.id = "memberRow"+i;

	        /* Edit */
	    	var cell = row.insertCell(-1);
	        cell.innerHTML = "<a id='editTeamMemberLink' name='editTeamMemberLink' title='Edit Team Member' style='cursor:pointer' onclick='editTeamMember(\""+members[i].teamMemberId+"\",\""+"memberInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%;' ></a>";

	        /* Identifier */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].identifier;

	        /* Name */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].person.person.preferredName.display;

	        /* Role */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].teamHierarchy + "<a id='editTeamMemberRoleLink' name='editTeamMemberRoleLink' title='Edit Team Member Role' style='cursor:pointer' onclick='editTeamMember(\""+members[i].teamMemberId+"\",\""+"memberRoleInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%;' ></a>";

	        /* Team */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = members[i].team.display + "<a id='editTeamMemberTeamLink' name='editTeamMemberTeamLink' title='Edit Team Member Team' style='cursor:pointer' onclick='editTeamMember(\""+members[i].teamMemberId+"\",\""+"memberTeamInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%;' ></a>";

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
    function editTeamMemberClose(index, type) { 
		if(type==="memberRoleInfo") { $('#editTeamMemberRoleDiv').dialog('close'); } 
		else if(type==="memberTeamInfo") { $('#editTeamMemberTeamDiv').dialog('close'); } 
		else if(type==="memberInfo") { $('#editTeamMemberDiv').dialog('close'); } 
	}
	function editTeamMemberSuccess(index, type) {
		if(type==="memberRoleInfo") { 
			var uuid = members[index].uuid;
			var role = document.getElementById("teamMemberRole"+index).value;

			var url = "/openmrs/ws/rest/v1/team/teammember?update=teamMemberRole&v=full";
			if(role != "") { url += "&roleId=" + role; }
			if(uuid != "") { url += "&uuid=" + uuid; }
			console.log(url);

			$.ajax({
				url: url,
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER ROLE");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].teamMemberId.toString() === teamMember.teamMemberId.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Role Updated Successfully</p>"; 
							$('#editTeamMemberRoleDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER ROLE"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Role</p>"; }
			});
		} 
		else if(type==="memberTeamInfo") { 
			var uuid = members[index].uuid;
			var team = document.getElementById("teamMemberTeam"+index).value;

			var url = "/openmrs/ws/rest/v1/team/teammember?update=teamMemberTeam&v=full";
			if(team != "") { url += "&teamId=" + team; }
			if(uuid != "") { url += "&uuid=" + uuid; }
			console.log(url);

			$.ajax({
				url: url,
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER TEAM");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].teamMemberId.toString() === teamMember.teamMemberId.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Team Updated Successfully</p>"; 
							$('#editTeamMemberTeamDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER TEAM"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Team</p>"; }
			});
		} 
		else if(type==="memberInfo") { 
			var uuid = members[index].uuid;
	    	var id = document.getElementById("teamMemberIdentifier"+index).value;
	    	var firstName = document.getElementById("teamMemberPersonFirstName"+index).value;
	    	var middleName = document.getElementById("teamMemberPersonMiddleName"+index).value;
	    	var lastName = document.getElementById("teamMemberPersonLastName"+index).value;

			var url = "/openmrs/ws/rest/v1/team/teammember?update=teamMemberInfo&v=full";
			if(id != "") { url += "&identifier=" + id; }
			if(firstName != "") { url += "&firstName=" + firstName; }
			if(middleName != "") { url += "&middleName=" + middleName; }
			if(lastName != "") { url += "&lastName=" + lastName; }
			if(uuid != "") { url += "&uuid=" + uuid; }
			console.log(url);

			$.ajax({
				url: url,
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER INFO");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].teamMemberId.toString() === teamMember.teamMemberId.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
				    		console.log("if");
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; 
							$('#editTeamMemberDiv').dialog('close'); 
				    	}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
			});
		} 
	}
	function editTeamMember(teamMemberId, type) {
	    for (var i = 0; i < members.length; i++) {
	    	if(members[i].teamMemberId.toString() === teamMemberId.toString()) {
	    		if(type==="memberRoleInfo") {
					var allTeamHierarchyNames = []; <c:forEach items="${allTeamHierarchyNames}" var="teamHierarchyName">allTeamHierarchyNames.push("${teamHierarchyName}");</c:forEach>
	    			var allTeamHierarchyIds = []; <c:forEach items="${allTeamHierarchyIds}" var="teamHierarchyId">allTeamHierarchyIds.push("${teamHierarchyId}");</c:forEach>
	    			var html = "<table><form><tr><td>Role: </td><td><select id='teamMemberRole"+i+"' name='teamMemberRole"+i+"'>";
		    		for (var j = 0; j < allTeamHierarchyNames.length; j++) {
		    			if(allTeamHierarchyNames[j].toString() === members[i].teamHierarchy.toString()) { html += "<option value='"+allTeamHierarchyIds[j]+"' selected >"+allTeamHierarchyNames[j]+"</option>"; } 
		    			else { html += "<option value='"+allTeamHierarchyIds[j]+"' selected >"+allTeamHierarchyNames[j]+"</option>"; }
	    	    	} html += "</select></td></tr><tr><td><button type='button' id='teamMemberRoleEditClose' name='teamMemberRoleEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberRoleInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberRoleEditSuccess' name='teamMemberRoleEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberRoleInfo"+"\");' >Save</button></td></tr></form></table>";
	    			document.getElementById("editTeamMemberRoleDiv").innerHTML = html;
		    		$("#editTeamMemberRoleDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Edit" , closeText: ""});
	    		}
	    		else if(type === "memberTeamInfo") {
		    	    var allTeamNames = []; <c:forEach items="${allTeamNames}" var="teamName">allTeamNames.push("${teamName}");</c:forEach>
	    			var allTeamIds = []; <c:forEach items="${allTeamIds}" var="teamId">allTeamIds.push("${teamId}");</c:forEach>
	    			var html = "<table><form><tr><td>Team: </td><td><select id='teamMemberTeam"+i+"' name='teamMemberTeam"+i+"'>";
		    		for (var j = 0; j < allTeamNames.length; j++) {
		    			if(allTeamNames[j].toString() === members[i].team.display.toString()) { html += "<option value='"+allTeamIds[j]+"' selected >"+allTeamNames[j]+"</option>"; }
		    			else { html += "<option value='"+allTeamIds[j]+"' >"+allTeamNames[j]+"</option>"; }
		    		} html += "</select></td></tr><tr><td><button type='button' id='teamMemberTeamEditClose' name='teamMemberTeamEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberTeamInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberTeamEditSuccess' name='teamMemberTeamEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberTeamInfo"+"\");' >Save</button></td></tr></form></table>";
					document.getElementById("editTeamMemberTeamDiv").innerHTML = html;
		    		$("#editTeamMemberTeamDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Edit" , closeText: ""});
	    		}
	    		else if(type === "memberInfo") {
	    			var html = "<table><form><tr><td>Identifier: </td><td><input type'text' id='teamMemberIdentifier"+i+"' name='teamMemberIdentifier"+i+"' value='"+members[i].identifier+"'></td></tr><tr><td>Name: </td><td><input type'text' id='teamMemberPersonFirstName"+i+"' name='teamMemberPersonFirstName"+i+"' value='"+members[i].personGivenName+"'></tr><tr><td>Middle Name: </td><td><input type'text' id='teamMemberPersonMiddleName"+i+"' name='teamMemberPersonMiddleName"+i+"' value='"+members[i].personMiddleName+"'></tr><tr><td>Family Name: </td><td><input type'text' id='teamMemberPersonLastName"+i+"' name='teamMemberPersonLastName"+i+"' value='"+members[i].personFamilyName+"'></td></tr><tr><td><button type='button' id='teamMemberEditClose' name='teamMemberEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberEditSuccess' name='teamMemberEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberInfo"+"\");' >Save</button></td></tr></form></table>";
					document.getElementById("editTeamMemberDiv").innerHTML = html;
		    		$("#editTeamMemberDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Edit" , closeText: ""});
	    		}
	    	}
	    }
	}
	function replace(arrayName,replaceTo, replaceWith) { for(var i=0; i<arrayName.length;i++ ) { if(arrayName[i]==replaceTo) { arrayName.splice(i,1,replaceWith); } } }
</script>

<h2 align="center">Team Members</h2>
<h3 id="errorHead" name="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" name="saveHead" align="center" style="color: green">${saved}</h3>

<table>
	<form:form><!-- commandName="filterTeamMember" method="post" -->
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


<div id="editTeamMemberDiv"></div>
<div id="editTeamMemberRoleDiv"></div>
<div id="editTeamMemberTeamDiv"></div>

<!-- <div id="editTeamMemberRole" title="Team Member - Edit Role">
  <p>Edit Team Member Role</p>
</div> -->



<%@ include file="/WEB-INF/template/footer.jsp"%>

