package com.chensy.socialnetwork.service;

import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;

public interface RoleService {
    void setRole(User user, Role role);
}
