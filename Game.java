/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author Abhinav Pothuri
 */
public class Game 
{
    public ArrayList<Question> questions;
    public Map<String, Team> teams = new HashMap<>();
    public boolean inProgress = true;     //if question is being read
    public Buzzer buzzer;          //if buzzer is not locked or locked
    public int questionNumber;
    public Game(ArrayList<User> teamA, ArrayList<User> teamB, ArrayList<User> teamC, 
            String teamA_Name, String teamB_Name, String teamC_Name, String fileName)
    {
        buzzer = new Buzzer();
        questionNumber = 1;
        teams.put(teamA_Name,new Team(teamA));
        if(!teamB.isEmpty())
            teams.put(teamB_Name,new Team(teamB));
        if(!teamC.isEmpty())
            teams.put(teamC_Name,new Team(teamC));
        
        //read file, turn into questions and bonuses. idk using pdf reader thingy. HARD PART
    }
    
    void play()
    {
        while(questionNumber <= questions.size())
        {
            questions.get(questionNumber).play(inProgress);
            ++questionNumber;
        }
    }
    
    public boolean check(String givenAnswer)
    {
        return questions.get(questionNumber - 1).isCorrect(givenAnswer);
    }
    
    public void award(Player player, Team team, Power power)
    {
        int add = 10;
        if(power == Power.POWER)
            add = 20;
        else if(power == Power.SUPERPOWER)
            add = 30;
        player.pointsScored += add;
        team.pointsScored += add;
    }
    
    public void clearBuzzer()
    {
        buzzer.isLocked = false;
        for(Team team : teams.values())
            team.isLocked = false;
    }
    
    
    
    
}
