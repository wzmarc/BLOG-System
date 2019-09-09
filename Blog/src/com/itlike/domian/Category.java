package com.itlike.domian;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class Category {
    private Integer cid;
    private String cname;
    private Integer parentid;

    @Override
    public String toString() {
        return "Category{" +
                "cid=" + cid +
                ", cname='" + cname + '\'' +
                ", parentid=" + parentid +
                '}';
    }
}
