package br.com.certificatevalid.dto;

import br.com.certificatevalid.enums.DataStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOutDto {

    private Long userId;

    private String userName;

    private String email;

    private DataStatusEnum dataStatus;

    private String companyName;

}
