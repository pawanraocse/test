package com.practise.test.tiptop;

import java.util.ArrayList;
import java.util.List;

public class TipTipProcessInfo {
    private int id;
    private String processName;
    private String tranceProcess;
    private String atType;
    private List<TipTopMediaInputFormat> mediaInputFormats;


    public TipTipProcessInfo() {
        mediaInputFormats = new ArrayList<>();
    }

    public void addMediaInputFormat(TipTopMediaInputFormat mediaInputFormat) {
        mediaInputFormats.add(mediaInputFormat);
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(final String processName) {
        this.processName = processName;
    }

    public String getTranceProcess() {
        return tranceProcess;
    }

    public void setTranceProcess(final String tranceProcess) {
        this.tranceProcess = tranceProcess;
    }

    public String getAtType() {
        return atType;
    }

    public void setAtType(final String atType) {
        this.atType = atType;
    }
}
