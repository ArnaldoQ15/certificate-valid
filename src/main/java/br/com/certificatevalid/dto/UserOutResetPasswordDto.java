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
public class UserOutResetPasswordDto {

    private Long userId;

    private String userName;

    private String email;

    private DataStatusEnum dataStatus;

    private String companyName;

    private String temporaryPassword;

}
