package fplhn.udpm.identity.core.admin.managemodule.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateModuleRequest {

    @NotNull(message = "Tên mô-đun không được để trống")
    @NotBlank(message = "Tên mô-đun không được để trống")
    private String tenModule;

    @NotNull(message = "Mã mô-đun không được để trống")
    @NotBlank(message = "Mã mô-đun không được để trống")
    private String maModule;

    @NotNull(message = "Địa chỉ mô-đun không được để trống")
    @NotBlank(message = "Địa chỉ mô-đun không được để trống")
    private String urlModule;
}
