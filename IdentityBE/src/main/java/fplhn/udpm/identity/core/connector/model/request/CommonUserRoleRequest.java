package fplhn.udpm.identity.core.connector.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CommonUserRoleRequest {

    @NotNull
    private String clientId;

    @NotNull
    private String clientSecret;

    @NotNull
    private String userCode;

}
