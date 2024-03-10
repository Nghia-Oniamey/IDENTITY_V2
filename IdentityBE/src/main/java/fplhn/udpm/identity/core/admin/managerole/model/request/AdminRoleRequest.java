package fplhn.udpm.identity.core.admin.managerole.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class AdminRoleRequest extends PageableRequest {

    private Long id;

    @NotBlank(message = "Mã chức vụ không được để trống")
    private String ma;

    @NotBlank(message = "Tên chức vụ không được để trống")
    private String ten;

    private List<Long> arrayField;

}
