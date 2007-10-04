/*
 * Copyright 2007 the original author or authors.
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
package net.java.impala.command;

import org.springframework.util.Assert;

/**
 * Contains information on a single property required for executing a command
 * 
 * @author Phil Zoio
 */
public class CommandInfo {

	private String shortPropertyName;

	private String propertyName;

	private String description;

	private String requestString;

	private String defaultValue;

	private String[] extraLines;

	private boolean shared;

	private boolean optional;

	private boolean isolated;

	public CommandInfo(String shortPropertyName, String propertyName, String description, String requestString,
			String defaultValue, String[] extraLines, boolean shared, boolean optional, boolean isolated) {
		super();
		this.shortPropertyName = shortPropertyName;
		this.propertyName = propertyName;
		this.description = description;
		this.requestString = requestString;
		this.defaultValue = defaultValue;
		this.extraLines = extraLines;
		this.shared = shared;
		this.optional = optional;
		this.isolated = isolated;
	}

	public String getDescription() {
		return description;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public String getShortPropertyName() {
		return shortPropertyName;
	}

	public String getRequestString() {
		return requestString;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public String[] getExtraLines() {
		return extraLines;
	}

	public boolean isShared() {
		return shared;
	}

	public boolean isOptional() {
		return optional;
	}

	public boolean isIsolated() {
		return isolated;
	}

	public String validate(String input) {
		//note that input will not be called with a non-null, untrimmed value
		Assert.notNull(input);
		return null;
	}

}
