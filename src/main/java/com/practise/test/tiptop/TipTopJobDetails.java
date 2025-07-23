package com.practise.test.tiptop;

import java.util.List;
import java.util.StringJoiner;

public class TipTopJobDetails {
    private String processName;
    private long id;
    private double tat;
    private String username;
    private String company;
    private long userId;
    private String displayName;
    private String assetName;
    private double mediaDuration;
    private String jobStatus;
    private String createdAt;
    private String updatedAt;
    private String errorMessage;
    private double totalPrice;
    private List<TipTopService> services;

    @Override
    public String toString() {
        return new StringJoiner(", ", TipTopJobDetails.class.getSimpleName() + "[", "]")
            .add("processName='" + processName + "'")
            .add("id=" + id)
            .add("tat=" + tat)
            .add("username='" + username + "'")
            .add("company='" + company + "'")
            .add("userId=" + userId)
            .add("displayName='" + displayName + "'")
            .add("assetName='" + assetName + "'")
            .add("mediaDuration=" + mediaDuration)
            .add("jobStatus='" + jobStatus + "'")
            .add("createdAt='" + createdAt + "'")
            .add("updatedAt='" + updatedAt + "'")
            .add("errorMessage='" + errorMessage + "'")
            .add("totalPrice=" + totalPrice)
            .add("services=" + services)
            .toString();
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(final String processName) {
        this.processName = processName;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public double getTat() {
        return tat;
    }

    public void setTat(final double tat) {
        this.tat = tat;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(final String company) {
        this.company = company;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(final long userId) {
        this.userId = userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(final String assetName) {
        this.assetName = assetName;
    }

    public double getMediaDuration() {
        return mediaDuration;
    }

    public void setMediaDuration(final double mediaDuration) {
        this.mediaDuration = mediaDuration;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(final String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<TipTopService> getServices() {
        return services;
    }

    public void setServices(final List<TipTopService> services) {
        this.services = services;
    }
}
