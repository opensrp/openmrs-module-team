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
		console.log("edit")
	});
	function validation() {
		document.getElementById("saveButton").disabled = true;
		var name = roleName.value;
		var reportsTo = document.getElementById("reportsTo").value;
		
		console.log("name: "+name);
		console.log("reportsTo: "+reportsTo);
		var mustSelectMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Role name can't be empty.";
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		}
		if (reportsTo == 0) {
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
					console.log(data)
					console.log(status)
					document.getElementById("saveRole").submit();
				}
			});
		}

	}
</script>

<h2 align="center">Edit Role</h2>
<h3 style="color: red; display: inline">${error}</h3>
<h3 align="center" style="color: green;">${saved}</h3>
<h3 style="color: green; display: inline">${edit}</h3>
<table class="role">
	<form:form id="saveRole" name="saveRole" method="post" commandName="roleData">
		<tr>
			<td>Name</td>
			<td><form:input id="roleName" path="name" />
			</td>
		</tr>
		<tr>
			<td>Reports To</td>
			<td>
			<select id="reportsTo" name="reportsTo" style="width:165px">
				<option value="${reportsTo.uuid}">${reportsTo.name}</option>
				<c:forEach var="reportsToOptions" items="${reportsToOptions}" varStatus="loop">
					<c:if test="${reportsToOptions.uuid ne reportsTo.uuid}">
						<option value="${reportsToOptions.uuid}">${reportsToOptions.name}</option>
					</c:if>
				</c:forEach>
			</select>
			</td>
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