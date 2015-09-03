/**
 * 
 */
package org.openmrs.module.teammodule.web.v1_0.search;

import java.util.Arrays;
import java.util.List;

import org.openmrs.api.context.Context;
import org.openmrs.module.teammodule.TeamMember;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.RestConstants;
import org.openmrs.module.webservices.rest.web.resource.api.PageableResult;
import org.openmrs.module.webservices.rest.web.resource.api.SearchConfig;
import org.openmrs.module.webservices.rest.web.resource.api.SearchHandler;
import org.openmrs.module.webservices.rest.web.resource.api.SearchQuery;
import org.openmrs.module.webservices.rest.web.response.ResponseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Safwan
 *
 */

@Component
public class MemberLocationSearchHandler implements SearchHandler {
	
	private final SearchConfig searchConfig = new SearchConfig("default", RestConstants.VERSION_1 + "/location/search", Arrays.asList("1.8.*", "1.9.*", "1.10.*", "1.11.*", "1.12.*"),
	        Arrays.asList(new SearchQuery.Builder("Allows you to find members based on a particular location").withRequiredParameters("source").build()));

	@Override
	public SearchConfig getSearchConfig() {
		return searchConfig;
	}

	@Override
	public PageableResult search(RequestContext context)
			throws ResponseException {
		System.out.println("Inside");
		String source = context.getParameter("source");
		System.out.println(source);
		
		System.out.println(Context.getLocationService().getLocationByUuid(source));
		List<TeamMember> member = null;
		//System.out.println(member);
		return null;
	}

}
