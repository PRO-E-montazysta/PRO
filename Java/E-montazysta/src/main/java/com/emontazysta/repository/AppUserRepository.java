package com.emontazysta.repository;

import com.emontazysta.enums.Role;
import com.emontazysta.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByUsername(String username);
    AppUser findByResetPasswordToken(String resetPasswordToken);
    List<AppUser> findAllByRolesNotContaining(Role role);
    List<AppUser> findAllByIdIn(List<Long> listOfIds);
    List<AppUser> findAllByRolesContaining(Role role);

}
