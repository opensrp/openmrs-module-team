/**
 * 
 */
package org.openmrs.module.teammodule.extension.html;

import java.util.LinkedHashMap;
import java.util.Map;

import org.openmrs.module.Extension;
import org.openmrs.module.web.extension.AdministrationSectionExt;

/**
 * @author Muhammad Safwan, Shakeeb Raza & Zohaib Masood
 *
 */
public class AdminList extends AdministrationSectionExt {

	/**
	 * @see AdministrationSectionExt#getMediaType()
	 */
	public Extension.MEDIA_TYPE getMediaType() {
		return Extension.MEDIA_TYPE.html;
	}
	
	/**
	 * @see AdministrationSectionExt#getTitle()
	 */
	public String getTitle() {
		return "Team Management Module";
	}
	
	/**
	 * @see AdministrationSectionExt#getLinks()
	 */
	public Map<String, String> getLinks() {
		LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
		map.put("module/teammodule/addTeam.form", "New Team");
		map.put("module/teammodule/teamMemberAddForm.form", "New Team Member");
		map.put("module/teammodule/addRole.form", "New Team Hierarchy (Role)");
		map.put("module/teammodule/team.form", "Manage Teams");
		map.put("module/teammodule/teamMemberView.form", "Manage Team Members");
		map.put("module/teammodule/teamRole.form", "Manage Team Hierarchy (Roles)");
		return map;
	}
}
