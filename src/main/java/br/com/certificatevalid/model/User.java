package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import static br.com.certificatevalid.util.Constants.*;

@Data
@Entity
@Table(name = "userSystem")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    @NotBlank(message = NOT_BLANK)
    private String username;

    @Column
    @NotBlank(message = NOT_BLANK)
    private String password;

    @Column
    @NotBlank(message = NOT_BLANK)
    @CPF(message = CPF_INVALID)
    private String documentCpf;

    @Column
    @NotBlank(message = NOT_BLANK)
    @Email(message = EMAIL_INVALID)
    private String email;

    @ManyToOne
    @JoinColumn(name = "companyId")
    @ToString.Exclude
    private Company company;

}
