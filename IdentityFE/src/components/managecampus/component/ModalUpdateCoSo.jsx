import {Button, Col, Input, Modal, Popconfirm, Row, Tooltip} from "antd";
import GlobalLoading from "../../global-loading/GlobalLoading";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faBuildingCircleArrowRight, faPenToSquare} from "@fortawesome/free-solid-svg-icons";
import {Container, Form} from "react-bootstrap";
import {toast} from "react-toastify";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import coSoService from "../../../service/campusservice/CoSoService";

const ModalUpdateCoSo = (props) => {

    const {
        isModalUpdateCoSoOpen,
        setIsModalUpdateCoSoOpen,
        dataUpdate,
        setDataUpdate,
    } = props;

    const {loading, setLoading} = useLoading();

    const handleCloseUpdateCoSoOpen = () => {
        setIsModalUpdateCoSoOpen(false);
    }

    const onChangeInputSuaCoSo = (e) => {
        const {name, value} = e.target;

        setDataUpdate({...dataUpdate, [name]: value});
    };

    const handleOkSuaCoSo = () => {
        if (dataUpdate.tenCoSo.trim() === "" || dataUpdate.tenCoSo.trim() === null || dataUpdate.maCoSo.trim() === "" || dataUpdate.maCoSo.trim() === null) {
            toast.warning("Vui lòng điền đầy đủ các trường");
            return;
        }
        coSoService.updateCoSo(dataUpdate, dataUpdate.idCoSo)
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
                setIsModalUpdateCoSoOpen(false);
            })
            .catch((error) => {
                toast.error("cập nhật thất bại");
                console.log(error.message);
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
                ><FontAwesomeIcon icon={faBuildingCircleArrowRight}/> Cập nhật tên cơ sở</h3>}
                open={isModalUpdateCoSoOpen}
                onCancel={handleCloseUpdateCoSoOpen}
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
                            <Input
                                className="mb-3"
                                size="large"
                                placeholder="Tên cơ sở"
                                onChange={(e) => onChangeInputSuaCoSo(e)}
                                type="text"
                                name="tenCoSo"
                                value={dataUpdate ? dataUpdate.tenCoSo : null}
                                required
                            />
                            <Form.Label className={"pb-1"}>Mã:</Form.Label>
                            <Input className="mb-3"
                                   size="large"
                                   placeholder="Mã cơ sở"
                                   onChange={(e) => onChangeInputSuaCoSo(e)}
                                   type="text"
                                   name="maCoSo"
                                   value={dataUpdate ? dataUpdate.maCoSo : null}
                                   required
                            />
                        </Col>
                    </Row>
                    <Row className="d-flex justify-content-end">
                        <Tooltip title="Cập nhật tên cơ sở" color={"#052C65"}>
                            <Popconfirm
                                placement="top"
                                title={"Thông báo"}
                                description={"Bạn có muốn cập nhật tên cơ sở không ?"}
                                okText="Có"
                                cancelText="Không"
                                onConfirm={() => handleOkSuaCoSo()}
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

export default ModalUpdateCoSo;