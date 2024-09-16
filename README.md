# mobsoftstore-spring-boot

A simple software store application created with Spring Boot framework.  
It's the same application as
[mobsoftstore-spring-mvc](https://github.com/nenad-dram/mobsoftstore-spring-mvc)
but with Spring Boot.

## Building and deploying
The application can be packaged either as a .jar or a .war file.  
In both cases the final artifact will be available under project's _build/lib/_ directory.

### JAR
To package as a .jar file from the application directory execute Gradle task _bootJar_.  
E.g. _./gradlew bootJar_ or _gradlew.bat bootJar_ depending upon the OS.

### WAR
To package as a .war file from the application directory execute Gradle task _bootWar_.  
E.g. _./gradlew bootWar_ or _gradlew.bat bootWar_ depending upon the OS.

## Profiles

The following profiles are supported:

- embedded-db - with H2 DB
- main - with PostgreSQL DB
- jndi - for a JNDI-managed DataSource

## Starting

### JAR
To start the app packaged as a .jar file execute the following command:  
`java -jar -Dspring.profiles.active=profile-name mobsoftstore-1.0.jar`  
where profile-name is one of the profiles mentioned in the previous section.  
By default, application will be available on port 8000 (http://localhost:8000/mobsoftstore/)

### WAR
Note: It's assumed that Tomcat server is used as a Servlet container. 
Tomcat 10.* has to be used due to Spring 6/Spring Boot 3 compatibility.

To start the app packaged as a .war file place the file inside the Tomcat's webapp directory,  
and before starting the Tomcat server the desired profile can be set by setting the environment 
variable in the setenv.sh or setenv.bat file (Tomcat's bin directory).  
_export JAVA_OPTS="\$JAVA_OPTS -Dspring.profiles.active=profile-name"_ (setenv.sh)  
_set "JAVA_OPTS=\$JAVA_OPTS -Dspring.profiles.active=profile-name"_ (setenv.bin)  
By default, application will be available on port 8080 (http://localhost:8080/mobsoftstore/)

### JNDI DataSource

A JNDI DataSource named _jdbc/mobsoftstore_ can be used to configure a Container-managed DataSource.
The connection parameters must be placed into context.xml (Tomcat's config directory) within the
existing context element.  
Connection resource example:  
_<Resource name="jdbc/mobsoftstore" auth="Container"
type="javax.sql.DataSource" driverClassName="org.postgresql.Driver"
url="jdbc:postgresql://localhost:5432/storedb"
username="postgres" password="postgres" maxTotal="20" maxIdle="10"
maxWaitMillis="-1" />_

The _jndi_ profile must be used when starting the application,
this will instruct Spring to use the JNDI-provided DataSource.

