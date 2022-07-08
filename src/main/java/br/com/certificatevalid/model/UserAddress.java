package br.com.certificatevalid.model;

import br.com.certificatevalid.enums.FederativeUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAddress {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long addressId;

    private String street;

    private String number;

    private String district;

    private String complement;

    private String city;

    @Enumerated(EnumType.STRING)
    private FederativeUnit state;

    private String zipCode;

}