<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	
	#history, #teamDetail, #teamSupervisorDetail {
	    /* border: 1px solid black; */
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #teamDetail th, #teamDetail td, #supervisorTeamDetail th, #supervisorTeamDetail td {
	    /* border: 1px solid black; */
	    text-align: left;
	    font-size: 14px;
	    padding: 10px;
	}
	#history tr:hover, #teamDetail tr:hover, #supervisorTeamDetail tr:hover {
		background-color:#f5f5f5
	}
	#history tr:nth-child(odd), #teamDetail tr:nth-child(odd), #supervisorTeamDetail tr:nth-child(odd) {
		background-color:#f5f5f5
	}
	#images
	{
	width: 20px;
	height: 20px;
	float: right;
	cursor:pointer;
	}
	#link
	{
	cursor:pointer;
	}
	
</style>

<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-ui.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	
function CreateTable()
{
		var tbody=null,table=null,dataTable=null;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
		tbody.id= "tbody";
		$.get("/openmrs/ws/rest/v1/team/team?v=full", function(data) {
			teams = data.results; 
			
				teamMembers = data.results; 
				table.appendChild(tbody);
				dataTable=$('#general').DataTable({
				"data": teams,
				"language" : {
					"search" : "_INPUT_",
					"searchPlaceholder" : "Search..."
				},
				"columns": [
		            { 
		            	"data": "uuid",
		              	"render": 
		              		function ( data, type, row, meta) {
								return "<a title='Edit Team' onclick='editTeamInfo(\""+ data + "\");' >"
								+ "<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png' style='padding-right: 40%;'></a>";
						}
					},
					{
						 "data" : "teamIdentifier",
						 "render" : 
							 function(data,type,row,meta) {
								return "<a title='Team Detail' id='link' " 
								+ "onclick='teamDetail(\""+ row.uuid+ "\");'>"+ data + "</a>";
						}
					},
					{
					 	"data" : "teamName"
					},
					{
						 "data" : "supervisor",
						 "render" : 
							 function(data,type,row,meta) {
				 				if (data === null || data === "") {
									return "<a title='Edit Team Supervisor' onclick='editTeamSupervisorInfo(\"" + row.uuid + "\",\"" + row.supervisorTeamUuid + "\");' >"
									+"<img  id='images' src='/openmrs/moduleResources/teammodule/img/edit.png' ></a>";
								} else {
									return data.display + "<br/>[" + row.supervisorIdentifier + "]"
									+ "<a title='Edit Team Supervisor' onclick='editTeamSupervisorInfo(\"" + row.uuid+ "\",\"" + row.supervisorTeamUuid + "\");' >"
									+"<img  id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
								}
			 				}
					},
					{
						 "data" : "supervisorTeam",
						 "render" : 
							 function(data,type,row,meta) {
							 	if (data === null || data === "") {
									return "-";
								} else {
									return "<a title='Team Detail'  id='link' onclick='SupervisorTeamDetails(\"" + row.supervisorTeamUuid + "\");'>" + data + "</a>";
								}
						 	}
					},
					{
						"data" : "location",
						"render" :  
							function(data,type,row,meta) {
							 	return data.name
								+ "<a title='Edit Team Location'onclick='editTeamLocationInfo(\"" + row.uuid + "\",\"" + data.uuid + "\");' >"
								+"<img  id='images' src='/openmrs/moduleResources/teammodule/img/edit.png' ></a>";
						 	}
					},
					{
						"data" : "voided",
						"render" : 
							function(data,type,row,meta) {
								return data + "<a title='Void Team' onclick='editTeamVoidedInfo(\""+ row.uuid + "\");' >"
								+"<img  id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
							}
					},
					{
						"data" : "members",
						"render" : 
							function(data,type,row,meta) {
								if(data)
									return "<a title='Team Members'  id='link' onclick='teamMember(\"" + row.uuid + "\");'>" + data + " Members</a>"
								else
									return "-";
							}
					},
					{
						"data" : "uuid",
						"render" : 
							function(data,type,row,meta) {
								return "<a title='Team History' onClick='teamHistory(\"" + data + "\")'>"
								+"<img  id='images' src='/openmrs/moduleResources/teammodule/img/history.png'></a>"
							}
					} ],
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
	
  	$(document).ready(function(){
  		$('#historyDialog').hide();
		$('#memberDialog').hide();
		$('#allMemberDialog').hide();
		$('#SupervisorTeamDetailDiv').hide();
		$('#editTeamDiv').hide();
		$('#TeamDetailDiv').hide();
		$('#editTeamLocationDiv').hide();
		$('#editTeamVoidedDiv').hide();
		$('#editTeamSupervisorDiv').hide();
		
		CreateTable();
	});
  	

	function teamHistory(teamUuid) {
		$.get("/openmrs/ws/rest/v1/team/teamlog?v=full&q=" + teamUuid,function(data) {
			var name = "", action = "", dataNew = "", dataOld = "", dateCreated = "";
			document.getElementById("historyBody").innerHTML = "";
			if (data.results.length > 0) {
				for (i = 0; i < data.results.length; i++) {
					if (data.results[i].team === null) {
						name = "";
					} else {
						name = data.results[i].team.teamName;
					}
					if (data.results[i].auditInfo === null) {
						dateCreated = "";
					} else {
							dateCreated = data.results[i].auditInfo.dateCreated.toString().substr(0, 10);
					}
					if (data.results[i].action === null) {
						action = "";
					} else {
						action = data.results[i].action.toString();
					}
					if (data.results[i].dataNew === null) {
						dataNew = "";
					} else {
						dataNew = data.results[i].dataNew.toString();
					}
					if (data.results[i].dataOld === null) {
						dataOld = "";
					} else {
						dataOld = data.results[i].dataOld.toString();
					}
					$("#history").append("<tr id=\"historyRow\">"
										+ "<td style=\"text-align: left;\" valign=\"top\">" + name + "</td>"
										+ "<td style=\"text-align: left;\" valign=\"top\">" + action + "</td>"
										+ "<td style=\"text-align: left;\" valign=\"top\">" + dataNew + "</td>"
										+ "<td style=\"text-align: left;\" valign=\"top\">" + dataOld + "</td>"
										+ "<td style=\"text-align: left;\" valign=\"top\">" + dateCreated + "</td>" + "</tr>");
				}
			} else {
				var cell = document.getElementById("historyBody").insertRow(-1).insertCell(-1);
				cell.colSpan = 5;
				cell.innerHTML = "<strong>No Records Found</strong>";
				cell.setAttribute("style", "border: 1px solid; text-align: center;");
			}
		});
		$("#historyDialog").dialog({
			width : "auto",
			height : "400",
			title : "Team - History",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function teamDetail(uuid) {
		$.get("/openmrs/ws/rest/v1/team/team/"+uuid+"?v=full",function(team) {
			var supervisor = "", reportBy = "", dateCreated = "", dateChanged = "";
			
					if (team.supervisor === null) {
						supervisor = "";
					} else {
						supervisor = team.supervisor.display;
					}
	
					if (team.auditInfo != null) {
	
						if (team.auditInfo.dateCreated === null)
							dateCreated = "";
						else
							dateCreated = team.auditInfo.dateCreated.toString().substr(0, 10);
						if (team.auditInfo.dateChanged === null)
							dateChanged = "";
						else
							dateChanged = team.auditInfo.dateChanged.toString().substr(0, 10);
					}
					$('#teamDetail tr td').children('snap').remove();
					$("#teamDetailIdentifier").append("<snap>" + team.teamIdentifier + "</snap>");
					$("#teamDetailName").append("<snap>" + team.teamName + "</snap>");
					$("#teamDetailCurrentSupervisor").append("<snap>" + supervisor + "</snap>");
					$("#teamDetailSupervisorTeam").append("<snap>" + team.supervisorTeam + "</snap>");
					$("#teamDetailLocation").append("<snap>" + team.location.name + "</snap>");
					$("#teamDetailNumberOfMember").append("<snap>" + team.members + "</snap>");
					$("#teamDetailVoided").append("<snap>" + team.voided + "</snap>");
					$("#teamDetailVoidReason").append("<snap>" + team.voidReason + "</snap>");
					$("#teamDetailDateCreated").append("<snap>" + dateCreated + "</snap>");
					$("#teamDetailDateChanged").append("<snap>" + dateChanged + "</snap>");
	
			$("#TeamDetailDiv").dialog({
				width : "500px",
				height : "auto",
				title : "Team Detail",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}

	function SupervisorTeamDetails(uuid) {
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/team/" + uuid + "?v=full",
			success : function(supervisorTeam) {
				var dateCreated = "", dateChanged = "";

				if (supervisorTeam.auditInfo === null) {
					dateCreated = "";
					dateChanged = "";
				} else {
					if (supervisorTeam.auditInfo.dateCreated === null) {
						dateCreated = "";
					} else {
						dateCreated = supervisorTeam.auditInfo.dateCreated
								.toString().substr(0, 10);
					}
					if (supervisorTeam.auditInfo.dateChanged === null) {
						dateChanged = "";
					} else {
						dateChanged = supervisorTeam.auditInfo.dateChanged
								.toString().substr(0, 10);
					}
				}

				$('#supervisorTeamDetail tr td').children('snap').remove();
				$("#supervisorTeamDetailIdentifier").append("<snap>" + supervisorTeam.teamIdentifier + "</snap>");
				$("#supervisorTeamDetailName").append("<snap>" + supervisorTeam.teamName + "</snap>");
				$("#supervisorTeamDetailMember").append("<snap>" + supervisorTeam.members + "</snap>");
				$("#supervisorTeamDetailLocation").append("<snap>" + supervisorTeam.location.name + "</snap>");
				$("#supervisorTeamDetailVoided").append("<snap>" + supervisorTeam.voided + "</snap>");
				$("#supervisorTeamDetailVoidReason").append("<snap>" + supervisorTeam.voidReason + "</snap>");
				$("#supervisorTeamDetailDateCreated").append("<snap>" + dateCreated + "</snap>");
				$("#supervisorTeamDetailDateChanged").append("<snap>" + dateChanged + "</snap>");

				$("#SupervisorTeamDetailDiv").dialog({
					width : "auto",
					height : "auto",
					title : "Team Supervisor Detail",
					closeText : "",
					modal : true,
					open : onDialogOpen
				});
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-TEAM-DETAIL");
				console.log(jqXHR);
			}
		});
	}

	function teamMember(uuid) {
		$("#team").val(uuid);
		$("#teamForm").submit();
	}

	function editTeamVoidedInfo(uuid) {
		$.get("/openmrs/ws/rest/v1/team/team/" + uuid, function(team) {
			$("#editTeamVoided").val(team.voided.toString());
			$("#editTeamVoidReason").val(team.voidReason);
		});
		$("#editTeamVoidedUuid").val(uuid);
		console.log(uuid + ", " + $("#editTeamVoidedUuid").val());
		$("#editTeamVoidedDiv").dialog({
			width : "500px",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function editTeamSupervisorInfo(uuid, SupervisorUuid) {
		$.get("/openmrs/ws/rest/v1/team/teammember/?v=full", function(data) {
			teamMembers = data.results
			$('#editTeamSupervisor').children('option').remove();
			for (var i = 0; i < teamMembers.length; i++) {
				$("#editTeamSupervisor").append("<option value='"+teamMembers[i].uuid+"'>" + teamMembers[i].display + "</option>");
			}
			$("#editTeamSupervisor").val(SupervisorUuid);
			$("#editTeamSupervisorUuid").val(uuid);
			console.log(uuid + ", " + $("#editTeamSupervisorUuid").val());

		});

		$("#editTeamSupervisorDiv").dialog({
			width : "auto",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function editTeamLocationInfo(uuid, LocationUuid) {
		$.get("/openmrs/ws/rest/v1/location?v=full", function(data) {
			locations = data.results
			$('#editTeamLocation').children('option').remove();
			for (var i = 0; i < locations.length; i++) {
				$("#editTeamLocation").append("<option value='"+locations[i].uuid+"'>"+ locations[i].name + "</option>");
			}
			$("#editTeamLocation").val(LocationUuid);
			$("#editTeamLocationUuid").val(uuid);
			console.log(uuid + ", " + $("#editTeamLocationUuid").val());

		});

		$("#editTeamLocationDiv").dialog({
			width : "auto",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function editTeamInfo(uuid) {
		$.get("/openmrs/ws/rest/v1/team/team/" + uuid, function(team) {
			$("#editTeamName").val(team.teamName);
			$("#editTeamIdentifier").val(team.teamIdentifier);
		});
		$("#editTeamUuid").val(uuid);

		$("#editTeamDiv").dialog({
			width : "500px",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function teamInfoSubmit() {
		var name = $("#editTeamName").val();
		var identifier = $("#editTeamIdentifier").val();
		var uuid = document.getElementById("editTeamUuid").value;
		var data = {};
		data.teamName=name;
		data.teamIdentifier=identifier;
		data = JSON.stringify(data);
		//var data = '{ "teamName" : "' + name + '", "teamIdentifier" :"' + identifier + '" }';
		console.log(data);
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/team/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var team = result;
				saveLog("team", team.uuid, team.teamName,team.teamName, "TEAM_EDITED", "Name: " + name + ", Identifier: " + identifier);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>";
				$("#editTeamDiv").dialog('close');
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
				setTimeout(function() {
					document.getElementById("errorHead").innerHTML = ""
				}, 5000);
			}
		});
	}

	function teamSupervisorInfoSubmit() {
		var supervisor = document.getElementById("editTeamSupervisor").value;
		var uuid = document.getElementById("editTeamSupervisorUuid").value;
		console.log(supervisor + "," + uuid);
		var data = {};
		data.supervisor=supervisor;
		data = JSON.stringify(data);
		
		//var data = '{ "supervisor" : "' + supervisor + '" }';

		$.ajax({
			url : "/openmrs/ws/rest/v1/team/team/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var team = result;
				saveLog("team", team.uuid, team.teamName, team.teamName, "TEAM_SUPERVISOR_EDITED", "supervisor Changed to " + supervisor);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Supervisor Updated Successfully</p>";
				$("#editTeamSupervisorDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable();
				setTimeout(function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM SUPERVISOR");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>";
			}
		});
	}
	function teamLocationInfoSubmit() {
		var location = document.getElementById("editTeamLocation").value;
		var uuid = document.getElementById("editTeamLocationUuid").value;
		var data = {};
		data.location=location;
		data = JSON.stringify(data);
		//var data = '{ "location" : "' + location + '" }';
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/team/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var team = result;
				saveLog("team", team.uuid, team.teamName,
						team.teamName, "TEAM_LOCATION_EDITED",
						"Team Location Changed to " + location);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Location Updated Successfully</p>";
				$("#editTeamLocationDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable();
				setTimeout(function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM SUPERVISOR");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>";
			}
		});
	}

	function teamVoidedInfoSubmit() {
		var voided = document.getElementById("editTeamVoided").value;
		var voidReason = document.getElementById("editTeamVoidReason").value;
		var uuid = document.getElementById("editTeamVoidedUuid").value;
		var data={};
		if (voidReason.length > 255) {
			document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long";
		} else if (voided === "true" && voidReason === "") {
			document.getElementById("voidError").innerHTML = "Void Reason Can't be Empty";
		} else {
			if (voided != "false") {
				data.voided=voided;
				data.voidReason=voidReason;
			} else {
				data.voided=voided;
			}
			data = JSON.stringify(data);
			$.ajax({
				url : "/openmrs/ws/rest/v1/team/team/" + uuid,
				data : data,
				type : "POST",
				contentType : "application/json",
				success : function(result) {
					var team = result;
					console.log(team);
					saveLog("team", team.uuid, team.teamName, team.teamName, "TEAM_VOIDED_STATUS_EDITED", "Team Location Changed to " + location);
					document.getElementById("errorHead").innerHTML = "";
					document.getElementById("saveHead").innerHTML = "<p>Team Location Updated Successfully</p>";
					$("#editTeamVoidedDiv").dialog('close');
					$("#general").dataTable().fnDestroy();
					CreateTable();
					setTimeout(
							function() {
								document.getElementById("saveHead").innerHTML = ""
							}, 5000);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("ERROR-EDIT TEAM SUPERVISOR");
					console.log(jqXHR);
					document.getElementById("saveHead").innerHTML = "";
					document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>";
				}
			});
		}
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
					console.log("SUCCESS-SAVE " + type.toUpperCase()
							+ " LOG");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("ERROR-SAVE " + type.toUpperCase()
							+ " LOG");
					console.log(jqXHR);
					document.getElementById("saveHead").innerHTML = "";
					document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>";
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
	function voidReasonView(id) {
		if (document.getElementById("teamVoided" + id).value === "true") {
			document.getElementById("teamVoidReason" + id).disabled = false;
		} else {
			document.getElementById("teamVoidReason" + id).disabled = true;
		}
	}
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberView.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Teams</h2>

<a href="/openmrs/module/teammodule/addTeam.form" style="float: right;" title="Add Team"><img src="/openmrs/moduleResources/teammodule/img/plus.png" style=" width: 50px; width: 50px;position: relative; top: -10px; right: 10px; " ></a>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" align="center" style="color: green">${saved}</h3>
<h3 id="editHead" align="center" style="color: green;">${edit}</h3>

<table class="display" id ="general" style="border-left: 1px solid black; border-right: 1px solid black;">
	<thead>
		<tr>
			<openmrs:hasPrivilege privilege="Edit Team">
				<th style="border-top: 1px solid black;">Edit</th>
			</openmrs:hasPrivilege>
			<th style="border-top: 1px solid black;">Identifier</th>
			<th style="border-top: 1px solid black;">Name</th>
			<th style="border-top: 1px solid black;">Current Supervisor</th>
			<th style="border-top: 1px solid black;">Supervisor Team</th>
			<th style="border-top: 1px solid black;">Location</th>
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

<div id="TeamDetailDiv">
	<table id='teamDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="teamDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="teamDetailName"></td>
		</tr>
		<tr>
			<td>Current Supervisor:</td>
			<td id="teamDetailCurrentSupervisor"></td>
		</tr>
		<tr>
			<td>Supervisor Team:</td>
			<td id="teamDetailSupervisorTeam"></td>
		</tr>
		<tr>
			<td>Location:</td>
			<td id="teamDetailLocation"></td>
		</tr>
		<tr>
			<td>Members:</td>
			<td id="teamDetailNumberOfMember"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="teamDetailVoided"></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td id="teamDetailVoidReason"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="teamDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="teamDetailDateChanged"></td>
		</tr>
	</table>
</div>
		
<div id="SupervisorTeamDetailDiv">
	<table id='supervisorTeamDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="supervisorTeamDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="supervisorTeamDetailName"></td>
		</tr>
		<tr>
			<td>Number Of Members:</td>
			<td id="supervisorTeamDetailMember"></td>
		</tr>
		<tr>
			<td>Location:</td>
			<td id="supervisorTeamDetailLocation"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="supervisorTeamDetailVoided"></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td id="supervisorTeamDetailVoidReason"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="supervisorTeamDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="supervisorTeamDetailDateChanged"></td>
		</tr>
	</table>
</div>
<div id="editTeamDiv">
	<h3 id='infoError' name='infoError' style='color: red; display: inline'></h3>
		<input type="hidden" id="editTeamUuid">
		<table style='width: 100%;'>
			<tr>
				<td style='font-size: 18px;'>Name:</td>
				<td><input style='width: 95%; font-size: 14px; padding: 5px;'
					type='text' id='editTeamName' name='editTeamName' maxlength='45'>
				</td>
			</tr>
			<tr>
				<td style='font-size: 18px;'>Identifier:</td>
				<td><input style='width: 95%; font-size: 14px; padding: 5px;'
					type='text' id='editTeamIdentifier' name='editTeamIdentifier' maxlength='45'>
				</td>
			</tr>
			<tr>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td>
				<button type='button' onclick='teamInfoSubmit();' style='float: right;'>Save</button>
				</td>
			</tr>
		</table>
</div>
<div id="editTeamSupervisorDiv">
	<input type="hidden" id="editTeamSupervisorUuid">
	<table style='width: 100%;'>
		<tr>
			<td style='font-size: 18px;'>Supervisor:</td>
			<td><select id='editTeamSupervisor' name='editTeamSupervisor' title='Team Supervisor'
				size='4' style='font-size: 14px; padding: 5px; width: 100%;'>
			</select></td>
		</tr>
			<tr>
				<td></td>
				<td>
				<button type='button' onclick='teamSupervisorInfoSubmit();' style='float: right;'>Save</button>
				</td>
			</tr>
	</table>
</div>
<div id="editTeamLocationDiv">
	<input type="hidden" id="editTeamLocationUuid">
	<table style='width: 100%;'>
		<tr>
			<td style='font-size: 18px;'>Team Location:</td>
			<td>
				<select id='editTeamLocation' name='editTeamLocation'title='Team Location' 
				size='4' style='font-size: 14px; padding: 5px; width: 100%;'>
				</select>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamLocationInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	
	</table>
</div>
<div id="editTeamVoidedDiv">
	<h3 id='voidError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamVoidedUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Voided:</td>
			<td><select id='editTeamVoided' name='editTeamVoided'>
					<option value="true">True</option>
					<option value="false">False</option>
			</select></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td><textarea id='editTeamVoidReason' name='editTeamVoidReason'
					style='width: 95%; font-size: 14px; padding: 5px;'>
				</textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamVoidedInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>

</div>

<form id="teamForm" name="teamForm" action="/openmrs/module/teammodule/teamMember.form" method="POST">
	<input type="hidden" id="team" name="team">
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>