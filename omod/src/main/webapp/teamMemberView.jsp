<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.css"/>
<link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css"/>

<script src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9" type="text/javascript"></script>
<script type="text/javascript" src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/v/dt/dt-1.10.13/datatables.min.js"></script>
<script type="text/javascript" src="https://cdn.datatables.net/1.10.13/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script type="text/javascript">
	var locations = [];
	var members = [];
	var headerArray = ["edit", "Identifier", "Name", "Role", "Team", "Report To", "Locations", "Sub Roles", "Sub Teams", "History", "Voided", "Patients"];
	
	$(document).ready(function() {
		$.ajax({
			url: "/openmrs/ws/rest/v1/team/teammember?get=all&v=full",
			success : function(result) { 
				console.log("SUCCESS-ALL"); 
				console.log(result); 
				members = result.results;
				GenerateTable();
				$('#example').DataTable({"bFilter": false, "ordering": false});
			}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-ALL"); console.log(jqXHR); }
		});
		$("#submitBtn").bind("click", function() {
			var url = "/openmrs/ws/rest/v1/team/teammember?get=filter&v=full";
			var id = document.getElementById("filterById").value;
			var supervisor = document.getElementById("filterBySupervisor").value;
			var role = document.getElementById("filterByTeamRole").value;
			var team = document.getElementById("filterByTeam").value;
			var location = document.getElementById("filterByLocation").value;
			
			if(id != "") { url += "&identifier=" + id; }
			if(supervisor != "") { url += "&supervisor=" + supervisor; }
			if(role != "") { url += "&role=" + role; }
			if(team != "") { url += "&team=" + team; }
			if(location != "") { url += "&location=" + location; }
			$.ajax({
				url: url,
				success : function(result) { 
					console.log(url);
					console.log("SUCCESS-FILTER"); 
					console.log(result); 
					members = result.results;
					GenerateTable();
					//$('#example').DataTable({"bFilter": false});
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
			});
		});
	});
	 
	function GenerateTable() {
		var table = document.getElementById("example");
		table.innerHTML = "";
	    var thead = document.createElement("THEAD")
	    var row = thead.insertRow(-1);

	    for (var i = 0; i < headerArray.length; i++) {
	        var headerCell = document.createElement("TH");
	        headerCell.innerHTML = headerArray[i];
	        headerCell.setAttribute("style", "border: 1px solid");
	        row.appendChild(headerCell);
	    }
	    var tbody = document.createElement("TBODY");
    	if(members.length > 0) {
    	    for (var i = 0; i < members.length; i++) {
		    	row = tbody.insertRow(-1);
		    	row.id = "memberRow"+i;
		        /* Edit */
		    	var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        cell.innerHTML = "<a id='editTeamMemberLink' name='editTeamMemberLink' title='Edit Team Member' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
		        /* Identifier */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        cell.innerHTML = members[i].identifier;
		        /* Name */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
				if(members[i].person.person === undefined) { cell.innerHTML = members[i].person.display; } else { cell.innerHTML = members[i].person.person.display; }
		        /* Role */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        cell.innerHTML = members[i].teamRole.display + "<a id='editTeamMemberRoleLink' name='editTeamMemberRoleLink' title='Edit Team Member Role' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberRoleInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
		        /* Team */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
				cell.innerHTML = members[i].team.display + "<a id='editTeamMemberTeamLink' name='editTeamMemberTeamLink' title='Edit Team Member Team' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberTeamInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";
		        /* Report To */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
				if(members[i].person.person === undefined) { if(members[i].team.supervisor == members[i].person.display) { cell.innerHTML = ""; } else { cell.innerHTML = members[i].team.supervisor; } } else { if(members[i].team.supervisor == members[i].person.person.display) { cell.innerHTML = ""; } else { cell.innerHTML = members[i].team.supervisor; } }
		        /* Locations */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        var locationName = "";
		    	for(var j=0; j<members[i].locations.length; j++) { if(j === members[i].locations.length-1) { locationName += members[i].locations[j].name; } else { locationName += members[i].locations[j].name + "<br>"; } }
		    	cell.innerHTML = locationName;// + "<a id='editTeamMemberLocationLink' name='editTeamMemberLocationLink' title='Edit Team Member Location' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberLocationInfo"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";;
		        /* Sub Ordinate Roles */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        if(members[i].subTeamRoles === "") { cell.innerHTML = ""; } else { cell.innerHTML = members[i].subTeamRoles + " (" + members[i].subTeamRolesCount +")"; }
		        /* Sub Ordinate Teams */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        if(members[i].subTeams === "") { cell.innerHTML = ""; } else { cell.innerHTML = members[i].subTeams + " (" + members[i].subTeamsCount +")"; }
		        /* History */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        cell.innerHTML = '<a href="/openmrs/module/teammodule/memberHistory.form?personId='+members[i].person.uuid+'">History</a>';
		        /* Voided */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        cell.innerHTML = members[i].voided + "<a id='editTeamMemberVoidedLink' name='editTeamMemberVoidedLink' title='Void Team Member' style='cursor:pointer' onclick='editTeamMember(\""+members[i].uuid+"\",\""+"memberVoided"+"\");'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%; float: right;' ></a>";;
		        /* Patients */
		        var cell = row.insertCell(-1);
		    	cell.setAttribute("style", "border: 1px solid");
		        if(members[i].patients.length>0) cell.innerHTML = members[i].patients.length; else cell.innerHTML = "";
	    	}
	    }
    	else {
	    	row = tbody.insertRow(-1);
	        var cell = row.insertCell(-1);
	    	cell.colSpan= headerArray.length
    		cell.innerHTML = "<strong>No Records Found</strong>";
	    	cell.setAttribute("style", "border: 1px solid; text-align: center;");
    	}
	    table.appendChild(thead);
	    table.appendChild(tbody);
	}
    function editTeamMemberClose(index, type) { 
		if(type==="memberRoleInfo") { $('#editTeamMemberRoleDiv').dialog('close'); } 
		else if(type==="memberTeamInfo") { $('#editTeamMemberTeamDiv').dialog('close'); } 
		else if(type==="memberInfo") { $('#editTeamMemberDiv').dialog('close'); } 
		else if(type==="memberVoided") { $('#editTeamMemberVoidedDiv').dialog('close'); } 
		else if(type==="memberLocationInfo") { $('#editTeamMemberLocationDiv').dialog('close'); } 
	}
	function editTeamMemberSuccess(index, type) {
		if(type==="memberLocationInfo") { 
			/* var uuid = members[index].uuid;
			var role = document.getElementById("teamMemberRole"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid+"/role";
			var data = '{ '; if(role != "") { data += '"role" : "' + role + '"'; } data += ' }';
			console.log(url);
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER ROLE");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							//$('#example').DataTable({"bFilter": false});
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Role Updated Successfully</p>"; 
							$('#editTeamMemberRoleDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER ROLE"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Role</p>"; }
			}); */
		} 
		else if(type==="memberRoleInfo") { 
			var uuid = members[index].uuid;
			var role = document.getElementById("teamMemberRole"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid+"/role";
			var data = '{ '; if(role != "") { data += '"role" : "' + role + '"'; } data += ' }';
			console.log(url);
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER ROLE");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							//$('#example').DataTable({"bFilter": false});
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Role Updated Successfully</p>"; 
							$('#editTeamMemberRoleDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER ROLE"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Role</p>"; }
			});
		} 
		else if(type==="memberTeamInfo") { 
			var uuid = members[index].uuid;
			var team = document.getElementById("teamMemberTeam"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid+"/team";
			var data = '{ '; if(team != "") { data += '"team" : "' + team + '"'; } data += ' }';
			console.log(url);
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER TEAM");
					console.log(result); 
					var teamMember = result.results[0];
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							//$('#example').DataTable({"bFilter": false});
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Team Updated Successfully</p>"; 
							$('#editTeamMemberTeamDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER TEAM"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Team</p>"; }
			});
		} 
		else if(type==="memberVoided") { 
			var uuid = members[index].uuid;
	    	var voided = document.getElementById("teamMemberVoided"+index).value;
	    	var voidReason = document.getElementById("teamMemberVoidReason"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ '; 
			if(voided != "") { data += '"voided":"' + voided + '", '; } 
			if(voidReason != "") { data += '"voidReason":"' + voidReason + '"'; } 
			data += ' }';
			console.log(data); 
			console.log(url); 
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER VOIDED");
					console.log(result); 
					var teamMember = result;
					console.log(teamMember);
					for (var i = 0; i < members.length; i++) {
				    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
							replace(members, members[i], teamMember);
							console.log(members);
							GenerateTable();
							//$('#example').DataTable({"bFilter": false});
							document.getElementById("errorHead").innerHTML = ""; 
							document.getElementById("saveHead").innerHTML = "<p>Team Member Voided Successfully</p>"; 
							$('#editTeamMemberVoidedDiv').dialog('close'); 
						}
					}
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER VOIDED"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Voiding Team Member</p>"; }
			});
		}
		else if(type==="memberInfo") { 
			var uuid = members[index].uuid;
	    	var id = document.getElementById("teamMemberIdentifier"+index).value;
	    	var firstName = document.getElementById("teamMemberPersonFirstName"+index).value;
	    	var middleName = document.getElementById("teamMemberPersonMiddleName"+index).value;
	    	var lastName = document.getElementById("teamMemberPersonLastName"+index).value;
			var url = "/openmrs/ws/rest/v1/team/teammember/"+uuid;
			var data = '{ '; if(id != "") { data += '"identifier" : "' + id + '"'; } data += ' }';
			$.ajax({
				url: url,
				data : data,
			 	type: "POST",
     			contentType: "application/json",
				success : function(result) { 
					console.log("SUCCESS-EDIT TEAM MEMBER INFO");
					console.log(result); 
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
							console.log("SUCCESS-EDIT TEAM MEMBER INFO PERSON");
							console.log(result); 
							console.log(teamMember);
							for (var i = 0; i < members.length; i++) {
						    	if(members[i].uuid.toString() === teamMember.uuid.toString()) {
									replace(members, members[i], teamMember);
									console.log(members);
									GenerateTable();
									//$('#example').DataTable({"bFilter": false});
									document.getElementById("errorHead").innerHTML = ""; 
									document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>"; 
									$('#editTeamMemberDiv').dialog('close'); 
						    	}
							}
						}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO PERSON"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
					});
				}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
			});
		} 
	}
	function editTeamMember(uuid, type) {
	    for (var i = 0; i < members.length; i++) {
	    	if(members[i].uuid.toString() === uuid.toString()) {
	    		if(type==="memberLocationInfo") {
					var allLocationNames = []; var allLocationIds = []; 
					<c:forEach items="${allLocations}" var="location">allLocationIds.push("${location.id}");allLocationNames.push("${location.name}");</c:forEach>
	    			var html = "<table><form><tr><td>Member Locations: </td><td><select id='teamMemberCurrentLocations"+i+"' name='teamMemberCurrentLocations"+i+"' multiple onchange='getSelectedCurrentLocations(this)' size='"+members[i].locations.length+"'>"
		    		for (var j = 0; j < members[i].locations.length; j++) {
		    			html += "<option value='"+members[i].locations[j].uuid+"'>"+members[i].locations[j].display+"</option>";
		    		} html += "</select></td></tr><tr><td colspan='2'>REMOVE</td></tr>";
		    		html += "</form></table>";
					/* var html = "<table><form><tr><td>Locations: </td><td><select id='teamMemberLocations"+i+"' name='teamMemberLocations"+i+"' multiple onchange='getSelectedLocations(this)'>";
		    		for (var j = 0; j < allLocationNames.length; j++) {
		    			if(members[i].locations.includes(allLocationNames[j].toString())) { 
		    				html += "<option value='"+allLocationIds[j]+"' selected >"+allLocationNames[j]+"</option>"; 
	    				} 
		    			else { 
		    				html += "<option value='"+allLocationIds[j]+"'>"+allLocationNames[j]+"</option>";
	    				}
	    	    	} html += "</select></td></tr><tr><td><button type='button' id='teamMemberLocationEditClose' name='teamMemberLocationEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberLocationInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberLocationEditSuccess' name='teamMemberLocationEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberLocationInfo"+"\");' >Save</button></td></tr></form></table>";
	    			 */document.getElementById("editTeamMemberLocationDiv").innerHTML = html;
		    		$("#editTeamMemberLocationDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Location" , closeText: ""});
	    		}
	    		else if(type==="memberRoleInfo") {
					var allTeamRoleNames = []; var allTeamRoleIds = []; 
					<c:forEach items="${allTeamRoles}" var="teamRole">allTeamRoleIds.push("${teamRole.teamRoleId}");allTeamRoleNames.push("${teamRole.name}");</c:forEach>
	    			var html = "<table><form><tr><td>Role: </td><td><select id='teamMemberRole"+i+"' name='teamMemberRole"+i+"'>";
		    		for (var j = 0; j < allTeamRoleNames.length; j++) {
		    			if(allTeamRoleNames[j].toString() === members[i].teamRole.toString()) { html += "<option value='"+allTeamRoleIds[j]+"' selected >"+allTeamRoleNames[j]+"</option>"; } 
		    			else { html += "<option value='"+allTeamRoleIds[j]+"'>"+allTeamRoleNames[j]+"</option>"; }
	    	    	} html += "</select></td></tr><tr><td><button type='button' id='teamMemberRoleEditClose' name='teamMemberRoleEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberRoleInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberRoleEditSuccess' name='teamMemberRoleEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberRoleInfo"+"\");' >Save</button></td></tr></form></table>";
	    			document.getElementById("editTeamMemberRoleDiv").innerHTML = html;
		    		$("#editTeamMemberRoleDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Team Role" , closeText: ""});
	    		}
	    		else if(type === "memberTeamInfo") {
		    	    var allTeamNames = []; var allTeamIds = [];  
		    	    <c:forEach items="${allTeams}" var="team">allTeamIds.push("${team.teamId}"); allTeamNames.push("${team.teamName}");</c:forEach>
	    			var html = "<table><form><tr><td>Team: </td><td><select id='teamMemberTeam"+i+"' name='teamMemberTeam"+i+"'>";
		    		for (var j = 0; j < allTeamNames.length; j++) {
		    			if(allTeamNames[j].toString() === members[i].team.display.toString()) { html += "<option value='"+allTeamIds[j]+"' selected >"+allTeamNames[j]+"</option>"; }
		    			else { html += "<option value='"+allTeamIds[j]+"' >"+allTeamNames[j]+"</option>"; }
		    		} html += "</select></td></tr><tr><td><button type='button' id='teamMemberTeamEditClose' name='teamMemberTeamEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberTeamInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberTeamEditSuccess' name='teamMemberTeamEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberTeamInfo"+"\");' >Save</button></td></tr></form></table>";
					document.getElementById("editTeamMemberTeamDiv").innerHTML = html;
		    		$("#editTeamMemberTeamDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Team" , closeText: ""});
	    		}
	    		else if(type === "memberInfo") {
	    			var given = "", middle = "", last = "";
	    			if(members[i].person.person === undefined) { given = members[i].person.preferredName.givenName; middle = members[i].person.preferredName.middleName; last = members[i].person.preferredName.familyName; } 
	    			else { given = members[i].person.person.preferredName.givenName; middle = members[i].person.person.preferredName.middleName; last = members[i].person.person.preferredName.familyName; }

	    			var html = "<table><form><tr><td>Identifier: </td><td><input type'text' id='teamMemberIdentifier"+i+"' name='teamMemberIdentifier"+i+"' value='"+members[i].identifier+"'></td></tr><tr><td>Name: </td><td><input type'text' id='teamMemberPersonFirstName"+i+"' name='teamMemberPersonFirstName"+i+"' value='"+given+"'></tr><tr><td>Middle Name: </td><td><input type'text' id='teamMemberPersonMiddleName"+i+"' name='teamMemberPersonMiddleName"+i+"' value='"+middle+"'></tr><tr><td>Family Name: </td><td><input type'text' id='teamMemberPersonLastName"+i+"' name='teamMemberPersonLastName"+i+"' value='"+last+"'></td></tr><tr><td><button type='button' id='teamMemberEditClose' name='teamMemberEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberInfo"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberEditSuccess' name='teamMemberEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberInfo"+"\");' >Save</button></td></tr></form></table>";
	    			document.getElementById("editTeamMemberDiv").innerHTML = html;
		    		$("#editTeamMemberDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Edit" , closeText: ""});
	    		}
	    		else if(type === "memberVoided") {
	    			var html = "<table><form><tr><td>Voided: </td><td><select id='teamMemberVoided"+i+"' name='teamMemberVoided"+i+"'>";
	    			if(members[i].voided == true) { html += "<option value='"+members[i].voided+"' selected >"+members[i].voided+"</option><option value='false' >false</option>"; }
	    			else if(members[i].voided == false) { html += "<option value='true' >true</option><option value='"+members[i].voided+"' selected >"+members[i].voided+"</option>"; }
	    			html += "</select></td></tr><tr><td>Void Reason: </td><td><textarea id='teamMemberVoidReason"+i+"' name='teamMemberVoidReason"+i+"' value='"+members[i].voidReason+"'></textarea></tr><tr><td><button type='button' id='teamMemberVoidedEditClose' name='teamMemberVoidedEditClose' onclick='editTeamMemberClose(\""+i+"\",\""+"memberVoided"+"\");' >Cancel</button></td><td><button type='button' id='teamMemberVoidedEditSuccess' name='teamMemberVoidedEditSuccess' onclick='editTeamMemberSuccess(\""+i+"\",\""+"memberVoided"+"\");' >Save</button></td></tr></form></table>";	    			
	    			document.getElementById("editTeamMemberVoidedDiv").innerHTML = html;
		    		$("#editTeamMemberVoidedDiv").dialog({ width: "auto", height: "auto", title: "Team Member - Void" , closeText: ""});
	    		}
	    	}
	    }
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
</script>

<h2 align="center">Team Members</h2><br>
<h3 id="errorHead" name="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" name="saveHead" align="center" style="color: green">${saved}</h3>

<form>
	<table>
		<tr>
			<td>Filter By: </td>
			<td>
				<input id="filterById" name="filterById" placeholder="name, identifier"/>
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

<a href="/openmrs/module/teammodule/teamMemberAddForm.form" style="float: right;">Add New Member</a>
<br/>

<table id="example"></table>
<br>
<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>

<div id="editTeamMemberDiv"></div>
<div id="editTeamMemberRoleDiv"></div>
<div id="editTeamMemberTeamDiv"></div>
<div id="editTeamMemberVoidedDiv"></div>
<div id="editTeamMemberLocationDiv"></div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
