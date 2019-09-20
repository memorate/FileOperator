package com.main;

import com.myClass.repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {
    public static void main(String[] args) {
        File inFile = new File("D:\\MyData\\Administrator\\Desktop\\storage.txt");
        BufferedReader reader = null;
        String eachLine = null;
        int line = 1;
        long dev = 0;
        long doc = 0;
        long release = 0;
        long other = 0;
        List<repository> repoList = new ArrayList<>();
        List<repository> docList = new ArrayList<>();
        List<repository> releaseList = new ArrayList<>();
        List<repository> otherList = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(inFile));
            while ((eachLine = reader.readLine()) != null) {
                String[] spString = eachLine.split("\\s+");
                if (spString[1].toLowerCase().endsWith("dev")) {
                    dev = dev + Long.parseLong(spString[0]);
                    repoList.add(new repository(Long.parseLong(spString[0]), spString[1]));
                } else if (spString[1].toLowerCase().endsWith("doc")) {
                    doc = doc + Long.parseLong(spString[0]);
                    docList.add(new repository(Long.parseLong(spString[0]), spString[1]));
                } else if (spString[1].toLowerCase().endsWith("release")) {
                    release = release + Long.parseLong(spString[0]);
                    releaseList.add(new repository(Long.parseLong(spString[0]), spString[1]));
                } else {
                    other = other + Long.parseLong(spString[0]);
                    otherList.add(new repository(Long.parseLong(spString[0]), spString[1]));
                }
                line++;
            }
            Collections.sort(repoList);
            Collections.sort(docList);
            Collections.sort(releaseList);
            Collections.sort(otherList);
            repoList.addAll(docList);
            repoList.addAll(releaseList);
            repoList.addAll(otherList);
            System.out.println("dev" + ":" + dev/1024 + "G");
            System.out.println("doc" + ":" + doc/1024 + "G");
            System.out.println("release" + ":" + release/1024 + "G");
            System.out.println("other" + ":" + other/1024 + "G");
            System.out.println("------------------------");
//            FileWriter fw = new FileWriter("D:\\MyData\\Administrator\\Desktop\\ttttt.csv");
            FileWriter fw = new FileWriter("D:\\MyData\\Administrator\\Desktop\\result.txt");
            fw.flush();
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write("统计结果：" + "\r\n");
            bw.write("代码库" + "                   " + dev/1024 + "G" + "\r\n");
            bw.write("文档库" + "                   " + doc/1024 + "G" + "\r\n");
            bw.write("制品库" + "                   " + release/1024 + "G" + "\r\n");
            bw.write("其他" + "                      " + other/1024 + "G" + "\r\n");
            bw.write("----------------------------------" + "\r\n");
            bw.write("仓库名：" + "                " + "仓库大小：" + "\r\n");
            for (repository svnRepo : repoList) {
                String blank = calculate(svnRepo.getName());
                long sizeG = 0;
                if (svnRepo.getSize() < 1024) {
                    sizeG = svnRepo.getSize();
                    bw.write(svnRepo.getName() + blank + sizeG + "M" + "\r\n");
                } else {
                    sizeG = svnRepo.getSize() / 1024;
                    bw.write(svnRepo.getName() + blank + sizeG + "G" + "\r\n");
                }
            }
            bw.close();
            fw.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String calculate(String word) {
        int fill = 25 - word.length();
        String blank = "";
        for (int i = 0; i < fill; i++) {
            blank = blank + " ";
        }
        return blank;
    }
}
