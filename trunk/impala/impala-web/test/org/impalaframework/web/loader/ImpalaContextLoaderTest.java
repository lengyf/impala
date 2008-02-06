package org.impalaframework.web.loader;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;

import junit.framework.TestCase;

import org.easymock.EasyMock;
import org.impalaframework.module.definition.ModuleDefinitionSource;
import org.impalaframework.module.definition.RootModuleDefinition;
import org.impalaframework.web.WebConstants;
import org.impalaframework.web.loader.WebXmlBasedContextLoader;

public class ImpalaContextLoaderTest extends TestCase {

	private WebXmlBasedContextLoader contextLoader;
	private ServletContext servletContext;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		contextLoader = new WebXmlBasedContextLoader();
		servletContext = createMock(ServletContext.class);
	}

	public void testBootstrapLocations() throws Exception {
		String[] locations = contextLoader.getBootstrapContextLocations(EasyMock.createMock(ServletContext.class));
		assertTrue(locations.length > 0);
	}

	public void testGetModuleDefinition() {
		expect(servletContext.getInitParameter(WebXmlBasedContextLoader.CONFIG_LOCATION_PARAM)).andReturn(
				"context1.xml, context2.xml");
		expect(servletContext.getInitParameter(WebConstants.MODULE_NAMES_PARAM)).andReturn("p1, p2, p3");

		WebXmlBasedContextLoader contextLoader = new WebXmlBasedContextLoader();

		replay(servletContext);

		ModuleDefinitionSource builder = contextLoader.getModuleDefinitionSource(servletContext);
		RootModuleDefinition rootModuleDefinition = builder.getModuleDefinition();

		List<String> list = new ArrayList<String>();
		list.add("context1.xml");
		list.add("context2.xml");

		assertEquals(list, rootModuleDefinition.getContextLocations());

		assertTrue(Arrays.equals(new String[] { "p1", "p2", "p3" }, rootModuleDefinition.getModuleNames().toArray(new String[3])));

		verify(servletContext);
	}

	public void testGetChildModuleDefinitionString() {

		expect(servletContext.getInitParameter(WebConstants.MODULE_NAMES_PARAM)).andReturn("plugin1, plugin2");

		replay(servletContext);
		assertEquals("plugin1, plugin2", contextLoader.getModuleDefinitionString(servletContext));
		verify(servletContext);
	}

}
