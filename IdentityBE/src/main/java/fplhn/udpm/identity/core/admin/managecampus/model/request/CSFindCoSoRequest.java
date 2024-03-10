package fplhn.udpm.identity.core.admin.managecampus.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CSFindCoSoRequest extends PageableRequest {

    private String xoaMemCoSo;

    private Long[] arrayField;

}
