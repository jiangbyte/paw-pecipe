package io.jiangbyte.app.biz.recipe.dto;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serial;
import java.io.Serializable;

public class Ingredient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String name;
    private String amount;

    // getters/setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}