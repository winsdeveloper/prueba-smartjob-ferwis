package com.test.users.prueba_java_smartjob.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UserResponse(
    UUID id,
    String name,
    String email,
    List<PhoneResponse> phones,
    LocalDateTime created,
    LocalDateTime modified,
    LocalDateTime lastLogin,
    String token,
    Boolean isActive
) {}
