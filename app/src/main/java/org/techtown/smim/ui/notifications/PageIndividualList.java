package org.techtown.smim.ui.notifications;

public class PageIndividualList {

    String info_title;
    String info_desc;
    String image_view;

    public PageIndividualList(String info_title, String info_desc){
        this.info_title = info_title;
        this.info_desc = info_desc;
        this.image_view = image_view;
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

    public String getImageView() {
        return image_view;
    }

    public void setImageView(String info_desc) { this.image_view = image_view; }
}
