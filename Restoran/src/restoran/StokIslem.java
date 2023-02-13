/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Kemal
 */
public class StokIslem extends javax.swing.JFrame {

    /**
     * Creates new form StokIslem
     */
    private int calisanrestoran_id;
    private int calisankullanici_id;
    private int yetki;
    private int restoran_id = 0;
    private int malzeme_id;

    private DefaultTableModel model;

    public StokIslem(int kullanici_id) {
        initComponents();
        ArrayList<Calisanlar> kullanici = RestoranDB.Kullanicilar.KullanicilariListele(kullanici_id);
        for (Calisanlar item : kullanici) {
            calisanrestoran_id = item.getRestoran_id();
            calisankullanici_id = item.getKullanici_id();
            yetki = item.getYetki();
        }
        malzemeler();
        stok();
    }

    public void stok() {
        ArrayList<Malzeme_Kayit> malzeme = RestoranDB.Malzeme_Kayitlari.MalzemeleriListele();
        for (Malzeme_Kayit itemmalzeme : malzeme) {
            ArrayList<Restoranlar> restoran;
            if (yetki == 5) {
                restoran = RestoranDB.Restoranlarim.TümRestoranlaricarisizListele();
            } else {
                restoran = RestoranDB.Restoranlarim.RestoranlaricarisizListele(calisanrestoran_id);
            }
            for (Restoranlar itemrestoran : restoran) {
                ArrayList<Stok> stoklarim;
                if (yetki == 5) {
                    stoklarim = RestoranDB.Stoklarim.TümRestoranlarinToplamStokListele(itemrestoran.getRestoran_id(), itemmalzeme.getMalzeme_id());
                } else {
                    stoklarim = RestoranDB.Stoklarim.TümRestoranlarinToplamStokListele(calisanrestoran_id, itemmalzeme.getMalzeme_id());
                }
                for (Stok item : stoklarim) {
                    if (item.getStok_miktar() != 0) {
                        model = (DefaultTableModel) jTable1.getModel();
                        Object[] row = new Object[3];
                        row[0] = item.getRestoran().getRestoran_adi();
                        row[1] = item.getMalzeme().getMalzeme_adi();
                        row[2] = item.getStok_miktar();
                        model.addRow(row);
                    }
                }
            }
        }
    }

    public void malzemeler() {
        ArrayList<Malzeme_Kayit> malzeme = RestoranDB.Malzeme_Kayitlari.MalzemeleriListele();
        for (Malzeme_Kayit item : malzeme) {
            jComboBox1.addItem(item.getMalzeme_adi());
        }
    }

    public void malzemeadibul() {
        String ad = jComboBox1.getSelectedItem().toString();
        ArrayList<Malzeme_Kayit> malzeme = RestoranDB.Malzeme_Kayitlari.MalzemeListelead(ad);
        for (Malzeme_Kayit item : malzeme) {
            malzeme_id = item.getMalzeme_id();
        }
    }

    public void restoranadibul() {
        String ad = jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString();
        ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.RestoranlariListelead(ad);
        for (Restoranlar item : restoran) {
            restoran_id = item.getRestoran_id();
        }
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
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("STOKLAR");
        setResizable(false);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Restoran Adı", "Malzeme Adı", "Malzeme Miktarı"
            }
        ));
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Miktar:");

        jButton1.setText("EKLE");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("DÜŞ");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("GERİ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });

        jButton4.setText("STOK KAYITLARI");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel2.setText("Malzeme:");

        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton3)
                .addGap(29, 29, 29))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTextField2)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(240, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(159, 159, 159)
                .addComponent(jButton2)
                .addGap(141, 141, 141)
                .addComponent(jButton4)
                .addGap(184, 184, 184))
            .addGroup(layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(197, 197, 197)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton4))
                .addGap(36, 36, 36)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 404, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        if (yetki == 5) {
            YoneticiForm yonetici = new YoneticiForm(calisankullanici_id);
            yonetici.show();
            this.hide();
        } else {
            RestoranYonetimIslem yonetim = new RestoranYonetimIslem(calisankullanici_id);
            yonetim.show();
            this.hide();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        StokKayitlari stokgenel = new StokKayitlari(calisankullanici_id, restoran_id);
        stokgenel.show();
        this.hide();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
        model.getDataVector().clear();
        ArrayList<Malzeme_Kayit> malzeme = RestoranDB.Malzeme_Kayitlari.MalzemeleriListele();
        for (Malzeme_Kayit itemmalzeme : malzeme) {
            ArrayList<Restoranlar> restoran;
            if (yetki == 5) {
                restoran = RestoranDB.Restoranlarim.TümRestoranlaricarisizListele();
            } else {
                restoran = RestoranDB.Restoranlarim.RestoranlaricarisizListele(calisanrestoran_id);
            }
            for (Restoranlar itemrestoran : restoran) {
                ArrayList<Stok> stoklarim;
                if (yetki == 5) {
                    stoklarim = RestoranDB.Stoklarim.TümRestoranlarinToplamStokListeleAra(itemrestoran.getRestoran_id(), itemmalzeme.getMalzeme_id(), jTextField2.getText());
                } else {
                    stoklarim = RestoranDB.Stoklarim.TümRestoranlarinToplamStokListeleAra(calisanrestoran_id, itemmalzeme.getMalzeme_id(), jTextField2.getText());
                }
                for (Stok item : stoklarim) {
                    if (item.getStok_miktar() != 0) {
                        model = (DefaultTableModel) jTable1.getModel();
                        Object[] row = new Object[3];
                        row[0] = item.getRestoran().getRestoran_adi();
                        row[1] = item.getMalzeme().getMalzeme_adi();
                        row[2] = item.getStok_miktar();
                        model.addRow(row);
                    }
                }
            }
        }
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        // TODO add your handling code here:
        restoranadibul();
    }//GEN-LAST:event_jTable1MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        malzemeadibul();
        if (model != null) {
            model.getDataVector().clear();
        }
        if (yetki == 4) {
            RestoranDB.Stoklarim.Stok_Kayit(Double.parseDouble(jTextField1.getText()), malzeme_id, calisanrestoran_id);
        } else {
            RestoranDB.Stoklarim.Stok_Kayit(Double.parseDouble(jTextField1.getText()), malzeme_id, restoran_id);
        }
        stok();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        // TODO add your handling code here:
        malzemeadibul();
    }//GEN-LAST:event_jComboBox1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        malzemeadibul();
        model.getDataVector().clear();
        if (yetki == 4) {
            RestoranDB.Stoklarim.StokDus(Double.parseDouble(jTextField1.getText()), malzeme_id, calisanrestoran_id);
        } else {
            RestoranDB.Stoklarim.StokDus(Double.parseDouble(jTextField1.getText()), malzeme_id, restoran_id);
        }
        stok();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(StokIslem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StokIslem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StokIslem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StokIslem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new StokIslem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
