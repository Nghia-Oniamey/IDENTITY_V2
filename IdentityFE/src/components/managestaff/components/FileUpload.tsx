import React from "react";

const FileUpload = ({onFileSelect, refElement}: {
    onFileSelect: (file: File) => void,
    refElement: React.RefObject<HTMLInputElement>
}) => {
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        onFileSelect(e.target.files![0]);
    };

    return (
        <input
            type="file"
            ref={refElement}
            id="file"
            style={{display: "none"}}
            onChange={handleFileChange}
        />
    );
};

export default FileUpload;