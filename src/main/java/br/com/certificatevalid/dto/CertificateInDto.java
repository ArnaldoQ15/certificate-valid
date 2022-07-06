package br.com.certificatevalid.dto;

import br.com.certificatevalid.model.Company;
import br.com.certificatevalid.model.User;
import br.com.certificatevalid.model.VerificationCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CertificateInDto {

    @NotNull(message = NOT_BLANK)
    private Long userId;

    @NotNull(message = NOT_BLANK)
    private Long courseId;

}
