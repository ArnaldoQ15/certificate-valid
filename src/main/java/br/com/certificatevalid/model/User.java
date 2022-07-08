package br.com.certificatevalid.model;

import br.com.certificatevalid.enums.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

import static br.com.certificatevalid.util.Constants.*;

@Data
@Entity
@Table(name = "userSystem")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @NotNull(message = NOT_BLANK)
    @Enumerated(EnumType.STRING)
    private DataStatusEnum dataStatus;

    @NotNull(message = NOT_BLANK)
    private String userVerificationCode;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", nullable = false)
    private List<UserAddress> addresses;

}