package com.test.users.prueba_java_smartjob.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table("phones")
public class Phone {

  @Id
  @Builder.Default
  private Long id = null;

  @Column("number")
  private String number;

  @Column("city_code")
  private String cityCode;

  @Column("country_code")
  private String countryCode;

  private UUID userId;
}
