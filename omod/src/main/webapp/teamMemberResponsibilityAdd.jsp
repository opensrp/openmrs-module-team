<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Edit Team" otherwise="/login.htm" />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.default.css"
	id="toggleCSS" />
<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	
	function patientFunction() {
			var patient=document.getElementById("existingPersonId").value;
			var reason=document.getElementById("reason").value;
			var status=document.getElementById("status").value;
			var memberId=${memberId}
			jQuery.get("/openmrs/module/teammodule/teamMemberResponsibilityAddData.form?patientText="+patient+"&reason="+reason
					+"&status="+status+"&memberId="+memberId,function(){
				window.location.replace("/openmrs/module/teammodule/team.form");
				});
	}
</script>

<h3 style="color: red; display: inline">${errorMessage}</h3>

	<h3>Select Patient for Member</h3>

<form:form id="patientForm" name="patientForm" method="post"
	commandName="patient">
	<table class="form">
		<tr>
			<td>Team Member</td>
			<td>${memberName}</td>
		</tr>
		<tr>
			<td>Patient</td>
			<td>
			<openmrs_tag:personField formFieldName="person_id"
					formFieldId="existingPersonId"/>
			</td>
		</tr>
		<tr>
			<td>Reason: </td>
			<td>
			<input type="text" name="reason" id="reason"></input>
			</td>
			</tr>
		<tr>
			<td>Status: </td>
			<td>
			<input type="text" name="status" id="status"></input>
			</td>
			</tr>
		<tr>
			<td></td>
			<td align="center"><button type="button" onClick="patientFunction();">Add Patient</button></td>
		</tr>
	</table>
</form:form>
<%@ include file="/WEB-INF/template/footer.jsp"%>