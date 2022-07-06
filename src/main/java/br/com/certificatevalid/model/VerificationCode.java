package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @Length(max = 10)
    private String firstField;

    @Length(max = 10)
    private String secondField;

    @Length(max = 10)
    private String thirdField;

    @Length(max = 10)
    private String fourthField;

    @Length(max = 10)
    private String firthField;

    @Length(max = 50)
    private String fullField;

    @ManyToOne
    @JoinColumn(name = "userId")
    @NotNull(message = NOT_BLANK)
    private User user;

}
