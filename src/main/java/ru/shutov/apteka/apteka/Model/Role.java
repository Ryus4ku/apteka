package ru.shutov.apteka.apteka.Model;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Getter
public enum Role implements GrantedAuthority {
    GUEST(0, "Гость"),
    USER(1, "Пользователь"),
    SUPER_USER(2, "Супер пользователь");

    private static final Map<Integer, Role> ROLE_MAP;

    static {
        Map<Integer, Role> map = new HashMap<>();
        for (Role role : Role.values()) {
            map.put(role.getValue(), role);
        }

        ROLE_MAP = Collections.unmodifiableMap(map);
    }

    private int value;
    private String description;

    Role(int value, String description) {
        this.value = value;
        this.description = description;
    }

    @Override
    public String getAuthority() {
        return name();
    }
}
