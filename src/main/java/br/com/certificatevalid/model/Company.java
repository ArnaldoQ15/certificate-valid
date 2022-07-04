package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static br.com.certificatevalid.util.Constants.EMAIL_INVALID;
import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long companyId;

    @Column
    @NotBlank(message = NOT_BLANK)
    private String companyName;

    @Column
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL_INVALID)
    private String contactEmail;

    @Column
    private String companyVerificationCode;

}
