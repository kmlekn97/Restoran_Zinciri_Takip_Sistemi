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
public class Fatura {
    private int fatura_id;
    private String fatura_no;
    private Date fatura_tarih;
    private String firma_adi;
    private String firma_adresi;
    private String firma_tel;
    private String firma_vd;
    private int restoran_id;
    private int kullanici_id;
    private Restoranlar restoran;

    public Fatura(int fatura_id, String fatura_no, Date fatura_tarih, String firma_adi, String firma_adresi, String firma_tel, String firma_vd, int restoran_id, int kullanici_id, Restoranlar restoran) {
        this.fatura_id = fatura_id;
        this.fatura_no = fatura_no;
        this.fatura_tarih = fatura_tarih;
        this.firma_adi = firma_adi;
        this.firma_adresi = firma_adresi;
        this.firma_tel = firma_tel;
        this.firma_vd = firma_vd;
        this.restoran_id = restoran_id;
        this.kullanici_id = kullanici_id;
        this.restoran = restoran;
    }

    public int getFatura_id() {
        return fatura_id;
    }

    public String getFatura_no() {
        return fatura_no;
    }

    public Date getFatura_tarih() {
        return fatura_tarih;
    }

    public String getFirma_adi() {
        return firma_adi;
    }

    public String getFirma_adresi() {
        return firma_adresi;
    }

    public String getFirma_tel() {
        return firma_tel;
    }

    public String getFirma_vd() {
        return firma_vd;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public int getKullanici_id() {
        return kullanici_id;
    }

    public Restoranlar getRestoran() {
        return restoran;
    }

    public void setFatura_id(int fatura_id) {
        this.fatura_id = fatura_id;
    }

    public void setFatura_no(String fatura_no) {
        this.fatura_no = fatura_no;
    }

    public void setFatura_tarih(Date fatura_tarih) {
        this.fatura_tarih = fatura_tarih;
    }

    public void setFirma_adi(String firma_adi) {
        this.firma_adi = firma_adi;
    }

    public void setFirma_adresi(String firma_adresi) {
        this.firma_adresi = firma_adresi;
    }

    public void setFirma_tel(String firma_tel) {
        this.firma_tel = firma_tel;
    }

    public void setFirma_vd(String firma_vd) {
        this.firma_vd = firma_vd;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setKullanici_id(int kullanici_id) {
        this.kullanici_id = kullanici_id;
    }

    public void setRestoran(Restoranlar restoran) {
        this.restoran = restoran;
    }

    
    
}
