package fplhn.udpm.identity.infrastructure.config.excelconfig.model.dto;

import fplhn.udpm.identity.entity.Staff;
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
public class TransferRequestExcel {

    private Staff nhanVien;

}
