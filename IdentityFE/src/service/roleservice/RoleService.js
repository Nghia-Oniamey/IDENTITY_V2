import instance from "../base/request";

class RoleService {
    getAllRole(params) {
        return instance({
            method: "GET",
            url: `/role`,
            params: params,
        });
    }

    createRole(data) {
        return instance({
            method: "POST",
            url: `/role`,
            data: data,
        });
    }

    updateRole(data, id) {
        return instance({
            method: "PUT",
            url: `/role/${id}`,
            data: data,
        });
    }

    deleteRole(id) {
        return instance({
            method: "DELETE",
            url: `/role/${id}`,
        });
    }

    detailRole(params, id) {
        return instance({
            method: "GET",
            url: `/role/${id}`,
            params: params,
        });
    }
}

const roleService = new RoleService();

export default roleService;
