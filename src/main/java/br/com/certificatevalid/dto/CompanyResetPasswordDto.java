package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static br.com.certificatevalid.util.Constants.EMAIL_INVALID;
import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyResetPasswordDto {

    @NotBlank(message = NOT_BLANK)
    private String companyName;

    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL_INVALID)
    private String contactEmail;

}