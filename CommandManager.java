/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.greenninja.commands;

import com.mycompany.greenninja.game.*;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import org.jetbrains.annotations.NotNull;
/**
 *
 * @author Abhinav Pothuri
 */
public class CommandManager extends ListenerAdapter
{
    Game game;
    @Override
    public void onSlashCommandInteraction(@NotNull SlashCommandInteractionEvent event)
    {
        String commandName = event.getName();
        if(commandName.equals("welcome"))
        {
            String userTag = event.getUser().getAsTag();
            event.reply("Hello " + userTag).queue();
        }
        else if(commandName.equals("begin"))
        {
            //initialize game
            ArrayList<User> teamA = new ArrayList<>();
            ArrayList<User> teamB = new ArrayList<>();
            ArrayList<User> teamC = new ArrayList<>();
            
            OptionMapping option1 = event.getOption("team_a_name");
            OptionMapping option2 = event.getOption("team_b_name");
            OptionMapping option3 = event.getOption("team_c_name");
            OptionMapping option4 = event.getOption("packet_file");
            OptionMapping option5 = event.getOption("a1");
            OptionMapping option6 = event.getOption("b1");
            OptionMapping option7 = event.getOption("c1");
            OptionMapping option8 = event.getOption("a2");
            OptionMapping option9 = event.getOption("b2");
            OptionMapping option10 = event.getOption("c2");
            OptionMapping option11 = event.getOption("a3");
            OptionMapping option12 = event.getOption("b3");
            OptionMapping option13 = event.getOption("c3");
            OptionMapping option14 = event.getOption("a4");
            OptionMapping option15 = event.getOption("b4");
            OptionMapping option16 = event.getOption("c4");
            OptionMapping option17 = event.getOption("a5");
            OptionMapping option18 = event.getOption("b5");
            OptionMapping option19 = event.getOption("c5");
            
            teamA.add(option5.getAsUser());
            if(option6 != null)
                teamB.add(option6.getAsUser());
            if(option7 != null)
                teamC.add(option7.getAsUser());
            if(option8 != null)
                teamA.add(option8.getAsUser());
            if(option9 != null)
                teamB.add(option9.getAsUser());
            if(option10 != null)
                teamC.add(option10.getAsUser());
            if(option11 != null)
                teamA.add(option11.getAsUser());
            if(option2 != null)
                teamB.add(option12.getAsUser());
            if(option13 != null)
                teamC.add(option13.getAsUser());
            if(option14 != null)
                teamA.add(option14.getAsUser());
            if(option15 != null)
                teamB.add(option15.getAsUser());
            if(option16 != null)
                teamC.add(option16.getAsUser());
            if(option17 != null)
                teamA.add(option17.getAsUser());
            if(option18 != null)
                teamB.add(option18.getAsUser());
            if(option19 != null)
                teamC.add(option19.getAsUser());
            
            String teamB_Name = "";
            if(!teamB.isEmpty())
            {
                if(option2 != null)
                    teamB_Name = option2.getAsString();
                else
                    teamB_Name = "Team B";
            }
            
            String teamC_Name = "";
            if(!teamC.isEmpty())
            {
                if(option3 != null)
                    teamC_Name = option3.getAsString();
                else
                    teamC_Name = "Team C";
            }
            
            game = new Game(teamA, teamB, teamC, option1.getAsString(), teamB_Name, teamC_Name, option4.getAsAttachment().getFileName());
        }
        else if(commandName.equals("buzz"))
        {
            Team team = Utility.findTeam(game, event.getUser());
            if(team == null)
                event.reply("You're not currently playing").queue();
            if(!game.buzzer.isLocked && !team.isLocked)
            {
                team.isLocked = true;
                game.buzzer.isLocked = true;
                game.buzzer.player = event.getUser();

                //ask for answer and check if right
                event.getChannel().sendMessage(event.getUser().getAsMention() + " What is your answer?").queue();
                Utility.wait(3);
                game.buzzer.isLocked = false;
            }
            else
                event.reply("Buzzer is locked").queue();
        }
        else if(commandName.equals("answer"))
        {
            OptionMapping option1 = event.getOption("answer");
            if(game.buzzer.isLocked && game.buzzer.player.equals(event.getUser()))
            {
                //check if answer is correct
                if(game.check(option1.getAsString()))
                {
                    Team team = Utility.findTeam(game, event.getUser());
                    game.award(Utility.findUser(team, event.getUser()), team, game.questions.get(game.questionNumber - 1).getPower());
                    game.clearBuzzer();
                }
            }
            else
                event.reply("Not your turn to buzz").queue();
               
        }
        else if(commandName.equals("nextQuestion"))
        {
            game.inProgress = true;
            game.buzzer.isLocked = false;
        }
        else if(commandName.equals("showTeamScores"))
        {
            String message = "";
            for(String name : game.teams.keySet())
            {
                message += name + ": " + game.teams.get(name).pointsScored + "\n";
            }
            event.reply(message).queue();
        }
        else if(commandName.equals("showPlayerScores"))
        {
            String message = "";
            for(String name : game.teams.keySet())
            {
                message += name + ":\n" ;
                for(Player player : game.teams.get(name).players)
                {
                    message += "\t" + player.user.getAsMention() + ": " + player.pointsScored + "\n";
                }
            }
            event.reply(message).queue();
        }
    }
    
    
    @Override
    public void onGuildReady(@NotNull GuildReadyEvent event)
    {
        List<CommandData> commandData = new ArrayList<>();
        commandData.add(Commands.slash("welcome", "Hello"));
        
        OptionData option1 = new OptionData(OptionType.STRING, "team_a_name", "Name for Team A", true);
        OptionData option2 = new OptionData(OptionType.STRING, "team_b_name", "Name for Team B. Enter 'none' if none.", true);
        OptionData option3 = new OptionData(OptionType.STRING, "team_c_name", "Name for Team C. Enter 'none' if none.", true);
        OptionData option4 = new OptionData(OptionType.ATTACHMENT, "packet_file", "File Path for HB Packet", true);
        OptionData option5 = new OptionData(OptionType.USER, "a1", "Player 1 for Team A", true);
        OptionData option6 = new OptionData(OptionType.STRING, "b1", "Player 1 for Team B", false);
        OptionData option7 = new OptionData(OptionType.STRING, "c1", "Player 1 for Team C", false);
        OptionData option8 = new OptionData(OptionType.USER, "a2", "Player 2 for Team A", false);
        OptionData option9 = new OptionData(OptionType.STRING, "b2", "Player 2 for Team B", false);
        OptionData option10 = new OptionData(OptionType.STRING, "c2", "Player 2 for Team C", false);
        OptionData option11 = new OptionData(OptionType.USER, "a3", "Player 3 for Team A", false);
        OptionData option12 = new OptionData(OptionType.STRING, "b3", "Player 3 for Team B", false);
        OptionData option13 = new OptionData(OptionType.STRING, "c3", "Player 3 for Team C", false);
        OptionData option14 = new OptionData(OptionType.USER, "a4", "Player 4 for Team A", false);
        OptionData option15 = new OptionData(OptionType.STRING, "b4", "Player 4 for Team B", false);
        OptionData option16 = new OptionData(OptionType.STRING, "c4", "Player 4 for Team C", false);
        OptionData option17 = new OptionData(OptionType.USER, "a5", "Player 5 for Team A", false);
        OptionData option18 = new OptionData(OptionType.STRING, "b5", "Player 5 for Team B", false);
        OptionData option19 = new OptionData(OptionType.STRING, "c5", "Player 5 for Team C", false);
        
        commandData.add(Commands.slash("begin", "Start a History Bowl Round").addOptions(option1, option2,
                option3, option4, option5, option6, option7, option8, option9, option10, option11, option12,
                option13, option14, option15, option16, option17,option18, option19));
        
        commandData.add(Commands.slash("buzz", "Buzz into a question"));
        
        commandData.add(Commands.slash("answer", "Answer a question").addOption(OptionType.STRING, "answer", "What your answer is"));
        
        commandData.add(Commands.slash("nextQuestion", "Answer a question"));
        
        commandData.add(Commands.slash("showTeamScores", "Answer a question"));
        
        commandData.add(Commands.slash("showPlayerScores", "Answer a question"));
        
        commandData.add(Commands.slash("answer", "Answer a question"));
        
        event.getGuild().updateCommands().addCommands(commandData).queue();
    }
}
