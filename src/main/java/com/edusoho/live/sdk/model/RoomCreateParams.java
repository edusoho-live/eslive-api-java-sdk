package com.edusoho.live.sdk.model;

public class RoomCreateParams {
    private String name;

    private Long startAt;

    private Long endAt;

    private Long groupBundleNo;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getStartAt() {
        return startAt;
    }

    public Long getGroupBundleNo() {
        return groupBundleNo;
    }

    public void setStartAt(Long startAt) {
        this.startAt = startAt;
    }

    public Long getEndAt() {
        return endAt;
    }

    public void setEndAt(Long endAt) {
        this.endAt = endAt;
    }

    public void setGroupBundleNo(Long groupBundleNo) {
        this.groupBundleNo = groupBundleNo;
    }
}
