package com.myClass;

public class repository implements Comparable<repository>{
    private long size;

    private String name;

    public repository(long size, String name) {
        this.size = size;
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
    public int compareTo(repository svnRepo) {
        long tmp = svnRepo.size - this.size ;
        return Integer.parseInt(String.valueOf(tmp));
    }
}
