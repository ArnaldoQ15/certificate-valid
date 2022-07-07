package br.com.certificatevalid.model;

import br.com.certificatevalid.enums.CourseDataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long courseId;

    @NotBlank(message = NOT_BLANK)
    private String title;

    @NotBlank(message = NOT_BLANK)
    private String description;

    private String courseVerificationCode;

    @ManyToOne
    @JoinColumn(name = "companyId")
    private Company company;

    @NotNull(message = NOT_BLANK)
    @Enumerated(EnumType.STRING)
    private CourseDataStatusEnum dataStatus;

    @NotNull(message = NOT_BLANK)
    private OffsetDateTime finishDate;

}