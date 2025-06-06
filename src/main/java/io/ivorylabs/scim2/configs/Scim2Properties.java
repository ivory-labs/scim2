package io.ivorylabs.scim2.configs;

import com.unboundid.scim2.common.types.AuthenticationScheme;
import com.unboundid.scim2.common.types.BulkConfig;
import com.unboundid.scim2.common.types.ChangePasswordConfig;
import com.unboundid.scim2.common.types.ETagConfig;
import com.unboundid.scim2.common.types.FilterConfig;
import com.unboundid.scim2.common.types.PatchConfig;
import com.unboundid.scim2.common.types.ServiceProviderConfigResource;
import com.unboundid.scim2.common.types.SortConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "scim2")
public class Scim2Properties {

    @NotNull
    private String resourcesPackage;

    @NotBlank
    private String baseUrl;

    @NotNull
    private ServiceProviderConfigProperties serviceProviderConfig;

    public void setResourcesPackage(final String resourcesPackage) {
        if (!StringUtils.hasLength(resourcesPackage)) {
            log.warn("scim2.resourcesPackage was not set. This can slow down server initialization significantly.");
        }

        this.resourcesPackage = resourcesPackage;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ServiceProviderConfigProperties {

        private String documentationUri;

        @NotNull
        private PatchConfigProperties patch;

        @NotNull
        private BulkConfigProperties bulk;

        @NotNull
        private FilterConfigProperties filter;

        @NotNull
        private ChangePasswordConfigProperties changePassword;

        @NotNull
        private SortConfigProperties sort;

        @NotNull
        private ETagConfigProperties etag;

        private List<AuthenticationSchemeProperties> authenticationSchemes = new ArrayList<>();

        public ServiceProviderConfigResource getServiceProviderConfiguration() {
            return new ServiceProviderConfigResource(getDocumentationUri(),
                    getPatch().getPatchConfig(),
                    getBulk().getBulkConfig(),
                    getFilter().getFilterConfig(),
                    getChangePassword().getChangePasswordConfig(),
                    getSort().getSortConfig(),
                    getEtag().getETagConfig(),
                    authenticationSchemes.stream()
                            .map(AuthenticationSchemeProperties::getAuthenticationScheme)
                            .collect(Collectors.toList()));
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class PatchConfigProperties {

            private boolean supported = false;

            public PatchConfig getPatchConfig() {
                return new PatchConfig(supported);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class BulkConfigProperties {

            private boolean supported = false;

            @Min(0)
            private int maxOperations = 0;

            @Min(0)
            private int maxPayloadSize = 0;

            public BulkConfig getBulkConfig() {
                return new BulkConfig(supported, maxOperations, maxPayloadSize);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class FilterConfigProperties {

            private boolean supported = true;

            @Min(0)
            private int maxResults = 100;

            public FilterConfig getFilterConfig() {
                return new FilterConfig(supported, maxResults);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ChangePasswordConfigProperties {

            private boolean supported = false;

            public ChangePasswordConfig getChangePasswordConfig() {
                return new ChangePasswordConfig(supported);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class SortConfigProperties {

            private boolean supported = false;

            public SortConfig getSortConfig() {
                return new SortConfig(supported);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class ETagConfigProperties {

            private boolean supported = false;

            public ETagConfig getETagConfig() {
                return new ETagConfig(supported);
            }
        }

        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        public static class AuthenticationSchemeProperties {

            @NotBlank
            private String name;

            @NotBlank
            private String description;

            private URI specUri;

            private URI documentationUri;

            private String type;

            private boolean primary = false;

            public AuthenticationScheme getAuthenticationScheme() {
                return new AuthenticationScheme(name, description, specUri, documentationUri, type, primary);
            }
        }
    }

}
