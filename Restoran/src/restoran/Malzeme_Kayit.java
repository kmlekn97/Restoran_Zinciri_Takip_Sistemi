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
public class Malzeme_Kayit {
    private int malzeme_id;
    private String malzeme_adi;

    public Malzeme_Kayit(int malzeme_id, String malzeme_adi) {
        this.malzeme_id = malzeme_id;
        this.malzeme_adi = malzeme_adi;
    }

    public int getMalzeme_id() {
        return malzeme_id;
    }

    public void setMalzeme_id(int malzeme_id) {
        this.malzeme_id = malzeme_id;
    }

    public String getMalzeme_adi() {
        return malzeme_adi;
    }

    public void setMalzeme_adi(String malzeme_adi) {
        this.malzeme_adi = malzeme_adi;
    }
    
}
