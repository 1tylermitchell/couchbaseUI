package com.unhuman.couchbaseui.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.*;

@JsonSerialize
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ClusterConfig {
    private Map<String, UserConfig> userConfigs;

    ClusterConfig() {
        userConfigs = new HashMap<>();
    }

    public void upsertConfigServer(String user, UserConfig userConfig) {
        if (!userConfigs.containsKey(user) || !userConfigs.get(user).equals(userConfig)) {
            userConfigs.put(user, userConfig);
        }
    }

    @JsonIgnore
    public List<String> getUsers() {
        List<String> users = new ArrayList<>(userConfigs.keySet());

        Collections.sort(users);
        return users;
    }

    /**
     * Gets a user config for a user (gets existing or creates new)
     * @param user
     * @return UserConfig
     */
    public UserConfig getUserConfig(String user) {
        // Empty users should not get a values
        if (user.equals("")) {
            return null;
        }

        if (!userConfigs.containsKey(user)) {
            // Create user as non-validated
            UserConfig newConfig = UserConfig.createNewUserConfig();
            userConfigs.put(user, newConfig);
        }
        return userConfigs.get(user);
    }

    public void removeUser(String user) {
        userConfigs.remove(user);
    }
}
