package fplhn.udpm.identity.core.admin.managestudent.repository;

import fplhn.udpm.identity.core.admin.managestudent.model.request.FindStudentRequest;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.CbbBoMonTheoCoSoRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.ListStudentRespone;
import fplhn.udpm.identity.core.admin.managestudent.model.respone.StudentRespone;
import fplhn.udpm.identity.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentsRepository extends StudentRepository {

    //    CHUA_XOA,
//    DA_XOA,
//    Xoa_Han,
    @Query(value = """
            SELECT
            ROW_NUMBER() OVER(
            ORDER BY
                    SV.id DESC) AS stt,
                    sv.id as idSinhVien,
                    sv.ma_sinh_vien as maSinhVien,
                    sv.ho_ten as tenSinhVien,
                    sv.xoa_mem as xoaMemSinhVien,
                    sv.email_fpt as mailSinhVien,
                    sv.so_dien_thoai as sdtSinhVien,
                    sv.id_bo_mon_theo_co_so as idBoMonTheoCoSo,
                    CONCAT(bm.ten, '-', cs.ten) as tenBoMonTheoCoSo
            FROM
                    sinh_vien sv
                    JOIN bo_mon_theo_co_so bmtcs
                    ON sv.id_bo_mon_theo_co_so = bmtcs.id
                    JOIN bo_mon bm
                    ON bm.id = bmtcs.id_bo_mon
                    JOIN co_so cs
                    ON cs.id = bmtcs.id_co_so
            WHERE 
                	( :#{#req.arrayField} IS NULL 
                	OR :#{#req.arrayField} = '' ) 
                	OR sv.id IN :#{#req.arrayField} 
                """, countQuery = """
            SELECT 
                	COUNT(sv.id) 
            FROM
                    sinh_vien sv
                    JOIN bo_mon_theo_co_so bmtcs
                    ON sv.id_bo_mon_theo_co_so = bmtcs.id
                    JOIN bo_mon bm
                    ON bm.id = bmtcs.id_bo_mon
                    JOIN co_so cs
                    ON cs.id = bmtcs.id_co_so
            WHERE 
                	( :#{#req.arrayField} IS NULL 
                	OR :#{#req.arrayField} = '' ) 
                	OR sv.id IN :#{#req.arrayField} 
                """, nativeQuery = true)
    Page<StudentRespone> search(Pageable pageable, @Param("req") FindStudentRequest req);

    @Query(value = """
            SELECT
                  ROW_NUMBER() OVER(
                  ORDER BY
                          SV.id DESC) AS stt,
                          sv.id as idSinhVien,
                          sv.ma_sinh_vien as maSinhVien,
                          sv.ho_ten as tenSinhVien,
                          sv.xoa_mem as xoaMemSinhVien,
                          sv.email_fpt as mailSinhVien,
                          sv.so_dien_thoai as sdtSinhVien,
                          sv.id_bo_mon_theo_co_so as idBoMonTheoCoSo,
                          CONCAT(bm.ten, '-', cs.ten) as tenBoMonTheoCoSo
                  FROM
                          sinh_vien sv
                          JOIN bo_mon_theo_co_so bmtcs
                          ON sv.id_bo_mon_theo_co_so = bmtcs.id
                          JOIN bo_mon bm
                          ON bm.id = bmtcs.id_bo_mon
                          JOIN co_so cs
                          ON cs.id = bmtcs.id_co_so
                      """, countQuery = """
            SELECT 
            	COUNT(sv.id) 
            FROM
                sinh_vien sv
                JOIN bo_mon_theo_co_so bmtcs
                ON sv.id_bo_mon_theo_co_so = bmtcs.id
                JOIN bo_mon bm
                ON bm.id = bmtcs.id_bo_mon
                JOIN co_so cs
                ON cs.id = bmtcs.id_co_so
            """, nativeQuery = true)
    Page<StudentRespone> searchAll(Pageable pageable);

    @Query(value = """
            SELECT id as idSinhVien , ho_ten as tenSinhVien, ma_sinh_vien as maSinhVien  FROM sinh_vien
            """, nativeQuery = true)
    List<ListStudentRespone> getListSinhVien();

    @Query(value = """
            SELECT
            	ROW_NUMBER() OVER(
            ORDER BY
            	bmtcs.id DESC) as stt,
            	bmtcs.id as idBoMonTheoCoSo,
            	CONCAT(bm.ten, '-', cs.ten) as tenBoMonTheoCoSo
            from
            	bo_mon_theo_co_so bmtcs
            JOIN bo_mon bm
                            ON
            	bm.id = bmtcs.id_bo_mon
            JOIN co_so cs
                            ON
            	cs.id = bmtcs.id_co_so
                        """, nativeQuery = true)
    List<CbbBoMonTheoCoSoRespone> getComboboxBoMonTheoCoSo();

    @Query(value = """
            SELECT
                  ROW_NUMBER() OVER(
                  ORDER BY
                          SV.id DESC) AS stt,
                          sv.id as idSinhVien,
                          sv.ma_sinh_vien as maSinhVien,
                          sv.ho_ten as tenSinhVien,
                          sv.xoa_mem as xoaMemSinhVien,
                          sv.email_fpt as mailSinhVien,
                          sv.so_dien_thoai as sdtSinhVien,
                          sv.id_bo_mon_theo_co_so as idBoMonTheoCoSo,
                          CONCAT(bm.ten, '-', cs.ten) as tenBoMonTheoCoSo
                  FROM
                          sinh_vien sv
                          JOIN bo_mon_theo_co_so bmtcs
                          ON sv.id_bo_mon_theo_co_so = bmtcs.id
                          JOIN bo_mon bm
                          ON bm.id = bmtcs.id_bo_mon
                          JOIN co_so cs
                          ON cs.id = bmtcs.id_co_so
                  where sv.id=:idSV
                      """, nativeQuery = true)
    StudentRespone DetailSinhVien(@Param("idSV") Long idSV);
}
