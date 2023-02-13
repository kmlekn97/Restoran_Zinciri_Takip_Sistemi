/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

/**
 *
 * @author Kemal
 */
public class Hatirla {
    private int benihatirla_id;
    private String kullanici_adi;
    private String kullanici_sifre;
    private String MAC;

    public Hatirla(int benihatirla_id, String kullanici_adi, String kullanici_sifre, String MAC) {
        this.benihatirla_id = benihatirla_id;
        this.kullanici_adi = kullanici_adi;
        this.kullanici_sifre = kullanici_sifre;
        this.MAC = MAC;
    }

    public int getBenihatirla_id() {
        return benihatirla_id;
    }

    public String getKullanici_adi() {
        return kullanici_adi;
    }

    public String getKullanici_sifre() {
        return kullanici_sifre;
    }

    public String getMAC() {
        return MAC;
    }

    public void setBenihatirla_id(int benihatirla_id) {
        this.benihatirla_id = benihatirla_id;
    }

    public void setKullanici_adi(String kullanici_adi) {
        this.kullanici_adi = kullanici_adi;
    }

    public void setKullanici_sifre(String kullanici_sifre) {
        this.kullanici_sifre = kullanici_sifre;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }   
}
