package com.emontazysta.model;

import com.emontazysta.enums.Role;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AppUser implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Length(min = 3, max = 32, message = "First name has to be between 2 and 32 chars")
    private String firstName;

    @NotBlank
    @Length(min = 2, max = 32, message = "Last name has to be between 2 and 32 chars")
    private String lastName;

    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 5, message = "Password must contain at least 5 characters" )
    private String password;

    @NotBlank
    @Length(min = 3, message = "Username must contain at least 3 characters" )
    @Column(unique = true)
    private String username;

    private String resetPasswordToken;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        this.roles.forEach(role -> authorities.add(new SimpleGrantedAuthority(role.name().toUpperCase())));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getPhone() {
        return null;
    }

    public List<Attachment> getAttachments() {
        return null;
    }

    public List<Employment> getEmployments(){
        return null;
    }
}
