package restoran;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Kemal
 */
public class Resim_Islem {

    public static String ResimEkle(JFileChooser chooser, JLabel resimlik, String filename,String director) {

        chooser = new JFileChooser();
        chooser.showOpenDialog(resimlik);
        File f = chooser.getSelectedFile();
        filename = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(resimlik.getWidth(), resimlik.getHeight(), Image.SCALE_SMOOTH));
        resimlik.setIcon(imageIcon);
        File yeni=null;
        try {
            FileInputStream fis = new FileInputStream(filename);
            BufferedImage image = ImageIO.read(fis);
            String yol=director;
            ImageIO.write(image, "jpg",yeni=new File(yol, getSifre(15)+f.getName()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        return yeni.getName();
    }

    public static void ResimGoruntule(JLabel resimlik, String filename) {
        File f = new File(filename);
        filename = f.getAbsolutePath();
        ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(resimlik.getWidth(), resimlik.getHeight(), Image.SCALE_SMOOTH));
        resimlik.setIcon(imageIcon);
    }

    public static void ResimSil(String filename) {
        File f = new File(filename);
        if (!f.exists()) { // eğer dosya yoksa
            System.out.println("Dosya bulunamadığından silinemedi");
        } else {
            f.delete(); // eğer dosyamız varsa.. // silme işlemi gerçekleştirir.
            System.out.println(f.getName() + " adlı dosya başarılı bir şekilde silinmiştir.");
        }
    }

    public static String getSifre(int n) {

        // rastgele harf seç
        String metin = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        //  StringBuffer olutşurun 
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++) {

            // metinden rastgele bir tane değer oku
            int index
                    = (int) (metin.length()
                    * Math.random());

            // sb değişkenine harfleri ekle
            sb.append(metin
                    .charAt(index));
        }

        return sb.toString();
    }
}
