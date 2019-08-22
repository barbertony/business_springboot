package com.neuedu.exception;

public class MyException extends RuntimeException{

    private String  director;
    public MyException(){}
    public MyException(String msg, String director)
    {

        super(msg);
        this.director=director;
        System.out.println("gouzaochenggong");
    }
    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }
}
