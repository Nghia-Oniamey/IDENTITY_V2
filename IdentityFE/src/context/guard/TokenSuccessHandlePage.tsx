import {useNavigate, useSearchParams} from "react-router-dom";
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {getPermissionsUser, getUserInformation} from "../../utils/TokenDecoder";
import {setAuthorization} from "../redux/slice/AuthSlice";

const TokenSuccessHandlePage = () => {
    const [searchParams] = useSearchParams();

    const dispatch = useDispatch();

    const navigate = useNavigate();

    useEffect(() => {
        const token = searchParams.get("Token");
        const authenticToken = token ? JSON.parse(token) : null;
        const error = searchParams.get("error");
        if (authenticToken) {
            const {accessToken, refreshToken} = authenticToken;
            const userInfo = getUserInformation(accessToken);
            const permissions = getPermissionsUser(accessToken);
            if (userInfo && permissions) {
                console.log(userInfo, permissions, refreshToken, accessToken);
                dispatch(setAuthorization(
                    {
                        token: accessToken,
                        userInfo: userInfo,
                        permissions: permissions,
                        refreshToken: refreshToken,
                    }
                ));
                navigate("/admin/manage-staff");
            }
        }

        if (error) {
            navigate("/");
        }
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, []);

    return null;

}

export default TokenSuccessHandlePage;