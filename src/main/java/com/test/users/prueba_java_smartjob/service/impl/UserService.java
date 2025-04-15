package com.test.users.prueba_java_smartjob.service.impl;

import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import com.test.users.prueba_java_smartjob.entity.Phone;
import com.test.users.prueba_java_smartjob.entity.User;
import com.test.users.prueba_java_smartjob.mapper.UserMapper;
import com.test.users.prueba_java_smartjob.repository.PhoneRepository;
import com.test.users.prueba_java_smartjob.repository.UserRepository;
import com.test.users.prueba_java_smartjob.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionException;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements IUserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PhoneRepository phoneRepository;
  //private final PasswordEncoder passwordEncoder;
 // private final JwtTokenProvider tokenProvider;

  public Mono<ApiResponse<?>> createUser(UserRequest request) {
    return userRepository.existsByEmail(request.email())
        .flatMap(exists -> {
          if (exists) {
            return Mono.just(ApiResponse.error("El correo ya registrado"));
          }

          User user = userMapper.toEntity(request);
          user.setId(UUID.randomUUID());
          user.setPassword(request.password());
          user.setLastLogin(LocalDateTime.now());

          // 1. Guarda el User primero
          return userRepository.save(user)
              .flatMap(savedUser -> {
                // 2. Guarda los teléfonos asociados
                return phoneRepository.saveAll(
                        request.phones().stream()
                            .map(phoneReq -> Phone.builder()
                                .number(phoneReq.number())
                                .cityCode(phoneReq.cityCode())
                                .countryCode(phoneReq.countryCode())
                                .userId(savedUser.getId()) // Relación manual
                                .build()
                            )
                            .toList()
                    )
                    .then(Mono.just(ApiResponse.success(userMapper.toResponse(savedUser))));
              });
        })
        .onErrorResume(error -> {
          log.error("Error al crear usuario: {}", error.getMessage(), error);
          if (error instanceof DataIntegrityViolationException) {
            return Mono.just(ApiResponse.error("Error de integridad en base de datos"));
          } else if (error instanceof TransactionException) {
            return Mono.just(ApiResponse.error("Error en transacción"));
          }
          return Mono.just(ApiResponse.error("Error interno al procesar la solicitud"));
        });
  }
}
