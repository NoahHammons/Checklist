package com.example.checklist;

public class Items {
    private String name;
    private boolean checked;
    private String email;
    private String id;
    private Lists list;

    public Items() {}

    public Items(String email, String name, boolean checked, String id, Lists list){
        this.name = name;
        this.email = email;
        this.checked = checked;
        this.id = id;
        this.list = list;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public void setList(Lists list){
        this.list = list;
    }

    public Lists getList() {
        return list;
    }

    public String getListname(){
        return list.getName();
    }
    public void setName(String name){this.name = name; }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){return id;}
    public String getName(){return name;}
    public String getEmail(){return email;}
    public boolean getchecked(){return checked;}

}
