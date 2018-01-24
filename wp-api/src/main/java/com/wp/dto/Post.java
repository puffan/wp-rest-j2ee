package com.wp.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.common.StringUtil;
import de.ailis.pherialize.MixedArray;
import de.ailis.pherialize.Pherialize;

import java.util.ArrayList;
import java.util.List;

public class Post {

    private Long id;
    private String content;
    private Long author;
    private String comment_status;
    private List<CategoryDTO> categories;
    private List<String> tags;
    private String welink_title;
    private String welink_creatTime;
    private String welink_nameCn;
    private String welink_imgData;
    @JSONField(serialize = false)
    private String img_meta;
    private String welink_accountid;
    @JSONField(serialize = false)
    private Long categoryId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getAuthor() {
        return author;
    }

    public void setAuthor(Long author) {
        this.author = author;
    }

    public String getComment_status() {
        return comment_status;
    }

    public void setComment_status(String comment_status) {
        this.comment_status = comment_status;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

    public List<String> getTags() {
        if (null == tags) {
            return new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getWelink_title() {
        return welink_title;
    }

    public void setWelink_title(String welink_title) {
        this.welink_title = welink_title;
    }

    public String getWelink_creatTime() {
        return welink_creatTime;
    }

    public void setWelink_creatTime(String welink_creatTime) {
        this.welink_creatTime = welink_creatTime;
    }

    public String getWelink_nameCn() {
        return welink_nameCn;
    }

    public void setWelink_nameCn(String welink_nameCn) {
        this.welink_nameCn = welink_nameCn;
    }

    public String getWelink_imgData() {
        return welink_imgData;
    }

    public void setWelink_imgData(String welink_imgData) {
        this.welink_imgData = welink_imgData;
    }

    public String getImg_meta() {
        return img_meta;
    }

    public void setImg_meta(String img_meta) {
        this.img_meta = img_meta;
    }

    public String getWelink_accountid() {
        return welink_accountid;
    }

    public void setWelink_accountid(String welink_accountid) {
        this.welink_accountid = welink_accountid;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public void setImgData() {
        if (!StringUtil.isEmpty(this.welink_imgData) && !StringUtil.isEmpty(this.img_meta)) {
            MixedArray list = Pherialize.unserialize(this.img_meta).toArray();
            String name = list.getArray("sizes").getArray("medium").getString("file");
            this.welink_imgData = this.welink_imgData.substring(0, welink_imgData.lastIndexOf("/") + 1) + name;
        }
    }
}
