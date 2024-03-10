import {instanceNoAuth} from "../base/request";

const URL_ENTRY_MODULE = "/entry-module";


export const getAllEntryModule = () => {
    return instanceNoAuth({
        url: `${URL_ENTRY_MODULE}/all`,
        method: "GET",
    });
}
