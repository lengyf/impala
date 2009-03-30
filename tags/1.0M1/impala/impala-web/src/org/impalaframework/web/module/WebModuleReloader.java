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

package org.impalaframework.web.module;

import javax.servlet.ServletContext;

import org.impalaframework.exception.ConfigurationException;
import org.impalaframework.module.bootstrap.ModuleManagementFactory;
import org.impalaframework.module.definition.ModuleDefinitionSource;
import org.impalaframework.module.operation.ModuleOperation;
import org.impalaframework.module.operation.ModuleOperationConstants;
import org.impalaframework.module.operation.ModuleOperationInput;
import org.impalaframework.web.WebConstants;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.util.Assert;
import org.springframework.web.context.ServletContextAware;

@ManagedResource(objectName = "impala:service=webModuleOperations", description = "MBean exposing reconfiguration of web application")
public class WebModuleReloader implements ServletContextAware {

	private ServletContext servletContext;

	@ManagedOperation(description = "Uses the current ModuleDefintitionSource to perform a full reload of the module hierarchy")
	public void reloadModules() {
		
		Assert.notNull(servletContext);

		ModuleManagementFactory factory = (ModuleManagementFactory) servletContext
				.getAttribute(WebConstants.IMPALA_FACTORY_ATTRIBUTE);
		if (factory == null) {
			throw new ConfigurationException(
					"No instance of "
							+ ModuleManagementFactory.class.getName()
							+ " found. Your context loader needs to be configured to create an instance of this class and attach it to the ServletContext using the attribue WebConstants.IMPALA_FACTORY_ATTRIBUTE");
		}

		ModuleDefinitionSource source = (ModuleDefinitionSource) servletContext
				.getAttribute(WebConstants.MODULE_DEFINITION_SOURCE_ATTRIBUTE);
		if (source == null) {
			throw new ConfigurationException(
					"No instance of "
							+ ModuleDefinitionSource.class.getName()
							+ " found. Your context loader needs to be configured to create an instance of this class and attach it to the ServletContext using the attribue WebConstants.MODULE_DEFINITION_SOURCE_ATTRIBUTE");

		}

		ModuleOperationInput moduleOperationInput = new ModuleOperationInput(source, null, null);
		
		ModuleOperation operation = factory.getModuleOperationRegistry().getOperation(ModuleOperationConstants.ReloadRootModuleOperation);
		operation.execute(moduleOperationInput);
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

}