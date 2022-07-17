package com.example.store.model;

import com.orm.SugarRecord;

public class Categories extends SugarRecord {

    public String label;
    public int id;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

}
