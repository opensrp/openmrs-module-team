<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	
	#history, #teamRoleDetail {
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #teamRoleDetail th, #teamRoleDetail td {
	    text-align: left;
	    font-size: 14px;
	    padding: 10px;
	}
	#history tr:hover, #teamRoleDetail tr:hover {
		background-color:#f5f5f5
	}
	#history tr:nth-child(odd), #teamRoleDetail tr:nth-child(odd) {
		background-color:#f5f5f5
	}
	#images {
		cursor: pointer;
		width: 20px; height: 20px; float: right;
	}
	
</style>

<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-ui.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('#historyDialog').hide();
		$("#editTeamRoleDiv").hide();
		$("#editTeamRoleReportToDiv").hide();
		$("#editTeamRoleVoidedDiv").hide();
		$("#TeamRoleDetailDiv").hide();
		CreateTable();
		
	});
	
	function CreateTable()
	{
		var tbody=null,table=null,dataTable=null;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
		tbody.id= "tbody";
		var  data = $.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(teamRoleData) {
			teamRoles = teamRoleData.results; 
			table.appendChild(tbody);
			dataTable=$('#general').DataTable({
				"data": teamRoles,
				"language" : {
					"search" : "_INPUT_",
					"searchPlaceholder" : "Search..."
				},
				"columns": [
		            { "data": "uuid",
		              "render": function ( data, type, row, meta) {
		                return "<a title='Edit Team Role' "+
			                	" onclick='editTeamRoleInfo(\""	+ data	+ "\");' >"
			                	+"<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png' style='padding-right: 40%;'></a>";
		            	}
		            },
		            { "data": "identifier",
		            	"render": function ( data, type, row, meta) {
		                return "<a title='Team Role Detail' style='cursor: pointer;'"+
			                	" onclick='roleDetail(\""	+ row.uuid	+ "\");' >"+data+"</a>";
		            	}
		            },
		            { "data": "display" },
		            { "data": "ownsTeam" },
		            { "data": "reportTo",
		              "render": function ( data, type, row, meta) {
		                if(data)
						{
		                	return data.name+ "<a title='Edit Team Role Report To'"+
								" onclick='editTeamRoleReportTo(\""+ row.uuid+ "\",\""+ data.uuid+ "\");'>"
								+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
						}
		            		return "<a title='Edit Team Role Report To'"+
								" onclick='editTeamRoleReportTo(\""+ row.uuid+ "\",\"\");'>"
								+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
						}
		            },
		            { "data": "reportByName",
		              "render": function(data,type,row,meta) {
			            	if (data)
			            	{
								var reportBy = "";
								for (var j = 0; j < data.length; j++) {
									reportBy += "<li>" + data[j]+ "</li>";
								}
								return reportBy;
							} else {
								return "";
							}
		            	}
		            },
		            { "data": "voided",
		            	"render": function(data,type,row,meta){
		            		return data
							+ "<a title='Void Team Role' class='table-action-link'"+
							"onclick='editTeamRoleVoided(\""+ row.uuid+ "\",\""+ data+ "\",\""
							+ row.voidReason+ "\");'>"
							+"<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";	
		            	}
		            },
		            { "data": "members",
		            	"render": function(data, type, row, meta){
		            			if(data)
		            				return "<a title='Team Role Members' style='cursor:pointer' onclick='viewMember(\""+ row.uuid+ "\");'>"
		            				+ data + " Members</a>"
		            			else
		            				return "-"
			            	}
		            },
		            { "data": "uuid",
		            	"render": function(data,type,row,meta){
		            		return "<a title='Team Role History' onClick='teamsHierarchyHistory(\""+ row.uuid + "\")'>"
		            		+ "<img id='images' src='/openmrs/moduleResources/teammodule/img/history.png' ></a>"		
		            	}
		            }
		          ],
				"paging" : true,
				"lengthChange" : false,
				"searching" : true,
				"ordering" : true,
				"info" : false,
				"autoWidth" : true,
				"sDom" : 'lfrtip',
			});
		});
	}
	
	function roleDetail(uuid)
	{
		$.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(roles) {
			var reportTo = "", reportBy = "", dateCreated = "", dateChanged = "";
			var teamRoles=roles.results;
			for(var i=0;i<teamRoles.length;i++)
			{
				console.log(teamRoles[i].uuid+", "+uuid);
				if(teamRoles[i].uuid==uuid)
				{	
					if (teamRoles[i].reportTo === null)
					{
						reportTo = "";
					}else
					{
						reportTo = teamRoles[i].reportTo.name;
					}
					if (teamRoles[i].reportByName !== null) {
						for (var j = 0; j < teamRoles[i].reportByName.length; j++) {
							if (j === teamRoles[i].reportByName.length - 1) 
								reportBy += teamRoles[i].reportByName[j];
							else 
								reportBy += teamRoles[i].reportByName[j] + ", ";
						}
					}
					else {
						reportBy = "";
					}
					if (teamRoles[i].auditInfo != null) {
					
						if (teamRoles[i].auditInfo.dateCreated === null)
							dateCreated = "";
						else
							dateCreated = teamRoles[i].auditInfo.dateCreated.toString().substr(0, 10);
						if (teamRoles[i].auditInfo.dateChanged === null)
							dateChanged = "";
						else
							dateChanged = teamRoles[i].auditInfo.dateChanged.toString().substr(0, 10);
					}
					$('#teamRoleDetail tr td').children('snap').remove();
					$("#teamRoleDetailIdentifier").append("<snap>"+teamRoles[i].identifier+"</snap>");
					$("#teamRoleDetailName").append("<snap>"+teamRoles[i].name+"</snap>");
					$("#teamRoleDetailOwnsTeam").append("<snap>"+teamRoles[i].ownsTeam+"</snap>");
					$("#teamRoleDetailReportTo").append("<snap>"+reportTo+"</snap>");
					$("#teamRoleDetailReportby").append("<snap>"+reportBy+"</snap>");
					$("#teamRoleDetailNumberOfMember").append("<snap>"+teamRoles[i].members+"</snap>");
					$("#teamRoleDetailVoided").append("<snap>"+teamRoles[i].voided+"</snap>");
					$("#teamRoleDetailVoidReason").append("<snap>"+teamRoles[i].voidReason+"</snap>");
					$("#teamRoleDetailDateCreated").append("<snap>"+dateCreated+"</snap>");
					$("#teamRoleDetailDateChanged").append("<snap>"+dateChanged+"</snap>"); 
			}
		}
			
		$("#TeamRoleDetailDiv").dialog({
			width : "500px",
			height : "auto",
			title : "Team Role Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	});
	}
	function teamsHierarchyHistory(teamRoleUuid) {
		$.get("/openmrs/ws/rest/v1/team/teamrolelog?v=full&q="+ teamRoleUuid,
			function(data) {
			document.getElementById("historyBody").innerHTML = "";
			if (data.results.length > 0) {
				for (i = 0; i < data.results.length; i++) {
					$("#history").append("<tr id=\"historyRow\">"
						+ "<td style=\"text-align: left;\" valign=\"top\">"
						+ data.results[i].teamRole.display
						+ "</td>"
						+ "<td style=\"text-align: left;\" valign=\"top\">"
						+ data.results[i].action
						+ "</td>"
						+ "<td style=\"text-align: left;\" valign=\"top\">"
						+ data.results[i].dataNew
						+ "</td>"
						+ "<td style=\"text-align: left;\" valign=\"top\">"
						+ data.results[i].dataOld
						+ "</td>"
						+ "<td style=\"text-align: left;\" valign=\"top\">"
						+ data.results[i].auditInfo.dateCreated
						+ "</td>" + "</tr>");
				}
			} else
			{
				var cell = document.getElementById("historyBody").insertRow(-1).insertCell(-1);
				cell.colSpan = 5;
				cell.innerHTML = "<strong>No Records Found</strong>";
				cell.setAttribute("style","border: 1px solid; text-align: center;");
			}
			});
		$("#historyDialog").dialog({
			width : "auto",
			height : "400",
			title : "Team Role - History",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function editTeamRoleInfo(uuid, type) {
		$.get("/openmrs/ws/rest/v1/team/teamrole/" + uuid, function(teamRole) {
			$("#teamRoleName").val(teamRole.name);
			$("#teamRoleIdentifier").val(teamRole.identifier);
			$("#teamRoleOwnsTeam").val(teamRole.ownsTeam.toString());
			$("#teamRoleInfoUuid").val(uuid);
			$("#editTeamRoleDiv").dialog({
				width : "500px",
				height : "auto",
				title : "Team Role - Edit",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}

	function editTeamRoleReportTo(uuid, reportTo) {
		$('#teamRoleReportTo').children('option').remove();
		$.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(teamRoles) {
			for (var i = 0; i < teamRoles.results.length; i++) {
				if (teamRoles.results[i].uuid != uuid) {
					$("#teamRoleReportTo").append(
						'<option value="'+teamRoles.results[i].uuid+'">'+ teamRoles.results[i].name + '</option>');
				}
			}
			
			$("#teamRoleReportTo").val(reportTo);
			$("#teamRoleReportToUuid").val(uuid);

			$("#editTeamRoleReportToDiv").dialog({
				width : "500px",
				height : "auto",
				title : "Team Role - Edit",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}

	function editTeamRoleVoided(uuid, voided, reason) {
		$("#teamRoleVoided").val(voided);
		$("#teamRoleVoidedReason").val(reason);
		$("#teamRoleVoidedUuid").val(uuid);

		$("#editTeamRoleVoidedDiv").dialog({
			width : "500px",
			height : "auto",
			title : "Team Role - Void",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function viewMember(uuid) {
					document.getElementById("role").value = uuid
					document.getElementById("teamRoleForm").submit();
	}

	function teamRoleInfoSubmit() {
		var uuid = document.getElementById("teamRoleInfoUuid").value;
		var name = document.getElementById("teamRoleName").value;
		var identifier = document.getElementById("teamRoleIdentifier").value;
		var ownsTeam = document.getElementById("teamRoleOwnsTeam").value;
		var url = "/openmrs/ws/rest/v1/team/teamrole/" + uuid;
		var data = {};
		data.name=name;
		data.identifier=identifier;
		data.ownsTeam=ownsTeam;
		data = JSON.stringify(data);
		$.ajax({
			url : url,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamRole = result;
				saveLog("teamRole", teamRole.uuid, teamRole.name, teamRole.name, "TEAM_ROLE_EDITED",
						"Name: " + name + ", Identifier: " + identifier+", ownsTeam: " + ownsTeam);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>";
				$("#editTeamRoleDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable();
				setTimeout(function() {
				document.getElementById("saveHead").innerHTML = ""}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-ALL-TEAMS");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Reading All Team Roles</p>";
				setTimeout(function() {
					document.getElementById("errorHead").innerHTML = ""}, 5000);
			}
		});
	}

	function teamRoleReportToSubmit() {
		var uuid = document.getElementById("teamRoleReportToUuid").value;
		var reportTo = document.getElementById("teamRoleReportTo").value;

		var url = "/openmrs/ws/rest/v1/team/teamrole/" + uuid;
		var data = {};
		if(reportTo!="")
		{
		data.reportTo=reportTo;	
		}
		data = JSON.stringify(data);
		console.log(data);
		$.ajax({
			url : url,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamRole = result;
				saveLog("teamRole", teamRole.uuid,teamRole.name, teamRole.name,"TEAM_ROLE_REPORT_TO_EDITED", "Reporting to: "+ reportTo);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>";
				$("#editTeamRoleReportToDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable();
				setTimeout(function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-ALL-TEAMS");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Reading All Team Roles</p>";
				$("#editTeamRoleReportToDiv").dialog('close');
				setTimeout(function() {
					document.getElementById("errorHead").innerHTML = ""
				}, 5000);
			}
		});
	}

	function teamRoleVoidedSubmit() {
		var uuid = document.getElementById("teamRoleVoidedUuid").value;
		var voided = document.getElementById("teamRoleVoided").value;
		var voidReason = document.getElementById("teamRoleVoidedReason").value;
		
		if (voidReason.length > 255) {
			document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long";
			return;
		} else if (voided === "true" && voidReason === "") {
			document.getElementById("voidError").innerHTML = "Void Reason Can't be Empty";
			return;
		} 

		var data = {};

		if (voided != "false") {
			data.voided=voided;
			data.voidReason=voidReason;
		} else {
			data.voided=voided;
		}
		data = JSON.stringify(data);

		document.getElementById("voidError").innerHTML = "";
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/teamrole/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamRole = result;
				saveLog("teamRole", teamRole.uuid,teamRole.name, teamRole.name,"TEAM_ROLE_VOIDED_STATUS_EDITED",
						"Voided Status Change to: " + voided);
				
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>";
				$("#editTeamRoleVoidedDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable();
				
				setTimeout(function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-ALL-TEAMS");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Reading All Team Roles</p>";
				$("#editTeamRoleReportToDiv").dialog('close');
				setTimeout(function() {
					document.getElementById("errorHead").innerHTML = ""
				}, 5000);
			}
		});
	}

	function saveLog(type, uuid, dataNew, dataOld, action, log) {
		if (action.length <= 45 && dataNew.length <= 500
				&& dataOld.length <= 500 && log.length <= 500) {
			var url = "/openmrs/ws/rest/v1/team/" + type.toLowerCase() + "log/";
			var data = {};
			data[type]=uuid;
			data.dataNew=dataNew;
			data.dataOld=dataOld;
			data.action=action;
			data.log=log;
			data = JSON.stringify(data);
			
			$.ajax({
				url : url,
				data : data,
				type : "POST",
				contentType : "application/json",
				success : function(result) {
					console.log("SUCCESS-SAVE " + type.toUpperCase() + " LOG");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("ERROR-SAVE " + type.toUpperCase() + " LOG");
					console.log(jqXHR);
					document.getElementById("saveHead").innerHTML = "";
					document.getElementById("errorHead").innerHTML = "";
				}
			});
		} else {
			var errorStr = "";
			if (action.length > 45) {
				errorStr += "Action must have atleast 45 Characters.<br/>"
			}
			if (dataNew.length > 500) {
				errorStr += "New Data must have atleast 500 Characters.<br/>"
			}
			if (dataOld.length > 500) {
				errorStr += "Old Data must have atleast 500 Characters.<br/>"
			}
			if (log.length > 500) {
				errorStr += "Log must have atleast 500 Characters.<br/>"
			}
			console.log("errorStr: " + errorStr);
			document.getElementById("editHead").innerHTML = errorStr;
		}
	}
	function onDialogOpen(event, ui) {
		var dialogHeads = document.getElementsByClassName("ui-dialog-title");
		for (var loop = 0; loop < dialogHeads.length; loop++) {
			dialogHeads[loop].setAttribute("style", "font-size: 18px;");
		}
		var dialogCloseBtns = document
				.getElementsByClassName("ui-button-icon ui-icon ui-icon-closethick");
		for (var loop = 0; loop < dialogCloseBtns.length; loop++) {
			dialogCloseBtns[loop].setAttribute("style",
					"top: 0; left: 0; right: 0; bottom: 0;");
		}
	}
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMember.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Team Roles</h2>

<a href="/openmrs/module/teammodule/addRole.form" style="float: right;" title="Add Team Role"><img src="/openmrs/moduleResources/teammodule/img/plus.png" style=" width: 50px; width: 50px;position: relative; top: -10px; right: 10px; " ></a>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" align="center" style="color: green">${saved}</h3>
<h3 id="editHead" align="center" style="color: green;">${edit}</h3>

<table class="display" id="general" style="border-left: 1px solid black; border-right: 1px solid black;">
	<thead>
		<tr>
			<openmrs:hasPrivilege privilege="Edit Team">
				<th style="border-top: 1px solid black;">Edit</th>
			</openmrs:hasPrivilege>
			<th style="border-top: 1px solid black;">Identifier</th>
			<th style="border-top: 1px solid black;">Name</th>
			<th style="border-top: 1px solid black;">Owns Team</th>
			<th style="border-top: 1px solid black;">Report To</th>
			<th style="border-top: 1px solid black;">Reported By</th>
			<th style="border-top: 1px solid black;">Voided</th>
			<th style="border-top: 1px solid black;"># Members</th>
			<openmrs:hasPrivilege privilege="View Team">
				<th style="border-top: 1px solid black;">History</th>
			</openmrs:hasPrivilege>
		</tr>
	</thead>
</table>

<div id="historyDialog">
	<table id="history">
		<thead>
			<tr>
				<th>Team Name</th>
				<th>Action</th>
				<th>New Data</th>
				<th>Old Data</th>
				<th>Date</th>
			</tr>
		</thead><tbody id="historyBody"></tbody>
	</table>
</div>

<div id="TeamRoleDetailDiv">
	<table id='teamRoleDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="teamRoleDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="teamRoleDetailName"></td>
		</tr>
		<tr>
			<td>Owns Team:</td>
			<td id="teamRoleDetailOwnsTeam"></td>
		</tr>
		<tr>
			<td>Report To:</td>
			<td id="teamRoleDetailReportTo"></td>
		</tr>
		<tr>
			<td>Report By:</td>
			<td id="teamRoleDetailReportby"></td>
		</tr>
		<tr>
			<td>Members:</td>
			<td id="teamRoleDetailNumberOfMember"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="teamRoleDetailVoided"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="teamRoleDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="teamRoleDetailDateChanged"></td>
		</tr>
	</table>
</div>
<div id="editTeamRoleDiv">
<h3 id='infoError' name='infoError' style='color: red; display: inline'></h3>
	<form>
		<table style='width: 100%;' id="editTeamRoleTable">
			<tr>
				<td style='font-size: 18px;'>Name:</td>
				<td><input style='width: 95%; font-size: 14px; padding: 5px;'
					type='text' id='teamRoleName' name='teamRoleName' value='' maxlength='45'></td>
			</tr>
			<tr>
				<td style='font-size: 18px;'>Identifier:</td>
				<td><input style='width: 95%; font-size: 14px; padding: 5px;'
					type='text' id='teamRoleIdentifier'
					name='teamRoleIdentifier' maxlength='45'></td>
			</tr>
			<tr>
				<td style='font-size: 18px;'>Owns Team:</td>
				<td><select style='font-size: 14px; padding: 5px; width: 100%;'
					id='teamRoleOwnsTeam' name='teamRoleOwnsTeam' title='Owns Team'>
					<option value='true' >True</option>
					<option value='false' >False</option>
				</select></td>
			</tr>
			<tr>
			<td><input type="hidden" id="teamRoleInfoUuid"></td>
			<td><input type="button" onclick="teamRoleInfoSubmit()" id="editTeamRoleInfoSubmit" name="editTeamRoleInfoSubmit" value="update"> </td>
			</tr>	
		</table>
	</form>
</div>
<div id="editTeamRoleReportToDiv">
	<form>
		<table style='width: 100%;' id="editTeamRoleReportToTable" >
			<tr>
				<td style='font-size: 18px;'>Reports To:</td>
				<td><select style='font-size: 14px; padding: 5px; width: 100%;'
					id='teamRoleReportTo'>
				</select></td>
			<tr>
			<td><input type="hidden" id="teamRoleReportToUuid"></td>
			<td><input type="button" onclick="teamRoleReportToSubmit()" id="editTeamRoleReportToSubmit" name="editTeamRoleReportToSubmit" value="update"> </td>
			</tr>	
		</table>
	</form>
</div>
<div id="editTeamRoleVoidedDiv">
	<h3 id='voidError' name='voidError' style='color: red; display: inline'></h3>
	<form>
	<table style='width: 100%;' id="editTeamRoleVoidedTable">
		<tr>
			<td style='font-size: 18px;'>Voided:</td>
			<td><select id='teamRoleVoided' name='teamRoleVoided'>
					<option value="true">True</option>
					<option value="false">False</option>
			</select></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td><textarea id='teamRoleVoidedReason' name='teamRoleVoidedReason'
					style='width: 95%; font-size: 14px; padding: 5px;'>
				</textarea>
			</td>
		</tr>
		<tr>
			<td><input type="hidden" id="teamRoleVoidedUuid"></td>
				<td><input type="button" onclick="teamRoleVoidedSubmit()" id="editTeamRoleVoidedSubmit" name="editTeamRoleVoidedSubmit" value="update"> </td>
			
		</tr>
	</table>
	</form>
</div> 
<form id="teamRoleForm" name="teamRoleForm" action="/openmrs/module/teammodule/teamMember.form" method="POST">
	<input type="hidden" id="role" name="role">
</form> 

<%@ include file="/WEB-INF/template/footer.jsp"%>