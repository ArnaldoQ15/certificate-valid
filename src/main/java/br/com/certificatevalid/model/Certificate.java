package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    @ManyToOne
    @JoinColumn(name = "courseId")
    @NotNull(message = NOT_BLANK)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "verificationCodeId")
    @NotNull(message = NOT_BLANK)
    private VerificationCode verificationCode;

    @ManyToOne
    @JoinColumn(name = "userId")
    @NotNull(message = NOT_BLANK)
    private User user;

}