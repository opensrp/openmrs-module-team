/**
 * 
 */
package org.openmrs.module.teammodule.web.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.GlobalProperty;
import org.openmrs.Location;
import org.openmrs.Person;
import org.openmrs.PersonName;
import org.openmrs.Provider;
import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.Team;
import org.openmrs.module.teammodule.TeamLead;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.teammodule.api.TeamLeadService;
import org.openmrs.module.teammodule.api.TeamMemberService;
import org.openmrs.module.teammodule.api.TeamService;
import org.openmrs.util.OpenmrsConstants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Muhammad Safwan
 * 
 */
@Controller
@RequestMapping(value = "/module/teammodule/teamMemberAddForm")
public class TeamMemberAddForm {
	protected final Log log = LogFactory.getLog(getClass());
	/** Success form view name */
	private final String SUCCESS_FORM_VIEW = "/module/teammodule/teamMemberAddForm";

	// private final String SUCCESS_REDIRECT_LINK =
	// "redirect:/module/teammodule/teamMemberForm/teamMemberForm.form";

	@ModelAttribute("memberData")
	public TeamMember populateTeamMember() {
		TeamMember memberData = new TeamMember();
		Person person = new Person();
		person.setPersonDateCreated(new Date());
		memberData.setPerson(person);
		memberData.getPerson().addName(new PersonName());

		return memberData;
	}

