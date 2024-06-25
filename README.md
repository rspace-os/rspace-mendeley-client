## Mendeley API project

This project provides Java bindings and an abstraction over the Mendeley REST-API.

It comprises

* An API package with Java methods to invoke Mendeley web API operations, to be used by clients
* Implementations using Spring MVC Rest template to perform the API invocations
* An implementation of SpringSocial's Connection mechanism, which provides support for OAuth2 and to facilitate integration into web applications. 

### Getting started

#### Register yourself for Mendeley API application

* Create a regular Mendeley user account for yourself, and add a new document - any document will do.
* Go to http://dev.mendeley.com/myapps.html, and register a new application for yourself.
* Go to the site https://mendeley-show-me-access-tokens.herokuapp.com/ and get yourself a refresh key and an access key.

#### Set up an external property file for storing keys, ids etc
<b>Integration tests are currently ignored while we implement externalising of secrets.</b>
* Create a file username.properties (see rspace.properties as an example) in src/test/resources/users in this project, and set properties for your id and secret. Replace 'username' with the username of your system username on the  machine you run these tests on.
* Also, add this refresh key to your properties file.
* You should now be able to run the integration tests, which make API calls to Mendeley using the refresh key to get a new authorization key on each test run.