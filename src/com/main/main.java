package com.main;

import com.myClass.Repository;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class main {
    public static void main(String[] args) throws Exception {
        long devSize = 0;
        long docSize = 0;
        long releaseSize = 0;
        long otherSize = 0;
        List<Repository> repositoryList = parseTxt("D:\\文件\\持续集成项目\\svn生产环境仓库大小统计\\storage.txt");
        List<Repository> codeList = new ArrayList<>();
        List<Repository> docList = new ArrayList<>();
        List<Repository> releaseList = new ArrayList<>();
        List<Repository> otherList = new ArrayList<>();
        //根据仓库名后缀统计不同类型仓库
        for (Repository repository : repositoryList) {
            if (filter(repository.getName(), "dev")){
                devSize += repository.getSize();
                codeList.add(repository);
            }else if (filter(repository.getName(), "doc")){
                docSize += repository.getSize();
                docList.add(repository);
            }else if (filter(repository.getName(), "release")){
                releaseSize += repository.getSize();
                releaseList.add(repository);
            }else {
                otherSize += repository.getSize();
                otherList.add(repository);
            }
        }
        //每种仓库按仓库大小排序
        Collections.sort(codeList);
        Collections.sort(docList);
        Collections.sort(releaseList);
        Collections.sort(otherList);
        List<Repository> result = new ArrayList<>();
        //将同种仓库放在一起
        result.addAll(codeList);
        result.addAll(docList);
        result.addAll(releaseList);
        result.addAll(otherList);

//            FileWriter fw = new FileWriter("D:\\MyData\\Administrator\\Desktop\\ttttt.csv");
        FileWriter fw = new FileWriter("D:\\MyData\\Administrator\\Desktop\\result.txt");
        fw.flush();
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("统计结果：" + "\r\n");
        bw.write("代码库" + "                   " + devSize / 1024 + "G" + "\r\n");
        bw.write("文档库" + "                   " + docSize / 1024 + "G" + "\r\n");
        bw.write("制品库" + "                   " + releaseSize / 1024 + "G" + "\r\n");
        bw.write("其他" + "                     " + otherSize / 1024 + "G" + "\r\n");
        bw.write("----------------------------------" + "\r\n");
        bw.write("仓库名：" + "               " + "仓库大小：" + "\r\n");
        bw.write("------------代码库----------------" + "\r\n");
        output(codeList, bw);
        bw.write("------------文档库----------------" + "\r\n");
        output(docList, bw);
        bw.write("------------制品库----------------" + "\r\n");
        output(releaseList, bw);
        bw.write("------------其  他----------------" + "\r\n");
        output(otherList, bw);
        bw.close();
        fw.close();
    }

    private static List<Repository> parseTxt(String path) throws Exception {
        File file = new File(path);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        return reader.lines().map(line -> {
            String[] data = line.split("\\s+");
            return new Repository(data[0], data[1]);
        }).collect(Collectors.toList());
    }

    private static boolean filter(String source, String condition){
        return source.toLowerCase().endsWith(condition);
    }

    private static String calculate(String word) {
        int fill = 25 - word.length();
        String blank = "";
        for (int i = 0; i < fill; i++) {
            blank = blank + " ";
        }
        return blank;
    }

    private static void output(List<Repository> repositoryList, BufferedWriter writer) throws Exception{
        for (Repository svnRepo : repositoryList) {
            //对其，仓库名最大长度设置为25
            String blank = calculate(svnRepo.getName());
            long sizeG = 0;
            if (svnRepo.getSize() < 1024) {
                sizeG = svnRepo.getSize();
                writer.write(svnRepo.getName() + blank + sizeG + "M" + "\r\n");
            } else {
                sizeG = svnRepo.getSize() / 1024;
                writer.write(svnRepo.getName() + blank + sizeG + "G" + "\r\n");
            }
        }
    }
}
