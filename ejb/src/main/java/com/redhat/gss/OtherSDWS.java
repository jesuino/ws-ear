package com.redhat.gss;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebService;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.jboss.ws.api.annotation.WebContext;

@WebService
@Stateless
@SecurityDomain("my-sd")
@WebContext(contextRoot="/ws", urlPattern="/*", authMethod="BASIC", secureWSDLAccess=true)
@RolesAllowed("admin")
public class OtherSDWS {
	
	@WebMethod
	public String doSomething() {
		return "Something was done.";
	}

}