import {Button, Col, Input, Modal, Popconfirm, Row, Tooltip} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBookOpenReader, faPenToSquare,} from "@fortawesome/free-solid-svg-icons";
import {Container, Form} from "react-bootstrap";
import {toast} from "react-toastify";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import boMonChuyenNganhService from "../../../service/departmentservice/BoMonChuyenNganhService";

const ModalUpdateBoMon = (props) => {

    const {
        isModalUpdateBoMonOpen,
        setIsModalUpdateBoMonOpen,
        dataUpdate,
        setDataUpdate,
    } = props;

    const {loading, setLoading} = useLoading();

    const handleCloseUpdateBoMonOpen = () => {
        setIsModalUpdateBoMonOpen(false);
    }

    const onChangeInputSuaBoMon = (e) => {
        const {name, value} = e.target;
        setDataUpdate({...dataUpdate, [name]: value});
    };

    const handleOkSuaBoMon = () => {
        if (dataUpdate.tenBoMon.trim() === "" || dataUpdate.tenBoMon.trim() === null || dataUpdate.maBoMon.trim() === "" || dataUpdate.maBoMon.trim() === null) {
            toast.warning("Vui lòng điền đầy đủ các trường (không rỗng)");
            return;
        }
        boMonChuyenNganhService.updateBoMon(dataUpdate, dataUpdate.idBoMon)
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
                setIsModalUpdateBoMonOpen(false);
            })
            .catch((error) => {
                toast.error("cập nhật thất bại");
            });
    };

    return (
        <>
            {loading && <GlobalLoading/>}
            <Modal
                title={<h3
                    style={{
                        color: "#052C65"
                    }}
                ><FontAwesomeIcon icon={faBookOpenReader}/> Cập nhật bộ môn</h3>}
                open={isModalUpdateBoMonOpen}
                onCancel={handleCloseUpdateBoMonOpen}
                width={"70vw"}
                footer={
                    <>
                    </>
                }
            >
                <Container fluid className={"shadow p-4 rounded-3 mt-4"}>
                    <Row gutter={16}>
                        <Col flex={6}>
                            <Form.Label className={"pb-1"}>Tên:</Form.Label>
                            <Input className="mb-3"
                                   size="large"
                                   placeholder="Tên bộ môn"
                                   onChange={(e) => onChangeInputSuaBoMon(e)}
                                   type="text"
                                   name="tenBoMon"
                                   value={dataUpdate ? dataUpdate.tenBoMon : null}
                            />
                            <Form.Label className={"pb-1"}>Mã:</Form.Label>
                            <Input className="mb-3"
                                   size="large"
                                   placeholder="Mã bộ môn"
                                   onChange={(e) => onChangeInputSuaBoMon(e)}
                                   type="text"
                                   name="maBoMon"
                                   value={dataUpdate ? dataUpdate.maBoMon : null}
                            />
                        </Col>
                    </Row>
                    <Row className="d-flex justify-content-end">
                        <Tooltip title="Cập nhật tên chuyên ngành" color={"#052C65"}>
                            <Popconfirm
                                placement="top"
                                title={"Thông báo"}
                                description={"Bạn có muốn cập nhật tên chuyên ngành không ?"}
                                okText="Có"
                                cancelText="Không"
                                onConfirm={() => handleOkSuaBoMon()}
                            >
                                <Button icon={<FontAwesomeIcon icon={faPenToSquare} size="lg"/>}
                                        size={"large"}
                                        type={"primary"}
                                        style={{
                                            backgroundColor: "#052C65",
                                        }}
                                >
                                </Button>
                            </Popconfirm>
                        </Tooltip>
                    </Row>
                </Container>
            </Modal>
        </>
    );
};

export default ModalUpdateBoMon;