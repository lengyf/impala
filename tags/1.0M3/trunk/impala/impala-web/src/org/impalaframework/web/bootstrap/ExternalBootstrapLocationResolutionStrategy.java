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

package org.impalaframework.web.bootstrap;

import java.util.Properties;

import javax.servlet.ServletContext;

import org.impalaframework.exception.ConfigurationException;
import org.impalaframework.util.PropertyUtils;
import org.impalaframework.web.WebConstants;
import org.impalaframework.web.module.WebModuleUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.StringUtils;

public class ExternalBootstrapLocationResolutionStrategy extends DefaultBootstrapLocationResolutionStrategy {

	private static final Log logger = LogFactory.getLog(ExternalBootstrapLocationResolutionStrategy.class);
	
	private String defaultBootstrapResource = "impala.properties";

	public String[] getBootstrapContextLocations(ServletContext servletContext) {
		String bootstrapLocationsResource = WebModuleUtils.getLocationsResourceName(servletContext,
				WebConstants.BOOTSTRAP_LOCATIONS_RESOURCE_PARAM);

		ResourceLoader resourceLoader = getResourceLoader();
		Resource bootStrapResource = null;
		
		if (bootstrapLocationsResource == null) {
			bootStrapResource = resourceLoader.getResource(defaultBootstrapResource);
		}
		else {
			// figure out which resource loader to use
			bootStrapResource = resourceLoader.getResource(bootstrapLocationsResource);
		}

		if (bootStrapResource == null || !bootStrapResource.exists()) {
			logger.info("Unable to load locations resource from " + 
					bootstrapLocationsResource + ". Delegating to superclass");
			return super.getBootstrapContextLocations(servletContext);
		}
		Properties loadProperties = PropertyUtils.loadProperties(bootStrapResource);
		String property = loadProperties.getProperty(WebConstants.BOOTSTRAP_LOCATIONS_PROPERTY_PARAM);

		if (property == null) {
			throw new ConfigurationException("Bootstrap location resource '" + bootStrapResource
					+ "' does not contain property '" + WebConstants.BOOTSTRAP_LOCATIONS_PROPERTY_PARAM + "'");
		}

		return StringUtils.tokenizeToStringArray(property, " ,");
	}

	protected ResourceLoader getResourceLoader() {
		return new DefaultResourceLoader();
	}

	void setDefaultBootstrapResource(String defaultBootstrapResource) {
		this.defaultBootstrapResource = defaultBootstrapResource;
	}

}