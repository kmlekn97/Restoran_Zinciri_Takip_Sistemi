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
public class Urun {
        private int urunId;
        private String urunAdi;
        private String urunAyrinti;
        private double urunUcret;
        private String urun_resim;
        private int restoran_id;
        private Restoranlar restoran;

    public Urun(int urunId, String urunAdi, String urunAyrinti, double urunUcret, String urun_resim, int restoran_id, Restoranlar restoran) {
        this.urunId = urunId;
        this.urunAdi = urunAdi;
        this.urunAyrinti = urunAyrinti;
        this.urunUcret = urunUcret;
        this.urun_resim = urun_resim;
        this.restoran_id = restoran_id;
        this.restoran = restoran;
    }

    public int getUrunId() {
        return urunId;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public String getUrunAyrinti() {
        return urunAyrinti;
    }

    public double getUrunUcret() {
        return urunUcret;
    }

    public String getUrun_resim() {
        return urun_resim;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public Restoranlar getRestoran() {
        return restoran;
    }

    public void setUrunId(int urunId) {
        this.urunId = urunId;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }

    public void setUrunAyrinti(String urunAyrinti) {
        this.urunAyrinti = urunAyrinti;
    }

    public void setUrunUcret(double urunUcret) {
        this.urunUcret = urunUcret;
    }

    public void setUrun_resim(String urun_resim) {
        this.urun_resim = urun_resim;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setRestoran(Restoranlar restoran) {
        this.restoran = restoran;
    }

   
   
}
