import {Container} from "react-bootstrap";
import {useRef} from "react";
import {Button} from "antd";
import {useNavigate} from "react-router-dom";
import GlobalLoading from "../../global-loading/GlobalLoading";
import SearchBar from "../components/SearchBar";
import FileUpload from "../components/FileUpload";
import {buttonColorScheme} from "../style/button.style";
import {useNhanViens} from "../hooks/useNhanViens";
import TableStaff from "../components/TableStaff";
import {useFiles} from "../hooks/useFiles";
import {useLoading} from "../../../context/loading-context/LoadingContext";
import ModalModifyModuleRole from "../components/ModalModifyModuleRole";
import {useModalModifyModule} from "../hooks/useModalModifyModule";

const Staff = () => {
    const {
        danhSachNhanVien,
        paginationObj,
        setPaginationObj,
        totalPages,
        handleSearch,
        handleDelete,
    } = useNhanViens();

    const {show, handleClose, handleOpen, idStaff} = useModalModifyModule();

    const inputFile = useRef<HTMLInputElement>(null);

    const {downloadTemplate, uploadFile} = useFiles(inputFile);

    const navigate = useNavigate();

    const openFile = () => {
        if (inputFile.current) {
            inputFile.current.click();
        }
    };

    const {loading} = useLoading();

    return (
        <>
            <ModalModifyModuleRole
                show={show}
                handleClose={handleClose as any}
                module={undefined}
                role={undefined}
                idStaff={idStaff}
            />
            {loading && <GlobalLoading/>}
            <div className={"mb-5"}>
                <h2>Quản lý nhân viên</h2>
            </div>
            <Container fluid className={"shadow mb-5 p-3 rounded-4"}>
                <h4>Tìm kiếm nhân viên</h4>
                <SearchBar onSearch={handleSearch}/>
            </Container>
            <Container fluid className={"shadow p-3 rounded-4"}>
                <div
                    className={
                        "mb-3 d-flex align-items-center justify-content-lg-between"
                    }
                >
                    <h4>Danh sách nhân viên</h4>
                    <div className={"mb-3 d-flex justify-content-end"}>
                        <FileUpload
                            onFileSelect={(file: File) => {
                                uploadFile(file);
                            }}
                            refElement={inputFile}
                        />
                        <Button onClick={openFile} style={buttonColorScheme}>
                            Import Thông Tin Nhân Viên
                        </Button>
                        <Button
                            onClick={downloadTemplate}
                            className={"ms-3"}
                            style={buttonColorScheme}
                        >
                            Download Template
                        </Button>
                        <Button
                            onClick={() => {
                                navigate("add-staff");
                            }}
                            className={"ms-3"}
                            style={buttonColorScheme}
                        >
                            Thêm Nhân Viên
                        </Button>
                    </div>
                </div>
                <TableStaff
                    danhSachNhanVien={danhSachNhanVien}
                    totalPages={totalPages as number}
                    paginationObj={paginationObj}
                    setPaginationObj={setPaginationObj}
                    handleDelete={handleDelete as any}
                    handleOpenModalModifyModuleRole={handleOpen}
                />
            </Container>
        </>
    );
};

export default Staff;
