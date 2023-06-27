package com.godeltech.gbf.model.db;

public enum Delivery {
    XXS, XS, S, M, L;

    public String nameWithDesc(){
        return this.name()+"_DETAILS";
    }
}
