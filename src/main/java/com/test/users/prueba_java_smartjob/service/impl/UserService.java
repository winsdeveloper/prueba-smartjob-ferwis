package com.test.users.prueba_java_smartjob.service.impl;

import com.test.users.prueba_java_smartjob.dto.ApiResponse;
import com.test.users.prueba_java_smartjob.dto.request.UserRequest;
import com.test.users.prueba_java_smartjob.dto.response.UserResponse;
import com.test.users.prueba_java_smartjob.entity.User;
import com.test.users.prueba_java_smartjob.mapper.UserMapper;
import com.test.users.prueba_java_smartjob.repository.UserRepository;
import com.test.users.prueba_java_smartjob.service.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService implements IUserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  //private final PasswordEncoder passwordEncoder;
 // private final JwtTokenProvider tokenProvider;

  public Mono<ApiResponse<UserResponse>> createUser(UserRequest request) {
    return userRepository.existsByEmail(request.email())
        .flatMap(exists -> {
          if (exists) {
            return Mono.just(ApiResponse.error("El correo ya registrado"));
          }

          User user = userMapper.toEntity(request);
          user.setPassword(request.password()); //se podria codificar el passwor por practicas de seguridad
          //aunque en el test no lo especifica
        //  user.setToken(tokenProvider.generateToken(request.email()));
          user.setLastLogin(LocalDateTime.now());

          return userRepository.save(user)
              .map(savedUser -> {
                log.info("Usuario creado bajo el id: {}", savedUser.getId());
                return ApiResponse.success(userMapper.toResponse(savedUser));
              });
        });
  }
}
