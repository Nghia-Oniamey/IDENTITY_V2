package fplhn.udpm.identity.core.admin.managestaff.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuleRoleResponse {

    List<ModuleResponse> listModule;

    List<RoleResponse> listRole;

}
