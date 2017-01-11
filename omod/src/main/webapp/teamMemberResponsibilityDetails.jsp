<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <style>
#team_patient, #team_patient th, #team_patient td {
	border: 1px solid black;
	 border-collapse: collapse;
}
</style>
<script type="text/javascript">
function deleteFunction(partientMemberId)
{
$.get("/openmrs/module/teammodule/teamMemberResponsibilityUnAssign.form?memberPatientId="+partientMemberId,function(){

});
}
</script>
</head>
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<h1>Member's Patient</h1>	
<h2>Member Name: ${memberName}</h2>	
<br/>
<table id="team_patient">
<th>Patient Id</th>
<th>Patient Name</th>
<th>Patient Age</th>
<th>Assignment Date</th>
<th>Status</th>
<th>Reason</th>
<th>Un-Assign</th>

<c:forEach items="${list}" var="data">
   <tr>
   <td>${data.id}</td>
   <td>${data.name}</td>
   <td>${data.age}</td>
   <td>${data.assignmentDate}</td>
   <td>${data.status}</td>
   <td>${data.reason}</td>
   <td><button onClick="deleteFunction(${data.memberPatientId})">Un-Assign</button></td>
   
   </tr>
</c:forEach>
</table>
<%@ include file="/WEB-INF/template/footer.jsp"%>
