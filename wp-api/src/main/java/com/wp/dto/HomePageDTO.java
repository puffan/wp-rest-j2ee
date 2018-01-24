package com.wp.dto;

import java.util.ArrayList;
import java.util.List;

public class HomePageDTO {

    private Long categoryId;
    private String categoryName;
    private List<PostForHomePageDTO> listData;

    public HomePageDTO() {
        listData = new ArrayList<>();
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public List<PostForHomePageDTO> getListData() {
        return listData;
    }

    public void setListData(List<PostForHomePageDTO> listData) {
        this.listData = listData;
    }
}
