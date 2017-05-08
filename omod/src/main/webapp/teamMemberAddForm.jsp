<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Member" otherwise="/login.htm" />
<openmrs:htmlInclude file="/scripts/jquery/jsTree/jquery.tree.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/jsTree/themes/classic/style.css" />
<openmrs:htmlInclude file="/openmrs.js" />
<openmrs:htmlInclude file="/scripts/openmrsmessages.js" appendLocale="true" />

<link href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" rel="stylesheet">
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.default.css" id="toggleCSS" />
<script src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9" type="text/javascript"></script>
<script src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	\$j = jQuery;
	jQuery(document).ready(function() {
		jQuery("#heading").hide();
		jQuery("#exist").hide();
		jQuery("#nameTip").hide();
		jQuery("#mNameTip").hide();
		jQuery("#fNameTip").hide();
		jQuery("#idTip").hide();
		//jQuery("#providerTip").hide();
		jQuery("#joinTip").hide();
		jQuery("#leaveTip").hide();
		jQuery("#voidTip").hide();
		//jQuery("#isDataProviderTip").hide();
		jQuery("#memberUsername").hide();
		jQuery("#memberPassword").hide();
		jQuery("#memberRole").hide();
		jQuery("#birthTip").hide();
		jQuery("#passwordText").hide();
		jQuery("#memberConfirmPassword").hide();	
		jQuery("#confirmPasswordText").hide();
	 	
		jQuery('#voided').change(function(){ if(this.checked) { jQuery('#voidTip').show(); } else { jQuery('#voidTip').hide(); } });
		
		//jQuery('#isDataProvider').change(function(){ if(this.checked) { jQuery('#isDataProviderTip').show(); } else { jQuery('#isDataProviderTip').hide(); } });

		jQuery('#choice').change(function() {
			if (this.checked) {
				jQuery("#memberName").hide();
				jQuery("#memberMiddleName").hide();
				jQuery("#memberFamilyName").hide();
				jQuery("#memberGender").hide();
				jQuery("#memberBirth").hide();
				jQuery("#heading").show();
				jQuery("#loginChoicebox").hide();
				jQuery("#exist").show();
				jQuery("#memberUsername").hide();
				jQuery("#memberPassword").hide();
				jQuery("#memberRole").hide();
				jQuery("#memberConfirmPassword").hide();
				
			} else {
				jQuery("#memberName").show();
				jQuery("#memberMiddleName").show();
				jQuery("#memberFamilyName").show();
				jQuery("#memberGender").show();
				jQuery("#memberBirth").show();
				jQuery("#heading").hide();
				jQuery("#loginChoicebox").show();
				jQuery("#exist").hide();
			}
		});
		jQuery('#loginChoice').change(function() {
			if (this.checked) {
				jQuery("#memberUsername").show();
				jQuery("#memberPassword").show();
				jQuery("#memberRole").show();
				jQuery("#memberConfirmPassword").show();
				
			} else {
				jQuery("#memberUsername").hide();
				jQuery("#memberPassword").hide();
				jQuery("#memberRole").hide();
				jQuery("#memberConfirmPassword").hide();
				
			}
		});
		
		jQuery("#joinDate" ).datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy' });
		jQuery("#leaveDate").datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy' });
		jQuery("#birthDate").datepicker({ maxDate: new Date(), dateFormat: 'dd/mm/yy' });
		
		document.getElementById("location").multiple = true;
		document.getElementById("location").size = 7;

		jQuery( "#password" ).blur(function() {
			var re = /(?=.*\d)(?=.*[a-z])(?=.*[A-Z]).{6,}/;
			var pass= jQuery( "#password" ).val();
			if(re.test(pass)!=true) jQuery("#passwordText").show();	
			else jQuery("#passwordText").hide();	
		});
		  
		jQuery( "#confirmPassword" ).blur(function() {
			var pass1= jQuery( "#password" ).val();
			var pass2= jQuery( "#confirmPassword" ).val();
			if(pass1 != pass2) jQuery("#confirmPasswordText").show();	
			else jQuery("#confirmPasswordText").hide();	
		});
		
		jQuery( "#userName" ).blur(function() {
			var user= jQuery( "#userName" ).val();
			console.log("user: " + user);
			if(user != "") {
			    jQuery.get("/openmrs/ws/rest/v1/team/teammember?getUserNameBy="+user, function(data){ console.log(data); });
			}
		});

		jQuery("#addBtn").bind("click", function() {
			var teamId = "${teamId}";
			
			var currentDate = new Date();

			var dd = currentDate.getDate();
			var mm = currentDate.getMonth() + 1; //January is 0!
			var yyyy = currentDate.getFullYear();

			if (dd < 10) { dd = '0' + dd }
			if (mm < 10) { mm = '0' + mm }

			currentDate = dd + '/' + mm + '/' + yyyy;

			var choice = document.getElementById("choice").checked;
			var existingPerson = document.getElementById("existingPersonId").value;
			var gName = givenName.value;
			var mName = middleName.value;
			var fName = familyName.value;
			var dobDate = birthDate.value;
			
			var jDate = joinDate.value;
			var lDate = leaveDate.value;
			var reason = voidReason.value;
			//var provider = document.getElementById("provider").value;
			var id = identifier.value;
			var selectedValue = gender.options[gender.selectedIndex].value;
			var regexp = /^[a-z/i][a-z.\- ]*[a-z/i]{2,}$/i;
			var idRegExp = /^[a-z|0-9]+[a-z.\-_]*[a-z|0-9]{2,}$/i;
			var mustSelectMessage = "";
			var dataTypeMessage = "";
			var user = document.getElementById("userName").value;
			var password = document.getElementById("password").value;

			if(choice){ if(existingPerson == null || existingPerson == ""){ mustSelectMessage += "Name can't be empty"; } }
			else{
				if (gName == null || gName == "") { mustSelectMessage += "Name can't be empty."; }
				if (!regexp.test(gName)) { dataTypeMessage += "<br>Min 3, max 20 All data types and either [- . Or _ ] are allowed for text field."; }
				if (fName == null || fName == "") { mustSelectMessage += "<br>Family Name can't be empty."; }
				if (!regexp.test(fName)) { dataTypeMessage += "<br>Family Name can contain [alphabets,.,-] min 3, max 20."; }
				if (selectedValue == 0) { mustSelectMessage += "<br>Please select a gender."; }
				if(document.getElementById("loginChoice").checked) {
					if (user == null || user == "") { mustSelectMessage += "UserName can't be empty."; }
					if (!regexp.test(user)) { dataTypeMessage += "<br>In UserName Min 3, max 20 All data types and either [- . Or _ ] are allowed for text field."; }
				}
				//if (selectedLocation == 0){
				//	mustSelectMessage += "<br>Please select location.";
				//}
			}
			  
				 if (id == null || id == "") {
					mustSelectMessage += "<br>Identifier can't be empty.";
				} if(!idRegExp.test(id)){
					dataTypeMessage += "<br>All data types and [-._] are allowed for identifier,min 3, max 20.";
					//alertify.alert("Only letters and numbers are allowed for Identifier.");	
				} if (jDate > currentDate) {
					mustSelectMessage += "<br>Join date can't be in future.";
					//alertify.alert("Join date can't be in future");
					jQuery("#joinTip").hide();
					document.getElementById("joinDate").value = "";
				} if (lDate > currentDate) {
					mustSelectMessage += "<br>Leave date can't be in future.";
					jQuery("#leaveTip").hide();
					document.getElementById("leaveDate").value = "";
				} if (document.getElementById("voided").checked && (reason == null || reason == "")) {
					mustSelectMessage += "<br>Either write a reason or uncheck the box please.";
				//} if (document.getElementById("isDataProvider").checked && (provider == null || provider == "")) {
				//	mustSelectMessage += "<br>Either write a provider name or uncheck the box please.";
				} if (dobDate > currentDate) {
					mustSelectMessage += "<br>Birth date can't be in future.";
					//alertify.alert("Join date can't be in future");
					jQuery("#birthTip").hide();
					document.getElementById("birthDate").value = "";
				}
			if(mustSelectMessage != ""){ alertify.alert(mustSelectMessage); }
			else if(dataTypeMessage != ""){ alertify.alert(dataTypeMessage); } 
			else {
				var xmlhttp = new XMLHttpRequest();
				var x;
				xmlhttp.open('POST', '/openmrs/module/teammodule/ajax/getMembers.form', true);  //"false" makes the request synchronous
				xmlhttp.setRequestHeader("Content-type","application/x-www-form-urlencoded");
				xmlhttp.send("identifier="+id+"&teamId="+teamId);
				xmlhttp.onreadystatechange=function() {
					if(xmlhttp.readyState==4 && xmlhttp.status==200) { 
						x=xmlhttp.responseText; 
						if(x == "" || x == null) { 
							var givenName = document.getElementById("givenName").value;
							var middleName = document.getElementById("middleName").value;
							var familyName = document.getElementById("familyName").value;
							var gender = document.getElementById("gender").value;
							var birthDate = document.getElementById("birthDate").value;
							var identifier = document.getElementById("identifier").value;
							var joinDate = document.getElementById("joinDate").value;
							var leaveDate = document.getElementById("leaveDate").value;
							var location = document.getElementById("location").value;
							var voided = document.getElementById("voided").checked;
							var voidReason = document.getElementById("voidReason").value;
							var isDataProvider = document.getElementById("isDataProvider").checked;
							//var provider = document.getElementById("provider").value;
							var teamRoleOption = document.getElementById("teamRoleOption").value;

							var choice = document.getElementById("choice").checked;
							var existingPersonId_selection = document.getElementById("existingPersonId_selection").value;
							
							document.getElementById("person_val").value = existingPersonId_selection
							var pId = document.getElementById("person_val").value;
							
							
							var loginChoice = document.getElementById("loginChoice").checked;
							var userName = document.getElementById("userName").value;
							var password = document.getElementById("password").value;
							var confirmPassword = document.getElementById("confirmPassword").value;
							var roleOption = document.getElementById("roleOption").value;
							
							/* console.log("givenName: " + givenName);
							console.log("middleName: " + middleName);
							console.log("familyName: " + familyName);
							console.log("gender: " + gender);
							console.log("birthDate: " + birthDate);
							console.log("identifier: " + identifier);
							console.log("joinDate: " + joinDate);
							console.log("leaveDate: " + leaveDate);
							console.log("location: " + location);
							console.log("voided: " + voided);
							console.log("voidReason: " + voidReason);
							console.log("isDataProvider: " + isDataProvider);
							//console.log("provider: " + provider);
							console.log("teamRoleOption: " + teamRoleOption);

							console.log("choice: " + choice);
							console.log("existingPersonId_selection: " + existingPersonId_selection);

							console.log("loginChoice: " + loginChoice);
							console.log("userName: " + userName);
							console.log("password: " + password);
							console.log("confirmPassword: " + confirmPassword);
							console.log("roleOption: " + roleOption);
							console.log("teamId: " + teamId); */

							var url = "/openmrs/ws/rest/v1/team/teammember?post=add&v=full";
							if(givenName != "") { url += "&givenName=" + givenName; }
							if(middleName != "") { url += "&middleName=" + middleName; }
							if(familyName != "") { url += "&familyName=" + familyName; }
							if(gender != "") { url += "&gender=" + gender; }
							if(birthDate != "") { url += "&birthDate=" + birthDate; }
							if(identifier != "") { url += "&identifier=" + identifier; }
							if(joinDate != "") { url += "&joinDate=" + joinDate; }
							if(leaveDate != "") { url += "&leaveDate=" + leaveDate; }
							if(location != "") { url += "&location=" + location; }
							if(voidReason != "") { url += "&voidReason=" + voidReason; }
							//if(provider != "") { url += "&provider=" + provider; }
							if(teamRoleOption != "") { url += "&teamRoleOption=" + teamRoleOption; }
							if(existingPersonId != "") { url += "&existingPersonId=" + existingPersonId_selection; }
							if(userName != "") { url += "&userName=" + userName; }
							if(password != "") { url += "&password=" + password; }
							if(confirmPassword != "") { url += "&confirmPassword=" + confirmPassword; }
							if(roleOption != "") { url += "&roleOption=" + roleOption; }
							if(teamId != "") { url += "&teamId=" + teamId; }

							if(choice) { url += "&choice=true"; } else { url += "&choice=false"; }
							if(loginChoice) { url += "&loginChoice=true"; } else { url += "&loginChoice=false"; }
							if(voided) { url += "&voided=true"; } else { url += "&voided=false"; }
							if(isDataProvider) { url += "&isDataProvider=true"; } else { url += "&isDataProvider=false"; }

							console.log(url);
							jQuery.ajax({
								url: url,
								success : function(result) { document.getElementById("saveHead").innerHTML = "New Member Added Successfully"; console.log("SUCCESS-POST"); console.log(result); 
								}, error: function(jqXHR, textStatus, errorThrown) { document.getElementById("errorHead").innerHTML = "Error Occured While Adding New Member"; console.log("ERROR-POST"); console.log(jqXHR); 
								}
							});
						}
						else { alertify.alert(x); }
					}
	  			}
			}
		});
	});
	
	function showCalendar(obj, yearsPrevious) {
		// if the object doesn't have an id, just set it to some random text so jq can use it
		if (!obj.id) { obj.id = "something_random" + (Math.random() * 1000); }

		//set appendText to something so it doesn't automatically pop into the page
		var opts = { appendText : " " };
		if (yearsPrevious) { opts["yearRange"] = "c-" + yearsPrevious + ":c10"; }
		
		var dp = new DatePicker('dd/mm/yyyy', obj.id, opts);
		jQuery.datepicker.setDefaults(jQuery.datepicker.regional['en_GB']);
		
		obj.onclick = null;
		dp.show();
		return false;
	}
	
	function join() {
		/* var date = "${teamDate}";
		var array = date.split(" ");
		var array = array[0].split("-");
		date = array[1] + "/" + array[2] + "/" + array[0];
		var d = new Date(date);
		jQuery( "#joinDate" ).datepicker("option", { minDate: new Date()}); */
		jQuery( "#joinDate" ).datepicker();
	}
	
	function leave() {
		startDate = jQuery('#joinDate').datepicker("getDate");
		jQuery( "#leaveDate" ).datepicker("option", { minDate: new Date(startDate), dateFormat: 'dd/mm/yy' });
	}
	
	function birth() {
		jQuery( "#birthDate" ).datepicker();
	}
	function validation(teamId) {
		
	}

	function personSelectedCallback(relType, person) {
		if (person != null && person.personId != null) {
			document.getElementById('useExistingButton').disabled = false;
		} else {
			document.getElementById('useExistingButton').disabled = true;
		}
	}
	
