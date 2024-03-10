package fplhn.udpm.identity.core.admin.managedepartmentcampus.repository;


import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoDetailRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.request.BMBoMonTheoCoSoRequest;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.BMListBoMonTheoCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.core.admin.managedepartmentcampus.model.response.NVListNhanVienResponse;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BMDepartmentCampusRepository extends DepartmentCampusRepository {

    @Query(value = """
               SELECT
               	ROW_NUMBER() OVER(
               	ORDER BY bmtcs.id DESC) as stt,
               	bmtcs.id as idBoMonTheoCoSo,
               	bmtcs.id_co_so as idCoSo,
               	bmtcs.id_chu_nhiem_bo_mon as idCNBM,
               	bmtcs.xoa_mem as xoaMemBoMonTheoCoSo,
               	cs.ten as tenCoSo,
               	nv.ho_ten as tenCNBM,
               	nv.ma_nhan_vien as maNhanVien
               FROM
               	bo_mon_theo_co_so bmtcs
               LEFT JOIN co_so cs ON
               	cs.id = bmtcs.id_co_so
               LEFT JOIN bo_mon bm ON
               	bm.id = bmtcs.id_bo_mon
               LEFT JOIN nhan_vien nv ON
               	nv.id = bmtcs.id_chu_nhiem_bo_mon
               WHERE
               	(bmtcs.id_bo_mon = :id)
               	AND (:#{#req.arrayField} IS NULL
               		OR :#{#req.arrayField} LIKE ''
               		OR cs.id IN %:#{#req.arrayField}%)
            """,
            countQuery = """
                    SELECT
                    	COUNT(bmtcs.id)
                    FROM
                    	bo_mon_theo_co_so bmtcs
                    LEFT JOIN co_so cs ON
                    	cs.id = bmtcs.id_co_so
                    LEFT JOIN bo_mon bm ON
                    	bm.id = bmtcs.id_bo_mon
                    LEFT JOIN nhan_vien nv ON
                    	nv.id = bmtcs.id_chu_nhiem_bo_mon
                    WHERE
                    	(bmtcs.id_bo_mon = :id)
                    	AND (:#{#req.arrayField} IS NULL
                    		OR :#{#req.arrayField} LIKE ''
                    		OR cs.id IN %:#{#req.arrayField}%)
                        """,
            nativeQuery = true
    )
    Page<BMBoMonTheoCoSoResponse> getBoMonTheoCoSosByValueFind(Long id, Pageable pageable, @Param("req") BMBoMonTheoCoSoDetailRequest req);

    @Query(value = """
               SELECT
               	ROW_NUMBER() OVER(
               	ORDER BY bmtcs.id DESC) as stt,
               	bmtcs.id as idBoMonTheoCoSo,
               	bmtcs.id_co_so as idCoSo,
               	bmtcs.id_chu_nhiem_bo_mon as idCNBM,
               	bmtcs.xoa_mem as xoaMemBoMonTheoCoSo,
               	cs.ten as tenCoSo,
               	nv.ho_ten as tenCNBM,
               	nv.ma_nhan_vien as maNhanVien   
               FROM
               	bo_mon_theo_co_so bmtcs
               LEFT JOIN co_so cs ON
               	cs.id = bmtcs.id_co_so
               LEFT JOIN bo_mon bm ON
               	bm.id = bmtcs.id_bo_mon
               LEFT JOIN nhan_vien nv ON
               	nv.id = bmtcs.id_chu_nhiem_bo_mon
               WHERE
               	(bmtcs.id_bo_mon = :id)
            """,
            countQuery = """
                    SELECT
                    	COUNT(bmtcs.id)
                    FROM
                    	bo_mon_theo_co_so bmtcs
                    LEFT JOIN co_so cs ON
                    	cs.id = bmtcs.id_co_so
                    LEFT JOIN bo_mon bm ON
                    	bm.id = bmtcs.id_bo_mon
                    LEFT JOIN nhan_vien nv ON
                    	nv.id = bmtcs.id_chu_nhiem_bo_mon
                    WHERE
                    	(bmtcs.id_bo_mon = :id)
                        """,
            nativeQuery = true
    )
    Page<BMBoMonTheoCoSoResponse> getBoMonTheoCoSosByValueFind(Long id, Pageable pageable);


    @Query(value = """
                SELECT 
                    ten as tenBoMon
                FROM 
                    bo_mon
                WHERE
                    id = :id
            """, nativeQuery = true)
    String getTenBoMon(Long id);

    @Query(value = """
                SELECT
                	id,
                	id_bo_mon,
                	id_chu_nhiem_bo_mon,
                	id_co_so,
                	xoa_mem
                FROM
                	bo_mon_theo_co_so
                WHERE
                	(:#{#req.idBoMon} IS NULL
                		OR :#{#req.idBoMon} LIKE ''
                		OR id_bo_mon = :#{#req.idBoMon})
                	AND (:#{#req.idCoSo} IS NULL
                		OR :#{#req.idCoSo} LIKE ''
                		OR id_co_so = :#{#req.idCoSo})
            """, nativeQuery = true)
    Optional<DepartmentCampus> existsByIdBoMonAndIdCoSoAndIdAdd(@Param("req") BMBoMonTheoCoSoRequest req);

    boolean existsBoMonTheoCoSoByCampusAndDepartment(Campus campus, Department department);

    @Query(value = """
                SELECT
                	id as idCoSo,
                	ten as tenCoSo
                FROM
                	co_so
                WHERE
                	xoa_mem LIKE 'NOT_DELETED'
            """, nativeQuery = true)
    List<CSListCoSoResponse> getListCoSo();

    @Query(value = """
                    SELECT
                        id as idNhanVien,
                        ho_ten as tenNhanVien,
                        ma_nhan_vien as maNhanVien
                     FROM
                        nhan_vien
                    WHERE
                        xoa_mem LIKE 'NOT_DELETED'
            """, nativeQuery = true)
    List<NVListNhanVienResponse> getListNhanVien();

    @Query(value = """
                        SELECT
                        	bmtcs.id_co_so as idCoSo,
                        	nv.ho_ten as tenNhanVien,
                        	cs.ten as tenCoSo,
                        	nv.ma_nhan_vien as maNhanVien
                        FROM
                        	bo_mon_theo_co_so bmtcs
                        LEFT JOIN nhan_vien nv ON
                        	nv.id = bmtcs.id_chu_nhiem_bo_mon
                        LEFT JOIN co_so cs ON
                        	cs.id = bmtcs.id_co_so
                        WHERE
                            bmtcs.id_bo_mon = :id
            """, nativeQuery = true)
    List<BMListBoMonTheoCoSoResponse> getListBoMonTheoCoSo(Long id);

}
