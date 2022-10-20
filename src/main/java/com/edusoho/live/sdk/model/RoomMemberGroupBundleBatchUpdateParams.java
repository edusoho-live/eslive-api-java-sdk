package com.edusoho.live.sdk.model;

import java.util.List;

public class RoomMemberGroupBundleBatchUpdateParams {

    private Long memberGroupBundleNo;

    private List<Long> roomIds;

    public Long getMemberGroupBundleNo() {
        return memberGroupBundleNo;
    }

    public List<Long> getRoomIds() {
        return roomIds;
    }

    public void setMemberGroupBundleNo(Long memberGroupBundleNo) {
        this.memberGroupBundleNo = memberGroupBundleNo;
    }

    public void setRoomIds(List<Long> roomIds) {
        this.roomIds = roomIds;
    }
}
