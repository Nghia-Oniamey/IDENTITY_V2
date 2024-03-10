package fplhn.udpm.identity.core.admin.managemodule.repository;

import fplhn.udpm.identity.core.admin.managemodule.model.request.FindModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ListModuleResponse;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ModuleResponse;
import fplhn.udpm.identity.repository.ModuleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CSModuleRepository extends ModuleRepository {

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY md.id DESC) AS stt,
                md.id AS idModule,
                md.ten AS tenModule,
                md.ma AS maModule,
                md.url AS urlModule,
                md.xoa_mem AS xoaMemModule
            FROM module md
            WHERE 
                  (:#{#request.listId} IS NULL
                  OR :#{#request.listId} = '' )
                  OR md.id IN :#{#request.listId}
            """,
            countQuery = """
                    SELECT 
                    	COUNT(md.id) 
                    FROM 
                    	module md
                    WHERE 
                    	( :#{#request.listId} IS NULL 
                    	OR :#{#request.listId} = '' ) 
                    	OR md.id IN :#{#request.listId}
                    """
            , nativeQuery = true)
    Page<ModuleResponse> searchModuleByListId(Pageable pageable, @Param("request") FindModuleRequest request);

    @Query(value = """
            SELECT ROW_NUMBER() OVER(ORDER BY md.id DESC) AS stt,
              md.id AS idModule,
              md.ten AS tenModule,
              md.[url] AS urlModule,
              md.ma AS maModule,
              md.xoa_mem AS xoaMemModule
            FROM module md
            """, countQuery = """
            SELECT 
            	COUNT(md.id) 
            FROM 
            	module md
            """,
            nativeQuery = true)
    Page<ModuleResponse> searchAllModule(Pageable pageable);

    Boolean existsByTen(String ten);

    Boolean existsByUrl(String url);

    Boolean existsByTenAndId(String tenModule, Long id);

    Boolean existsByUrlAndId(String urlModule, Long id);

    Boolean existsByMa(String ma);

    @Query(value = """
            SELECT id AS idModule, ma AS maModule,ten AS tenModule,[url] AS urlModule FROM module
            """, nativeQuery = true)
    List<ListModuleResponse> getAllModule();
}
