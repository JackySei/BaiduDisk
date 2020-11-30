package com.example.baidudisk.enity;

public class FileEntity  {

        public int type;
        public String name;
        public String date;

        public FileEntity(int type, String name, String date){
            this.type=type;
            this.name=name;
            this.date=date;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

}
