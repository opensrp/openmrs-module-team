package org.openmrs.module.webservices.rest.web.v1_0.controller;

import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.api.Resource;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

public interface SubResourceable extends Resource{

	public Object subresource(String uuid, String subResource, SimpleObject post, RequestContext context) throws ResponseException;

}
