package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class VerificationCode {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codeId;

    @Column
    @Length(max = 10)
    private String firstField;

    @Column
    @Length(max = 10)
    private String secondField;

    @Column
    @Length(max = 10)
    private String thirdField;

    @Column
    @Length(max = 10)
    private String fourthField;

    @Column
    @Length(max = 10)
    private String firthField;

    @Column
    @Length(max = 50)
    private String fullField;

}
