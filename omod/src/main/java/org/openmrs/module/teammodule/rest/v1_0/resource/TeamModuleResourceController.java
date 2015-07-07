/**
 * 
 */
package org.openmrs.module.teammodule.rest.v1_0.resource;

/*import org.openmrs.module.webservices.rest.web.RestConstants;
 import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;*/
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//import org.openmrs.module.webservices.rest.web.v1_0.*;

/**
 * @author Muhammad Safwan
 * 
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE)
public class TeamModuleResourceController extends MainResourceController {

	public static final String TEAMMODULE_NAMESPACE = "/teammodule";

	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + TEAMMODULE_NAMESPACE;
	}

}
