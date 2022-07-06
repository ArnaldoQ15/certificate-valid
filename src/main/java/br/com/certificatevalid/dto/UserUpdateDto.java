package br.com.certificatevalid.dto;

import br.com.certificatevalid.enums.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDto {

    private String username;

    private String password;

    private String email;

    private DataStatusEnum dataStatus;

}