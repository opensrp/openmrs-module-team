<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm" />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.default.css"
	id="toggleCSS" />
<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">

	jQuery(document).ready(function() {
		jQuery("#ownsTeam").attr('checked', false);
	});
	function validation() {
		document.getElementById("saveButton").disabled = true;
		var name = roleName.value;
		var reportsTo = document.getElementById("reportsTo");
		var selectedValue = reportsTo.options[reportsTo.selectedIndex].value;
		var mustSelectMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Role name can't be empty.";
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		}
		if (selectedValue == 0) {
			mustSelectMessage += "Must Select Report To Field";
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		}
		else {
			jQuery.ajax({
				url : "/openmrs/ws/rest/v1/team/teamrole?q=" + name
						+ "&v=full",
				type : "GET",
				contentType : "application/json;charset=UTF-8",
				dataType : "json",
				success : function(data, status) {
					if(status==200)
						document.getElementById("saveRole").submit();
				}
			});
		}
	}
</script>

<h2 align="center">Add Role</h2>
<table class="role">
	<form:form id="saveRole" name="saveRole" method="post" commandName="roleData">
		<tr>
			<td>Name</td>
			<td><form:input id="roleName" path="name" />
			</td>
		</tr>
		<tr>
			<td>Reports To</td>
			<td><form:select id="reportsTo" path="reportTo"
					cssStyle="width:165px">
					<form:option value="0" label=" Please Select " />
					<c:forEach var="reportsTo" items="${reportsTo}" varStatus="loop">
						<form:option value="${reportsTo}">${reportsTo.name}</form:option>
					</c:forEach>
				</form:select>
		</tr>
		<tr>
			<td>Owns Team ?</td>
			<td><form:checkbox id="ownsTeam" path="ownsTeam" /></td>
		</tr>
		
		<tr>
			<input type="hidden" value="save" name="type" />
			<td></td>
			<td><button id="saveButton" type="button" onClick="validation();">Save</button></td>
		</tr>

	</form:form>
	<tr>
		<td><a href="/openmrs/module/teammodule/teamRole.form">Show all Roles</a></td>
	</tr>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>