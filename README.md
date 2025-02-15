SCIM 2.0 SDK for Spring
=================

[SCIM](http://www.simplecloud.info), or _System for Cross-domain Identity Management_, is an IETF standard that defines an extensible schema mechanism and REST API for **managing users and other identity data**. SCIM is used by a variety of vendors — including Okta, Slack and Microsoft.

The BetterCloud SCIM 2.0 SDK provides a set of libraries for creating new SCIM services quickly utilizing Spring.

Table of Contents
-----------------
* [SDK Components](#sdk-components)
* [Installing the Spring Boot Starter](#installing-the-spring-boot-starter)
* [Configuring the ServiceProviderConfig](#configuring-the-service-provider-configuration)
* [Adding Resources](#adding-resources)
* [Version History](#version-history)
* [Development](#development)
* [License](#license)
* [Other Notes](#other-notes)

SDK Components
----------------------------------
| Component name              | What it is | Who needs it                                             |
|-----------------------------| --- |----------------------------------------------------------|
| `scim2-spring-boot-starter` | Spring Boot starter that handle auto configuration of discovery APIs and utilities for configuring REST APIs with Spring Web MVC. | SCIM service provider implementers utilizing Spring Boot. |
| `scim2-sdk-common`          | Shared model, exception, and utility classes. | Included as a compile time dependency of the above.      |

Installing the Spring Boot Starter
----------------------------------
The starter is available from Maven Central, for all modern Java build systems.

Maven:
```
<dependency>
    <groupId>io.ivory-labs</groupId>
    <artifactId>scim2-spring-boot-starter</artifactId>
    <version>1.0.0</version>
    
    <groupId>com.unboundid.product.scim2</groupId>
    <artifactId>scim2-sdk-common</artifactId>
    <version>2.4.0</version>
    <scope>runtime</scope>
</dependency>
```

Gradle:
```
dependencies {
    implementation 'io.ivory-labs:scim2-spring-boot-starter:1.0.0'
    runtimeOnly 'com.unboundid.product.scim2:scim2-sdk-common:2.4.0'
}
```

Configuring the ServiceProviderConfig
-------------------------------------
Every SCIM 2.0 service must support the /ServiceProviderConfig API. The scim2-spring-boot-starter will autoconfigure a Spring Web MVC endpoint given the following properties are provided.

| Property | Required | Description |
| --- | --- | --- |
| scim2.baseUrl | Yes | The url of the server excluding the server context path. |
| scim2.resourcesPackage | No | Defines the package containing the resource controllers. This is optional but is HIGHLY recommended for improving the speed of server initialization.  |
| scim2.service-provider-config.documentationUri | No | An HTTP-addressable URL pointing to the service provider's human-consumable help documentation.  |
| scim2.service-provider-config.patch.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.bulk.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.bulk.maxOperations | No | An integer value specifying the maximum number of operations. DEFAULT 0 |
| scim2.service-provider-config.bulk.maxPayloadSize | No | An integer value specifying the maximum payload size in bytes. DEFAULT 0 |
| scim2.service-provider-config.filter.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT true |
| scim2.service-provider-config.filter.maxResults | No | An integer value specifying the maximum number of resources returned in a response. DEFAULT 100 |
| scim2.service-provider-config.changePassword.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.sort.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.etag.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.etag.supported | No | A Boolean value specifying whether or not the operation is supported. DEFAULT false |
| scim2.service-provider-config.authenticationSchemes | No | Please review the documentation below for more detail on this multi-value property. |

scim2.service-provider-config.authenticationSchemes is a multi-valued property with the following properties defined:  

| Property | Required | Description |
| --- | --- | --- |
| type | Yes | The authentication scheme.  This specification defines the values "oauth", "oauth2", "oauthbearertoken", "httpbasic", and "httpdigest". |
| name | Yes | The common authentication scheme name, e.g., HTTP Basic. |
| description | Yes | A description of the authentication scheme. |
| specUri | No | An HTTP-addressable URL pointing to the authentication scheme's specification. |
| documentationUri | No | An HTTP-addressable URL pointing to the authentication scheme's usage documentation. |

**Sample Configuration**
```YAML
scim2:
  resourcesPackage: io.ivorylabs.scim2.controllers
  baseUrl: https://app.ivory-labs.io
  service-provider-config:
    documentationUri: http://www.simplecloud.info
    patch:
      supported: true
    bulk:
      supported: false
      maxOperations: 1000
      maxPayloadSize: 10000
    filter:
      supported: true
      maxResults: 25
    change-password:
      supported: true
    sort:
      supported: true
    etag:
      supported: true
    authenticationSchemes:
      - name: OAuth Bearer Token
        description: Authentication Scheme using OAuth Bearer Token Standard
        specUri: http://tools.ietf.org/html/draft-ietf-oauth-v2-bearer-01
        documentationUri: http://example.com/help/oauth.html
        type: oauthbearertoken
        primary: true

```

Adding Resources
--------------------------
Any controller annotated with  @ScimResource and @RequestMapping will be detected and will be auto configured as a ResourceTypeDefinition.  If the resource is discoverable the /ResourceTypes and /Schemas discovery APIs will be automatically configured to serve these resource.  
**Example**
```java
@RestController
@RequestMapping("/user")
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
public class UserController {
	// CRUD APIs...
}
```

Version History
---------------
Note that changes to the major version (i.e. the first number) represent possible breaking changes, and
may require modifications in your code to migrate. Changes to the minor version (i.e. the second number)
should represent non-breaking changes. The third number represents any very minor bugfix patches.

* **1.0.0**: Initial public release.  Support for auto configuring ServiceProviderConfig, ResourceType, and Schema discovery APIs.
             
Development
-----------
Pull requests are welcomed for bug fixes or enhancements that do not alter the external facing class and method
signatures. For any breaking changes that would alter the contract provided by this driver, please open up an issue
to discuss it first.

All code changes should include unit tests.  Unit tests are any that can be run in isolation, with no external dependencies.

Unit tests are located under the `src/test` directory, and can be run with the Grade `test` task.

License
-------
This repository contains code branched from the [Staffbase/scim2](https://github.com/staffbase/scim2) project.

Ivory Labs contributions are licensed under the MIT License (MIT).

--

Staffbase/scim2 is a fork from the [BetterCloud/scim2](https://github.com/bettercloud/scim2) project.

Staffbase contributions are licensed under the MIT License (MIT).

--

The BetterCloud SCIM 2.0 SDK contains code branched from the [Ping Identity SCIM 2.0 SDK](https://github.com/pingidentity/scim2).

BetterCloud contributions are licensed under the MIT License (MIT).

--

The UnboundID SCIM2 SDK is available under three licenses: the GNU General Public License version 2 (GPLv2), the GNU Lesser General Public License version 2.1 (LGPLv2.1), and a free-right-to-use license created by UnboundID Corp. See the [LICENSE](LICENSES/LICENSE.txt) file for more info.
