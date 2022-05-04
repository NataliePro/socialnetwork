package com.chensy.socialnetwork.service.impl;

import com.chensy.socialnetwork.dao.RoleDao;
import com.chensy.socialnetwork.model.Role;
import com.chensy.socialnetwork.model.User;
import com.chensy.socialnetwork.service.RoleService;
import org.springframework.stereotype.Component;

@Component
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;

    public RoleServiceImpl(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    @Override
    public void setRole(User user, Role role) {
        roleDao.setRole(user.getId(), role.getAuthority());
    }
}
