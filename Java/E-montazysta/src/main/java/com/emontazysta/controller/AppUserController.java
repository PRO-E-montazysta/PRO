package com.emontazysta.controller;

import com.emontazysta.data.RoleToUserForm;
import com.emontazysta.model.AppUser;
import com.emontazysta.repository.AppUserRepository;
import com.emontazysta.service.AppUserService;
import com.emontazysta.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_BASE_CONSTANT)
public class AppUserController {

    private final AppUserService userService;
    private final RoleService roleService;
    private final AppUserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/users")
    public ResponseEntity<List<AppUser>> getAppUsers() {
        return ResponseEntity.ok().body(userService.getUsers());
    }

    @PostMapping("/user/create")
    public ResponseEntity<AppUser> saveAppUser(@RequestBody final AppUser user) {
        if(userRepository.findByUsername(user.getUsername()) == null) {
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/register").toUriString());
            return ResponseEntity.created(uri).body(userService.saveUser(user, bCryptPasswordEncoder));
        }else {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }


    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form) {
        if (roleService.getAllRoles().contains(form.getRoleName())
                && userRepository.findByUsername(form.getUsername())  != null) {
            userService.addRoleToUser(form.getUsername(), form.getRoleName());
            return ResponseEntity.ok().build();
        }else{
            log.info("Can't add role '{}' no such user role", form.getRoleName());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/roles")
    public ResponseEntity<List<String>> getAllRoles() {
        return ResponseEntity.ok().body(roleService.getAllRoles());
    }

}
