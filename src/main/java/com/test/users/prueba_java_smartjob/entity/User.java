package com.test.users.prueba_java_smartjob.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("users")
public class User {

  @Id
  private UUID id;

  @Column("name")
  private String name;

  @Column("email")
  private String email;

  @Column("password")
  private String password;

  @Column("created")
  @Builder.Default
  private LocalDateTime created = LocalDateTime.now();

  @Column("modified")
  private LocalDateTime modified;

  @Column("last_login")
  private LocalDateTime lastLogin;

  @Column("token")
  private String token;

  @Column("is_active")
  @Builder.Default
  private Boolean isActive = true;

  private List<Phone> phones;
}
