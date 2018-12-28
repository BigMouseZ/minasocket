/**
 * 
 */
package com.signalway.util.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * @author Weign
 *
 */
@Configuration
public class ContextBase {
	@Autowired
	WebApplicationContext webApplicationContext;

	public WebApplicationContext getWebApplicationContext()
	{
		return ContextLoader.getCurrentWebApplicationContext();
	}
	
	public ServletContext getServletContext()
	{
		return webApplicationContext.getServletContext();
	}
	
	public String getWebRootAbsolutePath()
	{
		return webApplicationContext.getServletContext().getRealPath("/");
	}

}
