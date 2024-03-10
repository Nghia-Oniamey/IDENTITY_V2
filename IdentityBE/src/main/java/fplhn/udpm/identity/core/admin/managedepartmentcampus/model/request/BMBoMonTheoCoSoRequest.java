package fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BMBoMonTheoCoSoRequest {

    @NotNull(message = "Id bộ môn không được để trống")
    private Long idBoMon;

    @NotNull(message = "Id cơ sở không được để trống")
    private Long idCoSo;

    @NotNull(message = "Id CNBM không được để trống")
    private Long idCNBM;

}
