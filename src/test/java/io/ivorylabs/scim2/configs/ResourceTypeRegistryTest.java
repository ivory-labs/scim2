package io.ivorylabs.scim2.configs;

import io.ivorylabs.scim2.ResourceTypeDefinition;
import io.ivorylabs.scim2.TestApplication;
import io.ivorylabs.scim2.resourcetypes.ResourceTypeRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {TestApplication.class, ResourceTypeRegistryConfiguration.class})
public class ResourceTypeRegistryTest {

    @Autowired
    private ResourceTypeRegistry resourceTypeRegistry;

    @Autowired
    private Set<ResourceTypeDefinition> resourceTypeDefinitions;

    @Test
    public void validateResourceTypeRegistry() {
        assertEquals(resourceTypeRegistry.getResourceTypeDefinitions(), resourceTypeDefinitions);
    }

    @Test
    public void validateResourceTypeDefinitions() {
        assertEquals(4, resourceTypeDefinitions.size());
        assertTrue(resourceTypeDefinitions.stream()
                .anyMatch(resourceTypeDefinition -> resourceTypeDefinition.getName().equals("ServiceProviderConfig")));
        assertTrue(resourceTypeDefinitions.stream().anyMatch(resourceTypeDefinition -> resourceTypeDefinition.getName().equals("Schema")));
        assertTrue(resourceTypeDefinitions.stream().anyMatch(resourceTypeDefinition -> resourceTypeDefinition.getName().equals("ResourceType")));
        assertTrue(resourceTypeDefinitions.stream().anyMatch(resourceTypeDefinition -> resourceTypeDefinition.getName().equals("User")));
    }

}
