package com.emontazysta.configuration;

import com.emontazysta.util.EnvVars;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final String cloudManagerUsername = EnvVars.get("CLOUD_MANAGER_USERNAME", "admin");
    private final String cloudManagerPassword = EnvVars.get("CLOUD_MANAGER_PASSWORD", "password");

}
