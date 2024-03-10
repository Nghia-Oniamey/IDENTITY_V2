package fplhn.udpm.identity.core.admin.managestudent.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateStudentRequest {

    Long idSinhVien;

    private String maSinhVien;

    private String tenSinhVien;

    private String mailSinhVien;

    private String sdtSinhVien;

    private Long idBoMonTheoCoSo;

}
