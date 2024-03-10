package fplhn.udpm.identity.util;

import fplhn.udpm.identity.core.common.config.PageableRequest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

@Component
public class PaginationUtil {
    private PaginationUtil() {
    }

    public Pageable pageable(PageableRequest paginationRequest) {
        Pageable pageable = null;
        if (paginationRequest.getOrderBy().equals("ascend")) {
            pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), Sort.by(paginationRequest.getSortBy()).ascending());
        } else {
            pageable = PageRequest.of(paginationRequest.getPage() - 1, paginationRequest.getSize(), Sort.by(paginationRequest.getSortBy()).descending());
        }
        return pageable;

    }


}
