package fplhn.udpm.identity.infrastructure.config.excelconfig.commonio;


import fplhn.udpm.identity.entity.DepartmentCampus;
import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.config.excelconfig.model.dto.TransferRequestExcel;
import fplhn.udpm.identity.infrastructure.config.excelconfig.model.request.ImportExcelRequest;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.CourseCampusStaffRepository;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.MainCampusJobConfigRepository;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.StaffConfigJobRepository;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class NhanVienProcessor implements ItemProcessor<ImportExcelRequest, TransferRequestExcel> {

    private StaffConfigJobRepository staffConfigJobRepository;

    private CourseCampusStaffRepository courseCampusStaffRepository;

    private MainCampusJobConfigRepository mainCampusJobConfigRepository;

    @Autowired
    public void setMainCampusJobConfigRepository(MainCampusJobConfigRepository mainCampusJobConfigRepository) {
        this.mainCampusJobConfigRepository = mainCampusJobConfigRepository;
    }

    @Autowired
    public void setCourseCampusStaffRepository(CourseCampusStaffRepository courseCampusStaffRepository) {
        this.courseCampusStaffRepository = courseCampusStaffRepository;
    }

    @Autowired
    public void setStaffConfigJobRepository(StaffConfigJobRepository staffConfigJobRepository) {
        this.staffConfigJobRepository = staffConfigJobRepository;
    }

    @Override
    public TransferRequestExcel process(ImportExcelRequest item) {
        String tenBoMonCoSo = item.getTenBoMonTheoCoSo();
        String tenBoMon = tenBoMonCoSo.split("-")[0];
        String tenCoSo = tenBoMonCoSo.split("-")[1];

        Optional<DepartmentCampus> boMonTheoCoSo = courseCampusStaffRepository.findByBoMonCoSo(tenBoMon, tenCoSo);
        if (boMonTheoCoSo.isEmpty()) return null;

        String maNhanVien = validateAndGetMaNhanVien(item);
        if (maNhanVien == null) return null;
        Staff nhanVien = getNhanVien(item, boMonTheoCoSo.get(), maNhanVien);

        return new TransferRequestExcel(nhanVien);
    }

    private String validateAndGetMaNhanVien(ImportExcelRequest item) {
        if (item.getIdGiaoVien() == null || item.getIdGiaoVien().isEmpty()) return null;
        if (!item.getEmailFe().contains(item.getIdGiaoVien()) || !item.getEmailFpt().contains(item.getIdGiaoVien()))
            return null;
        return item.getIdGiaoVien();
    }

    private Staff getNhanVien(ImportExcelRequest item, DepartmentCampus departmentCampus, String maNhanVien) {
        Optional<Staff> existingNhanVien = staffConfigJobRepository.findNhanVienByStaffCode(maNhanVien);
        Staff nhanVien;
        if (existingNhanVien.isPresent()) {
            nhanVien = existingNhanVien.get();
        } else {
            nhanVien = new Staff();
            nhanVien.setStaffCode(maNhanVien);
        }
        nhanVien.setFullName(item.getHoTen());
        nhanVien.setAccountFE(item.getEmailFe());
        nhanVien.setAccountFPT(item.getEmailFpt());
//        nhanVien.setPhoneNumber(item.getPhoneNumber());
        nhanVien.setDepartmentCampus(departmentCampus);
        nhanVien.setDeleteStatus(DeleteStatus.NOT_DELETED);
        return nhanVien;
    }

}
