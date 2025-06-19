package com.richcorgi.iap.guide.sample.config;

import com.apple.itunes.storekit.client.AppStoreServerAPIClient;
import com.apple.itunes.storekit.verification.SignedDataVerifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.Set;

@Component
public class AppStoreServerConfigure {

    @Bean
    public AppStoreServerAPIClient appStoreServerAPIClient(AppStoreServerAPIClientProperties properties){
        return  new AppStoreServerAPIClient(properties.getSigningKey(),properties.getKeyId(),properties.getIssuerId(),properties.getBundleId(),properties.getEnvironment());
    }


    @Bean
    public SignedDataVerifier signedDataVerifier(AppStoreServerAPIClientProperties properties){

        Set<InputStream> rootCAs = Set.of(
                new ByteArrayInputStream(Base64.getDecoder().decode(properties.getRootCAG2Base64().trim())),
                new ByteArrayInputStream(Base64.getDecoder().decode(properties.getRootCAG3Base64().trim()))
        );
        return new SignedDataVerifier(rootCAs, properties.getBundleId(), properties.getAppId(), properties.getEnvironment(), true);
    }


}
