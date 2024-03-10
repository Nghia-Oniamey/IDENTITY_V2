package fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BMFindBoMonTheoCoSoByIdBoMonAndIdBoMonTheoCoSoRequest {

    @NotNull(message = "Id bộ môn không được để trống")
    private Long idBoMon;

    @NotNull(message = "Id bộ môn theo cơ sở không được để trống")
    private Long idBoMonTheoCoSo;
}
