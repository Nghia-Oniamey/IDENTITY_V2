import React, {useEffect, useState} from "react";
import {Button, Form, message, Modal, Popconfirm, Select} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBridgeWater} from "@fortawesome/free-solid-svg-icons";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import boMonTheoCoSoService from "../../../service/departmentcampusservice/BoMonTheoCoSoService";
import {toast} from "react-toastify";

const ModalUpdateMonHocTheoCoSo = (props) => {

    const [form] = Form.useForm();

    const [dataCoSo, setDataCoSo] = useState();
    const [dataNhanVien, setDataNhanVien] = useState();

    const {
        isModalUpdateMonHocTheoCoSoOpen,
        setIsModalUpdateMonHocTheoCoSoOpen,
        idBoMon,
        tenBoMon,
        dataUpdate
    } = props;

    const {loading, setLoading} = useLoading();

    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const handleCloseUpdateMonHocTheoCoSoOpen = () => {
        setIsModalUpdateMonHocTheoCoSoOpen(false);
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const getListCoSo = (isModalUpdateMonHocTheoCoSoOpen) => {
        if (isModalUpdateMonHocTheoCoSoOpen === true) {
            boMonTheoCoSoService
                .getListCoSo()
                .then((response) => {
                    setDataCoSo(
                        response.data ? response.data.map(coSo => ({
                            value: coSo.idCoSo,
                            label: coSo.tenCoSo
                        })) : []
                    );
                    console.log(dataCoSo);
                }).catch((error) => {
                console.error(error);
            })
            console.log("dataUpdate", dataUpdate);
            form.setFieldsValue({'idCoSo': dataUpdate.idCoSo});
        }
    }

    const getListNhanVien = (isModalUpdateMonHocTheoCoSoOpen) => {
        if (isModalUpdateMonHocTheoCoSoOpen === true) {
            boMonTheoCoSoService
                .getListNhanVien()
                .then((response) => {
                    setDataNhanVien(
                        response.data ? response.data.map(nhanVien => ({
                            value: nhanVien.idNhanVien,
                            label: nhanVien.tenNhanVien
                        })) : []
                    );
                    console.log(dataNhanVien);
                }).catch((error) => {
                console.error(error);
            })
            form.setFieldsValue({'idNhanVien': dataUpdate.idCNBM});
        }
    }

    const handleSubmitUpdateBoMonTheoCoSo = async () => {
        const formValue = await form.getFieldsValue();

        const request = {
            "idCoSo": formValue.idCoSo ? formValue.idCoSo : "",
            "idCNBM": formValue.idNhanVien ? formValue.idNhanVien : "",
            "idBoMon": idBoMon ? parseInt(idBoMon, 10) : ""
        }

        boMonTheoCoSoService
            .updateBoMonTheoCoSo(request, dataUpdate.idBoMonTheoCoSo)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    if (response.data.status === "OK") {
                        toast.success(response.data.message);
                    } else if (response.data.status === "NOT_ACCEPTABLE") {
                        toast.warning(response.data.message, {
                            position: "top-right",
                            autoClose: 5000,
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            progress: undefined,
                            theme: "light",
                        });
                    } else {
                        toast.error(response.data.message, {
                            position: "top-right",
                            autoClose: 5000,
                            hideProgressBar: false,
                            closeOnClick: true,
                            pauseOnHover: true,
                            draggable: true,
                            progress: undefined,
                            theme: "light",
                        });
                    }
                }
                setIsModalUpdateMonHocTheoCoSoOpen(false);
            }).catch((error) => {
            console.log(error);
            message.info("Cập nhật thất bại")

        });
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const handleFieldsChange = (changedFields, allFields) => {
        const allFieldsFilled = Object.keys(allFields).every(
            (field) => !!allFields[field].value,
        );
        setIsButtonDisabled(allFieldsFilled);
    };

    useEffect(() => {
        getListCoSo(isModalUpdateMonHocTheoCoSoOpen);
        getListNhanVien(isModalUpdateMonHocTheoCoSoOpen);
    }, [isModalUpdateMonHocTheoCoSoOpen]);

    return (
        <>
            {loading && <GlobalLoading/>}
            <Modal
                title={<h5 style={{color: "#052C65"}}><FontAwesomeIcon icon={faBridgeWater}/> Cập nhật cơ sở và chủ
                    nhiệm cho bộ môn: <span style={{color: "red"}}>{tenBoMon}</span></h5>}
                open={isModalUpdateMonHocTheoCoSoOpen}
                onCancel={handleCloseUpdateMonHocTheoCoSoOpen}
                width={"40vw"}
                footer={[
                    isButtonDisabled && (
                        <Popconfirm
                            placement="top"
                            title={"Thông báo"}
                            description={"Bạn có muốn cập nhật không ?"}
                            okText="Có"
                            cancelText="Không"
                            onConfirm={() => handleSubmitUpdateBoMonTheoCoSo()}
                        >
                            <Button
                                type="primary"
                                style={{backgroundColor: "success", borderColor: "success"}}
                            >
                                Xác nhận
                            </Button>
                        </Popconfirm>
                    ),
                ]}
            >
                <Form
                    className="mt-4"
                    form={form}
                    onFieldsChange={(changedFields, allFields) =>
                        handleFieldsChange(changedFields, allFields)
                    }
                >
                    <Form.Item
                        tooltip="Không cập nhật trường này"
                        name="idCoSo"
                        label="Chọn Cơ sở"
                        wrapperCol={{span: 24}}
                        labelCol={{span: 24}}
                        validateTrigger={"onBlur"}
                        rules={[
                            {
                                required: true,
                                message: "Tên cơ sở không được để trống!",
                            },
                        ]}
                    >
                        <Select
                            showSearch
                            style={{
                                width: "100%",
                                backgroundColor: "#fff",

                            }}
                            size="large"
                            placeholder="Tìm kiếm cơ sở"
                            optionFilterProp="children"
                            filterOption={(input, option) => (option?.label ?? '').includes(input)}
                            filterSort={(optionA, optionB) =>
                                (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                            }
                            options={dataCoSo ? dataCoSo : []}
                            disabled
                        />
                    </Form.Item>
                    <Form.Item
                        name="idNhanVien"
                        label="Chọn chủ nhiệm bộ môn"
                        wrapperCol={{span: 24}}
                        labelCol={{span: 24}}
                        validateTrigger={"onBlur"}
                        rules={[
                            {
                                required: true,
                                message: "Tên chủ nhiệm bộ môn không được để trống!",
                            },
                        ]}
                    >
                        <Select
                            showSearch
                            style={{
                                width: "100%",
                            }}
                            size="large"
                            placeholder="Tìm kiếm chủ nhiệm bộ môm"
                            optionFilterProp="children"
                            filterOption={(input, option) => (option?.label ?? '').includes(input)}
                            filterSort={(optionA, optionB) =>
                                (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                            }
                            options={dataNhanVien ? dataNhanVien : []}
                        />
                    </Form.Item>
                </Form>
            </Modal>
        </>
    );
};

export default ModalUpdateMonHocTheoCoSo;
