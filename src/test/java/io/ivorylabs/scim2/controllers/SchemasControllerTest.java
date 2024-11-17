package io.ivorylabs.scim2.controllers;

import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.ScimResource;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.messages.ListResponse;
import io.ivorylabs.scim2.controllers.discovery.SchemasController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SchemasControllerTest {

    @Autowired
    private SchemasController schemasController;

    @Test
    public void search() throws ScimException {
        final ListResponse<GenericScimResource> response = schemasController.search(null);
        assertNotNull(response);
    }

    @Test
    public void getSchemaById() throws ScimException {
        final ScimResource response = schemasController.get("urn:ietf:params:scim:schemas:core:2.0:User");
        assertNotNull(response);
        assertEquals("urn:ietf:params:scim:schemas:core:2.0:User", response.getId());
    }

}
