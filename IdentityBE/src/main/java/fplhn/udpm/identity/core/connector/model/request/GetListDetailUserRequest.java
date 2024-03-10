package fplhn.udpm.identity.core.connector.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class GetListDetailUserRequest {

    @NotNull(message = "clientId is required")
    private String clientId;

    @NotNull(message = "clientSecret is required")
    private String clientSecret;

    List<String> userCodes;

}
