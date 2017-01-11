<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <style>
#memberDialog> table, #memberDialog> table th, #memberDialog> table td {
	border: 1px solid black;
	 border-collapse: collapse;
}

#historyDialog> table, #historyDialog> table th, #historyDialog> table td {
	border: 1px solid black;
	 border-collapse: collapse;
}

</style>
  <script type="text/javascript">
  $(document).ready(function(){
	        $('#historyDialog').hide();
	        $('#memberDialog').hide();
	});
  function teamHistory(teamId) {

	  $.get("/openmrs/module/teammodule/teamHistory.form?teamId="+teamId, function(data){

		  var myTable = document.getElementById("history");
		  var rowCount = myTable.rows.length;
		  for (i = 0; i < rowCount; i++)
		  {
			  $("#historyRow").remove();
		  }
			  for (i = 0; i < data.length; i++)
			  {
			  $("#history").append("<tr id=\"historyRow\">"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].name+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].parsedJoinDate+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].parsedLeaveDate+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].gender+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].duration+"</td>"
			+"</tr>"); 
			  }
	});
	  $( "#historyDialog" ).dialog( { width: "auto",
		    height: "auto"});
  }

  function teamMember(teamId) {
	    $.get("/openmrs/module/teammodule/teamMember/listPopup.form?teamId="+teamId, function(data){
		  console.log(data);
		  var myTable = document.getElementById("member");
		  var rowCount = myTable.rows.length;
		  for (i = 0; i < rowCount; i++)
		  {
			  $("#memberRow").remove();
		  }
		  for (i = 0; i < data.length; i++)
		  {
			  console.log(data[i].edit);
		    $("#member").append("<tr id=\"memberRow\">"
				+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].teamMemberId+"</td>"
				+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].personName +"</td>"
				+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].join+"</td>"
				+"<td style=\"text-align: left;\"> <div style=\" height:40px; width:145px; z-index:1 position:fixed; overflow-y:scroll\"> "
				+data[i].location+" </div></td>" 
				+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMemberResponsibility.form?teamId="+data[i].teamId+"\">Patients</a></td>"
				+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMember/list.form?teamId="+data[i].teamId+"\">Detail</a></td>"
				+"</tr>");
		  }
	  });
	  $( "#memberDialog" ).dialog( { width: "auto",
		    height: "auto"});  
  }
  
  </script>
</head>

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">

<div id="historyDialog">
<table id="history">
	<th>Team Lead</th>
	<th>Date Made</th>
	<th>Date Removed</th>
	<th>Gender</th>
	<th>Duration</th>
</table>
</div>

<div id="memberDialog">
<table id="member">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Join Date</th>
			<th>Gender</th>
			<th>Patients</th>
			<th>Detail</th>
		</tr>
	</table>
	</div>
  
<h1>Teams</h1>
<c:if test="${not empty searchedTeam}">
	<h3>Search Results for "${searchedTeam}"</h3>
</c:if>
<table>
	<tr>
		<td>Enter Team Name or ID</td>
		<form:form method="post" commandName="searchTeam">
			<td><form:input id="teamName" path="teamName" /></td>
			<td><button type="submit">Search</button></td>
		</form:form>
	</tr>

</table>
<c:if test="${empty searchedTeam}">
	<table class="extra">
		<tr>
			<openmrs:hasPrivilege privilege="Add Team">
				<td><a href="/openmrs/module/teammodule/addTeam.form">Add
						Team</a></td>
			</openmrs:hasPrivilege>

			<openmrs:hasPrivilege privilege="View Member">
				<td><a
					href="/openmrs/module/teammodule/allMember.form?searchMember=&from=&to=">View
						All Members</a></td>
			</openmrs:hasPrivilege>
		</tr>
	</table>
</c:if>
<c:choose>
	<c:when test="${not empty team}">
		<table class="general">
			<tr>
				<openmrs:hasPrivilege privilege="Edit Team">
					<th>Edit</th>
				</openmrs:hasPrivilege>
				<th>Id</th>
				<th>Team Name</th>
				<th>Date Created</th>
				<th>Location</th>
				<openmrs:hasPrivilege privilege="View Member">
					<th>No. of members</th>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="Add Member">
					<th>Add Member</th>
				</openmrs:hasPrivilege>
				<th>Team Lead</th>
				<openmrs:hasPrivilege privilege="Edit Team">
					<th>Change TeamLead</th>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="View Team">
					<th>History</th>
				</openmrs:hasPrivilege>
			</tr>

			<c:forEach var="team" items="${team}" varStatus="loop">
				<c:if test="${team.voided}">
					<tr>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a
								href="/openmrs/module/teammodule/editTeam.form?teamId=${team.teamId}">Edit</a>
							</td>
						</openmrs:hasPrivilege>
						<td><c:out value="${team.teamIdentifier}" /></td>
						<td valign="top" style="text-align: center;"><c:out value="${team.teamName}" /></td>
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td style="text-align: center;"><c:out value="${team.location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><p onclick="teamMember(<c:out value="${team.teamId}" />)" style="cursor:pointer" ><u><c:out value="${length[loop.index]}" /></u></p></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="Add Member">
							<td><a
								href="/openmrs/module/teammodule/teamMemberAddForm.form?teamId=${team.teamId}">Add
									Member</a></td>
						</openmrs:hasPrivilege>
						<td style="text-align: center;"><c:out value="${team.teamName}" /></td>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a
								href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=&changeLead=true">
									change TeamLead </a></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="View Team">
							<td><button onclick="teamHistory(<c:out value="${team.teamId}" />)">History</button>
							</td>
						</openmrs:hasPrivilege>
					</tr>
				</c:if>

				<c:if test="${!team.voided}">
					<tr>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a
								href="/openmrs/module/teammodule/editTeam.form?teamId=${team.teamId}">Edit</a>
							</td>
						</openmrs:hasPrivilege>
						<td><c:out value="${team.teamIdentifier}" /></td>
						<td style="text-align: center;"><c:out value="${team.teamName}" /></td>
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td style="text-align: center;"><c:out value="${team.location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><p onclick="teamMember(<c:out value="${team.teamId}" />)" style="cursor:pointer" ><u><c:out value="${length[loop.index]}" /></u></p></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="Add Member">
							<td><a
								href="/openmrs/module/teammodule/teamMemberAddForm.form?teamId=${team.teamId}">Add
									Member</a></td>
						</openmrs:hasPrivilege>
						<td style="text-align: center;"><c:out value="${team.teamName}" /></td>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a
								href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=&changeLead=true">
									change TeamLead </a></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="View Team">
							<td><button onclick="teamHistory(<c:out value="${team.teamId}" />)" >History</button></td>
						</openmrs:hasPrivilege>
					</tr>
				</c:if>
			</c:forEach>
  </table>
</c:when>
	<c:otherwise>
		<p>No record(s) found</p>
	</c:otherwise>
</c:choose>
<c:if test="${not empty searchedTeam}">
	<p>
		<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
	</p>
</c:if>



<%@ include file="/WEB-INF/template/footer.jsp"%>