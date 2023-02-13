/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.util.Date;

/**
 *
 * @author Kemal
 */
public class Malzeme {
    private int malzeme_id;
    private int malzeme_adi;
    private double malzeme_miktar; 
    private int restoran_id;
    private double malzeme_fiyat;
    private double malzeme_birim_fiyat;
    private int fatura_durum;
    private Date tarih;
    private Malzeme_Kayit stok_kayit;

    public Malzeme(int malzeme_id, int malzeme_adi, double malzeme_miktar, int restoran_id, double malzeme_fiyat, double malzeme_birim_fiyat, int fatura_durum, Date tarih, Malzeme_Kayit stok_kayit) {
        this.malzeme_id = malzeme_id;
        this.malzeme_adi = malzeme_adi;
        this.malzeme_miktar = malzeme_miktar;
        this.restoran_id = restoran_id;
        this.malzeme_fiyat = malzeme_fiyat;
        this.malzeme_birim_fiyat = malzeme_birim_fiyat;
        this.fatura_durum = fatura_durum;
        this.tarih = tarih;
        this.stok_kayit = stok_kayit;
    }

    public int getMalzeme_id() {
        return malzeme_id;
    }

    public int getMalzeme_adi() {
        return malzeme_adi;
    }

    public double getMalzeme_miktar() {
        return malzeme_miktar;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public double getMalzeme_fiyat() {
        return malzeme_fiyat;
    }

    public double getMalzeme_birim_fiyat() {
        return malzeme_birim_fiyat;
    }

    public int getFatura_durum() {
        return fatura_durum;
    }

    public Date getTarih() {
        return tarih;
    }

    public Malzeme_Kayit getStok_kayit() {
        return stok_kayit;
    }

    public void setMalzeme_id(int malzeme_id) {
        this.malzeme_id = malzeme_id;
    }

    public void setMalzeme_adi(int malzeme_adi) {
        this.malzeme_adi = malzeme_adi;
    }

    public void setMalzeme_miktar(double malzeme_miktar) {
        this.malzeme_miktar = malzeme_miktar;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setMalzeme_fiyat(double malzeme_fiyat) {
        this.malzeme_fiyat = malzeme_fiyat;
    }

    public void setMalzeme_birim_fiyat(double malzeme_birim_fiyat) {
        this.malzeme_birim_fiyat = malzeme_birim_fiyat;
    }

    public void setFatura_durum(int fatura_durum) {
        this.fatura_durum = fatura_durum;
    }

    public void setTarih(Date tarih) {
        this.tarih = tarih;
    }

    public void setStok_kayit(Malzeme_Kayit stok_kayit) {
        this.stok_kayit = stok_kayit;
    }  
}
