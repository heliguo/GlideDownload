package com.example.glidedownload.utils;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * author:lgh on 2020-04-01 10:58
 */
public class FixFileNameUtils {

    /**
     * 通过文件路径直接修改文件名
     *
     * @param filePath    需要修改的文件的完整路径
     * @param newFileName 需要修改的文件的名称(注意后缀)
     * @return 修改文件名后的完整路径名（string）
     */
    public static String FixFileName(String filePath, String newFileName) {
        File f = new File(filePath);
        if (!f.exists()) { // 判断原文件是否存在（防止文件名冲突）
            return null;
        }
        newFileName = newFileName.trim();
        if ("".equals(newFileName) || newFileName == null) // 文件名不能为空
            return null;
        String newFilePath = null;
        newFilePath = filePath.substring(0, filePath.lastIndexOf("/")) + File.separator + newFileName;
        File nf = new File(newFilePath);
        try {
            f.renameTo(nf); // 修改文件名
        } catch (Exception err) {
            err.printStackTrace();
            return null;
        }
        return newFilePath;
    }

    /**
     * 复制文件（图片）
     *
     * @param srcpic 原文件名
     * @param despic 新文件名
     * @return 新文件路径
     */
    public static String copyPic(String srcpic, String despic) {
        if (srcpic.equals(despic)) {
            Log.e("FixFileNameUtils", "copyPic fialed");
            return null;
        }
        File file1 = new File(srcpic);
        File file2 = new File(despic);
        if (file2.exists()){
            Log.e("FixFileNameUtils", "despic exists");
            return null;
        }
        byte[] b = new byte[(int) file1.length()];
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            in = new FileInputStream(file1);
            //没有指定文件则会创建
            out = new FileOutputStream(file2);
            //read()--int，-1表示读取完毕
            while (in.read(b) != -1) {
                out.write(b);
            }
            out.flush();
            in.close();
            out.close();
            return despic;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
