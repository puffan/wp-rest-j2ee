package com.wp.dto;

public class Category {

    private Long id;
    private String name;
    private Integer count;
    private String taxonomy;
    private Long parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTaxonomy() {
        return "category";
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public Long getParent() {
        return 0L;
    }

    public void setParent(Long parent) {
        this.parent = parent;
    }
}