</script>

<h3 id="errorHead" name="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" name="saveHead" align="center" style="color: green">${saved}</h3>

<h2 align="center">Add Member</h2>

<form:form id="saveMember" name="saveMember" commandName="memberData">
	<table class="team">
		<tr>
			<td style="padding-bottom: 15px">
				<input type="checkbox" id="choice" name="choice" />
				 Choose Existing Person
			</td>
		</tr>
		<tr id="heading">
			<td>
				<!-- style="padding-left: 5em" -->
				<h3>
					Existing Person
				</h3>
			</td>
		</tr>
		<tr id="exist">
			<td>
				Name
			</td>
			<td>
				<openmrs_tag:personField formFieldName="person_id" formFieldId="existingPersonId" />
				<input type="hidden" name="person_val" id="person_val" />
				<br />
			</td>
		</tr>
		<tr id="memberName" type="hide">
			<td>
				Member Name
			</td>
			<td>
				<form:input id="givenName" path="person.names[0].givenName" onfocus="jQuery('#nameTip').show();" onblur="jQuery('#nameTip').hide();" maxlength="20" />
				<span style="color: red">
					*
				</span>
				<span id="nameTip">
					Min 3 and max 20.Alphabets,[-.] are allowed
				</span>
			</td>
		</tr>
		<tr id="memberMiddleName" type="hide">
			<td>
				Member Middle Name
			</td>
			<td>
				<form:input id="middleName" path="person.names[0].middleName" onfocus="jQuery('#mNameTip').show();" onblur="jQuery('#mNameTip').hide();" maxlength="20" />
				<span style="color: red">*</span>
				<span id="mNameTip">Min 3 and max 20.Alphabets,[-.] are allowed</span>
			</td>
		</tr>
		<tr id="memberFamilyName" type="hide">
			<td>
				Member Family Name
			</td>
			<td>
				<form:input id="familyName" path="person.names[0].familyName" onfocus="jQuery('#fNameTip').show();" onblur="jQuery('#fNameTip').hide();" maxlength="20" />
				<span style="color: red">*</span>
				<span id="fNameTip">Min 3 and max 20.Alphabets,[-.] are allowed</span>
			</td>
		</tr>
		<tr>
			<td style="padding-bottom: 15px" id="loginChoicebox">
				<input type="checkbox" id="loginChoice" name="loginChoice" /> Add Login Detail
			</td>
		</tr>
		<tr id="memberUsername" type="hide">
			<td>
				User Name: 
			</td>
			<td>
				<input type="text" name="userName" id="userName" autocomplete="on" />
			</td>
		</tr>
		<tr id="memberPassword" type="hide">
			<td>
				Password: 
			</td>
			<td>
				<input type="password" name="password" id="password" autocomplete="off" />
			</td>
			<td>
				<b id="passwordText">Atleast one number, one lowercase and one uppercase letter atleast six characters</b>
			</td>
		</tr>
		<tr id="memberConfirmPassword" type="hide">
			<td>
				Confirm Password: 
			</td>
			<td>
				<input type="password" name="confirmPassword" id="confirmPassword" autocomplete="off" />
			</td>
			<td>
				<b id="confirmPasswordText">Password and Confirm Password not match</b>
			</td>
		</tr>
		<tr id="memberRole" type="hide">
			<td valign="top">
				Role:
			</td>
			<td>
				<select id="roleOption" multiple>
					<option value="" label="Please Select " />
					<c:forEach items="${allRoles}" var="roles" varStatus="varStatus">
						<option value="${roles}">${roles}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr id="memberGender" type="hide">
			<td>
				Gender
			</td>
			<td>
				<form:select id="gender" path="person.gender" cssStyle="width:165px"> 
					<form:option value="0" label=" Please Select " />
					<form:option value="M">Male</form:option>
					<form:option value="F">Female</form:option>
				</form:select>
				<span style="color: red">*</span>
			</td>
		</tr>
		<tr id="memberBirth" type="hide">
			<td>
				Birth Date
			</td>
			<td>
				<form:input id="birthDate" path="person.birthdate" onblur="jQuery('#birthTip').hide();" onfocus="showCalendar(this,60);jQuery('#birthTip').show();birth();" />
				<span style="color: red">
					*
				</span>
				<span id="birthTip">
					Date shouldn't be in future
				</span>
			</td>
		</tr> 
		<tr id="memberIdentifier">
			<td>
				Member Identifier
			</td>
			<td>
				<form:input id="identifier" path="identifier" onfocus="jQuery('#idTip').show();" onblur="jQuery('#idTip').hide();" maxlength="20" />
				<span style="color: red">*</span>
				<span style="padding-left: 12px" id="idTip">Min 3 and max 20.All data types and [-_] are allowed</span>
			</td>
		</tr>
		<tr id="memberJoin">
			<td>
				Join Date
			</td>
			<td>
				<form:input id="joinDate" path="joinDate" onblur="jQuery('#joinTip').hide();" onfocus="showCalendar(this,60);jQuery('#joinTip').show();join();" />
				<span id="joinTip">Date shouldn't be in future</span>
			</td>
		</tr> 
		<tr id="memberEnd">
			<td>
				Leave Date
			</td>
			<td>
				<form:input id="leaveDate" path="leaveDate" onfocus="showCalendar(this,60);jQuery('#leaveTip').show();leave();" onblur="jQuery('#leaveTip').hide();" />
				<span id="leaveTip">Date shouldn't be in future</span>
			</td>
		</tr>
		<tr>
			<td>
				Location
			</td>
			<td>
				<openmrs:fieldGen type="org.openmrs.Location" formFieldName="location" val="${selectedLocation}" />
			</td>
		</tr>
		<tr>
			<td>
				Retire Member ?
			</td>
			<td>
				<form:checkbox id="voided" path="voided" />
			</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<span id="voidTip">Reason must be written</span></td>
		</tr>
		<tr>
			<td>Retire Reason</td>
			<td>
				<form:textarea id="voidReason" path="voidReason" />
			</td>
		</tr>
		<tr>
			<td>
				Is Data Provider ?
			</td>
			<td>
				<form:checkbox id="isDataProvider" path="isDataProvider" />
			</td>
		</tr>
		<!-- <tr>
			<td></td>
			<td>
				<span id="isDataProviderTip">
					Provider Name must be written
				</span>
			</td>
		</tr>
		<tr>
			<td>
				Provider
			</td>
			<td>
				<form:input id="provider" path="provider" onfocus="jQuery('#providerTip').show();" onblur="jQuery('#providerTip').hide();" maxlength="20" />
				<span style="padding-left: 12px" id="providerTip">
					Min 3 and max 20.All data types and [-_] are allowed
				</span>
			</td>
		</tr> -->
		<tr>
			<td>
				Member Role
			</td>
			<td>
				<select id="teamRoleOption"> <!-- multiple -->
					<option value="" label="Please Select " />
					<c:forEach items="${allTeamHierarchy}" var="memberRoles" varStatus="varStatus">
						<option value="${memberRoles.teamHierarchyId}">${memberRoles.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<%-- <tr>
			<td></td>
			<td style="text-align: left;">
				<button type="button" onClick="validation(${teamId});">Add</button>
			</td>
		</tr> --%>
		<tr>
			<td></td>
			<td style="text-align: left;">
				<button type="button" id="addBtn" name="addBtn" > <%-- onClick="validation(${teamId});" --%>
					Add
				</button>
			</td>
		</tr>
		<tr></tr>
		<tr>
			<td>
				<a href="/openmrs/module/teammodule/team.form">Back to Team List</a>
			</td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>