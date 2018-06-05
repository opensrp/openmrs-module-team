<%@ include file="/WEB-INF/template/include.jsp"%>

<%@ include file="/WEB-INF/template/header.jsp"%>

<openmrs:require privilege="Add Member" otherwise="/login.htm" />
<openmrs:htmlInclude file="/scripts/jquery/jsTree/jquery.tree.min.js" />
<openmrs:htmlInclude file="/scripts/jquery/jsTree/themes/classic/style.css" />
<openmrs:htmlInclude file="/openmrs.js" />
<openmrs:htmlInclude file="/scripts/openmrsmessages.js" appendLocale="true" />

<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/teamModule.css?v=1.1" type="text/css" >
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.core.css" />
<link rel="stylesheet" href="/openmrs/moduleResources/teammodule/themes/alertify.default.css" id="toggleCSS" />
<script type="text/javascript" src="/openmrs/scripts/jquery-ui/js/jquery-ui-datepicker-i18n.js?v=1.9.3.f535e9"></script>
<script type="text/javascript" src="/openmrs/moduleResources/teammodule/alertify.min.js"></script>

<script type="text/javascript">
	$j = jQuery;
	var locations = [];
	var userRoles = [];
	var allLocationIds = []; 
	var allLocationUuids = []; 
	<c:forEach items="${allLocations}" var="location"> allLocationIds.push("${location.id}"); allLocationUuids.push("${location.uuid}"); </c:forEach>
	jQuery(document).ready(function() {
		jQuery("#heading").hide();
		jQuery("#exist").hide();
		jQuery("#nameTip").hide();
		jQuery("#mNameTip").hide();
		jQuery("#fNameTip").hide();
		jQuery("#idTip").hide();
		jQuery("#joinTip").hide();
		jQuery("#leaveTip").hide();
		jQuery("#voidTip").hide();
		jQuery("#memberUsername").hide();
		jQuery("#memberPassword").hide();
		jQuery("#memberRole").hide();
		jQuery("#birthTip").hide();
		jQuery("#passwordText").hide();
		jQuery("#memberConfirmPassword").hide();	
		jQuery("#confirmPasswordText").hide();
	 	
		jQuery('#voided').change(function(){ if(this.checked) { jQuery('#voidTip').show(); } else { jQuery('#voidTip').hide(); } });
		
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
			} 
			else {
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
			} 
			else {
				jQuery("#memberUsername").hide();
				jQuery("#memberPassword").hide();
				jQuery("#memberRole").hide();
				jQuery("#memberConfirmPassword").hide();
			}
		});
		
		jQuery("#joinDate" ).datepicker({ maxDate: new Date(), dateFormat: 'yy-mm-dd' });
		jQuery("#birthDate").datepicker({ maxDate: new Date(), dateFormat: 'yy-mm-dd' });
		jQuery("#leaveDate").datepicker({ maxDate: new Date(), dateFormat: 'yy-mm-dd' });
		
		document.getElementById("location").multiple = true;
		document.getElementById("location").size = 7;
		document.getElementById("location").setAttribute("style", "width: 181px");
		document.getElementById("location").value = "";
		document.getElementById("location").setAttribute("onchange", "getSelectedLocations(this)");

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

		jQuery("#addBtn").bind("click", function() {
			var teamId = "${teamId}";
			
			var currentDate = new Date();

			var dd = currentDate.getDate();
			var mm = currentDate.getMonth() + 1; //January is 0!
			var yyyy = currentDate.getFullYear();

			if (dd < 10) { dd = '0' + dd }
			if (mm < 10) { mm = '0' + mm }

			currentDate = yyyy + '-' + mm + '-' + dd;

			var choice = document.getElementById("choice").checked;
			var existingPerson = document.getElementById("existingPersonId").value;
			var gName = givenName.value;
			var mName = middleName.value;
			var fName = familyName.value;
			var dobDate = birthDate.value;
			
			var jDate = joinDate.value;
			var lDate = leaveDate.value;
			var id = identifier.value;
			var selectedValue = gender.options[gender.selectedIndex].value;
			var selectedMemberRoleValue = teamRoleOption.options[teamRoleOption.selectedIndex].value;
			var selectedMemberValue = teamOption.options[teamOption.selectedIndex].value;
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
			}
			if (id == null || id == "") { mustSelectMessage += "<br>Identifier can't be empty."; }
			if(!idRegExp.test(id)){ dataTypeMessage += "<br>All data types and [-._] are allowed for identifier,min 3, max 20."; }
			if (jDate > currentDate) { mustSelectMessage += "<br>Join date can't be in future."; jQuery("#joinTip").hide(); document.getElementById("joinDate").value = ""; } 
			if (dobDate > currentDate) { mustSelectMessage += "<br>Birth date can't be in future."; jQuery("#birthTip").hide(); document.getElementById("birthDate").value = ""; } 
			//if (selectedMemberRoleValue == 0) { mustSelectMessage += "<br>Please select a Member Role."; }
			//if (selectedMemberValue == 0) { mustSelectMessage += "<br>Please select a Member Team."; }
			if(document.getElementById("loginChoice").checked) {
				if (user == null || user == "") { mustSelectMessage += "<br>UserName can't be empty."; }
				if (!regexp.test(user)) { dataTypeMessage += "<br>In UserName Min 3, max 20 All data types and either [- . Or _ ] are allowed for text field."; }
			}
			if(mustSelectMessage != ""){ alertify.alert(mustSelectMessage); }
			else if(dataTypeMessage != ""){ alertify.alert(dataTypeMessage); } 
			else {
				var ident = document.getElementById("identifier").value;
				var url1 = "/openmrs/ws/rest/v1/team/teammember?get=filter&v=full";
				if(ident != "") { url1 += "&identifier=" + ident; }
				jQuery.ajax({
					url: url1,
					success : function(result) { console.log("SUCCESS-FILTER"); var myIdentifiers = []; for(var loop=0; loop<result.results.length; loop++) { myIdentifiers.push(result.results[loop].identifier); }
						if(myIdentifiers.includes(ident)) { alertify.alert("Identifier must be unique"); }
						else {
							var givenName = document.getElementById("givenName").value;
							var middleName = document.getElementById("middleName").value;
							var familyName = document.getElementById("familyName").value;
							var gender = document.getElementById("gender").value;
							var birthDate = document.getElementById("birthDate").value;
							var identifier = document.getElementById("identifier").value;
							var joinDate = document.getElementById("joinDate").value;
							var leaveDate = document.getElementById("leaveDate").value;
							var location = document.getElementById("location").value;
							var isDataProvider = document.getElementById("isDataProvider").checked;
							var teamRoleOption = document.getElementById("teamRoleOption").value;
							var teamOption = document.getElementById("teamOption").value;
							var choice = document.getElementById("choice").checked;
							var existingPersonId_selection = document.getElementById("existingPersonId_selection").value;
							document.getElementById("person_val").value = existingPersonId_selection;
							var pId = document.getElementById("person_val").value;
							var loginChoice = document.getElementById("loginChoice").checked;
							var userName = document.getElementById("userName").value;
							var password = document.getElementById("password").value;
							var confirmPassword = document.getElementById("confirmPassword").value;
							var roleOption = document.getElementById("roleOption").value;
							var person = "";
							if(!choice) {
								var url3 = "/openmrs/ws/rest/v1/person";
								var names=[]
								var name={}
								if(givenName != "") { name.givenName=givenName; }
								if(middleName != "") { name.middleName=middleName; }
								if(familyName != "") { name.familyName=familyName; }
								if(gender==="M") { name.prefix="Mr."; } else if(gender==="F") { name.prefix="Ms."; }
								name.preferred=true;
								names.push(name)
								var data3={};
								if(names != "") { data3.names=names; }
								if(gender != "") { data3.gender=gender; }
								if(birthDate != "") { data3.birthdate=birthDate; }
								data3=JSON.stringify(data3);
								jQuery.ajax({
									url: url3,
									data : data3,
									type: "POST",
									contentType: "application/json",
									success : function(result) { console.log("SUCCESS-PERSON IF"); console.log(result);
										person = result.uuid;
										if(loginChoice) {
											var url2 = "/openmrs/ws/rest/v1/user";
											var data2={}
											if(userName != "") { data2.username=userName; }
											if(password != "") { data2.password=password; }
											if(roleOption != "") { 
												data2.roles=[]
												for(var k = 0; k < userRoles.length; k++) { 
													var role={};
													if(k === userRoles.length-1) { 
														role.uuid=userRoles[k]; } 
													else { 
														role.uuid=userRoles[k]; } 
													data2.roles.push(role)
												} 
												 
											}
											data2.person={};
											data2.person.uuid=person;
											data2.systemId=userName;
											data2=JSON.stringify(data2);
											
											jQuery.ajax({
												url: url2,
												data : data2,
												type: "POST",
												contentType: "application/json",
												success : function(result) { console.log("SUCCESS-USER"); 
												}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-USER"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Creating Team Member</p>"; }
											});
										}
										var url = "/openmrs/ws/rest/v1/team/teammember";
										var data={};
										if(identifier != "") { data.identifier=identifier; }
										if(location != "") {
											console.log(location);
											data.locations=[]; 
											for(var k = 0; k < locations.length; k++) { 
												var loc={}
												if(k === locations.length-1) { 
													loc.uuid=locations[k]; 
												} 
												else { 
													loc.uuid=locations[k]; 
												}
												data.locations.push(loc);
											}  
										}
										if(joinDate != "") { data.joinDate=joinDate; }
										if(leaveDate != "") { data.leaveDate=leaveDate; }
										if(teamOption != "0") { 
											data.team={}
											data.team.uuid=teamOption; 
										}
										if(teamRoleOption != "0") { 
										data.teamRole={};
										data.teamRole.uuid=teamRoleOption; }
										data.person={};
										data.person.uuid=person;
										data.isDataProvider=isDataProvider;
										console.log(data);
										data=JSON.stringify(data);
										
										jQuery.ajax({
											url: url,
											data : data,
											type: "POST",
											contentType: "application/json",
											success : function(result) { console.log("SUCCESS-TEAM MEMBER"); resetForm(); saveLog("teamMember", result.uuid.toString(), result.display.toString(), "", "TEAM_MEMBER_ADDED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Created Successfully</p>";
											}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-TEAM MEMBER"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Creating Team Member</p>"; }
										});
									}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-PERSON IF"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Creating Team Member</p>"; }
								});
							} else {
								jQuery.ajax({
									url: "/openmrs/ws/rest/v1/person?v=full&q="+pId,
									contentType: "application/json",
									success : function(result) { console.log("SUCCESS-PERSON ELSE"); 
										for(var p=0; p<result.results.length; p++) { if(pId === result.results[p].display) { person = result.results[p].uuid; } }
										var url = "/openmrs/ws/rest/v1/team/teammember";
										var data = {}; 
										if(identifier != "") { data.identifier=identifier; }
										if(location != "") { 
											data.locations=[];
											for(var k = 0; k < locations.length; k++) { 
												var loc={}
												if(k === locations.length-1) { 
													loc.uuid=locations[k]; } 
												else { 
													loc.uuid=locations[k]; } 
												data.locations.push(loc);
											}; 
										}
										if(joinDate != "") { data.joinDate=joinDate; }
										if(leaveDate != "") { data.leaveDate=leaveDate; }
										if(teamOption != "0") { 
											data.team={};
											data.team.uuid=teamOption; 
										}
										if(teamRoleOption != "0") { 
										data.teamRole={}
										data.teamRole.uuid=teamRoleOption; 
										}
										data.person={};
										data.person.uuid=person;
										data.isDataProvider=isDataProvider;
										console.log(data);
										data=JSON.stringify(data);
										
										jQuery.ajax({
											url: url,
											data : data,
											type: "POST",
											contentType: "application/json",
											success : function(result) { console.log("SUCCESS-TEAM MEMBER"); resetForm(); saveLog("teamMember", result.uuid.toString(), "", result.display.toString(), "TEAM_MEMBER_ADDED", ""); document.getElementById("errorHead").innerHTML = ""; document.getElementById("saveHead").innerHTML = "<p>Team Member Created Successfully</p>";
											}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-TEAM MEMBER"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Creating Team Member</p>"; }
										});
									}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-PERSON ELSE"); console.log(jqXHR); document.getElementById("saveHead").innerHTML = ""; document.getElementById("errorHead").innerHTML = "<p>Error Occured While Creating Team Member</p>"; }
								});
							}
						}
					}, error: function(jqXHR, textStatus, errorThrown) { console.log("ERROR-FILTER"); console.log(jqXHR); }
				});			
			}
		});
	});
	
	function resetForm() {
		document.getElementById("givenName").value = "";
		document.getElementById("middleName").value = "";
		document.getElementById("familyName").value = "";
		document.getElementById("birthDate").value = "";
		document.getElementById("gender").value = "0";
		document.getElementById("identifier").value = "";
		document.getElementById("joinDate").value = "";
		document.getElementById("leaveDate").value = "";
		document.getElementById("location").value = "";
		document.getElementById("isDataProvider").checked=false;
		document.getElementById("teamRoleOption").value = "0";
		document.getElementById("teamOption").value = "0";
		document.getElementById("choice").checked=false;
		document.getElementById("existingPersonId_selection").value = "";
		document.getElementById("person_val").value = "";
		document.getElementById("loginChoice").checked=false;
		document.getElementById("userName").value = "";
		document.getElementById("password").value = "";
		document.getElementById("confirmPassword").value = "";
		document.getElementById("roleOption").value = "";
		window.scrollTo(0, 0);
	}
	
	function showCalendar(obj, yearsPrevious) {
		if (!obj.id) { obj.id = "something_random" + (Math.random() * 1000); }
		var opts = { appendText : " " };
		if (yearsPrevious) { opts["yearRange"] = "c-" + yearsPrevious + ":c10"; }
		var dp = new DatePicker('yyyy-mm-dd', obj.id, opts);
		jQuery.datepicker.setDefaults(jQuery.datepicker.regional['en_GB']);
		obj.onclick = null; dp.show(); return false;
	}
	function join() { jQuery( "#joinDate" ).datepicker(); }	
	function leave() { jQuery("#leaveDate").datepicker("option", { minDate: new Date(jQuery('#joinDate').datepicker("getDate")), dateFormat: 'yy-mm-dd' }); }
	function birth() { jQuery( "#birthDate" ).datepicker(); }
	function personSelectedCallback(relType, person) { if (person != null && person.personId != null) { document.getElementById('useExistingButton').disabled = false; } else { document.getElementById('useExistingButton').disabled = true; } }
	function removeDuplicates(arr) { var tmp = [];  for(var i = 0; i < arr.length; i++) {  if(tmp.indexOf(arr[i]) == -1) { tmp.push(arr[i]); } } return tmp; }
	function getSelectedLocations(select) { var array = []; for (var i = 0; i < select.options.length; i++) { if (select.options[i].selected) { array.push(allLocationUuids[allLocationIds.indexOf(select.options[i].value)]); } } locations = removeDuplicates(array); }
	function getSelectedUserRoles(select) { var array = []; for (var i = 0; i < select.options.length; i++) { if (select.options[i].selected) { array.push(select.options[i].value); } } userRoles = removeDuplicates(array); }
	
	function saveLog(type, uuid, dataNew, dataOld, action, log) {
		if(action.length <= 45 && dataNew.length <= 500 && dataOld.length <= 500 && log.length <= 500) { 
			var url = "/openmrs/ws/rest/v1/team/"+type.toLowerCase()+"log/";
			var data={}
			data.type=uuid;
			data.dataNew=dataNew;
			data.dataOld=dataOld;
			data.action=action
			data.log=log;
			data=JSON.stringify(data);
			
			jQuery.ajax({
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
</script>

<ul id="menu">
	<li class="first"><a href="/openmrs/module/teammodule/addRole.form" title="New Team Hierarchy (Role)">New Team Hierarchy (Role)</a></li>
	<li><a href="/openmrs/module/teammodule/addTeam.form" title="New Team">New Team</a></li>
	<li class="active"><a href="/openmrs/module/teammodule/teamMemberAddForm.form" title="New Team Member">New Team Member</a></li>
	<li><a href="/openmrs/module/teammodule/teamRole.form" title="Manage Team Hierarchy (Roles)">Manage Team Hierarchy (Roles)</a></li>
	<li><a href="/openmrs/module/teammodule/team.form" title="Manage Teams">Manage Teams</a></li>
	<li><a href="/openmrs/module/teammodule/teamMember.form" title="Manage Team Members">Manage Team Members</a></li>
</ul>

<h2 align="center">Add Team Member</h2>

<h3 id="errorHead" style="color: red; display: inline">${error}</h3>
<h3 id="saveHead" align="center" style="color: green">${saved}</h3>
<h3 id="editHead" align="center" style="color: green;">${edit}</h3>

<form:form id="saveMember" name="saveMember" commandName="memberData">
	<table class="team">
		<tr>
			<td style="padding-bottom: 15px">
				<input type="checkbox" id="choice" name="choice" />
				 Choose Existing Person
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr id="exist">
			<td>
				<h3>
					Existing Person
				</h3>
			</td>
			<td>
				<openmrs_tag:personField formFieldName="person_id" formFieldId="existingPersonId" />
				<input type="hidden" name="person_val" id="person_val" />
				<!-- <br /> -->
			</td>
			<td></td>
		</tr>
		<tr id="memberName" type="hide">
			<td>
				First Name:
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
				Middle Name:
			</td>
			<td>
				<form:input id="middleName" path="person.names[0].middleName" onfocus="jQuery('#mNameTip').show();" onblur="jQuery('#mNameTip').hide();" maxlength="20" />
				<span id="mNameTip">Min 3 and max 20.Alphabets,[-.] are allowed</span>
			</td>
			<td></td>
		</tr>
		<tr id="memberFamilyName" type="hide">
			<td>
				Family Name:
			</td>
			<td>
				<form:input id="familyName" path="person.names[0].familyName" onfocus="jQuery('#fNameTip').show();" onblur="jQuery('#fNameTip').hide();" maxlength="20" />
				<span style="color: red">*</span>
				<span id="fNameTip">Min 3 and max 20.Alphabets,[-.] are allowed</span>
			</td>
			<td></td>
		</tr>
		<tr id="memberBirth" type="hide">
			<td>
				Birth Date:
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
			<td></td>
		</tr> 
		<tr id="memberGender" type="hide">
			<td>
				Gender:
			</td>
			<td>
				<form:select id="gender" path="person.gender" cssStyle="width:181px"> 
					<form:option value="0" label=" Please Select " />
					<form:option value="M">Male</form:option>
					<form:option value="F">Female</form:option>
				</form:select>
				<span style="color: red">*</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td style="padding-bottom: 15px" id="loginChoicebox">
				<input type="checkbox" id="loginChoice" name="loginChoice" /> Add Login Detail
			</td>
			<td></td>
			<td></td>
		</tr>
		<tr id="memberUsername" type="hide">
			<td>
				User Name: 
			</td>
			<td>
				<input type="text" name="userName" id="userName" autocomplete="on" />
			</td>
			<td></td>
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
				User Role:
			</td>
			<td>
				<select id="roleOption" multiple onchange="getSelectedUserRoles(this)" style="width: 181px;">
					<option value="" label="Please Select " />
					<c:forEach items="${allRoles}" var="roles" varStatus="varStatus">
						<option value="${roles.uuid}">${roles}</option>
					</c:forEach>
				</select>
			</td>
			<td></td>
		</tr>
		<tr id="memberIdentifier">
			<td>
				Member Identifier:
			</td>
			<td>
				<form:input id="identifier" path="identifier" onfocus="jQuery('#idTip').show();" onblur="jQuery('#idTip').hide();" maxlength="20" />
				<span style="color: red">*</span>
				<span style="padding-left: 12px" id="idTip">Min 3 and max 20.All data types and [-_] are allowed</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				Team:
			</td>
			<td>
				<select id="teamOption" style="width: 181px;">
					<option value="0" label="Please Select " />
					<c:forEach items="${allTeams}" var="memberTeams" varStatus="varStatus">
						<option value="${memberTeams.uuid}">${memberTeams.teamName}</option>
					</c:forEach>
				</select>
				<!-- <span style="color: red">*</span> -->
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				Team Role:
			</td>
			<td>
				<select id="teamRoleOption" style="width: 181px;">
					<option value="0" label="Please Select " />
					<c:forEach items="${allTeamRole}" var="memberRoles" varStatus="varStatus">
						<option value="${memberRoles.uuid}">${memberRoles.name}</option>
					</c:forEach>
				</select>
				<!-- <span style="color: red">*</span> -->
			</td>
			<td></td>
		</tr>
		<tr id="memberJoin">
			<td>
				Join Date:
			</td>
			<td>
				<form:input id="joinDate" path="joinDate" onblur="jQuery('#joinTip').hide();" onfocus="showCalendar(this,60);jQuery('#joinTip').show();join();" />
				<span id="joinTip">Date shouldn't be in future</span>
			</td>
			<td></td>
		</tr> 
		<tr id="memberEnd">
			<td>
				Leave Date:
			</td>
			<td>
				<form:input id="leaveDate" path="leaveDate" onfocus="showCalendar(this,60);jQuery('#leaveTip').show();leave();" onblur="jQuery('#leaveTip').hide();" />
				<span id="leaveTip">Date shouldn't be in future</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				Assigned Locations:
			</td>
			<td>
				<openmrs:fieldGen type="org.openmrs.Location" formFieldName="location" val="${selectedLocation}" />
				<span style="color: red">*</span>
			</td>
			<td></td>
		</tr>
		<tr>
			<td>
				Is Data Provider?
			</td>
			<td>
				<form:checkbox id="isDataProvider" path="isDataProvider" />
			</td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td style="text-align: left;">
				<button type="button" id="addBtn" name="addBtn" >
					Add
				</button>
			</td>
			<td></td>
		</tr>
		<tr><td></td><td></td><td></td></tr>
		<tr><td></td><td></td><td></td></tr>
		<tr>
			<td>
				<a href="/openmrs/module/teammodule/teamMember.form">
					Show all Team Members
				</a>
			</td>
			<td></td>
			<td></td>
		</tr>
	</table>
</form:form>

<%@ include file="/WEB-INF/template/footer.jsp"%>