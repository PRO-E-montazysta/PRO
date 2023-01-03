package com.emontazysta.service.impl;

import com.emontazysta.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.service.AppUserService;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl  implements AppUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserRepository appUserRepository;

    public List<AppUser> getAll() {
        log.info("Fetching all users");
        return appUserRepository.findAll();
    }

    public AppUser getById(Long id) {
        return appUserRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

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

        AppUser user = appUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
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

    public void setRolesToUser(Long id, List <String> roles) {
        log.info("Adding roles {} to user {}", roles, appUserRepository.findById(id).get().getUsername());
        AppUser user = appUserRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        user.setRoles(roles.stream()
                .map(role -> Role.valueOf(role))
                .collect(Collectors.toList())   );
    }

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
