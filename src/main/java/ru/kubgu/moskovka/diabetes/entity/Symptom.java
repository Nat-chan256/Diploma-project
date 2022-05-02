/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.kubgu.moskovka.diabetes.entity;

/**
 *
 * @author Administrator
 */
public class Symptom {
    private String headache = "";
    private String BV = "";
    private String EU = "";
    private String polydipsia = "";
    private String LC = "";
    private String NV = "";
    private String polyphagia = "";
    private String tiredness = "";
    private String LW = "";
    private String FST = "";
    private String FI = "";
    private String sensation = "";
    private String coldSweat = "";

    public Symptom() {
    }

    public Symptom(String headache, String BV, String EU, String polydipsia, String LC, String NV, String polyphagia,
                   String tiredness, String LW, String FST, String FI, String sensation, String coldSweat) {
        this.headache = headache;
        this.BV = BV;
        this.EU = EU;
        this.polydipsia = polydipsia;
        this.LC = LC;
        this.NV = NV;
        this.polyphagia = polyphagia;
        this.tiredness = tiredness;
        this.LW = LW;
        this.FST = FST;
        this.FI = FI;
        this.sensation = sensation;
        this.coldSweat = coldSweat;
    }
    
    public String getHeadache() {
        return headache;
    }

    public void setHeadache(String headache) {
        this.headache = headache;
    }

    public String getBV() {
        return BV;
    }

    public void setBV(String BV) {
        this.BV = BV;
    }

    public String getEU() {
        return EU;
    }

    public void setEU(String EU) {
        this.EU = EU;
    }

    public String getPolydipsia() {
        return polydipsia;
    }

    public void setPolydipsia(String polydipsia) {
        this.polydipsia = polydipsia;
    }

    public String getLC() {
        return LC;
    }

    public void setLC(String LC) {
        this.LC = LC;
    }

    public String getNV() {
        return NV;
    }

    public void setNV(String NV) {
        this.NV = NV;
    }

    public String getPolyphagia() {
        return polyphagia;
    }

    public void setPolyphagia(String polyphagia) {
        this.polyphagia = polyphagia;
    }

    public String getTiredness() {
        return tiredness;
    }

    public void setTiredness(String tiredness) {
        this.tiredness = tiredness;
    }

    public String getLW() {
        return LW;
    }

    public void setLW(String LW) {
        this.LW = LW;
    }

    public String getFST() {
        return FST;
    }

    public void setFST(String FST) {
        this.FST = FST;
    }

    public String getFI() {
        return FI;
    }

    public void setFI(String FI) {
        this.FI = FI;
    }

    public String getSensation() {
        return sensation;
    }

    public void setSensation(String sensation) {
        this.sensation = sensation;
    }

    public String getColdSweat() {
        return coldSweat;
    }

    public void setColdSweat(String coldWweat) {
        this.coldSweat = coldWweat;
    }

    @Override
    public String toString() {
        return "Symptom{" +
                "headache='" + headache + '\'' +
                ", BV='" + BV + '\'' +
                ", EU='" + EU + '\'' +
                ", polydipsia='" + polydipsia + '\'' +
                ", LC='" + LC + '\'' +
                ", NV='" + NV + '\'' +
                ", polyphagia='" + polyphagia + '\'' +
                ", tiredness='" + tiredness + '\'' +
                ", LW='" + LW + '\'' +
                ", FST='" + FST + '\'' +
                ", FI='" + FI + '\'' +
                ", sensation='" + sensation + '\'' +
                ", coldSweat='" + coldSweat + '\'' +
                '}';
    }
}
