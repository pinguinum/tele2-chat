package org.tele2chat.security.service;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.tele2chat.security.AuthProviderService;
import org.tele2chat.security.enums.SocialProvider;

@Service
public class GoogleAuthProviderService implements AuthProviderService {
    @Override
    public String getUsername(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute("name");
    }

    @Override
    public String getEmail(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute("email");
    }

    @Override
    public String getPicture(OAuth2User oAuth2User) {
        return oAuth2User.getAttribute("picture");
    }

    @Override
    public SocialProvider getProvider() {
        return SocialProvider.GOOGLE;
    }
}
