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
public class Rapor extends Kullanici{

    public Rapor(Kayit log,int kullaniciId, String kullaniciAdi, String sifre, int yetki, String kullanici_resim) {
        super(kullaniciId, kullaniciAdi, sifre, yetki, kullanici_resim);
    }


    
    @Override
    public void setKullanici_resim(String kullanici_resim) {
        super.setKullanici_resim(kullanici_resim); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getKullanici_resim() {
        return super.getKullanici_resim(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setYetki(int yetki) {
        super.setYetki(yetki); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getYetki() {
        return super.getYetki(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setSifre(String sifre) {
        super.setSifre(sifre); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSifre() {
        return super.getSifre(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setKullaniciAdi(String kullaniciAdi) {
        super.setKullaniciAdi(kullaniciAdi); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getKullaniciAdi() {
        return super.getKullaniciAdi(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setKullaniciId(int kullaniciId) {
        super.setKullaniciId(kullaniciId); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getKullaniciId() {
        return super.getKullaniciId(); //To change body of generated methods, choose Tools | Templates.
    }
       
}
