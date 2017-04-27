<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<head>
<<<<<<< HEAD
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
			console.log("team.jsp")
			
 			$('#historyDialog').hide();
	        $('#memberDialog').hide();
 		});
		function teamHistory(teamId) {
			$.get("/openmrs/module/teammodule/teamHistory.form?teamId="+teamId, function(data){
				var myTable = document.getElementById("history");
				var rowCount = myTable.rows.length;
				for (i = 0; i < rowCount; i++) { $("#historyRow").remove(); }
				for (i = 0; i < data.length; i++) { $("#history").append("<tr id=\"historyRow\"><td style=\"text-align: left;\" valign=\"top\">"+data[i].name+"</td><td style=\"text-align: left;\" valign=\"top\">"+data[i].parsedJoinDate[i]+"</td><td style=\"text-align: left;\" valign=\"top\">"+data[i].parsedLeaveDate[i]+"</td><td style=\"text-align: left;\" valign=\"top\">"+data[i].gender+"</td><td style=\"text-align: left;\" valign=\"top\">"+data[i].duration+"</td></tr>"); }
			});
		  	$( "#historyDialog" ).dialog( { width: "auto", height: "auto", title: "History" , closeText: ""});
		}
	
	  	function teamMember(teamId) {
		    $.get("/openmrs/module/teammodule/teamMember/listPopup.form?teamId="+teamId, function(data){
			  	console.log(data);
			  	var myTable = document.getElementById("member");
			  	var rowCount = myTable.rows.length;
			  	for (i = 0; i < rowCount; i++) { $("#memberRow").remove(); }
			  	for (i = 0; i < data.length; i++) { 
			  		console.log(data[i].edit);
					$("#member").append("<tr id=\"memberRow\">"
					+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].teamMemberId+"</td>"
					+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].personName +"</td>"
					+"<td style=\"text-align: left;\" valign=\"top\">"+data[i].join+"</td>"
					+"<td style=\"text-align: left;\">"
					+data[i].gender+"</td>" 
					+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMemberResponsibility.form?teamId="+data[i].teamId+"\">Patients</a></td>"
					+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMember/list.form?teamId="+data[i].teamId+"\">Detail</a></td>"
					+"</tr>");
				}
			});
			$( "#memberDialog" ).dialog( { width: "auto", height: "auto", title: "Team Members", closeText: ""});  
	  	}
	  
	</script>
</head>

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">

<div id="historyDialog">
	<table id="history">
		<th>Team Lead</th>
		<th>Change Date</th>
		<th>Removed Date </th>
		<th>Gender</th>
		<th>Duration</th>
	</table>
</div>

<div id="memberDialog">
	<table id="member">
		<tr>
			<th>Identifier</th>
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
=======
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
  <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
  <link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
  <script type="text/javascript">
  $(document).ready(function(){
	  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?v=default", function(data, status){
		  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?v=default", function(data, status){
			  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?supervisor=&v=default", function(data, status){
				  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/teammember?v=default", function(data, status){
					  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/teamlog?teamId=&v=default", function(data, status){
										        
	    });
		   // When both Ajax requests were successful
		   $.when(call1, call2).done(function(a1, a2){
		      // a1 and a2 are arguments resolved for the call1 and call2 ajax requests, respectively.
		      // Each argument is an array with the following structure: [ data, statusText, jqXHR ]

		      // Merge data from two Ajax calls
		      var data = a1[0].d.results.concat(a2[0].d.results);

		      $('#example').dataTable({
		         "aaData": data,
		         "aoColumns": [
		            { "mData": "Data1" },
		            { "mData": "Data2" },
		            { "mData": "Data3" },
		            { "mData": "Data4" },
		            { "mData": "Data5" }
		         ]
		      });
		   });
	});
  
  </script>
</head>


  
<h1>Teams</h1>

<table class="extra">
>>>>>>> f1671e21c9f0cb285d8d4507d187d7b6db986bcc
		<tr>
			<openmrs:hasPrivilege privilege="Add Team">
				<td><a href="/openmrs/module/teammodule/addTeam.form">Add Team</a></td>
			</openmrs:hasPrivilege>

			<openmrs:hasPrivilege privilege="View Member">
				<td><a href="/openmrs/module/teammodule/allMember.form?searchMember=&from=&to=">View All Members</a></td>
			</openmrs:hasPrivilege>
		</tr>
	</table>
<<<<<<< HEAD
</c:if>

<c:choose>
	<c:when test="${not empty team}">
		<table class="general">
=======
		<table class="general" id ="general">
			<thead>
>>>>>>> f1671e21c9f0cb285d8d4507d187d7b6db986bcc
			<tr>
				<openmrs:hasPrivilege privilege="Edit Team">
				<th>Edit</th>
				</openmrs:hasPrivilege>
				<th>Identifier</th>
				<th>Team Name</th>
<<<<<<< HEAD
				<!-- <th>Date Created</th> -->
=======
				<th>Current Supervisor</th>
				<th>Supervisor Team</th>
>>>>>>> f1671e21c9f0cb285d8d4507d187d7b6db986bcc
				<th>Location</th>
				<openmrs:hasPrivilege privilege="View Team">
					<th>History</th>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="View Team">
					<th>Voided</th>
				</openmrs:hasPrivilege>
			</tr>
<<<<<<< HEAD

			<c:forEach var="team" items="${team}" varStatus="loop">
				<c:if test="${team.voided}">
					<tr>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a href="/openmrs/module/teammodule/editTeam.form?teamId=${team.teamId}">Edit</a></td>
						</openmrs:hasPrivilege>
						<td><c:out value="${team.teamIdentifier}" /></td>
						<td valign="top" style="text-align: left;"><c:out value="${team.teamName}" /></td>
						<%-- <td><c:out value="${parsedDate[loop.index]}" /></td> --%>
						<td style="text-align: center;"><c:out value="${teamLocation[loop.index].location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><p onclick="teamMember(<c:out value="${team.teamId}" />)" style="cursor:pointer" ><u><c:out value="${length[loop.index]}" /></u></p></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="Add Member">
							<td><a href="/openmrs/module/teammodule/teamMemberAddForm.form?teamId=${team.teamId}">Add Member</a></td>
						</openmrs:hasPrivilege>
							<td style="text-align: center;"><c:out value="${teamLead[loop.index]}" /></td>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=&changeLead=true"> change TeamLead </a></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="View Team">
							<td><button onclick="teamHistory(<c:out value="${team.teamId}" />)">History</button></td>
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
						<td style="text-align: center;"><c:out value="${teamLocation[loop.index].location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><p onclick="teamMember(<c:out value="${team.teamId}" />)" style="cursor:pointer" ><u><c:out value="${length[loop.index]}" /></u></p></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="Add Member">
							<td><a href="/openmrs/module/teammodule/teamMemberAddForm.form?teamId=${team.teamId}">Add Member</a></td>
						</openmrs:hasPrivilege>
							<td style="text-align: center;"><c:out value="${teamLead[loop.index]}" /></td>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=&changeLead=true"> change TeamLead </a></td>
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
=======
			</thead>
  </table>
>>>>>>> f1671e21c9f0cb285d8d4507d187d7b6db986bcc
	<p>
		<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
	</p>



<%@ include file="/WEB-INF/template/footer.jsp"%>