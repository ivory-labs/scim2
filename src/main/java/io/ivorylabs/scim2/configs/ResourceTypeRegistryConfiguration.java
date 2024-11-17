package io.ivorylabs.scim2.configs;

import io.ivorylabs.scim2.BaseUrlProvider;
import io.ivorylabs.scim2.ResourceTypeDefinition;
import io.ivorylabs.scim2.StaticBaseUrlProvider;
import io.ivorylabs.scim2.annotations.ScimResource;
import io.ivorylabs.scim2.resourcetypes.ResourceTypeRegistry;
import io.ivorylabs.scim2.resourcetypes.SimpleResourceTypeRegistry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

/**
 * This configuration is designed to be a registry of all the existing SCIM resources in the system.
 * All beans annotated with the {@link ScimResource} and {@link RequestMapping} will be loaded as resource type definitions.
 */
@Slf4j
@Configuration
public class ResourceTypeRegistryConfiguration {

    @Bean
    @ConditionalOnMissingBean(BaseUrlProvider.class)
    public BaseUrlProvider baseUrlProvider(final Scim2Properties scim2Properties) {
        return new StaticBaseUrlProvider(scim2Properties.getBaseUrl());
    }

    @Bean
    @ConditionalOnMissingBean(ResourceTypeRegistry.class)
    public ResourceTypeRegistry resourceTypeRegistry(final Set<ResourceTypeDefinition> definitions) {
        return new SimpleResourceTypeRegistry(definitions);
    }

    @Bean
    public Set<ResourceTypeDefinition> getResourceDefinitions(final Scim2Properties scim2Properties) throws ClassNotFoundException {
        final TypeFilter scimResourceFilter = new AnnotationTypeFilter(ScimResource.class);
        final TypeFilter requestMappingFilter = new AnnotationTypeFilter(RequestMapping.class);
        final TypeFilter andFilter = (metadataReader, metadataReaderFactory) -> scimResourceFilter.match(metadataReader, metadataReaderFactory)
                && requestMappingFilter.match(metadataReader, metadataReaderFactory);

        final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(andFilter);

        final Set<ResourceTypeDefinition> resourceTypeDefinitions = new HashSet<>();
        for (final BeanDefinition beanDefinition : provider.findCandidateComponents(scim2Properties.getResourcesPackage())) {
            final Class<?> className = Class.forName(beanDefinition.getBeanClassName());
            resourceTypeDefinitions.add(ResourceTypeDefinition.fromScimResource(className.getAnnotation(ScimResource.class),
                    className.getAnnotation(RequestMapping.class)));
        }

        return resourceTypeDefinitions;
    }

}
