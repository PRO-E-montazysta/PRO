package com.emontazysta.configuration;

import com.emontazysta.util.EnvVars;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "security")
public class SecurityProperties {

    private final String cloudManagerUsername = EnvVars.get("CLOUD_MANAGER_USERNAME", "admin");
    private final String cloudManagerPassword = EnvVars.get("CLOUD_MANAGER_PASSWORD", "password");
    private final String jwtSecretKey = EnvVars.get("JWT_SECRET_KEY", "6251655468576D5A7134743777217A25432646294A404E635266556A586E3272");

}
