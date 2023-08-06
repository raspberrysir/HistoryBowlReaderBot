/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.game;

import net.dv8tion.jda.api.entities.User;

/**
 *
 * @author Abhinav Pothuri
 */
public class Player
{
    public User user;
    public int pointsScored = 0;
    Player(User user)
    {
        this.user = user;
    }
}
