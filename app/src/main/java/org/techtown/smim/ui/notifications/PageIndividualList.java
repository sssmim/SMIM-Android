package org.techtown.smim.ui.notifications;

public class PageIndividualList {

    String info_title;
    String info_desc;

    public PageIndividualList(String info_title, String info_desc){
        this.info_title = info_title;
        this.info_desc = info_desc;
    }

    public String getInfoTitle() {
        return info_title;
    }

    public void setInfoTitle(String info_title) {
        this.info_title = info_title;
    }

    public String getInfoDesc() {
        return info_desc;
    }

    public void setInfoDesc(String info_desc) {
        this.info_desc = info_desc;
    }
}
