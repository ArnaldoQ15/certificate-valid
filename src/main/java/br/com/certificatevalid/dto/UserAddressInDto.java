package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

import static br.com.certificatevalid.util.Constants.INVALID_CEP_LENGHT;
import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressInDto {

    private Long addressId;

    @NotBlank(message = NOT_BLANK)
    @Length(min = 8, max = 8, message = INVALID_CEP_LENGHT)
    private String zipCode;

    private String number;

    private String complement;

}