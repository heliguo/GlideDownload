package com.example.glidedownload.utils;

import java.io.File;

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
}
