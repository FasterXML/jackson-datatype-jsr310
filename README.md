Datatype module to make Jackson (http://jackson.codehaus.org) 
recognize Date/Time types defined in JSR-310

## Status

Experimental until Jackson 2.2.

## Usage

### Maven dependency

To use module on Maven-based projects, use following dependency:

    <dependency>
      <groupId>com.fasterxml.jackson.datatype</groupId>
      <artifactId>jackson-datatype-jsr310</artifactId>
      <version>2.2.0</version>
    </dependency>    

(or whatever version is most up-to-date at the moment)

### Registering module


Like all standard Jackson modules (libraries that implement Module interface), registration is done as follows:

    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JSR310Module());

after which functionality is available for all normal Jackson operations.

## More

See [Wiki](jackson-datatype-jsr310/wiki) for more information (javadocs, downloads).
