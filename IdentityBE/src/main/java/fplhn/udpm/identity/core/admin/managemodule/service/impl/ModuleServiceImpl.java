package fplhn.udpm.identity.core.admin.managemodule.service.impl;

import fplhn.udpm.identity.core.admin.managemodule.model.request.CreateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.FindModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.request.UpdateModuleRequest;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ListModuleResponse;
import fplhn.udpm.identity.core.admin.managemodule.model.response.ModuleResponse;
import fplhn.udpm.identity.core.admin.managemodule.repository.CSModuleRepository;
import fplhn.udpm.identity.core.admin.managemodule.repository.ClientEntryRepository;
import fplhn.udpm.identity.core.admin.managemodule.service.ModuleService;
import fplhn.udpm.identity.core.common.config.PageableObject;
import fplhn.udpm.identity.core.common.config.ResponseModel;
import fplhn.udpm.identity.core.common.config.ResponseObject;
import fplhn.udpm.identity.entity.Client;
import fplhn.udpm.identity.entity.Module;
import fplhn.udpm.identity.infrastructure.constant.DeleteStatus;
import fplhn.udpm.identity.util.GenerateClientUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    @Value("${client.secret.length}")
    private int CLIENT_SECRET_LENGTH;

    private final CSModuleRepository moduleRepository;

    private final ClientEntryRepository clientEntryRepository;


    @Override
    public PageableObject<ModuleResponse> getModuleByListId(FindModuleRequest request) {
        try {
            Pageable pageable = PageRequest.of(request.getPage() - 1, request.getSize());
            if (request.getListId() == null || request.getListId().length == 0) {
                Page<ModuleResponse> res = moduleRepository.searchAllModule(pageable);
                return new PageableObject<>(res);
            }
            Page<ModuleResponse> res = moduleRepository.searchModuleByListId(pageable, request);
            return new PageableObject<>(res);
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return null;
        }
    }

    @Override
    public ResponseObject addModule(CreateModuleRequest request) {
        try {
            request.setTenModule(request.getTenModule().replaceAll("\\s+", " "));
            boolean isDuplicateModule = moduleRepository.existsByMa(request.getMaModule().trim());

            if (isDuplicateModule) {
                return new ResponseObject(null, HttpStatus.NOT_ACCEPTABLE, "Mã mô-đun đã tồn tại");
            } else {
                if (moduleRepository.existsByUrl(request.getUrlModule().trim())) {
                    return new ResponseObject(null, HttpStatus.NOT_ACCEPTABLE, "Địa chỉ mô-đun đã tồn tại");
                } else {
                    Module module = new Module();
                    module.setDeleteStatus(DeleteStatus.NOT_DELETED);
                    module.setMa(request.getMaModule());
                    module.setTen(request.getTenModule().trim());
                    module.setUrl(request.getUrlModule().trim());
                    Module moduleSave = moduleRepository.save(module);
                    String clientId = GenerateClientUtils.generateRandomClientID();
                    String clientSecret = GenerateClientUtils.generateRandomClientSecret(CLIENT_SECRET_LENGTH);
                    Client client = new Client();
                    client.setClientId(clientId);
                    client.setClientSecret(clientSecret);
                    client.setModule(moduleSave);
                    client.setDeleteStatus(DeleteStatus.NOT_DELETED);
                    Client clientSave = clientEntryRepository.save(client);
                    return new ResponseObject(clientSave, HttpStatus.OK, "Thêm mô-đun thành công");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(System.out);
            return new ResponseObject(null, HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi hệ thống");
        }
    }

    @Override
    public ResponseModel updateModule(UpdateModuleRequest updateModuleRequest, Long id) {
        updateModuleRequest.setTenModule(updateModuleRequest.getTenModule().replaceAll("\\s+", " "));
        boolean checkTrung = moduleRepository.existsById(id);

        if (checkTrung) {
            Module module = moduleRepository.findById(id).get();
            if (!module.getMa().trim().equalsIgnoreCase(updateModuleRequest.getMaModule().trim())) {
                if (moduleRepository.existsByMa(updateModuleRequest.getMaModule().trim())) {
                    return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mã mô-đun đã tồn tại: " +
                            updateModuleRequest.getTenModule().trim());
                }
            }
            if (!module.getUrl().trim().equalsIgnoreCase(updateModuleRequest.getUrlModule().trim())) {
                if (moduleRepository.existsByUrlAndId(updateModuleRequest.getUrlModule().trim(), id)) {
                    return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Địa chỉ mô-đun đã tồn tại: " +
                            updateModuleRequest.getUrlModule().trim());
                }
            }
            module.setId(id);
            module.setTen(updateModuleRequest.getTenModule());
            module.setMa(updateModuleRequest.getMaModule());
            module.setUrl(updateModuleRequest.getUrlModule());
            moduleRepository.save(module);
            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mô-đun không tồn tại");
        }
    }

    @Override
    public ResponseModel updateXoaMemModule(Long id) {
        boolean checkTrung = moduleRepository.existsById(id);

        if (checkTrung) {
            Module module = moduleRepository.findById(id).get();
            System.out.println(module);
            if (module.getDeleteStatus().equals(DeleteStatus.NOT_DELETED)) {
                module.setDeleteStatus(DeleteStatus.DELETED);
            } else {
                module.setDeleteStatus(DeleteStatus.NOT_DELETED);
            }

            this.moduleRepository.save(module);

            return new ResponseModel(HttpStatus.OK, "Cập nhật thành công");
        } else {
            return new ResponseModel(HttpStatus.NOT_ACCEPTABLE, "Mô-đun không tồn tại");
        }
    }

    @Override
    public List<ListModuleResponse> getListModule() {
        try {
            return moduleRepository.getAllModule();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
