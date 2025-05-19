package io.ivorylabs.scim2.resourcetypes;

import io.ivorylabs.scim2.ResourceTypeDefinition;

import java.util.Set;

public interface ResourceTypeRegistry {

    Set<ResourceTypeDefinition> getResourceTypeDefinitions();

}
