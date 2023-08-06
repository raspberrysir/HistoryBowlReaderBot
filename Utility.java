/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.game;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import net.dv8tion.jda.api.entities.User;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

/**
 *
 * @author Abhinav Pothuri
 */
public class Utility 
{
    public static void wait(int t)
    {
        try
        {
            Thread.sleep(t);
        }
        catch(InterruptedException e)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    public static Team findTeam(Game game, User user)
    {
        Team team = null;
        for(Team team2 : game.teams.values())
        {
            for(Player player : team2.players)
            {
                if(user == player.user)
                    team = team2;
            }
        }
        return team;
    }
    
    public static Player findUser(Team team, User user)
    {
        Player player = null;
        for(Player player2 : team.players)
        {
            if(user == player2.user)
                player = player2;
        }
        return player;
    }
    

    public static String PDFReader(String fileName) 
    {
        try (PDDocument document = PDDocument.load(new File(fileName))) 
        {
            PDFTextStripper stripper = new PDFTextStripper();
            String extractedText = stripper.getText(document);
            System.out.println("File Opened");
            return extractedText;
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
            return null;
        }
    }
    
    
    public static void readPDF(String fileName, ArrayList<Question> questions)
    {
        String str = Utility.PDFReader(fileName);
        String[] words = str.split(" ");
        int index = 0;
        
//        for(String s : words)
//            System.out.println("|" + s + "|");
        
        
        
        boolean tick = false;
        while(index < words.length)
        {
            if((words[index] + words[index + 1]).contains("First"))
            {
                if(tick)
                    break;
                tick = true;
                for(int i = 1; i < 11; ++i)
                {
                    //finding question number
                    while(!words[index].contains("(" + i + ")"))
                        ++index;
                    ++index;
                    
                    String question = "";
                    if(i == 1)
                        question += "First Quarter. \n";
                    
                    //read question
                    question += "Question Number " + i + ": \n";
                    while(!words[index].contains("ANSWER:"))
                    {
                        question += words[index] + " ";
                        ++index;
                    }
                    ++index;
                    
                    
                    //read answer
                    String answer = "Answer for Question Number " + i + ": \n";
                    while(!words[index].contains("(") && !words[index].contains("2"))
                    {
                        answer += words[index] + " ";
                        ++index;
                    }
                    questions.add(new Question(question, answer, Quarter.QUARTER1));
                }
            }
            else if((words[index] + words[index + 1]).contains("Second"))
            {
                for(int i = 1; i < 9; ++i)
                {
                    //finding question number
                    while(!words[index].contains("(" + i + ")"))
                        ++index;
                    ++index;
                    
                    String question = "";
                    if(i == 1)
                        question += "Second Quarter. \n";
                    
                    //read question
                    question += "Question Number " + i + ": \n";
                    while(!words[index].contains("ANSWER:"))
                    {
                        question += words[index] + " ";
                        ++index;
                    }
                    ++index;
                    
                    //read answer
                    String answer = "Answer for Question Number " + i + ": \n";
                    while(!words[index].contains("(") && !words[index].contains("BONUS") && !words[index].contains("2"))
                    {
                        answer += words[index] + " ";
                        ++index;
                    }
                    questions.add(new Question(question, answer, Quarter.QUARTER1));
                    
                    if(words[index].contains("("))
                    {
                        while(!words[index].contains(")"))
                            ++index;
                    }
                    ++index;
                    
                    //read bonus----------------------------------------
                    String bonusQuestion = "Bonus for Question Number " + i + ": ";
                    while(!words[index].contains("ANSWER:"))
                    {
                        bonusQuestion += words[index] + " ";
                        ++index;
                    }
                    ++index;
                    
                    //read bonus answer
                    String bonusAnswer = "Answer for Bonus Number " + i + ": \n";
                    while(!words[index].contains("(") && !words[index].contains("2"))
                    {
                        bonusAnswer += words[index] + " ";
                        ++index;
                    }
                    questions.add(new Question(bonusQuestion, bonusAnswer, Quarter.BONUS));
                }
            }
            else if("Third Quarter".equals(words[index] + " " + words[index + 1]))
            {
                
            }
            else if("Fourth Quarter".equals(words[index] + " " + words[index + 1]))
            {
                
            }
            ++index;
        }
        
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static void main(String[] args)
    {
        ArrayList<Question> questions = new ArrayList<>();
        readPDF("C:\\Users\\Abhinav Pothuri\\Downloads\\Bowl-Round-1.pdf", questions);
        for(Question question : questions)
        {
            System.out.println(question.question);
            System.out.println("\n" + question.answer);
            System.out.println();
        }
    }
}
