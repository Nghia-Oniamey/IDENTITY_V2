import {Hourglass} from "react-loader-spinner";

const GlobalLoading = () => {
    return (
        <>
            <div
                style={{
                    position: "fixed",
                    top: "0",
                    left: "0",
                    width: "100%",
                    height: "100%",
                    backgroundColor: "rgba(0, 0, 0, 0.1)",
                    zIndex: 9999,
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    backdropFilter: "blur(1px)",
                }}
            >
                <Hourglass
                    height="80"
                    width="80"
                    ariaLabel="hourglass-loading"
                    wrapperStyle={{}}
                    wrapperClass=""
                    colors={["#306cce", "#72a1ed"]}
                />
            </div>
        </>
    );
};

export default GlobalLoading;
