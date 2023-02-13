/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author Kemal
 */
public class PDFTABLE {

    private com.itextpdf.text.Font font;
    private BaseFont arial = null;
    Document document = new Document();
    private float genel_toplam = 0;

    private String dosya_adi = null;

    NumberFormat currency = NumberFormat.getCurrencyInstance();

    public PDFTABLE(String dosyaadi) {

        try {
            PdfWriter.getInstance(document, new FileOutputStream(dosyaadi + ".pdf"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }
        dosya_adi = dosyaadi + ".pdf";
        document.open();
    }

    public void fontarrial() {

        try {
            arial = BaseFont.createFont("assets/arial.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void addTableIstatistikHeader(PdfPTable table) {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 13.0f);
        Stream.of("RESTORAN", "GÜNLÜK SATIŞ", "AYLIK SATIŞ", "YILLIK SATIŞ", "GENEL SATIŞ")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public void addTableCariHeader(PdfPTable table) {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 13.0f);
        Stream.of("RESTORAN", "Gelir", "Gider", "KDV", "NET KAR")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public void addTableMalzemeHeader(PdfPTable table) {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 10.0f);
        Stream.of("RESTORAN", "Malzeme Adı", "Malzeme Miktarı", "Fiyat", "Genel Toplam", "Tarih")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle, font));
                    table.addCell(header);
                });
    }

