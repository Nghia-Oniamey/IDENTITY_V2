package fplhn.udpm.identity.core.admin.managecampus.model.request;

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
public class CSUpdateCoSoRequest {

    @NotNull(message = "Tên cơ sở không được để trống")
    @NotBlank(message = "Tên cơ sở không được để trống")
    private String tenCoSo;

    @NotNull(message = "Mã cơ sở không được để trống")
    @NotBlank(message = "Mã cơ sở không được để trống")
    private String maCoSo;


}
