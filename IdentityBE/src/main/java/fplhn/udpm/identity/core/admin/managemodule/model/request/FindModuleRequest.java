package fplhn.udpm.identity.core.admin.managemodule.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FindModuleRequest extends PageableRequest {

    private String xoaMem;

    private Long[] listId;
}
