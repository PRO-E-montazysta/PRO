package com.emontazysta.service.impl;

import com.emontazysta.Role;
import com.emontazysta.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final AppUserServiceImpl userService;


    @Override
    public List<String> getAllRoles() {
        List<String> allRoles = Stream.of(Role.values())
                .map(Role::name)
                .collect(Collectors.toList());;
        return allRoles;
    }

    public List<String> getAllRoles(Principal principal) {
        log.info("Fetching all roles");
        List<String> allRoles = getAllRoles();
        Set<Role> principalRoles = userService.findByUsername(principal.getName()).getRoles();
        if (!principalRoles.contains(Role.CLOUD_ADMIN)) {
            allRoles.remove("CLOUD_ADMIN");
            return allRoles;
        }
        return allRoles;
    }
}
