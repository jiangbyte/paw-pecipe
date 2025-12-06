package io.jiangbyte.app.biz.recipe.dto;

import java.io.Serial;
import java.io.Serializable;

public class Step implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String desc;

    //        private String image;
    // getters/setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
//        public String getImage() { return image; }
//        public void setImage(String image) { this.image = image; }
}