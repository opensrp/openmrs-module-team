<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>
<openmrs:require privilege="View Team" otherwise="/login.htm" />
<head>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<link rel="stylesheet" href="/resources/demos/style.css">
<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
<link rel="stylesheet"
	href="https://cdn.datatables.net/1.10.15/css/jquery.dataTables.min.css">
<script
	src="https://cdn.datatables.net/1.10.15/js/jquery.dataTables.min.js"></script>
<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<style type="text/css">
#memberDialog>table, #memberDialog>table th, #memberDialog>table td {
	border: 1px solid black;
	border-collapse: collapse;
}

#historyDialog>table, #historyDialog>table th, #historyDialog>table td {
	border: 1px solid black;
	border-collapse: collapse;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$('#historyDialog').hide();
		$('#memberDialog').hide();
		var tbody,table,teamDataVar;
		table = document.getElementById("general");
		tbody= document.createElement("TBODY");
			var  data = $.get("/openmrs/ws/rest/v1/team/teamrole?v=full", function(teamRoleData) {
			teamRoles = teamRoleData.results;
			GenerateTable(tbody);
			table.appendChild(tbody);
			$('#general').DataTable({
				"language" : {
					"search" : "_INPUT_",
					"searchPlaceholder" : "Search..."
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

	function GenerateTable(tbody) {
			for(var i=0;i<teamRoles.length;i++)
			{
			row = tbody.insertRow(-1);
				/* Edit */
			var cell = row.insertCell(-1);
			cell.innerHTML = "Edit";
			/* Name */
			var cell = row.insertCell(-1);
			cell.innerHTML = teamRoles[i].name;
			/* Role */
			var cell = row.insertCell(-1);
			cell.innerHTML = teamRoles.length;
			
			var cell = row.insertCell(-1);
			cell.innerHTML = teamRoles[i].ReportToName;	
			/* Team */
			var cell = row.insertCell(-1);
			cell.innerHTML = teamRoles[i].ownsTeam;
			
			/* Report To */
			var cell = row.insertCell(-1);
			if(teamRoles[i].ReportByName != null)
			for(var i=0;i<teamRoles[i].ReportByName.length;i++)
				cell.innerHTML = teamRoles[i].ReportByName[i][1];
			else
			cell.innerHTML = "-";
			
			var cell = row.insertCell(-1);
			cell.innerHTML = '<a onClick="teamsHierarchyHistory(\''+ teamRoles[i].uuid + '\')">History</a>';
			
			}
		
	}

	function teamsHierarchyHistory(teamId) {
		$.get("/openmrs/ws/rest/v1/team/teamhierarchylog?teamRole=" + teamId,
						function(data) {
							var myTable = document.getElementById("history");
							var rowCount = myTable.rows.length;

							for (i = 0; i < rowCount; i++) {
								$("#historyRow").remove();
							}
							for (i = 0; i < data.length; i++) {
								$("#history")
										.append(
												"<tr id=\"historyRow\">"
														+ "<td style=\"text-align: left;\" valign=\"top\">"
														+ data.results[i].teamRole.name
														+ "</td>"
														+ "<td style=\"text-align: left;\" valign=\"top\">"
														+ data[i].action
														+ "</td>"
														+ "<td style=\"text-align: left;\" valign=\"top\">"
														+ data[i].dataNew
														+ "</td>"
														+ "<td style=\"text-align: left;\" valign=\"top\">"
														+ data[i].log
														+ "</td>" + "</tr>");
							}
						});
		$("#historyDialog").dialog({
			width : "auto",
			height : "auto",
			title : "History",
			closeText : ""
		});
	}
	
</script>
</head>

<div id="historyDialog">
	<table id="history">
		<tr>
			<th>Team Name</th>
			<th>Action</th>
			<th>New Data</th>
			<th>Date</th>
		</tr>
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
<h1>Teams Hierarchy</h1>

<table class="extra">
	<tr>
		<openmrs:hasPrivilege privilege="Add Team">
			<td><a href="/openmrs/module/teammodule/addRole.form">Add
					Role</a></td>
		</openmrs:hasPrivilege>
	</tr>
</table>
<table class="general" id="general">
	<thead>
		<tr>
			<openmrs:hasPrivilege privilege="Edit Team">
				<th>Edit</th>
			</openmrs:hasPrivilege>
			<th>Name</th>
			<th>Number Of member</th>
			<th>Report To</th>
			<th>Owns Team</th>
			<th>Report By</th>
			<openmrs:hasPrivilege privilege="View Team">
				<th>History</th>
			</openmrs:hasPrivilege>
		</tr>
	</thead>
</table>
<p>
	<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
</p>



<%@ include file="/WEB-INF/template/footer.jsp"%>