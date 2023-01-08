package com.emontazysta.service;

import com.emontazysta.Role;
import com.emontazysta.model.AppUser;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Set;

public interface AppUserService extends UserDetailsService {
     AppUser findByUsername(String username);

    List<AppUser> getAll();
    AppUser getById(Long id);
    AppUser add(AppUser user);
    void delete(Long id);
    void setRolesToUser(Long id, Set<Role> roles);

    AppUser update(Long id, AppUser user);
}
