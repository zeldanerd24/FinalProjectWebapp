package com.krothenberger.model;

/**
 * Created by Kevin on 4/21/2015.
 */
public class Results {

    private int number;
    private String result;
    private String computeIp;
    private String hostIp;
    private long time;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getComputeIp() {
        return computeIp;
    }

    public void setComputeIp(String computeIp) {
        this.computeIp = computeIp;
    }

    public String getHostIp() {
        return hostIp;
    }

    public void setHostIp(String hostIp) {
        this.hostIp = hostIp;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
