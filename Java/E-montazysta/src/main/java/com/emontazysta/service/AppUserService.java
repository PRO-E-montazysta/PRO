package com.emontazysta.service;

import com.emontazysta.enums.Role;
import com.emontazysta.model.AppUser;
import com.emontazysta.model.dto.AppUserDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
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
    void generateResetPasswordToken(String username);
    void changePassword(String token, String password);

    AppUser update(Long id, AppUser user);

    List <AppUserDto> getFilteredUsers(AppUserSearchCriteria appUserSearchCriteria);
}
