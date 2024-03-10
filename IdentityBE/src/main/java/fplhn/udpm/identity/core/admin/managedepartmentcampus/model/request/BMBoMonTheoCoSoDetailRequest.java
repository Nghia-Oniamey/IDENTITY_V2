package fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
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
public class BMBoMonTheoCoSoDetailRequest extends PageableRequest {

    private Long[] arrayField;

    private String xoaMemBoMon;

}
