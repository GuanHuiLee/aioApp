package com.zgg.commonlibrary.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;


/**
 * ================================================
 * 作    者：loujingying@aliyun.com
 * 版    本：1.0.0
 * 创建日期：2017/8/4
 * 描    述：文件工具类
 * 修订历史：
 * ================================================
 */

public class FileUtils {

    private static final String TAG = "FileUtils";


    public static String encodeByBase64(String fileName) {
        String data = "";
        try {
            File file = new File(fileName);
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = in.read(buffer);
            data = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (Exception ex) {
        }
        return data;
    }

    public static String encodeByBase64(File file) {
        String data = "";
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = in.read(buffer);
            data = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (Exception ex) {
        }
        return data;
    }


    /**
     * 在SD卡上创建文件
     *
     * @param path 目录路径
     * @return
     * @throws IOException
     */
    public static File makeFile(String path) {
        if (!isAvailable()) {
            return null;
        }
        File file = new File(path);
        if (file.exists()) {
            return file;
        } else {
            try {
                if (path.contains("/")) {
                    String dir = path.substring(0, path.lastIndexOf('/'));
                    makeDir(dir);
                }
                if (!file.createNewFile()) {
                    return null;
                }
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    /**
     * 在SD卡上创建目录
     *
     * @param dir
     * @return
     */
    public static File makeDir(String dir) {
        if (!isAvailable()) {
            return null;
        }
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            if (!dirFile.mkdirs()) {
                return null;
            }
        }
        return dirFile;
    }

    /***
     * 获取SD卡的剩余容量,单位是Byte
     *
     * @return
     */
    public static long getAvailableSize() {
        if (isAvailable()) {
            // 取得sdcard文件路径
            File pathFile = Environment.getExternalStorageDirectory();
            android.os.StatFs statfs = new android.os.StatFs(pathFile.getPath());
            // 获取SDCard上每个block的SIZE
            long nBlocSize = statfs.getBlockSize();
            // 获取可供程序使用的Block的数量
            long nAvailaBlock = statfs.getAvailableBlocks();
            // 计算 SDCard 剩余大小Byte
            long nSDFreeSize = nAvailaBlock * nBlocSize;
            return nSDFreeSize;
        }
        return 0;
    }

    /***
     * 获取SD卡是否可用
     *
     * @return
     */
    public static boolean isAvailable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /***
     * 从sd卡中读取文件，并且以InputStream返回
     *
     * @param path
     * @return
     */
    public static InputStream readISFrom(String path) {
        File file = new File(path);
        FileInputStream is = null;
        try {
            is = new FileInputStream(file);
            return is;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 从InputStream中读取文件，并且以字节流返回
     *
     * @param is
     * @return
     */
    public static byte[] readFrom(InputStream is) {
        try {
            byte[] data = new byte[is.available()];
            is.read(data);
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new byte[]{};
    }

    /***
     * 从sd卡中读取文件，并且以字节流返回
     *
     * @param path
     * @return
     */
    public static byte[] readFrom(String path) {
        return readFrom(readISFrom(path));
    }

    /**
     * 将一个byte[]写入到SD卡中
     */
    public static void writeTo(String path, byte[] bytes) {
        OutputStream output = null;
        try {
            int size = bytes.length;
            // 拥有可读可写权限，并且有足够的容量
            if (isAvailable() && size < getAvailableSize()) {
                File file = makeFile(path);
                output = new FileOutputStream(file);
                output.write(bytes);
                output.flush();
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 将一个InputStream里面的数据写入到SD卡中
     */
    public static void writeTo(String path, InputStream is) {
        writeTo(path, readFrom(is));
    }

    /***
     * 获取文件的路径
     *
     * @param dir
     * @param fileName
     * @return
     */
    public static String getFilePath(String dir, String fileName) {
        return dir + File.separator + fileName;
    }

    /**
     * 生成随机文件名：当前年月日时分秒+五位随机数
     *
     * @return
     */
    public static String getRandomFileName(String fileSuffix) {

        SimpleDateFormat simpleDateFormat;
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date date = new Date();
        String str = simpleDateFormat.format(date);
        Random random = new Random();
        int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;// 获取5位随机数
        return rannum + str + "." + fileSuffix;

    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context
     * @param imageFile
     * @return content Uri
     */
    public static Uri getImageContentUri(Context context, java.io.File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets the corresponding path to a file from the given content:// URI
     *
     * @param selectedVideoUri The content:// URI to find the file path from
     * @param contentResolver  The content resolver to use to perform the query.
     * @return the file path as a string
     */
    public static String getFilePathFromContentUri(Uri selectedVideoUri,
                                                   ContentResolver contentResolver) {
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};

        Cursor cursor = contentResolver.query(selectedVideoUri, filePathColumn, null, null, null);
//      也可用下面的方法拿到cursor
//      Cursor cursor = this.context.managedQuery(selectedVideoUri, filePathColumn, null, null, null);

        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        filePath = cursor.getString(columnIndex);
        cursor.close();
        return filePath;
    }

    /**
     * 读取Asserts目录下的文件返回String
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String getJson(Context context, String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    /**
     * 获取文件夹大小
     *
     * @param file File实例
     * @return long
     */
    public static long getFolderSize(File file) {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 格式转换
     *
     * @param size
     * @return
     */
    public static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;

        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else
            return String.format("%d B", size);
    }

    /**
     * 删除指定目录下文件及目录
     *
     * @param filePath
     * @param deleteThisPath
     * @return
     */
    public static void deleteFolderFile(String filePath, boolean deleteThisPath) {
        if (!TextUtils.isEmpty(filePath)) {
            try {
                File file = new File(filePath);
                if (file.isDirectory()) {// 处理目录
                    File files[] = file.listFiles();
                    for (int i = 0; i < files.length; i++) {
                        deleteFolderFile(files[i].getAbsolutePath(), true);
                    }
                }
                if (deleteThisPath) {
                    if (!file.isDirectory()) {// 如果是文件，删除
                        file.delete();
                    } else {// 目录
                        if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                            file.delete();
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
