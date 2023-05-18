package com.emontazysta.controller;

import com.emontazysta.enums.Role;
import com.emontazysta.mapper.UserMapper;
import com.emontazysta.model.dto.AppUserDto;
import com.emontazysta.model.dto.EmployeeDto;
import com.emontazysta.model.dto.EmploymentDto;
import com.emontazysta.model.searchcriteria.AppUserSearchCriteria;
import com.emontazysta.service.*;
import com.emontazysta.util.AuthUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.emontazysta.configuration.Constants.API_BASE_CONSTANT;

@Slf4j
@RestController
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequestMapping(value = API_BASE_CONSTANT + "/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class AppUserController {

    private final AppUserService userService;
    private final RoleService roleService;
    private final UserMapper userMapper;
    private final SpecialistService specialistService;
    private final SalesRepresentativeService salesRepresentativeService;
    private final ManagerService managerService;
    private final FitterService fitterService;
    private final ForemanService foremanService;
    private final WarehousemanService warehousemanService;
    private final WarehouseManagerService warehouseManagerService;
    private final CompanyAdminService companyAdminService;
    private final AuthUtils authUtils;
    private final EmploymentService employmentService;

    @GetMapping("/all")
    @Operation(description = "Allows to get all Users.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<AppUserDto>> getAppUsers() {
        return ResponseEntity.ok().body(userService.getAll().stream()
                .map(userMapper::toDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    @Operation(description = "Allows to add new User.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AppUserDto> saveAppUser(@RequestBody @Valid final AppUserDto user, Principal principal) {
        Set<Role> roles = userService.findByUsername(principal.getName()).getRoles(); //TODO: move logic to separate service
        if (roles.contains(Role.CLOUD_ADMIN)) {
            if (userService.findByUsername(user.getUsername()) == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(userService.add(UserMapper.toEntity(user))));
            } else {
                log.info("User {} already exists", user.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else if (roles.contains(Role.ADMIN)
                && !(roles.contains(Role.CLOUD_ADMIN))
                && !user.getRoles().contains(Role.CLOUD_ADMIN)) {
            if (userService.findByUsername(user.getUsername()) == null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(userService.add(UserMapper.toEntity(user))));
            } else {
                log.info("User {} already exists", user.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } else {
            log.info("User {} does not have the required permissions", principal.getName());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    @PutMapping("/{id}")
    @Operation(description = "Allows to update User.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<AppUserDto> updateAppUser(@PathVariable Long id, @Valid @RequestBody final AppUserDto user, Principal principal) {
        Set<Role> roles = userService.findByUsername(principal.getName()).getRoles();
        if (roles.contains(Role.CLOUD_ADMIN)) {
            if (userService.getById(id) != null) {
                return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(userService.update(id, UserMapper.toEntity(user))));
            }
        } else if (!roles.contains(Role.CLOUD_ADMIN) && roles.contains(Role.ADMIN)) {
            if (!userService.getById(user.getId()).getRoles().contains(Role.CLOUD_ADMIN)) {
                return ResponseEntity.status(HttpStatus.OK).body(userMapper.toDto(userService.update(id, UserMapper.toEntity(user))));
            } else {
                log.info("User {} does not have the required permissions", principal.getName());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/{id}")
    @Operation(description = "Allows to get User by given Id.", security = @SecurityRequirement(name = "bearer-key"))
    public AppUserDto getUserById(@PathVariable Long id) {
        Optional<EmploymentDto> userEmployment = employmentService.getCurrentEmploymentByEmployeeId(id);
        Long wantedUserCompanyId = userEmployment.isPresent() ? userEmployment.get().getCompanyId() : null;
        Long loggedUserCompanyId = authUtils.getLoggedUserCompanyId();

        if(Objects.equals(wantedUserCompanyId, loggedUserCompanyId)){
            switch (userService.getById(id).getRoles().iterator().next()) {
                case SPECIALIST:
                    return specialistService.getById(id);
                case SALES_REPRESENTATIVE:
                    return salesRepresentativeService.getById(id);
                case MANAGER:
                    return managerService.getById(id);
                case FITTER:
                    return fitterService.getById(id);
                case FOREMAN:
                    return foremanService.getById(id);
                case WAREHOUSE_MAN:
                    return warehousemanService.getById(id);
                case WAREHOUSE_MANAGER:
                    return warehouseManagerService.getById(id);
                case ADMIN:
                    return companyAdminService.getById(id);
                default:
                    return userMapper.toDto(userService.getById(id));
            }
        }else {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }
    }


    @PostMapping("/{id}/setroles")
    @Operation(description = "Allows to assign list of roles to the user", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<String> addRoleToUser(@PathVariable Long id, @RequestBody Set<Role> roles) {
        ResponseEntity<String> response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //TODO: move logic to separate service
        boolean correctRoles = roles.stream()
                .allMatch(role -> roleService.getAllRoles().contains(role.name()));
        if (correctRoles && userService.getById(id) != null) {
            userService.setRolesToUser(id, roles);
            response = ResponseEntity.status(HttpStatus.OK).build();
        } else if (!correctRoles) {
            log.info("Can't add role '{}' no such user role", roles); //TODO: change to point wrong role not whole set
        } else if (userService.getById(id) == null) {
            log.info("Can't add role '{}' can't find user with id {}", roles, id); //TODO: change to point wrong role not whole set
        }
        return response;
    }

    @GetMapping("/filter")
    @Operation(description = "Return filtered Users by given parameters.", security = @SecurityRequirement(name = "bearer-key"))
    public ResponseEntity<List<EmployeeDto>> filterUsers(AppUserSearchCriteria appUserSearchCriteria, Principal principal) {
        return new ResponseEntity<>(userService.getFilteredUsers(appUserSearchCriteria, principal), HttpStatus.OK);
    }

}
