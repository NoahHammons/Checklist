package com.example.checklist;

public class Lists {
        private String name;
        private String email;
        private String id;

        public Lists() {}

        public Lists(String email, String name, String id){
            this.name = name;
            this.email = email;
            this.id = id;
        }

        public void setName(String name){this.name = name; }
        public void setId(String id){
            this.id = id;
        }
        public String getId(){return id;}
        public String getName(){return name;}
        public String getEmail(){return email;}

}
