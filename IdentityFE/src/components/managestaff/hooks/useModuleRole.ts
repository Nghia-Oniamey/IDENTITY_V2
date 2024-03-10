import {useEffect, useState} from "react";
import {Form} from "antd";
import quanlynhanvienApi from "../../../service/staffservice/quanlynhanvien.api";
import {Module, Role} from "../../../type/index.t";

export const useModuleRole = (idStaff: number | undefined) => {
    const [form] = Form.useForm();

    const [roleData, setRoleData] = useState<Role[]>([]);

    const [moduleData, setModuleData] = useState<Module[]>([]);

    useEffect(() => {
        const fetchListRole = async () => {
            try {
                const response = await quanlynhanvienApi.fetchListRole();
                setRoleData(response.data.data);
            } catch (error) {
                console.log(error);
            }
        };

        const fetchListModule = async () => {
            try {
                const response = await quanlynhanvienApi.fetchListModule();
                setModuleData(response.data.data);
            } catch (error) {
                console.log(error);
            }
        };

        fetchListRole();
        fetchListModule();

    }, []);

    useEffect(() => {
        const fetchListModuleRole = async () => {
            try {
                const response = await quanlynhanvienApi.fetchListRoleAndListModuleByIdStaff(idStaff);
                console.log('module-role', response.data);
                if (response.data?.data) {
                    form.setFieldsValue({
                        module: response.data.data.listModule.map((item: Module) => item.id),
                        role: response.data.data.listRole.map((item: Role) => item.id),
                    });
                }
            } catch (error) {
                console.log(error);
            }
        };

        if (idStaff) {
            fetchListModuleRole();
        }

    }, [idStaff]);

    return {
        roleData,
        moduleData,
        form,
    };

};