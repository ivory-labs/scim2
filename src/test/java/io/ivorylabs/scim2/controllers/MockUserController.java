package io.ivorylabs.scim2.controllers;

import com.unboundid.scim2.common.types.UserResource;
import io.ivorylabs.scim2.annotations.ScimResource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@ScimResource(description = "Access User Resources", name = "User", schema = UserResource.class)
public class MockUserController {

}
