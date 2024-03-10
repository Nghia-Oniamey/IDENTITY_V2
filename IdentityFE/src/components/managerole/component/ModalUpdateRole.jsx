import {faBuildingWheat, faPenToSquare,} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {Button, Col, Form, Input, Modal, Popconfirm, Row, Tooltip,} from "antd";
import {useEffect, useState} from "react";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import roleService from "../../../service/roleservice/RoleService";
import GlobalLoading from "../../global-loading/GlobalLoading";

const ModalUpdateRole = (props) => {
    const {
        isModalUpdateRoleOpen,
        setIsModalUpdateRoleOpen,
        dataUpdate,
        setDataUpdate,
    } = props;

    const [form] = Form.useForm();

    const [loading, setLoading] = useState(false);

    const handleCloseUpdateRoleOpen = () => {
        setIsModalUpdateRoleOpen(false);
        form.resetFields();
    };

    useEffect(() => {
        if (dataUpdate) {
            form.setFieldsValue({
                ma: dataUpdate.ma,
                ten: dataUpdate.ten,
                idCoSo: Number(dataUpdate.idCoSo),
            });
        }
    }, [dataUpdate]);

    const handleOkSuaRole = async () => {
        const formValue = await form.getFieldsValue();

        roleService
            .updateRole(formValue, dataUpdate.id)
            .then((res) => {
                console.log(res);
                if (res.data.content.status === "OK") {
                    toast.success(res.data.content.message, {
                        position: "top-right",
                        autoClose: 5000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "light",
                    });
                } else if (res.data.content.status === "BAD_REQUEST") {
                    toast.warning(res.data.content.message, {
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
                    toast.error(res.data.content.message, {
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
                handleCloseUpdateRoleOpen();
            })
            .catch((error) => {
                console.log(error);
                toast.error("cập nhật thất bại");
            });
    };

    return (
        <>
            {loading && <GlobalLoading/>}
            <Modal
                title={
                    <h3
                        className="d-flex gap-2 align-items-center"
                        style={{
                            color: "#052C65",
                        }}
                    >
                        <FontAwesomeIcon icon={faBuildingWheat}/>
                        Chỉnh sửa vai trò
                    </h3>
                }
                open={isModalUpdateRoleOpen}
                onCancel={handleCloseUpdateRoleOpen}
                width={"50vw"}
                footer={<></>}
            >
                <Container fluid className={"shadow p-4 rounded-3 mt-4"}>
                    <Row gutter={16}>
                        <Col flex={6}>
                            <Form form={form} layout="vertical" initialValues={{}}>
                                <Form.Item name="ma" label={"Mã: "} required={true}>
                                    <Input size="large" placeholder="Nhập mã vai trò"/>
                                </Form.Item>
                                <Form.Item name="ten" label={"Tên: "} required={true}>
                                    <Input size="large" placeholder="Nhập tên vai trò"/>
                                </Form.Item>
                                <Form.Item>
                                    <Col>
                                        <Tooltip title="" color={"#052C65"}>
                                            <Popconfirm
                                                placement="top"
                                                title="Thông báo"
                                                description="Bạn có muốn chỉnh sửa vai trò không?"
                                                okText="Có"
                                                cancelText="Không"
                                                onConfirm={() => handleOkSuaRole()}
                                            >
                                                <Button
                                                    icon={
                                                        <FontAwesomeIcon icon={faPenToSquare} size="lg"/>
                                                    }
                                                    size="large"
                                                    type="primary"
                                                    style={{
                                                        backgroundColor: "#052C65",
                                                        width: "100%",
                                                    }}
                                                >
                                                    Xác nhận
                                                </Button>
                                            </Popconfirm>
                                        </Tooltip>
                                    </Col>
                                </Form.Item>
                            </Form>
                        </Col>
                    </Row>
                </Container>
            </Modal>
        </>
    );
};

export default ModalUpdateRole;
