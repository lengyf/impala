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

import org.impalaframework.resolver.LocationConstants;

import junit.framework.Test;
import junit.framework.TestSuite;
import tests.AlternativeWineMerchantTest;
import tests.InitialIntegrationTest;
import tests.WineDAOTest;
import tests.WineMerchantTest;
import tests.WineProjectMerchantTest;

public class AutomatedWineorderTests {

	public static Test suite() {
		System.setProperty(LocationConstants.WORKSPACE_ROOT_PROPERTY, "../wineorder-sample/");
		
		TestSuite suite = new TestSuite();
		//note some of these tests are repeated to simulated a larger test suite
		//and the effects of reloading/unloading
		suite.addTestSuite(InitialIntegrationTest.class);
		suite.addTestSuite(InProjectWineDAOTest.class);
		suite.addTestSuite(WineDAOTest.class);
		suite.addTestSuite(WineMerchantTest.class);
		suite.addTestSuite(AlternativeWineMerchantTest.class);
		suite.addTestSuite(InitialIntegrationTest.class);
		suite.addTestSuite(WineMerchantTest.class);
		suite.addTestSuite(WineProjectMerchantTest.class);
		suite.addTestSuite(WineDAOTest.class);
		return suite;
	}
}