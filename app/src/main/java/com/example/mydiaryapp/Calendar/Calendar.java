package com.example.mydiaryapp.Calendar;
public class Calendar {

    private int id;
    private String calendarName;
    private String accountName;
    private boolean isSelected; // 체크박스를 위한 상태

    public Calendar(int id, String calendarName, String accountName) {
        this.id = id;
        this.calendarName = calendarName;
        this.accountName = accountName;
        this.isSelected = false; // 기본값은 비선택 상태
    }

    // Getter/Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCalendarName() {
        return calendarName;
    }

    public void setCalendarName(String calendarName) {
        this.calendarName = calendarName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

}