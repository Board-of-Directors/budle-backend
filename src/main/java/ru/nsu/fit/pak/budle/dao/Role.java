package ru.nsu.fit.pak.budle.dao;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@Table(name = "role")
public class Role implements GrantedAuthority {

    static private final String ROLE_PREFIX = "ROLE_";
    static private final String ADMIN_POSTFIX = "ADMIN";
    @Transient
    public static final Role ADMIN_ROLE = new Role(1L, ROLE_PREFIX + ADMIN_POSTFIX);
    static private final String USER_POSTFIX = "USER";
    @Transient
    public static final Role USER_ROLE = new Role(2L, ROLE_PREFIX + USER_POSTFIX);
    static private final String OWNER_POSTFIX = "OWNER";
    @Transient
    public static final Role OWNER_ROLE = new Role(4L, ROLE_PREFIX + OWNER_POSTFIX);
    static private final String WORKER_POSTFIX = "WORKER";
    @Transient
    public static final Role WORKER_ROLE = new Role(3L, ROLE_PREFIX + WORKER_POSTFIX);

    @Id
    private Long id;
    private String name;


    @Override
    public String getAuthority() {
        return name;
    }
}
