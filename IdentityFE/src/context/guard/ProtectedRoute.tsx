import {useNavigate} from "react-router-dom";
import {useSelector} from "react-redux";
import {ReactNode, useEffect} from "react";
import {RootState} from "../redux/store";

export default function ProtectedRoute({children}: { children: ReactNode | Element }) {

    const {userInfo} = useSelector((state: RootState) => state.auth.authorization);

    const navigate = useNavigate();

    useEffect(() => {
        if (userInfo === null) {
            navigate("/not-authorization", {replace: true});
        }
    }, [navigate, userInfo]);

    return <>{children}</>;
}