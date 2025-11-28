import { toast } from "react-toastify";
import { ToastMessageType } from "../enums/ToastMessageType";

class ToastService {

    showMessage(value: string, messageType: ToastMessageType): void {
        if (messageType == ToastMessageType.SUCCESS) {
            toast.success(value);
        }
        else if (messageType == ToastMessageType.INFO) {
            toast.info(value);
        }
        else if (messageType == ToastMessageType.WARN) {
            toast.warn(value);
        } else {
            toast.error(value);
        }
    }
}

export default new ToastService();