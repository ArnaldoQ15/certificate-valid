package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static br.com.certificatevalid.util.Constants.EMAIL_INVALID;
import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUpdateDto {

    @NotNull(message = NOT_BLANK)
    private Long userId;

    private String companyName;

    @Email(message = EMAIL_INVALID)
    private String contactEmail;

    @NotBlank(message = NOT_BLANK)
    private String companyPassword;

}
