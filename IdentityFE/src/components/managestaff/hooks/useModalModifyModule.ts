import {useState} from "react";

export const useModalModifyModule = () => {
    const [show, setShow] = useState(false);
    const [module, setModule] = useState({});
    const [role, setRole] = useState([]);
    const [idStaff, setIdStaff] = useState<number>();

    const handleClose = () => {
        setShow(false);
    };

    const handleOpen = (id: number) => {
        setShow(true);
        setIdStaff(id);
    };

    return {
        show,
        module,
        role,
        handleClose,
        handleOpen,
        setRole,
        idStaff,
    };
}