package com.wp.dto;

public class CategoryDTO implements Cloneable {

    private Long term_id;
    private String term_name;

    public Long getTerm_id() {
        return term_id;
    }

    public void setTerm_id(Long term_id) {
        this.term_id = term_id;
    }

    public String getTerm_name() {
        return term_name;
    }

    public void setTerm_name(String term_name) {
        this.term_name = term_name;
    }

    @Override
    public CategoryDTO clone() {
        CategoryDTO clone = null;
        try {
            clone = (CategoryDTO) super.clone();

        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); // won't happen
        }

        return clone;
    }
}
