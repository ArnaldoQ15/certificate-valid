package br.com.certificatevalid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;

import static br.com.certificatevalid.util.Constants.NOT_BLANK;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseInDto {

    @NotBlank(message = NOT_BLANK)
    private String title;

    @NotBlank(message = NOT_BLANK)
    private String description;

    @NotNull(message = NOT_BLANK)
    private Long companyId;

    @NotNull(message = NOT_BLANK)
    private OffsetDateTime finishDate;

}