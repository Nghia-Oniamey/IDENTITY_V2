import {Col, Form, Input, Modal, Row} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useEffect, useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBuildingWheat} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import moduleService from "../../../service/moduleservice/ModuleService";

const ModalUpdateModule = (props) => {

    const [form] = Form.useForm();
    const {
        isModalUpdateModuleOpen,
        setIsModalUpdateModuleOpen,
        dataUpdate
    } = props;

    const [initialValues, setInitialValues] = useState({
        idModule: "",
        maModule: "",
        tenModule: "",
        urlModule: "",
        xoaMemModule: ""
    });

    // load lai form khi initValue thay doi du lieu
    useEffect(() => {
        if (dataUpdate != undefined) {
            setInitialValues({
                idModule: props.dataUpdate.idModule,
                maModule: props.dataUpdate.maModule,
                tenModule: props.dataUpdate.tenModule,
                urlModule: props.dataUpdate.urlModule,
                xoaMemModule: props.dataUpdate.xoaMemModule
            });
        }
        form.resetFields();
    }, [props]);


    const [loading, setLoading] = useState(false);

    const handleCloseUpdateModuleOpen = () => {
        setIsModalUpdateModuleOpen(false);
        form.resetFields();
    }

    const handleOkUpdateModule = async () => {
        try {
            // Kiểm tra hợp lệ của form
            await form.validateFields();

            const formValue = await form.getFieldsValue();
            console.log(formValue);
            console.log(props.dataUpdate.idModule);
            moduleService.updateModule(formValue, props.dataUpdate.idModule).then((res) => {
                if (res.data.status === "OK") {
                    console.log(res.data);
                    toast.success(res.data.message, {
                        position: "top-right",
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "light",
                    });
                } else if (res.data.status === "NOT_ACCEPTABLE") {
                    toast.warning(res.data.message, {
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
                    toast.error(res.data.message, {
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
                handleCloseUpdateModuleOpen();
                cancelComfirm();
            }).catch((err) => {
                console.log(err);
                console.log(err.code);
                if (err.code && err.code === "ERR_BAD_REQUEST") {
                    toast.error(err.message, {
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
            });
        } catch (error) {
            console.error("Lỗi khi kiểm tra form:", error);
            cancelComfirm();
        }
    }
    //modal comfirm khi update
    const [isOpenComfirm, setIsOpenComfirm] = useState(false);
    const showComfirm = () => {
        setIsOpenComfirm(true);
    }
    const xacNhanComfirm = async () => {
        handleOkUpdateModule();
    }
    const cancelComfirm = () => {
        setIsOpenComfirm(false);
    }
    return (
        <>
            {loading && <GlobalLoading/>}
            <Modal
                title={
                    <h3
                        className="d-flex gap-2 align-items-center"
                        style={{
                            color: "#052C65"
                        }}
                    >
                        <FontAwesomeIcon icon={faBuildingWheat}/>Chỉnh sửa thông tin mô-đun
                    </h3>
                }
                open={isModalUpdateModuleOpen}
                onOk={showComfirm}
                onCancel={handleCloseUpdateModuleOpen}
                width={"50vw"}
                okText="Hoàn tất"
                cancelText="Quay lại"
                // footer={
                //     <>
                //     </>
                // }
            >
                <Container fluid className={"shadow p-4 rounded-3 mt-4"}>
                    <Row gutter={16}>
                        <Col flex={6}>
                            <Form
                                form={form}
                                initialValues={initialValues}
                            >
                                <div style={{marginBottom: '16px'}}>
                                    <label htmlFor="urlModule" style={{display: 'block', marginBottom: '8px'}}>Tên
                                        mô-đun</label>
                                    <Form.Item
                                        name='tenModule'
                                        rules={[
                                            {
                                                validator: (_, value) => {
                                                    const trimmedValue = value ? value.trim() : "";
                                                    if (!trimmedValue) {
                                                        return Promise.reject(new Error("Tên mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                                    } else if (trimmedValue.length >= 255) {
                                                        return Promise.reject(new Error("Tên mô-đun không được quá 255 ký tự"));
                                                    } else {
                                                        return Promise.resolve();
                                                    }
                                                },
                                            },
                                        ]}
                                    >
                                        <Input
                                            value='tenModule'
                                            name='tenModule'
                                            size="large"
                                            placeholder="Nhập tên mô-đun"
                                        />
                                    </Form.Item>
                                </div>
                                <div style={{marginBottom: '16px'}}>
                                    <label htmlFor="maModule" style={{display: 'block', marginBottom: '8px'}}>Mã
                                        mô-đun</label>
                                    <Form.Item
                                        name='maModule'
                                        rules={[
                                            {
                                                validator: (_, value) => {
                                                    const trimmedValue = value ? value.trim() : "";
                                                    if (!trimmedValue) {
                                                        return Promise.reject(new Error("Mã mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                                    } else if (trimmedValue.length >= 255) {
                                                        return Promise.reject(new Error("Mã mô-đun không được quá 255 ký tự"));
                                                    } else {
                                                        return Promise.resolve();
                                                    }
                                                },
                                            },
                                        ]}
                                    >
                                        <Input
                                            id="maModule"
                                            name='maModule'
                                            size="large"
                                            placeholder="Nhập mã mô-đun"
                                        />
                                    </Form.Item>
                                </div>

                                <div style={{marginBottom: '16px'}}>
                                    <label htmlFor="urlModule" style={{display: 'block', marginBottom: '8px'}}>Địa chỉ
                                        mô-đun</label>
                                    <Form.Item
                                        name='urlModule'
                                        rules={[
                                            {
                                                validator: (_, value) => {
                                                    const trimmedValue = value ? value.trim() : "";
                                                    if (!trimmedValue) {
                                                        return Promise.reject(new Error("Địa chỉ mô-đun không được để trống hoặc chỉ chứa khoảng trắng"));
                                                    } else if (trimmedValue.length >= 255) {
                                                        return Promise.reject(new Error("Địa chỉ mô-đun không được quá 255 ký tự"));
                                                    } else {
                                                        return Promise.resolve();
                                                    }
                                                },
                                            },
                                        ]}
                                    >
                                        <Input
                                            id="urlModule"
                                            name='urlModule'
                                            size="large"
                                            placeholder="Nhập địa chỉ mô-đun"
                                        />
                                    </Form.Item>
                                </div>
                                {/* <div style={{ marginBottom: '16px' }}>
                                    <label htmlFor="statusModule" style={{ display: 'block', marginBottom: '8px' }}>Trạng thái mô-đun</label>
                                    
                                </div> */}

                            </Form>
                        </Col>
                    </Row>
                </Container>
            </Modal>
            {/* modal comfirm khi update */}
            <Modal
                title="Xác nhận sửa thông tin mô-đun"
                onOk={xacNhanComfirm}
                onCancel={cancelComfirm}
                open={isOpenComfirm}
                okText="Xác nhận"
                cancelText="Hủy"
                className="mt-5"
                okButtonProps={{style: {backgroundColor: 'green', color: 'white'}}}
            >
                <p>Bạn có chắc muốn sửa thông tin mô-đun hay không ?</p>
            </Modal>
        </>
    );
};

export default ModalUpdateModule;