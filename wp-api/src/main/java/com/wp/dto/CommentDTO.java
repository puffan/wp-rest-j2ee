package com.wp.dto;

import java.util.List;

public class CommentDTO {

    private Integer parentCount;
    private List<Comment> listData;

    public Integer getParentCount() {
        return parentCount;
    }

    public void setParentCount(Integer parentCount) {
        this.parentCount = parentCount;
    }

    public List<Comment> getListData() {
        return listData;
    }

    public void setListData(List<Comment> listData) {
        this.listData = listData;
    }
}
