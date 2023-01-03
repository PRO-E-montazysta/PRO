package com.emontazysta.service;

import com.emontazysta.model.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

public interface AppUserService extends UserDetailsService {
     AppUser findByUsername(String username);

    List<AppUser> getAll();
    AppUser getById(Long id);
    AppUser add(AppUser user);
    void delete(Long id);

    AppUser update(Long id, AppUser user);
}
