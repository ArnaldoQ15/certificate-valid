package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

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

    @NotEmpty(message = NOT_BLANK)
    @Valid
    private List<UserAddressInDto> addresses;

}
