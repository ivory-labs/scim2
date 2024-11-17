package io.ivorylabs.scim2;

import javax.validation.constraints.NotBlank;

public class StaticBaseUrlProvider implements BaseUrlProvider {

    private final String baseUrl;

    public StaticBaseUrlProvider(@NotBlank String baseUrl) {
        this.baseUrl = baseUrl;
    }

    @Override
    public String getBaseUrl() {
        return baseUrl;
    }

}
