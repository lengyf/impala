package org.impalaframework.service.registry;

import org.impalaframework.service.registry.internal.ServiceRegistryImpl;
import org.impalaframework.spring.service.registry.DynamicServiceRegistryTargetSource;
import org.springframework.util.ClassUtils;

import junit.framework.TestCase;

public class DynamicServiceRegistryTargetSourceTest extends TestCase {

	private ServiceRegistryImpl serviceRegistry;

	public void testGetTarget() {
		serviceRegistry = new ServiceRegistryImpl();
		DynamicServiceRegistryTargetSource targetSource = new DynamicServiceRegistryTargetSource("mybean", serviceRegistry);
		
		assertNull(targetSource.getServiceRegistryReference());
		
		Object service = new Object();
		serviceRegistry.addService("mybean", "moduleName", service, ClassUtils.getDefaultClassLoader());
		
		assertNotNull(targetSource.getServiceRegistryReference());
		
		serviceRegistry.remove(service);
		
		assertNull(targetSource.getServiceRegistryReference());
	}

}