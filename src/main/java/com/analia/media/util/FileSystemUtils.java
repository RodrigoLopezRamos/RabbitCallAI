package com.analia.media.util;

import com.analia.common.exception.AnaliaException;
import com.analia.common.exception.ExceptionCode;
import com.analia.media.util.ImageUtil.ImageSize;
import jakarta.ws.rs.core.MultivaluedMap;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.math.BigInteger;
import java.util.Date;

public class FileSystemUtils {
    /**
     * @param input_stream
     * @return
     */
    public static byte[] convertInputToByteArray(InputStream input_stream) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        int bytesRead;
        try {
            while ((bytesRead = input_stream.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return output.toByteArray();
    }

    /**
     * @param inputImageFile for instance 3/fileName
     * @return
     * */
    public static boolean resizeImage(byte[] imageInByte, String inputImageFile, String mediaFolder) throws AnaliaException {
        try {
            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bufferedImage = ImageIO.read(in);
            String inputFileExtension = inputImageFile.substring(inputImageFile.lastIndexOf('.'));
            String path = mediaFolder.concat(inputImageFile).concat(File.separator);
            File folder = new File(path);
            folder.mkdirs();

            for (ImageSize imageSize : ImageSize.values()) {
                BufferedImage smallBufferedImage = ImageUtil.resizeImage(bufferedImage, imageSize);
                File file = new File(folder, imageSize.name().toLowerCase().concat(inputFileExtension));
                file.createNewFile();
                ImageIO.write(smallBufferedImage, inputFileExtension.replace(".", ""), file);
            }
            return true;
        } catch (IOException e) {
            throw new AnaliaException(ExceptionCode.SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * @param inputStream
     * @param directoryId
     * @param fileName
     * @param mediaFolder
     * @return
     * @throws AnaliaException
     */
    public static String saveFileOnFileSystem(InputStream inputStream, BigInteger directoryId, String fileName, String mediaFolder) throws AnaliaException {
        try {
            String folderPath = mediaFolder.concat(String.valueOf(directoryId));
            String outPutFilePath = mediaFolder.concat(String.valueOf(directoryId)).concat(File.separator).concat(fileName);
            File folder = new File(folderPath);
            File outPutFile = new File(outPutFilePath);
            if (!folder.exists()) {
                folder.mkdirs();
            }
            OutputStream outputStream = new FileOutputStream(outPutFile);
            int read = 0;
            byte[] bytes = new byte[1024];

            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.close();

            return outPutFilePath;
        } catch (IOException e) {
            throw new AnaliaException(ExceptionCode.SERVER_ERROR, e.getMessage(), e);
        }
    }

    /**
     * @param header
     * @return
     */
    public static String getFileName(MultivaluedMap<String, String> header) {

        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "default".concat(new Date().toString());
    }

    public static boolean isMediaAnImage(MultivaluedMap<String, String> header) {
        boolean isImage = false;
        String contentType = header.getFirst("Content-Type");
        if (contentType.contains("image")) {
            isImage = true;
        }
        return isImage;
    }


    public static void writeAndResizeFile(byte[] content, String filename, String mediaFolder) throws AnaliaException {
        FileSystemUtils.resizeImage(content, filename, mediaFolder);
    }


    public static String writeFile(byte[] imageInByte, int galleryId, String inputImageFile, String mediaFolder) throws AnaliaException {
        try {
            if (imageInByte == null || imageInByte.length == 0) {
                throw new AnaliaException(ExceptionCode.SERVER_ERROR, "Image Bytes is empty!");
            }

            InputStream in = new ByteArrayInputStream(imageInByte);
            BufferedImage bufferedImage = ImageIO.read(in);
            String inputFileExtension = inputImageFile.substring(inputImageFile.lastIndexOf('.'));
            String path = mediaFolder + galleryId;

            File folder = new File(path);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            BufferedImage smallBufferedImage = ImageUtil.resizeImage(bufferedImage, ImageSize.SMALL);
            File file = new File(folder, inputImageFile);
            file.createNewFile();
            ImageIO.write(smallBufferedImage, inputFileExtension.replace(".", ""), file);
            return (galleryId + File.separator + inputImageFile);
        } catch (IOException e) {
            throw new AnaliaException(ExceptionCode.SERVER_CRITICAL_ERROR, e.getMessage(), e);
        }
    }

}
