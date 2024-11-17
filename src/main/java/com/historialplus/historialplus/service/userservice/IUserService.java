package com.historialplus.historialplus.service.userservice;

import com.historialplus.historialplus.dto.userDTOs.UserDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserCreateDto;
import com.historialplus.historialplus.dto.userDTOs.request.UserUpdateDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserListResponseDto;
import com.historialplus.historialplus.dto.userDTOs.response.UserResponseDto;
import com.historialplus.historialplus.entities.RoleEntity;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IUserService {
    List<UserResponseDto> findAll();

    Optional<UserResponseDto> findById(@NonNull UUID id);

    UserDto save(UserCreateDto userDto);

    void deleteById(UUID id);

    UserResponseDto createHospitalUserByManagement(UserCreateDto userDto);

    UserResponseDto update(UUID id, UserUpdateDto userDto);

    Page<UserListResponseDto> searchUsers(String username, String dni, String hospital, UUID id, RoleEntity role, Pageable pageable);
}
