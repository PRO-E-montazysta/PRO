package com.emontazysta.service;


import com.emontazysta.model.AppUser;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser user);
    void addRoleToUser(String username, String roleName);
    AppUser getUser(String username);
    List<AppUser> getUsers();
}
