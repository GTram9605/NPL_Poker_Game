package za.ac.nplinnovations.nplpoker.pojos;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

import za.ac.nplinnovations.nplpoker.R;

public class Card implements Serializable {
    private String code;
    private String name;
    private Integer image;

    public Card() {
    }

    public Card(String code, String name, Integer image) {
        this.code = code;
        this.name = name;
        this.image = image;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

}
