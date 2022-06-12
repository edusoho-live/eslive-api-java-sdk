package com.edusoho.live.sdk.model;

import com.edusoho.live.sdk.common.QueryParams;

import java.util.HashMap;
import java.util.Map;

public class MemberListParams implements QueryParams {

    private Long roomId;

    private Long page;

    private Long size;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    @Override
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("roomId", roomId.toString());
        params.put("page", page.toString());
        params.put("size", size.toString());

        return params;
    }
}
