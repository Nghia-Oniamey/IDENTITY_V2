import instance from "../base/request";

class ModuleService {
    getModuleByListId(params) {
        return instance({
            method: "GET",
            url: `module/get-all-module`,
            params: params,
        });
    }

    getListModule() {
        return instance({
            method: "GET",
            url: "module/get-list-module"
        });
    }

    createModule(data) {
        return instance({
            method: "POST",
            url: `/module/add-module`,
            data: data,
        });
    }

    updateModule(data, id) {
        return instance({
            method: "PUT",
            url: `/module/update-module/${id}`,
            data: data,
        });
    }

    deleteModule(id) {
        return instance({
            method: "PUT",
            url: `/module/update-xoa-mem-module/${id}`,
        });
    }
}

const moduleService = new ModuleService();

export default moduleService;