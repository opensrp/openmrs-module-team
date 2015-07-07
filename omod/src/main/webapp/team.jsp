<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">


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
						<td valign="top"><c:out value="${team.teamName}" /></td>
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td><c:out value="${team.location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><a
								href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=">
									<c:out value="${length[loop.index]}" />
							</a></td>
						</openmrs:hasPrivilege>
						<td>Team is Voided</td>
						<td><c:out value="${teamLead[loop.index]}" /></td>
						<td>Team is Voided</td>
						<openmrs:hasPrivilege privilege="View Team">
							<td><a
								href="/openmrs/module/teammodule/teamHistory.form?teamId=${team.teamId}">History</a>
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
						<td><c:out value="${team.teamName}" /></td>
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td><c:out value="${team.location.name}" /></td>
						<openmrs:hasPrivilege privilege="View Member">
							<td><a
								href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}">
									<c:out value="${length[loop.index]}" />
							</a></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="Add Member">
							<td><a
								href="/openmrs/module/teammodule/teamMemberAddForm.form?teamId=${team.teamId}">Add
									Member</a></td>
						</openmrs:hasPrivilege>
						<td><c:out value="${teamLead[loop.index]}" /></td>
						<openmrs:hasPrivilege privilege="Edit Team">
							<td><a
								href="/openmrs/module/teammodule/teamMember/list.form?teamId=${team.teamId}&member=&changeLead=true">
									change TeamLead </a></td>
						</openmrs:hasPrivilege>
						<openmrs:hasPrivilege privilege="View Team">
							<td><a
								href="/openmrs/module/teammodule/teamHistory.form?teamId=${team.teamId}">History</a>
							</td>
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