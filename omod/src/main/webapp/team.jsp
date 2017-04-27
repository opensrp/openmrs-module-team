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
  <script type="text/javascript">
  $(document).ready(function(){
	  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?v=default", function(data, status){
		  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?v=default", function(data, status){
			  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/team?supervisor=&v=default", function(data, status){
				  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/teammember?v=default", function(data, status){
					  $.get("http://127.0.0.1:8080/openmrs/ws/rest/v1/team/teamlog?teamId=&v=default", function(data, status){
										        
	    });
		   // When both Ajax requests were successful
		   $.when(call1, call2).done(function(a1, a2){
		      // a1 and a2 are arguments resolved for the call1 and call2 ajax requests, respectively.
		      // Each argument is an array with the following structure: [ data, statusText, jqXHR ]

		      // Merge data from two Ajax calls
		      var data = a1[0].d.results.concat(a2[0].d.results);

		      $('#example').dataTable({
		         "aaData": data,
		         "aoColumns": [
		            { "mData": "Data1" },
		            { "mData": "Data2" },
		            { "mData": "Data3" },
		            { "mData": "Data4" },
		            { "mData": "Data5" }
		         ]
		      });
		   });
	});
  
  </script>
</head>


  
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