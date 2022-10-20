package com.edusoho.live.sdk.model;

import java.util.List;

public class RoomMemberGroupBundleBatchUpdateParams {

    private Long memberGroupBundleNo;

    private List<Long> ids;

    public Long getMemberGroupBundleNo() {
        return memberGroupBundleNo;
    }

    public List<Long> getIds() {
        return ids;
    }

    public void setMemberGroupBundleNo(Long memberGroupBundleNo) {
        this.memberGroupBundleNo = memberGroupBundleNo;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
