// @ts-ignore
import logoFpoly from "../../assets/image/Logo_FPoly.png";
// @ts-ignore
import logoUDPM from "../../assets/image/logo-udpm-dark.png";
import {Link} from "react-router-dom";
import './index.css'

export default function NotAuthorized() {
    return (
        <div className="content-error">
            <div className="logo">
                <img width="20%" src={logoFpoly} alt="Logo"/>
                <img width="20%" src={logoUDPM} alt="Logo"/>
            </div>
            <h1>401!</h1>
            <h2>Not Authorized</h2>
            <Link to={'/'}>Đăng nhập lại</Link>
        </div>
    )
}