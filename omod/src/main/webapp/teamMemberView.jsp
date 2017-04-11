<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<script src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9" type="text/javascript"></script>

<h1>Team Members</h1>

<c:choose>
	<c:when test="${not empty teamMembers}">
		<table class="general">
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
				<th>Voided</th>
				<th>Patients</th>
			</tr>
			
			
			<c:forEach var="teamMembers" items="${teamMembers}" varStatus="teamMemberLoop">
				<td></td>
				<td><c:out value="${teamMembers.identifier}" /></td>
				<td><c:out value="${teamMembers.person.givenName} ${teamMembers.person.familyName}" /></td>
				<td><c:out value="${teamMembers.teamRole.teamRoleId}" /></td>
				<td><c:out value="${teamMembers.team.teamId}" /></td>
				<!-- <td>REPORT TO</td> -->
				<c:forEach var="teamMemberIds" items="${teamMemberIds}" varStatus="teamMemberIdLoop">
					<c:choose>
					    <c:when test="${teamMemberIds == teamMembers.id}">
					    	<td><c:out value="${reportsTo[teamMemberIdLoop.index]}" /></td>
					    	<td><c:out value="${teamMemberLocations[teamMemberIdLoop.index]}" /></td>
					    </c:when>
					    <c:otherwise>
					    	<td>No Location</td>
					    </c:otherwise>
					</c:choose>
				</c:forEach>
				
				<td>TODO - SUB ROLES</td>
				
				<c:forEach var="teamMemberIds" items="${teamMemberIds}" varStatus="teamMemberIdLoop">
					<c:choose>
					    <c:when test="${teamMemberIds == teamMembers.id}">
					    	<td>
					    	<c:forEach var="subTeams" items="${subTeams[teamMemberIdLoop.index]}" varStatus="subTeamsLoop">
					    		<c:out value="${subTeams}" />
					    	</c:forEach>
					    	</td>
					    </c:when>
					    <c:otherwise>
					    	<td>No Location</td>
					    </c:otherwise>
					</c:choose>
				</c:forEach>
				
				<td><a href="/openmrs/module/teammodule/memberHistory.form?personId=${teamMembers.person.personId}">History</a></td>
				<c:choose>
					<c:when test="${teamMembers.voided == false}">
						<td>No</td>
					</c:when>
					<c:otherwise>
						<td>Yes</td>
					</c:otherwise>
				</c:choose>
				
				<c:forEach var="teamMemberIds" items="${teamMemberIds}" varStatus="teamMemberIdLoop">
					<c:choose>
					    <c:when test="${teamMemberIds == teamMembers.id}">
					    	<td><c:out value="${teamMemberPatients[teamMemberIdLoop.index]}" /></td>
					    </c:when>
					    <c:otherwise>
					    	<td>No Patients</td>
					    </c:otherwise>
					</c:choose>
				</c:forEach>
				
				
			</c:forEach>
		</table>
	</c:when>
	<c:otherwise>
		<p>No record(s) found</p>
	</c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/template/footer.jsp"%>