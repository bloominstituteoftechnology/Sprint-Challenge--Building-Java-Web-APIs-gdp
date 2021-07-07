package com.lambdaschool.gdp.model;

import java.util.concurrent.atomic.AtomicInteger;

public class GDP {
    private static final AtomicInteger iCounter = new AtomicInteger();
    private int iID;
    private String strName;
    private long lGDP;

    public GDP(String strName, int iGDP) {
        this.iID = iCounter.incrementAndGet();
        this.strName = strName;
        this.lGDP = (long)iGDP;
    }

    public GDP(String strName, long lGDP) {
        this.iID = iCounter.incrementAndGet();
        this.strName = strName;
        this.lGDP = lGDP;
    }


    public GDP(String strName, String strGDP) {
        this.iID = iCounter.incrementAndGet();
        this.strName = strName;
        this.lGDP = Integer.parseInt(strGDP);
    }

    public int getiID() {
        return iID;
    }


    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public long getlGDP() {
        return lGDP;
    }

    public void setlGDP(int lGDP) {
        this.lGDP = lGDP;
    }
}