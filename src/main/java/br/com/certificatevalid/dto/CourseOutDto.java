package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseOutDto {

    private Long courseId;

    private String title;

    private String description;

    private String courseVerificationCode;

    private String companyName;

    private OffsetDateTime finishDate;

}