package org.tele2chat.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tele2chat.security.enums.SocialProvider;

import java.util.HashMap;
import java.util.Map;

@Component
public class AuthProviderFactory {

    private final Map<SocialProvider, AuthProviderService> providers = new HashMap<>();

    @Autowired
    public AuthProviderFactory(Map<String, AuthProviderService> providerServices) {
        for (AuthProviderService service : providerServices.values()) {
            providers.put(service.getProvider(), service);
        }
    }

    public AuthProviderService getAuthProviderService(SocialProvider socialProvider) {
        return providers.get(socialProvider);
    }
}
