package com.common.enums;

public enum HttpCode {
    success(200),
    errparam(400),
    unauthorized(401),//鉴权认证失败
    forbidden(403),//禁止访问
    notFound(404),//资源未发现
    internalServerError(500),
    serviceUnavailable(503);//服务不可用

    private int code;

    HttpCode(int code) {
        this.code = code;
    }

    public int value() {
        return this.code;
    }

    public static HttpCode valueOf(int code) {
        switch (code) {
            case 200:
                return HttpCode.success;
            case 400:
                return HttpCode.errparam;
            case 401:
                return HttpCode.unauthorized;
            case 403:
                return HttpCode.forbidden;
            case 404:
                return HttpCode.notFound;
            case 500:
                return HttpCode.internalServerError;
            case 503:
                return HttpCode.serviceUnavailable;
            default:
                return HttpCode.internalServerError;
        }
    }

}
