package br.com.certificatevalid.dto;

import br.com.certificatevalid.enums.FederativeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddressOutDto {

    private Long addressId;

    private String zipCode;

    private String street;

    private String number;

    private String complement;

    private String district;

    private String city;

    private FederativeUnit state;

}