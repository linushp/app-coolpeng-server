//package com.coolpeng.framework.utils;
//
//import com.sun.image.codec.jpeg.JPEGImageEncoder;
//import com.sun.image.codec.jpeg.JPEGCodec;
//import com.sun.image.codec.jpeg.JPEGEncodeParam;
//
//import javax.imageio.ImageIO;
//import javax.swing.*;
//import java.io.*;
//import java.awt.*;
//import java.awt.image.BufferedImage;
//import java.awt.image.Kernel;
//import java.awt.image.ConvolveOp;
//
//public class ImageUtil {
//
//    public static void resize(File originalFile, File resizedFile,
//            int newWidth, float quality) throws IOException {
//
//        if (quality > 1) {
//            throw new IllegalArgumentException(
//                    "Quality has to be between 0 and 1");
//        }
//
//        ImageIcon ii = new ImageIcon(originalFile.getCanonicalPath());
//        Image i = ii.getImage();
//        Image resizedImage = null;
//
//        int iWidth = i.getWidth(null);
//        int iHeight = i.getHeight(null);
//
//        if (iWidth > iHeight) {
//            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
//                    / iWidth, Image.SCALE_SMOOTH);
//        } else {
//            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
//                    newWidth, Image.SCALE_SMOOTH);
//        }
//
//        // This code ensures that all the pixels in the image are loaded.
//        Image temp = new ImageIcon(resizedImage).getImage();
//
//        // Create the buffered image.
//        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
//                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
//
//        // Copy image to buffered image.
//        Graphics g = bufferedImage.createGraphics();
//
//        // Clear background and paint the image.
//        g.setColor(Color.white);
//        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
//        g.drawImage(temp, 0, 0, null);
//        g.dispose();
//
//        // Soften.
//        float softenFactor = 0.05f;
//        float[] softenArray = { 0, softenFactor, 0, softenFactor,
//                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
//        Kernel kernel = new Kernel(3, 3, softenArray);
//        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
//        bufferedImage = cOp.filter(bufferedImage, null);
//
//        // Write the jpeg to a file.
//        FileOutputStream out = new FileOutputStream(resizedFile);
//
//        // Encodes image as a JPEG data stream
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//
//        JPEGEncodeParam param = encoder
//                .getDefaultJPEGEncodeParam(bufferedImage);
//
//        param.setQuality(quality, true);
//
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(bufferedImage);
//    } // Example usage
//
//
//    public static InputStream resize(InputStream inputStream,
//                         int newWidth, float quality) throws IOException{
//
//
//        if (quality > 1) {
//            throw new IllegalArgumentException(
//                    "Quality has to be between 0 and 1");
//        }
//        Image image =  ImageIO.read(inputStream);
//        ImageIcon ii = new ImageIcon(image);
//        Image i = ii.getImage();
//        Image resizedImage = null;
//
//        int iWidth = i.getWidth(null);
//        int iHeight = i.getHeight(null);
//
//        if (newWidth > iWidth){
//            newWidth = iWidth;
//        }
//
//        if (iWidth > iHeight) {
//            resizedImage = i.getScaledInstance(newWidth, (newWidth * iHeight)
//                    / iWidth, Image.SCALE_SMOOTH);
//        } else {
//            resizedImage = i.getScaledInstance((newWidth * iWidth) / iHeight,
//                    newWidth, Image.SCALE_SMOOTH);
//        }
//
//        // This code ensures that all the pixels in the image are loaded.
//        Image temp = new ImageIcon(resizedImage).getImage();
//
//        // Create the buffered image.
//        BufferedImage bufferedImage = new BufferedImage(temp.getWidth(null),
//                temp.getHeight(null), BufferedImage.TYPE_INT_RGB);
//
//        // Copy image to buffered image.
//        Graphics g = bufferedImage.createGraphics();
//
//        // Clear background and paint the image.
//        g.setColor(Color.white);
//        g.fillRect(0, 0, temp.getWidth(null), temp.getHeight(null));
//        g.drawImage(temp, 0, 0, null);
//        g.dispose();
//
//        // Soften.
//        float softenFactor = 0.05f;
//        float[] softenArray = { 0, softenFactor, 0, softenFactor,
//                1 - (softenFactor * 4), softenFactor, 0, softenFactor, 0 };
//        Kernel kernel = new Kernel(3, 3, softenArray);
//        ConvolveOp cOp = new ConvolveOp(kernel, ConvolveOp.EDGE_NO_OP, null);
//        bufferedImage = cOp.filter(bufferedImage, null);
//
//        // Write the jpeg to a file.
//        ByteArrayOutputStream out = new ByteArrayOutputStream();
//
//        // Encodes image as a JPEG data stream
//        JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
//
//        JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bufferedImage);
//
//        param.setQuality(quality, true);
//
//        encoder.setJPEGEncodeParam(param);
//        encoder.encode(bufferedImage);
//
//        out.flush();
//        out.close();
//
//        ByteArrayInputStream inputStream2 = new ByteArrayInputStream(out.toByteArray());
//
//        return inputStream2;
//    }
//
//    public static void main(String[] args) throws IOException {
////       File originalImage = new File("C:\\11.jpg");
////       resize(originalImage, new File("c:\\11-0.jpg"),150, 0.7f);
////       resize(originalImage, new File("c:\\11-1.jpg"),150, 1f);
//         File originalImage = new File("D:\\龙珠图片\\WallPaper\\26699.jpg");
//         resize(originalImage, new File("c:\\1207-0.jpg"),250, 0.7f);
//         resize(originalImage, new File("c:\\1207-1.jpg"),250, 1f);
//    }
//}