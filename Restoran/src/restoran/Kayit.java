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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class Kayit {
    
    private int kayitID = 0;
    private Date kayitZaman;
    private String kayitIslem = null;
    
    public Kayit(int kayitid, Date kayitzaman, String kayitislem)
    {
        this.kayitID = kayitid;
        this.kayitZaman = kayitzaman;
        this.kayitIslem = kayitislem;
    }

    public int getKayitID() {
        return kayitID;
    }

    public void setKayitID(int kayitID) {
        this.kayitID = kayitID;
    }

    public Date getKayitZaman() {
        return kayitZaman;
    }

    public void setKayitZaman(Date kayitZaman) {
        this.kayitZaman = kayitZaman;
    }

    public String getKayitIslem() {
        return kayitIslem;
    }

    public void setKayitIslem(String kayitIslem) {
        this.kayitIslem = kayitIslem;
    }
    
}
