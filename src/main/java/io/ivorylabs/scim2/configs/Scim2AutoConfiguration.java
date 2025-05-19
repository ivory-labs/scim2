package io.ivorylabs.scim2.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.ivorylabs.scim2")
@EnableConfigurationProperties(Scim2Properties.class)
public class Scim2AutoConfiguration {

}
