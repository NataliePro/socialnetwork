package com.chensy.socialnetwork.dao;

import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;

public interface RoleDao {
    Role getRoleByName(String name);
    void setRole(Long userId, String roleName);
}
