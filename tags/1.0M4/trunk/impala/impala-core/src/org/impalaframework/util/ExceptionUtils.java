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

package org.impalaframework.util;


import java.io.PrintWriter;
import java.io.StringWriter;

import org.impalaframework.exception.CallStackException;

/**
 * <p>
 * Provides utilities for manipulating and examining <code>Throwable</code>
 * objects.
 * </p>
 * {@link #getStackTrace(Throwable)} is based on the Apache Jakarta Commons Lang
 * implementation of this method
 */
public class ExceptionUtils {

	// -----------------------------------------------------------------------
	/**
	 * <p>
	 * Gets the stack trace from a Throwable as a String.
	 * </p>
	 * 
	 * <p>
	 * The result of this method vary by JDK version as this method uses
	 * {@link Throwable#printStackTrace(java.io.PrintWriter)}. On JDK1.3 and
	 * earlier, the cause exception will not be shown unless the specified
	 * throwable alters printStackTrace.
	 * </p>
	 * 
	 * @param throwable the <code>Throwable</code> to be examined
	 * @return the stack trace as generated by the exception's
	 * <code>printStackTrace(PrintWriter)</code> method
	 */
	public static String getStackTrace(Throwable throwable) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw, true);
		throwable.printStackTrace(pw);
		return sw.getBuffer().toString();
	}
	
	public static String callStackAsString() {
		return getStackTrace(new CallStackException());
	}

}
