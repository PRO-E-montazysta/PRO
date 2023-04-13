package com.emontazysta.mapper;

import  com.emontazysta.enums.Role;
import com.emontazysta.model.Company;
import com.emontazysta.model.dto.CompanyWithAdminDto;
import com.emontazysta.model.CompanyAdmin;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class CompanyWithAdminMapper {

    public static Company companyFromDto(CompanyWithAdminDto dto) {
        return Company.builder()
                .id(null)
                .companyName(dto.getCompanyName())
                .createdAt(LocalDate.now())
                .status(dto.getStatus())
                .statusReason(dto.getStatusReason())
                .warehouses(new ArrayList<>())
                .orders(new ArrayList<>())
                .clients(new ArrayList<>())
                .employments(new ArrayList<>())
                .toolTypes(new ArrayList<>())
                .build();
    }

    public static CompanyAdmin companyAdminFromDto(CompanyWithAdminDto dto) {
        return new CompanyAdmin(null, dto.getFirstName(), dto.getLastName(), dto.getEmail(), new BCryptPasswordEncoder().encode(dto.getPassword()),
                dto.getUsername(), null, Set.of(Role.ADMIN), dto.getPhone(), dto.getPesel(), new ArrayList<>(),
                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }
}
