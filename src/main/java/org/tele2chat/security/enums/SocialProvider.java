package org.tele2chat.security.enums;

public enum SocialProvider {
    GOOGLE("google"), VK("vk");

    private final String providerId;

    SocialProvider(String providerId) {
        this.providerId = providerId;
    }

    public static SocialProvider fromProviderId(String providerId) {
        for (SocialProvider provider : values()) {
            if (provider.getProviderId().equalsIgnoreCase(providerId)) {
                return provider;
            }
        }
        throw new IllegalArgumentException("Unknown provider ID: " + providerId);
    }

    public String getProviderId() {
        return providerId;
    }
}
