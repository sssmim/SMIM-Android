package org.techtown.smim.ui.notifications;

public class CustomExercise {
    String iname;
    String ipart;
    int iimageRes;
    private boolean isSelected = false;

    public CustomExercise(String iname, String ipart, int iimageRes) {
        this.iname = iname;
        this.ipart = ipart;
        this.iimageRes = iimageRes;
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

    public void isetSelected(boolean selected){
        isSelected = selected;
    }

    public boolean iisSelected() {
        return isSelected;
    }
}
