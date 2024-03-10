package fplhn.udpm.identity.core.common.config;

import fplhn.udpm.identity.infrastructure.constant.PaginationConstant;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PageableRequest {

    private int page = PaginationConstant.DEFAULT_PAGE;
    private int size = PaginationConstant.DEFAULT_SIZE;
    private String orderBy = PaginationConstant.DEFAULT_ORDER_BY;
    private String sortBy = PaginationConstant.DEFAULT_SORT_BY;
    private String q = PaginationConstant.DEFAULT_Q;

}