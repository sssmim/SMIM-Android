package org.techtown.smim.ui.dashboard;

public class Youtube {
    int  imgsrc;
    String title;


    public Youtube(int imgsrc, String title){
       this.imgsrc=imgsrc;
        this.title=title;

    }

    public int  getImgsrc() {
      return imgsrc;
    }

    public void  setImgsrc(int  imgsrc) {
   this.imgsrc =imgsrc;
    }

    public String getTitle() {
        return title;
    }

    public void  setTitle(String title) {
        this.title =title;
    }



}
