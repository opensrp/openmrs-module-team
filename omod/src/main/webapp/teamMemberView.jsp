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
	$(document).ready(function() {
		$('#example').DataTable({
			"language": { "search": "Filter records:" }
		});
	});
</script>


<h1>Team Members</h1>



<table>
	<form:form method="post" ><!-- commandName="filterTeamMember" -->
		<tr>
			<td>Filter By: </td>
			<td>
				<input id="filterById" name="filterById" placeholder="name, id, supervisor" oninput="search()"/>
			</td>
			<td>
				<select id="filterBySupervisor" name="filterBySupervisor" onchange="search()">
					<option value="" selected>Select Supervisor</option>
				   	<c:forEach items="${allSupervisorIds}" var="supervisor" varStatus="loop">
				    	<option value="${supervisor}" >${allSupervisorNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeamRole" name="filterByTeamRole" onchange="search()">
					<option value="" selected>Select Team Role</option>
				   	<c:forEach items="${allTeamRoleIds}" var="teamRole" varStatus="loop">
				    	<option value="${teamRole}" >${allTeamRoleNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeam" name="filterByTeam" onchange="search()">
					<option value="" selected>Select Team</option>
				   	<c:forEach items="${allTeamIds}" var="team" varStatus="loop">
				    	<option value="${team}" >${allTeamNames[loop.index]}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByLocation" name="filterByLocation" onchange="search()">
					<option value="" selected>Select Location</option>
				   	<c:forEach items="${allLocations}" var="locations" varStatus="loop">
				    	<option value="${locations}" >${locations}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<button type="submit">Filter</button>
			</td>
		</tr>
	</form:form>
</table>

<br/>
<c:choose>
	<c:when test="${not empty teamMembers}">
		<table id="example" class="general" cellspacing="0" width="100%">
			<thead>
				<tr>
					<th>edit</th>
					<th>Identifier</th>
					<th>Name</th>
					<th>Role</th>
					<th>Team</th>
					<th>Report To</th>
					<th>Locations</th>
					<th>Sub Ordinate Roles</th>
					<th>Sub Ordinate Teams</th>
					<th>History</th>
					<th>Patients</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="teamMembers" items="${teamMembers}" varStatus="loop">
					<tr>
						<!-- TEAM MEMBER EDIT -->
						<td>...</td>
						<!-- TEAM MEMBER IDENTIFIER -->
						<td><c:out value="${teamMembers.identifier}" /></td>
						<!-- TEAM MEMBER NAME -->
						<td><c:out value="${teamMembers.person.personName}" /></td>
						<!-- TEAM MEMBER TEAM ROLE NAME -->
						<td><c:out value="${teamMembers.teamRole.name}" /></td>
						<!-- TEAM MEMBER TEAM NAME -->
						<td><c:out value="${teamMembers.team.teamName}" /></td>
						<!-- TEAM MEMBER REPORT TO -->
						<td><c:out value="${reportsTo[loop.index]}" /></td>
						<!-- TEAM MEMBER LOCATIONS -->
						<td><c:out value="${teamMemberLocations[loop.index]}" /></td>
						<!-- TEAM MEMBER SUB ROLES -->
				    	<td><c:out value="${subRoles[loop.index]}" /></td>
						<!-- TEAM MEMBER SUB TEAMS -->
						<td><c:out value="${subTeams[loop.index]}" /></td>
						<!-- TEAM MEMBER HISTORY -->
						<td><a href="/openmrs/module/teammodule/memberHistory.form?personId=${teamMembers.person.personId}">History</a></td>
						<!-- TEAM MEMBER PATIENTS COUNT -->
						<td><c:out value="${teamMemberPatients[loop.index]}" /></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:when>
	<c:otherwise>
		<p>No record(s) found</p>
	</c:otherwise>
</c:choose>

<script>
	function search() {
		var searchQuery = "";
		var id = document.getElementById("filterById").value;
		var supervisor = document.getElementById("filterBySupervisor").value;
		var teamRole = document.getElementById("filterByTeamRole").value;
		var team = document.getElementById("filterByTeam").value;
		var location = document.getElementById("filterByLocation").value;
		
		if(id != "") { searchQuery += id + " "; }
		if(supervisor != "") { searchQuery += supervisor + " "; }
		if(teamRole != "") { searchQuery += teamRole + " "; }
		if(team != "") { searchQuery += team + " "; }
		if(location != "") { searchQuery += location; }
		if(searchQuery == "") {}
		else { 
			//$.ajax({
			//	url: "/openmrs/module/teammodule/teamMemberView",
			//	type: "POST",
			//	data : {id: id, supervisor: supervisor, teamRole: teamRole, team: team, location: location },
			//	success : function(result) {
			//		console.log(result);
			//	}, error: function(jqXHR, textStatus, errorThrown) { 
			//		console.log(jqXHR);
			//		console.log(textStatus);
			//		console.log(errorThrown);
			//	}
			//});
			console.log(searchQuery);  
		}
	}
	
</script>

<%@ include file="/WEB-INF/template/footer.jsp"%>