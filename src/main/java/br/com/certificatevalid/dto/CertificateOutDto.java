package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CertificateOutDto {

    private Long certificateId;

    private String username;

    private String companyName;

    private String verificationCode;

}