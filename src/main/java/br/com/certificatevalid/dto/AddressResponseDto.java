package br.com.certificatevalid.dto;

import br.com.certificatevalid.enums.FederativeUnit;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AddressResponseDto {

    @JsonProperty("logradouro")
    private String street;

    @JsonProperty("bairro")
    private String district;

    @JsonProperty("complemento")
    private String complement;

    @JsonProperty("localidade")
    private String city;

    @JsonProperty("uf")
    private FederativeUnit state;

}