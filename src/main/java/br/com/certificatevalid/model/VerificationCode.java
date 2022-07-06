package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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

    @Length(max = 20)
    private String secondField;

    @Length(max = 10)
    private String thirdField;

    @Length(max = 10)
    private String fourthField;

    @Length(max = 10)
    private String firthField;

    @Length(max = 60)
    private String fullField;

}