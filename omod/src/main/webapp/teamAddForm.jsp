<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Team" otherwise="/login.htm" />

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
		 jQuery("#voided").attr('checked',false);
		 jQuery("#voidReason").val("");
		 jQuery("#nameTip").hide();
		 jQuery("#idTip").hide();
		 jQuery("#voidTip").hide();
		 document.getElementById("teamName").value = "";
		 document.getElementById("teamIdentifier").value = "";
		 
		 jQuery('#voided').change(function(){
		        if(this.checked)
		            jQuery('#voidTip').show();
		        else
		            jQuery('#voidTip').hide();

		    });
	 }); 
	function validation() {
		var regexp = /^[a-z/i][a-z\- ]*[a-z/i\-|0-9]{2,}$/i;
		var idRegExp = /^[a-z|0-9]+[a-z.\-_]*[a-z|0-9]{2,}$/i;
		var name = teamName.value;
		var id = teamIdentifier.value;
		var location = document.getElementById("location");
		var selectedValue = location.options[location.selectedIndex].value;
		var reason = voidReason.value;
		var mustSelectMessage= "";
		var dataTypeMessage = "";
		if (name == null || name == "") {
			mustSelectMessage += "Team name can't be empty.";
			//alertify.alert("Team name can't be empty");
		} if (!regexp.test(name)) {
			dataTypeMessage += "<br>Team Name must start with alphabet and can contain [alphanumeric text,-] min 3, max 20.";
			//alertify.alert("Team Name can contain only letters and numbers.\nMinimum 3 characters allowed");
		}  if (id != null && id != "") {
			if(!idRegExp.test(id)){
				dataTypeMessage += "<br>All data types and [-._] are allowed for identifier except special characters,min 3, max 20.";
				//alertify.alert("Only letters and numbers are allowed for Identifier");	
			}
		}  if (selectedValue == 0) {
			mustSelectMessage += "<br>Please select a location.";
			//alertify.alert("Please select a location");
		} if (document.getElementById("voided").checked && (reason == null || reason == "")) {
			mustSelectMessage += "<br>Either write a reason or uncheck the box please.";
			//alertify.alert("Either write a reason or uncheck the box please");
		} 
			 if(mustSelectMessage != ""){
					alertify.alert(mustSelectMessage);
				}else if(dataTypeMessage != ""){
					alertify.alert(dataTypeMessage);
				}else{
					var xmlhttp = new XMLHttpRequest();
					var x;
					xmlhttp.open('POST', '/openmrs/module/teammodule/ajax/getTeams.form', true);  //"false" makes the request synchronous
					xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
					xmlhttp.send("teamName="+name+"&locationId="+selectedValue);
					xmlhttp.onreadystatechange=function()
					{
					 if(xmlhttp.readyState==4 && xmlhttp.status==200)
					  {
					   x=xmlhttp.responseText;
					   if(x == "" || x == null){
							 document.getElementById("saveTeam").submit(); 
						}else{
						alertify.alert(x);
						}
					
				}
			  }
			}			 
				
		}

</script>

<h3 style="color: red; display: inline">${error}</h3>
<h3 align="center" style="color: green;">${saved}</h3>
<h3 style="color: green; display: inline">${edit}</h3>
<h2 align="center">Add Team</h2>
<table class="team">
	<form:form id="saveTeam" name="saveTeam" method="post"
		commandName="teamData">
		<tr>
			<td>Team Name</td>
			<td><form:input id="teamName" path="teamName" maxlength="20"
					onfocus="jQuery('#nameTip').show();"
					onblur="jQuery('#nameTip').hide();" /><span style="color: red">*</span>
			</td>
		</tr>
		<tr><td></td>
			<td><span id="nameTip">Must start with alphabet.Min 3 and
					max 20.Alphanumeric text,- is allowed</span></td>
		</tr>
		<tr>
			<td>Team Identifier</td>
			<td><form:input id="teamIdentifier" path="teamIdentifier"
					maxlength="20" onfocus="jQuery('#idTip').show();"
					onblur="jQuery('#idTip').hide();" /></td>
		</tr>
		<tr><td></td>
			<td><span style="padding-left: 12px" id="idTip">Min 3 and
					max 20.All data types and [-._] are allowed</span></td>
		</tr>
		<tr>
			<td>Location</td>
			<td><form:select id="location" path="location"
					cssStyle="width:165px">
					<form:option value="0" label=" Please Select " />
					<c:forEach var="location" items="${location}" varStatus="loop">
						<form:option value="${location.locationId}">${location.name}</form:option>
					</c:forEach>
				</form:select><span style="color: red">*</span></td>
		</tr>
		<tr>
			<td>Retire Team ?</td>
			<td><form:checkbox id="voided" path="voided" /></td>
			<!-- USE VOIDED STUPID -->
		</tr>
		<tr><td></td>
			<td><span style="padding-left: 160px" id="voidTip">Reason
					must be written</span></td>
		</tr>
		<tr>
			<td>Retire Reason</td>
			<td><form:textarea id="voidReason" path="voidReason" /></td>
		</tr>
		<tr>
			<input type="hidden" value="save" name="type" />
			<td></td>
			<td><button type="button" onClick="validation();">Save</button></td>
		</tr>

	</form:form>
	<tr>
		<td><a href="/openmrs/module/teammodule/team.form">Back to
				teams</a></td>
	</tr>
</table>

<%@ include file="/WEB-INF/template/footer.jsp"%>