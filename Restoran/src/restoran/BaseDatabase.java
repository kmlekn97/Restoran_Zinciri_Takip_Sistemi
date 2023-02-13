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
public class BaseDatabase {
    
    
     private String connectonstrng;

    public BaseDatabase(String connectonstrng) {
        this.connectonstrng = connectonstrng;
    }

    public String getConnectonstrng() {
        return connectonstrng;
    }

    public void setConnectonstrng(String connectonstrng) {
        this.connectonstrng = connectonstrng;
    }
        public void add(Siparis siparis)
    {
        System.out.println("Default Database: "+siparis.getSiparisID());
    }
    
}
