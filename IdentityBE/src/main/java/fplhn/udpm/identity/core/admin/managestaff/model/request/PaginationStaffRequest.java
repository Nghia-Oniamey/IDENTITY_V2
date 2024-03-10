package fplhn.udpm.identity.core.admin.managestaff.model.request;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
public class PaginationStaffRequest extends PageableRequest {

    private String column;

    private Sort.Direction type;

}
