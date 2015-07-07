<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Member" otherwise="/login.htm"  />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">

<h3>Member History</h3>

<p>
	<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
</p>

<table class="history">
	<th>Team Name</th>
	<th>Join Date</th>
	<th>End Date</th>

	<c:forEach var="join" items="${join}" varStatus="loop">
		<tr>
			<td>${teamName[loop.index]}</td>
			<td>${join}</td>
			<td>${leave[loop.index]}</td>
		</tr>
	</c:forEach>
</table>






<%@ include file="/WEB-INF/template/footer.jsp"%>