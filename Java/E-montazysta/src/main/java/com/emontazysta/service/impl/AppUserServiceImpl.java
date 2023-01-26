package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl  implements AppUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserRepository appUserRepository;

    @Override
    public List<AppUser> getAll() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

    @Override
    public AppUser getById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AppUser add(AppUser user) {
        log.info("Saving new user {} to the database",user.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return appUserRepository.save(user);
    }

    @Override
    public void delete(Long id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public AppUser update(Long id, AppUser newUser) {

        AppUser user = getById(id);
        AppUser updatedUser = AppUser.builder()
                .id(id)
                .firstName(newUser.getFirstName())
                .lastName(newUser.getLastName())
                .username(newUser.getUsername())
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .roles(user.getRoles())
                .build();
        return appUserRepository.save(updatedUser);
    }

    @Override
    public void setRolesToUser(Long id, Set<Role> roles) {
        log.info("Adding roles {} to user {}", roles, appUserRepository.findById(id).get().getUsername());
        AppUser user = appUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setRoles(roles);
    }

    @Override
    public AppUser findByUsername(String username) {
        log.info("Fetching user {}", username);
        return appUserRepository.findByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = appUserRepository
                .findByUsername(username);
        if(user == null){
            log.error("User not found in database");
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("User found in the database: {}", username);
        }
        return user;
    }


}
