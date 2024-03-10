package fplhn.udpm.identity.core.common.config;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
public class ResponseObject {

    private boolean isSuccess = false;
    private HttpStatus status;
    private Object data;
    private String message;

    public <T> ResponseObject(T obj, HttpStatus status, String message) {
        processReponseObject(obj);
        this.status = status;
        this.message = message;
    }

    public void processReponseObject(Object obj) {
        if (obj != null) {
            this.isSuccess = true;
            this.data = obj;
        }
    }

    public ResponseObject success(Object obj, String message) {
        processReponseObject(obj);
        this.status = HttpStatus.OK;
        this.message = message;
        return this;
    }
}
