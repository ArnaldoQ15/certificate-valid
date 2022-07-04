package br.com.certificatevalid.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionArgumentNotValidDto {

    private HttpStatus status;

    private String message;

    private OffsetDateTime timestamp;

    private List<Warnings> warnings;

    @AllArgsConstructor
    @Getter
    public static class Warnings {

        private String field;

        private String message;

    }

}
