package com.edufy.eddufy;

public class MessageInfo {

        public String sender;
        public String receiver;
        public String message;


    public MessageInfo (String sender ,String receiver, String message){

        this.sender = sender;
        this.receiver = receiver;
        this.message = message;

    }



        public MessageInfo(){

        }


        public String getSender() {return sender;}

        public String getReceiver() {
            return receiver;
        }

        public String getMessage() {
            return message;
        }

//        public void setSender(String sender) {
//            this.sender = sender;
//        }
//
//        public void setReceiver(String receiver) {
//            this.receiver = receiver;
//        }
//
//        public void setMessage(String message) {
//            this.message = message;
//        }

    }


