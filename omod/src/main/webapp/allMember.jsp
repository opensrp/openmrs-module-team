<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Member" otherwise="/login.htm"  />



<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<script
	src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9"
	type="text/javascript"></script>

<script type="text/javascript">
	function showCalendar(obj, yearsPrevious) {
		// if the object doesn't have an id, just set it to some random text so jq can use it
		if (!obj.id) {
			obj.id = "something_random" + (Math.random() * 1000);
		}

		//set appendText to something so it doesn't automatically pop into the page
		var opts = {
			appendText : " "
		};
		if (yearsPrevious)
			opts["yearRange"] = "c-" + yearsPrevious + ":c10";

		var dp = new DatePicker('dd/mm/yyyy', obj.id, opts);
		jQuery.datepicker.setDefaults(jQuery.datepicker.regional['en_GB']);

		obj.onclick = null;
		dp.show();
		return false;
	}
</script>
<h1>Team Members</h1>
<c:choose>
	<c:when test="${dateTo != null}">
		<h3>Search result between date range "${dateFrom}" - "${dateTo}"</h3>
	</c:when>
	<c:when test="${searchedMember != null}">
		<h3>Search result for member name "${searchedMember}"</h3>
	</c:when>
	<c:when
		test="${searchedMember == null} && ${dateTo == null} && ${dateFrom == null}">
		<h3></h3>
	</c:when>
</c:choose>

<table>
	<form:form method="post" commandName="searchMember">
		<tr>
			<td>Search Member by Name or ID</td>
			<td><input id="memberName" name="memberName" value="${searchedMember}"/></td>
		</tr>
		<tr>
			<td>Join Date Range</td>
			<td><input id="joinDateFrom" name="joinDateFrom" size="9"
				onfocus="showCalendar(this,60)" value="${dateFrom}"/> - <input id="joinDateTo"
				name="joinDateTo" size="9" onfocus="showCalendar(this,60)" value="${dateTo}"/></td>
			<td><button type="submit">Search</button></td>
		</tr>
	</form:form>
</table>

<openmrs:hasPrivilege privilege="View Team">
<p>
	<a href="/openmrs/module/teammodule/team.form">Team List</a>
</p>
</openmrs:hasPrivilege>

<c:choose>
	<c:when test="${not empty allMembers}">
		<table class="general">
			<tr>
				<th>Full Name</th>
				<th>Identifier</th>
				<th>Gender</th>
				<th>Team</th>
				<th>Team Join Date</th>
				<th>History</th>
				<th>Is Team Lead</th>
			</tr>

			<c:forEach var="allMembers" items="${allMembers}" varStatus="loop">
				<td><c:out
						value="${allMembers.person.givenName} ${allMembers.person.familyName}" /></td>
				<td><c:out value="${allMembers.identifier}" /></td>
				<td><c:out value="${allMembers.person.gender}" /></td>
				<td><c:out value="${allMembers.team.teamName}" /></td>
				<td><c:out value="${parsedDate[loop.index]}" /></td>
				<td><a
					href="/openmrs/module/teammodule/memberHistory.form?personId=${allMembers.person.personId}">History</a></td>

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
		<p>No record(s) found</p>
	</c:otherwise>
</c:choose>






<%@ include file="/WEB-INF/template/footer.jsp"%>