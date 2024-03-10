import instance from "../base/request";

class BoMonTheoCoSoService {

    getAllBoMonTheoCoSo(id, params) {
        return instance({
            method: "GET",
            url: `/bomon/get-all-bo-mon-theo-co-so/${id}`,
            params: params,
        });
    }

    createBoMonTheoCoSo(data) {
        return instance({
            method: "POST",
            url: `/bomon/add-bo-mon-theo-co-so`,
            data: data,
        });
    }

    updateBoMonTheoCoSo(data, id) {
        return instance({
            method: "PUT",
            url: `/bomon/update-bo-mon-theo-co-so/${id}`,
            data: data,
        });
    }

    deleteBoMonTheoCoSo(id) {
        return instance({
            method: "PUT",
            url: `/bomon/delete-bo-mon-theo-co-so/${id}`,
        });
    }

    getListCoSo() {
        return instance({
            method: "GET",
            url: `/bomon/get-list-co-so`,
        });
    }

    getListNhanVien() {
        return instance({
            method: "GET",
            url: `/bomon/get-list-chu-nhiem-bo-mon`,
        });
    }

    getTenBoMon(id) {
        return instance({
            method: "GET",
            url: `/bomon/get-ten-bo-mon/${id}`,
        });
    }

    getListBoMonTheoCoSo(id) {
        return instance({
            method: "GET",
            url: `/bomon/get-list-bo-mon-theo-co-so/${id}`,
        });
    }

}

const boMonTheoCoSoService = new BoMonTheoCoSoService();

export default boMonTheoCoSoService;