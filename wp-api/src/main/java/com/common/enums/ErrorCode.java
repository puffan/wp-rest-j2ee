package com.common.enums;

public enum ErrorCode {
    success(0),
    nullObject(1),//对象为空错误
    validateFail(2),//参数校验失败
    noPermsission(4),//没有权限
    persistToDBFail(8),//操作数据库失败
    unknown(1024);//未知错误

    private int code;

    ErrorCode(int code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }

    public static ErrorCode valueOf(int code) {
        switch (code) {
            case 0:
                return ErrorCode.success;
            case 1:
                return ErrorCode.nullObject;
            case 2:
                return ErrorCode.validateFail;
            case 1024:
                return ErrorCode.unknown;
            case 4:
                return ErrorCode.noPermsission;
            case 8:
                return ErrorCode.persistToDBFail;
            default:
                return ErrorCode.unknown;
        }
    }
}
