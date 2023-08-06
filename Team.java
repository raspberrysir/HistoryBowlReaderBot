/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.game;

import java.util.ArrayList;
import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author Abhinav Pothuri
 */
public class Team 
{
    public ArrayList<Player> players;
    public int pointsScored;
    public boolean isLocked = false;
    Team(ArrayList<User> users)
    {
        for(User user : users)
            players.add(new Player(user));
        pointsScored = 0;
    }
}
