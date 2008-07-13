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

package org.impalaframework.facade;

import java.util.ArrayList;
import java.util.List;


public class InteractiveOperationsFacade extends BaseOperationsFacade {

	public InteractiveOperationsFacade() {
		super();
	}
	
	@Override
	protected List<String> getBootstrapContextLocations() {
		List<String> locations = new ArrayList<String>();
		locations.add("META-INF/impala-bootstrap.xml");
		locations.add("META-INF/impala-interactive-bootstrap.xml");
		return locations;
	}
	
}
