## Prelude ##

The purpose of this namespace is to make it easier to configure `PropertySource` backed dynamic properties, as described
in the [dynamic properties wiki page](DynamicProperties.md).

## Adding the namespace headers ##

Before using one of the `service` elements in your bean definition file, you will need to add the Impala service namespace declaration
to the root element of the relevant Spring configuration files.

```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans" 
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:dynaprop="http://www.impalaframework.org/schema/dynamicproperties"
       xsi:schemaLocation="
http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
http://www.impalaframework.org/schema/dynamicproperties http://impala.googlecode.com/svn/schema/dynamic-properties.xsd">
```

Note the entry `xmlns:dynaprop="http://www.impalaframework.org/schema/dynamicproperties"` declares the Impala dynamic properties, and the entry
`http://www.impalaframework.org/schema/dynamicproperties http://impala.googlecode.com/svn/schema/dynamic-properties.xsd` associates
this with a schema location.

## An example ##

All the dynamic properties elements have the same basic structure, so we'll consider a simple example using a `BooleanPropertyValue`.

To begin with, we need a `PropertySource` instance which will 'back' or sit behind the various property entries. The
simplest of these is the `StaticPropertiesPropertySource`, which simply uses a wired-in `java.util.Properties` instance.
Other implementations might use a database table, JMX, a dynamically modifiable file system property source, etc.

```
<bean id="propertySource" class="org.impalaframework.config.StaticPropertiesPropertySource">
    <property name="properties">
    	<bean class = "org.springframework.beans.factory.config.PropertiesFactoryBean">
    		<property name="location" value = "classpath:dynamic.properties"/>
    	</bean>
    </property>
</bean>
```

The file _dynamic.properties_ contains an entry as shown below:
```
boolean.property=true
```

Without the property namespace, a dynamic property entry might look as follows:

```
<bean id = "booleanProperty" class="org.impalaframework.config.FloatPropertyValue">
	<property name="name" value="boolean.property"/>
	<property name="propertySource" ref="propertySource"/>
</bean>
```

Using the namespace, it is shown as below:

```
<dynaprop:boolean id = "booleanProperty" name="boolean.property" propertySource="propertySource" />
```

## Common elements ##

Each of the dynamic property elements have a common set of attributes:
  * `id`: the id of the bean. Only required if the bean is used in a standalone way.
  * `name`: the name of the property being accessed. In our example above, the name needs to be `boolean.property` to access
> the associated value in the _dynamic.properties_ file.
  * `propertySource`: the backing `PropertySource` instances.

As of Impala 1.0.1 it will also be possible to set a default value for the entry, using a `defaultValue` attribute.

## The elements ##

### boolean ###

Creates an instance of `BooleanPropertyValue` which can be injected into a bean requiring dynamically updatable properties.

```
<property name="booleanPropertyValue">
	<dynaprop:boolean name="boolean.property"	propertySource="propertySource" />
</property>
```

## date ##

Creates an instance of `DatePropertyValue`.

```
<property name="datePropertyValue">
	<dynaprop:date name="date.property" propertySource="propertySource" pattern="yyyy-MM-dd" />
</property>
```

## double ##

Creates an instance of `DoublePropertyValue`.

```
<property name="doublePropertyValue">
	<dynaprop:double name="double.property"	propertySource="propertySource" />
</property>
```

## float ##

Creates an instance of `FloatPropertyValue`.

```
<property name="floatPropertyValue">
	<dynaprop:float name="float.property" propertySource="propertySource" />
</property>
```

## int ##

Creates an instance of `IntPropertyValue`.

```
<property name="intPropertyValue">
	<dynaprop:int name="int.property" propertySource="propertySource" />
</property>
```

## long ##

Creates an instance of `LongPropertyValue`.

```
<property name="longPropertyValue">
	<dynaprop:long name="long.property" propertySource="propertySource" />
</property>
```

## string ##

Creates an instance of `StringPropertyValue`.

```
<property name="stringPropertyValue">
	<dynaprop:string name="string.property"	propertySource="propertySource" />
</property>
```