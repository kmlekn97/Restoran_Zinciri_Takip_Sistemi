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
public class Menu {
    private int menu_id;
    private String menu_adi;
    private Date menu_tarih;
    private String urun_tipi;
    private int urun_id;
    private int restoran_id;
    private Urun urunler;
    private Restoranlar restoran;

    public Menu(int menu_id, String menu_adi, Date menu_tarih, String urun_tipi, int urun_id, int restoran_id, Urun urunler, Restoranlar restoran) {
        this.menu_id = menu_id;
        this.menu_adi = menu_adi;
        this.menu_tarih = menu_tarih;
        this.urun_tipi = urun_tipi;
        this.urun_id = urun_id;
        this.restoran_id = restoran_id;
        this.urunler = urunler;
        this.restoran = restoran;
    }

    public int getMenu_id() {
        return menu_id;
    }

    public String getMenu_adi() {
        return menu_adi;
    }

    public Date getMenu_tarih() {
        return menu_tarih;
    }

    public String getUrun_tipi() {
        return urun_tipi;
    }

    public int getUrun_id() {
        return urun_id;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public Urun getUrunler() {
        return urunler;
    }

    public Restoranlar getRestoran() {
        return restoran;
    }

    public void setMenu_id(int menu_id) {
        this.menu_id = menu_id;
    }

    public void setMenu_adi(String menu_adi) {
        this.menu_adi = menu_adi;
    }

    public void setMenu_tarih(Date menu_tarih) {
        this.menu_tarih = menu_tarih;
    }

    public void setUrun_tipi(String urun_tipi) {
        this.urun_tipi = urun_tipi;
    }

    public void setUrun_id(int urun_id) {
        this.urun_id = urun_id;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setUrunler(Urun urunler) {
        this.urunler = urunler;
    }

    public void setRestoran(Restoranlar restoran) {
        this.restoran = restoran;
    }    
}
