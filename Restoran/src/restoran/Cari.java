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
public class Cari {
    
    private int cari_Id;
    private double cari_gelir;
    private double cari_gider;
    private int restoran_Id;

    public Cari(int cari_Id, double cari_gelir, double cari_gider, int restoran_Id) {
        this.cari_Id = cari_Id;
        this.cari_gelir = cari_gelir;
        this.cari_gider = cari_gider;
        this.restoran_Id = restoran_Id;
    }

    public int getCari_Id() {
        return cari_Id;
    }

    public void setCari_Id(int cari_Id) {
        this.cari_Id = cari_Id;
    }

    public double getCari_gelir() {
        return cari_gelir;
    }

    public void setCari_gelir(double cari_gelir) {
        this.cari_gelir = cari_gelir;
    }

    public double getCari_gider() {
        return cari_gider;
    }

    public void setCari_gider(double cari_gider) {
        this.cari_gider = cari_gider;
    }

    public int getRestoran_Id() {
        return restoran_Id;
    }

    public void setRestoran_Id(int restoran_Id) {
        this.restoran_Id = restoran_Id;
    }

    
    
    




    
}
