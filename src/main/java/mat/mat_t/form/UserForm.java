package mat.mat_t.form;


import lombok.Getter;
import lombok.Setter;
import mat.mat_t.domain.user.Gender;
import mat.mat_t.domain.user.Instructor;
import mat.mat_t.domain.user.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


@Getter
@Setter
public class UserForm {

    @NotNull
    private Long id;

    @NotBlank(message = "필수 입력란입니다.")
    private String loginId;

    @NotBlank(message = "필수 입력란입니다.")
    private String password;

    @NotBlank(message = "필수 입력란입니다.")
    private String name;

    @NotBlank(message = "필수 입력란입니다.")
    private String nickname;

    @NotBlank(message = "필수 입력란입니다.")
    private String birthDate;

    @NotBlank(message = "필수 입력란입니다.")
    private String phoneNumber;

    @NotBlank(message = "필수 입력란입니다.")
    private String email;

    @NotNull(message = "필수 입력란입니다.")
    private Gender gender;

    public UserForm(){}

    public UserForm(User user){
        this.id=user.getId();
    }
}
