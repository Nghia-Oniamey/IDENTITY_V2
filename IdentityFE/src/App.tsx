import {FloatButton} from "antd";
import {ToastContainer} from "react-toastify";
import {LoadingProvider} from "./context/loading-context/LoadingContext";
import {BrowserRouter} from "react-router-dom";
import React from "react";
import Router from "./route";

function App() {
    return (
        <LoadingProvider>
            <div className="scroll-smooth md:scroll-auto font-sans">
                <BrowserRouter>
                    <Router/>
                </BrowserRouter>
                <FloatButton.BackTop/>
                <ToastContainer/>
            </div>
        </LoadingProvider>
    )
}

export default App;
