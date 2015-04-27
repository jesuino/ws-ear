# A Web Application secured with a WS EJB in an EAR

This is a example to show how to use a security domain for a Web Application and a different security domain for a Web Service in an EJB.
It should run on JBoss EAP 6.x or WildFly.

# About the example

You will find an ear with a Web and EJB application. 

* The Web app has a public context, that will not ask for a credential: `http://localhost:8080/ws-ear-web/public`
* It also has a private context, which requires authentication in the `other` security domain (which users can be added using bin`/add-user.sh`) using an user that is has the `admin` role: `http://localhost:8080/ws-ear-web/private`
* The EJB App has an EJB Web Service which is secured by the `my-sd` security domain and only allows authenticated users with `admin` role. Its WSDL can is in URL: `http://localhost:8080/ws?wsdl`

# Configuring JBoss EAP 6.x

The `other` security domain is available by default in JBoss EAP 6.x, but you must add users to the `Application Realm` and make sure these users are in the `admin` role;
The `my-sd` security domain must be added. Here is how you can do this:

* First edit `$JBOSS_HOME/standalone/configuration/standalone.xml` to add a new security domain:

~~~
<security-domain name="my-sd" cache-type="default">
    <authentication>
	<login-module code="org.jboss.security.auth.spi.UsersRolesLoginModule" flag="optional">
	    <module-option name="usersProperties" value="${jboss.server.config.dir}/my-sd-users.properties"/>
	    <module-option name="rolesProperties" value="${jboss.server.config.dir}/my-sd-roles.properties"/>
	</login-module>
    </authentication>
</security-domain>
~~~ 

* Following, create the files `my-sd-users.properties` and `my-sd-roles.properties` in `standalone/configuration` directory. For example, to create the files for an user `admin` with `admin123` in role `admin` and `user`, create the files with the following content: 
**my-sd-users.properties**
~~~ 
admin=admin123
~~~ 
**my-sd-roles.properties**
~~~ 
admin=admin
~~~ 

# Building the example

Run `mvn clean package` in the root directory of this project (`ws-ear`) and find the build EAR in `ws-ear/ear/target/ws-ear.ear`. It can be deployed in JBoss EAP 6.x.
