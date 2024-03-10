package fplhn.udpm.identity.core.admin.managecampus.service.impl;

import fplhn.udpm.identity.core.admin.managecampus.model.request.CSCreateCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.request.CSFindCoSoRequest;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSCoSoResponse;
import fplhn.udpm.identity.core.admin.managecampus.model.response.CSListCoSoResponse;
import fplhn.udpm.identity.core.admin.managecampus.repository.CSCampusRepository;
import fplhn.udpm.identity.core.admin.managecampus.service.CSCoSoService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;
import fplhn.udpm.identity.entity.Campus;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CSCoSoServiceImpl implements CSCoSoService {

    @Autowired
    private CSCampusRepository csCoSoRepository;

    @Override
    public PageableObject<CSCoSoResponse> getAllCoSo(CSFindCoSoRequest request) {
        Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
        if (request.getArrayField() == null || request.getArrayField().length == 0) {
            Page<CSCoSoResponse> res = csCoSoRepository.search(pageable);
            return new PageableObject<>(res);
        }
        Page<CSCoSoResponse> res = csCoSoRepository.search(pageable, request);
        return new PageableObject<>(res);
    }

    @Override
    public ResponseModel addCoSo(CSCreateCoSoRequest request) {
        if (request.getTenCoSo().length() > 255) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Tên cơ sở vượt quá định dạng");
        }
        request.setTenCoSo(request.getTenCoSo().replaceAll("\\s+", " "));
        boolean tonTai = csCoSoRepository.existsByMa(request.getMaCoSo().trim());

        if (tonTai) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mã cơ sở đã tồn tại");
        } else {
            Campus campus = new Campus();
            campus.setDeleteStatus(DeleteStatus.NOT_DELETED);
            campus.setTen(request.getTenCoSo().trim());
            campus.setMa(request.getMaCoSo().trim());
            this.csCoSoRepository.save(campus);
            return new ResponseModel(HttpStatus.CREATED, "Thêm mới thành công");
        }
    }

    @Override
    public ResponseModel updateCoSo(CSCreateCoSoRequest request, Long id) {
        if (request.getTenCoSo().length() > 255) {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Tên cơ sở vượt quá định dạng");
        }
        request.setTenCoSo(request.getTenCoSo().replaceAll("\\s+", " "));
        boolean tonTai = csCoSoRepository.existsById(id);

        if (tonTai) {
            Optional<Campus> coSoOptional = csCoSoRepository.findById(id);
            if (!coSoOptional.get().getMa().trim().equalsIgnoreCase(request.getMaCoSo().trim())) {
                if (csCoSoRepository.existsByMa(request.getMaCoSo().trim())) {
                    return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mã cơ sở đã tồn tại: " +
                            request.getMaCoSo().trim());
                }
            }
            coSoOptional.get().setId(id);
            coSoOptional.get().setTen(request.getTenCoSo().trim());
            coSoOptional.get().setMa(request.getMaCoSo().trim());
            this.csCoSoRepository.save(coSoOptional.get());
            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Cơ sở không tồn tại");
        }
    }

    @Override
    public ResponseModel updateXoaMemCoSo(Long id) {
        boolean tonTai = csCoSoRepository.existsById(id);

        if (tonTai) {
            Optional<Campus> coSoOptional = csCoSoRepository.findById(id);

            if (coSoOptional.get().getDeleteStatus().equals(DeleteStatus.NOT_DELETED)) {
                coSoOptional.get().setDeleteStatus(DeleteStatus.DELETED);
            } else {
                coSoOptional.get().setDeleteStatus(DeleteStatus.NOT_DELETED);
            }

            this.csCoSoRepository.save(coSoOptional.get());

            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Cơ sở không tồn tại");
        }
    }

    @Override
    public List<CSListCoSoResponse> getListCoSo() {
        return this.csCoSoRepository.getListCoSo();
    }
}
