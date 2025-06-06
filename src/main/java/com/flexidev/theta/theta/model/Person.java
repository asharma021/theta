package com.flexidev.theta.theta.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;


@Table(schema = "person")
public class Person extends BaseModel implements UserDetails {

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Size(min = 4, max = 255)
    @Email
    @Column(name = "email", nullable = false, length = 255)
    private String email;

    @Column(name = "birth", length = 255)
    private LocalDate birth;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Column(name = "roles", nullable = false, length = 500)
    private String roles;

    @Transient
    private Collection<String> roleCollection = new ArrayList<>();

    public Person() {
    }

    public Person(Person person) {
        this.setId(person.getId());
        this.name = person.getName();
        this.email = person.getEmail();
        this.birth = person.getBirth();
        this.password = person.getPassword();
        this.setActive(person.getActive());
        this.roles = person.getRoles();

        String rolesString = person.getRoles();
        if (rolesString != null && !rolesString.isEmpty()) {
            String[] roleNames = rolesString.split(",");
            for (String roleName : roleNames) {
                String trimmedRole = roleName.trim();
                if (!trimmedRole.isEmpty()) {
                    roleCollection.add(trimmedRole);
                }
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"id\": \"").append(getId()).append("\", ");
        sb.append("\"name\": \"").append(this.name).append("\", ");
        sb.append("\"email\": \"").append(this.email).append("\", ");
        sb.append("\"password\": \"[PROTECTED]\"").append("}");
        return sb.toString();
    }

    public Map<String, Object> toMap() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        map.put("id", getId());
        map.put("name", this.name);
        map.put("email", this.email);
        map.put("password", "[PROTECTED]");
        return map;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.isEnabled();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.getActive();
    }

    @Override
    public boolean isEnabled() {
        return this.getActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String rolesString = getRoles();
        if (rolesString != null && !rolesString.isEmpty()) {
            String[] roleNames = rolesString.split(",");
            for (String roleName : roleNames) {
                String trimmedRole = roleName.trim();
                if (!trimmedRole.isEmpty()) {
                    authorities.add(new SimpleGrantedAuthority(trimmedRole));
                }
            }
        }
        return authorities;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public void setBirth(LocalDate birth) {
        this.birth = birth;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Collection<String> getRoleCollection() {
        return roleCollection;
    }

    public void setRoleCollection(Collection<String> roleCollection) {
        this.roleCollection = roleCollection;
    }
}
