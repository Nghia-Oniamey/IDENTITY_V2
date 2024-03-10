package fplhn.udpm.identity.core.admin.managestaff.repository;

import fplhn.udpm.identity.core.admin.managestaff.model.response.DetailStaffResponse;
import fplhn.udpm.identity.core.admin.managestaff.model.response.StaffResponse;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.repository.StaffRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StaffCustomRepository extends StaffRepository {

    @Query(
            value = """
                    SELECT
                        nv.id as id,
                        nv.ma_nhan_vien as maNhanVien,
                        nv.ho_ten AS tenNhanVien,
                        nv.tai_khoan_fe as taiKhoanFE,
                        nv.tai_khoan_fpt as taiKhoanFPT,
                        bm.ten as tenBoMon
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    WHERE nv.xoa_mem = 'NOT_DELETED'
                    GROUP BY
                        nv.id, nv.ma_nhan_vien, nv.ho_ten, nv.tai_khoan_fe, nv.tai_khoan_fpt, bm.ten
                    """,
            countQuery = """
                    SELECT
                        COUNT(DISTINCT nv.id)
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    WHERE nv.xoa_mem = 'NOT_DELETED'
                        """,
            nativeQuery = true
    )
    Page<List<StaffResponse>> findAllNhanVien(Pageable pageable);

    Optional<Staff> findByAccountFE(String emailFe);

    Optional<Staff> findByAccountFPT(String emailFpt);

    Optional<Staff> findByStaffCode(String maNhanVien);

    @Query(
            value = """

                    SELECT 
                    	nv.ma_nhan_vien as maNhanVien,
                    	nv.ho_ten as tenNhanVien,
                    	bm.id as idBoMon,
                    	cs.id as idCoSo,
                    	nv.tai_khoan_fpt as emailFpt,
                    	nv.tai_khoan_fe as emailFe,
                    	nv.so_dien_thoai as soDienThoai
                    FROM
                    	nhan_vien nv
                     LEFT JOIN
                    	[nhan_vien-chuc_vu] nvcv on nv.id = nvcv.id_nhan_vien	
                    LEFT JOIN
                    	chuc_vu cv on
                    	nvcv.id_chuc_vu = cv.id
                    LEFT JOIN
                    	bo_mon_theo_co_so bmtcs on
                    	nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                    	bo_mon bm ON
                    	bmtcs.id_bo_mon = bm.id
                    LEFT JOIN 
                        co_so cs on
                        bmtcs.id_co_so = cs.id                   	
                    WHERE
                        nv.id = :id 
                    group by nv.ma_nhan_vien, nv.tai_khoan_fe, nv.tai_khoan_fpt, nv.ho_ten, bm.id, cs.id, nv.so_dien_thoai
                    """,
            nativeQuery = true
    )
    DetailStaffResponse findDetailNhanVienById(Long id);

    @Query(
            value = """
                        SELECT
                        nv.id as id,
                        nv.ma_nhan_vien as maNhanVien,
                        nv.ho_ten AS tenNhanVien,
                        nv.tai_khoan_fe as taiKhoanFE,
                        nv.tai_khoan_fpt as taiKhoanFPT,
                        bm.ten as tenBoMon
                    FROM
                        nhan_vien nv
                    LEFT JOIN
                        bo_mon_theo_co_so bmtcs on nv.id_bo_mon_theo_co_so = bmtcs.id
                    LEFT JOIN
                        bo_mon bm on bmtcs.id_bo_mon = bm.id
                    WHERE nv.xoa_mem = 'NOT_DELETED'
                    GROUP BY
                        nv.id, nv.ma_nhan_vien, nv.tai_khoan_fe, nv.ho_ten, nv.tai_khoan_fpt, bm.ten
                    """,
            nativeQuery = true
    )
    List<StaffResponse> findAllNhanVien();

}
