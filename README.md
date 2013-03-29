Datatype module to make Jackson (http://jackson.codehaus.org) recognize Date/Time types defined in JSR-310.

## Status

Experimental until Jackson 2.2.

## Summary

All JSR310 types are serialized as numbers (integers or decimals as appropriate) if the
[SerializationFeature#WRITE_DATES_AS_TIMESTAMPS](http://fasterxml.github.com/jackson-databind/javadoc/2.2.0/com/fasterxml/jackson/databind/SerializationFeature.html#WRITE_DATES_AS_TIMESTAMPS)
feature is enabled, and otherwise are serialized in standard [ISO-8601](http://en.wikipedia.org/wiki/ISO_8601)
string representation. ISO-8601 specifies formats for representing offset dates and times, zoned dates and times,
local dates and times, periods, durations, and more. All JSR310 types have built-in translation to and from
ISO-8601 formats.

The only exception to this rule is the serialization/deserialization of
[Period](http://download.java.net/jdk8/docs/api/java/time/Period.html), which always
results in an ISO-8601 format because Periods must be represented in years, months, and/or days.

## Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

```xml
<dependency>
    <groupId>com.fasterxml.jackson.datatype</groupId>
    <artifactId>jackson-datatype-jsr310</artifactId>
    <version>2.2.0</version>
</dependency>
```

(or whatever version is most up-to-date at the moment)

### Registering module

As of Jackson 2.2 (this statement is tentative and subject to change before release), Modules self-register using the
Java 6 Service Provider Interface (SPI) feature. You can activate this by instructing an ObjectMapper to discover
Modules:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.discoverModules();
```

If you prefer to selectively register this module, this is done as follows, without the call to `discoverModules()`.:

```java
ObjectMapper mapper = new ObjectMapper();
mapper.registerModule(new JSR310Module());
```

After either of these, functionality is available for all normal Jackson operations.

## More

See [Wiki](jackson-datatype-jsr310/wiki) for more information (javadocs, downloads).
