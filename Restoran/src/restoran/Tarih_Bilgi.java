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
public class Tarih_Bilgi {
    
    private int siparisSayisi;
    private double gunlukCiro;

    public Tarih_Bilgi(int siparisSayisi, double gunlukCiro) {
        this.siparisSayisi = siparisSayisi;
        this.gunlukCiro = gunlukCiro;
    }

    public int getSiparisSayisi() {
        return siparisSayisi;
    }

    public void setSiparisSayisi(int siparisSayisi) {
        this.siparisSayisi = siparisSayisi;
    }

    public double getGunlukCiro() {
        return gunlukCiro;
    }

    public void setGunlukCiro(double gunlukCiro) {
        this.gunlukCiro = gunlukCiro;
    }

        
    
}
