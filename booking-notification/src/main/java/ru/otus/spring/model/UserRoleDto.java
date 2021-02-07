package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * @author MTronina
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto implements GrantedAuthority {

    /**
     * Наименование роли.
     */
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
