package com.yondev.yaumiyah.entity;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by ThinkPad on 5/7/2017.
 */

public class Target {
    private int id;
    private String judul;
    private Date waktu;
    private int pengulangan;
    private int tipe;
    private Date last_notif;
    private Date create_date;
    private boolean is_deteted;
    private boolean notifikasi;
    private String note;
    private int icon;
    private boolean hasAction = false;
    private int laststatus;
    private ArrayList<TargetDetail> details;
    private boolean vibrasi;
    private String soundname;
    private String sounduri;
    private int soundtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Date getWaktu() {
        return waktu;
    }

    public void setWaktu(Date waktu) {
        this.waktu = waktu;
    }

    public int getPengulangan() {
        return pengulangan;
    }

    public void setPengulangan(int pengulangan) {
        this.pengulangan = pengulangan;
    }

    public int getTipe() {
        return tipe;
    }

    public void setTipe(int tipe) {
        this.tipe = tipe;
    }

    public Date getLast_notif() {
        return last_notif;
    }

    public void setLast_notif(Date last_notif) {
        this.last_notif = last_notif;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }

    public boolean is_deteted() {
        return is_deteted;
    }

    public void setIs_deteted(boolean is_deteted) {
        this.is_deteted = is_deteted;
    }

    public boolean isNotifikasi() {
        return notifikasi;
    }

    public void setNotifikasi(boolean notifikasi) {
        this.notifikasi = notifikasi;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public ArrayList<TargetDetail> getDetails() {
        return details;
    }

    public void setDetails(ArrayList<TargetDetail> details) {
        this.details = details;
    }

    public boolean isHasAction() {
        return hasAction;
    }

    public void setHasAction(boolean hasAction) {
        this.hasAction = hasAction;
    }

    public int getLaststatus() {
        return laststatus;
    }

    public void setLaststatus(int laststatus) {
        this.laststatus = laststatus;
    }

    public boolean isVibrasi() {
        return vibrasi;
    }

    public void setVibrasi(boolean vibrasi) {
        this.vibrasi = vibrasi;
    }

    public int getSoundtime() {
        return soundtime;
    }

    public void setSoundtime(int soundtime) {
        this.soundtime = soundtime;
    }

    public String getSoundname() {
        return soundname;
    }

    public void setSoundname(String soundname) {
        this.soundname = soundname;
    }

    public String getSounduri() {
        return sounduri;
    }

    public void setSounduri(String sounduri) {
        this.sounduri = sounduri;
    }
}
