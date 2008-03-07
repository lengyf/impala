/*
 * Copyright 2007-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.impalaframework.web.loader;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.impalaframework.exception.ConfigurationException;
import org.impalaframework.util.PropertyUtils;
import org.impalaframework.web.WebConstants;
import org.impalaframework.web.bootstrap.ExternalBootstrapLocationResolutionStrategy;
import org.impalaframework.web.module.WebModuleUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

/**
 * Allows you to specify bootstrapLocationsResource, either as a system property or as
 * a servlet context init parameter named WebConstants.BOOTSTRAP_LOCATIONS_RESOURCE_PARAM.
 * If found, then this resource is loaded as a property file and used to find the
 * parent context locations as well as the child module names for bootstrapping the 
 * application
 * @author Phil Zoio
  */
public class ConfigurableWebXmlBasedContextLoader extends WebXmlBasedContextLoader {

	private static final Log logger = LogFactory.getLog(ConfigurableWebXmlBasedContextLoader.class);
	
	@Override
	public String[] getBootstrapContextLocations(ServletContext servletContext) {
		return new ExternalBootstrapLocationResolutionStrategy().getBootstrapContextLocations(servletContext);
	}

	@Override
	protected String getModuleDefinitionString(ServletContext servletContext) {
		String bootstrapLocationsResource = WebModuleUtils.getLocationsResourceName(servletContext, WebConstants.BOOTSTRAP_MODULES_RESOURCE_PARAM);
		if (bootstrapLocationsResource == null) {
			return super.getModuleDefinitionString(servletContext);
		}
		else {
			ResourceLoader resourceLoader = getResourceLoader();
			Resource bootStrapResource = resourceLoader.getResource(bootstrapLocationsResource);

			if (bootStrapResource == null || !bootStrapResource.exists()) {
				logger.info("Unable to load locations resource from " + 
						bootstrapLocationsResource + ". Delegating to superclass");
				return super.getModuleDefinitionString(servletContext);
			}
			Properties loadProperties = PropertyUtils.loadProperties(bootStrapResource);
			String property = loadProperties.getProperty(WebConstants.MODULE_NAMES_PARAM);

			if (property == null) {
				throw new ConfigurationException("Bootstrap location resource '" + bootStrapResource
						+ "' does not contain property '" + WebConstants.MODULE_NAMES_PARAM + "'");
			}

			return property;
		}
	}
	
	protected String[] getParentLocations(ServletContext servletContext) {
		String bootstrapLocationsResource = WebModuleUtils.getLocationsResourceName(servletContext, WebConstants.BOOTSTRAP_MODULES_RESOURCE_PARAM);
		if (bootstrapLocationsResource == null) {
			return super.getParentLocations(servletContext);
		}
		else {
			ResourceLoader resourceLoader = getResourceLoader();
			Resource bootStrapResource = resourceLoader.getResource(bootstrapLocationsResource);

			if (bootStrapResource == null || !bootStrapResource.exists()) {
				logger.info("Unable to load locations resource from " + bootstrapLocationsResource 
						+ ". Delegating to superclass");
				return super.getParentLocations(servletContext);
			}
			Properties loadProperties = PropertyUtils.loadProperties(bootStrapResource);
			String property = loadProperties.getProperty(WebConstants.PARENT_LOCATIONS);

			if (property == null) {
				throw new ConfigurationException("Bootstrap location resource '" + bootStrapResource
						+ "' does not contain property '" + WebConstants.PARENT_LOCATIONS + "'");
			}

			return StringUtils.tokenizeToStringArray(property, " ,");
		}
	}

	protected ResourceLoader getResourceLoader() {
		return new DefaultResourceLoader();
	}

}
