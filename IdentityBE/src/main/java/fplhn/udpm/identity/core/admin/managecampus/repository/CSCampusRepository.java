package fplhn.udpm.identity.core.admin.managecampus.repository;

import fplhn.udpm.identity.core.admin.managecampus.model.request.CSFindCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSCoSoResponse;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.repository.CampusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CSCampusRepository extends CampusRepository {

//    CHUA_XOA,
//    DA_XOA,
//    Xoa_Han,

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER( 
            	ORDER BY cs.id DESC) AS stt, 
            	cs.id as idCoSo, 
            	cs.ten as tenCoSo, 
            	cs.ma as maCoSo,
            	cs.xoa_mem as xoaMemCoSo 
            FROM 
            	co_so cs 
            WHERE 
            	( :#{#req.arrayField} IS NULL 
            	OR :#{#req.arrayField} = '' ) 
            	OR cs.id IN :#{#req.arrayField} 
            """, countQuery = """
            SELECT 
            	COUNT(cs.id) 
            FROM 
            	co_so cs 
            WHERE 
            	( :#{#req.arrayField} IS NULL 
            	OR :#{#req.arrayField} = '' ) 
            	OR cs.id IN :#{#req.arrayField} 
            """, nativeQuery = true)
    Page<CSCoSoResponse> search(Pageable pageable, @Param("req") CSFindCoSoRequest req);

    @Query(value = """
            SELECT 
            	ROW_NUMBER() OVER( 
            	ORDER BY cs.id DESC) AS stt, 
            	cs.id as idCoSo, 
            	cs.ten as tenCoSo, 
            	cs.ma as maCoSo,
            	cs.xoa_mem as xoaMemCoSo 
            FROM 
            	co_so cs 
            """, countQuery = """
            SELECT 
            	COUNT(cs.id) 
            FROM 
            	co_so cs 
            """, nativeQuery = true)
    Page<CSCoSoResponse> search(Pageable pageable);

    Boolean existsByMa(String ma);

    @Query(value = """
            SELECT id as idCoSo , ten as tenCoSo, ma as maCoSo  FROM co_so
            """, nativeQuery = true)
    List<CSListCoSoResponse> getListCoSo();

}
