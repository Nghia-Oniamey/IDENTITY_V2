package fplhn.udpm.identity.core.admin.managedepartment.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BMFindBoMonRequest extends PageableRequest {

    private String xoaMemBoMon;

    private Long[] arrayField;

}
