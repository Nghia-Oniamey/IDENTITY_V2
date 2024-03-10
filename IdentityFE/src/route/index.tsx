import NotFound from "../pages/404";
import NotAuthorized from "../pages/403";
import Landing from "../pages/login";
import ProtectedRoute from "../context/guard/ProtectedRoute";
import DashboardAdmin from "../layout/admin";
import {Outlet, useRoutes} from "react-router-dom";
import React from "react";
import Staff from "../components/managestaff/layout";
import ModifyStaff from "../components/managestaff/layout/ModifyStaff";
import ManageCampus from "../components/managecampus/layout";
import QuanLyBoMonChuyenNganh from "../components/managedepartment/layout/QuanLyBoMonChuyenNganh";
import QuanLyBoMonTheoCoSo from "../components/managedepartmentcampus/layout/QuanLyBoMonTheoCoSo";
import ManageModule from "../components/managemodule/layout";
import ManageRole from "../components/managerole/layout";
import ManageStudent from "../components/managestudent/layout";
import TokenSuccessHandlePage from "../context/guard/TokenSuccessHandlePage";

interface RouteType {
    path: string;
    element: React.ReactNode;
    children?: RouteType[];
}

const protectedRoute = (path: string, element: React.ReactNode, children?: RouteType[]) => ({
    path: `/admin/${path}`,
    element: <ProtectedRoute><DashboardAdmin>{element}</DashboardAdmin></ProtectedRoute>,
    children
});

const Router = () => {
    const routes: RouteType[] = [
        {path: "*", element: <NotFound/>},
        {path: "/not-authentication", element: <NotAuthorized/>},
        {path: "/not-authorization", element: <NotAuthorized/>},
        {path: "/", element: <Landing/>},
        protectedRoute("manage-campus", <ManageCampus/>),
        protectedRoute("manage-student", <ManageStudent/>),
        protectedRoute("manage-staff", <Outlet/>, [
            {path: "", element: <Staff/>},
            {path: "add-staff", element: <ModifyStaff/>},
            {path: "update-staff/:id", element: <ModifyStaff/>}
        ]),
        protectedRoute("manage-department", <QuanLyBoMonChuyenNganh/>),
        protectedRoute("manage-department/:idBoMon", <QuanLyBoMonTheoCoSo/>),
        protectedRoute("manage-department-campus/:idBoMon", <QuanLyBoMonTheoCoSo/>),
        protectedRoute("manage-module", <ManageModule/>),
        protectedRoute("manage-role", <ManageRole/>),
        {path: "/login", element: <TokenSuccessHandlePage/>}
    ];

    return useRoutes(routes);
}

export default Router;