package com.practise.test.tiptop;

import java.util.StringJoiner;

public class TipTopService {

    public long getJobId() {
        return jobId;
    }

    public void setJobId(final long jobId) {
        this.jobId = jobId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(final String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getOutputType() {
        return outputType;
    }

    public void setOutputType(final int outputType) {
        this.outputType = outputType;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(final String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(final String serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TipTopService.class.getSimpleName() + "[", "]")
            .add("jobId=" + jobId)
            .add("status='" + status + "'")
            .add("errorMessage='" + errorMessage + "'")
            .add("outputType=" + outputType)
            .add("id=" + id)
            .add("serviceName='" + serviceName + "'")
            .add("serviceType='" + serviceType + "'")
            .toString();
    }
    private long jobId;
    private String status;
    private String errorMessage;
    private int outputType;
    private long id;
    private String serviceName;
    private String serviceType;

}
