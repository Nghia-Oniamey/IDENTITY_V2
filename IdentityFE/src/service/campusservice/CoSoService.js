import instance from "../base/request";

class CoSoService {

    getAllCoSo(params) {
        return instance({
            method: "GET",
            url: `/coso/get-all-co-so`,
            params: params,
        });
    }

    createCoSo(data) {
        return instance({
            method: "POST",
            url: `/coso/add-co-so`,
            data: data,
        });
    }

    updateCoSo(data, id) {
        return instance({
            method: "PUT",
            url: `/coso/update-co-so/${id}`,
            data: data,
        });
    }

    deleteCoSo(id) {
        return instance({
            method: "PUT",
            url: `/coso/update-xoa-mem-co-so/${id}`,
        });
    }

    detailCoSo(params, id) {
        return instance({
            method: "GET",
            url: `/coso/get-all-co-so-con/${id}`,
            params: params
        });
    }

    createCoSoCon(data) {
        return instance({
            method: "POST",
            url: `/coso/add-co-so-con`,
            data: data,
        });
    }

    updateCoSoCon(data, id) {
        return instance({
            method: "PUT",
            url: `/coso/update-co-so-con/${id}`,
            data: data,
        });
    }

    deleteCoSoCon(id) {
        return instance({
            method: "PUT",
            url: `/coso/update-xoa-mem-co-so-con/${id}`,
        });
    }

    getListCoSo() {
        return instance({
            method: "GET",
            url: `/coso/get-list-co-so`,
        });
    }

    getListCoSoCon(id) {
        return instance({
            method: "GET",
            url: `/coso/get-list-co-so-con/${id}`,
        });
    }
}

const coSoService = new CoSoService();

export default coSoService;