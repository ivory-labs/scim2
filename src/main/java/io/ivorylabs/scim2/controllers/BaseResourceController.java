package io.ivorylabs.scim2.controllers;

import com.unboundid.scim2.common.ScimResource;
import com.unboundid.scim2.common.types.AttributeDefinition;
import io.ivorylabs.scim2.BaseUrlProvider;
import io.ivorylabs.scim2.ResourceTypeDefinition;
import io.ivorylabs.scim2.converters.GenericScimResourceConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class BaseResourceController<RESOURCE extends ScimResource> {

    protected final ResourceTypeDefinition resourceTypeDefinition = ResourceTypeDefinition.fromScimResource(this.getClass());

    protected GenericScimResourceConverter<RESOURCE> genericScimResourceConverter;

    public BaseResourceController(final BaseUrlProvider baseUrlProvider) {
        genericScimResourceConverter = new GenericScimResourceConverter<>(resourceTypeDefinition, baseUrlProvider);
    }

    protected Set<String> getValidSortPaths() {
        final Set<String> validSorts = resourceTypeDefinition.getCoreSchema()
                                                             .getAttributes()
                                                             .stream()
                                                             .map(attributeDefinition -> getValidSortPaths(null, attributeDefinition))
                                                             .flatMap(Collection::stream)
                                                             .collect(Collectors.toSet());
        // All SCIM resources must support id.
        validSorts.add("id");

        return validSorts;
    }

    private Set<String> getValidSortPaths(final String string, final AttributeDefinition attributeDefinition) {
        final Set<String> results = new HashSet<>();

        if (CollectionUtils.isEmpty(attributeDefinition.getSubAttributes())) {
            final String basePath = !StringUtils.hasLength(string) ? "" : string + ".";
            results.add(basePath + attributeDefinition.getName());
        } else {
            attributeDefinition.getSubAttributes().forEach(subAttribute -> results.addAll(getValidSortPaths(string, subAttribute)));
        }

        return results;
    }
}
