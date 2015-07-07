<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Member" otherwise="/login.htm"  />

	<script src="/openmrs/openmrs.js?v=1.9.3.f535e9" type="text/javascript"></script>
	<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
		type="text/css" rel="stylesheet">
	<link rel="stylesheet"
		href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
	<link rel="stylesheet"
		href="/openmrs/moduleResources/teammodule/themes/alertify.default.css"
		id="toggleCSS" />
	<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

	<script type="text/javascript">
	function changeTeamLead(team,lead,member){
		alertify.confirm("Are you sure you want to make him the team lead ?",function (e){
			if(e){
				window.location = "/openmrs/module/teammodule/teamMember/changeTeamLead.form?teamId="+team+"&teamLeadId="+lead+"&teamMemberId="+member;
			}else{
				
			}
		});
	}
	
	function validation(team1,lead1,member1,person1){
		if(lead1 == member1){
			alertify.alert("Can't be transferred as this person is Team Lead. Kindly make a new Team Lead before transferring");
		}else{
			alertify.confirm("Are you sure you want to transfer this person ?",function (e){
				if(e){
					window.location = "/openmrs/module/teammodule/transferForm.form?teamId="+team1+"&memberId="+member1+"&personId="+person1;
				}else{
					
				}
			});
					
			}
		}
	

</script>



	<!-- 	<tr>
		<input type="hidden" value="add" name="type" />

		<a href="/openmrs/module/basicmodule/teamForm/addTeam.form">Add
			Team</a>
	</tr> -->
	<!-- <tr>
		<form name='edit' action="teamList.form">
			<button type="submit">Edit Team</button>
		</form>
	</tr> -->

	<h3>${edit}</h3>

	<table>
		<form:form method="post" commandName="searchMember">
			<tr>
				<td>Search Member by Name or ID</td>
				<td><input id="memberName" name="memberName" /></td>

				<td><button type="submit">Search</button></td>
			</tr>
		</form:form>
	</table>

	<table class="general">
		<tr>
			<openmrs:hasPrivilege privilege="Edit Member">
				<th>Edit</th>
			</openmrs:hasPrivilege>
			<th>Id</th>
			<th>Name</th>
			<th>Gender</th>
			<th>Join Date</th>
			<th>Location</th>
			<openmrs:hasPrivilege privilege="View Member">
				<th>History</th>
			</openmrs:hasPrivilege>
			<openmrs:hasPrivilege privilege="Edit Team">
				<th>Transfer</th>
			</openmrs:hasPrivilege>
			<openmrs:hasPrivilege privilege="Edit Team">
				<th>Make TeamLead</th>
			</openmrs:hasPrivilege>
		</tr>
		<c:forEach var="teamMember" items="${teamMember}" varStatus="loop">
			<tr>
				<openmrs:hasPrivilege privilege="Edit Member">
					<td><a
						href="/openmrs/module/teammodule/teamMemberEditForm.form?person_id=${teamMember.person.personId}&teamId=${team.teamId}&teamMemberId=${teamMember.teamMemberId}">Edit</a></td>
				</openmrs:hasPrivilege>
				<td valign="top"><c:out value="${teamMember.identifier}" /></td>
				<td valign="top"><c:out
						value="${teamMember.person.givenName} ${teamMember.person.familyName}" /></td>
				<td><c:out value="${teamMember.person.gender}" /></td>
				<td><c:out value="${join[loop.index]}" /></td>
				<td> <div style=" height:40px; width:145px; z-index:1 position:fixed; overflow-y:scroll"> 
					<c:out value="${teamMember.location}" /> </div> 
				</td>
				<openmrs:hasPrivilege privilege="View Member">
					<c:choose>
						<c:when test="${teamMember.voided == true}">
							<td>Voided</td>
						</c:when>
						<c:otherwise>
							<td><a
								href="/openmrs/module/teammodule/memberHistory.form?personId=${teamMember.person.personId}">History</a></td>
						</c:otherwise>
					</c:choose>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="Edit Team">
					<c:choose>
						<c:when test="${teamMember.voided == true}">
							<td>Voided</td>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${teamLead.teamMember.teamMemberId != null}">
									<td style="cursor: pointer; color: #003366"
										onClick="validation(${team.teamId},${teamLead.teamMember.teamMemberId},${teamMember.teamMemberId},${teamMember.person.personId});">Transfer</td>
								</c:when>
								<c:otherwise>
									<td style="cursor: pointer; color: #003366"
										onClick="validation(${team.teamId},0,${teamMember.teamMemberId},${teamMember.person.personId});">Transfer</td>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="Edit Team">
					<c:choose>
						<c:when
							test="${teamLead.teamMember.teamMemberId == teamMember.teamMemberId}">
							<td>Team Lead</td>
						</c:when>
						<c:otherwise>
							<c:choose>
								<c:when test="${teamMember.voided == true}">
									<td>Can't be made</td>
								</c:when>
								<c:otherwise>
									<c:choose>
										<c:when test="${teamLead.teamMember.teamMemberId != null}">
											<td style="cursor: pointer; color: #003366"
												onClick="changeTeamLead(${team.teamId},${teamLead.teamMember.teamMemberId},${teamMember.teamMemberId});">Make
												New TeamLead</td>
										</c:when>
										<c:otherwise>
											<td style="cursor: pointer; color: #003366"
												onClick="changeTeamLead(${team.teamId},0,${teamMember.teamMemberId});">Make
												New TeamLead</td>
										</c:otherwise>
									</c:choose>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
				</openmrs:hasPrivilege>
			</tr>
		</c:forEach>
	</table>

	<p>
		<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
	</p>



	<%@ include file="/WEB-INF/template/footer.jsp"%>