The properties described on this page are used to set the built-in configuration options, typically found in _impala.properties_,
but which can also be set using system properties, and in web applications, using _web.xml_ `init-parameter` entries.

### Core Properties ###

The following properties are relevant for both standalone and web Impala runtime environments.


---


#### Spring context location properties ####

**all.locations**: A comma or space separated list of context locations. If this property is set, then other properties which would otherwise
be used to determine which Impala application context XML files to include will be ignored. The value set in this file is taken as
the truth. Note that if the value does not end in _.xml_, the it is assumed to take the form _impala-suppliedname.xml_. In other words,
_impala-_ is used as a prefix, and _.xml_ is used as a suffix. See BootstrapContexts for more details on how these context locations are used.

**extra.locations**: Used to add additional context locations. Use when you want simply want to add one more context locations to the set which would
otherwise be used. Note that if the value does not end in .xml, the it is assumed to take the form impala-suppliedname.xml. In other words,
_impala-_ is used as a prefix, and _.xml_ is used as a suffix. See BootstrapContexts for more details on how these context locations are used.


---


#### Proxy properties ####

**proxy.allow.no.service**: Property which applies for creation of proxies. True if the interceptor should allow the call to proceed (with a dummy value returned) if no service is present. Primarily present for testing purposes. Defaults to false.

**proxy.set.context.classloader:** Property which applies for creation of proxies. Whether to set the context class loader to the class loader of the module which contributed the service bean being invoked.

Set this to `true` if you are using frameworks which are relying on the thread's context class loader being correctly set _after_ module loading has taken place. For example, if you have modules which are dynamically loading classes after the initial module load. Spring is pretty good at setting the context class loader correctly during an application context refresh. This property makes sure that it is set correctly after any subsequent invocation.

**proxy.missing.service.retry.count:** Property which applies for creation of proxies. The number of times the interceptor should retry before giving up attempting to obtain a proxy. For example, if `proxy.missing.service.retry.count` is set to three, Impala will try one initially, plus another three times, before giving up attempting to obtain the service reference.

**proxy.missing.service.retry.interval:** Property which applies for creation of proxies. The amount of time in milliseconds between successive retries if `proxy.missing.service.retry.count` is greater than zero.


---


#### Classloader properties ####

**classloader.type**: The module management type to be used. Permissible values are _shared_, _hierarchical_ and _graph_.
The default value is _graph_.

**graph.bean.visibility.type**: Indicates the visibility type of Spring beans when using the graph class
loader. Permissible values are _none_, _graphOrdered_,
_parentFirst_ and _parentOnly_.

**parent.classloader.first**: This is used to specify whether Impala should attempt to load classes by first checking the application or system class path,
before examining the module class path. The default is _true_.


---


### Web Properties ###

The following properties are relevant when running Impala as a web application.

#### 'auto-reload' properties ####

**auto.reload.modules**: Whether modules should be automatically reloaded if the file/directory
containing the modules has modified. If true, a background thread will
monitor module files/folders and automatically reload modules when
changes are detected. Default is `false`.

**use.touch.file**: Whether to use a touch file to detect changes which could trigger a module reload.
If true, then will only check files for modifications if a modification in the
timestamp of the touch file is detected.

**touch.file**: The touch file resource which will be monitored. If not present and `use.touch.file`
is true, then `auto.reload.modules` is effectively turned off.

**auto.reload.extension.includes**: The extensions which are explicitly included when determining the last
modification timestamp for auto reloading modules. If specified, any file which ends with one of the extensions specified will be included
in the last modification timestamp check. For example, if `auto.reload.extension.includes` is set to `class,xml`, only Java class files and
XML files will be checked for modifications. If, for example, only a property file in the module is changed, the module won't be reloaded.
This property is useful for helping to ensure that modules don't reload too 'eagerly'.

**auto.reload.extension.excludes**: The extensions which are explicitly excluded when determining the last modification timestamp for auto-reload.
> For example, if `auto.reload.extension.excludes` is set to `sql`, and a file in the module ending with 'sql' is changed, the
module won't be reloaded. Like `auto.reload.extension.includes`, this property is also useful for helping to ensure that modules don't reload too 'eagerly'.

**auto.reload.check.interval**: The period in seconds between successive checks for modifications when
`auto.reload.modules` is used.

**auto.reload.check.delay**: The delay in seconds before the first check for modifications when
`auto.reload.modules` is used.

**auto.reload.monitoring.type**: The strategy used for selecting files to monitor when 'auto.reload.modules'
is used. The default value is `default` which entails simply monitoring the
files on the module's local classpath. An alternative strategy is `tmpfile`. In this case, Impala
will look for the module jar file, and will monitor a file with the same name as the jar file, but with
the extension `.tmp` instead of `.jar`. When a changed module is detected, the jar file is replaced by the
temp file during the module loading process.

The advantage of using the `tmpfile` strategy is that module file copying is integrated within the module
loading process, meaning that it will not interfere with module loading. It also prevents Impala attempting
to reload partially modified module jar files.

Together with the use of the touch file, the temp file mechanism allows for a robust auto-reload
mechanism.

