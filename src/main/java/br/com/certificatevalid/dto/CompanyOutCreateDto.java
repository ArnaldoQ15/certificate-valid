package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyOutCreateDto {

    private Long companyId;

    private String companyName;

    private String companyVerificationCode;

    private Long countUser;

    private String companyPassword;

}