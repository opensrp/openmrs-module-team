<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" >
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.default.css" id="toggleCSS" />
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	jQuery(document).ready(function() {
		jQuery("#ownsTeam").attr('checked', false);
		jQuery("#nameTip").hide();
		jQuery("#identifierTip").hide();
	});
	function validation() {
		document.getElementById("saveButton").disabled = true;
		var regexp = /^[a-z/i][a-z\- ]*[a-z/i\-|0-9]{2,}$/i;
		var idRegExp = /^[a-z|0-9]+[a-z.\-_]*[a-z|0-9]{2,}$/i;
		var name = document.getElementById("roleName").value;
		var identifier = document.getElementById("roleIdentifier").value;
		var reportsTo = document.getElementById("reportsTo").value;
		var mustSelectMessage = "";
		var dataTypeMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Role name can't be empty.";
		} if (!regexp.test(name)) {
			dataTypeMessage += "Must start with alphabet.Min 3 and max 20.Alphanumeric text,- is allowed for name.";
		} if (identifier == null || identifier == "") {
			mustSelectMessage += "Role identifier can't be empty.";
		} if (!regexp.test(identifier)) {
			dataTypeMessage += "Must start with alphabet.Min 3 and max 20.Alphanumeric text,- is allowed for name.";
		}
		
		if(mustSelectMessage != ""){
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		} else if(dataTypeMessage != ""){
			alertify.alert(dataTypeMessage);
			document.getElementById("saveButton").disabled = false;
		} else {
			var url = "/openmrs/ws/rest/v1/team/teamrole";
			var data = '{ "ownsTeam": '+document.getElementById("ownsTeam").checked+', "reportTo": "'+reportsTo+'", "name": "'+name+'" , "identifier": "'+identifier+'" }';
			jQuery.ajax({
				url : url,
				data: data,
				type: "POST",
				contentType: "application/json",
				success : function(result) { console.log("SUCCESS-TEAM ROLE"); resetForm(); saveLog("teamRole", result.uuid.toString(), "", result.name.toString(), "TEAM_ROLE_ADDED", ""); document.getElementById("saveButton").disabled = false; document.getElementById("errorHead").innerHTML = ""; document.getElementById("savedHead").innerHTML = "<p>Team Role Created Successfully</p>";
				}, error: function(jqXHR, textStatus, errorThrown) { console.log(jqXHR); document.getElementById("errorHead").innerHTML = "Error Occured While Creating Team Role"; document.getElementById("savedHead").innerHTML = ""; document.getElementById("saveButton").disabled = false; }
			});
		}
	}
	function saveLog(type, uuid, dataNew, dataOld, action, log) {
		if(action.length <= 45 && dataNew.length <= 500 && dataOld.length <= 500 && log.length <= 500) { 
			var url = "/openmrs/ws/rest/v1/team/"+type.toLowerCase()+"log/";
			var data = '{ "'+type+'":"'+uuid+'", "dataNew":"'+dataNew+'", "dataOld":"'+dataOld+'", "action":"'+action+'", "log":"'+log+'" }';
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { console.log("SUCCESS-SAVE "+type.toUpperCase()+" LOG"); 
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-SAVE "+type.toUpperCase()+" LOG"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Location(s)</p>"; }
			});
		}
		else { 
			var errorStr = "";
			if(action.length > 45) { errorStr += "Action must have atleast 45 Characters.<br/>" }
			if(dataNew.length > 500) { errorStr += "New Data must have atleast 500 Characters.<br/>" }
			if(dataOld.length > 500) { errorStr += "Old Data must have atleast 500 Characters.<br/>" }
			if(log.length > 500) { errorStr += "Log must have atleast 500 Characters.<br/>" }
			console.log("errorStr: "+errorStr);
			document.getElementById("editHead").innerHTML = errorStr;
		}
	}
	function resetForm() {
		document.getElementById("roleName").value = "";
		document.getElementById("roleIdentifier").value = "";
		document.getElementById("reportsTo").value = "0";
		document.getElementById("ownsTeam").checked=false;
		window.scrollTo(0, 0);
	}
</script>

<ul id="menu">
	<li class="first active"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberView.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Add Role</h2>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="savedHead" align="center" style="color: green;">${saved}</h3>
<h3 id="editHead" align="center" style="color: green;">${edit}</h3>

<form:form id="saveRole" name="saveRole" commandName="roleData">
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
				Identifier:
			</td>
			<td>
				<form:input id="roleIdentifier" path="identifier" maxlength="20" onfocus="jQuery('#identifierTip').show();" onblur="jQuery('#identifierTip').hide();" />
				<span style="color: red">
					 *
				</span>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="identifierTip">
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