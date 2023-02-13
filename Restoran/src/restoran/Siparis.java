/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.time.LocalDateTime;
import java.util.Date;

/**
 *
 * @author Kemal
 */
public class Siparis extends Masa{
        private int siparisID;
        private Urun siparisUrun;
        private int siparisAdet;
        private Date siparisZaman;
        private int siparisDurum;
        private int siparisOdenme;
        private int siparisMasa;
        private int restoran_id;

    public Siparis(int siparisID, Urun siparisUrun, int siparisAdet, Date siparisZaman, int siparisDurum, int siparisOdenme, int siparisMasa, int masaNo, int masaDurum, int ID, int restoran_id, String restoran_adi, String restoran_adres, String restoran_tel, String restoran_vd, Cari cari_kayit, int ulke_id, int il_id, int ilce_id) {
        super(masaNo, masaDurum, ID, restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cari_kayit, ulke_id, il_id, ilce_id);
        this.siparisID = siparisID;
        this.siparisUrun = siparisUrun;
        this.siparisAdet = siparisAdet;
        this.siparisZaman = siparisZaman;
        this.siparisDurum = siparisDurum;
        this.siparisOdenme = siparisOdenme;
        this.siparisMasa = siparisMasa;
    }

    public int getSiparisID() {
        return siparisID;
    }

    public Urun getSiparisUrun() {
        return siparisUrun;
    }

    public int getSiparisAdet() {
        return siparisAdet;
    }

    public Date getSiparisZaman() {
        return siparisZaman;
    }

    public int getSiparisDurum() {
        return siparisDurum;
    }

    public int getSiparisOdenme() {
        return siparisOdenme;
    }

    public int getSiparisMasa() {
        return siparisMasa;
    }

    public void setSiparisID(int siparisID) {
        this.siparisID = siparisID;
    }

    public void setSiparisUrun(Urun siparisUrun) {
        this.siparisUrun = siparisUrun;
    }

    public void setSiparisAdet(int siparisAdet) {
        this.siparisAdet = siparisAdet;
    }

    public void setSiparisZaman(Date siparisZaman) {
        this.siparisZaman = siparisZaman;
    }

    public void setSiparisDurum(int siparisDurum) {
        this.siparisDurum = siparisDurum;
    }

    public void setSiparisOdenme(int siparisOdenme) {
        this.siparisOdenme = siparisOdenme;
    }

    public void setSiparisMasa(int siparisMasa) {
        this.siparisMasa = siparisMasa;
    }


   
    
    
}
