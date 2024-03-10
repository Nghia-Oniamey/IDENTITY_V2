package fplhn.udpm.identity.core.connector.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetListUserFromRoleCodeRequest {

    @NotNull
    private String clientId;

    @NotNull
    private String clientSecret;

    @NotNull
    private String roleCode;

}
