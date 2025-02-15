package io.ivorylabs.scim2.controllers.discovery;

import com.unboundid.scim2.common.GenericScimResource;
import com.unboundid.scim2.common.exceptions.ScimException;
import com.unboundid.scim2.common.types.ServiceProviderConfigResource;
import com.unboundid.scim2.common.utils.ApiConstants;
import io.ivorylabs.scim2.BaseUrlProvider;
import io.ivorylabs.scim2.annotations.ScimResource;
import io.ivorylabs.scim2.controllers.BaseResourceController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The ServiceProviderConfig is populated through application properties.  Please review the readme for further details.
 * <p>
 * SCIM provides a schema for representing the service provider's
 * configuration, identified using the following schema URI:
 * "urn:ietf:params:scim:schemas:core:2.0:ServiceProviderConfig".
 * <p>
 * The service provider configuration resource enables a service
 * provider to discover SCIM specification features in a standardized
 * form as well as provide additional implementation details to clients.
 * All attributes have a mutability of "readOnly".  Unlike other core
 * resources, the "id" attribute is not required for the service
 * provider configuration resource.
 * <p>
 * RFC 7643
 * SCIM Core Schema
 * September 2015
 * <a href="https://tools.ietf.org/html/rfc7643#page-26">...</a>
 */
@RestController
@RequestMapping(value = ApiConstants.SERVICE_PROVIDER_CONFIG_ENDPOINT)
@ScimResource(description = "SCIM 2.0 Service Provider Config",
        name = "ServiceProviderConfig",
        schema = ServiceProviderConfigResource.class,
        discoverable = false)
public class ServiceProviderConfigController extends BaseResourceController<ServiceProviderConfigResource> {

    private final ServiceProviderConfigResource serviceProviderConfigResource;

    @Autowired
    public ServiceProviderConfigController(final BaseUrlProvider baseUrlProvider,
                                           final ServiceProviderConfigResource serviceProviderConfigResource) {
        super(baseUrlProvider);
        this.serviceProviderConfigResource = serviceProviderConfigResource;
    }

    /**
     * Service request to retrieve the Service Provider Config.
     *
     * @return The Service Provider Config.
     * @throws ScimException if an error occurs.
     */
    @GetMapping
    public GenericScimResource get() throws ScimException {
        return genericScimResourceConverter.convert(serviceProviderConfigResource);
    }

}
