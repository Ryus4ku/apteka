package ru.shutov.apteka.apteka.DTOs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContextDto implements Serializable {
    private boolean isAuthorized;
    private boolean isAdmin;
}
