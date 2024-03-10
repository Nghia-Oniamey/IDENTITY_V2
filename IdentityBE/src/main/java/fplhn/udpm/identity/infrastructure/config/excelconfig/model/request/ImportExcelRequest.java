package fplhn.udpm.identity.infrastructure.config.excelconfig.model.request;

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
public class ImportExcelRequest {

    private String idGiaoVien;

    private String hoTen;

    private String chucVu;

    private String emailFpt;

    private String emailFe;

    private String tenBoMonTheoCoSo;

}
