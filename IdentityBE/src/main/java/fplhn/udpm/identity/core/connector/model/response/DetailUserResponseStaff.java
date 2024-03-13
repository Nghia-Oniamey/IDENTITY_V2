package fplhn.udpm.identity.core.connector.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetailUserResponseStaff {

    private String id;

    private String name;

    private String userCode;

    private String userName;

    private String emailFE;

    private String emailFPT;

    private String picture;

    private String userSubjectCode;

    private String userTrainingFacilityCode;

}
