/**
 * 
 */
package org.openmrs.module.teammodule.rest.v1_0.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.v1_0.controller.BaseUriSetup;
import org.openmrs.module.webservices.rest.web.v1_0.controller.MainResourceController;
import org.openmrs.module.webservices.rest.web.v1_0.controller.SubResourceable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestUtil;
import org.openmrs.module.webservices.rest.web.api.RestService;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Muhammad Safwan
 * 
 */

@Controller
@RequestMapping("/rest/" + RestConstants.VERSION_1 + TeamModuleResourceController.TEAMMODULE_NAMESPACE)
public class TeamModuleResourceController extends MainResourceController {
	@Autowired
	RestService restService;
	
	@Autowired
	BaseUriSetup baseUriSetup;
	
	public static final String TEAMMODULE_NAMESPACE = "/team";

	@Override
	public String getNamespace() {
		return RestConstants.VERSION_1 + TEAMMODULE_NAMESPACE;
	}
	
	@RequestMapping(value = "/{resource}/{uuid}/{subresource}", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@PathVariable("resource") String resource, @PathVariable("uuid") String uuid,
			@PathVariable("subresource") String subresource, 
	        @RequestBody SimpleObject post, HttpServletRequest request, HttpServletResponse response)
	        throws ResponseException {
		baseUriSetup.setup(request);
		RequestContext context = RestUtil.getRequestContext(request, response);
		SubResourceable res = (SubResourceable) restService.getResourceByName(buildResourceName(resource));
		Object updated = res.subresource(uuid, subresource, post, context);
		return RestUtil.updated(response, updated);
	}

}
