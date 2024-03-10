package fplhn.udpm.identity.core.admin.managestudent.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindStudentRequest extends PageableRequest {

    private String xoaMemSinhVien;

    private Long[] arrayField;

}
