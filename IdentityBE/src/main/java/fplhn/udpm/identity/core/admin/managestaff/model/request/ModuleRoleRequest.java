package fplhn.udpm.identity.core.admin.managestaff.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ModuleRoleRequest {

    @NotNull(message = "Mã module không được để trống")
    private Long moduleId;

    @NotNull(message = "Danh sách role không được để trống")
    private List<Long> roleIds;

}
