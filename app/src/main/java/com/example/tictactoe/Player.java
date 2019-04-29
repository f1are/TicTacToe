package com.example.tictactoe;

public class Player {

    private String name = "Player";
    private long thinkingTime;


    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    void setThinkingTime(long thinkingTime){
        this.thinkingTime = thinkingTime;
    }

    long getThinkingTime(){
        return thinkingTime;
    }

    int getThinkingTimeMs(){
        return (int)(thinkingTime%1000);
    }

    int getThinkingTimeS(){
        return (int)(thinkingTime/1000%60);
    }

    int getThinkingTimeM(){
        return (int)(thinkingTime/1000/60);
    }
}
