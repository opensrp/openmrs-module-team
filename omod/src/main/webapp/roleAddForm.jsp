<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm" />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.default.css" id="toggleCSS" />
<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#ownsTeam").attr('checked', false);
		jQuery("#nameTip").hide();
	});
	function validation() {
		document.getElementById("saveButton").disabled = true;
		var regexp = /^[a-z/i][a-z\- ]*[a-z/i\-|0-9]{2,}$/i;
		var idRegExp = /^[a-z|0-9]+[a-z.\-_]*[a-z|0-9]{2,}$/i;
		var name = document.getElementById("roleName").value;
		var reportsTo = document.getElementById("reportsTo").value;
		var mustSelectMessage = "";
		var dataTypeMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Role name can't be empty.";
		} if (!regexp.test(name)) {
			dataTypeMessage += "Must start with alphabet.Min 3 and max 20.Alphanumeric text,- is allowed for name.";
		}
		
		if(mustSelectMessage != ""){
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		} else if(dataTypeMessage != ""){
			alertify.alert(dataTypeMessage);
			document.getElementById("saveButton").disabled = false;
		} else {
			console.log(name);
			jQuery.ajax({
				url : "/openmrs/ws/rest/v1/team/teamrole?q=" + name + "&v=full",
				success : function(data, status) { var myNames = []; 
					for(var i=0; i<data.results.length; i++) { myNames.push(data.results[i].name); }
					if(myNames.includes(name)) { alertify.alert("Name must be unique"); document.getElementById("saveButton").disabled = false; }
					else { document.getElementById("saveRole").submit(); }
				}, error: function(jqXHR, textStatus, errorThrown) { console.log(jqXHR); document.getElementById("errorId").innerHTML = "Error Occured While Creating Team"; document.getElementById("savedId").innerHTML = ""; }
			});
		}
	}
</script>

<h2 align="center">Add Role</h2>

<h3 style="color: red; display: inline">${error}</h3>
<h3 align="center" style="color: green;">${saved}</h3>
<h3 style="color: green; display: inline">${edit}</h3>

<form:form id="saveRole" name="saveRole" method="post" commandName="roleData">
	<table class="team">
		<tr>
			<td>
				Name:
			</td>
			<td>
				<form:input id="roleName" path="name" maxlength="20" onfocus="jQuery('#nameTip').show();" onblur="jQuery('#nameTip').hide();" />
				<span style="color: red">
					 *
				</span>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="nameTip">
					Must start with alphabet.Min 3 and max 20.Alphanumeric text,- is allowed
				</span>
			</td>
		</tr>
		<tr>
			<td>
				Reports To:
			</td>
			<td>
				<select id="reportsTo" name="reportsTo" style="width:181px">
					<option value="0" label=" Please Select " />
					<c:forEach var="reportsTo" items="${reportsTo}" varStatus="loop">
						<option value="${reportsTo.uuid}">${reportsTo.name}</option>
					</c:forEach>
				<select>
			</td>
		</tr>
		<tr>
			<td>
				Owns Team:
			</td>
			<td>
				<form:checkbox id="ownsTeam" path="ownsTeam" />
			</td>
		</tr>
		<tr>
			<input type="hidden" value="save" name="type" />
			<td></td>
			<td>
				<button id="saveButton" type="button" onClick="validation();">
					Add
				</button>
			</td>
		</tr>
		<tr>
			<td>
				<a href="/openmrs/module/teammodule/teamRole.form">
					Show all Roles
				</a>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>