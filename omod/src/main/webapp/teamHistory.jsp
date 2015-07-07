<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm"  />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">

<h1>${team.teamName} History</h1>

<p>
	<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
</p>
<table class="history">
	<th>Team Lead</th>
	<th>Date Made</th>
	<th>Date Removed</th>
	<th>Gender</th>
	<th>Duration</th>

	<c:forEach var="lead" items="${teamLeadList}" varStatus="loop">
		<tr>
			<td>${lead.name}</td>
			<td>${lead.parsedJoinDate[loop.index]}</td>
			<td>${lead.parsedLeaveDate[loop.index]}</td>
			<td>${lead.gender}</td>
			<td>${lead.duration}</td>
		</tr>
	</c:forEach>

</table>




<%@ include file="/WEB-INF/template/footer.jsp"%>