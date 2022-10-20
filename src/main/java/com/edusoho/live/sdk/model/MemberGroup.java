package com.edusoho.live.sdk.model;

public class MemberGroup {

    private String no;

    private String name;

    public String getNo() {
        return no;
    }

    public String getName() {
        return name;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "MemberGroup{" +
                "no='" + no + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
