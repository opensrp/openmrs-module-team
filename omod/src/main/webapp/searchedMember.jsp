<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Member" otherwise="/login.htm" />

<c:choose>
	<c:when test="${teamMember  == null}" >
	
		<h3>Search result between date range "${dateFrom}" - "${dateTo}"</h3>
	</c:when>
	<c:otherwise>
		<h3>Search result for member name "${searchMember}"</h3>
	</c:otherwise>
</c:choose>



<c:choose>
	<c:when test="${not empty teamMember}">

		<table border="1" cellspacing="0">
			<tr>
				<th>Name</th>
				<th>Identifier</th>
				<th>Gender</th>
				<th>Team</th>
				<th>Team Join Date</th>
				<th>Is Team Lead</th>
			</tr>
			<c:forEach var="allMembers" items="${teamMember}" varStatus="loop">
				<td><c:out
						value="${allMembers.person.givenName} ${allMembers.person.familyName}" /></td>
				<td><c:out value="${allMembers.identifier}" /></td>
				<td><c:out value="${allMembers.person.gender}" /></td>
				<td><c:out value="${allMembers.team.teamName}" /></td>
				<td><c:out value="${parsedJoinDate[loop.index]}" /></td>

				<c:choose>
					<c:when test="${allMembers.isTeamLead == false}">
						<td>No</td>
					</c:when>
					<c:otherwise>
						<td>Yes</td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>
		</table>
	</c:when>

	<c:otherwise>

		<table border="1" cellspacing="0">
			<tr>
				<th>Name</th>
				<th>Identifier</th>
				<th>Gender</th>
				<th>Team</th>
				<th>Team Join Date</th>
				<th>Is Team Lead</th>
			</tr>
			<c:forEach var="allMembers" items="${dateSearch}" varStatus="loop">
				<td><c:out
						value="${allMembers.person.givenName} ${allMembers.person.familyName}" /></td>
				<td><c:out value="${allMembers.identifier}" /></td>
				<td><c:out value="${allMembers.person.gender}" /></td>
				<td><c:out value="${allMembers.team.teamName}" /></td>
				<td><c:out value="${parsedJoinDate[loop.index]}" /></td>

				<c:choose>
					<c:when test="${allMembers.isTeamLead == false}">
						<td>No</td>
					</c:when>
					<c:otherwise>
						<td>Yes</td>
					</c:otherwise>
				</c:choose>
				</tr>
			</c:forEach>
		</table>
	</c:otherwise>
</c:choose>



<%@ include file="/WEB-INF/template/footer.jsp"%>