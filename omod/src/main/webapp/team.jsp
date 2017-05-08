<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<head>
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <link rel="stylesheet" href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
  <script src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
  <link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
	<style type="text/css">
	#memberDialog> table, #memberDialog> table th, #memberDialog> table td {
	border: 1px solid black;
	 border-collapse: collapse;
	}
	#historyDialog> table, #historyDialog> table th, #historyDialog> table td {
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
	}
	</style>
  <script type="text/javascript">
  
  $(document).ready(function(){
	  $('#historyDialog').hide();
      $('#memberDialog').hide();
      $('#allMemberDialog').hide();
      $('#supervisorTeamDetailDialog').hide();
	  $.get("/openmrs/ws/rest/v1/team/team?v=full",function(data) { 
			teams = data.results;
			GenerateTable();
			$('#general').DataTable({
				"language": {
					 			        "search": "_INPUT_",
					 			        "searchPlaceholder": "Search..."
					 			    },
					 			    "paging" : true,
					 				"lengthChange" : false,
					 				"searching" : true,
					 				"ordering" : true,
					 				"info" : false,
					 				"autoWidth" : true,
					 				"sDom" : 'lfrtip',
			});
		});
	  });
  
  function GenerateTable() {
		var table = document.getElementById("general");
	    
	    var tbody = document.createElement("TBODY")
	    for (var i = 0; i < teams.length; i++) {
	    	row = tbody.insertRow(-1);
	    	/* Edit */
	    	var cell = row.insertCell(-1);
	        cell.innerHTML = "<a id='editTeamLink' name='editTeamLink' title='Edit Team' style='cursor:pointer' href='/openmrs/module/teammodule/_editTeam.form?location="+teams[i].location.uuid+"&name="+teams[i].teamName+"'><img src='/openmrs/moduleResources/teammodule/img/edit.png' style=' width: 20px; height: 20px; padding-left: 10%;' ></a>";
	        /* Identifier */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = teams[i].teamIdentifier;
	        /* Name */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = teams[i].teamName;
	        /* Role */
	        if(teams[i].supervisor!=null)
	        {
	        var cell = row.insertCell(-1);
	        cell.innerHTML = "<a id='editTeamLink' name='editTeamLink' title='Edit Team' style='cursor:pointer' onClick=teamMember("+teams[i].supervisor.uuid+")'>"+teams[i].supervisor+"</a>" ;
	        var cell = row.insertCell(-1);
	        cell.innerHTML = "<a id='editTeamLink' name='editTeamLink' title='Edit Team' style='cursor:pointer' onClick=supervisorTeamDetail("+teams[i].supervisor.team.uuid+")'>"+teams[i].supervisor.team+"</a>" ;
		    }
	        else
	        {
	        	var cell = row.insertCell(-1);
		        cell.innerHTML = "-" ;
		        var cell = row.insertCell(-1);
		        cell.innerHTML = "-";
		        
	        }	
	        var cell = row.insertCell(-1);
	        cell.innerHTML = "<a id='editTeamrLink' name='editTeamLink' title='Edit Team' style='cursor:pointer' onClick=teamAllMember("+teams[i].uuid+")'>"+teams[i].members+"</a>" ;
	        
	        /* Report To */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = teams[i].location.name;
	        
	        var cell = row.insertCell(-1);
	        cell.innerHTML = '<a onClick="teamHistory('+teams[i].teamIdentifier+')">History</a>';
	        /* Patients */
	        var cell = row.insertCell(-1);
	        cell.innerHTML = teams[i].voided;
		}
	    table.appendChild(tbody);
	}
  
