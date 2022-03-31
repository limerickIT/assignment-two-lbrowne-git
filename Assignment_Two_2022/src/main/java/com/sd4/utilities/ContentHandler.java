package com.sd4.utilities;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ContentHandler {
    private static final String ImageDir = new File("").getAbsolutePath() + "/src/main/resources/static/assets/images/";

    private static final String FileType = "JPG";

    /** Searches for an image and converts content to bytecode array
     * 
     * @param path  path to images
     * @return bytecode stream representation of image
     */
    public static byte[] GetImage(String path){
        try {
            String imagePath = ImageDir + path+"."+FileType;
            System.out.println(imagePath);

            File file = new File(imagePath);
            if(file.exists()){
                BufferedImage bi;
                    bi = ImageIO.read(file);
                
                ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
                ImageIO.write(bi, FileType, bArrayOutputStream);
                return bArrayOutputStream.toByteArray();
            }
            else{
                return null;
            }
            }
        catch (IOException e) {
            return null;
        }
    }

    public static byte[] GetCompressedFolder(String folder){

        if(folder.isEmpty()){
           folder =  ImageDir;
        }
        String sourceFile = folder;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(ImageDir+"BeerImages.zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceFile);
            CompressDirectoryToZip(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
            File file = new File(ImageDir+"BeerImages.zip");
            return Files.readAllBytes(file.toPath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void CompressDirectoryToZip(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException{
        if(fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                CompressDirectoryToZip(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }
}
