<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Member" otherwise="/login.htm" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery-ui.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/css/jquery.dataTables.min.css">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css">

<style type="text/css">
	#history, #teamMemberTeamDetail, #teamMemberDetail, #teamMemberRoleDetail {
	    /* border: 1px solid black; */
	    border-collapse: collapse;
	    width: 100%;
	}
	#history th, #history td, #teamMemberTeamDetail th, #teamMemberTeamDetail td, #teamMemberShowSubTeams th, #teamMemberShowSubTeams td,
	#teamMemberRoleDetail th, #teamMemberRoleDetail td, #teamMemberDetail th, #teamMemberDetail td {
	    /* border: 1px solid black; */
	    align: left;
	    font-size: 14px;
	    padding: 10px;
	    
	}
	#history tr:hover, #teamMemberRoleDetail tr:hover, #teamMemberTeamDetail tr:hover, #teamMemberDetail tr:hover, #teamMemberShowSubTeams tr:hover {
		background-color: #f5f5f5;
	}
	#history tr:nth-child(odd), #teamMemberTeamDetail tr:nth-child(odd), #teamMemberDetail tr:nth-child(odd), #teamMemberRoleDetail tr:nth-child(odd), #teamMemberShowSubTeams tr:nth-child(odd){
		background-color: #f5f5f5;
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
	
	function filter()
	{
		var id= $("#filterById").val()
		var supervisor =$("#filterBySupervisor").val();
		var role =$("#filterByTeamRole").val();
		var team =$("#filterByTeam").val();
		var location =$("#filterByLocation").val();	
		var param="";
		if(id != "") { param += "&nameOrIdentifier=" + id; }
		if(supervisor != "") { param += "&supervisor=" + supervisor; }
		if(role != "") { param += "&role=" + role; }
		if(team != "") { param += "&team=" + team; }
		if(location != "") { param += "&location=" + location; }
		$("#general").dataTable().fnDestroy();
		CreateTable(param)
	}
	$(document).ready(function() {
		$('#historyDialog').hide();
		$("#teamMemberDetailDiv").hide();
		$("#teamMemberTeamDetailDiv").hide();
		$("#teamMemberShowSubTeamsDiv").hide();
		$("#teamMemberRoleDetailDiv").hide();
		$("#editTeamMemberVoidedDiv").hide();
		$("#editTeamMemberDataProviderDiv").hide();
		$("#editTeamMemberTeamDiv").hide();
		$("#editTeamMemberTeamRoleDiv").hide();
		$("#editTeamMemberLocationDiv").hide();
		$("#editTeamMemberDiv").hide();
		var param="";
		var team="${team}";
		var role="${role}";
		if(team!="")
		{ 
			param += "&team=" + team;
			$("#filterByTeam").val(team);
			CreateTable(param)
			return;
		}
		if(role!="")
		{ 
			param += "&role=" + role;
			$("#filterByTeamRole").val(role);
			CreateTable(param)
			return;
		}
		CreateTable("")
	});
	 
	function CreateTable(params)
	{
		var tbody=null,table=null,dataTable=null;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
		tbody.id= "tbody";
		var  data = $.get("/openmrs/ws/rest/v1/team/teammember?v=full"+params, function(teamMemberData) {
			teamMembers = teamMemberData.results; 
			table.appendChild(tbody);
			dataTable=$('#general').DataTable({
				"data": teamMembers,
				"language" : {
					"search" : "_INPUT_",
					"searchPlaceholder" : "Search..."
				},
				"columns": [
		            { "data": "uuid",
		              "render": function ( data, type, row, meta) {
		                return "<a title='Edit Team Role' " + " onclick='editTeamMemberInfo(\"" + data + "\");' >"
			                	+"<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png' style='padding-right: 40%;'></a>";
		            	}
		            },
		            { "data": "identifier",
		            	"render": function ( data, type, row, meta) {
		                return "<a title='Team Role Detail' style='cursor: pointer;'"+
			                	" onclick='teamMemberDetail(\""	+ row.uuid	+ "\");' >"+data+"</a>";
		            	}
		            },
		            { "data": "display" },
		            { "data": "teamRole",
			              "render": function ( data, type, row, meta) {
			                if(data)
							{
			                	return "<a title='Team Role Detail' style='cursor: pointer;'"+
			                		" onclick='roleDetail(\""+ data.uuid	+ "\");' >"+data.name+"</a>"
			                		+ "<a title='Edit Team Role Report To'"
			                		+ " onclick='editTeamRoleInfo(\""+row.uuid+"\",\""+ data.uuid+ "\");'>"
									+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
							}
			            		return "<a title='Edit Team Role Report To'"+
									" onclick='editTeamRoleInfo(\""+row.uuid+"\",\"\");'>"
									+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
							}
			        },
			        { "data": "team",
			              "render": function ( data, type, row, meta) {
			                var view="";
			            	if(data)
							{
			            		view+="<li>"+"<a title='Team Detail' style='cursor: pointer;'"+
		        				" onclick='teamDetail(\""+ data.uuid+ "\")'>" + data.teamName + "</a></li>";
							}
			            		return view+"<a title='Edit Team Role Report To'"+
									" onclick='editTeamMemberTeamInfo(\""+ row.uuid+ "\",\""+ data.uuid+ "\");'>"
									+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
							}
			        },
			        { "data": "uuid",
		              "render": function ( data, type, row, meta) {
		                if(data)
						{
		                	return row.locations[0].name+ "<a title='Edit Team Member Location'"+
								" onclick='editTeamMemberLocationInfo(\""+ data+ "\",\""+ row.locations[0].uuid+ "\");'>"
								+" <img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";
						}
		            	}
		            },
		            { "data": "subTeams",
			              "render": function ( data, type, row, meta) {
			                var view="";
			            	if(data)
							{	
			            		view+="<ul><li>"+"<a title='Show SubTeams' style='cursor: pointer;'"+
			                		" onclick='showSubTeams(\""+ row.uuid+ "\")'>" + data.length + " SubTeams</a></li> </ul>"	                	
							}
			            	else
			            	{
			            		view="<center>-</center>"	
			            	}
			            		return view
							}
			        },
			        { "data": "isDataProvider",
			        	"render": function(data,type,row,meta){
		            		return data
							+ "<a title='Void Team Role' class='table-action-link'"+
							"onclick='editTeamMemberDataProviderInfo(\""+ row.uuid+ "\");'>"
							+"<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";	
		            	}
			        },
		            { "data": "voided",
		            	"render": function(data,type,row,meta){
		            		return data
							+ "<a title='Void Team Role' class='table-action-link'"+
							"onclick='editTeamMemberVoidedInfo(\""+ row.uuid+ "\");'>"
							+"<img id='images' src='/openmrs/moduleResources/teammodule/img/edit.png'></a>";	
		            	}
		            },
		            { "data": "patients",
		            	"render": function(data, type, row, meta){
		            			if(data!="")
		            				return "<a title='Team Role Members' style='cursor:pointer' onclick='viewMember(\""+ row.uuid+ "\");'>"
		            				+ data + " Patients</a>"
		            			else
		            				return "<center>-</center>"
			            	}
		            },
		            { "data": "uuid",
		            	"render": function(data,type,row,meta){
		            		return "<a title='Team Role History' onClick='teamMemberHistory(\""+ row.uuid + "\")'>"
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
	function editTeamMemberInfo(uuid) {
		var given = "", middle = "", last = "";
		$.get("/openmrs/ws/rest/v1/team/teammember/" + uuid + "?v=full",function(teammember) {
			if (teammember.person.preferredName !== null) {
				$('#teamMemberPersonFirstName').val(teammember.person.preferredName.givenName);
				$('#teamMemberPersonMiddleName').val(teammember.person.preferredName.middleName);
				$('#teamMemberPersonLastName').val(teammember.person.preferredName.familyName);
			}
			$('#teamMemberUuid').val(uuid);
			$('#teamMemberPersonUuid').val(teammember.person.uuid);
			$('#teamMemberIdentifier').val(teammember.identifier);
			$("#editTeamMemberDiv").dialog({
				width : "500px",
				height : "auto",
				title : "Team Member - Edit",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}
	function teamMemberInfoSubmit()
	{
		var firstName=$('#teamMemberPersonFirstName').val();
		var middleName=$('#teamMemberPersonMiddleName').val();
		var lastName=$('#teamMemberPersonLastName').val();
		var identifier=$('#teamMemberIdentifier').val();
		var personUuid=$('#teamMemberPersonUuid').val();
		var memberUuid=$('#teamMemberUuid').val();
		var otherTeamMemberFirstNames = []; var otherTeamMemberMiddleNames = []; var otherTeamMemberFamilyNames = []; var otherTeamMemberIdentifiers = [];
		var data={};
		jQuery.ajax({
			url: "/openmrs/ws/rest/v1/team/teammember/"+memberUuid,
			success : function(test) { var otherTeamMembers = test.results; 

			if(identifier != "") {
				data.identifier = identifier
			}
			data = JSON.stringify(data);
			$.ajax({
				url: "/openmrs/ws/rest/v1/team/teammember/"+memberUuid,
				data : data,
			 	type: "POST",
		    	contentType: "application/json",
				success : function(result) { 
					var teamMember = result;
					var data1 = {};
					if(firstName != "") { data1.givenName=firstName; }
					if(middleName != "") { data1.middleName=middleName; }
					if(lastName != "") { data1.familyName=lastName; }
					var url1 = "/openmrs/ws/rest/v1/person/"+test.person.uuid+"/name/";
					if(test.person.person === undefined) { url1 += test.person.preferredName.uuid; } 
					else { url1 += teamMember.person.person.preferredName.uuid; }
					data1=JSON.stringify(data1);
					$.ajax({
						url: url1,
						data : data1,
					 	type: "POST",
						contentType: "application/json",
						success : function(result1) { 
							saveLog("teamMember",memberUuid,result.display,result.display,"TEAM_MEMBER_EDITED","");
							document.getElementById("errorHead").innerHTML = "";
							document.getElementById("saveHead").innerHTML = "<p>Team Member Information Updated Successfully</p>";
							$("#editTeamMemberDiv").dialog('close')
							$("#general").dataTable().fnDestroy();
							CreateTable("");
							setTimeout(
							function() {
								document.getElementById("saveHead").innerHTML = ""
							}, 5000);}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO PERSON"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
							});
						}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-EDIT TEAM MEMBER INFO"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Information</p>"; }
					});
				}
		});
	}
	
	function teamMemberHistory(teamMemberUuid) {
		$.get("/openmrs/ws/rest/v1/team/teammemberlog?v=full&q=" + teamMemberUuid, function(data) {
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
	
	$("#historyDialog").dialog({
			width : "auto",
			height : "400",
			title : "Team Member - History",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}
	
    function showSubTeams(uuid)
    {
    	$.get("/openmrs/ws/rest/v1/team/teammember/" + uuid + "?v=full",function(teammember) {
			var subTeams=teammember.subTeams;
			var view=""
   			for(var i=0;i<subTeams.length;i++)
   			{	
   				view+="<tr>"
   	   			console.log(subTeams[i].teamName)
   				view+="<td>"+"<a title='SubTeams' style='cursor: pointer;''"+
   				" onclick='teamDetail(\""+ subTeams[i].uuid+ "\")'>" + subTeams[i].teamName + "</a></td>";
   				view+="</tr>"
   			}
			$("#teamMemberSubTeams").children("tr").remove();
   			$("#teamMemberSubTeams").append(view);
        			
    		$("#teamMemberShowSubTeamsDiv").dialog({
				width : "500px",
				height : "auto",
				title : "SubTeams",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
    	});
    }
	function teamDetail(uuid) {
		$.get("/openmrs/ws/rest/v1/team/team/" + uuid + "?v=full",function(team) {
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
				$('#teamMemberTeamDetail tr td').children('snap').remove();
				$("#teamMemberTeamDetailIdentifier").append("<snap>" + team.teamIdentifier + "</snap>");
				$("#teamMemberTeamDetailName").append("<snap>" + team.teamName + "</snap>");
				$("#teamMemberTeamDetailCurrentSupervisor").append("<snap>" + supervisor + "</snap>");
				$("#teamMemberTeamDetailSupervisorTeam").append("<snap>" + team.supervisorTeam + "</snap>");
				$("#teamMemberTeamDetailLocation").append("<snap>" + team.location.name + "</snap>");
				$("#teamMemberTeamDetailNumberOfMember").append("<snap>" + team.members + "</snap>");
				$("#teamMemberTeamDetailVoided").append("<snap>" + team.voided + "</snap>");
				$("#teamMemberTeamDetailVoidReason").append("<snap>" + team.voidReason + "</snap>");
				$("#teamMemberTeamDetailDateCreated").append("<snap>" + dateCreated + "</snap>");
				$("#teamMemberTeamDetailDateChanged").append("<snap>" + dateChanged + "</snap>");
				
				$("#teamMemberTeamDetailDiv").dialog({
					width : "500px",
					height : "auto",
					title : "SubTeam Detail",
					closeText : "",
					modal : true,
					open : onDialogOpen
				});
						});
	}

	function teamMemberDetail(uuid) {
		console.log(uuid)
		$.get("/openmrs/ws/rest/v1/team/teammember/" + uuid + "?v=full",function(teamMember) {
			var supervisor = "", subTeams = "", subTeamRoles = "";
			console.log(teamMember)
			if (teamMember.team === null) {
				supervisor = "";
			} else {
				if (teamMember.team.supervisor === null)
					supervisor = "";
				else
					supervisor = teamMember.team.supervisor.display;
			}
			if (teamMember.subTeams === null) {
				subTeams = "";
			} else {
				subTeams = "<ul>"
				for (var j = 0; j < teamMember.subTeams.length; j++)
					subTeams += "<li>"+ teamMember.subTeams[j].teamName+ "</li>";
				subTeams += "</ul>"
			}
			if (teamMember.subTeamRoles === null) {
				subTeams = "";
			} else {
				subTeamRoles = "<ul>"
				for (var j = 0; j < teamMember.subTeamRoles.length; j++)
					subTeamRoles += "<li>"+ teamMember.subTeamRoles[j].display+ "</li>";
				subTeamRoles += "</ul>"
			}

			if (teamMember.auditInfo != null) {

				if (teamMember.auditInfo.dateCreated === null)
					dateCreated = "";
				else
					dateCreated = teamMember.auditInfo.dateCreated.toString().substr(0, 10);
				if (teamMember.auditInfo.dateChanged === null)
					dateChanged = "";
				else
					dateChanged = teamMember.auditInfo.dateChanged.toString().substr(0, 10);
			}

			$('#teamMemberDetail tr td').children('snap').remove();
			$('#teamMemberDetail tr td').children('ul').remove();
			$("#teamMemberDetailIdentifier").append("<snap>" + teamMember.identifier + "</snap>");
			$("#teamMemberDetailName").append("<snap>" + teamMember.display + "</snap>");
			$("#teamMemberDetailLocation").append("<snap>" + teamMember.locations[0].display + "</snap>");
			$("#teamMemberDetailCurrentTeam").append("<snap>" + teamMember.team.teamName + "</snap>");
			$("#teamMemberDetailCurrentSupervisor").append("<snap>" + supervisor + "</snap>");
			$("#teamMemberDetailSubTeams").append(subTeams);
			$("#teamMemberDetailSubRoles").append(subTeamRoles);
			$("#teamMemberDetailVoided").append("<snap>" + teamMember.voided + "</snap>");
			$("#teamMemberDetailVoidReason").append("<snap>" + teamMember.voidReason + "</snap>");
			$("#teamMemberDetailDateCreated").append("<snap>" + dateCreated + "</snap>");
			$("#teamMemberDetailDateChanged").append("<snap>" + dateChanged + "</snap>");

			$("#teamMemberDetailDiv").dialog({
				width : "500px",
				height : "450",
				title : "Team Member Detail",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}
	function roleDetail(uuid)
	{
		$.get("/openmrs/ws/rest/v1/team/teamrole/"+uuid+"?v=full", function(role) {
			var reportTo = "", reportBy = "", dateCreated = "", dateChanged = "";
			if (role.reportTo === null)
			{
				reportTo = "";
			}else
			{
				reportTo = role.reportTo.name;
			}
			if (role.reportByName !== null) {
				for (var j = 0; j < role.reportByName.length; j++) {
					if (j === role.reportByName.length - 1) 
						reportBy += role.reportByName[j];
					else 
						reportBy += role.reportByName[j] + ", ";
				}
			}
			else {
				reportBy = "";
			}
			if (role.auditInfo != null) {
			
				if (role.auditInfo.dateCreated === null)
					dateCreated = "";
				else
					dateCreated = role.auditInfo.dateCreated.toString().substr(0, 10);
				if (role.auditInfo.dateChanged === null)
					dateChanged = "";
				else
					dateChanged = role.auditInfo.dateChanged.toString().substr(0, 10);
			}
			$('#teamMemberRoleDetail tr td').children('snap').remove();
			$("#teamMemberRoleDetailIdentifier").append("<snap>"+role.identifier+"</snap>");
			$("#teamMemberRoleDetailName").append("<snap>"+role.name+"</snap>");
			$("#teamMemberRoleDetailOwnsTeam").append("<snap>"+role.ownsTeam+"</snap>");
			$("#teamMemberRoleDetailReportTo").append("<snap>"+reportTo+"</snap>");
			$("#teamMemberRoleDetailReportby").append("<snap>"+reportBy+"</snap>");
			$("#teamMemberRoleDetailNumberOfMember").append("<snap>"+role.members+"</snap>");
			$("#teamMemberRoleDetailVoided").append("<snap>"+role.voided+"</snap>");
			$("#teamMemberRoleDetailVoidReason").append("<snap>"+role.voidReason+"</snap>");
			$("#teamMemberRoleDetailDateCreated").append("<snap>"+dateCreated+"</snap>");
			$("#teamMemberRoleDetailDateChanged").append("<snap>"+dateChanged+"</snap>"); 
			$("#teamMemberRoleDetailDiv").dialog({
				width : "auto",
				height : "auto",
				title : "Team Role Detail",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
		});
	}
	function editTeamRoleInfo(memberUuid,roleUuid)
	{
		$.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(data) {
		var roles=data.results;
		$('#editTeamMemberTeamRolesSelect').children('option').remove();
		
		for(var i=-0;i<roles.length;i++)
		{
			$("#editTeamMemberTeamRolesSelect").append("<option value='"+roles[i].uuid+"'>"+roles[i].name+"</option>"); 	
		}
		$("#editTeamMemberTeamRolesSelect").val(roleUuid); 	
		$("#editTeamMemberTeamRolesUuid").val(memberUuid); 	
		
		$("#editTeamMemberTeamRoleDiv").dialog({
			width : "auto",
			height : "auto",
			title : "Team Role Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
		});
	}
	
	function teamMemberTeamRolesInfoSubmit()
	{
		var uuid = $("#editTeamMemberTeamRolesUuid").val();
		var role = $("#editTeamMemberTeamRolesSelect").val(); 	
		var data = {};
		if (role != "") {
			data.teamRole = role;
		}
		data= JSON.stringify(data);;
				
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/teammember/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamMember = result;
				console.log(teamMember)
				saveLog("teamMember", uuid, teamMember.teamRole.name, teamMember.teamRole.name, "TEAM_MEMBER_TEAM_ROLE_CHANGED","");
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Member Role Updated Successfully</p>";
				$('#editTeamMemberTeamRoleDiv').dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable("");
				setTimeout(
				function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM MEMBER ROLE");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Role</p>";
			}
		});
			} 
	function editTeamMemberTeamInfo(memberUuid,teamUuid)
	{
		$.get("/openmrs/ws/rest/v1/team/team?v=full", function(data) {
			var teams=data.results;
			
			$('#editTeamMemberTeamsSelect').children('option').remove();
			for(var i=-0;i<teams.length;i++)
			{
				$("#editTeamMemberTeamsSelect").append("<option value='"+teams[i].uuid+"'>"+teams[i].teamName+"</option>"); 	
			}
			$("#editTeamMemberTeamsSelect").val(teamUuid); 	
			$("#editTeamMemberTeamsUuid").val(memberUuid); 	
			
			$("#editTeamMemberTeamDiv").dialog({
				width : "auto",
				height : "auto",
				title : "Edit Team Member Location",
				closeText : "",
				modal : true,
				open : onDialogOpen
			});
			});
	}
	
	function teamMemberTeamsInfoSubmit()
	{
		var uuid = $("#editTeamMemberTeamsUuid").val();
		var team = $("#editTeamMemberTeamsSelect").val(); 	
		var data = {};
		
		if (team != "") {
			data.team = team;
		}
		data = JSON.stringify(data);
					
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/teammember/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamMember = result;
				console.log(teamMember)
				saveLog("teamMember", uuid, teamMember.team.teamName, teamMember.team.teamName, "TEAM_MEMBER_TEAM_CHANGED","");
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Member Team Updated Successfully</p>";
				$('#editTeamMemberTeamDiv').dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable("");
				setTimeout(
				function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM MEMBER TEAM");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Team</p>";
			}
		});
			} 
	
	function editTeamMemberLocationInfo(memberUuid,locationUuid)
	{
		$.get("/openmrs/ws/rest/v1/location?v=full", function(data) {
		var locations=data.results;
		
		$('#editTeamMemberLocationsSelect').children('option').remove();
		for(var i=-0;i<locations.length;i++)
		{
			$("#editTeamMemberLocationsSelect").append("<option value='"+locations[i].uuid+"'>"+locations[i].name+"</option>"); 	
		}
		$("#editTeamMemberLocationsSelect").val(locationUuid); 	
		$("#editTeamMemberLocationsUuid").val(memberUuid); 	
		
		$("#editTeamMemberLocationDiv").dialog({
			width : "auto",
			height : "auto",
			title : "Edit Team Member Location",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
		});
	}
	
	function teamMemberLocationsInfoSubmit()
	{
		var uuid = $("#editTeamMemberLocationsUuid").val();
		var location = $("#editTeamMemberLocationsSelect").val(); 	
		var data ={};
		if (location != "") {
			data.locations=[];
			var loc={};
			loc.uuid=location;
			locations.push(loc)
		}
			
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/teammember/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamMember = result;
				console.log(teamMember)
				saveLog("teamMember", uuid, teamMember.locations[0].name, teamMember.locations[0].name, "TEAM_MEMBER_TEAM_LOCATION_CHANGED","");
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Member Location Updated Successfully</p>";
				$('#editTeamMemberLocationDiv').dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable("");
				setTimeout(
				function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM MEMBER LOCATION");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Location</p>";
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
					console.log("SUCCESS-SAVE " + type.toUpperCase()
							+ " LOG");
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("ERROR-SAVE " + type.toUpperCase()
							+ " LOG");
					console.log(jqXHR);
					document.getElementById("saveHead").innerHTML = "";
					document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Location(s)</p>";
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

	function editTeamMemberDataProviderInfo(uuid) {
		$.get("/openmrs/ws/rest/v1/team/teammember/" + uuid, function(teamMember) {
			$("#editTeamMemberDataProvider").val(teamMember.isDataProvider.toString());
		});
		$("#editTeamMemberDataProviderUuid").val(uuid);
		$("#editTeamMemberDataProviderDiv").dialog({
			width : "auto",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function teamMemberDataProviderInfoSubmit() {
		var dataProvider = document.getElementById("editTeamMemberDataProvider").value;
		var uuid = document.getElementById("editTeamMemberDataProviderUuid").value;
		var data;
		data = {}
		data.isDataProvider= dataProvider.toString();
		data = JSON.stringify(data);
		$.ajax({
			url : "/openmrs/ws/rest/v1/team/teammember/" + uuid,
			data : data,
			type : "POST",
			contentType : "application/json",
			success : function(result) {
				var teamMember = result;
				console.log(teamMember);
				saveLog("teamMember", teamMember.uuid, teamMember.display, teamMember.display,
						"TEAM_MEMBER_IS_DATA_PROVIDER_STATUS_EDITED", "Team Member Data Provider Status Change to "+ dataProvider);
				document.getElementById("errorHead").innerHTML = "";
				document.getElementById("saveHead").innerHTML = "<p>Team Data Provider Status Updated Successfully</p>";
				$("#editTeamMemberDataProviderDiv").dialog('close');
				$("#general").dataTable().fnDestroy();
				CreateTable("");
				setTimeout(function() {
					document.getElementById("saveHead").innerHTML = ""
				}, 5000);
			},
			error : function(jqXHR, textStatus, errorThrown) {
				console.log("ERROR-EDIT TEAM DATA PROVIDER STATUS");
				console.log(jqXHR);
				document.getElementById("saveHead").innerHTML = "";
				document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Data Provider Status</p>";
			}
		});
	}

	function editTeamMemberVoidedInfo(uuid) {
		$.get("/openmrs/ws/rest/v1/team/teammember/" + uuid, function(team) {
			$("#editTeamMemberVoided").val(team.voided.toString());
			$("#editTeamMemberVoidReason").val(team.voidReason);
		});
		$("#editTeamMemberVoidedUuid").val(uuid);
		$("#editTeamMemberVoidedDiv").dialog({
			width : "500px",
			height : "auto",
			title : "Team Supervisor Detail",
			closeText : "",
			modal : true,
			open : onDialogOpen
		});
	}

	function teamMemberVoidedInfoSubmit() {
		var voided = document.getElementById("editTeamMemberVoided").value;
		var voidReason = document.getElementById("editTeamMemberVoidReason").value;
		var uuid = document.getElementById("editTeamMemberVoidedUuid").value;
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
			console.log(data);
			$.ajax({
				url : "/openmrs/ws/rest/v1/team/teammember/" + uuid,
				data : data,
				type : "POST",
				contentType : "application/json",
				success : function(result) {
					var teamMember = result;
					console.log(teamMember)
					saveLog("teammember", teamMember.uuid, teamMember.display,teamMember.display,
							"TEAM_MEMBER_VOIDED_STATUS_EDITED", "Team Member Voided Status Change to "+ voided);
					document.getElementById("errorHead").innerHTML = "";
					document.getElementById("saveHead").innerHTML = "<p>Team Member Void Status Updated Successfully</p>";
					$("#editTeamMemberVoidedDiv").dialog('close');
					$("#general").dataTable().fnDestroy();
					CreateTable("");
					setTimeout(
							function() {
								document.getElementById("saveHead").innerHTML = ""
							}, 5000);
				},
				error : function(jqXHR, textStatus, errorThrown) {
					console.log("ERROR-EDIT TEAM MEMBER VOIDED STATUS UPDATE");
					console.log(jqXHR);
					document.getElementById("saveHead").innerHTML = "";
					document.getElementById("errorHead").innerHTML = "<p>Error Occured While Updating Team Member Void Status</p>";
				}
			});
		}
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
				    	<option value="${supervisor.uuid}" >${supervisor.person.personName}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeamRole" name="filterByTeamRole">
					<option value="" selected>Select Team Role</option>
				   	<c:forEach items="${allTeamRoles}" var="teamRole">
				    	<option value="${teamRole.uuid}" >${teamRole.name}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByTeam" name="filterByTeam">
					<option value="" selected>Select Team</option>
				   	<c:forEach items="${allTeams}" var="team">
				    	<option value="${team.uuid}" >${team.teamName}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<select id="filterByLocation" name="filterByLocation">
					<option value="" selected>Select Location</option>
				   	<c:forEach items="${allLocations}" var="location">
				    	<option value="${location.uuid}" >${location}</option>
				   	</c:forEach>
				</select>
			</td>
			<td>
				<button type="button" id="submitBtn" name="submitBtn" onclick="filter()">Filter</button>
			</td>
		</tr>
	</table>

<table class="display" id="general" style="border-left: 1px solid black; border-right: 1px solid black;">
	<thead>
		<tr>
			<openmrs:hasPrivilege privilege="Edit Member">
				<th style="border-top: 1px solid black;">Edit</th>
			</openmrs:hasPrivilege>
			<th style="border-top: 1px solid black;">Identifier</th>
			<th style="border-top: 1px solid black;">Name</th>
			<th style="border-top: 1px solid black;">Role</th>
			<th style="border-top: 1px solid black;">Team</th>
			<th style="border-top: 1px solid black;">Locations</th>
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

<div id="teamMemberShowSubTeamsDiv">
	<table id="teamMemberShowSubTeams">
		<tr>
			<td>SubTeams:</td>
			<td id="teamMemberSubTeams"></td>
		</tr>
	</table>
</div>
<div id="teamMemberDetailDiv">
<table id='teamMemberDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="teamMemberDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="teamMemberDetailName"></td>
		</tr>
		<tr>
			<td>Location:</td>
			<td id="teamMemberDetailLocation"></td>
		</tr>
		<tr>
			<td>Current Team:</td>
			<td id="teamMemberDetailCurrentTeam"></td>
		</tr>
		<tr>
			<td>Current Supervisor:</td>
			<td id="teamMemberDetailCurrentSupervisor"></td>
		</tr>
		<tr>
			<td>SubTeams:</td>
			<td id="teamMemberDetailSubTeams"></td>
		</tr>
		<tr>
			<td>SubRoles:</td>
			<td id="teamMemberDetailSubRoles"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="teamMemberDetailVoided"></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td id="teamMemberDetailVoidReason"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="teamMemberDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="teamMemberDetailDateChanged"></td>
		</tr>
	</table>
</div>
<div id="editTeamMemberDiv">
<h3 id='infoError' style='color: red; display: inline'></h3>

<input type='hidden' id='teamMemberUuid' name='teamMemberUuid'>
<input type='hidden' id='teamMemberPersonUuid' name='teamPersonUuid'>
<table style=' width: 100%; '>
	<tr>
		<td style=' font-size: 18px; '>Name: </td>
		<td><input type='text' id='teamMemberPersonFirstName' name='teamMemberPersonFirstName' maxlength='50' ></td>
	</tr>
	<tr>
		<td style=' font-size: 18px; '>Middle Name: </td>
		<td><input type='text' id='teamMemberPersonMiddleName' name='teamMemberPersonMiddleName' value='' maxlength='50' ></td>
	</tr>
	<tr>
		<td style=' font-size: 18px; '>Family Name: </td>
		<td><input id='teamMemberPersonLastName' name='teamMemberPersonLastName' maxlength='50' ></td>
	</tr>
	<tr>
		<td style=' font-size: 18px; '>Identifier: </td>
		<td><input style=' width: 95%; font-size: 14px; padding: 5px; ' type='text' id='teamMemberIdentifier' name='teamMemberIdentifier' maxlength='45' ></td>
	</tr>
	<tr>
		<td></td>
		<td></td>
	</tr>
	<tr>
		<td></td>
		<td><button type='button' id='teamMemberEditSubmit' name='teamMemberEditSubmit' onclick='teamMemberInfoSubmit();' style='float: right;'>Save</button></td></tr></table></form>
</div>
<div id="editTeamMemberTeamRoleDiv">
<h3 id='editTeamMemberTeamRolesError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamMemberTeamRolesUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Team Roles:</td>
			<td><select id='editTeamMemberTeamRolesSelect' name='editTeamMemberTeamRolesSelect'>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamMemberTeamRolesInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>
</div>
<div id="editTeamMemberTeamDiv">
<h3 id='editTeamMemberTeamsError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamMemberTeamsUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Team Roles:</td>
			<td><select id='editTeamMemberTeamsSelect' name='editTeamMemberTeamsSelect'>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamMemberTeamsInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>
</div>
<div id="editTeamMemberVoidedDiv">
	<h3 id='voidError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamMemberVoidedUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Voided:</td>
			<td><select id='editTeamMemberVoided' name='editTeamMemberVoided'>
					<option value="true">True</option>
					<option value="false">False</option>
			</select></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td><textarea id='editTeamMemberVoidReason' name='editTeamMemberVoidReason'
					style='width: 95%; font-size: 14px; padding: 5px;'>
				</textarea>
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamMemberVoidedInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>
</div>
<div id="editTeamMemberDataProviderDiv">
<h3 id='voidError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamMemberDataProviderUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Is Data Provider:</td>
			<td><select id='editTeamMemberDataProvider' name='editTeamMemberDataProvider'>
					<option value="true">True</option>
					<option value="false">False</option>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamMemberDataProviderInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>
	</div>
<div id="editTeamMemberLocationDiv">
<h3 id='editTeamMemberLocationsError' style='color: red; display: inline'></h3>
	<input type="hidden" id="editTeamMemberLocationsUuid">
	<table style='width: 100%;'>
		<tr>
			<td>Team Locations:</td>
			<td><select id='editTeamMemberLocationsSelect' name='editTeamMemberLocationsSelect'>
			</select></td>
		</tr>
		<tr>
			<td></td>
			<td>
			<button type='button' onclick='teamMemberLocationsInfoSubmit();' style='float: right;'>Save</button>
			</td>
		</tr>
	</table>
</div>
<div id="teamMemberTeamDetailDiv">
<table id='teamMemberTeamDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="teamMemberTeamDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="teamMemberTeamDetailName"></td>
		</tr>
		<tr>
			<td>Current Supervisor:</td>
			<td id="teamMemberTeamDetailCurrentSupervisor"></td>
		</tr>
		<tr>
			<td>Supervisor Team:</td>
			<td id="teamMemberTeamDetailSupervisorTeam"></td>
		</tr>
		<tr>
			<td>Location:</td>
			<td id="teamMemberTeamDetailLocation"></td>
		</tr>
		<tr>
			<td>Members:</td>
			<td id="teamMemberTeamDetailNumberOfMember"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="teamMemberTeamDetailVoided"></td>
		</tr>
		<tr>
			<td>Void Reason:</td>
			<td id="teamMemberTeamDetailVoidReason"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="teamMemberTeamDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="teamMemberTeamDetailDateChanged"></td>
		</tr>
	</table>
</div>
<div id="teamMemberRoleDetailDiv">
<table id='teamMemberRoleDetail'>
		<tr>
			<td>Identifier:</td>
			<td id="teamMemberRoleDetailIdentifier"></td>
		</tr>
		<tr>
			<td>Name:</td>
			<td id="teamMemberRoleDetailName"></td>
		</tr>
		<tr>
			<td>Owns Team:</td>
			<td id="teamMemberRoleDetailOwnsTeam"></td>
		</tr>
		<tr>
			<td>Report To:</td>
			<td id="teamMemberRoleDetailReportTo"></td>
		</tr>
		<tr>
			<td>Report By:</td>
			<td id="teamMemberRoleDetailReportby"></td>
		</tr>
		<tr>
			<td>Members:</td>
			<td id="teamMemberRoleDetailNumberOfMember"></td>
		</tr>
		<tr>
			<td>Voided:</td>
			<td id="teamMemberRoleDetailVoided"></td>
		</tr>
		<tr>
			<td>Date Created:</td>
			<td id="teamMemberRoleDetailDateCreated"></td>
		</tr>
		<tr>
			<td>Date Changed:</td>
			<td id="teamMemberRoleDetailDateChanged"></td>
		</tr>
	</table>
</div>

<%@ include file="/WEB-INF/template/footer.jsp"%>
