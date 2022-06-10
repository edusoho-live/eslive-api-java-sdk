package com.edusoho.live.sdk.model;

import com.edusoho.live.sdk.common.QueryParams;

import java.util.HashMap;
import java.util.Map;

public class MemberListParams implements QueryParams {

    private Long roomId;

    private Long offset;

    private Long limit;

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    @Override
    public Map<String, String> toQueryParams() {
        Map<String, String> params = new HashMap<>();
        params.put("roomId", roomId.toString());
        params.put("offset", offset.toString());
        params.put("limit", limit.toString());

        return params;
    }
}
