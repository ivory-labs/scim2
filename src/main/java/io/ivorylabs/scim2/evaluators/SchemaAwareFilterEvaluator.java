package io.ivorylabs.scim2.evaluators;

import com.unboundid.scim2.common.Path;
import com.unboundid.scim2.common.types.AttributeDefinition;
import com.unboundid.scim2.common.utils.FilterEvaluator;
import io.ivorylabs.scim2.ResourceTypeDefinition;

/**
 * A schema aware filter evaluator that respects case sensitivity.
 */
public class SchemaAwareFilterEvaluator extends FilterEvaluator {

    private final ResourceTypeDefinition resourceType;

    /**
     * Create a new schema aware filter evaluator.
     *
     * @param resourceType The resource type definition.
     */
    public SchemaAwareFilterEvaluator(final ResourceTypeDefinition resourceType) {
        this.resourceType = resourceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AttributeDefinition getAttributeDefinition(final Path path) {
        return resourceType.getAttributeDefinition(path);
    }

}
