package com.bootx.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Config extends BaseEntity<Long> {

    @Column(length = 1000)
    private String updateFsId;

    public String getUpdateFsId() {
        return updateFsId;
    }

    public void setUpdateFsId(String updateFsId) {
        this.updateFsId = updateFsId;
    }
}