**enforce.reloadability** (from 1.0.3): if set to `true`, then any module for with the _module.property_ entry
`reloadable=false`, and any of its ancestors/dependencies, is not subject to auto-reloading (nor can it it
be explicitly reloaded via JMX).

---


#### Miscellaneous web properties ####

**external.root.module.name**: (forthcoming in 1.0M7) The module whose application context should be interpreted as the root module by
external applications. This allows it to be published in web applications as the Spring root web application context.
The default is an empty String, which is interpreted as simply to use the name of the Impala root module. This
property can be useful if you want to allow, for example, the top level web module to be published as the root,
instead of the Impala root module.

**embedded.mode**: This property must be set to true if you want to run a web application
embedded within the IDE, without any specific packaging required.
Suitable for a fast develop/deploy/test cycle. The alternative involves
creating a war file with modules placed in jars files in
_WEB-INF/modules_. This is the default, and is the valid production
setting.

**partitioned.servlet.context**: Whether `ServletContext` attributes and resources should be partitioned
across modules. This makes it possible to set attributes and make resources
visible within modules only through {@link ServletContext} methods.

**session.module.protection**: Attempt to protect session state when module is reloaded. If class loader
which loaded original object in session is replaced, the object in
session will no longer be visible to application code once module is
reloaded. The solution is to clone the object via Java serialization,
replacing the class loader of session object during the cloning process.
The cloned object is then added to the session in place of the old object
with the stale class loader.

**preserve.session.on.reload.failure**: If a session attribute is lost because it cannot be "reloaded" maintain the
existing session. Default is `true`.

**web.module.prefix**:
The portion of the path which is ignored for the purpose of module selection when dynamically
mapping requests to modules. For example, if a module name is `webframeworks-webflow`, then the
URL _http://localhost:8080/myapp/webflow/home.do_ will be mapped to the `webframeworks-webflow` module.
Without this property set, then you would need to use the URL _http://localhost:8080/myapp/webframeworks-webflow/home.do_.
to map to the `webframeworks-webflow` module.

**module.prefix.mapping.enabled**: If true, then Impala supports a mechanism which allows modules to "subscribe" to
particular URLs by prefix, using a `org.impalaframework.web.spring.integration.ModuleUrlPrefixContributor`
declaration in the module's application context.

Note: this property will appear in 1.0 M7.

**top.level.module.prefixes**:

If `top.level.module.path.enabled` is set to `true`, then this contains the comma-separated
list of prefixes which is used to direct requests to modules based on the top level path of the URL.
For example, if the value for `top.level.module.prefixes` is `module1,module2`,
then a request with the URL _http://localhost:8080/Module2resource_ will be directed to the module
`module2`. Note that the prefix is case insensitive.

For MVC- or request-based web frameworks such as Spring MVC, this kind of module selection will typically not be necessary, as
most of these give you a great deal of control over how you map requests to controllers or actions.
However, for some component based frameworks, much more restrictive assumptions are made about how
particular URLs map to elements of the application.

**Note**
At this point you may wish to consider enabling your own scheme for module selection.
Here, it is simply a case of following the instructions in `ExtendingImpala`, and providing an
implementation of the [http://impala.googlecode.com/svn/trunk/impala/impala-web/src/org/impalaframework/web/integration/RequestModuleMapper.java](RequestModuleMapper.md)
interface. An alternative implementation may be based on cookies or URL rewriting with a request parameter,
or may be based on an explicit selection with the selected value held in the session.


---


### JMX Properties ###

**expose.jmx.operations**: if set to true, then Impala's JMX operations are exposed. If set to false, then no
Impala-specific beans will be exported. Default is `true`.

**jmx.locate.existing.server**: Spring provides a mechanism which allows for an existing MBean server to be located. This is useful,
in particular, when running Impala in an application server which hosts a JMX environment. One limitation however, is that
only one Impala application which uses JMX can be configured in this way on a single application server instance. The reason is that
each of Impala application will attempt to use the same JMX bean name for the respective expored JMX beans. Defaults to `false`.

**jmx.prefer.platform.mbean.server**: (1.0.2 onwards). Applies if `jmx.locate.existing.server` is true. If true, then attempts to reuse platform MBean server, rather than locating an MBean server which might have been created by the application or perhaps the application server, by calling `ManagementFactory.getPlatformMBeanServer()`.

**expose.mx4j.adaptor**: Set this property to true to use the MX4J console. The MX4J classes must be present on the class path. If you are
using the application server's native JMX console via the `jmx.locate.existing.server` property, then you probably won't want to set this
property. Defaults to `false`.

**jmx.adaptor.port**: If the MX4J console is used, it can be accessed via HTTP server using a separate port. Use this property to change
the port used from the default of `8001`.


---


### Build Properties ###

The following properties relate to the Impala build configuration. Unlike the other property sets above, they are set in _build.properties_.

**workspace.root**: The module root directory, used as the base location when looking for module jars or directories.

**module.class.dir**: The directory or jar relative to `workspace.root` in which to find modules.

**module.test.dir**: The version of the application. Used, for example, when searching for module jar files. For example, if version is 1.1
then `mymodule` may be found in a jar file called `mymodule-1.1.jar`.