import React, {memo, useState} from "react";
import {LogoutOutlined, MenuFoldOutlined, MenuUnfoldOutlined, ProjectOutlined,} from "@ant-design/icons";
import {Button, Layout, Menu, theme, Tooltip} from "antd";
import {Link, useLocation, useNavigate} from "react-router-dom";
// @ts-ignore
import logoUDPM from "../../assets/image/logo-udpm-dark.png";
import {useDispatch, useSelector} from "react-redux";
import {RootState} from "../../context/redux/store";
import {removeAuthorization} from "../../context/redux/slice/AuthSlice";

const {Header, Sider, Content} = Layout;


interface MenuItem {
    key: string;
    icon: React.ReactNode;
    children?: MenuItem[];
    label: React.ReactNode;
}

const DashboardAdmin = ({children}: { children: React.ReactNode }) => {
    const [collapsed, setCollapsed] = useState(false);
    const {
        token: {colorBgContainer},
    } = theme.useToken();

    function getItem(icon: React.ReactNode, key: string, children?: MenuItem[], label?: React.ReactNode) {
        return {
            key,
            icon,
            children,
            label,
        };
    }

    const buttonNavigatorStyle = {marginRight: "8px", marginTop: 7};

    const textNavigatorStyle = {marginLeft: 15, marginRight: 15, fontSize: 13};

    const {userInfo} = useSelector((state: RootState) => state.auth.authorization);

    const dispatch = useDispatch();

    const navigate = useNavigate();

    const location = useLocation();

    const handleLogout = () => {
        dispatch(removeAuthorization());
        navigate("/");
    }

    const items = [
        getItem(
            <Link to="/admin/manage-campus">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý cơ sở</span>
            </Link>,
            "/admin/manage-campus",
        ),
        getItem(
            <Link to="/admin/manage-department">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý bộ môn</span>
            </Link>,
            "/admin/manage-department",
        ),
        getItem(
            <Link to="/admin/manage-staff">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý nhân viên</span>
            </Link>,
            "/admin/manage-staff",
        ),
        getItem(
            <Link to="/admin/manage-student">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý học sinh</span>
            </Link>,
            "/admin/manage-student",
        ), getItem(
            <Link to="/admin/manage-module">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý mô-đun</span>
            </Link>,
            "/admin/manage-module",
        ),
        getItem(
            <Link to="/admin/manage-role">
                <ProjectOutlined style={buttonNavigatorStyle}/>
                <span style={textNavigatorStyle}>Quản lý vai trò</span>
            </Link>,
            "/admin/manage-role",
        ),
    ];

    return (
        <Layout>
            <Sider
                trigger={null}
                collapsible
                collapsed={collapsed}
                style={{
                    overflow: "auto",
                    height: "100vh",
                    position: "fixed",
                    left: 0,
                    top: 0,
                    bottom: 0,
                    alignItems: "center",
                    justifyContent: "center",
                }}
            >
                <div
                    className="demo-logo-vertical"
                    style={{
                        width: "100%",
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        justifyContent: "center",
                        padding: collapsed ? "4px" : 0,
                    }}
                >
                    <img
                        src={logoUDPM}
                        alt="logo"
                        style={{
                            padding: "15px",
                            width: collapsed ? "60px" : "90%",
                            transition: "width 0.2s",
                            borderRadius: collapsed ? "50%" : '30px',
                            backgroundColor: "#fff",
                        }}
                        className={'my-4'}
                    />
                </div>
                <Menu
                    selectedKeys={[location.pathname]}
                    defaultOpenKeys={["sub1"]}
                    mode="inline"
                    theme="dark"
                    items={items}
                />
            </Sider>
            <Layout
                style={{
                    marginLeft: collapsed ? 80 : 200,
                }}
            >
                <Header
                    style={{
                        padding: 0,
                        background: colorBgContainer,
                        display: 'flex',
                        justifyContent: 'space-between',
                    }}
                >
                    <Button
                        type="text"
                        icon={collapsed ? <MenuUnfoldOutlined/> : <MenuFoldOutlined/>}
                        onClick={() => setCollapsed(!collapsed)}
                        style={{
                            fontSize: "16px",
                            width: 64,
                            height: 64,
                        }}
                    />
                    <div
                        style={{
                            display: 'flex',
                            alignItems: 'center',
                            paddingRight: '20px',
                        }}
                    >
                        {/*<img*/}
                        {/*    src={userInfo.}*/}
                        {/*    alt="avatar"*/}
                        {/*    style={{*/}
                        {/*        width: '40px',*/}
                        {/*        height: '40px',*/}
                        {/*        borderRadius: '50%',*/}
                        {/*        marginRight: '10px',*/}
                        {/*    }}*/}
                        {/*/>*/}
                        <span>{userInfo?.fullName}</span>
                        <Tooltip
                            title="Đăng xuất"
                            placement="bottom"
                        >
                            <Button
                                type="text"
                                icon={<LogoutOutlined/>}
                                onClick={handleLogout}
                                style={{
                                    marginLeft: '10px',
                                }}
                            />
                        </Tooltip>
                    </div>
                </Header>
                <Content
                    style={{
                        margin: "24px 16px",
                        padding: 24,
                        background: colorBgContainer,
                    }}
                >
                    {children}
                </Content>
            </Layout>
        </Layout>
    );
};
export default memo(DashboardAdmin);
