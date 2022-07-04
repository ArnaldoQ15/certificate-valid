package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static br.com.certificatevalid.util.Constants.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInDto {

    private Long userId;

    @NotBlank(message = NOT_BLANK)
    private String username;

    @NotBlank(message = NOT_BLANK)
    private String password;

    @NotBlank(message = NOT_BLANK)
    @CPF(message = CPF_INVALID)
    private String documentCpf;

    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL_INVALID)
    private String email;

}
