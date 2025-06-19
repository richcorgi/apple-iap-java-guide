package com.richcorgi.iap.guide.sample.config;

import com.apple.itunes.storekit.model.Environment;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app-store-server")
@Data
public class AppStoreServerAPIClientProperties {
    private String issuerId;
    private String keyId;
    private String bundleId;
    private String signingKey;
    private Long appId;
    private Environment environment;
    private String rootCAG2Base64;
    private String rootCAG3Base64;
}
