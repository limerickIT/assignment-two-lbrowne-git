package com.sd4.utilities;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRGeneratorHandler {

    private static final int width = 400;
    private static final int height = 400;


    /** Creates & stores QR image.
     *       
     * 
     *      @param text       :       Text or content to store in image.
     *      @throws WriterException
     *      @throws IOException
     */
    public static byte[] GenerateQRCodeImage(String text)throws WriterException, IOException {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(text, BarcodeFormat.QR_CODE, width, height);
        BufferedImage bi = MatrixToImageWriter.toBufferedImage(bitMatrix);
        ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(bi, "JPG", bArrayOutputStream);
        return bArrayOutputStream.toByteArray();
    }
}