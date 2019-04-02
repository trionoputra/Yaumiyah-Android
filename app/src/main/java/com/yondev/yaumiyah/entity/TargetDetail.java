package com.yondev.yaumiyah.entity;

import java.util.Date;

/**
 * Created by ThinkPad on 5/7/2017.
 */

public class TargetDetail {
    private int id;
    private int status;
    private Date updated_date;
    private String note;
    private boolean temp;
    private int id_target;

    private String nama_target;
    private int jumlahOk;
    private int jumlahSkip;
    private int jumlahNo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getUpdated_date() {
        return updated_date;
    }

    public void setUpdated_date(Date updated_date) {
        this.updated_date = updated_date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isTemp() {
        return temp;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    public int getId_target() {
        return id_target;
    }

    public void setId_target(int id_target) {
        this.id_target = id_target;
    }

    public String getNama_target() {
        return nama_target;
    }

    public void setNama_target(String nama_target) {
        this.nama_target = nama_target;
    }

    public int getJumlahOk() {
        return jumlahOk;
    }

    public void setJumlahOk(int jumlahOk) {
        this.jumlahOk = jumlahOk;
    }

    public int getJumlahSkip() {
        return jumlahSkip;
    }

    public void setJumlahSkip(int jumlahSkip) {
        this.jumlahSkip = jumlahSkip;
    }

    public int getJumlahNo() {
        return jumlahNo;
    }

    public void setJumlahNo(int jumlahNo) {
        this.jumlahNo = jumlahNo;
    }
}
