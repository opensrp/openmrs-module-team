/**
 * 
 */
package org.openmrs.module.teammodule.page.controller;

import javax.servlet.http.HttpServletRequest;

import org.openmrs.api.EncounterService;
import org.openmrs.ui.framework.page.PageModel;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Muhammad Safwan
 *
 */

public class TeamPageController {
	
	public void controller( PageModel model, HttpServletRequest request, EncounterService service, @RequestParam(value = "patientId", required = false) Integer patientId) {
		
		
	}

}
