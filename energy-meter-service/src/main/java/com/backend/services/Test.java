package com.backend.services;

public class Test {
    public static void main(String[] args) {
         Abc myobj= new Abc("shilpa","2");
    }

}

class Abc{
    String name;
    String id;
    public Abc(String name,String id){
        this.name = name;
        this.id = id;
    }
    public void test (){
        System.out.println("test : "+name+" : "+id);
    }
}