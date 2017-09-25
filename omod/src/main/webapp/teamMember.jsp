<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Member" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	#history, #subTeamsTable, #subTeamRolesTable, #teamMemberDetail {
	    /* border: 1px solid black; */
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #subTeamsTable th, #subTeamsTable td, #subTeamRolesTable th, #subTeamRolesTable td, #teamMemberDetail th, #teamMemberDetail td {
	    /* border: 1px solid black; */
	    text-align: left;
	    font-size: 14px;
	    padding: 10px;
	}
	#history tr:hover, #subTeamsTable tr:hover, #subTeamRolesTable tr:hover, #teamMemberDetail tr:hover {
		background-color: #f5f5f5;
	}
	#history tr:nth-child(odd), #subTeamsTable tr:nth-child(odd), #subTeamRolesTable tr:nth-child(odd), #teamMemberDetail tr:nth-child(odd) {
		background-color: #f5f5f5;
	}
</style>

<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-1.12.4.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery-ui.js"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/js/jquery.dataTables.min.js"></script>

<script type="text/javascript">
	function getId()  { return "${id}"; } 
	function getSupervisorId()  { return "${supervisorId}"; }
	function getTeamId()  { return "${teamId}"; }
	function getTeamRoleId()  { return "${teamRoleId}"; }
	function getLocationId()  { return "${locationId}"; }
	
	var my_id = getId().toString();
	var my_supervisorId = getSupervisorId().toString();
	var my_teamId = getTeamId().toString();
	var my_teamRoleId = getTeamRoleId().toString();
	var my_locationId = getLocationId().toString();
	
	var locations = [];
	var members = [];
	var headerArray = ["edit", "Identifier", "Name", "Role", "Team", "Report To", "Locations", "Sub Roles", "Sub Teams", "Is Data Provider", "Voided", "Patients", "History"];
	
	$(document).ready(function() {
		$('#historyDialog').hide();
		
		var url = "/openmrs/ws/rest/v1/team/teammember?v=full";
		if(my_id === "" && my_supervisorId === "" && my_teamRoleId === "" && my_teamId === "" && my_locationId === "") { 
			url += "&get=all"; 
		}
		else { url += "&get=filter";
			if(my_id != "") { url += "&identifier=" + my_id; }
			if(my_supervisorId != "") { url += "&supervisor=" + my_supervisorId; }
			if(my_teamId != "") { url += "&team=" + my_teamId; }
			if(my_teamRoleId != "") { url += "&role=" + my_teamRoleId; }
			if(my_locationId != "") { url += "&location=" + my_locationId; }
		}
		
		$.ajax({
			url: url,
			success : function(result) { 
				members = result.results;
				var table = document.getElementById("general");
				var tbody= document.createElement("TBODY");
				tbody.id= "tbody";
				GenerateTable(tbody);
				table.appendChild(tbody);
				$('#general').DataTable({
					"bFilter": false,
					"ordering": false,
					"columnDefs": [ 
                        { "width": "1%", "targets": [ 0 ] }, 
                        { "width": "17%", "targets": [ 1, 2, 3, 4, 5] },
                        { "width": "7%", "targets": [ 6, 7] },
                        { "width": "1%", "targets": [ 8, 9 ] } 
	                ],
					"paging" : true,
					"lengthChange" : false,
					"searching" : false,
					"ordering" : false,
					"info" : false,
					"autoWidth" : true,
					"sDom" : 'lfrtip'
				});
			}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-ALL"); console.log(jqXHR); }
		});
		$("#submitBtn").bind("click", function() {
			var url = "/openmrs/ws/rest/v1/team/teammember?v=full&get=filter";
			var id = document.getElementById("filterById").value;
			var supervisor = document.getElementById("filterBySupervisor").value;
			var role = document.getElementById("filterByTeamRole").value;
			var team = document.getElementById("filterByTeam").value;
			var location = document.getElementById("filterByLocation").value;
			if(id === "" && supervisor === "" && role === "" && team === "" && location === "") { }
			else {
				if(id != "") { url += "&identifier=" + id; }
				if(supervisor != "") { url += "&supervisor=" + supervisor; }
				if(role != "") { url += "&role=" + role; }
				if(team != "") { url += "&team=" + team; }
				if(location != "") { url += "&location=" + location; }
				$.ajax({
					url: url,
					success : function(result) { 
						members = result.results;
						var tbody = document.getElementById("tbody");
						tbody.innerHTML = ""; 
						GenerateTable(tbody);
					}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
				});
			}
		});
	});
	 
	function GenerateTable(tbody) {
		if(members.length > 0) {
    	    for (var i = 0; i < members.length; i++) {
    	    	if(members[i].voided === true) { }
				else {
					row = tbody.insertRow(-1); row.setAttribute("role", "row"); 
			    	if(i%2 === 0) { row.setAttribute("class", "odd"); } else { row.setAttribute("class", "even"); }
	
			    	/* Edit */
			    	var cell = row.insertCell(-1);
			        cell.innerHTML = "<a id='editTeamMemberLink' name='editTeamMemberLink' title='Edit Team Member' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: left;' ></a>";
			        /* Identifier */
			        var cell = row.insertCell(-1);
			        var name = "";
					if(members[i].person.person === undefined) { name = members[i].person.display; } else { name = members[i].person.person.display; }
					cell.innerHTML = "<a id='editTeamMemberIdentifierLink' name='editTeamMemberIdentifierLink' title='Team Member Detail' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberDetail"+"\");'>"+members[i].identifier+"</a>"+"<br/>"+name;
			        /* Role */
			        var cell = row.insertCell(-1);
			        if(members[i].teamRole === null) { cell.innerHTML = "<a id='editTeamMemberRoleLink' name='editTeamMemberRoleLink' title='Edit Team Member Role' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberRoleInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; } 
			        else { cell.innerHTML = members[i].teamRole.display + "<a id='editTeamMemberRoleLink' name='editTeamMemberRoleLink' title='Edit Team Member Role' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberRoleInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
			        /* Team */
			        var cell = row.insertCell(-1);
					if(members[i].team === null) { cell.innerHTML = "<br/><a id='editTeamMemberTeamLink' name='editTeamMemberTeamLink' title='Edit Team Member Team' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberTeamInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
					else {
				        var reportTo = ""; if(members[i].person.person === undefined) { if(members[i].team.supervisor == members[i].person.display) { reportTo = ""; } else { reportTo = members[i].team.supervisor; } } else { if(members[i].team.supervisor == members[i].person.person.display) { reportTo = ""; } else { reportTo = members[i].team.supervisor; } }
						if(reportTo === "") { cell.innerHTML = members[i].team.display + "<br/>[...] <a id='editTeamMemberTeamLink' name='editTeamMemberTeamLink' title='Edit Team Member Team' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberTeamInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
						else { cell.innerHTML = members[i].team.display + "<br/>[" + reportTo + "] <a id='editTeamMemberTeamLink' name='editTeamMemberTeamLink' title='Edit Team Member Team' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberTeamInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
					}
			       	/* Locations */
			        var cell = row.insertCell(-1);
			        var locationName = "";
			    	for(var j=0; j<members[i].locations.length; j++) { if(j === members[i].locations.length-1) { locationName += "<li>" + members[i].locations[j].name + "</li>"; } else { locationName += "<li>" + members[i].locations[j].name + "</li>"; } }
			    	if(locationName === "") { cell.innerHTML = locationName + "<a id='editTeamMemberLocationLink' name='editTeamMemberLocationLink' title='Edit Team Member Location' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberLocationInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; } else { cell.innerHTML = locationName + "<a id='editTeamMemberLocationLink' name='editTeamMemberLocationLink' title='Edit Team Member Location' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberLocationInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>"; }
			        /* Sub Ordinate Teams */
			        var cell = row.insertCell(-1);
			        if(members[i].subTeams === null) { cell.innerHTML = ""; } else { 
			        	var text = "";
						for(var j=0; j<members[i].subTeams.length; j++) { text += "<li><a id='teamMemberTeam' name='teamMemberTeam' title='Vew Team' style='cursor:pointer' onclick='teamMemberTeamDetail(\""+members[i].uuid+"\",\""+members[i].subTeams[j].uuid+"\");'>"+members[i].subTeams[j].teamName+"</a> ("+members[i].subTeams[j].members+")</li>" }
			        	cell.innerHTML = text;
			        } 
			        /* Is Data Provider */
			        var cell = row.insertCell(-1); 
					cell.innerHTML = members[i].isDataProvider + "<a id='editTeamMemberDataProviderLink' name='editTeamMemberDataProviderLink' title='Edit Team Member Data Provider' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberDataProviderInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
			        /* Voided */
			        var cell = row.insertCell(-1);
			        cell.innerHTML = members[i].voided + "<a id='editTeamMemberVoidedLink' name='editTeamMemberVoidedLink' title='Void Team Member' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberVoidedInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
			        /* Patients */
			        var cell = row.insertCell(-1);
			        if(members[i].patients.length === 0) { cell.innerHTML = ""; } else { cell.innerHTML = "<a id='teamMemberPatientLink' name='teamMemberPatientLink' title='Team Member Patients' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberPatientInfo"+"\");'>"+members[i].patients.length+"</a>"; }
			        /* History */
			        var cell = row.insertCell(-1);
			        cell.innerHTML = "<a id='teamMemberHistoryLink' name='teamMemberHistoryLink' title='Team Member History' style='cursor:pointer' onClick='teamMemberHistory(\""+members[i].uuid+"\")'><img src='/openmrs/moduleResources/teammodule/img/history.png' style=' width: 20px; height: 20px; padding-right: 10%; float: right;' ></a>";
			        
			        if(members[i].voidReason === null) { members[i].voidReason = ""; }
    	    	}
    	    }
	    }
    	else {
	    	row = tbody.insertRow(-1);
	        var cell = row.insertCell(-1);
	    	cell.colSpan= headerArray.length
    		cell.innerHTML = "<strong>No Records Found</strong>";
	    	cell.setAttribute("style", "border: 1px solid; text-align: center; background-color: #bbccf7");
    	}
	}
	function teamMemberHistory(teamMemberId) {
		$.get("/openmrs/ws/rest/v1/team/teammemberlog?v=full&teamMember=" + teamMemberId, function(data) {
			var display = "", action = "", dataNew = "", dataOld = "", dateCreated = ""; 
			document.getElementById("historyBody").innerHTML = "";
			if(data.results.length > 0) {
				for (i = 0; i < data.results.length; i++) { 
					if(data.results[i].teamMember === null) { display = ""; } else { if(data.results[i].teamMember.display === null) { display = ""; } else { display = data.results[i].teamMember.display.toString(); } }
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
		$("#historyDialog").dialog({ width : "auto", height : "auto", title : "Team Member - History", closeText : "", modal: true, open: onDialogOpen });
	}
    function editTeamMemberClose(index, type) {
    	if(type === "memberDetail") { $('#detailTeamMemberDiv').dialog('close'); } 
		else if(type === "memberRoleInfo") { $('#editTeamMemberRoleDiv').dialog('close'); } 
		else if(type === "memberTeamInfo") { $('#editTeamMemberTeamDiv').dialog('close'); } 
		else if(type === "memberInfo") { $('#editTeamMemberDiv').dialog('close'); } 
		else if(type === "memberLocationInfo") { $('#editTeamMemberLocationDiv').dialog('close'); } 
		else if(type === "memberVoidedInfo") { $('#editTeamMemberVoidedDiv').dialog('close'); } 
		else if(type === "memberDataProviderInfo") { $('#editTeamMemberDataProviderDiv').dialog('close'); } 
	}
	function teamMemberTeamDetail(teamMemberUuid, teamUuid) {
		$.ajax({
			url: "/openmrs/ws/rest/v1/team/team/"+teamUuid+"?v=full",
			success : function(team) { 
				var dateCreated = "", dateChanged = ""; 
				if(team.auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(team.auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = team.auditInfo.dateCreated.toString().substr(0, 10); } if(team.auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = team.auditInfo.dateChanged.toString().substr(0, 10); } }
				document.getElementById("teamMemberTeamDetailDiv").innerHTML = "<table id='subTeamsTable'><tr><td>Identifier: </td><td>"+team.teamIdentifier+"</td></tr><tr><td>Name: </td><td>"+team.teamName+"</td></tr><tr><td>Current Supervisor: </td><td>"+team.supervisor+"</td></tr><tr><td>Supervisor Team: </td><td>"+team.supervisorTeam+"</td></tr><tr><td>Location: </td><td>"+team.location.name+"</td></tr><tr><td>Members: </td><td>"+team.members+"</td></tr><tr><td>Voided: </td><td>"+team.voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
	    		$("#teamMemberTeamDetailDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Sub Team" , closeText: "", modal: true, open: onDialogOpen });
			}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-TEAM-DETAIL"); console.log(jqXHR); }
		});
	}
	function teamMemberTeamRoleDetail(teamMemberUuid, teamRoleUuid) {
		$.ajax({
			url: "/openmrs/ws/rest/v1/team/teamrole/"+teamRoleUuid+"?v=full",
			success : function(teamRole) { 
				var reportTo = "", reportBy = "", dateCreated = "", dateChanged = ""; 
				if(teamRole.reportTo === null) { reportTo = ""; } else { reportTo = teamRole.reportTo.name; } 
				if(teamRole.reportByName !== null) { for(var j=0; j<teamRole.reportByName.length; j++) { if(j===teamRole.reportByName.length-1) { reportBy += teamRole.reportByName[j]; } else { reportBy += teamRole.reportByName[j] + ", "; } } } else { reportBy = ""; }
				if(teamRole.auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(teamRole.auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = teamRole.auditInfo.dateCreated.toString().substr(0, 10); } if(teamRole.auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = teamRole.auditInfo.dateChanged.toString().substr(0, 10); } }
				document.getElementById("teamMemberTeamRoleDetailDiv").innerHTML = "<table id='subTeamRolesTable'><tr><td>Identifier: </td><td>"+teamRole.identifier+"</td></tr><tr><td>Name: </td><td>"+teamRole.name+"</td></tr><tr><td>Owns Team: </td><td>"+teamRole.ownsTeam.toString()+"</td></tr><tr><td>Report To: </td><td>"+reportTo+"</td></tr><tr><td>Report By: </td><td>"+reportBy+"</td></tr><tr><td>Members: </td><td>"+teamRole.members+"</td></tr><tr><td>Voided: </td><td>"+teamRole.voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
	    		$("#teamMemberTeamRoleDetailDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Sub Team Role" , closeText: "", modal: true, open: onDialogOpen });
			}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-TEAM-DETAIL"); console.log(jqXHR); }
		});
	}
	function editTeamMember(uuid, type) {
	    for (var i = 0; i < members.length; i++) {
	    	if(members[i].uuid.toString() === uuid.toString()) {
	    		if(type === "memberPatientInfo") { }
	    		else if(type === "memberDetail") {
					var locationName = "", subTeamRole = "", subTeam = "", dateCreated = "", dateChanged = "", team = "", teamSupervisor = "", teamRole = "";  
					if(members[i].locations === null) { locationName = ""; } else { for(var j=0; j<members[i].locations.length; j++) { if(j === members[i].locations.length-1) { locationName += members[i].locations[j].name; } else { locationName += members[i].locations[j].name + ", "; } } }
					if(members[i].subTeamRoles === null) { subTeamRole = ""; } else { for(var j=0; j<members[i].subTeamRoles.length; j++) { if(j===members[i].subTeamRoles.length-1) { subTeamRole += members[i].subTeamRoles[j].name + " (" + members[i].subTeamRoles[j].members + ")"; } else { subTeamRole += members[i].subTeamRoles[j].name + " (" + members[i].subTeamRoles[j].members + "), "; } } }
					if(members[i].subTeams === null) { subTeam = ""; } else { for(var j=0; j<members[i].subTeams.length; j++) { if(j===members[i].subTeams.length-1) { subTeam += members[i].subTeams[j].teamName + " (" + members[i].subTeams[j].members + ")"; } else { subTeam += members[i].subTeams[j].teamName + " (" + members[i].subTeams[j].members + "), "; } } }
					if(members[i].auditInfo === null) { dateCreated = ""; dateChanged = ""; } else { if(members[i].auditInfo.dateCreated === null) { dateCreated = ""; } else { dateCreated = members[i].auditInfo.dateCreated.toString().substr(0, 10); } if(members[i].auditInfo.dateChanged === null) { dateChanged = ""; } else { dateChanged = members[i].auditInfo.dateChanged.toString().substr(0, 10); } }
					if(members[i].team === null) { team = ""; teamSuperviosr = ""; } else { team = members[i].team.display; teamSuperviosr = members[i].team.supervisor; }
					if(members[i].teamRole === null) { teamRole = ""; } else { teamRole = members[i].teamRole.display; }
					document.getElementById("detailTeamMemberDiv").innerHTML = "<table id='teamMemberDetail'><tr><td>Identifier: </td><td>"+members[i].identifier+"</td></tr><tr><td>Name: </td><td>"+members[i].display+"</td></tr><tr><td>Role: </td><td>"+teamRole+"</td></tr><tr><td>Team: </td><td>"+team+"</td></tr><tr><td>Report To: </td><td>"+teamSupervisor+"</td></tr><tr><td>Locations: </td><td>"+locationName+"</td></tr><tr><td>Sub Roles: </td><td>"+subTeamRole+"</td></tr><tr><td>Sub Teams: </td><td>"+subTeam+"</td></tr><tr><td>Data Provider: </td><td>"+members[i].isDataProvider+"</td></tr><tr><td>Voided: </td><td>"+members[i].voided+"</td></tr><tr><td>Date Created: </td><td>"+dateCreated+"</td></tr><tr><td>Date Changed: </td><td>"+dateChanged+"</td></tr></table>";
	    			$("#detailTeamMemberDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Detail" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberLocationInfo") {
					var allLocationNames = []; var allLocationIds = []; var myLocationNames = [];
					<c:forEach items="${allLocations}" var="location">allLocationIds.push("${location.uuid}");allLocationNames.push("${location.name}");</c:forEach>
	    			var mySize; if(members[i].locations.length > 0) { mySize = members[i].locations.length + 1; } else { mySize = members[i].locations.length + 2; } 
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; ' >Member Locations: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberCurrentLocations"+i+"' name='teamMemberCurrentLocations"+i+"' onchange='getSelectedCurrentLocations(this)' size='"+mySize+"' title='Click To Remove Member Location'>"
		    		for (var j = 0; j < members[i].locations.length; j++) {
		    			html += "<option value='"+members[i].locations[j].uuid+"'>"+members[i].locations[j].display+"</option>";
		    			myLocationNames.push(members[i].locations[j].display);
		    		} html += "</select></td></tr><tr><td style=' font-size: 18px; '>All Locations: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberAllLocations"+i+"' name='teamMemberAllLocations"+i+"' onchange='getSelectedAllLocations(this)' size='"+mySize+"' title='Click To Add Member Location'>";
		    		for (var k = 0; k < allLocationNames.length; k++) {
		    			if(myLocationNames.includes(allLocationNames[k])) { }
		    			else { html += "<option value='"+allLocationIds[k]+"'>"+allLocationNames[k]+"</option>"; }
		    		} html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberLocationEditClose' name='teamMemberLocationEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberLocationInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamMemberLocationEditSuccess' name='teamMemberLocationEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberLocationInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
		    		document.getElementById("editTeamMemberLocationDiv").innerHTML = html;
		    		$("#editTeamMemberLocationDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberRoleInfo") {
					var allTeamRoleNames = []; var allTeamRoleIds = []; 
					<c:forEach items="${allTeamRoles}" var="teamRole">allTeamRoleIds.push("${teamRole.uuid}");allTeamRoleNames.push("${teamRole.name}");</c:forEach>
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Role: </td><td><select size=5 style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberRole"+i+"' name='teamMemberRole"+i+"'>";
	    			if(members[i].teamRole === null) { 
	    				html += "<option value='' selected>Please Select</option>";
	    				for (var j = 0; j < allTeamRoleNames.length; j++) { html += "<option value='"+allTeamRoleIds[j]+"' >"+allTeamRoleNames[j]+"</option>"; }
    				}
	    			else { html += "<option value=''>Please Select</option>";
		    			for (var j = 0; j < allTeamRoleNames.length; j++) { 
			    			if(allTeamRoleNames[j].toString() === members[i].teamRole.display.toString()) { html += "<option value='"+allTeamRoleIds[j]+"' selected >"+allTeamRoleNames[j]+"</option>"; } 
			    			else { html += "<option value='"+allTeamRoleIds[j]+"'>"+allTeamRoleNames[j]+"</option>"; }
		    			}
		    		} html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberRoleEditClose' name='teamMemberRoleEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberRoleInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamMemberRoleEditSuccess' name='teamMemberRoleEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberRoleInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamMemberRoleDiv").innerHTML = html;
		    		$("#editTeamMemberRoleDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberTeamInfo") {
		    	    var allTeamNames = []; var allTeamIds = [];  
		    	    <c:forEach items="${allTeams}" var="team">allTeamIds.push("${team.uuid}"); allTeamNames.push("${team.teamName}");</c:forEach>
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Team: </td><td><select size=5 style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberTeam"+i+"' name='teamMemberTeam"+i+"'>";
	    			if(members[i].team === null) { 
	    				html += "<option value='' selected>Please Select</option>";
	    				for (var j = 0; j < allTeamNames.length; j++) { html += "<option value='"+allTeamIds[j]+"' >"+allTeamNames[j]+"</option>"; }
    				}
	    			else { html += "<option value=''>Please Select</option>";
	    				for (var j = 0; j < allTeamNames.length; j++) {
			    			if(allTeamNames[j].toString() === members[i].team.display.toString()) { html += "<option value='"+allTeamIds[j]+"' selected >"+allTeamNames[j]+"</option>"; }
			    			else { html += "<option value='"+allTeamIds[j]+"' >"+allTeamNames[j]+"</option>"; }
		    			}
	    			} html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberTeamEditClose' name='teamMemberTeamEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberTeamInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamMemberTeamEditSuccess' name='teamMemberTeamEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberTeamInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
					document.getElementById("editTeamMemberTeamDiv").innerHTML = html;
		    		$("#editTeamMemberTeamDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberInfo") {
	    			var given = "", middle = "", last = "";
	    			if(members[i].person.person === undefined) { 
	    				if(members[i].person.preferredName.givenName !== null) { given = members[i].person.preferredName.givenName; } else { given = ""; }
	    				if(members[i].person.preferredName.middleName !== null) { middle = members[i].person.preferredName.middleName; } else { middle = ""; }
	    				if(members[i].person.preferredName.familyName !== null) { last = members[i].person.preferredName.familyName; } else { last = ""; }
	    			} else { 
	    				if(members[i].person.person.preferredName.givenName !== null) { given = members[i].person.person.preferredName.givenName; } else { given = ""; }
	    				if(members[i].person.person.preferredName.middleName !== null) { middle = members[i].person.person.preferredName.middleName; } else { middle = ""; }
	    				if(members[i].person.person.preferredName.familyName !== null) { last = members[i].person.person.preferredName.familyName; } else { last = ""; }
	    			}
	    			var html = "<h3 id='infoError' name='infoError' style='color: red; display: inline'></h3><form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Name: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamMemberPersonFirstName"+i+"' name='teamMemberPersonFirstName"+i+"' value='"+given+"' maxlength='50' ></tr><tr><td style=' font-size: 18px; '>Middle Name: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamMemberPersonMiddleName"+i+"' name='teamMemberPersonMiddleName"+i+"' value='"+middle+"' maxlength='50' ></tr><tr><td style=' font-size: 18px; '>Family Name: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamMemberPersonLastName"+i+"' name='teamMemberPersonLastName"+i+"' value='"+last+"' maxlength='50' ></td></tr><tr><td style=' font-size: 18px; '>Identifier: </td><td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type'text' id='teamMemberIdentifier"+i+"' name='teamMemberIdentifier"+i+"' value='"+members[i].identifier+"' maxlength='45' ></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberEditClose' name='teamMemberEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberInfo"+"\");' style='float: left;'>Cancel</button><button type='button' id='teamMemberEditSuccess' name='teamMemberEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";
	    			document.getElementById("editTeamMemberDiv").innerHTML = html;
		    		$("#editTeamMemberDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberVoidedInfo") {
	    			var html = "<h3 id='voidError' name='voidError' style='color: red; display: inline'></h3><form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Voided: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberVoided"+i+"' name='teamMemberVoided"+i+"' onchange='voidReasonView(\""+i+"\");'>";
	    			if(members[i].voided == true) { html += "<option value='"+members[i].voided+"' selected >"+members[i].voided+"</option><option value='false' >false</option>"; }
	    			else if(members[i].voided == false) { html += "<option value='true' >true</option><option value='"+members[i].voided+"' selected >"+members[i].voided+"</option>"; }
	    			html += "</select></td></tr><tr><td style=' font-size: 18px; '>Void Reason: </td><td><textarea style=' width: 95%; font-size: 14px; padding: 5px; ' id='teamMemberVoidReason"+i+"' name='teamMemberVoidReason"+i+"' value='"+members[i].voidReason+"' maxlength='255' rows='5' ></textarea></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberVoidedEditClose' name='teamMemberVoidedEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberVoidedInfo"+"\");' style='float: left;' >Cancel</button><button type='button' id='teamMemberVoidedEditSuccess' name='teamMemberVoidedEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberVoidedInfo"+"\");' style='float: right;'>Save</button></td></tr></table></form>";	    			
	    			document.getElementById("editTeamMemberVoidedDiv").innerHTML = html; voidReasonView(i);
		    		$("#editTeamMemberVoidedDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Void" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    		else if(type === "memberDataProviderInfo") {
	    			var html = "<form><table style=' width: 100%; '><tr><td style=' font-size: 18px; '>Data Provider: </td><td><select style=' font-size: 14px; padding: 5px; width: 100%; ' id='teamMemberDataProvider"+i+"' name='teamMemberDataProvider"+i+"'>";
	    			if(members[i].isDataProvider == true) { html += "<option value='"+members[i].isDataProvider+"' selected >"+members[i].isDataProvider+"</option><option value='false' >false</option>"; }
	    			else if(members[i].isDataProvider == false) { html += "<option value='true' >true</option><option value='"+members[i].isDataProvider+"' selected >"+members[i].isDataProvider+"</option>"; }
	    			html += "</select></td></tr><tr><td></td><td></td></tr><tr><td></td><td><button type='button' id='teamMemberDataProviderEditClose' name='teamMemberDataProviderEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberDataProviderInfo"+"\");' style='float: left;' >Cancel</button><button type='button' id='teamMemberDataProviderEditSuccess' name='teamMemberDataProviderEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberDataProviderInfo"+"\");' style='float: right;' >Save</button></td></tr></table></form>";	    			
	    			document.getElementById("editTeamMemberDataProviderDiv").innerHTML = html;
		    		$("#editTeamMemberDataProviderDiv").dialog({ width: "500px", height: "auto", title: "Team Member - Edit" , closeText: "", modal: true, open: onDialogOpen });
	    		}
	    	}
	    }
	}
	function editTeamMemberSuccess(index, type) {
		if(type === "memberInfo") {
			var otherTeamMemberFirstNames = []; var otherTeamMemberMiddleNames = []; var otherTeamMemberFamilyNames = []; var otherTeamMemberIdentifiers = [];
			jQuery.ajax({
				url: "/openmrs/ws/rest/v1/team/teammember?v=full",
				success : function(test) { var otherTeamMembers = test.results; 
					for (var i = 0; i < otherTeamMembers.length; i++) { 
						if(otherTeamMembers[i].uuid.toString() !== members[index].uuid.toString()) { 
							if(otherTeamMembers[i].person.person === undefined) {
								otherTeamMemberFirstNames.push(otherTeamMembers[i].person.preferredName.givenName);
								otherTeamMemberMiddleNames.push(otherTeamMembers[i].person.preferredName.middleName);
								otherTeamMemberFamilyNames.push(otherTeamMembers[i].person.preferredName.familyName); 
								otherTeamMemberIdentifiers.push(otherTeamMembers[i].identifier);
							}
							else {
								otherTeamMemberFirstNames.push(otherTeamMembers[i].person.person.preferredName.givenName);
								otherTeamMemberMiddleNames.push(otherTeamMembers[i].person.person.preferredName.middleName);
								otherTeamMemberFamilyNames.push(otherTeamMembers[i].person.person.preferredName.familyName); 
								otherTeamMemberIdentifiers.push(otherTeamMembers[i].identifier);
							}
						}
					}
					var uuid = members[index].uuid;
			    	var id = document.getElementById("teamMemberIdentifier"+index).value;
			    	var firstName = document.getElementById("teamMemberPersonFirstName"+index).value;
			    	var middleName = document.getElementById("teamMemberPersonMiddleName"+index).value;
			    	var lastName = document.getElementById("teamMemberPersonLastName"+index).value;
			    	if((!(otherTeamMemberFirstNames.includes(firstName))) && (!(otherTeamMemberMiddleNames.includes(middleName))) && (!(otherTeamMemberFamilyNames.includes(lastName))) && (!(otherTeamMemberIdentifiers.includes(id)))) {
			    		if(id === members[index].identifier) {// NO CHANGE IN IDENTIFIER
				    		var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
							var data = '{ '; if(id != "") { data += '"identifier" : "' + id + '"'; } data += ' }';
							$.ajax({
								url: url,
								data : data,
							 	type: "POST",
				     			contentType: "application/json",
								success : function(result) { 
									var teamMember = result;
									var data2 = '{ ';
									if(firstName != "") { data2 += ' "givenName" : "' + firstName + '", '; }
									if(middleName != "") { data2 += ' "middleName" : "' + middleName + '", '; }
									if(lastName != "") { data2 += ' "familyName" : "' + lastName + '" '; }
									data2 += ' }';
									var url2 = "/openmrs/ws/rest/v1/person/"+teamMember.person.uuid+"/name/";
									if(teamMember.person.person === undefined) { url2 += teamMember.person.preferredName.uuid; } else { url2 += teamMember.person.person.preferredName.uuid; }
									$.ajax({
										url: url2,
										data : data2,
									 	type: "POST",
						     			contentType: "application/json",
										success : function(result) { 
											for (var i = 0; i < members.length; i++) {
										    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
													if(teamMember.person.person === undefined) { teamMember.person.preferredName = result; if(teamMember.person.preferredName.display.toString() === members[i].person.preferredName.display.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.person.preferredName.display.toString(), members[i].person.preferredName.display.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; } } 
													else { teamMember.person.person.preferredName = result; if(teamMember.person.person.preferredName.display.toString() === members[i].person.person.preferredName.display.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.person.person.preferredName.display.toString(), members[i].person.person.preferredName.display.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; } members[i].person.person = teamMember.person.person; }
													members[i].person = teamMember.person;
													if(teamMember.identifier.toString() === members[i].identifier.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.identifier.toString(), members[i].identifier.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; }
													members[i].identifier = teamMember.identifier;
													var tbody = document.getElementById("tbody");
													tbody.innerHTML = ""; GenerateTable(tbody);
													$('#editTeamMemberDiv').dialog('close'); 
										    	}
											}
										}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO PERSON"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
									});
								}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
							});
						}
				    	else {
							var url1 = "/openmrs/ws/rest/v1/team/teammember?get=filter&v=full";
							if(id != "") { url1 += "&identifier=" + id; }
							console.log(url1);
							jQuery.ajax({
								url: url1,
								success : function(result) { var myIdentifiers = []; for(var loop=0; loop<result.results.length; loop++) { myIdentifiers.push(result.results[loop].identifier); }
									if(myIdentifiers.includes(id)) { }
									else {
										var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
										var data = '{ '; if(id != "") { data += '"identifier" : "' + id + '"'; } data += ' }';
										$.ajax({
											url: url,
											data : data,
										 	type: "POST",
							     			contentType: "application/json",
											success : function(result) { 
												var teamMember = result;
												var data2 = '{ ';
												if(firstName != "") { data2 += ' "givenName" : "' + firstName + '", '; }
												if(middleName != "") { data2 += ' "middleName" : "' + middleName + '", '; }
												if(lastName != "") { data2 += ' "familyName" : "' + lastName + '" '; }
												data2 += ' }';
												var url2 = "/openmrs/ws/rest/v1/person/"+teamMember.person.uuid+"/name/"+teamMember.person.person.preferredName.uuid;
												$.ajax({
													url: url2,
													data : data2,
												 	type: "POST",
									     			contentType: "application/json",
													success : function(result) { 
														for (var i = 0; i < members.length; i++) {
													    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
													    		if(teamMember.person.person === undefined) { teamMember.person.preferredName = result; if(teamMember.person.preferredName.display.toString() === members[i].person.preferredName.display.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.person.preferredName.display.toString(), members[i].person.preferredName.display.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; } }
																else { teamMember.person.person.preferredName = result; if(teamMember.person.person.preferredName.display.toString() === members[i].person.person.preferredName.display.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.person.person.preferredName.display.toString(), members[i].person.person.preferredName.display.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; } members[i].person.person = teamMember.person.person; }
																members[i].person = teamMember.person;
																if(teamMember.identifier.toString() === members[i].identifier.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.identifier.toString(), members[i].identifier.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; }
																members[i].identifier = teamMember.identifier;
																var tbody = document.getElementById("tbody");
																tbody.innerHTML = ""; GenerateTable(tbody);
																$('#editTeamMemberDiv').dialog('close'); 
													    	}
														}
													}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO PERSON"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
												});
											}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
										});
									}
								}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
							});
				    	}
		    		}
					else {
						var str = "";
						if(otherTeamMemberFirstNames.includes(firstName)) { str += "First Name must be unique."; if(otherTeamMemberMiddleNames.includes(middleName)) { str += " Middle Name must be unique."; } if(otherTeamMemberFamilyNames.includes(lastName)) { str += " Family Name must be unique."; } if(otherTeamMemberIdentifiers.includes(id)) { str += " Identifier must be unique."; } }
						else if(otherTeamMemberMiddleNames.includes(middleName)) { str += "Middle Name must be unique."; if(otherTeamMemberFamilyNames.includes(lastName)) { str += " Family Name must be unique."; } if(otherTeamMemberIdentifiers.includes(id)) { str += " Identifier must be unique."; } }
						else if(otherTeamMemberFamilyNames.includes(lastName)) { str += "Family Name must be unique."; if(otherTeamMemberIdentifiers.includes(id)) { str += " Identifier must be unique."; } }
						else if(otherTeamMemberIdentifiers.includes(id)) { str += "Identifier must be unique."; }
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-ALL-TEAMS"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Reading All Team Members</p>"; }
			}); 
		}
		else if(type === "memberLocationInfo") { 
			var uuid = members[index].uuid;
			var currentLocations = [];
			for(var k=0; k<document.getElementById("teamMemberCurrentLocations"+index).options.length; k++) { currentLocations.push(document.getElementById("teamMemberCurrentLocations"+index).options[k].value); } 
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ "locations" : [ ';
			for(var k = 0; k < currentLocations.length; k++) { if(k === currentLocations.length-1) { data += '{ "uuid":"'+currentLocations[k]+'" }'; } else { data += '{ "uuid":"'+currentLocations[k]+'" }, '; } } 
			data += ' ] }';
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					var teamMember = result;
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
				    		var arr1 = []; for(var k=0; k<teamMember.locations.length; k++) { arr1.push(teamMember.locations[k].name.toString()); }
				    		var arr2 = []; for(var k=0; k<members[i].locations.length; k++) { arr2.push(members[i].locations[k].name.toString()); }
			    			var str1 = "["+arr1.toString()+"]"; var str2 = "["+arr2.toString()+"]";
			    			if(str1 === str2) {  }
			    			else {
			    				if(teamMember.locations.length === 0 && members[i].locations.length === 0) { }
				    			else if(teamMember.locations.length === 0 && members[i].locations.length > 0) { if(str1 === str2) {} else { saveLog("teamMember", members[i].uuid.toString(), str1, str2, "TEAM_MEMBER_LOCATION_REMOVED", ""); } }
				    			else if(teamMember.locations.length > 0 && members[i].locations.length === 0) { if(str1 === str2) {} else { saveLog("teamMember", members[i].uuid.toString(), str1, str2, "TEAM_MEMBER_LOCATION_ADDED", ""); } }
				    			else if(teamMember.locations.length < members[i].locations.length) { if(str1 === str2) {} else { saveLog("teamMember", members[i].uuid.toString(), str1, str2, "TEAM_MEMBER_LOCATION_REMOVED", ""); } }
				    			else if(teamMember.locations.length > members[i].locations.length) { if(str1 === str2) {} else { saveLog("teamMember", members[i].uuid.toString(), str1, str2, "TEAM_MEMBER_LOCATION_ADDED", ""); } }
				    			else { if(str1 === str2) {} else { saveLog("teamMember", members[i].uuid.toString(), str1, str2, "TEAM_MEMBER_LOCATION_CHANGED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Location(s) Updated Successfully</p>"; } }
			    			} members[i].locations = teamMember.locations;
				    		var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							$('#editTeamMemberLocationDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER LOCATION"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Location(s)</p>"; }
			});
		} 
		else if(type === "memberRoleInfo") { 
			var uuid = members[index].uuid;
			var role = document.getElementById("teamMemberRole"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ "teamRole" : "'; if(role != "") { data += role; } data += '" }'; 
			//var data = '{ "teamRole" : "' + role + '" }';
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) {
					var teamMember = result;
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
				    		if(members[i].teamRole !== null && teamMember.teamRole !== null) { if(teamMember.teamRole.name.toString() === members[i].teamRole.name.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.teamRole.name.toString(), members[i].teamRole.name.toString(), "TEAM_MEMBER_TEAM_ROLE_CHANGED", ""); } } else { if(members[i].teamRole === null) { saveLog("teamMember", members[i].uuid.toString(), teamMember.teamRole.name.toString(), "", "TEAM_MEMBER_TEAM_ROLE_CHANGED", ""); } if(teamMember.teamRole === null) { saveLog("teamMember", members[i].uuid.toString(), "", members[i].teamRole.name.toString(), "TEAM_MEMBER_TEAM_ROLE_CHANGED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Role Updated Successfully</p>"; } }
				    		members[i].teamRole = teamMember.teamRole;
				    		var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							$('#editTeamMemberRoleDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER ROLE"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Role</p>"; }
			});
		} 
		else if(type === "memberTeamInfo") {
			var uuid = members[index].uuid;
			var team = document.getElementById("teamMemberTeam"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ "team" : "' + team + '" }';
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) {
					var teamMember = result;
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
				    		if(members[i].team !== null && teamMember.team !== null) { if(teamMember.team.teamName.toString() === members[i].team.teamName.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.team.teamName.toString(), members[i].team.teamName.toString(), "TEAM_MEMBER_TEAM_CHANGED", ""); } } else { var newData = ""; var oldData = ""; if(members[i].team === null) { newData = teamMember.team.teamName.toString(); oldData = ""; } if(teamMember.team === null) { newData = ""; oldData = members[i].team.teamName.toString(); } saveLog("teamMember", members[i].uuid.toString(), newData, oldData, "TEAM_MEMBER_TEAM_CHANGED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Team Updated Successfully</p>"; }
				    		members[i].team = teamMember.team;
				    		var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							$('#editTeamMemberTeamDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER TEAM"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Team</p>"; }
			});
		} 
		else if(type === "memberDataProviderInfo") { 
			var uuid = members[index].uuid;
	    	var dataProvider = document.getElementById("teamMemberDataProvider"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ '; if(dataProvider != "") { data += '"isDataProvider":"' + dataProvider + '" '; } data += ' }'; 
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					var teamMember = result;
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
				    		if(teamMember.isDataProvider.toString() === members[i].isDataProvider.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.isDataProvider.toString(), members[i].isDataProvider.toString(), "TEAM_MEMBER_EDITED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Data Provider Updated Successfully</p>"; }
							members[i].isDataProvider = teamMember.isDataProvider;
							var tbody = document.getElementById("tbody");
							tbody.innerHTML = ""; GenerateTable(tbody);
							$('#editTeamMemberDataProviderDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER VOIDED"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member</p>"; }
			});
		}
		else if(type === "memberVoidedInfo") { 
			var uuid = members[index].uuid;
	    	var voided = document.getElementById("teamMemberVoided"+index).value;
	    	var voidReason = document.getElementById("teamMemberVoidReason"+index).value;
			if(voidReason.length > 255) { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else if(voided === "true" && voidReason === "") { document.getElementById("voidError").innerHTML = "Void Reason must be 255 charachers long"; }
			else {
				var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
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
						var teamMember = result;
						for (var i = 0; i < members.length; i++) {
					    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
					    		if(teamMember.voidReason === null) { teamMember.voidReason = ""; }
					    		if(teamMember.voided.toString()+"-"+teamMember.voidReason.toString() === members[i].voided.toString()+"-"+members[i].voidReason.toString()) {} else { saveLog("teamMember", members[i].uuid.toString(), teamMember.voided.toString()+"-"+teamMember.voidReason.toString(), members[i].voided.toString()+"-"+members[i].voidReason.toString(), "TEAM_MEMBER_VOIDED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Voided Successfully</p>"; }
					    		members[i].voided = teamMember.voided;
					    		members[i].voidReason = teamMember.voidReason;
					    		var tbody = document.getElementById("tbody");
								tbody.innerHTML = ""; GenerateTable(tbody);
								$('#editTeamMemberVoidedDiv').dialog('close'); 
							}
						}
					}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER VOIDED"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Voiding Team Member</p>"; }
				});
			}
		}
	}
	function getSelectedAllLocations(mySelect) {
		var allLocationNames = []; var allLocationIds = []; 
		<c:forEach items="${allLocations}" var="location">allLocationIds.push("${location.uuid}");allLocationNames.push("${location.name}");</c:forEach>
		var option = document.createElement("option"); option.value = mySelect.value; option.text = allLocationNames[allLocationIds.indexOf(mySelect.value)];
	    (document.getElementById("teamMemberCurrentLocations"+(mySelect.id).replace("teamMemberAllLocations", ""))).add(option);
	    mySelect.remove(mySelect.selectedIndex);
	}
	function getSelectedCurrentLocations(mySelect) {
		var allLocationNames = []; var allLocationIds = []; 
		<c:forEach items="${allLocations}" var="location">allLocationIds.push("${location.uuid}");allLocationNames.push("${location.name}");</c:forEach>
		var option = document.createElement("option"); option.value = mySelect.value; option.text = allLocationNames[allLocationIds.indexOf(mySelect.value)];
	    (document.getElementById("teamMemberAllLocations"+(mySelect.id).replace("teamMemberCurrentLocations", ""))).add(option);
	    mySelect.remove(mySelect.selectedIndex);
	}
	function replace(arrayName,replaceTo, replaceWith) { for(var i=0; i<arrayName.length;i++ ) { if(arrayName[i]==replaceTo) { arrayName.splice(i,1,replaceWith); } } }
	function removeDuplicates(arr) { var tmp = []; for(var i = 0; i < arr.length; i++) { if(tmp.indexOf(arr[i]) == -1) { tmp.push(arr[i]); } } return tmp; }
	function getSelectedLocations(select) { 
		var array = []; 
		for (var i = 0; i < select.options.length; i++) { 
			if (select.options[i].selected) { 
				array.push(select.options[i].value); 
			}
		} locations = removeDuplicates(array);
	}
	function remove() {
	    var select = document.getElementById("mySelect");
	    for(var i=0; i<select.options.length; i++) {
	    	for(var j=0; j<locations.length; j++) {
	    		if(select.options.item(i).value === locations[j]) {
				    select.remove(i);
	        	}
	        }
	    }
	}
	function compareArrays(arr_New, arr_Old) {
		if(arr_New.toString() === arr_Old.toString()) { return true; }
		else { return false; }
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
	function setFilters() {
		document.getElementById("filterById").value = my_id;
		document.getElementById("filterBySupervisor").value = my_supervisorId;
		document.getElementById("filterByTeamRole").value = my_teamRoleId;
		document.getElementById("filterByTeam").value = my_teamId;
		document.getElementById("filterByLocation").value = my_locationId;
	}
	function voidReasonView(id) {
		if(document.getElementById("teamMemberVoided"+id).value === "true") { document.getElementById("teamMemberVoidReason"+id).disabled=false; } else { document.getElementById("teamMemberVoidReason"+id).disabled=true; }
	}
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/teamMember.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Team Members</h2><br/>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" align="center" style="color: green">${saved}</h3>
<h3 id="editHead" align="center" style="color: red;">${edit}</h3>

<a href="/openmrs/module/teammodule/teamMemberAddForm.form" style="float: right;" title="Add Team Member"><img src="/openmrs/moduleResources/teammodule/img/plus.png" style=" width: 50px; width: 50px;position: relative; top: -40px; right: 10px; " ></a>

<form id="filterForm" name="filterForm" onsubmit="testForm()">
	<table>
		<tr>
			<td>Filter By: </td>
			<td>
				<input id="filterById" name="filterById" placeholder="Name or Identifier"/>
			</td>
			<td>
				<select id="filterBySupervisor" name="filterBySupervisor">
					<option value="" selected>Select Supervisor</option>
				   	<c:forEach items="${allSupervisors}" var="supervisor">
				    	<option value="${supervisor.teamMemberId}" >${supervisor.person.personName}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeamRole" name="filterByTeamRole">
					<option value="" selected>Select Team Role</option>
				   	<c:forEach items="${allTeamRoles}" var="teamRole">
				    	<option value="${teamRole.teamRoleId}" >${teamRole.name}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeam" name="filterByTeam">
					<option value="" selected>Select Team</option>
				   	<c:forEach items="${allTeams}" var="team">
				    	<option value="${team.teamId}" >${team.teamName}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByLocation" name="filterByLocation">
					<option value="" selected>Select Location</option>
				   	<c:forEach items="${allLocations}" var="location">
				    	<option value="${location.id}" >${location}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<button type="button" id="submitBtn" name="submitBtn">Filter</button>
			</td>
		</tr>
	</table>
</form>

<table class="display" id="general" style="border-left: 1px solid black; border-right: 1px solid black;">
	<thead>
		<tr>
			<openmrs:hasPrivilege privilege="Edit Member">
				<th style="border-top: 1px solid black;">Edit</th>
			</openmrs:hasPrivilege>
			<th style="border-top: 1px solid black;">Identifier<br/>[Name]</th>
			<!-- <th style="border-top: 1px solid black;">Name</th> -->
			<th style="border-top: 1px solid black;">Role</th>
			<th style="border-top: 1px solid black;">Team<br/>[Supervisor]</th>
			<th style="border-top: 1px solid black;">Locations</th>
			<!-- <th style="border-top: 1px solid black;">Sub Roles</th> -->
			<th style="border-top: 1px solid black;">Sub Teams</th>
			<th style="border-top: 1px solid black;">Is Data Provider</th>
			<th style="border-top: 1px solid black;">Voided</th>
			<th style="border-top: 1px solid black;">Patients</th>
			<openmrs:hasPrivilege privilege="View Member">
				<th style="border-top: 1px solid black;">History</th>
			</openmrs:hasPrivilege>
		</tr>
	</thead>
</table>

<div id="historyDialog">
	<table id="history">
		<thead>
			<tr>
				<th>Team Member Name</th>
				<th>Action</th>
				<th>New Data </th>
				<th>Old Data </th>
				<th>Date</th>
			</tr>
		</thead><tbody id="historyBody"></tbody>
	</table>
</div>

<div id="detailTeamMemberDiv"></div>
<div id="editTeamMemberDiv"></div>
<div id="editTeamMemberRoleDiv"></div>
<div id="editTeamMemberTeamDiv"></div>
<div id="editTeamMemberVoidedDiv"></div>
<div id="editTeamMemberDataProviderDiv"></div>
<div id="editTeamMemberLocationDiv"></div>
<div id="teamMemberTeamDetailDiv"></div>
<div id="teamMemberTeamRoleDetailDiv"></div>

<script type="text/javascript"> setFilters(); </script>
<%@ include file="/WEB-INF/template/footer.jsp"%>
