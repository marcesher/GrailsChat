This demonstrates the [Atmosphere](https://github.com/Atmosphere/atmosphere/) [chat sample](https://github.com/Atmosphere/atmosphere/wiki/Getting-Started-with-the-samples), but within a Grails application.

The JavaScript is virtually identical; the Grails service uses a slightly modified version of Atmosphere plugin and is essentially a copy/paste/grails-ize job.

# Modifications

1. In your Atmosphere plugin's `dependencies.groovy` file, in the compile dependency, change the version to `org.atmosphere:atmosphere-runtime:1.0.0.beta5`

2. Replace the `jquery.atmosphere.js` file with the latest one in [Stephane's repo](https://github.com/smaldini/grails-atmosphere/tree/master/web-app/js/jquery)

# Tomcat Configuration

Using the latest Tomcat plugin, run-app should just work.

When deploying to your own Tomcat, you'll need to enable the NIO connector

1. In `server.xml`, change the HTTP/1.1 connector protocol to `protocol="org.apache.coyote.http11.Http11NioProtocol"`.
2. Eventually, you'll want to [read more about configuring this connector](http://tomcat.apache.org/tomcat-7.0-doc/config/http.html#NIO_specific_configuration)

# Notable Config Settings

In `config.groovy`, note I've set `tomcat.nio=true`. You'll want to do the same in your applications that use websockets for use with the Tomcat plugin (i.e. run-app)

