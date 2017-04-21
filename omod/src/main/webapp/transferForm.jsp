<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Edit Team" otherwise="/login.htm" />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1"
	type="text/css" rel="stylesheet">
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet"
	href="/openmrs/moduleResources/teammodule/themes/alertify.default.css"
	id="toggleCSS" />
<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	\$j=jQuery;
	
	jQuery(document).ready(function() {
		//jQuery("#location").hide();
		document.getElementById("location").multiple = true;
		document.getElementById("location").size = 7;
	});
	function validation() {
		var team = document.getElementById("team");
		var selectedValue = team.options[team.selectedIndex].value;
		
		//var xmlhttp = new XMLHttpRequest();
		//var x;
		//xmlhttp.open('POST', '/openmrs/module/teammodule/ajax/getLocations.form', true);  //"false" makes the request synchronous
		//xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
		//xmlhttp.send("teamId="+teamId);
		//xmlhttp.onreadystatechange=function()
		//{
		// if(xmlhttp.readyState==4 && xmlhttp.status==200)
		//  {
		//   x=xmlhttp.responseText;
		//   if(x == "" || x == null){
		//		 document.getElementById("transferForm").submit(); 
		//	}else{
		//		 document.getElementById("location").show();
			//}
		
//	}
 // }

		if (selectedValue == 0) {
			alertify.alert("Please select a team");
		} else {
			document.getElementById("transferForm").submit();
		}
	}
</script>

<h3 style="color: red; display: inline">${errorMessage}</h3>

	<h3>Select team to transfer</h3>

<form id="transferForm" name="transferForm" method="post">
<input type="hidden" name="teamId" value="${teamId}"/>
<input type="hidden" name="memberId" value="${memberId}"/>
	<table class="form">

		<tr>
			<td>Team</td>
			<td><select id="team" name="transferredTeam" >
					<c:forEach var="teams" items="${teams}" varStatus="loop">
						<c:if test="${teamId != teams.teamId}">
							<option value="${teams.teamName}">${teams.teamName}</option>
						</c:if>
					</c:forEach>
				</select></td>
		</tr>
		
		<tr>
			<td>Location</td>
			<td><openmrs:fieldGen type="org.openmrs.Location" 
				formFieldName="transferredLocation" val="${selectedLocation}"  /><span style="color:red"> This hierarchy is from start 
				so the locations must be selected wisely </span></td>
		</tr>
		
		<tr>
			<td></td>
			<td align="center"><button type="button" onClick="validation();">Transfer</button></td>
		</tr>
	</table>
</form>





<%@ include file="/WEB-INF/template/footer.jsp"%>