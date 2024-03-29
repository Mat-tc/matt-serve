package mat.mat_t.domain.class_;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Days {

    /*
    * 기본 Days data set setting
    * insert into Days values(1, '월요일');
    * insert into Days values(2, '화요일');
    * insert into Days values(3, '수요알');
    * insert into Days values(4, '목요일');
    * insert into Days values(5, '금요일');
    * insert into Days values(6, '토요일');
    * insert into Days values(7, '일요일');
    *
    * */
    @Id
    @Column(name = "day_id")
    private Long dayId;

    @Column(nullable = false)
    private String dayName;

    @OneToMany(mappedBy = "days")
    private List<ClassDay> classDayList = new ArrayList<>();

    public Days() {
    }

    public Days(Long dayId) {
        this.dayId = dayId;
    }
}
