package com.example.bismillah.AdminBerita;

public class Data {
    private String Judul;
    private String ImageURL;
    private String Isi;
    private String Tanggal;

    public Data(String judul, String imageURL, String isi, String tanggal) {
        Judul = judul;
        ImageURL = imageURL;
        Isi = isi;
    }

    public Data() {
    }

    public String getJudul() {
        return Judul;
    }

    public void setJudul(String judul) {
        Judul = judul;
    }

    public String getImageURL() {
        return ImageURL;
    }

    public void setImageURL(String imageURL) {
        ImageURL = imageURL;
    }

    public String getIsi() {
        return Isi;
    }

    public void setIsi(String isi) {
        Isi = isi;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }
}
