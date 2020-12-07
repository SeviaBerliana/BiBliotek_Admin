package com.bibliotek.model;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;
import com.bibliotek.BR;

public class Pengumuman  extends BaseObservable {
    private String idPengumuman;
    private String judul;
    private String deskripsi;

    public Pengumuman(String judul, String deskripsi) {
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public Pengumuman(String idPengumuman, String judul, String deskripsi) {
        this.idPengumuman = idPengumuman;
        this.judul = judul;
        this.deskripsi = deskripsi;
    }

    public String getIdPengumuman(){
        return idPengumuman;
    }

    @Bindable
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
        notifyPropertyChanged(BR.judul);
    }

    @Bindable
    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
        notifyPropertyChanged(BR.deskripsi);
    }

}
