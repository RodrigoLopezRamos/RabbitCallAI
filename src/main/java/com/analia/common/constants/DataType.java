package com.analia.common.constants;

public enum DataType {
    DATA_TYPE_STRING(1);

    private int id;

    DataType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
