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
public class Masa extends Restoranlar{

    private int masaNo;
    private int masaDurum;
    private int ID;

    public Masa(int masaNo, int masaDurum, int ID, int restoran_id, String restoran_adi, String restoran_adres, String restoran_tel, String restoran_vd, Cari cari_kayit, int ulke_id, int il_id, int ilce_id) {
        super(restoran_id, restoran_adi, restoran_adres, restoran_tel, restoran_vd, cari_kayit, ulke_id, il_id, ilce_id);
        this.masaNo = masaNo;
        this.masaDurum = masaDurum;
        this.ID = ID;
    }

    public int getMasaNo() {
        return masaNo;
    }

    public int getMasaDurum() {
        return masaDurum;
    }

    public int getID() {
        return ID;
    }

    public void setMasaNo(int masaNo) {
        this.masaNo = masaNo;
    }

    public void setMasaDurum(int masaDurum) {
        this.masaDurum = masaDurum;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

   

    

}
