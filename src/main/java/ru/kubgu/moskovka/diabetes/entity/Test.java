package ru.kubgu.moskovka.diabetes.entity;

public class Test {
    private String OGTT = "";
    private String FPG = "";
    private String CPG = "";

    public Test() {
    }

    public Test(String OGTT, String FPG, String CPG) {
        this.OGTT = OGTT;
        this.FPG = FPG;
        this.CPG = CPG;
    }

    public String getOGTT() {
        return OGTT;
    }

    public void setOGTT(String OGTT) {
        this.OGTT = OGTT;
    }

    public String getFPG() {
        return FPG;
    }

    public void setFPG(String FPG) {
        this.FPG = FPG;
    }

    public String getCPG() {
        return CPG;
    }

    public void setCPG(String CPG) {
        this.CPG = CPG;
    }

    @Override
    public String toString() {
        return "Test{" +
                "OGTT='" + OGTT + '\'' +
                ", FPG='" + FPG + '\'' +
                ", CPG='" + CPG + '\'' +
                '}';
    }
}
