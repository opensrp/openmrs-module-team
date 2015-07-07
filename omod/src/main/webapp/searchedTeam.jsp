<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Team" otherwise="/login.htm"  />


<h3>Search Results for ${searchedTeam}</h3>
<table>
	<form:form method="post" commandName="searchTeam">
		<tr>
			<td><form:input id="teamName" path="teamName" /></td>
			<td><button type="submit">Search</button></td>
		</tr>

	</form:form>
</table>

<table border="1" cellpadding="0">
	<tr>
		<th>Edit</th>
		<th>Id</th>
		<th>Team Name</th>
		<th>Date Created</th>
		<th>Location</th>
		<th>Members</th>
		<th>Add Member</th>
		<th>Team Lead</th>
		<th>History</th>
	</tr>
	<!-- <c:set var="length" value="${length}"/> -->

			<c:forEach var="team" items="${team}" varStatus="loop">
				<c:if test="${team.voided}">
					<tr>
						<td><a href="/openmrs/module/teammodule/teamForm/editTeam.form?teamId=${team.teamId}">Edit</a>
						</td>
						<td><c:out value="${team.teamIdentifier}" /></td>
						<td valign="top"><c:out value="${team.teamName}" /></td>
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td><c:out value="${team.location.name}" /></td>
						<td><a href="/openmrs/module/teammodule/teamMember.form?teamId=${team.teamId}">
								<c:out value="${length[loop.index]}" />
						</a></td>
						<td>Voided/Retired</td>
						<td><c:out value="${teamLead[loop.index]}" /></td>
						<td><a href="/openmrs/module/teammodule/teamHistory.form?teamId=${team.teamId}">History</a></td>

					</tr>
				</c:if>
				<!-- <form:form action="teamForm.form">
			<input type="hidden" value="edit" name="type" /> -->
				<c:if test="${team.voided == false}">
					<tr>
						<td><a href="/openmrs/module/teammodule/teamForm/editTeam.form?teamId=${team.teamId}">Edit</a>
						</td>
						<td><c:out value="${team.teamIdentifier}" /></td>
						<td><c:out value="${team.teamName}" /></td>
						<!-- 	<td><fmt:formatDate value="${team.dateCreated}"
								pattern="dd-MM-yyyy" /></td> -->
						<td><c:out value="${parsedDate[loop.index]}" /></td>
						<td><c:out value="${team.location.name}" /></td>
						<td><a
							href="/openmrs/module/teammodule/teamMember.form?teamId=${team.teamId}">
								<!-- <c:set var="i" value="${status.index}"/> --> <c:out
									value="${length[loop.index]}" />
						</a></td>
						<td><a href="/openmrs/module/teammodule/teamMemberForm/addMember.form?teamId=${team.teamId}">Add
								Member</a></td>
						<td><c:out value="${teamLead[loop.index]}" /></td>
						<td><a href="/openmrs/module/teammodule/teamHistory.form?teamId=${team.teamId}">History</a></td>


						<!-- &teamId=${team.teamId} -->
					</tr>

				</c:if>
				<!-- </form:form> -->
			</c:forEach>
</table>
<table>
	<tr>
		<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
	</tr>
</table>


<%@ include file="/WEB-INF/template/footer.jsp"%>