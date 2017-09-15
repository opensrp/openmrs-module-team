package org.openmrs.module.webservices.rest.web.v1_0.controller;

import org.openmrs.OpenmrsData;
import org.openmrs.module.webservices.rest.SimpleObject;
import org.openmrs.module.webservices.rest.web.RequestContext;
import org.openmrs.module.webservices.rest.web.resource.impl.DataDelegatingCrudResource;
import org.openmrs.module.webservices.rest.web.response.ObjectNotFoundException;
import org.openmrs.module.webservices.rest.web.response.ResponseException;

public abstract class ComplexDataDelegatingCrudResource<T extends OpenmrsData> extends DataDelegatingCrudResource<T> implements SubResourceable {

	@Override
	public Object subresource(String uuid, String subResource, SimpleObject post, RequestContext context) throws ResponseException {
		T delegate = getByUniqueId(uuid);
		if (delegate == null)
			throw new ObjectNotFoundException();
		
		return subresourceSave(delegate, subResource, post, context);
	}

	public abstract Object subresourceSave(T delegate, String subResource, SimpleObject post, RequestContext context);
}
