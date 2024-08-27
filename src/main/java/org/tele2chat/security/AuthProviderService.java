package org.tele2chat.security;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.tele2chat.security.enums.SocialProvider;

public interface AuthProviderService {
    String getUsername(OAuth2User oAuth2User);

    String getEmail(OAuth2User oAuth2User);

    String getPicture(OAuth2User oAuth2User);

    SocialProvider getProvider();
}
