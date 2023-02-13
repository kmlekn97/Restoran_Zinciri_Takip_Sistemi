/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import static com.mysql.cj.conf.PropertyKey.logger;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.Timer;

/**
 *
 * @author Kemal
 */
public class GarsonBildirimForm extends javax.swing.JFrame {

    /**
     * Creates new form GarsonBildirimForm
     */
    private int calisanrestoran_id;
    private int calisankullanici_id;
    private int kullanici_ids;

    public GarsonBildirimForm(int kullanici_id) {
        initComponents();
        ArrayList<Calisanlar> kullanici = RestoranDB.Kullanicilar.KullanicilariListele(kullanici_id);
        for (Calisanlar item : kullanici) {
            jLabel1.setText(item.getCalisan_adi() + " " + item.getCalisan_soyadi());
            calisanrestoran_id = item.getRestoran_id();
            kullanici_ids = item.getKullaniciId();
        }
        HazırSiparisYerlestir();
        TamamlananSiparisYerlestir();
        HazırlananSiparisYerlestir();
        AlınanSiparisYerlestir();
        GönderilenSiparisYerlestir();
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //...Perform a task...

            }
        };
        Timer timer = new Timer(10000, new MyTimerActionListener());

        timer.setRepeats(true);
        timer.start();
    }
    private int kullanici_id = -1;

    class MyTimerActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            model.clear();
            HazırSiparisYerlestir();
            TamamlananSiparisYerlestir();
            HazırlananSiparisYerlestir();
            AlınanSiparisYerlestir();
            GönderilenSiparisYerlestir();
        }
    }
    NumberFormat currency = NumberFormat.getCurrencyInstance();
    DefaultListModel model = new DefaultListModel();

    public void GönderilenSiparisYerlestir() {
        ArrayList<Siparis> siparisler2 = RestoranDB.Siparisler.SiparisleriYerlestir(0, calisanrestoran_id);
        for (Siparis item : siparisler2) {
            String metin = item.getSiparisMasa() + " " + item.getSiparisAdet() + " adet " + item.getSiparisUrun().getUrunAdi() + " ürün ücreti = " + (currency.format(item.getSiparisAdet() * item.getSiparisUrun().getUrunUcret())) + "(" + currency.format(item.getSiparisUrun().getUrunUcret()) + ")  Gönderildi";
            model.addElement(metin + "\r\n");
        }
        jList1.setModel(model);
    }

    public void AlınanSiparisYerlestir() {
        ArrayList<Siparis> siparisler2 = RestoranDB.Siparisler.SiparisleriYerlestir(1, calisanrestoran_id);
        for (Siparis item : siparisler2) {
            String metin = item.getSiparisMasa() + " " + item.getSiparisAdet() + " adet " + item.getSiparisUrun().getUrunAdi() + " ürün ücreti = " + (currency.format(item.getSiparisAdet() * item.getSiparisUrun().getUrunUcret())) + "(" + currency.format(item.getSiparisUrun().getUrunUcret()) + ")  Alındı";
            model.addElement(metin + "\r\n");
        }
        jList1.setModel(model);
    }

    public void HazırlananSiparisYerlestir() {
        ArrayList<Siparis> siparisler2 = RestoranDB.Siparisler.SiparisleriYerlestir(2, calisanrestoran_id);
        for (Siparis item : siparisler2) {
            String metin = item.getSiparisMasa() + " " + item.getSiparisAdet() + " adet " + item.getSiparisUrun().getUrunAdi() + " ürün ücreti = " + (currency.format(item.getSiparisAdet() * item.getSiparisUrun().getUrunUcret())) + "(" + currency.format(item.getSiparisUrun().getUrunUcret()) + ")  Hazırlanıyor";
            model.addElement(metin + "\r\n");
        }
        jList1.setModel(model);
    }

    public void HazırSiparisYerlestir() {
        ArrayList<Siparis> siparisler2 = RestoranDB.Siparisler.SiparisleriYerlestir(3, calisanrestoran_id);
        for (Siparis item : siparisler2) {
            String metin = item.getSiparisMasa() + " " + item.getSiparisAdet() + " adet " + item.getSiparisUrun().getUrunAdi() + " ürün ücreti = " + (currency.format(item.getSiparisAdet() * item.getSiparisUrun().getUrunUcret())) + "(" + currency.format(item.getSiparisUrun().getUrunUcret()) + ")  Hazırlandı";
            model.addElement(metin + "\r\n");
        }
        jList1.setModel(model);
    }

    public void TamamlananSiparisYerlestir() {
        ArrayList<Siparis> siparisler2 = RestoranDB.Siparisler.SiparisleriYerlestir(4, calisanrestoran_id);
        for (Siparis item : siparisler2) {
            String metin = item.getSiparisMasa() + " " + item.getSiparisAdet() + " adet " + item.getSiparisUrun().getUrunAdi() + " ürün ücreti = " + (currency.format(item.getSiparisAdet() * item.getSiparisUrun().getUrunUcret())) + "(" + currency.format(item.getSiparisUrun().getUrunUcret()) + ")  Teslim Edildi";
            model.addElement(metin + "\r\n");
        }
        jList1.setModel(model);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Garson Bildirim");

        jScrollPane1.setViewportView(jList1);

        jButton1.setText("Geri");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setText("jLabel1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jButton1))
                .addGap(57, 57, 57))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(5, 5, 5)
                .addComponent(jButton1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        GarsonForm garson = new GarsonForm(kullanici_ids);
        garson.show();
        this.hide();
    }//GEN-LAST:event_jButton1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GarsonBildirimForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GarsonBildirimForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GarsonBildirimForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GarsonBildirimForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new GarsonBildirimForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
