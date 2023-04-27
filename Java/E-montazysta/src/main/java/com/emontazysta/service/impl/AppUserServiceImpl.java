package com.emontazysta.service.impl;

import com.emontazysta.enums.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.repository.criteria.AppUserCriteriaRepository;
import com.emontazysta.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl  implements AppUserService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AppUserRepository appUserRepository;
    private final AppUserCriteriaRepository  appUserCriteriaRepository;

    @Override
    public List<AppUser> getAll() {
        log.info("Fetching all users");
        return appUserRepository.findAllByDeletedIsFalse();
    }

    @Override
    public AppUser getById(Long id) {
        return appUserRepository.findByIdAndDeletedIsFalse(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public AppUser add(AppUser user) {
        user.setUsername(user.getUsername().toLowerCase());
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
                .email(newUser.getEmail())
                .password(newUser.getPassword())
                .username(user.getUsername())
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
    @Transactional
    public void generateResetPasswordToken(String username) {
        AppUser user = appUserRepository.findByUsername(username);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        user.setResetPasswordToken(UUID.randomUUID().toString());
        Map<String, Object> variables = new HashMap<>();
        variables.put("link", ""); // TODO link to frontend with token
        // TODO mailService.sendEmail()
    }

    @Override
    @Transactional
    public void changePassword(String token, String password) {
        AppUser user = appUserRepository.findByResetPasswordToken(token);
        if (user == null) {
            throw new EntityNotFoundException();
        }
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setResetPasswordToken(null);
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

    @Override
    public List <EmployeeDto> getFilteredUsers(AppUserSearchCriteria appUserSearchCriteria){
    return appUserCriteriaRepository.findAllWithFilters(appUserSearchCriteria);
    }
}
