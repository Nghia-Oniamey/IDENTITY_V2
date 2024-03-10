import instance from "../base/request";

class BoMonChuyenNganhService {

    getAllBoMon(params) {
        return instance({
            method: "GET",
            url: `/bomon/get-all-bo-mon`,
            params: params,
        });
    }

    createBoMon(data) {
        return instance({
            method: "POST",
            url: `/bomon/add-bo-mon`,
            data: data,
        });
    }

    updateBoMon(data, id) {
        return instance({
            method: "PUT",
            url: `/bomon/update-bo-mon/${id}`,
            data: data,
        });
    }

    deleteBoMon(id) {
        return instance({
            method: "PUT",
            url: `/bomon/delete-bo-mon/${id}`,
        });
    }

    detailBoMon(params, id) {
        return instance({
            method: "GET",
            url: `/chuyennganh/get-all-chuyen-nganh/${id}`,
            params: params
        });
    }

    createChuyenNganh(data) {
        return instance({
            method: "POST",
            url: `/chuyennganh/add-chuyen-nganh`,
            data: data,
        });
    }

    updateChuyenNganh(data, id) {
        return instance({
            method: "PUT",
            url: `/chuyennganh/update-chuyen-nganh/${id}`,
            data: data,
        });
    }

    deleteChuyenNganh(id) {
        return instance({
            method: "PUT",
            url: `/chuyennganh/delete-chuyen-nganh/${id}`,
        });
    }

    getListBoMon() {
        return instance({
            method: "GET",
            url: `/bomon/get-list-bo-mon`,
        });
    }

    getListChuyenNganh(id) {
        return instance({
            method: "GET",
            url: `/chuyennganh/get-list-chuyen-nganh/${id}`,
        });
    }

}

const boMonChuyenNganhService = new BoMonChuyenNganhService();

export default boMonChuyenNganhService;