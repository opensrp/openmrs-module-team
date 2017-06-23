<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" >
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.default.css" id="toggleCSS" />
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	\$j=jQuery;
	jQuery(document).ready(function() {
		jQuery("#nameTip").hide();
		jQuery("#idTip").hide();
		document.getElementById("teamName").value = "";
		document.getElementById("teamIdentifier").value = "";
		document.getElementById("teamSupervisorOption").value = "0";
	});
	 
	function validation() {
		document.getElementById("saveButton").disabled = true;
		var regexp = /^[a-z/i][a-z\- ]*[a-z/i\-|0-9]{2,}$/i;
		var idRegExp = /^[a-z|0-9]+[a-z.\-_]*[a-z|0-9]{2,}$/i;
		var name = document.getElementById("teamName").value;
		var id = document.getElementById("teamIdentifier").value;
		var location = document.getElementById("location");
		var supervisor =  document.getElementById("teamSupervisorOption").value;
		var selectedValue = location.options[location.selectedIndex].value;
		var mustSelectMessage= "";
		var dataTypeMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Team name can't be empty.";
		} if (!regexp.test(name)) {
			dataTypeMessage += "<br>Must start with alphabet.Min 3 and max 20.Alphanumeric text,- is allowed for name.";
		} if (id == null || id == "") {
			mustSelectMessage += "<br>Team identifier can't be empty.";
		} if(!idRegExp.test(id)){
			dataTypeMessage += "<br>Min 3, max 20 All data types and either [- . Or _ ] are allowed for identifier.";
		} if (selectedValue == 0) {
			mustSelectMessage += "<br>Please select a location.";
		} 
		if(mustSelectMessage != ""){
			alertify.alert(mustSelectMessage);
			document.getElementById("saveButton").disabled = false;
		} else if(dataTypeMessage != ""){
			alertify.alert(dataTypeMessage);
			document.getElementById("saveButton").disabled = false;
		} else { var str = "";
			jQuery.ajax({
				url:"/openmrs/ws/rest/v1/team/team?v=full&q="+name,
				success: function(data,status) { var myNames = []; 
					for(var i=0; i<data.results.length; i++) { myNames.push(data.results[i].teamName); }
					if(myNames.includes(name)) { str += "Name must be unique";
						jQuery.ajax({
							url:"/openmrs/ws/rest/v1/team/team?v=full&q="+id,
							success: function(data,status) { var myIdentifiers = []; 
								for(var i=0; i<data.results.length; i++) { myIdentifiers.push(data.results[i].teamIdentifier); }
								if(myIdentifiers.includes(id)) { str += "<br>Identifier must be unique"; }
								document.getElementById("saveButton").disabled = false;
								alertify.alert(str);
							}, error: function(jqXHR, textStatus, errorThrown) { console.log(jqXHR); }
						});
					}
					else {
						jQuery.ajax({
							url:"/openmrs/ws/rest/v1/team/team?v=full&q="+id,
							success: function(data,status) { var myIdentifiers = []; 
								for(var i=0; i<data.results.length; i++) { myIdentifiers.push(data.results[i].teamIdentifier); }
								if(myIdentifiers.includes(id)) { alertify.alert("Identifier must be unique"); alertify.alert(str); }
								else {
									var url = "/openmrs/ws/rest/v1/team/team";
									var data = '{ "teamName": "'+name+'", "location": "'+selectedValue+'", "teamIdentifier": "'+id+'", "supervisor": "'+supervisor+'" }';
									jQuery.ajax({
										url : url,
										data: data,
										type: "POST",
										contentType: "application/json",
										success : function(result) { console.log("SUCCESS-TEAM ROLE"); resetForm();  saveLog("team", result.uuid.toString(), "", result.teamName.toString(), "TEAM_ADDED", ""); document.getElementById("saveButton").disabled = false; document.getElementById("errorHead").innerHTML = ""; document.getElementById("savedHead").innerHTML = "<p>Team Created Successfully</p>";
										}, error: function(jqXHR, textStatus, errorThrown) { console.log(jqXHR); document.getElementById("errorHead").innerHTML = "Error Occured While Creating Team"; document.getElementById("savedHead").innerHTML = ""; document.getElementById("saveButton").disabled = false; }
									});
								}
							}
						});
					}			
				}
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
		document.getElementById("teamName").value = "";
		document.getElementById("teamIdentifier").value = "";
		document.getElementById("teamSupervisorOption").value = "0";
		document.getElementById("location").value = "0";
		window.scrollTo(0, 0);
	}
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberView.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Add Team</h2>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="savedHead" align="center" style="color: green;">${saved}</h3>
<h3 id="editHead" align="center" style="color: green;">${edit}</h3>


<form:form id="saveTeam" name="saveTeam" method="post" commandName="teamData">
	<table class="team">
		<tr>
			<td>
				Name:
			</td>
			<td>
				<form:input id="teamName" path="teamName" maxlength="20" onfocus="jQuery('#nameTip').show();" onblur="jQuery('#nameTip').hide();" />
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
				<form:input id="teamIdentifier" path="teamIdentifier" maxlength="20" onfocus="jQuery('#idTip').show();" onblur="jQuery('#idTip').hide();" />
				<span style="color: red">
					 *
				</span>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span style="padding-left: 12px" id="idTip">
					Min 3, max 20 All data types and either [- . Or _ ] are allowed for identifier.
				</span>
			</td>
		</tr>
		<tr>
			<td>
				Location:
			</td>
			<td>
				<form:select id="location" path="location" cssStyle="width:181px">
					<form:option value="0" label=" Please Select " />
					<c:forEach var="location" items="${location}" varStatus="loop">
						<form:option value="${location.uuid}">${location.name}</form:option>
					</c:forEach>
				</form:select>
				<span style="color: red">
					 *
				 </span>
			 </td>
		</tr>
		<tr>
			<td>
				Supervisor:
			</td>
			<td>
				<select id="teamSupervisorOption" style="width: 181px;">
					<option value="0" label="Please Select " />
					<c:forEach items="${teamSupervisors}" var="teamSupervisor" varStatus="loop">
						<option value="${teamSupervisor.uuid}">${teamSupervisor.person.personName}</option>
					</c:forEach>
				</select>
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
				<a href="/openmrs/module/teammodule/team.form">
					Back to teams
				</a>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>