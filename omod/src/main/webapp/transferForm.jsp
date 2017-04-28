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

<form:form id="transferForm" name="transferForm" method="post"
	commandName="transfer">
	<table class="form">

		<tr>
			<td>Team</td>
			<td><form:select id="team" path="team.teamId"  >
					<form:option value="0" label=" Please Select " />
					<c:forEach var="teams" items="${teams}" varStatus="loop">
						<c:if test="${teamId != teams.teamId}">
							<form:option value="${teams.teamId}">${teams.teamName}</form:option>
						</c:if>
					</c:forEach>
				</form:select></td>
			<!-- <td><form:errors path="team" cssClass="error" /></td> -->
		</tr>
		<!-- <tr>
			<td>Is Team Lead ?</td>
			<td><form:checkbox id="isTeamLead" path="isTeamLead" /></td>
		</tr> -->
		
		<tr>
			<td>Location</td>
			<td><openmrs:fieldGen type="org.openmrs.Location" 
				formFieldName="location" val="${selectedLocation}"  /><span style="color:red"> This hierarchy is from start 
				so the locations must be selected wisely </span></td>
		</tr>
		
		<tr>
			<td></td>
			<td align="center"><button type="button" onClick="validation();">Transfer</button></td>
		</tr>
	</table>
</form:form>





<%@ include file="/WEB-INF/template/footer.jsp"%>