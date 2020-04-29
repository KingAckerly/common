package com.lsm.common.bean;


import java.util.Map;

public class RequestLog {

    private String startTime;
    private String url;
    private String description;
    private Integer level;
    private String type;
    private String httpMethod;
    private String classMethod;
    private String ip;
    private Map<String, Object> requestArgs;
    private String responseArgs;
    private String timeConsuming;
    private Object responseResult;
    private String entTime;

    public String getStartTime() {
        return startTime;
    }

    public RequestLog setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public RequestLog setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public RequestLog setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getLevel() {
        return level;
    }

    public RequestLog setLevel(Integer level) {
        this.level = level;
        return this;
    }

    public String getType() {
        return type;
    }

    public RequestLog setType(String type) {
        this.type = type;
        return this;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public RequestLog setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
        return this;
    }

    public String getClassMethod() {
        return classMethod;
    }

    public RequestLog setClassMethod(String classMethod) {
        this.classMethod = classMethod;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public RequestLog setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public Map<String, Object> getRequestArgs() {
        return requestArgs;
    }

    public RequestLog setRequestArgs(Map<String, Object> requestArgs) {
        this.requestArgs = requestArgs;
        return this;
    }

    public String getResponseArgs() {
        return responseArgs;
    }

    public RequestLog setResponseArgs(String responseArgs) {
        this.responseArgs = responseArgs;
        return this;
    }

    public String getTimeConsuming() {
        return timeConsuming;
    }

    public RequestLog setTimeConsuming(String timeConsuming) {
        this.timeConsuming = timeConsuming;
        return this;
    }

    public Object getResponseResult() {
        return responseResult;
    }

    public RequestLog setResponseResult(Object responseResult) {
        this.responseResult = responseResult;
        return this;
    }

    public String getEntTime() {
        return entTime;
    }

    public RequestLog setEntTime(String entTime) {
        this.entTime = entTime;
        return this;
    }

    @Override
    public String toString() {
        return "RequestLog{" +
                "startTime='" + startTime + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", level='" + level + '\'' +
                ", type='" + type + '\'' +
                ", httpMethod='" + httpMethod + '\'' +
                ", classMethod='" + classMethod + '\'' +
                ", ip='" + ip + '\'' +
                ", requestArgs='" + requestArgs + '\'' +
                ", responseArgs='" + responseArgs + '\'' +
                ", timeConsuming='" + timeConsuming + '\'' +
                ", responseResult='" + responseResult + '\'' +
                ", entTime='" + entTime + '\'' +
                '}';
    }
}
