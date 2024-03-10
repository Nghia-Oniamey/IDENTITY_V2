package fplhn.udpm.identity.infrastructure.config.dbgenerator;

import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.entity.Department;
import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.config.dbgenerator.repository.CampusTempRepository;
import fplhn.udpm.identity.infrastructure.config.dbgenerator.repository.DepartmentTempRepository;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import fplhn.udpm.identity.repository.DepartmentCampusRepository;
import fplhn.udpm.identity.repository.StaffRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DBGenerator {

    private final DepartmentTempRepository departmentRepository;

    private final DepartmentCampusRepository departmentCampusRepository;

    private final CampusTempRepository campusRepository;

    private final StaffRepository staffRepository;

    public DBGenerator(
            DepartmentTempRepository departmentRepository,
            DepartmentCampusRepository departmentCampusRepository,
            CampusTempRepository campusRepository,
            StaffRepository staffRepository
    ) {
        this.departmentRepository = departmentRepository;
        this.departmentCampusRepository = departmentCampusRepository;
        this.campusRepository = campusRepository;
        this.staffRepository = staffRepository;
    }

    // more @Autowired

    // cấu hình

    String[] tenCoSoTo = {
            "Đà Nẵng",
            "Hà Nội",
            "Hồ Chí Minh",
            "Cần Thơ",
            "Tây Nguyên",
            "Hải Phòng",
            "Đồng Nai",
            "Bắc Giang",
            "Thừa Thiên Huế",
            "Thái Nguyên",
            "Bình Định",
            "Bình Dương",
            "Hà Nam",
            "Vĩnh Phúc",
            "Thanh Hóa",
            "Quảng Nam",
            "Bình Phước",
            "Bà Rịa Vũng Tàu",
            "Nghệ An",
            "Nam Định",
            "Nha Trang"
    };

    String[] maCoSoTo = {
            "DN",
            "HN",
            "HCM",
            "CT",
            "TN",
            "HP",
            "DNai",
            "BG",
            "TTH",
            "TN",
            "BD",
            "BDuong",
            "HNam",
            "VP",
            "THoa",
            "QNam",
            "BPhuoc",
            "BRVT",
            "NA",
            "ND",
            "NT"
    };

    String[] tenBoMonCNTT = {
            "Công nghệ thông tin",
            "Quản trị kinh doanh",
            "Công nghệ kỹ thuật điều khiển và Tự động hoá",
            "Ứng dụng phần mềm",
            "Thẩm mỹ làm đẹp",
    };

    String[] maBoMonCNTT = {
            "CNTT",
            "QTKD",
            "CNKTDKVTDH",
            "UDPM",
            "TMLD"
    };

    String[] tenGmailFpt = {
            "nghiathph32178@fpt.edu.vn",
            "hieunmph42056@fpt.edu.vn",
            "luatlvph31971@fpt.edu.vn",
            "cuongnbph35909@fpt.edu.vn",
            "hangnt169@fpt.edu.vn",
            "dungna29@fpt.edu.vn",
            "tiennh21@fpt.edu.vn",
            "nganct6@fpt.edu.vn",
            "admin@fpt.edu.vn"
    };

    String[] tenGmailFe = {
            "nghiathph32178@fe.edu.vn",
            "hieunmph42056@fe.edu.vn",
            "luatlvph31971@fe.edu.vn",
            "cuongnbph35909@fe.edu.vn",
            "hangnt169@fe.edu.vn",
            "dungna29@fe.edu.vn",
            "tiennh21@fe.edu.vn",
            "nganct6@fe.edu.vn",
            "admin@fe.edu.vn"
    };

    String[] maNhanVien = {
            "nghiathph32178",
            "hieunmph42056",
            "luatlvph31971",
            "cuongnbph35909",
            "hangnt169",
            "dungna29",
            "tiennh21",
            "nganct6",
            "admin"
    };

    String[] tenNhanVien = {
            "Trịnh Hiếu Nghĩa",
            "Nguyễn Minh Hiếu",
            "Lục Văn Luật",
            "Nguyễn Bá Cường",
            "Nguyễn Thúy Hằng",
            "Nguyễn Anh Dũng",
            "Nguyễn Hoàng Tiến",
            "Chu Thị Ngân",
            "ADMIN"
    };

    /**
     * main chạy
     *
     * @throws Exception
     */
//    @PostConstruct
    private void GeneratorData() throws Exception {

        dataCoSo();

        dataBoMon();

        dataBoMonTheoCoSo();

        dataNhanVien();

        dataBoMonCoSoChuNhiem();

    }

    /**
     * Cơ sở to
     *
     * @throws Exception
     */

    private void dataCoSo() throws Exception {

        // Thêm từng Bộ môn vào cơ sở dữ liệu
        for (int i = 0; i < tenCoSoTo.length; i++) {
            String ten = tenCoSoTo[i];
            String ma = maCoSoTo[i];

            Campus coSo = new Campus();
            coSo.setTen(ten);
            coSo.setMa(ma);
            coSo.setDeleteStatus(DeleteStatus.NOT_DELETED);
            campusRepository.save(coSo);
        }
    }

    /**
     * Bộ môn
     *
     * @throws Exception
     */

    private void dataBoMon() throws Exception {

        // Thêm từng Bộ môn vào cơ sở dữ liệu
        for (int i = 0; i < maBoMonCNTT.length; i++) {
            String ten = tenBoMonCNTT[i];
            String ma = maBoMonCNTT[i];
            Department boMon = new Department();
            boMon.setTen(ten);
            boMon.setMa(ma);
            boMon.setDeleteStatus(DeleteStatus.NOT_DELETED);
            departmentRepository.save(boMon);
        }

    }

    /**
     * Bộ môn theo cơ sở
     */

    private void dataBoMonTheoCoSo() throws Exception {
        for (String coSoTo : tenCoSoTo) {
            Campus coSo = campusRepository.findByTen(coSoTo);
            for (String tenBoMon : tenBoMonCNTT) {
                Department boMon = departmentRepository.findByTen(tenBoMon);
                DepartmentCampus boMonTheoCoSo = new DepartmentCampus();
                boMonTheoCoSo.setDeleteStatus(DeleteStatus.NOT_DELETED);
                boMonTheoCoSo.setCampus(coSo);
                boMonTheoCoSo.setDepartment(boMon);
                departmentCampusRepository.save(boMonTheoCoSo);
            }
        }

    }

    /**
     * Nhân viên
     *
     * @throws Exception
     */

    private void dataNhanVien() throws Exception {

        for (long i = 1L; i <= tenNhanVien.length; i++) {

            Staff nhanVien = new Staff();
            Optional<DepartmentCampus> boMonTheoCoSo = departmentCampusRepository.findById(i);
            if (boMonTheoCoSo.isPresent()) {
                nhanVien.setFullName(tenNhanVien[(int) i - 1]);
                nhanVien.setDeleteStatus(DeleteStatus.NOT_DELETED);
                nhanVien.setDepartmentCampus(boMonTheoCoSo.get());
                nhanVien.setAccountFE(tenGmailFe[(int) i - 1]);
                nhanVien.setAccountFPT(tenGmailFpt[(int) i - 1]);
                nhanVien.setStaffCode(maNhanVien[(int) i - 1]);
                staffRepository.save(nhanVien);
            }
        }
    }

    /**
     * thêm chủ nhiệm bộ môn
     *
     * @throws Exception
     */

    private void dataBoMonCoSoChuNhiem() throws Exception {
        for (Long i = 1L; i <= 210L; i++) {
            Optional<Staff> nhanVien = staffRepository.findById(i);
            Optional<DepartmentCampus> boMonTheoCoSo = departmentCampusRepository.findById(i);
            if (nhanVien.isPresent() && boMonTheoCoSo.isPresent()) {
                boMonTheoCoSo.get().setStaff(nhanVien.get());
                boMonTheoCoSo.get().setDeleteStatus(DeleteStatus.NOT_DELETED);
                departmentCampusRepository.save(boMonTheoCoSo.get());
            }
        }
    }

}