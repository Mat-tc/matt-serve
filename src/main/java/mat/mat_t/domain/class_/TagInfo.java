package mat.mat_t.domain.class_;

import lombok.Builder;
import lombok.Data;
import mat.mat_t.form.TagInfoForm;

import javax.persistence.*;


@Entity
@Data
public class TagInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "tagInfo_id")
    private Long tagInfoId;

    @Column(name = "tag_name", unique = true)
    private String tagName;

    @Builder
    public TagInfo(Long tagInfoId, String tagName) {
        this.tagInfoId = tagInfoId;
        this.tagName = tagName;
    }

    public TagInfo(Long tagInfoId) {
        this.tagInfoId = tagInfoId;
    }

    public TagInfo() {
    }

    public TagInfo(TagInfoForm form){
        this.tagInfoId = form.getTagInfoId();
        this.tagName = form.getTagName();
    }

}
