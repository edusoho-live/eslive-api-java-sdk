package com.edusoho.live.sdk.model;

import java.util.List;

public class GroupBatchCreateParams {

    private Long bundleNo;

    private List<String> names;

    public Long getBundleNo() {
        return bundleNo;
    }

    public List<String> getNames() {
        return names;
    }

    public void setBundleNo(Long bundleNo) {
        this.bundleNo = bundleNo;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }
}
