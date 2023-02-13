/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package restoran;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.Reader;
import com.google.zxing.Result;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

/**
 *
 * @author Kemal
 */
public class QR {

    public void QRWrite(String path) {
        try {

            ByteArrayOutputStream out = QRCode.from(path)
                    .to(ImageType.PNG).stream();

            String f_name = path;
            String Path_name = "";

            FileOutputStream fout = new FileOutputStream(new File(Path_name + (f_name + ".PNG")));
            fout.write(out.toByteArray());
            fout.flush();

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void QRread(JLabel resimlik, String filename) {
        try {

            InputStream barcodeInputStream = new FileInputStream(filename);
            ImageIcon imageIcon = new ImageIcon(new ImageIcon(filename).getImage().getScaledInstance(resimlik.getWidth(), resimlik.getHeight(), Image.SCALE_SMOOTH));
            resimlik.setIcon(imageIcon);
            BufferedImage barcBufferedImage = ImageIO.read(barcodeInputStream);

            LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Reader reader = new MultiFormatReader();
            Result reslut = reader.decode(bitmap);

            resimlik.setText(reslut.getText());

        } catch (Exception e) {
        }
    }

}
