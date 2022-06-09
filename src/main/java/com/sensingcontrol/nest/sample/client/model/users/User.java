package com.sensingcontrol.nest.sample.client.model.users;

import com.sensingcontrol.nest.sample.client.model.enums.UserRole;

public class User {
    private String email;
    private String id;
    private String parentId;
    private String firstName;
    private String lastName;
    private UserRole role;

    public String getEmail() {
        return email;
    }
    public String getId() { return id; }
    public String getParentId() {
        return parentId;
    }
    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public UserRole getRole() {
        return role;
    }
}