function teamHistory(teamId) {
$.get("/openmrs/ws/rest/v1/team/teamlog?q="+teamId+"&v=full", function(data){
	  var myTable = document.getElementById("history");
	  var rowCount = myTable.rows.length;
	
	  for (i = 0; i < rowCount; i++)
	  {
		  $("#historyRow").remove();
	  }
		  for (i = 0; i < data.results.length; i++)
		  {
			  var d = new Date(data.results[i].auditInfo.dateCreated);
			  var date= "N/A"
			  if(Date.parse(d)){
				  date = d.format("dd-mm-yyyy");
				}
			  
		  $("#history").append("<tr id=\"historyRow\">"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.results[i].team.teamName+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.results[i].action+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.results[i].dataNew+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+date+"</td>"
		+"</tr>"); 
		  }
});
$( "#historyDialog" ).dialog( { width: "auto",
	    height: "auto", title: "History" , closeText: ""});
}
function teamAllMember(teamUuid) {/* 
	$.get("/openmrs/ws/rest/v1/team/teamMember/"+teamUuid+"", function(data){
	  var myTable = document.getElementById("allMember");
	  var rowCount = myTable.rows.length;
	
	  for (i = 0; i < rowCount; i++)
	  {
		  $("#allMemberRow").remove();
	  }
		    var d = new Date(data.results[i].auditInfo.dateCreated);
			  var date = d.format("dd-mm-yyyy");
		  $("#allMember").append("<tr id=\"allMemberRow\">"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.identifier+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.team.teamName+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.joinDate+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+date.location.name+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+date.person.gender+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\"><a href=\"/openmrs/module/teammodule/teamMember/list.form?teamMemberId="+data[i].teamMemberId+"\">Detail</a></td>"
		+"</tr>"); 
});
$( "#allMemberDialog" ).dialog( { width: "auto",
	    height: "auto", title: "All Team Members", closeText: ""});   */
}

function teamMember(teamUuid) {
	$.get("/openmrs/ws/rest/v1/team/teamMember/"+teamUuid+"?v=full", function(data){
	  var myTable = document.getElementById("member");
	  var rowCount = myTable.rows.length;
	
	  for (i = 0; i < rowCount; i++)
	  {
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
	$( "#memberDialog" ).dialog( { width: "auto",
	    height: "auto", title: "Team Members", closeText: ""});  
}

function supervisorTeamDetail(teamUuid) {
	$.get("/openmrs/ws/rest/v1/team/team/"+teamUuid+"?v=full", function(data){
	  var myTable = document.getElementById("supervisorTeamDetail");
	  var rowCount = myTable.rows.length;
	
	  for (i = 0; i < rowCount; i++)
	  {
		  $("#supervisorTeamRow").remove();
	  }
		    var d = new Date(data.results[i].auditInfo.dateCreated);
			  var date = d.format("dd-mm-yyyy");
		$("#supervisorTeamDetailTable").append("<tr id=\"supervisorTeamRow\">"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.identifier+"</td>"
		+"<td style=\"text-align: left;\" valign=\"top\">"+data.teamName+"</td>");
		if(data.supervisor==null)
			{
			$("#supervisorTeamDetail").append(
			"<td style=\"text-align: left;\" valign=\"top\">-</td>"
			);
			}
		else
			{
			$("#supervisorTeamDetail").append(
					"<td style=\"text-align: left;\" valign=\"top\">"+data.supervisor.name+"</td>"
			);
			}
		$("#supervisorTeamDetail").append(
				"<td style=\"text-align: left;\" valign=\"top\">"+date.voided+"</td>"
				+"<td style=\"text-align: left;\" valign=\"top\">"+date.location.name+"</td>"
				+"</tr>");
});
	$( "#supervisorTeamDetailDialog" ).dialog( { width: "auto",
	    height: "auto", title: "supervisor Team Detail", closeText: ""});  
}

  </script>
</head>

<div id="historyDialog">
  <table id="history">
  <tr>
	<th>Team Name</th>
	<th>Action</th>
	<th>New Data </th>
	<th>Date</th>
	</tr>
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
<h1>Teams</h1>

<table class="extra">
		<tr>
			<openmrs:hasPrivilege privilege="Add Team">
				<td><a href="/openmrs/module/teammodule/addTeam.form">Add
						Team</a></td>
			</openmrs:hasPrivilege>

			<openmrs:hasPrivilege privilege="View Member">
				<td><a
					href="/openmrs/module/teammodule/allMember.form?searchMember=&from=&to=">View
						All Members</a></td>
			</openmrs:hasPrivilege>
		</tr>
	</table>
		<table class="general" id ="general">
			<thead>
			<tr>
				<openmrs:hasPrivilege privilege="Edit Team">
				<th>Edit</th>
				</openmrs:hasPrivilege>
				<th>Identifier</th>
				<th>Team Name</th>
				<th>Current Supervisor</th>
				<th>Supervisor Team</th>
				<th>Number Of member</th>
				<th>Location</th>
				<openmrs:hasPrivilege privilege="View Team">
					<th>History</th>
				</openmrs:hasPrivilege>
				<openmrs:hasPrivilege privilege="View Team">
					<th>Voided</th>
				</openmrs:hasPrivilege>
			</tr>
			</thead>
  </table>
	<p>
		<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
	</p>



<%@ include file="/WEB-INF/template/footer.jsp"%>