    public void addMalzemeRows(PdfPTable table, int restoran_id) throws DocumentException {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 13.0f);
        if (restoran_id == 0) {
            String restoran_adi = null;
            ArrayList<Malzeme> malzemeler = RestoranDB.Malzemeler.TümMalzemeleriListele();
            for (Malzeme item : malzemeler) {
                if (item.getFatura_durum() == 0) {
                    ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.RestoranlariListele(item.getRestoran_id());
                    for (Restoranlar itemres : restoran) {
                        restoran_adi = itemres.getRestoran_adi();
                    }
                    genel_toplam += item.getMalzeme_fiyat();
                    Stream.of(restoran_adi, item.getStok_kayit().getMalzeme_adi(), String.valueOf(item.getMalzeme_miktar()), String.valueOf(this.currency.format(item.getMalzeme_birim_fiyat())), String.valueOf(this.currency.format(item.getMalzeme_fiyat())), String.valueOf(item.getTarih()))
                            .forEach(columnTitle -> {
                                PdfPCell header = new PdfPCell();
                                header.setPhrase(new Phrase(columnTitle, font));
                                table.addCell(header);
                            });
                    RestoranDB.Malzemeler.Malzemedurumdegistir(1, item.getRestoran_id());
                }
            }
        } else {
            String restoran_adi = null;
            ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.RestoranlariListele(restoran_id);
            for (Restoranlar itemres : restoran) {
                restoran_adi = itemres.getRestoran_adi();
            }
            ArrayList<Malzeme> malzemeler = RestoranDB.Malzemeler.RestoranMalzemeleriListele(restoran_id);
            for (Malzeme item : malzemeler) {
                genel_toplam += item.getMalzeme_fiyat();
                Stream.of(restoran_adi, item.getStok_kayit().getMalzeme_adi(), String.valueOf(item.getMalzeme_miktar()), String.valueOf(this.currency.format(item.getMalzeme_birim_fiyat())), String.valueOf(this.currency.format(item.getMalzeme_fiyat())), String.valueOf(item.getTarih()))
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setPhrase(new Phrase(columnTitle, font));
                            table.addCell(header);
                        });
            }
            RestoranDB.Malzemeler.Malzemedurumdegistir(1, restoran_id);
        }
    }

    public void addIstatistikRows(PdfPTable table, int restoran_id) {
        if (restoran_id == 0) {
            ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.TümRestoranlaricarisizListele();
            for (Restoranlar item : restoran) {
                fontarrial();
                font = new com.itextpdf.text.Font(arial, 13.0f);
                Stream.of(item.getRestoran_adi(), String.valueOf(this.currency.format(RestoranDB.Istatistik.GunlukCiro(item.getRestoran_id()))), String.valueOf(this.currency.format(RestoranDB.Istatistik.AylikCiro(item.getRestoran_id()))), String.valueOf(this.currency.format(RestoranDB.Istatistik.YillikCiro(item.getRestoran_id()))), String.valueOf(this.currency.format(RestoranDB.Istatistik.ToplamCiro(item.getRestoran_id()))))
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setPhrase(new Phrase(columnTitle, font));
                            table.addCell(header);
                        });
            }
            table.addCell("GENEL TOPLAM");
            table.addCell(String.valueOf(this.currency.format(RestoranDB.Istatistik.GunlukCiro())));
            table.addCell(String.valueOf(this.currency.format(RestoranDB.Istatistik.AylikCiro())));
            table.addCell(String.valueOf(this.currency.format(RestoranDB.Istatistik.YillikCiro())));
            table.addCell(String.valueOf(this.currency.format(RestoranDB.Istatistik.ToplamCiro())));
        } else {
            String restoran_adi = null;
            ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.RestoranlariListele(restoran_id);
            for (Restoranlar item : restoran) {
                restoran_adi = item.getRestoran_adi();
            }
            fontarrial();
            font = new com.itextpdf.text.Font(arial, 13.0f);
            Stream.of(restoran_adi, String.valueOf(this.currency.format(RestoranDB.Istatistik.GunlukCiro(restoran_id))), String.valueOf(this.currency.format(RestoranDB.Istatistik.AylikCiro(restoran_id))), String.valueOf(this.currency.format(RestoranDB.Istatistik.YillikCiro(restoran_id))), String.valueOf(this.currency.format(RestoranDB.Istatistik.ToplamCiro(restoran_id))))
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
        }
    }

    public void addCariRows(PdfPTable table, int restoran_id) {
        if (restoran_id == 0) {
            ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.TümRestoranlaricarisizListele();
            for (Restoranlar itemres : restoran) {
                float gelir = 0, gider = 0;
                ArrayList<Cari> cari = RestoranDB.Cariler.CariListele(itemres.getRestoran_id());

                for (Cari item : cari) {
                    gelir += item.getCari_gelir();
                    gider += item.getCari_gider();
                }
                fontarrial();
                font = new com.itextpdf.text.Font(arial, 13.0f);
                Stream.of(itemres.getRestoran_adi(), String.valueOf(this.currency.format(gelir)), String.valueOf(this.currency.format(gider)), String.valueOf(this.currency.format(gelir * 0.18)), String.valueOf(this.currency.format(RestoranDB.Cariler.karHesapla(itemres.getRestoran_id()))))
                        .forEach(columnTitle -> {
                            PdfPCell header = new PdfPCell();
                            header.setPhrase(new Phrase(columnTitle, font));
                            table.addCell(header);
                        });
            }
            this.addKapananCariRows(table);
            float gelir = 0, gider = 0;
            ArrayList<Cari> cari = RestoranDB.Cariler.CariListele();

            for (Cari item : cari) {
                gelir += item.getCari_gelir();
                gider += item.getCari_gider();
            }
            ArrayList<Kapanan_Restoran> kapanancari = RestoranDB.Kapanan_Restoranlar.TümKapananRestoranlariListele();
            for (Kapanan_Restoran itemk : kapanancari) {
                gelir += itemk.getGelir();
                gider += itemk.getGider();
            }
            table.addCell("GENEL TOPLAM");
            table.addCell(String.valueOf(this.currency.format(gelir)));
            table.addCell(String.valueOf(this.currency.format(gider)));
            table.addCell(String.valueOf(this.currency.format(gelir * 0.18)));
            table.addCell(String.valueOf(this.currency.format(RestoranDB.Cariler.TümRestoranlarkarHesapla() + RestoranDB.Kapanan_Restoranlar.TümRestoranlarkarHesapla() - gelir * 0.18)));
        } else {

            float gelir = 0, gider = 0;
            String restoran_adi = null;
            ArrayList<Restoranlar> restoran = RestoranDB.Restoranlarim.RestoranlariListele(restoran_id);
            for (Restoranlar item : restoran) {
                restoran_adi = item.getRestoran_adi();
            }
            ArrayList<Cari> cari = RestoranDB.Cariler.CariListele(restoran_id);

            for (Cari item : cari) {
                gelir += item.getCari_gelir();
                gider += item.getCari_gider();
            }
            fontarrial();
            font = new com.itextpdf.text.Font(arial, 13.0f);
            Stream.of(restoran_adi, String.valueOf(this.currency.format(gelir)), String.valueOf(this.currency.format(gider)), String.valueOf(this.currency.format(gelir * 0.18)), String.valueOf(this.currency.format(RestoranDB.Cariler.karHesapla(restoran_id))))
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
        }
    }

    public void addKapananCariRows(PdfPTable table) {
        float gelir, gider;
        ArrayList<Kapanan_Restoran> restoran = RestoranDB.Kapanan_Restoranlar.TümKapananRestoranlariListele();
        for (Kapanan_Restoran itemres : restoran) {
            gelir = (float) itemres.getGelir();
            gider = (float) itemres.getGider();
            fontarrial();
            font = new com.itextpdf.text.Font(arial, 13.0f);
            Stream.of(itemres.getKapanan_restoran_ad(), String.valueOf(this.currency.format(gelir)), String.valueOf(this.currency.format(gider)), String.valueOf(this.currency.format(gelir * 0.18)), String.valueOf(this.currency.format(RestoranDB.Kapanan_Restoranlar.karHesapla(itemres.getKapanan_restoran_idBirinci()))))
                    .forEach(columnTitle -> {
                        PdfPCell header = new PdfPCell();
                        header.setPhrase(new Phrase(columnTitle, font));
                        table.addCell(header);
                    });
        }
    }

    public void dosyabaslik(String tittle) throws DocumentException {
        BaseFont arial = null;

        try {
            arial = BaseFont.createFont("assets/arial.ttf",
                    BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }

        com.itextpdf.text.Font font;
        font = new com.itextpdf.text.Font(arial, 30.0f);

        Paragraph paragraf = new Paragraph(tittle, font);
        paragraf.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
        document.add(paragraf);
        document.add(new Paragraph(" "));
    }

    public void dosyakapa() {
        document.close();

        try {
            File f = new File(dosya_adi);
            Desktop.getDesktop().open(f);

        } catch (Exception e) {
        }
    }

    public void createIstatistikReport(int restoran_id) {
        PdfPTable table = new PdfPTable(5);
        this.addTableIstatistikHeader(table);
        this.addIstatistikRows(table, restoran_id);
        try {
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createICariReport(int restoran_id) {

        PdfPTable table = new PdfPTable(5);
        this.addTableCariHeader(table);
        this.addCariRows(table, restoran_id);
        try {
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void createMalzemeFatura(int restoran_id) throws DocumentException {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 13.0f);
        PdfPTable table = new PdfPTable(6);
        this.addTableMalzemeHeader(table);
        this.addMalzemeRows(table, restoran_id);
        try {
            document.add(table);
            Paragraph bosluk = new Paragraph(" ", font);
            Paragraph ara_toplam = new Paragraph(("ARA TOPLAM=") + String.valueOf(this.currency.format(genel_toplam - genel_toplam * 0.18)), font);
            Paragraph kdv = new Paragraph(("KDV=") + String.valueOf(this.currency.format(genel_toplam * 0.18)), font);
            Paragraph toplam = new Paragraph(("GENEL TOPLAM=") + String.valueOf(this.currency.format(genel_toplam)), font);
            document.add(bosluk);
            document.add(ara_toplam);
            document.add(bosluk);
            document.add(kdv);
            document.add(bosluk);
            document.add(toplam);
        } catch (DocumentException ex) {
            Logger.getLogger(OptimizasyonIslem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void alicisaticibilgi(String tip, String firma_adi, String firma_adresi, String firma_tel, String firma_vd) throws DocumentException {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 13.0f);
        Paragraph baslik = new Paragraph(tip, font);
        Paragraph adi = new Paragraph("Firma Adı: " + firma_adi, font);
        Paragraph adres = new Paragraph("Firma Adresi: " + firma_adresi, font);
        Paragraph tel = new Paragraph("Firma TEL: " + firma_tel, font);
        Paragraph VD = new Paragraph("Firma V.D.: " + firma_vd, font);
        document.add(baslik);
        document.add(adi);
        document.add(adres);
        document.add(tel);
        document.add(VD);
    }

    public void createmenu(int restoran_id, String urun_tipi) throws DocumentException {
        fontarrial();
        font = new com.itextpdf.text.Font(arial, 18.0f);
        ArrayList<Menu> menu = RestoranDB.Menuler.MenuListele(urun_tipi, restoran_id);
        for (Menu item : menu) {
            Paragraph urun = new Paragraph(item.getUrunler().getUrunAdi() + "................................................." + currency.format(item.getUrunler().getUrunUcret()), font);
            urun.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            document.add(urun);
            font = new com.itextpdf.text.Font(arial, 9.0f);
            Paragraph ayrinti = new Paragraph(item.getUrunler().getUrunAyrinti(), font);
            ayrinti.setAlignment(com.itextpdf.text.Image.ALIGN_CENTER);
            document.add(ayrinti);
        }
    }

    public void addQR(String file) throws BadElementException, IOException {
        try {
            Image image = Image.getInstance(file);
            image.setAlignment(com.itextpdf.text.Image.ALIGN_RIGHT);
            document.add(image);
        } catch (DocumentException ex) {
            Logger.getLogger(PDFTABLE.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
