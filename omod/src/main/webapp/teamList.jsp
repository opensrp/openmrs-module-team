<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<table>

<c:forEach var="team" items="${team}">
<form:form action="teamForm.form">
<input type="hidden" value="edit" name="type" /> 
			<tr>
				<td valign="top">
					<c:out value="${team.teamName}"/>
				</td>		
				<td> <button type="submit"> Edit </button></td>  <!-- &teamId=${team.teamId} -->
			</tr>
</form:form>
		</c:forEach>

</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>