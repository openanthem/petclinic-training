Petclinic setup instructions
=======================
 

## Steps to build
* Build cloud-config: mvn clean install (optionally add -DskipTests=true)
* Build platform.extension.client.petclinic: mvn clean install (optionally add -DskipTests=true)
* Build petclinic-gateway: mvn clean install (optionally add -DskipTests=true)
* Build petclinic.web: mvn clean install (optionally add -DskipTests=true)

## To start the spring boot applications
* Run cloud-config as a spring boot application
* Run petclinic-gateway as a spring boot application
* Run petclinic-web as a spring boot application
* Run nimbus-ui in 4200.  (npm start)


## To open the app in a browser
* Hit "http://localhost:8080/petclinic#/h/petclinicdashboard/vpDashboard"


Please note:  There will be sock-js errors in the console when the ui app is served in-memory (ng serve) which can be ignored.  These are spit by webpack (used by angular internally) when the requests are from a proxy rather than from a web browser. We need to eject webpack and change its configuration if we need these errors suppressed.

These are not seen when backed by a production quality servers like express.
