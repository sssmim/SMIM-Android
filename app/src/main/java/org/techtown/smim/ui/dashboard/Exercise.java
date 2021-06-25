package org.techtown.smim.ui.dashboard;

public class Exercise {
    String stime;
    String etime;
    String memo;

    public Exercise(String stime,String etime,String memo){
        this.stime=stime;
        this.etime=etime;
        this.memo=memo;
    }

    public String getStime() {
        return stime;
    }

    public void  setStime(String stime) {
    this.stime =stime;
    }

    public String getEtime() {
        return etime;
    }

    public void  setEtime(String etime) {
        this.etime =etime;
    }

    public String getMeno() {
        return memo;
    }

    public void  setMemo(String memo) {
        this.memo =memo;
    }



}
