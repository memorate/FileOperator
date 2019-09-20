package com.myClass;

public class Repository implements Comparable<Repository>{
    private long size;

    private String name;

    public Repository(long size, String name) {
        this.size = size;
        this.name = name;
    }

    public Repository(String size, String name) {
        this.size = Long.parseLong(size);
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "svnRepo{" +
                "size=" + size +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public int compareTo(Repository svnRepo) {
        //倒序排序
        long tmp = svnRepo.size - this.size ;
        return Integer.parseInt(String.valueOf(tmp));
    }
}
