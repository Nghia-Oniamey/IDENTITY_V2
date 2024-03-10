import {toast} from "react-toastify";
import quanlynhanvienApi from "../../../service/staffservice/quanlynhanvien.api";
import React from "react";

export const useFiles = (ref: React.RefObject<HTMLInputElement>) => {
    const downloadTemplate = async () => {
        try {
            const response = await quanlynhanvienApi.downloadTemplate(2);
            const url = window.URL.createObjectURL(
                new Blob([response.data], {type: "application/vnd.ms-excel"}),
            );
            const link = document.createElement("a");
            link.href = url;
            link.setAttribute("download", "template.xlsx");
            document.body.appendChild(link);
            link.click();
        } catch (e) {
            toast.error("Không thể tải xuống template");
        }
    };

    const uploadFile = async (file: File) => {
        try {
            const response = await quanlynhanvienApi.uploadFile(file);
            toast.success(response.data);
            if (ref.current) {
                ref.current.value = "";
            }
        } catch (error: any) {
            toast.error(error.response.data);
        }
    };

    return {
        downloadTemplate,
        uploadFile,
    };
};
