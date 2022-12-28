package com.emontazysta.service.impl;

import com.emontazysta.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository appUserRepository;
    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new user {} to the database",user.getUsername());
        return appUserRepository.save(user);
    }


    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding role {} to user {}", roleName, username);
        AppUser user = appUserRepository.findByUsername(username);
        user.getRoles().add(Role.valueOf(roleName));

    }

    @Override
    public AppUser getUser(String username) {
        log.info("Fetching user {}", username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public List<AppUser> getUsers() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }
}
