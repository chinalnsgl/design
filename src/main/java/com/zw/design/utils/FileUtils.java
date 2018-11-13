package com.zw.design.utils;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Base64;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtils {

    public static boolean upload(MultipartFile file, String path) {
        if (!file.isEmpty()) {
            //取得当前上传文件的文件名称
            String fileName = file.getOriginalFilename();
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File saveFile = new File(path + fileName);
            try {
                file.transferTo(saveFile);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static void decodeBase64DataURLToImage(String dataURL, String path, String imgName) throws IOException {
        String base64 = dataURL.substring(dataURL.indexOf(",") + 1);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        FileOutputStream write = new FileOutputStream(new File(path + imgName));
        byte[] decoderBytes = Base64.getDecoder().decode(base64);
        write.write(decoderBytes);
        write.close();
    }

    public static void zipFileAll(List<File> files, ZipOutputStream outputStream) {
        for (File file : files) {
            zipFile(file, outputStream);
        }
    }

    private static void zipFile(File file, ZipOutputStream outputStream) {
        try {
            if (file.exists()) {
                if (file.isFile()) {
                    FileInputStream IN = new FileInputStream(file);
                    BufferedInputStream bins = new BufferedInputStream(IN, 1024);
                    // 将文件名写入压缩文件中
                    ZipEntry entry = new ZipEntry(file.getName());
                    outputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[1024];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, nNumber);
                    }
                    outputStream.flush();
                    bins.close();
                    IN.close();
                } else {
                    File[] files = file.listFiles();
                    for (File file1 : files) {
                        zipFile(file1, outputStream);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void downloadZip(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            try {
                File f = new File(file.getPath());
                f.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void downloadFile(File file, HttpServletResponse response) {
        try {
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(file.getPath()));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            response.reset();
            OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(file.getName(), "UTF-8"));
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

}
