package com.uzykj.chinatruck.utils;

import java.io.File;
import java.util.HashSet;
import java.util.concurrent.locks.Lock;

/**
 * @author ghostxbh
 */
public class FileUtils {
    private static final String PATH = "/Volumes/Extreme SSD/testfiles";
    private static final String NEW = "/new/";

    private static void moveTotherFolders(String dirPath, String fileName) {
        String startPath = dirPath;
        String endPath = PATH + NEW;
        try {
            File startFile = new File(startPath);
            File tmpFile = new File(endPath);//获取文件夹路径
            if (!tmpFile.exists()) {//判断文件夹是否创建，没有创建则创建新文件夹
                tmpFile.mkdirs();
            }
            System.out.println(endPath + startFile.getName());
            if (startFile.renameTo(new File(endPath + startFile.getName()))) {
                System.out.println("File is moved successful!");
                System.out.println("文件移动成功！文件名：《{}》" + fileName);
            } else {
                System.out.println("File is failed to move!");
            }
        } catch (Exception e) {
            System.out.println("File move error!");
        }
    }

    private static void recursiveFiles(String path) {
        // 创建 File对象
        File file = new File(path);

        // 取 文件/文件夹
        File[] files = file.listFiles();

        // 对象为空 直接返回
        if (files == null) {
            return;
        }

        // 目录下文件
        if (files.length == 0) {
            System.out.println(path + "该文件夹下没有文件");
        }

        HashSet<String> nameSet = new HashSet<String>();
        // 存在文件 遍历 判断
        for (File f : files) {

            // 判断是否为 文件夹
            if (f.isDirectory()) {
                System.out.print("文件夹: ");
                System.out.println(f.getAbsolutePath());

                // 为 文件夹继续遍历
                recursiveFiles(f.getAbsolutePath());

                // 判断是否为 文件
            } else if (f.isFile()) {

                System.out.print("文件: ");
                String name = f.getName();
                String absolutePath = f.getAbsolutePath();
                if (!nameSet.contains(name)) {
                    nameSet.add(name);
                    moveTotherFolders(absolutePath, name);
                }
            } else {
                System.out.print("未知错误文件");
            }

        }

    }

    public static void main(String[] args) {
        recursiveFiles(PATH);
    }
}
