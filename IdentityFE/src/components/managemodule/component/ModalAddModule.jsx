import {Form, Modal} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBuildingWheat} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import InputCustom from "./InputCustom";
import moduleService from "../../../service/moduleservice/ModuleService";

const ModalAddModule = (props) => {

    const [form] = Form.useForm();

    const {
        isModalAddModuleOpen,
        setIsModalAddModuleOpen,
    } = props;

    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const [loading, setLoading] = useState(false);

    const handleCloseAddModuleOpen = () => {
        setIsModalAddModuleOpen(false);
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const handleOkThemModule = async () => {
        try {
            // Kiểm tra hợp lệ của form
            await form.validateFields();

            const formValue = await form.getFieldsValue();
            console.log(formValue);
            moduleService.createModule(formValue).then((res) => {
                if (res.data.status === "CREATED") {
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
                handleCloseAddModuleOpen();
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
    //modal comfirm khi add
    const [isOpenComfirm, setIsOpenComfirm] = useState(false);
    const showComfirm = () => {
        setIsOpenComfirm(true);
    }
    const xacNhanComfirm = async () => {
        handleOkThemModule();
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
                        <FontAwesomeIcon icon={faBuildingWheat}/>Thêm mô-đun
                    </h3>
                }
                open={isModalAddModuleOpen}
                onOk={showComfirm}
                onCancel={handleCloseAddModuleOpen}
                width={"50vw"}
                okText="Hoàn tất"
                cancelText="Quay lại"
                // footer={
                //     <>
                //     </>
                // }
            >
                <Container fluid className={"shadow p-4 rounded-3 mt-4"}>
                    <InputCustom
                        form={form}
                        isButtonDisabled={isButtonDisabled}
                        setIsButtonDisabled={setIsButtonDisabled}
                        handleOk={handleOkThemModule}
                        titleTooltip={"Thêm mô-đun"}
                        titlePopconfirm={"Thông báo"}
                        contentPopconfirm={"Bạn có muốn thêm mô-đun mới không?"}
                        placeholderInput={"Nhập tên mô-đun"}
                        sizeInput={"large"}
                        nameFormInput={"tenModule"}
                        sizeButton={"large"}
                        typeButton={"primary"}
                    />
                </Container>
            </Modal>
            {/* modal comfirm khi add */}
            <Modal
                title="Xác nhận thêm mô-đun"
                onOk={xacNhanComfirm}
                onCancel={cancelComfirm}
                open={isOpenComfirm}
                okText="Xác nhận"
                cancelText="Hủy"
                className="mt-5"
                okButtonProps={{style: {backgroundColor: 'green', color: 'white'}}}
            >
                <p>Bạn có chắc muốn thêm hay không ?</p>
            </Modal>
        </>
    );
};

export default ModalAddModule;