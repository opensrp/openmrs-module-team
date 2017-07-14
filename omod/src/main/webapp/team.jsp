<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="View Team" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	/* #memberDialog> table, #memberDialog> table th, #memberDialog> table td {
		border: 1px solid black;
		border-collapse: collapse;
	}
	#allMemberDialog> table, #memberDialog> table th, #memberDialog> table td {
		border: 1px solid black;
		border-collapse: collapse;
	}
	#supervisorTeamDetailDialog> table, #historyDialog> table th, #historyDialog> table td {
		border: 1px solid black;
		border-collapse: collapse;
	} */
	#history, #teamDetail, #teamSupervisorDetail {
	    /* border: 1px solid black; */
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #teamDetail th, #teamDetail td, #teamSupervisorDetail th, #teamSupervisorDetail td {
	    /* border: 1px solid black; */
	    text-align: left;
	    font-size: 14px;
	    padding: 10px;
	}
	#history tr:hover, #teamDetail tr:hover, #teamSupervisorDetail tr:hover {
		background-color:#f5f5f5
	}
	#history tr:nth-child(odd), #teamDetail tr:nth-child(odd), #teamSupervisorDetail tr:nth-child(odd) {
		background-color:#f5f5f5
	}

</style>

<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-ui.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	var headerArrayLength = 9;
  	var allSupervisors = [];
  	$(document).ready(function(){
  		$('#historyDialog').hide();
		$('#memberDialog').hide();
		$('#allMemberDialog').hide();
		$('#supervisorTeamDetailDialog').hide();
		var tbody,table;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
		tbody.id= "tbody";
		$.get("/openmrs/ws/rest/v1/team/team?v=full",function(data) { teams = data.results;
			$.get("/openmrs/ws/rest/v1/team/teammember?get=all&v=full",function(data) { 
				allSupervisors = data.results;
				GenerateTable(tbody);
				table.appendChild(tbody);
				$('#general').DataTable({
					"language": {
	 			        "search": "_INPUT_",
	 			        "searchPlaceholder": "Search"
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
		
	});
  
	function GenerateTable(tbody) {
    	if(teams.length > 0) {
			for (var i = 0; i < teams.length; i++) {
		    	row = tbody.insertRow(-1); row.setAttribute("role", "row"); 
		    	if(i%2 === 0) { row.setAttribute("class", "odd"); } else { row.setAttribute("class", "even"); }
		    	/* Edit */
		    	var cell = row.insertCell(-1);
		        cell.innerHTML = "<a id='editTeamLink' name='editTeamLink' title='Edit Team' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: left; ' ></a>"
		        /* Identifier */
		        var cell = row.insertCell(-1);
				cell.innerHTML = "<a id='editTeamIdentifierLink' name='editTeamIdentifierLink' title='Team Detail' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamDetail"+"\");'>"+teams[i].teamIdentifier+"</a>";
		        /* Name */
		        var cell = row.insertCell(-1); 
		        cell.innerHTML = teams[i].teamName;
		        /* Supervisor */
		        if(teams[i].supervisor===null || teams[i].supervisor==="") {
			        /* Name */
		        	var cell = row.insertCell(-1);
			        cell.innerHTML = "<a id='editTeamSupervisorLink' name='editTeamSupervisorLink' title='Edit Team Supervisor' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamSupervisorInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
			        /* Team */
			        var cell = row.insertCell(-1);
			        cell.innerHTML = "";
			        //cell.innerHTML = "-" ;
		        }	
		        else {
			        /* Name */
		        	var cell = row.insertCell(-1);
			        cell.innerHTML = teams[i].supervisor + "<br/>[" + teams[i].supervisorIdentifier + "]" + "<a id='editTeamSupervisorLink' name='editTeamSupervisorLink' title='Edit Team Supervisor' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamSupervisorInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
			        //cell.innerHTML = "<a id='editTeamSupervisorIdentifierLink' name='editTeamSupervisorIdentifierLink' title='Team Supervisor Detail' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamSupervisorDetail"+"\");'>"+teams[i].supervisor + " [" + teams[i].supervisorIdentifier + "]"+"</a>";
			        /* Team */
		        	var cell = row.insertCell(-1);
					cell.innerHTML = "<a id='editSupervisorTeamLink' name='editSupervisorTeamLink' title='Team Detail' style='cursor:pointer' onclick='editTeam(\""+teams[i].supervisorTeamUuid+"\",\""+"teamDetail"+"\");'>"+teams[i].supervisorTeam+"</a>";
			    }
		        /* Location */
		        var cell = row.insertCell(-1);
		        cell.innerHTML = teams[i].location.name + "<a id='editTeamLocationLink' name='editTeamLocationLink' title='Edit Team Location' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamLocationInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"
		        /* Voided */
		        var cell = row.insertCell(-1);
		        cell.innerHTML = teams[i].voided + "<a id='editTeamVoidedLink' name='editTeamVoidedLink' title='Void Team' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamVoidedInfo"+"\");' ><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"
		        /* Total Members */
		        var cell = row.insertCell(-1);
		        if(teams[i].members === 0) { cell.innerHTML = ""; } else {
		        	cell.innerHTML = "<a id='teamMemberLink' name='teamMemberLink' title='Team Members' style='cursor:pointer' onclick='editTeam(\""+teams[i].uuid+"\",\""+"teamMembersInfo"+"\");'>"+teams[i].members+"</a> Members"; 
		        }
		        /* History */
		        var cell = row.insertCell(-1);
		        cell.innerHTML = "<a id='teamHistoryLink' name='teamHistoryLink' title='Team History' style='cursor:pointer' onClick='teamHistory(\""+teams[i].uuid+"\")'><img src='/openmrs/moduleResources/teammodule/img/history.png' style=' width: 20px; height: 20px; padding-right: 10%; float: right;' ></a>";
		    	
		        if(teams[i].voidReason === null) { teams[i].voidReason = ""; }
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
  
	function teamHistory(teamId) {
		$.get("/openmrs/ws/rest/v1/team/teamlog?v=full&team=" + teamId, function(data) {
			var display = "", action = "", dataNew = "", dataOld = "", dateCreated = ""; 
			document.getElementById("historyBody").innerHTML = "";
			if(data.results.length > 0) {
				for (i = 0; i < data.results.length; i++) { 
					if(data.results[i].team === null) { display = ""; } else { if(data.results[i].team.display === null) { display = ""; } else { display = data.results[i].team.display.toString(); } }
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
		$("#historyDialog").dialog({ width : "auto", height : "auto", title : "Team - History", closeText : "", modal: true, open: onDialogOpen });
	}
	function teamMember(teamUuid) {
		$.get("/openmrs/ws/rest/v1/team/teamMember/"+teamUuid+"?v=full", function(data){
		  	var myTable = document.getElementById("member");
		  	var rowCount = myTable.rows.length;
		
		  	for (i = 0; i < rowCount; i++) {
				$("#memberRow").remove();
		  	}
		    var d = new Date(data.results[i].auditInfo.dateCreated);
			var date = d.format("dd-mm-yyyy");
			$("#member").append("<tr id=\"historyRow\">"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data.identifier+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data.team.teamName+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data.joinDate+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+date.location.name+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+date.person.gender+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMember/list.form?teamMemberId="+data[i].uuid+"\">Detail</a></td>"
			+"</tr>"); 
		});
		$( "#memberDialog" ).dialog( { width: "500px", height: "auto", title: "Team Members", closeText: "", modal: true, open: onDialogOpen });  
	}
	function supervisorTeamDetail(teamUuid) {
		$.get("/openmrs/ws/rest/v1/team/team/"+teamUuid+"?v=full", function(data){
		  	var myTable = document.getElementById("supervisorTeamDetail");
		  	var rowCount = myTable.rows.length;
		  	for (i = 0; i < rowCount; i++) { $("#supervisorTeamRow").remove(); }
	      	var d = new Date(data.results[i].auditInfo.dateCreated);
			var date = d.format("dd-mm-yyyy");
			$("#supervisorTeamDetailTable").append("<tr id=\"supervisorTeamRow\">"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data.identifier+"</td>"
			+"<td style=\"text-align: left;\" valign=\"top\">"+data.teamName+"</td>");
			if(data.supervisor==null) { $("#supervisorTeamDetail").append("<td style=\"text-align: left;\" valign=\"top\">-</td>"); }
			else { $("#supervisorTeamDetail").append("<td style=\"text-align: left;\" valign=\"top\">"+data.supervisor.name+"</td>"); }
			$("#supervisorTeamDetail").append("<td style=\"text-align: left;\" valign=\"top\">"+date.voided+"</td><td style=\"text-align: left;\" valign=\"top\">"+date.location.name+"</td></tr>");
		});
		$( "#supervisorTeamDetailDialog" ).dialog( { width: "500px", height: "auto", title: "supervisor Team Detail", closeText: "", modal: true, open: onDialogOpen });  
	}
	function editTeamClose(index, type) { 
		if(type === "teamInfo") { $('#editTeamDiv').dialog('close'); } 
		else if(type === "teamSupervisorDetail") { $('#detailTeamSupervisorDiv').dialog('close'); } 
		else if(type === "teamSupervisorInfo") { $('#editTeamSupervisorDiv').dialog('close'); } 
		else if(type === "teamLocationInfo") { $('#editTeamLocationDiv').dialog('close'); } 
		else if(type === "teamVoidedInfo") { $('#editTeamVoidedDiv').dialog('close'); } 
	}
	function editTeam(uuid, type) {
	    for (var i = 0; i < teams.length; i++) {
	    	if(teams[i].uuid.toString() === uuid.toString()) {
	    		if(type === "teamMembersInfo") {
					document.getElementById("team").value = teams[i].uuid.toString();
					document.getElementById("teamForm").submit();
				}
				else if(type === "teamDetail") {
	    			var dateCreated = "", dateChanged = ""; 
					if(teams[i].auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(teams[i].auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = teams[i].auditInfo.dateCreated.toString().substr(0, 10); } if(teams[i].auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = teams[i].auditInfo.dateChanged.toString().substr(0, 10); } }
					document.getElementById("detailTeamDiv").innerHTML = "<table id='teamDetail'><tr><td>Identifier: </td><td>"+teams[i].teamIdentifier+"</td></tr><tr><td>Name: </td><td>"+teams[i].teamName+"</td></tr><tr><td>Current Supervisor: </td><td>"+teams[i].supervisor+"</td></tr><tr><td>Supervisor Team: </td><td>"+teams[i].supervisorTeam+"</td></tr><tr><td>Location: </td><td>"+teams[i].location.name+"</td></tr><tr><td>Members: </td><td>"+teams[i].members+"</td></tr><tr><td>Voided: </td><td>"+teams[i].voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
					$("#detailTeamDiv").dialog({ width: "500px", height: "auto", title: "Team Detail" , closeText: "", modal: true, open: onDialogOpen });
				}
				else if(type === "teamSupervisorDetail") {
					$.ajax({
						url: "/openmrs/ws/rest/v1/team/teammember/"+teams[i].supervisorUuid+"?v=full",
						success : function(supervisor) {
							var locationName = "", subTeamRole = "", subTeam = "", dateCreated = "", dateChanged = ""; 
							if(supervisor.locations === null) { locationName = ""; } else { for(var j=0; j<supervisor.locations.length; j++) { if(j === supervisor.locations.length-1) { locationName += supervisor.locations[j].name; } else { locationName += supervisor.locations[j].name + ", "; } } }
							if(supervisor.subTeamRoles === null) { subTeamRole = ""; } else { for(var j=0; j<supervisor.subTeamRoles.length; j++) { if(j===supervisor.subTeamRoles.length-1) { subTeamRole += supervisor.subTeamRoles[j].name; } else { subTeamRole += supervisor.subTeamRoles[j].name + ", "; } } }
							if(supervisor.subTeams === null) { subTeam = ""; } else { for(var j=0; j<supervisor.subTeams.length; j++) { if(j===supervisor.subTeams.length-1) { subTeam += supervisor.subTeams[j].teamName; } else { subTeam += supervisor.subTeams[j].teamName + ", "; } } }
							if(supervisor.auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(supervisor.auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = supervisor.auditInfo.dateCreated.toString().substr(0, 10); } if(supervisor.auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = supervisor.auditInfo.dateChanged.toString().substr(0, 10); } }
							document.getElementById("detailTeamSupervisorDiv").innerHTML = "<table id='teamSupervisorDetail'><tr><td>Identifier: </td><td>"+supervisor.identifier+"</td></tr><tr><td>Name: </td><td>"+supervisor.display+"</td></tr><tr><td>Role: </td><td>"+supervisor.teamRole.display+"</td></tr><tr><td>Team: </td><td>"+supervisor.teamRole.display+"</td></tr><tr><td>Report To: </td><td>"+supervisor.team.supervisor+"</td></tr><tr><td>Locations: </td><td>"+locationName+"</td></tr><tr><td>Sub Roles: </td><td>"+subTeamRole+"</td></tr><tr><td>Sub Teams: </td><td>"+subTeam+"</td></tr><tr><td>Data Provider: </td><td>"+supervisor.isDataProvider+"</td></tr><tr><td>Voided: </td><td>"+supervisor.voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
			    			$("#detailTeamSupervisorDiv").dialog({ width: "500px", height: "auto", title: "Team Supervisor Detail" , closeText: "", modal: true, open: onDialogOpen });
						}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-TEAM-DETAIL"); console.log(jqXHR); }
					});
				}
				else if(type === "teamInfo") {
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Name: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamName"+i+"' name='teamName"+i+"' value='"+teams[i].teamName+"'></tr><tr><td style=' font-size: 18px; '>Identifier: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamIdentifier"+i+"' name='teamIdentifier"+i+"' value='"+teams[i].teamIdentifier+"'></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamEditClose' name='teamEditClose' onclick='editTeamClose(\""+i+"\",\""+"teamInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamEditSuccess' name='teamEditSuccess' onclick='editTeamSuccess(\""+i+"\",\""+"teamInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamDiv").innerHTML = html;
		    		$("#editTeamDiv").dialog({ width: "500px", height: "auto", title: "Team - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "teamSupervisorInfo") { 
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Supervisor: </td><td><select id='teamSupervisor"+i+"' name='teamSupervisor"+i+"' size='4' title='Team Supervisor' style=' font-size: 14px; padding: 5px; width: 100%; '>";
					if(teams[i].supervisor === null || teams[i].supervisor === "") { html += "<option value='' selected></option>"; for (var j = 0; j < allSupervisors.length; j++) { html += "<option value='"+allSupervisors[j].uuid.toString()+"'>"+allSupervisors[j].display.toString()+" ["+allSupervisors[j].identifier.toString()+"]</option>"; } }
					else { html += "<option value=''></option>"; for (var j = 0; j < allSupervisors.length; j++) { 
						if(allSupervisors[j].display.toString() === teams[i].supervisor.toString() && allSupervisors[j].identifier.toString() === teams[i].supervisorIdentifier.toString()) { html += "<option value='"+allSupervisors[j].uuid.toString()+"' selected >"+allSupervisors[j].display.toString()+" ["+allSupervisors[j].identifier.toString()+"]</option>"; } 
						else { html += "<option value='"+allSupervisors[j].uuid.toString()+"'>"+allSupervisors[j].display.toString()+" ["+allSupervisors[j].identifier.toString()+"]</option>"; } }
					} html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamSupervisorEditClose' name='teamSupervisorEditClose' onclick='editTeamClose(\""+i+"\",\""+"teamSupervisorInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamSupervisorEditSuccess' name='teamSupervisorEditSuccess' onclick='editTeamSuccess(\""+i+"\",\""+"teamSupervisorInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamSupervisorDiv").innerHTML = html;
		    		$("#editTeamSupervisorDiv").dialog({ width: "500px", height: "auto", title: "Team - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "teamLocationInfo") {
		    		var allLocationNames = []; var allLocationIds = [];
					<c:forEach items="${allLocations}" var="location">allLocationIds.push("${location.uuid}");allLocationNames.push("${location.name}");</c:forEach>
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Team Location: </td><td><select id='teamLocation"+i+"' name='teamLocation"+i+"' size='4' title='Team Location' style=' font-size: 14px; padding: 5px; width: 100%; '>"
		    		for (var j = 0; j < allLocationNames.length; j++) {
		    			if(allLocationNames[j].toString() === teams[i].location.name.toString()) { html += "<option value='"+allLocationIds[j]+"' selected >"+allLocationNames[j]+"</option>"; }
		    			else { html += "<option value='"+allLocationIds[j]+"'>"+allLocationNames[j]+"</option>"; }
		    		} html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamLocationEditClose' name='teamLocationEditClose' onclick='editTeamClose(\""+i+"\",\""+"teamLocationInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamLocationEditSuccess' name='teamLocationEditSuccess' onclick='editTeamSuccess(\""+i+"\",\""+"teamLocationInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
		    		document.getElementById("editTeamLocationDiv").innerHTML = html;
		    		$("#editTeamLocationDiv").dialog({ width: "500px", height: "auto", title: "Team - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "teamVoidedInfo") {
	    			var html = "<h3 id='voidError' name='voidError' style='color: red; display: inline'></h3><form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Voided: </td><td><select id='teamVoided"+i+"' name='teamVoided"+i+"' style=' font-size: 14px; padding: 5px; width: 100%; '>";
	    			if(teams[i].voided == true) { html += "<option value='"+teams[i].voided+"' selected >"+teams[i].voided+"</option><option value='false' >false</option>"; }
	    			else if(teams[i].voided == false) { html += "<option value='true' >true</option><option value='"+teams[i].voided+"' selected >"+teams[i].voided+"</option>"; }
	    			html += "</select></td></tr><tr><td style=' font-size: 18px; '>Void Reason: </td><td><textarea style=' width: 95%; font-size: 14px; padding: 5px; ' id='teamVoidReason"+i+"' name='teamVoidReason"+i+"' value='"+teams[i].voidReason+"'></textarea></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamVoidedEditClose' name='teamVoidedEditClose' onclick='editTeamClose(\""+i+"\",\""+"teamVoidedInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamVoidedEditSuccess' name='teamVoidedEditSuccess' onclick='editTeamSuccess(\""+i+"\",\""+"teamVoidedInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";	    			
	    			document.getElementById("editTeamVoidedDiv").innerHTML = html;
		    		$("#editTeamVoidedDiv").dialog({ width: "500px", height: "auto", title: "Team - Void" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    	}
	    }
	}
	function editTeamSuccess(index, type) {
		if(type === "teamInfo") { 
			var uuid = teams[index].uuid;
			var teamName = document.getElementById("teamName"+index).value;
			var teamIdentifier = document.getElementById("teamIdentifier"+index).value;
			var url = "/openmrs/ws/rest/v1/team/team/"+uuid;
			var data = '{ "teamName":"' + teamName + '", "teamIdentifier": "' + teamIdentifier + '" }'; 
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) {
					var team = result;
					for (var i = 0; i < teams.length; i++) {
				    	if(teams[i].uuid.toString() === team.uuid.toString()) {
				    		if(team.teamName.toString() === teams[i].teamName.toString()) {} else { saveLog("team", teams[i].uuid.toString(), team.teamName.toString(), teams[i].teamName.toString(), "TEAM_EDITED", ""); }
				    		if(team.teamIdentifier.toString() === teams[i].teamIdentifier.toString()) {} else { saveLog("team", teams[i].uuid.toString(), team.teamIdentifier.toString(), teams[i].teamIdentifier.toString(), "TEAM_EDITED", ""); }
				    		teams[i].teamName = team.teamName;
				    		teams[i].teamIdentifier = team.teamIdentifier;
							var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Updated Successfully</p>"; 
							$('#editTeamDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>"; }
			});
		}
		else if(type === "teamSupervisorInfo") { 
			var uuid = teams[index].uuid;
			var supervisor = document.getElementById("teamSupervisor"+index).value;
			var url = "/openmrs/ws/rest/v1/team/team/"+uuid;
			var data = '{ "supervisor":"' + supervisor + '" }'; 
			console.log();
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) {
					var team = result;
					for (var i = 0; i < teams.length; i++) {
				    	if(teams[i].uuid.toString() === team.uuid.toString()) {
				    		if(team.supervisor.toString() === teams[i].supervisor.toString()) {} else { saveLog("team", teams[i].uuid.toString(), team.supervisor.toString(), teams[i].supervisor.toString(), "TEAM_SUPERVISOR_CHANGED", ""); }
				    		teams[i].supervisor = team.supervisor;
				    		teams[i].supervisorIdentifier = team.supervisorIdentifier;
				    		teams[i].supervisorTeam = team.supervisorTeam;
				    		var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Supervisor Updated Successfully</p>"; 
							$('#editTeamSupervisorDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM SUPERVISOR"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>"; }
			});
		}
		else if(type === "teamLocationInfo") { 
			var uuid = teams[index].uuid;
	    	var location = document.getElementById("teamLocation"+index).value;
	    	var url = "/openmrs/ws/rest/v1/team/team/"+uuid;
			var data = '{ "location":"' + location + '" }'; 
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) {
					var team = result;
					for (var i = 0; i < teams.length; i++) {
				    	if(teams[i].uuid.toString() === team.uuid.toString()) {
				    		if(team.location.name.toString() === teams[i].location.name.toString()) {} else { saveLog("team", teams[i].uuid.toString(), team.location.name.toString(), teams[i].location.name.toString(), "TEAM_LOCATION_EDITED", ""); }
				    		teams[i].location = team.location;
				    		var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Location Updated Successfully</p>"; 
							$('#editTeamLocationDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM LOCATION"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team</p>"; }
			});
		}
		else if(type === "teamVoidedInfo") { 
			var uuid = teams[index].uuid;
	    	var voided = document.getElementById("teamVoided"+index).value;
	    	var voidReason = document.getElementById("teamVoidReason"+index).value;
			if(voidReason.length > 255) { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else if(voided === "true" && voidReason === "") { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else {
				var url = "/openmrs/ws/rest/v1/team/team/"+uuid;
				var data = '{ '; 
				if(voided != "false") { data += '"voided":"' + voided + '", "voidReason":"' + voidReason + '"'; } else { data += '"voided":"' + voided + '"'; } 
				data += ' }';
				document.getElementById("voidError").innerHTML = "";
				$.ajax({
					url: url,
					data : data,
				 	type: "POST",
	     			contentType: "application/json",
					success : function(result) {
						var team = result;
						for (var i = 0; i < teams.length; i++) {
					    	if(teams[i].uuid.toString() === team.uuid.toString()) {
					    		if(team.voidReason === null) { team.voidReason = ""; }
					    		if(team.voided.toString()+"-"+team.voidReason.toString() === teams[i].voided.toString()+"-"+teams[i].voidReason.toString()) {} else { saveLog("team", teams[i].uuid.toString(), team.voided.toString()+"-"+team.voidReason.toString(), teams[i].voided.toString()+"-"+teams[i].voidReason.toString(), "TEAM_VOIDED", ""); }
					    		teams[i].voided = team.voided;
					    		teams[i].voidReason = teams.voidReason;
					    		var tbody = document.getElementById("tbody");
								tbody.innerHTML = ""; GenerateTable(tbody);
								document.getElementById("errorHead").innerHTML = ""; 
								document.getElementById("saveHead").innerHTML = "<p>Team Voided Successfully</p>"; 
								$('#editTeamVoidedDiv').dialog('close'); 
							}
						}
					}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM VOIDED"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Voiding Team</p>"; }
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
	function onDialogOpen( event, ui ) {
		var dialogHeads = document.getElementsByClassName("ui-dialog-title");  for (var loop = 0; loop < dialogHeads.length; loop++) {  dialogHeads[loop].setAttribute("style", "font-size: 18px;"); } 
		var dialogCloseBtns = document.getElementsByClassName("ui-button-icon ui-icon ui-icon-closethick"); for (var loop = 0; loop < dialogCloseBtns.length; loop++) {  dialogCloseBtns[loop].setAttribute("style", "top: 0; left: 0; right: 0; bottom: 0;"); } 
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

<div id="allMemberDialog">
	<table id="allMember">
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

<div id="memberDialog">
	<table id="member">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Join Date</th>
			<th>Location</th>
			<th>Gender</th>
		</tr>
	</table>
</div>

<div id="supervisorTeamDetailDialog">
	<table id="supervisorTeamDetail">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>supervisor</th>
			<th>Gender</th>
			<th>Patients</th>
			<th>Detail</th>
		</tr>
	</table>
</div>

<div id="detailTeamDiv"></div>
<div id="detailTeamSupervisorDiv"></div>
<div id="editTeamDiv"></div>
<div id="editTeamSupervisorDiv"></div>
<div id="editTeamLocationDiv"></div>
<div id="editTeamVoidedDiv"></div>

<form id="teamForm" name="teamForm" action="/openmrs/module/teammodule/teamMemberView.form" method="POST">
	<input type="hidden" id="team" name="team">
</form>

<%@ include file="/WEB-INF/template/footer.jsp"%>