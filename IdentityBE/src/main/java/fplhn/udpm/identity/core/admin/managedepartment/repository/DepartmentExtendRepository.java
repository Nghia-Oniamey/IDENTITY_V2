package fplhn.udpm.identity.core.admin.managedepartment.repository;

import fplhn.udpm.identity.core.admin.managedepartment.model.request.BMFindBoMonRequest;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMBoMonResponse;
import fplhn.udpm.identity.core.admin.managedepartment.model.response.BMListBoMonResponse;
import fplhn.udpm.identity.repository.DepartmentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentExtendRepository extends DepartmentRepository {

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(
            	ORDER BY bm.id DESC) AS stt,
            	bm.id as idBoMon,
            	bm.ma as maBoMon,
            	bm.ten as tenBoMon,
            	bm.xoa_mem as xoaMemBoMon
            FROM
            	bo_mon bm
            WHERE
            	:#{#req.arrayField} IS NULL
            	OR :#{#req.arrayField} LIKE ''
            	OR bm.id IN :#{#req.arrayField}
            """, countQuery = """
            SELECT 
                COUNT(bm.id) FROM bo_mon bm 
            WHERE 
                :#{#req.arrayField} IS NULL
            	OR :#{#req.arrayField} LIKE ''
            	OR bm.id IN :#{#req.arrayField}
            """, nativeQuery = true)
    Page<BMBoMonResponse> getAllBoMonByFilter(Pageable pageable, @Param("req") BMFindBoMonRequest req);

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(
            	ORDER BY bm.id DESC) AS stt,
            	bm.id as idBoMon,
            	bm.ma as maBoMon,
            	bm.ten as tenBoMon,
            	bm.xoa_mem as xoaMemBoMon
            FROM
            	bo_mon bm
            """, countQuery = """
            SELECT 
                COUNT(bm.id) FROM bo_mon bm 
            """, nativeQuery = true)
    Page<BMBoMonResponse> getAllBoMon(Pageable pageable);

    Boolean existsByMa(String maBoMon);

    @Query(value = """
            SELECT 
                id as idBoMon,
                ma as maBoMon,
                ten as tenBoMon
            FROM
                bo_mon bm
            """, nativeQuery = true)
    List<BMListBoMonResponse> getListBoMon();

}
