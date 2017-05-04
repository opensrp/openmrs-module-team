/**
 * 
 */
package org.openmrs.module.teammodule.web.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Role;
import org.openmrs.User;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamHierarchy;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamHierarchyService;
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
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.resource.impl.DelegatingResourceDescription;
import org.openmrs.module.webservices.rest.web.resource.impl.NeedsPaging;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

/**
 * @author Muhammad Safwan
 * 
 */

@Resource(name = RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE + "/teammember", supportedClass = TeamMember.class, supportedOpenmrsVersions = { "1.8.*", "1.9.*, 1.10.*, 1.11.*", "1.12.*" })
public class TeamMemberRequestResource extends DataDelegatingCrudResource<TeamMember> {

	@Override
	public TeamMember newDelegate() {
		return new TeamMember();
	}

	@Override
	public TeamMember save(TeamMember delegate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DelegatingResourceDescription getRepresentationDescription(Representation rep) {
		DelegatingResourceDescription description = null;

		if (Context.isAuthenticated()) {
			description = new DelegatingResourceDescription();
			if (rep instanceof DefaultRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");

				description.addProperty("patients", Representation.REF);
				description.addProperty("teamHierarchy");
				description.addProperty("reportTo");
				description.addProperty("subTeam");
				description.addProperty("subTeamHierarchy");
				description.addProperty("personId");
				description.addProperty("personGivenName");
				description.addProperty("personMiddleName");
				description.addProperty("personFamilyName");
			} else if (rep instanceof FullRepresentation) {
				description.addProperty("teamMemberId");
				description.addProperty("identifier");
				description.addProperty("person");
				description.addProperty("uuid");
				description.addProperty("location");
				description.addProperty("team");
				description.addProperty("patients", Representation.REF);

				description.addProperty("teamHierarchy");
				description.addProperty("reportTo");
				description.addProperty("subTeam");
				description.addProperty("subTeamHierarchy");
				description.addProperty("personId");
				description.addProperty("personGivenName");
				description.addProperty("personMiddleName");
				description.addProperty("personFamilyName");
			}
		}
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
		// TODO Auto-generated method stub

	}

	@Override
	public void purge(TeamMember delegate, RequestContext context) throws ResponseException {
		// TODO Auto-generated method stub

	}

	@SuppressWarnings({ "deprecation", "rawtypes" })
	@Override
	public SimpleObject search(RequestContext context) {
		if(context.getParameter("getUserNameBy") != null) {
			System.out.println("getUserNameBy: " + context.getParameter("getUserNameBy"));
			User user = Context.getUserService().getUserByUsername(context.getParameter("getUserNameBy"));
			List<User> users = new ArrayList<>();
			users.add(user);
			return new NeedsPaging<User>(users, context).toSimpleObject(this);
		}
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
		else if(context.getParameter("update") != null) {
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
				teamMember.setTeamRole(Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(roleId)));
				Context.getService(TeamMemberService.class).updateTeamMember(teamMember);
				
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else { System.out.println("update: " + context.getParameter("update")); return null; }
		}
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
				TeamHierarchy teamRole = null; if(teamRoleId != null) { teamRole = Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(teamRoleId)); }
				Location location = null; if(locationId != null) { location = Context.getLocationService().getLocation(Integer.parseInt(locationId)); }
				
				//System.out.println("\nTo Function: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");

				List<TeamMember> teamMembers = (List<TeamMember>) Context.getService(TeamMemberService.class).searchTeamMember(id, supervisor, teamRole, team, location, null, null);

				//System.out.println("\nAFTER: " + id + ", " + supervisorId + ", " + teamRoleId + ", " + teamId + ", " + locationId + "\n");
				System.out.println("teamMembers: " + teamMembers);

				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
			}
			else { System.out.println("get: " + context.getParameter("get")); return null; }
		}
		else if(context.getParameter("post") != null) {
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
				if(teamRoleOption != null) { teamMember.setTeamHierarchy(Context.getService(TeamHierarchyService.class).getTeamRoleById(Integer.parseInt(teamRoleOption))); } else { teamMember.setTeamHierarchy(null); }
				if(teamId != null) { teamMember.setTeam(Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId))); } else { teamMember.setTeam(null); }
				if(location != null) { Set<Location> locationSet = new HashSet<Location>(); locationSet.add(Context.getLocationService().getLocation(Integer.parseInt(location))); teamMember.setLocation(locationSet); } else { teamMember.setLocation(null); }
				
				Context.getService(TeamMemberService.class).saveTeamMember(teamMember);
				List<TeamMember> teamMembers = new ArrayList<>();
				teamMembers.add(teamMember);
				System.out.println("teamMembers: " + teamMembers);
				return new NeedsPaging<TeamMember>(teamMembers, context).toSimpleObject(this);
//				return null;
			}
			else { return null;
			}
		}
		else { return null; }
	}

	@PropertyGetter("personId")
	public String getPersonId(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getPerson().getId().toString();
	}
	
	@PropertyGetter("personGivenName")
	public String getPersonGivenName(TeamMember teamMember) {
		if (teamMember == null || teamMember.getPerson().getPersonName().getGivenName() == null) { return ""; }
		return teamMember.getPerson().getPersonName().getGivenName().toString();
	}
	
	@PropertyGetter("personMiddleName")
	public String getPersonMiddleName(TeamMember teamMember) {
		if (teamMember == null || teamMember.getPerson().getPersonName().getMiddleName() == null) { return ""; }
		return teamMember.getPerson().getPersonName().getMiddleName().toString();
	}
	
	@PropertyGetter("personFamilyName")
	public String getPersonFamilyName(TeamMember teamMember) {
		if (teamMember == null || teamMember.getPerson().getPersonName().getFamilyName() == null) { return ""; }
		return teamMember.getPerson().getPersonName().getFamilyName().toString();
	}
	
	@PropertyGetter("teamHierarchy")
	public String getTeamRoleName(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamHierarchy().getName().toString();
	}
	
	@PropertyGetter("reportTo")
	public String getReportTo(TeamMember teamMember) {
		if (teamMember == null){ return ""; }
		return teamMember.getTeamHierarchy().getReportTo().getName();
	}

	@PropertyGetter("subTeam")
	public String getSubTeam(TeamMember teamMember) {
		List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMember.getPerson().getId());
		if(tm == null) { return ""; }
		else { String teamNames = "";
			for (int j = 0; j < tm.size(); j++) { 
				if(j==tm.size()-1) { teamNames += tm.get(j).getTeam().getTeamName() + ""; }
				else { teamNames += tm.get(j).getTeam().getTeamName() + ", "; }
			} return teamNames;
		}
	}
	
	@PropertyGetter("subTeamHierarchy")
	public String getSubTeamRole(TeamMember teamMember) {
		List<TeamMember> tm = Context.getService(TeamMemberService.class).getTeamMemberByPersonId(teamMember.getPerson().getId());
		if(tm == null) { return ""; }
		else { String teamNames = "";
			for (int j = 0; j < tm.size(); j++) { 
				if(j==tm.size()-1) { teamNames += tm.get(j).getTeamHierarchy().getName() + ""; }
				else { teamNames += tm.get(j).getTeamHierarchy().getName() + ", "; }
			} return teamNames;
		}
	}
}