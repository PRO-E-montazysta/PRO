package com.emontazysta.service;

import java.security.Principal;
import java.util.List;

public interface RoleService {
    List<String> getAllRoles();
    List<String> getAllRoles(Principal principal);
}
