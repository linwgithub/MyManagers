package com.linw.mymanager.common.utils;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {

    private static final String LOG_TAG = FileUtil.class.getSimpleName();

    public static Boolean createPath(String pathString) {
        File path = new File(pathString);
        Boolean ret = true;

        if (!path.exists()) {
            if (!path.mkdirs()) {
                ret = false;
            }
        }
        return ret;
    }

    public static Boolean createFile(String fileString) {
        Boolean ret = true;
        File file = new File(fileString);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                ret = false;
            }
        }
        return ret;
    }

    /**
     * 拷贝文件
     *
     * @param fromFile 源文件
     * @param toFile   目标文件
     * @return 是否拷贝成功
     */
    public static Boolean copyfile(File fromFile, File toFile) {
        Boolean ret = false;
        if (!fromFile.exists()) {
            return ret;
        }

        if (!fromFile.isFile()) {
            return ret;
        }

        if (!fromFile.canRead()) {
            return ret;
        }

        if (!toFile.getParentFile().exists()) {
            toFile.getParentFile().mkdirs();
        }

        if (toFile.exists()) {
            toFile.delete();
        }

        try {
            FileInputStream fisfrom = new FileInputStream(fromFile);
            FileOutputStream fosto = new FileOutputStream(toFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fisfrom.read(buffer)) > 0) {
                fosto.write(buffer, 0, length);
            }
            fisfrom.close();
            fosto.close();
            ret = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * 拷贝文件
     *
     * @param fromFilePath 源文件路径
     * @param toFilePath   目标文件路径
     * @return 是否拷贝成功
     */
    public static Boolean copyfile(String fromFilePath, String toFilePath) {
        if (fromFilePath == null || toFilePath == null) {
            return false;
        }

        File fromFile = new File(fromFilePath);
        File toFile = new File(toFilePath);

        return copyfile(fromFile, toFile);
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(String filePath) {
        boolean bRet = false;
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            file.delete();
            bRet = true;
        }
        return bRet;
    }

    /**
     * 重命名文件
     */
    public static boolean moveFile(String srcFileName, String destFileName) {
        File srcFile = new File(srcFileName);
        if (!srcFile.exists() || !srcFile.isFile()) {
            return false;
        }
        return srcFile.renameTo(new File(destFileName));
    }

    /**
     * 将字符串写入文件
     */
    public static boolean writeStringToFile(String content, String filePath) {
        return writeDataToFile(content.getBytes(), filePath);
    }

    /**
     * 将数据写入文件
     */
    public static boolean writeDataToFile(byte[] data, String filePath) {
        if (data == null || data.length == 0) {
            return false;
        }

        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }

        boolean bRet = false;
        try {
            FileOutputStream fosto = new FileOutputStream(file);
            fosto.write(data);
            fosto.flush();
            fosto.close();
            bRet = true;
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage());
            e.printStackTrace();
        }

        return bRet;
    }

    /**
     * 从文件中读取数据
     */
    public static byte[] readContentFromFile(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return null;
        }

        byte[] buffer = null;

        try {
            FileInputStream fis = new FileInputStream(filePath);
            int length = fis.available();
            buffer = new byte[length];
            fis.read(buffer);
            fis.close();
        } catch (Exception ex) {
            Log.d(LOG_TAG, ex.getMessage());
            buffer = null;
        }

        return buffer;
    }

    /**
     * 从文件中读取字符串
     */
    public static String readContentStringFromFile(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return "";
        }

        String content = "";

        try {
            FileInputStream fis = new FileInputStream(filePath);
            int length = fis.available();
            byte[] buffer = new byte[length];
            fis.read(buffer);

            content = new String(buffer, "UTF-8");

            fis.close();
        } catch (Exception ex) {
            Log.d(LOG_TAG, ex.getMessage());
        }

        return content;
    }

    /**
     * 从asset文件中读取字符串
     */
    public static String readContentStringFromAssets(Context context, String fileName) {
        if (context == null || fileName == null || fileName.length() == 0) {
            return "";
        }

        try {
            InputStreamReader is = new InputStreamReader(context.getResources().getAssets().open(fileName));
            BufferedReader buf = new BufferedReader(is);
            String line = "";
            String result = "";
            while ((line = buf.readLine()) != null) {
                result += line;
            }
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    /**
     * 判断文件是否存在
     */
    public static boolean isFileExist(String filePath) {
        if (filePath == null || filePath.length() == 0) {
            return false;
        }

        File file = new File(filePath);
        if (file != null && file.isFile() == true && file.exists() == true) {
            return true;
        }

        return false;
    }

}
