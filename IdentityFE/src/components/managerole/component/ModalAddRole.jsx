import {Button, Col, Form, Input, Modal, Popconfirm, Row, Tooltip,} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBuildingWheat, faPenToSquare,} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import roleService from "../../../service/roleservice/RoleService";

const ModalAddRole = (props) => {
    const [form] = Form.useForm();

    const {isModalAddRoleOpen, setIsModalAddRoleOpen} = props;

    const [loading, setLoading] = useState(false);

    const handleCloseAddRoleOpen = () => {
        setIsModalAddRoleOpen(false);
        form.resetFields();
    };

    const handleOkThemRole = async () => {
        const formValue = await form.getFieldsValue();

        roleService
            .createRole(formValue)
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
                handleCloseAddRoleOpen();
            })
            .catch((err) => {
                if (err.code && err.code === "ERR_BAD_REQUEST") {
                    toast.error(err.response.data.content.ten, {
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
                        Thêm vai trò
                    </h3>
                }
                open={isModalAddRoleOpen}
                onCancel={handleCloseAddRoleOpen}
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
                                                description="Bạn có muốn thêm vai trò mới không?"
                                                okText="Có"
                                                cancelText="Không"
                                                onConfirm={() => handleOkThemRole()}
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
                                                    Thêm vai trò
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

export default ModalAddRole;
