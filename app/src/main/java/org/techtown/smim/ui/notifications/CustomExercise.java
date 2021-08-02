package org.techtown.smim.ui.notifications;

public class CustomExercise {
    String iname;
    String ipart;
    String iexplain;
    int iimageRes;

    public CustomExercise(String iname, String ipart, int iimageRes, String iexplain) {
        this.iname = iname;
        this.ipart = ipart;
        this.iimageRes = iimageRes;
        this.iexplain = iexplain;
    }

    public String igetName() {
        return iname;
    }

    public void isetName(String iname) {
        this.iname = iname;
    }

    public String igetPart() {
        return ipart;
    }

    public void isetPart(String ipart) {
        this.ipart = ipart;
    }

    public int igetImageRes() {
        return iimageRes;
    }

    public void isetImageRes(int iimageRes) {
        this.iimageRes = iimageRes;
    }

    public String igetExplain() { return iexplain; }

    public void isetExplain() { this.iexplain = iexplain; }

}
