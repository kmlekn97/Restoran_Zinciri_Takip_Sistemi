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
public class SiparisManager {
     private BaseDatabase baseproductDao;

    public SiparisManager(BaseDatabase baseproductDao) {
        this.baseproductDao = baseproductDao;
    }
   public void add(Siparis siparis)
    {
        baseproductDao.add(siparis);
        System.out.println("Ürün İşkatmanı işlemleri");
        System.out.println("Ürün İsmi:"+siparis.getSiparisMasa());
    }
    
}
