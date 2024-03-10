import React, {useEffect, useState} from "react";
import {Button, Form, Modal, Popconfirm, Select} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faFireBurner} from "@fortawesome/free-solid-svg-icons";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import boMonTheoCoSoService from "../../../service/departmentcampusservice/BoMonTheoCoSoService";
import {toast} from "react-toastify";

const ModalAddMonHocTheoCoSo = (props) => {

    const [form] = Form.useForm();

    const [dataCoSo, setDataCoSo] = useState();
    const [dataNhanVien, setDataNhanVien] = useState();

    const {
        isModalCreateMonHocTheoCoSoOpen,
        setIsModalCreateMonHocTheoCoSoOpen,
        idBoMon,
        tenBoMon,
    } = props;

    const {loading, setLoading} = useLoading();

    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const handleCloseCreateMonHocTheoCoSoOpen = () => {
        setIsModalCreateMonHocTheoCoSoOpen(false);
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const getListCoSo = (isModalCreateMonHocTheoCoSoOpen) => {
        if (isModalCreateMonHocTheoCoSoOpen === true) {
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
        }
    }

    const getListNhanVien = (isModalCreateMonHocTheoCoSoOpen) => {
        if (isModalCreateMonHocTheoCoSoOpen === true) {
            boMonTheoCoSoService
                .getListNhanVien()
                .then((response) => {
                    setDataNhanVien(
                        response.data ? response.data.map(nhanVien => ({
                            value: nhanVien.idNhanVien,
                            label: nhanVien.tenNhanVien + ' - ' + nhanVien.maNhanVien
                        })) : []
                    );
                    console.log("dataNhanVien", dataNhanVien);
                }).catch((error) => {
                console.error(error);
            })
        }
    }

    const handleFieldsChange = (changedFields, allFields) => {
        const allFieldsFilled = Object.keys(allFields).every(
            (field) => !!allFields[field].value,
        );
        setIsButtonDisabled(allFieldsFilled);
    };

    const handleSubmitCreateBoMonTheoCoSo = async () => {
        const formValue = await form.getFieldsValue();

        const request = {
            "idCoSo": formValue.idCoSo ? formValue.idCoSo : "",
            "idCNBM": formValue.idNhanVien ? formValue.idNhanVien : "",
            "idBoMon": idBoMon ? parseInt(idBoMon, 10) : ""
        }

        boMonTheoCoSoService
            .createBoMonTheoCoSo(request)
            .then((response) => {
                console.log(response);
                if (response.status === 200) {
                    if (response.data.status === "CREATED") {
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
                setIsModalCreateMonHocTheoCoSoOpen(false);
            }).catch((error) => {
            console.log(error);
            toast.error("Thêm thất bại")
        });
        form.resetFields();
        setIsButtonDisabled(false);
    }

    useEffect(() => {
        console.log("ádasfsdfsa");
        getListCoSo(isModalCreateMonHocTheoCoSoOpen);
        getListNhanVien(isModalCreateMonHocTheoCoSoOpen);
    }, [isModalCreateMonHocTheoCoSoOpen]);

    return (
        <>
            {loading && <GlobalLoading/>}
            <Modal
                title={<h5 style={{color: "#052C65"}}><FontAwesomeIcon icon={faFireBurner}/> Thêm cơ sở và chủ nhiệm cho
                    bộ môn: <span style={{color: "red"}}>{tenBoMon}</span></h5>}
                open={isModalCreateMonHocTheoCoSoOpen}
                onCancel={handleCloseCreateMonHocTheoCoSoOpen}
                width={"40vw"}
                footer={[
                    isButtonDisabled && (
                        <Popconfirm
                            placement="top"
                            title={"Thông báo"}
                            description={"Bạn có muốn thêm không ?"}
                            okText="Có"
                            cancelText="Không"
                            onConfirm={() => handleSubmitCreateBoMonTheoCoSo()}
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
                            size="large"
                            hasFeedback={true}
                            showSearch
                            style={{
                                width: "100%",
                            }}
                            placeholder="Tìm kiếm cơ sở"
                            optionFilterProp="children"
                            filterOption={(input, option) => (option?.label ?? '').includes(input)}
                            filterSort={(optionA, optionB) =>
                                (optionA?.label ?? '').toLowerCase().localeCompare((optionB?.label ?? '').toLowerCase())
                            }
                            options={dataCoSo ? dataCoSo : []}
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
                            size="large"
                            hasFeedback={true}
                            showSearch
                            style={{
                                width: "100%",
                            }}
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

export default ModalAddMonHocTheoCoSo;
