package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInDto {

    private Long userId;

    private String userName;

    private String password;

    @CPF
    private String documentCpf;

    @Email
    private String email;

}
