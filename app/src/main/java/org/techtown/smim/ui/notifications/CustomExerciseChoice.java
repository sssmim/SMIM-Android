package org.techtown.smim.ui.notifications;

public class CustomExerciseChoice {
    int cimage;
    String cname;
    String cpart;

    public CustomExerciseChoice(int cimage, String cname, String cpart){
        this.cimage = cimage;
        this.cname = cname;
        this.cpart = cpart;
    }

    public int cgetImage() { return cimage; }

    public void csetImage(int image) { this.cimage = cimage; }

    public String cgetName() { return cname; }

    public void csetName(String cname) { this.cname = cname; }

    public String cgetPart() { return cpart; }

    public void csetPart(String cpart) { this.cpart = cpart; }
}
