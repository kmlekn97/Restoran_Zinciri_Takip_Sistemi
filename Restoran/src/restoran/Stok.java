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
public class Stok {

    private int stok_id;
    private double stok_miktar;
    private int malzeme_id;
    private int restoran_id;
    private Restoranlar restoran;
    private Malzeme_Kayit malzeme;

    public Stok(int stok_id, double stok_miktar, int malzeme_id, int restoran_id, Restoranlar restoran, Malzeme_Kayit malzeme) {
        this.stok_id = stok_id;
        this.stok_miktar = stok_miktar;
        this.malzeme_id = malzeme_id;
        this.restoran_id = restoran_id;
        this.restoran = restoran;
        this.malzeme = malzeme;
    }

    public int getStok_id() {
        return stok_id;
    }

    public double getStok_miktar() {
        return stok_miktar;
    }

    public int getMalzeme_id() {
        return malzeme_id;
    }

    public int getRestoran_id() {
        return restoran_id;
    }

    public Restoranlar getRestoran() {
        return restoran;
    }

    public Malzeme_Kayit getMalzeme() {
        return malzeme;
    }

    public void setStok_id(int stok_id) {
        this.stok_id = stok_id;
    }

    public void setStok_miktar(double stok_miktar) {
        this.stok_miktar = stok_miktar;
    }

    public void setMalzeme_id(int malzeme_id) {
        this.malzeme_id = malzeme_id;
    }

    public void setRestoran_id(int restoran_id) {
        this.restoran_id = restoran_id;
    }

    public void setRestoran(Restoranlar restoran) {
        this.restoran = restoran;
    }

    public void setMalzeme(Malzeme_Kayit malzeme) {
        this.malzeme = malzeme;
    }
}
