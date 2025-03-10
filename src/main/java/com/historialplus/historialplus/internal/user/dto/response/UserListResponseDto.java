package com.historialplus.historialplus.internal.user.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class UserListResponseDto {
    private UUID id;
    private String username;
    private String email;
    private String dni;
    private String hospital;
    private String state;
    private List<String> roles;
}
