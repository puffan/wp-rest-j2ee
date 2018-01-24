package com.common.dto;


import com.common.enums.ErrorCode;
import com.common.enums.HttpCode;

public class ResultModel {
    private Integer status;
    private Integer code;
    private String info;
    private Object data;
    private Page page;

    public ResultModel() {
        this.status = ErrorCode.success.value();
        this.code = HttpCode.success.value();
        this.info = null;
        this.data = null;
        this.page = null;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(ErrorCode status) {
        this.status = status.value();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(HttpCode code) {
        this.code = code.value();
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

}
