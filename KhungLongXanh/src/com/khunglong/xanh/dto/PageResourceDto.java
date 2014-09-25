package com.khunglong.xanh.dto;

/**
 * page chua info cua cac fan page co trong array resource duoc dua vao trong resource mng de quan ly
 * 
 * @author SonNT28
 * 
 */
public class PageResourceDto {
    int id;
    String displayName;
    String fbName;
    int drawer;
    String description;

    public PageResourceDto() {
        super();
    }

    public PageResourceDto(String[] arr) {
        fbName = arr[0];
        displayName = arr[1];
        description = arr[2];
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFbName() {
        return fbName;
    }

    public void setFbName(String fbName) {
        this.fbName = fbName;
    }

    public int getDrawer() {
        return drawer;
    }

    public void setDrawer(int drawer) {
        this.drawer = drawer;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
