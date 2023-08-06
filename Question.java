/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.game;

import java.util.concurrent.TimeUnit;
/**
 *
 * @author Abhinav Pothuri
 */
public class Question 
{
    String question;
    String answer;
    Quarter quarter;
    Question(String tossup, String answer, Quarter quarter)
    {
        this.question = tossup;
        this.answer = answer;
        this.quarter = quarter;
    }
    
    void play(boolean inProgress)
    {
        while(inProgress)
        {
            if(quarter == Quarter.QUARTER1)
            {
                read(question, inProgress);
                if(inProgress)
                {
                    read("time is up", inProgress);
                    inProgress = false;
                }
            }
            else if(quarter == Quarter.QUARTER2)
            {
                read(question, inProgress);
                Utility.wait(3);
                if(inProgress)
                {
                    read("time is up", inProgress);
                    inProgress = false;
                }
                    
            }
            else if(quarter == Quarter.QUARTER3)
            {
                //special stuff
            }
            else if(quarter == Quarter.QUARTER4)
            {
                
            }
            else if(quarter == Quarter.BEE)
            {
                read(question, inProgress);
                if(inProgress)
                {
                    read("time is up", inProgress);
                    inProgress = false;
                }
            }
            else if(quarter == Quarter.BONUS)
            {
                read(question, inProgress);
                if(inProgress)
                {
                    read("time is up", inProgress);
                    inProgress = false;
                }
            }
        }
    }
    
    boolean isCorrect(String playerAnswer)
    {
        //check if they're close enough
        boolean isCorrect = playerAnswer.equalsIgnoreCase(answer);
        return isCorrect;
    }
    
    void read(String str, boolean inProgress)
    {
        while(inProgress)
        {
            //tts
        }
    }
    
    public Power getPower()
    {
        return Power.NORMAL;
    }
}


