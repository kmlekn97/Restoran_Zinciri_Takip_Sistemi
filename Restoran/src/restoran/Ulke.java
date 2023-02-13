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
public class Ulke {
    
    private String name;
    private int Id;
    private String kod;
    private String phonekod;

    public Ulke(String name, int Id, String kod, String phonekod) {
        this.name = name;
        this.Id = Id;
        this.kod = kod;
        this.phonekod = phonekod;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return Id;
    }

    public String getKod() {
        return kod;
    }

    public String getPhonekod() {
        return phonekod;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public void setPhonekod(String phonekod) {
        this.phonekod = phonekod;
    }
    
}
