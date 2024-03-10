import {Form, Modal} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {useState} from "react";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBuildingWheat} from "@fortawesome/free-solid-svg-icons";
import {Container} from "react-bootstrap";
import {toast} from "react-toastify";
import InputCustom from "./InputCustom";
import coSoService from "../../../service/campusservice/CoSoService";

const ModalAddCampus = (props) => {

    const [form] = Form.useForm();

    const {
        isModalAddCoSoOpen,
        setIsModalAddCoSoOpen,
    } = props;

    const [isButtonDisabled, setIsButtonDisabled] = useState(false);

    const [loading, setLoading] = useState(false);

    const handleCloseAddCoSoOpen = () => {
        setIsModalAddCoSoOpen(false);
        form.resetFields();
        setIsButtonDisabled(false);
    }

    const handleOkThemCoSo = async () => {

        const formValue = await form.getFieldsValue();

        coSoService.createCoSo(formValue).then((res) => {
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
            handleCloseAddCoSoOpen();
        }).catch((err) => {
            console.log("nó vào đấy");
            console.log(err);
            console.log(err.code);
            if (err.code && err.code === "ERR_BAD_REQUEST") {
                toast.error(err.response.data.tenCoSo, {
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
                        <FontAwesomeIcon icon={faBuildingWheat}/>Thêm cơ sở
                    </h3>
                }
                open={isModalAddCoSoOpen}
                onCancel={handleCloseAddCoSoOpen}
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
                        handleOk={handleOkThemCoSo}
                        titleTooltip={"Thêm cơ sở"}
                        titlePopconfirm={"Thông báo"}
                        contentPopconfirm={"Bạn có muốn thêm cơ sở mới không?"}
                        placeholderInput={"Nhập tên cơ sở"}
                        sizeInput={"large"}
                        nameFormInput={"tenCoSo"}
                        codeFormInput={"maCoSo"}
                        sizeButton={"large"}
                        typeButton={"primary"}
                    />
                </Container>
            </Modal>
        </>
    );
};

export default ModalAddCampus;