package io.ivorylabs.scim2.controllers.discovery;

import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.ScimResource;
import com.unboundid.scim2.common.exceptions.ResourceNotFoundException;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.filters.Filter;
import com.unboundid.scim2.common.messages.ListResponse;
import com.unboundid.scim2.common.utils.ApiConstants;
import io.ivorylabs.scim2.BaseUrlProvider;
import io.ivorylabs.scim2.ResourceTypeDefinition;
import io.ivorylabs.scim2.controllers.BaseResourceController;
import io.ivorylabs.scim2.evaluators.SchemaAwareFilterEvaluator;
import io.ivorylabs.scim2.resourcetypes.ResourceTypeRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class SchemaAwareController extends BaseResourceController<GenericScimResource> {

    private final SchemaAwareFilterEvaluator filterEvaluator = new SchemaAwareFilterEvaluator(resourceTypeDefinition);
    protected ResourceTypeRegistry resourceTypeRegistry;

    @Autowired
    public SchemaAwareController(final BaseUrlProvider baseUrlProvider,
                                 final ResourceTypeRegistry resourceTypeRegistry) {
        super(baseUrlProvider);
        this.resourceTypeRegistry = resourceTypeRegistry;
    }

    protected abstract List<GenericScimResource> getResources(final Set<ResourceTypeDefinition> resourceDefinitions);

    /**
     * Service SCIM request to retrieve all resource types or schemas defined at the
     * service provider using GET.
     *
     * @param filterString The filter string used to request a subset of resources.
     * @return All resource types in a ListResponse container.
     * @throws ScimException If an error occurs.
     */
    @GetMapping
    public ListResponse<GenericScimResource> search(
            @RequestParam(value = ApiConstants.QUERY_PARAMETER_FILTER, required = false) final String filterString) throws ScimException {

        final List<GenericScimResource> filteredResources = !StringUtils.hasLength(filterString)
                ? getResources(resourceTypeRegistry.getResourceTypeDefinitions())
                : filterResources(Filter.fromString(filterString));
        final List<GenericScimResource> preparedResources = genericScimResourceConverter.convert(null, null, filteredResources);

        return new ListResponse<>(preparedResources.size(), preparedResources, 1, preparedResources.size());
    }

    /**
     * Service SCIM request to retrieve a resource type or schema by ID.
     *
     * @param id The ID of the resource type to retrieve.
     * @return The retrieved resource type.
     * @throws ScimException If an error occurs.
     */
    @GetMapping(value = "/{id}")
    public ScimResource get(@PathVariable("id") final String id) throws ScimException {
        final Filter filter = Filter.eq("id", id);
        return filterResources(filter).stream().findFirst().orElseThrow(() -> new ResourceNotFoundException(id));
    }

    private List<GenericScimResource> filterResources(final Filter filter) {
        return getResources(resourceTypeRegistry.getResourceTypeDefinitions()).stream().filter(genericScimResource -> {
            try {
                return filter.visit(filterEvaluator, genericScimResource.getObjectNode());
            } catch (ScimException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
    }

}
