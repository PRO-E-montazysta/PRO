package com.emontazysta.service;

import com.emontazysta.Role;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class RoleService {

    public List<String> getAllRoles() {
        log.info("Fetching all roles");
        List<String> roles = Stream.of(Role.values())
                .map(Role::getValue)
                .collect(Collectors.toList());
        return roles;
    }
}
