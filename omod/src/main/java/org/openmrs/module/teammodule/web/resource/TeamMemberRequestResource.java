/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.List;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamRole;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamRoleService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.module.teammodule.rest.v1_0.resource.TeamModuleResourceController;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.annotation.PropertyGetter;
import org.openmrs.module.webservices.rest.web.annotation.Resource;
import org.openmrs.module.webservices.rest.web.representation.DefaultRepresentation;
import org.openmrs.module.webservices.rest.web.representation.FullRepresentation;
import org.openmrs.module.webservices.rest.web.representation.Representation;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.openmrs.module.webservices.rest.web.v1_0.controller.ComplexDataDelegatingCrudResource;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends ComplexDataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember delegate) {
		try {
			if(delegate.getId() > 0){
				Context.getService(TeamMemberService.class).updateTeamMember(delegate);
				return delegate;
			}
			Context.getService(TeamMemberService.class).saveTeamMember(delegate);
		}
		catch(Exception e) { e.printStackTrace(); throw new RuntimeException(e); }
		return delegate;
	}
	
	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("identifier");
				description.addProperty("person", Representation.DEFAULT);
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team", Representation.DEFAULT);
				description.addProperty("patients", Representation.DEFAULT);
				description.addProperty("voided");
				description.addProperty("subTeams");
				
				description.addProperty("teamRole", Representation.DEFAULT);
				//description.addProperty("subTeamRoles");

			} else if (rep instanceof FullRepresentation) {
				description.addProperty("identifier");
				description.addProperty("person", Representation.FULL);
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team", Representation.FULL);
				description.addProperty("patients", Representation.FULL);
				description.addProperty("voided");
				description.addProperty("subTeams");
				description.addProperty("auditInfo");
				description.addSelfLink();
				
				description.addProperty("teamRole", Representation.FULL);
				//description.addProperty("subTeamRoles");
			}
		}
		return description;
	}


	@Override
	public SimpleObject subresourceSave(TeamMember delegate, String subResource, SimpleObject post, RequestContext context) {
		if(subResource.equals("team") && post.get("team") != null) {//post.get("team") == new team id
			System.out.println("subResource: " + subResource);
			System.out.println("team: " + post.get("team").toString());
			delegate.setTeam(Context.getService(TeamService.class).getTeam(Integer.parseInt(post.get("team").toString())));
			Context.getService(TeamMemberService.class).saveTeamMember(delegate);
			List<TeamMember> teamMembers = new ArrayList<>();
			teamMembers.add(delegate);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else { return null; }
	}
	
	@Override
	public DelegatingResourceDescription getUpdatableProperties()  {
		DelegatingResourceDescription description = new DelegatingResourceDescription();
		description.addProperty("identifier");
		description.addProperty("person");	
		description.addProperty("location");
		description.addProperty("team");
		description.addProperty("patients");
		description.addProperty("subTeams");
		description.addProperty("voided");
		//description.addProperty("teamRole");
		//description.addProperty("subTeamRoles");
		return description;
	}

	@Override
	public TeamMember getByUniqueId(String uuid) {
		//System.out.println("Inside");
		Person person = Context.getPersonService().getPersonByUuid(uuid);
		//System.out.println(person);
		if (person != null) {
			Integer id = person.getPersonId();
			List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(id);
			id = tm.get(0).getTeamMemberId();
			TeamMember member = Context.getService(TeamMemberService.class).getTeamMember(id);
			return member;
		}
		return Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);

	}

	@Override
	protected void delete(TeamMember delegate, String reason, RequestContext context) throws ResponseException {
		Context.getService(TeamMemberService.class).purgeTeamMember(delegate);
	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		Context.getService(TeamMemberService.class).purgeTeamMember(delegate);
	}

	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("q") != null) {
			System.out.println("q: " + context.getParameter("q"));
			List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).searchTeamMember(null, null, context.getParameter("q"), null, null);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else if(context.getParameter("id") != null) {
			System.out.println("id: " + context.getParameter("id"));
			TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(context.getParameter("id")));
			List<TeamMember> teamMembers = new ArrayList<>();
			teamMembers.add(teamMember);
			System.out.println("teamMembers: " + teamMembers);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		// FOR UI
		else if(context.getParameter("get") != null) {
			//GET 'ALL' TEAM MEMBERS 'ON PAGE LOAD' - teamMemberView.form
			if(context.getParameter("get").equals("all")) {
				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).getAllTeamMember(null, true, null, null);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			//GET 'FILTERED' TEAM MEMBERS 'ON FILTER BUTTON CLICK' - teamMemberView.form
			else if(context.getParameter("get").equals("filter")) {
				String id = "", supervisorId = "", teamRoleId = "", teamId = "", locationId = "";
				
				if(context.getParameter("identifier") != null) { id = context.getParameter("identifier"); }
				if(context.getParameter("supervisor") != null) { supervisorId = context.getParameter("supervisor"); }
				if(context.getParameter("role") != null) { teamRoleId = context.getParameter("role"); }
				if(context.getParameter("team") != null) { teamId = context.getParameter("team"); }
				if(context.getParameter("location") != null) { locationId = context.getParameter("location"); }

				//System.out.println("\nFrom UI: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				if(id.equals("") ) { id = null; }
				if(supervisorId.equals("") ) { supervisorId = null; }
				if(teamRoleId.equals("") ) { teamRoleId = null; }
				if(teamId.equals("") ) { teamId = null; }
				if(locationId.equals("") ) { locationId = null; }

				TeamMember supervisor = null; if(supervisorId != null) { supervisor = Context.getService(TeamMemberService.class).getTeamMember(Integer.parseInt(supervisorId)); }
				Team team = null; if(teamId != null) { team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId)); }
				TeamRole teamRole = null; if(teamRoleId != null) { teamRole = Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(teamRoleId)); }
				Location location = null; if(locationId != null) { location = Context.getLocationService().getLocation(Integer.parseInt(locationId)); }
				
				//System.out.println("\nTo Function: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).searchTeamMember(id, supervisor, teamRole, team, location, null, null);

				//System.out.println("\nAFTER: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");
				System.out.println("teamMembers: " + teamMembers);

				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else { System.out.println("get: " + context.getParameter("get")); return null; }
		}
		/*else if(context.getParameter("update") != null) {
			//UPDATE TEAM MEMBER INFO - teamMemberView.form
			if(context.getParameter("update").equals("teamMemberInfo")) {
				String uuid = "", identifier = "", firstName = "", middleName = "", lastName = "";

				if(context.getParameter("uuid") != null) { uuid = context.getParameter("uuid"); }
				if(context.getParameter("identifier") != null) { identifier = context.getParameter("identifier"); }
				if(context.getParameter("firstName") != null) { firstName = context.getParameter("firstName"); }
				if(context.getParameter("middleName") != null) { middleName = context.getParameter("middleName"); }
				if(context.getParameter("lastName") != null) { lastName = context.getParameter("lastName"); }
				
				if(uuid.equals("") ) { uuid = null; }
				if(identifier.equals("") ) { identifier = null; }
				if(firstName.equals("") ) { firstName = null; }
				if(middleName.equals("") ) { middleName = null; }
				if(lastName.equals("") ) { lastName = null; }

				System.out.println("uuid: " + uuid);
				System.out.println("identifier: " + identifier);
				System.out.println("firstName: " + firstName);
				System.out.println("middleName: " + middleName);
				System.out.println("lastName: " + lastName);

				TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
				teamMember.setIdentifier(identifier);
				teamMember.getPerson().getPersonName().setGivenName(firstName);
				teamMember.getPerson().getPersonName().setMiddleName(middleName);
				teamMember.getPerson().getPersonName().setFamilyName(lastName);
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			//UPDATE TEAM MEMBER TEAM - teamMemberView.form
			else if(context.getParameter("update").equals("teamMemberTeam")) {
				String uuid = "", teamId = "";
				
				if(context.getParameter("uuid") != null) { uuid = context.getParameter("uuid"); }
				if(context.getParameter("teamId") != null) { teamId = context.getParameter("teamId"); }
				
				if(uuid.equals("") ) { uuid = null; }
				if(teamId.equals("") ) { teamId = null; }

				System.out.println("uuid: " + uuid);
				System.out.println("teamId: " + teamId);

				TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
				teamMember.setTeam(Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId)));
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			//UPDATE TEAM MEMBER ROLE - teamMemberView.form
			else if(context.getParameter("update").equals("teamMemberRole")) {
				String uuid = "", roleId = "";

				if(context.getParameter("uuid") != null) { uuid = context.getParameter("uuid"); }
				if(context.getParameter("roleId") != null) { roleId = context.getParameter("roleId"); }
				
				if(uuid.equals("") ) { uuid = null; }
				if(roleId.equals("") ) { roleId = null; }

				System.out.println("uuid: " + uuid);
				System.out.println("roleId: " + roleId);

				TeamMember teamMember = Context.getService(TeamMemberService.class).getTeamMemberByUuid(uuid);
				teamMember.setTeamRole(Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(roleId)));
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else { System.out.println("update: " + context.getParameter("update")); return null; }
		}*/
		/*else if(context.getParameter("post") != null) {
			if(context.getParameter("post").equals("add")) {
				String givenName="",familyName="",gender="",birthDate="",identifier="",joinDate="",leaveDate="",location="",voided="",voidReason="",isDataProvider="",provider="",teamRoleOption="",choice="",existingPersonId="",loginChoice="",userName="",password="",confirmPassword="",roleOption="",teamId="";
				if(context.getParameter("givenName") != null) { givenName = context.getParameter("givenName"); }
				if(context.getParameter("familyName") != null) { familyName = context.getParameter("familyName"); }
				if(context.getParameter("gender") != null) { gender = context.getParameter("gender"); }
				if(context.getParameter("birthDate") != null) { birthDate = context.getParameter("birthDate"); }
				if(context.getParameter("identifier") != null) { identifier = context.getParameter("identifier"); }
				if(context.getParameter("joinDate") != null) { joinDate = context.getParameter("joinDate"); }
				if(context.getParameter("leaveDate") != null) { leaveDate = context.getParameter("leaveDate"); }
				if(context.getParameter("location") != null) { location = context.getParameter("location"); }
				if(context.getParameter("voided") != null) { voided = context.getParameter("voided"); }
				if(context.getParameter("voidReason") != null) { voidReason = context.getParameter("voidReason"); }
				if(context.getParameter("isDataProvider") != null) { isDataProvider = context.getParameter("isDataProvider"); }
				if(context.getParameter("provider") != null) { provider = context.getParameter("provider"); }
				if(context.getParameter("teamRoleOption") != null) { teamRoleOption = context.getParameter("teamRoleOption"); }
				if(context.getParameter("choice") != null) { choice = context.getParameter("choice"); }
				if(context.getParameter("existingPersonId") != null) { existingPersonId = context.getParameter("existingPersonId"); }
				if(context.getParameter("loginChoice") != null) { loginChoice = context.getParameter("loginChoice"); }
				if(context.getParameter("userName") != null) { userName = context.getParameter("userName"); }
				if(context.getParameter("password") != null) { password = context.getParameter("password"); }
				if(context.getParameter("confirmPassword") != null) { confirmPassword = context.getParameter("confirmPassword"); }
				if(context.getParameter("roleOption") != null) { roleOption = context.getParameter("roleOption"); }
				if(context.getParameter("teamId") != null) { teamId = context.getParameter("teamId"); }
				
				
				if(givenName.equals("")) { givenName = null; }
				if(familyName.equals("")) { familyName = null; }
				if(gender.equals("")) { gender = null; }
				if(birthDate.equals("")) { birthDate = null; }
				if(identifier.equals("")) { identifier = null; }
				if(joinDate.equals("")) { joinDate = null; }
				if(leaveDate.equals("")) { leaveDate = null; }
				if(location.equals("")) { location = null; }
				if(voided.equals("")) { voided = null; }
				if(voidReason.equals("")) { voidReason = null; }
				if(isDataProvider.equals("")) { isDataProvider = null; }
				if(provider.equals("")) { provider = null; }
				if(teamRoleOption.equals("")) { teamRoleOption = null; }
				if(choice.equals("")) { choice = null; }
				if(existingPersonId.equals("")) { existingPersonId = null; }
				if(loginChoice.equals("")) { loginChoice = null; }
				if(userName.equals("")) { userName = null; }
				if(password.equals("")) { password = null; }
				if(confirmPassword.equals("")) { confirmPassword = null; }
				if(roleOption.equals("")) { roleOption = null; }
				if(teamId.equals("")) { teamId = null; }
				
				System.out.println("\n givenName: " + givenName);
				System.out.println("\n familyName: " + familyName);
				System.out.println("\n gender: " + gender);
				System.out.println("\n birthDate: " + birthDate);
				System.out.println("\n identifier: " + identifier);
				System.out.println("\n joinDate: " + joinDate);
				System.out.println("\n leaveDate: " + leaveDate);
				System.out.println("\n location: " + location);
				System.out.println("\n voided: " + voided);
				System.out.println("\n voidReason: " + voidReason);
				System.out.println("\n isDataProvider: " + isDataProvider);
				System.out.println("\n provider: " + provider);
				System.out.println("\n teamRoleOption: " + teamRoleOption);
				System.out.println("\n teamId: " + teamId);

				System.out.println("\n choice: " + choice);
				System.out.println("\n existingPersonId: " + existingPersonId);

				System.out.println("\n loginChoice: " + loginChoice);
				System.out.println("\n userName: " + userName);
				System.out.println("\n password: " + password);
				System.out.println("\n confirmPassword: " + confirmPassword);
				System.out.println("\n roleOption: " + roleOption);
				
				
				Person person = new Person();
				if(choice != null) {
					//Choose Existing Person - checked
					if(choice.equals("true")) {
						Set<Person> personList = Context.getPersonService().getSimilarPeople(existingPersonId, null, null);
						for (Iterator iterator = personList.iterator(); iterator.hasNext();) {
							Person p = (Person) iterator.next();
							if(p.getPersonName().toString().equals(existingPersonId)) {
								person = Context.getPersonService().getPerson(p.getId());
							}
						}
					}
					//Choose Existing Person - not checked
					else {
						PersonName pNames = new PersonName(givenName, "", familyName);
						pNames.setPreferred(true);

						Set<PersonName> names = new TreeSet<>();
						names.add(pNames);
						
						person.setGender(gender);
						person.setBirthdate(new Date(birthDate));
						person.setNames(names);
						Context.getPersonService().savePerson(person);
						
						pNames.setPerson(person);
						Context.getPersonService().savePersonName(pNames);
						
						if(loginChoice != null) {
							//Add Login Detail - checked
							if(choice.equals("true")) {
								Set<Role> roles = new HashSet<>();
								roles.add(Context.getUserService().getRole(roleOption));
								User user = new User();
								user.setUsername(userName);
								user.setSystemId(userName);
								user.setRoles(roles);
								user.setPerson(person);
								user = Context.getUserService().saveUser(user, password);
							}
						}
					}
				}
				
				TeamMember teamMember = new TeamMember(); 
				
				if(identifier != null) { teamMember.setIdentifier(identifier); }
				if(joinDate != null) { teamMember.setJoinDate(new Date(joinDate)); }
				if(leaveDate != null) { teamMember.setLeaveDate(new Date(leaveDate)); }
				if(voided != null) { if(voided.equals("true")) { teamMember.setVoided(true); teamMember.setVoidReason(voidReason); } else { teamMember.setVoided(false); teamMember.setVoidReason(null); } }
				if(isDataProvider != null) { if(isDataProvider.equals("true")) { teamMember.setIsDataProvider(true); teamMember.setProvider(provider); } else { teamMember.setIsDataProvider(false); teamMember.setProvider(null); } }

				if(existingPersonId != null) { teamMember.setPerson(person); } else { teamMember.setPerson(null); }
				if(teamRoleOption != null) { teamMember.setTeamRole(Context.getService(TeamRoleService.class).getTeamRoleById(Integer.parseInt(teamRoleOption))); } else { teamMember.setTeamRole(null); }
				if(teamId != null) { teamMember.setTeam(Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId))); } else { teamMember.setTeam(null); }
				if(location != null) { Set<Location> locationSet = new HashSet<Location>(); locationSet.add(Context.getLocationService().getLocation(Integer.parseInt(location))); teamMember.setLocation(locationSet); } else { teamMember.setLocation(null); }
				
				Context.getService(TeamMemberService.class).saveTeamMember(teamMember);
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
//				return null;
			}
			else { return null; }
		}*/
		else if(context.getParameter("teamName")!=null && context.getParameter("locationId")!=null) {
			Location location = Context.getLocationService().getLocationByUuid(context.getParameter("locationId"));
			Team team = Context.getService(TeamService.class).getTeam(context.getParameter("teamName"),location.getLocationId());
			List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).searchTeamMemberByTeam(team.getTeamId());
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		}
		else { return null; }
	}

	@PropertyGetter("display")
	public String getDisplayString(TeamMember teamMember) {
		if (teamMember == null){		
			return null;
		}
		
		return teamMember.getPerson().getPersonName().toString();
	}
	
	@PropertyGetter("subTeams")
	public String getSubTeam(TeamMember teamMember) {
		List<Team> teams = Context.getService(TeamService.class).getSubTeams(teamMember);
		if(teams == null) { return null; }
		else {
			String str = "";
			for (int i = 0; i < teams.size(); i++) {
				if(i==teams.size()-1) { str += teams.get(i).getTeamName(); }
				else { str += teams.get(i).getTeamName() + ", "; }
			} return str;
		}
	}
	 @Override
	public SimpleObject getAll(RequestContext context) throws ResponseException {
		 List<TeamMember> teamMembers = Context.getService(TeamMemberService.class).getAllTeamMember(true, 0, 25);
			return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
		
	}
	/*@PropertyGetter("subTeamRoles")
	public String getSubTeamRole(TeamMember teamMember) {
		List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMember.getPerson().getId());
		if(tm == null) { return ""; }
		else { String teamNames = "";
			for (int j = 0; j < tm.size(); j++) { 
				if(j==tm.size()-1) { teamNames += tm.get(j).getTeamRole().getName() + ""; }
				else { teamNames += tm.get(j).getTeamRole().getName() + ", "; }
			} return teamNames;
		}
	}
	
	@PropertyGetter("teamRole")
	public String getTeamRoleName(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamRole().getName().toString();
	}

	@PropertyGetter("reportTo")
	public String getReportTo(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamRole().getReportTo().getName();
	}*/
	
	

	/*@PropertySetter("team")
	public Team setTeam(String teamStr) {
		System.out.println();
		System.out.println("teamStr: " + teamStr);
		if (teamStr == null){ return null; }
		else {
			Team team = Context.getService(TeamService.class).getTeam(teamStr);
			if(team == null) { return Context.getService(TeamService.class).getTeam(Integer.parseInt(teamStr)); }
			else { return team; }
		}
	}

	@Override
	public void setConvertedProperties(TeamMember delegate, Map<String, Object> propertyMap, DelegatingResourceDescription description, boolean mustIncludeRequiredProperties) throws ConversionException {
		for (Map.Entry<String, Object> prop : propertyMap.entrySet()) {
			System.out.println("Conversion");
			System.out.println(delegate);
			System.out.println(prop.getKey());
			System.out.println(prop.getValue());
			setProperty(delegate, prop.getKey(), prop.getValue());
		}
	}*/
}