	/**
	 * Initially called after the formBackingObject method to get the landing
	 * form name
	 * 
	 * @return String form view name
	 * @throws IOException
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String showForm(// @ModelAttribute("existingMember") TeamMember
							// teamMember,
			Model model, HttpServletRequest request) throws IOException {

		String error = request.getParameter("error");
		model.addAttribute("error", error);
		String saved = request.getParameter("saved");
		model.addAttribute("saved", saved);
		String teamId = request.getParameter("teamId");
		model.addAttribute("teamId", teamId);
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		Date teamDate = team.getDateCreated();
		model.addAttribute("teamDate", teamDate);
		Location location = team.getLocation();
		model.addAttribute("location", location);
		/*Set<Location> childLocation = location.getChildLocations();
		if (childLocation == null || childLocation.isEmpty()) {
			model.addAttribute("childLocation", location);
		} else {
			model.addAttribute("childLocation", childLocation);
		}*/
		List<Location> allLocations = Context.getLocationService().getAllLocations();
		model.addAttribute("allLocations",allLocations);
		 //model.addAttribute("json", getHierarchyAsJson());
		 model.addAttribute("locationWidgetType",
		 Context.getAdministrationService().saveGlobalProperty( 
				new GlobalProperty(OpenmrsConstants.GLOBAL_PROPERTY_LOCATION_WIDGET_TYPE, "default")));
		return SUCCESS_FORM_VIEW;

	}
	
	/*@Override
	protected Map<String, Object> referenceData(HttpServletRequest request)
			throws Exception {
		Map<String, Object> ret = new HashMap<String, Object>();
		String teamId = request.getParameter("teamId");
		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(teamId));
		Location location = team.getLocation();
		Set<Location> childLocation = location.getChildLocations();
		/*List<LocationTag> tags = Context.getLocationService()
				.getAllLocationTags();*/
		//Collections.sort(childLocation, new MetadataComparator(Context.getLocale()));
		//ret.put("locationTags", childLocation);
		/*ret.put("attributeTypes", Context.getLocationService()
				.getAllLocationAttributeTypes());
		return ret;
	}*/
	
	/*@Override
	protected Object formBackingObject(HttpServletRequest request)
			throws ServletException {

		Location location = null;

		if (Context.isAuthenticated()) {
			LocationService ls = Context.getLocationService();
			String locationId = request.getParameter("locationId");
			if (locationId != null) {
				location = ls.getLocation(Integer.valueOf(locationId));
			}
		}

		if (location == null) {
			location = new Location();

		} 
			return location;
		
	}*/

	/**
	 * Gets JSON formatted for jstree jquery plugin [ { data: ..., children:
	 * ...}, ... ]
	 * 
	 * @return
	 * @throws IOException
	 */
	
	/*private String getHierarchyAsJson() throws IOException {
		// fetch all locations at once to avoid n+1 lazy-loads
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Location loc : Context.getLocationService().getAllLocations()) {
			if (loc.getParentLocation() == null) {
				list.add(toJsonHelper(loc));
			}
		}
		
		// If this gets slow with lots of locations then switch out ObjectMapper for the
		// stream-based version. (But the TODO above is more likely to be a performance hit.)
		StringWriter w = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(w, list);
		return w.toString();
	}*/
	 

	/**
	 * { data: "Location's name (tags)", children: [ recursive calls to this
	 * method, ... ] }
	 * 
	 * @param loc
	 * @return
	 */
	/*private Map<String, Object> toJsonHelper(Location loc) {
		Map<String, Object> ret = new LinkedHashMap<String, Object>();
		StringBuilder sb = new StringBuilder(getName(loc));
		if (loc.getTags() != null && loc.getTags().size() > 0) {
			sb.append(" (");
			for (Iterator<LocationTag> i = loc.getTags().iterator(); i.hasNext();) {
				LocationTag t = i.next();
				sb.append(getName(t));
				if (i.hasNext()) {
					sb.append(", ");
				}
			}
			sb.append(")");
		}
		ret.put("data", sb.toString());
		if (loc.getChildLocations() != null && loc.getChildLocations().size() > 0) {
			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			for (Location child : loc.getChildLocations()) {
				children.add(toJsonHelper(child));
			}
			ret.put("children", children);
		}
		return ret;
	}*/
	
	/*private String getName(BaseOpenmrsMetadata element) {
		String name = StringEscapeUtils.escapeHtml(element.getName());
		name = StringEscapeUtils.escapeJavaScript(name);
		return element.isRetired() ? "<strike>" + name + "</strike>" : name;
	}*/

	/**
	 * All the parameters are optional based on the necessity
	 * 
	 * @param httpSession
	 * @param anyRequestObject
	 * @param errors
	 * @return
	 */

	@RequestMapping(method = RequestMethod.POST)
	public String onSubmit(HttpSession httpSession, @ModelAttribute("anyRequestObject") Object anyRequestObject, HttpServletRequest request,
	/* @RequestParam(value = "teamParam", required = false) String teamId, */@ModelAttribute("memberData") TeamMember teamMember, BindingResult errors, @RequestParam(required = false, value = "userId") Integer userId,
			@RequestParam(required = false, value = "existingPerson") String existingPerson, Model model) {
		//Object obj = null;
		
		if (errors.hasErrors()) {
			// return error view
		}
		
		//System.out.println("Inside");
		
		//System.out.println(teamMember.getLocation());

		/*
		 * model.addAttribute("isNewUser", isNewUser(user)); if (isNewUser(user)
		 * || Context.hasPrivilege(PrivilegeConstants.EDIT_USER_PASSWORDS)) {
		 * model.addAttribute("modifyPasswords", true); }
		 */

		/*
		 * if (createNewPerson != null) { model.addAttribute("createNewPerson",
		 * createNewPerson); }
		 */

		/*
		 * if (!isNewUser(user)) { model.addAttribute("changePassword", new
		 * UserProperties
		 * (user.getUserProperties()).isSupposedToChangePassword()); }
		 */
		String tId = request.getParameter("teamId");
		String pId = request.getParameter("person_id");
		String error = "";

		TeamLead teamLead = new TeamLead();
		Person person = null;
		if (pId == "" || pId == null) {
			 person = Context.getPersonService().savePerson(teamMember.getPerson());
			teamMember.setPerson(person);
		} else {
			 person = Context.getPersonService().getPerson(Integer.parseInt(pId));
			teamMember.setPerson(person);
		}
		//System.out.println("Saved Person");
		/*
		 * if (existingPerson != null) { Person person =
		 * Context.getPersonService().getPerson(Integer.parseInt(pId));
		 * teamMember.setPerson(person); } else { Person person =
		 * Context.getPersonService().savePerson(teamMember.getPerson());
		 * teamMember.setPerson(person); }
		 */

		/*
		 * if (teamMember.getPerson().getGivenName().isEmpty() ||
		 * teamMember.getPerson().getFamilyName().isEmpty()) { error =
		 * "Name and Family Name can't be empty"; model.addAttribute("error",
		 * error); }else{ Person person =
		 * Context.getPersonService().savePerson(teamMember.getPerson());
		 * teamMember.setPerson(person); }
		 */

		Team team = Context.getService(TeamService.class).getTeam(Integer.parseInt(tId));
		teamMember.setTeam(team);
		//teamMember.setLocation(teamMember.getLocation());

		if (teamMember.getJoinDate() == null) {
			teamMember.setJoinDate(new Date());
		} else {

		}
		if (teamMember.getPerson().getDateCreated() == null) {
			teamMember.getPerson().setDateCreated(new Date());
		}
		//System.out.println("Error:"+error);
		if (error.isEmpty()) {

			if (teamMember.getIsTeamLead().booleanValue()) {
				TeamLead tl = Context.getService(TeamLeadService.class).getTeamLead(team);
				if (tl == null) {
					Context.getService(TeamMemberService.class).save(teamMember);
					teamLead.setTeam(team);
					teamLead.setTeamMember(teamMember);
					if (teamMember.getJoinDate() == null) {
						teamLead.setJoinDate(new Date());
					} else {
						teamLead.setJoinDate(teamMember.getJoinDate());
					}
					teamLead.setUuid(UUID.randomUUID().toString());
					Context.getService(TeamLeadService.class).save(teamLead);
				} else {
					error = "Team Lead for this team already exists. ";
					model.addAttribute("error", error);
				}
			}
		}
		model.addAttribute("teamId", tId);
		if (error.isEmpty()) {
			//System.out.println("Inside Member Save");	
			teamMember.setUuid(UUID.randomUUID().toString());
			Context.getService(TeamMemberService.class).save(teamMember);
			String saved = "Member saved successfully";
			//System.out.println("Member Saved");
			Provider provider = new Provider();
			provider.setPerson(person);
			provider.setName(person.getGivenName() + " " + person.getFamilyName());
			provider.setIdentifier(teamMember.getIdentifier());
			
			Context.getProviderService().saveProvider(provider);
			model.addAttribute("saved", saved);
		}
		
		for(int i = 0; i < teamMember.getLocation().size(); i++){
			Integer locationId = teamMember.getLocation().iterator().next().getLocationId();
			//System.out.println(locationId);
			Location location = Context.getLocationService().getLocation(locationId);
			System.out.println(location);
			teamMember.getLocation().add(location);
			
			Context.getService(TeamMemberService.class).saveLocation(location);
			
		}
		
		
		//System.out.println(teamMember.getLocation());
		
		/*try {
			Location location = (Location) obj;
			WebAttributeUtil.handleSubmittedAttributesForType(location, errors, LocationAttribute.class, request, Context.getLocationService().getAllLocationAttributeTypes());

			// System.out.println(request.getParameter("name"));

			List<Location> locationList = Context.getLocationService().getAllLocations();
			for (int i = 0; i < locationList.size(); i++) {
				if (locationList.get(i).getName().equals(request.getParameter("name"))) {
					System.out.println("Match");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
*/
		return "redirect:/module/teammodule/teamMemberAddForm.form?teamId=" + tId;
	}

	// Made command Object (memberData)

}
