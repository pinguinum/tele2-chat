package org.tele2chat.security.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;
import org.tele2chat.service.UserService;

@Component
@NoArgsConstructor
public class CustomOidcUserService extends OidcUserService {

    private UserService userService;

    @Autowired
    public void setPasswordEncoder(UserService userService) {
        this.userService = userService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) {
        OidcUser oidcUser = super.loadUser(userRequest);
        userService.authUserWithSocialMedia(oidcUser, userRequest.getClientRegistration().getRegistrationId());

        return oidcUser;
    }
}
