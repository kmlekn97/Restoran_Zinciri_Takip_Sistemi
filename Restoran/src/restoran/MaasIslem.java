/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.Scrollable;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Kemal
 */
public class MaasIslem extends javax.swing.JFrame {

    /**
     * Creates new form MaasIslem
     */
    public CheckBoxGroup liste;
    private int calisanrestoran_id;
    private int calisankullanici_id;
    private int yetki;
    ArrayList<Calisanlar> calisan;
    
    NumberFormat currency = NumberFormat.getCurrencyInstance();

    public MaasIslem(int kullanici_id) {
        initComponents();
        ArrayList<Calisanlar> kullanici = RestoranDB.Kullanicilar.KullanicilariListele(kullanici_id);
        for (Calisanlar item : kullanici) {
            calisanrestoran_id = item.getRestoran_id();
            calisankullanici_id = item.getKullanici_id();
            yetki = item.getYetki();
        }
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(0, 4));
        int i = -1;
        if (yetki == 5) {
            calisan = RestoranDB.Calisanlarim.CalisanListele();
        } else {
            calisan = RestoranDB.Calisanlarim.CalisanListeleRestoran(calisanrestoran_id);
        }
        String[] calisanListe = new String[calisan.size() + 1];
        calisanListe[calisanListe.length - 1] = "Son Bu Kayıt Boştur...";
        for (Calisanlar item : calisan) {
            i++;
            if (i >= calisanListe.length) {

            } else {
                calisanListe[i] = item.getCalisan_adi() + " " + item.getCalisan_soyadi() + " ( " + currency.format(item.getCalisan_maas()) + " ) [ " + item.getCalisan_id() + " ]";
            }
        }
        liste = new CheckBoxGroup(calisanListe);
        this.add(liste);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    public class CheckBoxGroup extends JPanel {

        private JCheckBox all;
        private List<JCheckBox> checkBoxes;
        private ArrayList<String> selected = new ArrayList<String>();
        public JCheckBox cb;

        public CheckBoxGroup(String... options) {
            checkBoxes = new ArrayList<>();
            setLayout(new BorderLayout());
            JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 1, 1));
            all = new JCheckBox("Select All...");
            all.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (JCheckBox cb : checkBoxes) {
                        cb.setSelected(all.isSelected());
                    }
                }
            });
            header.add(all);
            add(header, BorderLayout.NORTH);

            JPanel content = new JPanel(new GridBagLayout());
            content.setBackground(UIManager.getColor("List.background"));
            if (options.length > 0) {

                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridwidth = GridBagConstraints.REMAINDER;
                gbc.anchor = GridBagConstraints.NORTHWEST;
                gbc.weightx = 1;
                for (int index = 0; index < options.length - 1; index++) {
                    JCheckBox cb = new JCheckBox(options[index]);
                    cb.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            selected.add(cb.getText());
                        }
                    });
                    cb.setOpaque(true);
                    if (index >= options.length) {
                        cb.hide();
                    }
                    checkBoxes.add(cb);
                    content.add(cb, gbc);
                }
                cb = new JCheckBox(options[options.length - 1]);
                cb.setOpaque(false);
                checkBoxes.add(cb);
                gbc.weighty = 1;
                content.add(cb, gbc);
            }

            add(new JScrollPane(content));
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

        jPanel1 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MAAŞ");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jButton1.setText("Geri");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("ÖDE");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("ZAM");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel1.setText("Zam Yüzdesi:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jButton1)
                                .addGap(26, 26, 26)
                                .addComponent(jButton3)
                                .addGap(26, 26, 26)
                                .addComponent(jButton2)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addGap(30, 30, 30)
                        .addComponent(jTextField2)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(756, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 224, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        Object[] noSaveOption = {"Evet", "Hayır"}; // Obje Dizisi Oluşturduk

        String message = "MAAŞLARI ÖDEMEK İstediğinize Emin Misiniz?"; // Verilecek Mesaj

        String title = "Dikkat!!"; // Pencere Başlığı
        int noSave = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, noSaveOption, null);

        if (noSave == JOptionPane.YES_OPTION) {
            // Kullanıcı Evet'e Bastıysa
            String deger = "";
            ArrayList<Integer> calisanids = new ArrayList<Integer>();
            ArrayList<Integer> calisan_ID = new ArrayList<Integer>();
            for (int i = 0; i < liste.selected.size(); i++) {
                deger = "";
                deger = String.valueOf(liste.selected.get(i).substring(liste.selected.get(i).toString().indexOf("[", deger.length() - 1) + 1) + " ");
                deger = deger.replace("]", " ");
                calisanids.add(Integer.parseInt(deger.trim()));
            }
            for (Integer calisan : calisanids) {
                if (!calisan_ID.contains(calisan)) {
                    calisan_ID.add(calisan);
                } else {
                    calisan_ID.clear();
                    calisan_ID.add(calisan);
                }
            }
            for (int i = 0; i < calisan_ID.size(); i++) {
                RestoranDB.Calisanlarim.calisanMaasOde(calisan_ID.get(i));
            }
            calisanids.clear();

        } else if (noSave == JOptionPane.NO_OPTION) {
            // Kullanıcı Hayır'ya Bastıysa
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
        liste.removeAll();
        this.remove(liste);
        ArrayList<Calisanlar> calisanlar;
        int i = -1;
        if (yetki == 5) {
            calisanlar = RestoranDB.Calisanlarim.CalisanMaasAra(jTextField1.getText(), 0);
        } else {
            calisanlar = RestoranDB.Calisanlarim.CalisanMaasAra(jTextField1.getText(), calisanrestoran_id);
        }

        String[] calisanListe = new String[calisanlar.size() + 1];
        calisanListe[calisanListe.length - 1] = "Son Bu Kayıt Boştur...";
        for (Calisanlar item : calisanlar) {
            i++;
            if (i >= calisanListe.length) {

            } else {
                calisanListe[i] = item.getCalisan_adi() + " " + item.getCalisan_soyadi() + " ( " + item.getCalisan_maas() + " TL) [ " + item.getCalisan_id() + " ]";
            }
        }
        liste = new CheckBoxGroup(calisanListe);
        this.add(liste);
        this.setVisible(true);
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Object[] noSaveOption = {"Evet", "Hayır"}; // Obje Dizisi Oluşturduk

        String message = "ZAM Yapmak İstediğinize Emin Misiniz?"; // Verilecek Mesaj

        String title = "Dikkat!!"; // Pencere Başlığı
        int noSave = JOptionPane.showOptionDialog(null, message, title, JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, noSaveOption, null);

        if (noSave == JOptionPane.YES_OPTION) {
            // Kullanıcı Evet'e Bastıysa
            String deger = "";
            ArrayList<Integer> calisanids = new ArrayList<Integer>();
            ArrayList<Integer> calisan_ID = new ArrayList<Integer>();
            for (int i = 0; i < liste.selected.size(); i++) {
                deger = "";
                deger = String.valueOf(liste.selected.get(i).substring(liste.selected.get(i).toString().indexOf("[", deger.length() - 1) + 1) + " ");
                deger = deger.replace("]", " ");
                calisanids.add(Integer.parseInt(deger.trim()));
            }
            for (Integer calisan : calisanids) {
                if (!calisan_ID.contains(calisan)) {
                    calisan_ID.add(calisan);
                } else {
                    calisan_ID.clear();
                    calisan_ID.add(calisan);
                }
            }
            for (int i = 0; i < calisan_ID.size(); i++) {
                RestoranDB.Calisanlarim.Zamyap(Double.parseDouble(jTextField2.getText()), calisan_ID.get(i));
            }
            calisanids.clear();

        } else if (noSave == JOptionPane.NO_OPTION) {
            // Kullanıcı Hayır'ya Bastıysa
        }

    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(MaasIslem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MaasIslem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MaasIslem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MaasIslem.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                //new MaasIslem().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
