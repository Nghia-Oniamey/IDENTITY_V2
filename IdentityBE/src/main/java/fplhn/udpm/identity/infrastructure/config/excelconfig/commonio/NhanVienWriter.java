package fplhn.udpm.identity.infrastructure.config.excelconfig.commonio;

import fplhn.udpm.identity.entity.Staff;
import fplhn.udpm.identity.infrastructure.config.excelconfig.model.dto.TransferRequestExcel;
import fplhn.udpm.identity.infrastructure.config.excelconfig.repository.StaffConfigJobRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class NhanVienWriter implements ItemWriter<TransferRequestExcel> {

    private StaffConfigJobRepository staffConfigJobRepository;

    @Autowired
    public void setStaffConfigJobRepository(@Qualifier("excelNhanVienRepository") StaffConfigJobRepository staffConfigJobRepository) {
        this.staffConfigJobRepository = staffConfigJobRepository;
    }

    @Override
    public void write(Chunk<? extends TransferRequestExcel> chunk) {
        for (TransferRequestExcel transferRequestExcel : chunk) {
            try {
                Staff nhanVien = transferRequestExcel.getNhanVien();
                Optional<Staff> nhanVienOptional = staffConfigJobRepository.findNhanVienByStaffCode(nhanVien.getStaffCode());
                if (nhanVienOptional.isPresent()) {
                    nhanVien = nhanVienOptional.get();
                }
                Staff savedNhanVien = staffConfigJobRepository.save(nhanVien);
            } catch (Exception e) {
                log.error("Error processing record: " + transferRequestExcel, e);
            }
        }
    }

}
