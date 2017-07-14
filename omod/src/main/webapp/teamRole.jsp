<%@ include file="/WEB-INF/template/include.jsp"%>
<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	#memberDialog>table, #memberDialog>table th, #memberDialog>table td {
		border: 1px solid black;
		border-collapse: collapse;
	}
	
	#history, #teamRoleDetail {
	    /* border: 1px solid black; */
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #teamRoleDetail th, #teamRoleDetail td {
	    /* border: 1px solid black; */
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
</style>

<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-ui.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	var headerArrayLength = 9;
	var teamRoles = [];
	$(document).ready(function() {
		$('#historyDialog').hide();
		$('#memberDialog').hide();
		var tbody,table,teamDataVar;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
		tbody.id= "tbody";
		var  data = $.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(teamRoleData) {
			teamRoles = teamRoleData.results; 
			GenerateTable(tbody);
			table.appendChild(tbody);
			$('#general').DataTable({
				"language" : {
					"search" : "_INPUT_",
					"searchPlaceholder" : "Search..."
				},
				"columnDefs": [ 
                       { "width": "1%", "targets": [ 0 ] },
                       { "width": "15%", "targets": [ 1, 2, 3, 4, 5] },
                       { "width": "7%", "targets": [ 6 ] },
                       { "width": "11%", "targets": [ 7, 8 ] }
                ],
				"paging" : true,
				"lengthChange" : false,
				"searching" : false,
				"ordering" : false,
				"info" : false,
				"autoWidth" : true,
				"sDom" : 'lfrtip',
			});
		});
	});

	function GenerateTable(tbody) {
    	if(teamRoles.length > 0) {
			for(var i=0;i<teamRoles.length;i++) {
				row = tbody.insertRow(-1); row.setAttribute("role", "row"); 
		    	if(i%2 === 0) { row.setAttribute("class", "odd"); } else { row.setAttribute("class", "even"); }
				/* Edit */
				var cell = row.insertCell(-1);
		        cell.innerHTML = "<a id='editTeamRoleLink' name='editTeamRoleLink' title='Edit Team Role' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: left;' ></a>";
				/* Identifier */ 
				var cell = row.insertCell(-1);
				cell.innerHTML = "<a id='editTeamRoleIdentifierLink' name='editTeamRoleIdentifierLink' title='Team Role Detail' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleDetail"+"\");'>"+teamRoles[i].identifier+"</a>";
				/* Name */
				var cell = row.insertCell(-1);
				cell.innerHTML = teamRoles[i].name;
				/* Owns Team */
				var cell = row.insertCell(-1);
				cell.innerHTML = teamRoles[i].ownsTeam;
				/* Report To */
				var cell = row.insertCell(-1);
				if(teamRoles[i].reportTo === null) { cell.innerHTML = "<a id='editTeamRoleReportToLink' name='editTeamRoleReportToLink' title='Edit Team Role Report To' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleReportToInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; } 
				else { cell.innerHTML = teamRoles[i].reportTo.name + "<a id='editTeamRoleReportToLink' name='editTeamRoleReportToLink' title='Edit Team Role Report To' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleReportToInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
				/* Report By */
				var cell = row.insertCell(-1);
				if(teamRoles[i].reportByName !== null) { 
					var reportBy = ""; 
					for(var j=0; j<teamRoles[i].reportByName.length; j++) { 
						if(j===teamRoles[i].reportByName.length-1) { reportBy += "<li>" + teamRoles[i].reportByName[j] + "</li>"; } 
						else { reportBy += "<li>" + teamRoles[i].reportByName[j] + "</li>"; } 
					} 
					if(teamRoles[i].reportByName.length > 0) { cell.innerHTML = reportBy; } 
					else { cell.innerHTML = reportBy; } 
				}
				else { cell.innerHTML = ""; }
				/* Voided */
				var cell = row.insertCell(-1);
				cell.innerHTML = teamRoles[i].voided + "<a id='editTeamRoleVoidedLink' name='editTeamRoleVoidedLink' title='Void Team Role' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleVoidedInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";;
				/* Total Members */
		        var cell = row.insertCell(-1);
		        if(teamRoles[i].members === 0) { cell.innerHTML = ""; } else { cell.innerHTML = "<a id='teamRoleMemberLink' name='teamRoleMemberLink' title='Team Role Members' style='cursor:pointer' onclick='editTeamRole(\""+teamRoles[i].uuid+"\",\""+"roleMemberInfo"+"\");'>"+teamRoles[i].members+"</a> Members"; }
				/* History */
				var cell = row.insertCell(-1);
		        cell.innerHTML = "<a id='teamRoleHistoryLink' name='teamRoleHistoryLink' title='Team Role History' style='cursor:pointer' onClick='teamsHierarchyHistory(\""+teamRoles[i].uuid+"\")'><img src='/openmrs/moduleResources/teammodule/img/history.png' style=' width: 20px; height: 20px; padding-right: 10%; float: right;' ></a>";
			
    	    	if(teamRoles[i].voidReason === null) { teamRoles[i].voidReason = ""; }
			}
    	}
    	else {
	    	row = tbody.insertRow(-1);
	        var cell = row.insertCell(-1);
	    	cell.colSpan = headerArrayLength;
    		cell.innerHTML = "<strong>No Records Found</strong>";
	    	cell.setAttribute("style", "border: 1px solid; text-align: center; background-color: #bbccf7");
    	}
	}
	function teamsHierarchyHistory(teamRoleId) {
		$.get("/openmrs/ws/rest/v1/team/teamrolelog?v=full&teamRole=" + teamRoleId, function(data) {
			var display = "", action = "", dataNew = "", dataOld = "", dateCreated = "";
			document.getElementById("historyBody").innerHTML = "";
			if(data.results.length > 0) {
				for (i = 0; i < data.results.length; i++) { 
					if(data.results[i].teamRole === null) { display = ""; } else { if(data.results[i].teamRole.display === null) { display = ""; } else { display = data.results[i].teamRole.display.toString(); } }
					if(data.results[i].auditInfo === null) { dateCreated = ""; } else { if(data.results[i].auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = data.results[i].auditInfo.dateCreated.toString().substr(0, 10); } }
					if(data.results[i].action === null) { action = ""; } else { action = data.results[i].action.toString(); }
					if(data.results[i].dataNew === null) { dataNew = ""; } else { dataNew = data.results[i].dataNew.toString(); }
					if(data.results[i].dataOld === null) { dataOld = ""; } else { dataOld = data.results[i].dataOld.toString(); }
					$("#history").append("<tr id=\"historyRow\">"+
						"<td style=\"text-align: left;\" valign=\"top\">" + display + "</td>"+
						"<td style=\"text-align: left;\" valign=\"top\">" + action + "</td>"+
						"<td style=\"text-align: left;\" valign=\"top\">" + dataNew + "</td>"+
						"<td style=\"text-align: left;\" valign=\"top\">" + dataOld + "</td>"+
						"<td style=\"text-align: left;\" valign=\"top\">" + dateCreated + "</td>"+
						"</tr>"
					); 
				}
			} else { 
				var cell = document.getElementById("historyBody").insertRow(-1).insertCell(-1); 
				cell.colSpan= 5; cell.innerHTML = "<strong>No Records Found</strong>"; 
				cell.setAttribute("style", "border: 1px solid; text-align: center;");
			}
		});
		$("#historyDialog").dialog({ width : "auto", height : "auto", title : "History", closeText : "", modal: true, open: onDialogOpen });
	}
	function replace(arrayName,replaceTo, replaceWith) { for(var i=0; i<arrayName.length;i++ ) { if(arrayName[i]==replaceTo) { arrayName.splice(i,1,replaceWith); } } }
	function remove(array, obj) { var temp = []; for(var i=0; i<array.length; i++) { if(array[i] === obj) { } else { temp.push(array[i]); } } return temp; }
	function editTeamRoleClose(index, type) { 
		if(type === "roleDetail") { $('#detailTeamRoleDiv').dialog('close'); }  
		else if(type === "roleInfo") { $('#editTeamRoleDiv').dialog('close'); }  
		else if(type === "roleReportToInfo") { $('#editTeamRoleReportToDiv').dialog('close'); }  
		else if(type === "roleVoidedInfo") { $('#editTeamRoleVoidedDiv').dialog('close'); }  
	}
	function editTeamRole(uuid, type) {
		for (var i = 0; i < teamRoles.length; i++) {
			if(teamRoles[i].uuid.toString() === uuid.toString()) {
				if(type === "roleMemberInfo") {
					document.getElementById("role").value = teamRoles[i].uuid.toString();
					document.getElementById("teamRoleForm").submit();
				}
				else if(type === "roleDetail") {
					var reportTo = "", reportBy = "", dateCreated = "", dateChanged = ""; 
					if(teamRoles[i].reportTo === null) { reportTo = ""; } else { reportTo = teamRoles[i].reportTo.name; } 
					if(teamRoles[i].reportByName !== null) { for(var j=0; j<teamRoles[i].reportByName.length; j++) { if(j===teamRoles[i].reportByName.length-1) { reportBy += teamRoles[i].reportByName[j]; } else { reportBy += teamRoles[i].reportByName[j] + ", "; } } } else { reportBy = ""; }
					if(teamRoles[i].auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(teamRoles[i].auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = teamRoles[i].auditInfo.dateCreated.toString().substr(0, 10); } if(teamRoles[i].auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = teamRoles[i].auditInfo.dateChanged.toString().substr(0, 10); } }
					document.getElementById("detailTeamRoleDiv").innerHTML = "<table id='teamRoleDetail'><tr><td>Identifier: </td><td>"+teamRoles[i].identifier+"</td></tr><tr><td>Name: </td><td>"+teamRoles[i].name+"</td></tr><tr><td>Owns Team: </td><td>"+teamRoles[i].ownsTeam.toString()+"</td></tr><tr><td>Report To: </td><td>"+reportTo+"</td></tr><tr><td>Report By: </td><td>"+reportBy+"</td></tr><tr><td>Members: </td><td>"+teamRoles[i].members+"</td></tr><tr><td>Voided: </td><td>"+teamRoles[i].voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
		    		$("#detailTeamRoleDiv").dialog({ width: "500px", height: "auto", title: "Team Role Detail" , closeText: "", modal: true, open: onDialogOpen });
				}
				else if(type === "roleInfo") {
					var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Name: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamRoleName"+i+"' name='teamRoleName"+i+"' value='"+teamRoles[i].name+"'></td></tr><td style=' font-size: 18px; '>Identifier: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamRoleIdentifier"+i+"' name='teamRoleIdentifier"+i+"' value='"+teamRoles[i].identifier+"'></td></tr><tr><td style=' font-size: 18px; '>Owns Team: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamRoleOwnsTeam"+i+"' name='teamRoleOwnsTeam"+i+"' title='Owns Team'>";
					if(teamRoles[i].ownsTeam) { html += "<option value='true' selected>True</option><option value='false' >False</option>"; }
					else { html += "<option value='true' >True</option><option value='false' selected>False</option>"; }
					html += "</select></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamRoleEditClose' name='teamRoleEditClose' onclick='editTeamRoleClose(\""+i+"\",\""+"roleInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamRoleEditSuccess' name='teamRoleEditSuccess' onclick='editTeamRoleSuccess(\""+i+"\",\""+"roleInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamRoleDiv").innerHTML = html;
					$("#editTeamRoleDiv").dialog({ width: "500px", height: "auto", title: "Team Role - Edit" , closeText: "", modal: true, open: onDialogOpen });
				}
				else if(type === "roleReportToInfo") {
					var allRoleNames = []; var allRoleIds = []; var myRoleNames = [];
					<c:forEach items="${allRoles}" var="role">allRoleIds.push("${role.uuid}");allRoleNames.push("${role.name}");</c:forEach>
					var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Reports To: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamRoleReportTo"+i+"' name='teamRoleReportTo"+i+"' title='Reports To'>";
					if(teamRoles[i].reportTo === null) { html += "<option value='' selected></option>"; 
						for (var j = 0; j < allRoleNames.length; j++) { if(allRoleNames[j] === teamRoles[i].name) {} else { html += "<option value='"+allRoleIds[j]+"'>"+allRoleNames[j]+"</option>"; } }
					}
					else { html += "<option value=''></option>";
						for (var j = 0; j < allRoleNames.length; j++) {
							if(teamRoles[i].reportTo.name === allRoleNames[j]) { if(allRoleNames[j] === teamRoles[i].name) {} else { html += "<option value='"+allRoleIds[j]+"' selected>"+allRoleNames[j]+"</option>"; } }
							else { if(allRoleNames[j] === teamRoles[i].name) {} else { html += "<option value='"+allRoleIds[j]+"'>"+allRoleNames[j]+"</option>"; } }
						}
					} html += "</select></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamRoleEditClose' name='teamRoleEditClose' onclick='editTeamRoleClose(\""+i+"\",\""+"roleReportToInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamRoleEditSuccess' name='teamRoleEditSuccess' onclick='editTeamRoleSuccess(\""+i+"\",\""+"roleReportToInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamRoleReportToDiv").innerHTML = html;
					$("#editTeamRoleReportToDiv").dialog({ width: "500px", height: "auto", title: "Team Role - Edit" , closeText: "", modal: true, open: onDialogOpen });
				}
				else if(type === "roleVoidedInfo") {
	    			var html = "<h3 id='voidError' name='voidError' style='color: red; display: inline'></h3><form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Voided: </td><td><select id='teamRoleVoided"+i+"' name='teamRoleVoided"+i+"' style=' font-size: 14px; padding: 5px; width: 100%; '>";
	    			if(teamRoles[i].voided == true) { html += "<option value='"+teamRoles[i].voided+"' selected >"+teamRoles[i].voided+"</option><option value='false' >false</option>"; }
	    			else if(teamRoles[i].voided == false) { html += "<option value='true' >true</option><option value='"+teamRoles[i].voided+"' selected >"+teamRoles[i].voided+"</option>"; }
	    			html += "</select></td></tr><tr><td style=' font-size: 18px; '>Void Reason: </td><td><textarea style=' width: 95%; font-size: 14px; padding: 5px; ' id='teamRoleVoidReason"+i+"' name='teamRoleVoidReason"+i+"' value='"+teamRoles[i].voidReason+"'></textarea></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamRoleVoidedEditClose' name='teamRoleVoidedEditClose' onclick='editTeamRoleClose(\""+i+"\",\""+"roleVoidedInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamRoleVoidedEditSuccess' name='teamRoleVoidedEditSuccess' onclick='editTeamRoleSuccess(\""+i+"\",\""+"roleVoidedInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";	    			
	    			document.getElementById("editTeamRoleVoidedDiv").innerHTML = html;
		    		$("#editTeamRoleVoidedDiv").dialog({ width: "500px", height: "auto", title: "Team Role - Void" , closeText: "", modal: true, open: onDialogOpen });
	    		}
			}
		}
	}
	function editTeamRoleSuccess(index, type) {
		if(type === "roleInfo") {
			var uuid = teamRoles[index].uuid;
			var name = document.getElementById("teamRoleName"+index).value;
			var identifier = document.getElementById("teamRoleIdentifier"+index).value;
	    	var ownsTeam = document.getElementById("teamRoleOwnsTeam"+index).value;
	    	var url = "/openmrs/ws/rest/v1/team/teamrole/"+uuid;
			var data = '{ "name" : "' + name + '", "identifier" : "' + identifier + '", "ownsTeam" : "' + ownsTeam + '" }';
			$.ajax({
				url : url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { var teamRole = result;
					for (var i = 0; i < teamRoles.length; i++) {
				    	if(teamRoles[i].uuid.toString() === teamRole.uuid.toString()) { 
							if(teamRole.name.toString() === teamRoles[i].name.toString()) {} else { saveLog("teamRole", teamRoles[i].uuid.toString(), teamRole.name.toString(), teamRoles[i].name.toString(), "TEAM_ROLE_EDITED", ""); } 
							if(teamRole.identifier.toString() === teamRoles[i].identifier.toString()) {} else { saveLog("teamRole", teamRoles[i].uuid.toString(), teamRole.identifier.toString(), teamRoles[i].identifier.toString(), "TEAM_ROLE_EDITED", ""); } 
							if(teamRole.ownsTeam.toString() === teamRoles[i].ownsTeam.toString()) {} else { saveLog("teamRole", teamRoles[i].uuid.toString(), teamRole.ownsTeam.toString(), teamRoles[i].ownsTeam.toString(), "TEAM_ROLE_EDITED", ""); } 
				    		teamRoles[i].display = teamRole.display; 
				    		teamRoles[i].name = teamRole.name; 
				    		teamRoles[i].identifier = teamRole.identifier; 
				    		teamRoles[i].ownsTeam = teamRole.ownsTeam; 
			    		}
					} var tbody = document.getElementById("tbody");
					tbody.innerHTML = ""; GenerateTable(tbody);
					document.getElementById("errorHead").innerHTML = ""; 
					document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>"; 
					$('#editTeamRoleDiv').dialog('close'); 
				}
			});
		}
		else if(type === "roleReportToInfo") {
			var uuid = teamRoles[index].uuid;
			var reportByName = "";
			if(teamRoles[index].reportTo !== null) { reportByName = teamRoles[index].reportTo.name; }
	    	var reportTo = document.getElementById("teamRoleReportTo"+index).value;
			var select = document.getElementById("teamRoleReportTo"+index);
			var reportBy = "";
	    	for(var i = 0; i < select.options.length; i++) { if(select.options[i].selected) { reportBy = select.options[i].text; } }
	    	var url = "/openmrs/ws/rest/v1/team/teamrole/"+uuid;
			var data = '{ "reportTo" : "' + reportTo + '" }';
			$.ajax({
				url : url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { var teamRole = result;
					for (var i = 0; i < teamRoles.length; i++) {
				    	if(teamRoles[i].uuid.toString() === teamRole.uuid.toString()) { 
				    		if(teamRoles[i].reportTo !== null && teamRole.reportTo !== null) { if(teamRole.reportTo.name.toString() === teamRoles[i].reportTo.name.toString()) {} else { saveLog("teamRole", teamRoles[i].uuid.toString(), teamRole.reportTo.name.toString(), teamRoles[i].reportTo.name.toString(), "TEAM_ROLE_EDITED", ""); } } else { var newData = ""; var oldData = ""; if(teamRoles[i].reportTo === null) { newData = teamRole.reportTo.name.toString(); oldData = ""; } if(teamRole.reportTo === null) { newData = ""; oldData = teamRoles[i].reportTo.name.toString(); } saveLog("teamRole", teamRoles[i].uuid.toString(), newData, oldData, "TEAM_ROLE_EDITED", ""); }
				    		teamRoles[i].reportTo = teamRole.reportTo; 
			    		}
				    	if(teamRoles[i].name === reportBy) { (teamRoles[i].reportByName).push(teamRole.name); }
				    	if(teamRoles[i].name === reportByName) { teamRoles[i].reportByName = remove(teamRoles[i].reportByName, teamRole.name); }
					} var tbody = document.getElementById("tbody");
					tbody.innerHTML = ""; GenerateTable(tbody);
					document.getElementById("errorHead").innerHTML = ""; 
					document.getElementById("saveHead").innerHTML = "<p>Team Role Updated Successfully</p>"; 
					$('#editTeamRoleReportToDiv').dialog('close'); 
				}
			});
		}
		else if(type === "roleVoidedInfo") {
			var uuid = teamRoles[index].uuid;
			var voided = document.getElementById("teamRoleVoided"+index).value;
	    	var voidReason = document.getElementById("teamRoleVoidReason"+index).value;
			if(voidReason.length > 255) { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else if(voided === "true" && voidReason === "") { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else {
				var url = "/openmrs/ws/rest/v1/team/teamrole/"+uuid;
				var data = '{ '; 
				if(voided != "false") { data += '"voided":"' + voided + '", "voidReason":"' + voidReason + '"'; } else { data += '"voided":"' + voided + '"'; } 
				data += ' }';
				document.getElementById("voidError").innerHTML = "";
				$.ajax({
					url: url,
					data : data,
				 	type: "POST",
	     			contentType: "application/json",
					success : function(result) { var teamRole = result;
						for (var i = 0; i < teamRoles.length; i++) {
					    	if(teamRoles[i].uuid.toString() === teamRole.uuid.toString()) {
					    		if(teamRole.voidReason === null) { teamRole.voidReason = ""; }
								if(teamRole.voided.toString()+"-"+teamRole.voidReason.toString() === teamRoles[i].voided.toString()+"-"+teamRoles[i].voidReason.toString()) {} else { saveLog("teamRole", teamRoles[i].uuid.toString(), teamRole.voided.toString()+"-"+teamRole.voidReason.toString(), teamRoles[i].voided.toString()+"-"+teamRoles[i].voidReason.toString(), "TEAM_ROLE_VOIDED", ""); }
					    		teamRoles[i].voided = teamRole.voided;
					    		teamRoles[i].voidReason = teamRole.voidReason;
					    		var tbody = document.getElementById("tbody");
								tbody.innerHTML = ""; GenerateTable(tbody);
								document.getElementById("errorHead").innerHTML = ""; 
								document.getElementById("saveHead").innerHTML = "<p>Team Role Voided Successfully</p>";  
								$('#editTeamRoleVoidedDiv').dialog('close'); 
							}
						}
					}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER VOIDED"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Voiding Team Role</p>"; }
				});
			}
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
				success : function(result) {
					console.log("SUCCESS-SAVE "+type.toUpperCase()+" LOG"); 
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-SAVE "+type.toUpperCase()+" LOG"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = ""; }
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
	function onDialogOpen( event, ui ) {
		var dialogHeads = document.getElementsByClassName("ui-dialog-title");  for (var loop = 0; loop < dialogHeads.length; loop++) {  dialogHeads[loop].setAttribute("style", "font-size: 18px;"); } 
		var dialogCloseBtns = document.getElementsByClassName("ui-button-icon ui-icon ui-icon-closethick"); for (var loop = 0; loop < dialogCloseBtns.length; loop++) {  dialogCloseBtns[loop].setAttribute("style", "top: 0; left: 0; right: 0; bottom: 0;"); } 
	}
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberView.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Team Roles</h2>

<a href="/openmrs/module/teammodule/addRole.form" style="float: right;" title="Add Team Member"><img src="/openmrs/moduleResources/teammodule/img/plus.png" style=" width: 50px; width: 50px;position: relative; top: -10px; right: 10px; " ></a>

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

<div id="memberDialog">
	<table id="member">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Join Date</th>
			<th>Gender</th>
			<th>Patients</th>
			<th>Detail</th>
		</tr>
	</table>
</div>

<div id="detailTeamRoleDiv"></div>
<div id="editTeamRoleDiv"></div>
<div id="editTeamRoleReportToDiv"></div>
<div id="editTeamRoleVoidedDiv"></div>

<form id="teamRoleForm" name="teamRoleForm" action="/openmrs/module/teammodule/teamMemberView.form" method="POST">
	<input type="hidden" id="role" name="role">
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>