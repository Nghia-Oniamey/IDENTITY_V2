import {Form, Modal} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBookOpen} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import boMonChuyenNganhService from "../../../service/departmentservice/BoMonChuyenNganhService";
import InputCustom from "../../managecampus/component/InputCustom";

const ModalAddBoMon = (props) => {

    const [form] = Form.useForm();

    const {
        isModalAddBoMonOpen,
        setIsModalAddBoMonOpen,
    } = props;

    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const [loading, setLoading] = useState(false);

    const handleCloseAddBoMonOpen = () => {
        setIsModalAddBoMonOpen(false);
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const handleOkThemBoMon = async () => {

        const formValue = await form.getFieldsValue();

        boMonChuyenNganhService.createBoMon(formValue).then((res) => {
            if (res.data.status === "CREATED") {
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
            handleCloseAddBoMonOpen();
        }).catch((err) => {
            if (err.code && err.code === "ERR_BAD_REQUEST") {
                toast.error(err.response.data.tenBoMon, {
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
                        <FontAwesomeIcon icon={faBookOpen}/>Thêm bộ môn
                    </h3>
                }
                open={isModalAddBoMonOpen}
                onCancel={handleCloseAddBoMonOpen}
                width={"50vw"}
                footer={
                    <>
                    </>
                }
            >
                <Container fluid className={"shadow p-4 rounded-3 mt-4"}>
                    <InputCustom
                        form={form}
                        isButtonDisabled={isButtonDisabled}
                        setIsButtonDisabled={setIsButtonDisabled}
                        handleOk={handleOkThemBoMon}
                        titleTooltip={"Thêm bộ môn"}
                        titlePopconfirm={"Thông báo"}
                        contentPopconfirm={"Bạn có muốn thêm bộ môn mới không?"}
                        placeholderInput={"Nhập tên bộ môn"}
                        sizeInput={"large"}
                        nameFormInput={"tenBoMon"}
                        codeFormInput={"maBoMon"}
                        sizeButton={"large"}
                        typeButton={"primary"}
                    />
                </Container>
            </Modal>
        </>
    );
};

export default ModalAddBoMon;