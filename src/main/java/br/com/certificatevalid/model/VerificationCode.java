package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String firstField;

    @Column
    private String secondField;

    @Column
    private String thirdField;

    @Column
    private String fourthField;

    @Column
    private String firthField;

    @Column
    private String fullField;

}
