package io.ivorylabs.scim2.resourcetypes;

import io.ivorylabs.scim2.ResourceTypeDefinition;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.Set;

@RequiredArgsConstructor
public class SimpleResourceTypeRegistry implements ResourceTypeRegistry {

    final Set<ResourceTypeDefinition> resourceTypes;

    @Override
    public Set<ResourceTypeDefinition> getResourceTypeDefinitions() {
        return Collections.unmodifiableSet(resourceTypes);
    }

}
