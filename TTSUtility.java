///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package com.mycompany.greenninja.game;
//
//import com.google.cloud.texttospeech.v1.*;
//import com.google.protobuf.ByteString;
//import net.dv8tion.jda.api.audio.AudioReceiveHandler;
//import net.dv8tion.jda.api.audio.AudioReceiveHandlerImpl;
//import net.dv8tion.jda.api.audio.AudioSendHandler;
//import net.dv8tion.jda.api.audio.AudioSendHandlerImpl;
//import net.dv8tion.jda.api.entities.Message;
//import net.dv8tion.jda.api.entities.TextChannel;
//import net.dv8tion.jda.api.entities.User;
//import net.dv8tion.jda.api.hooks.ListenerAdapter;
//import net.dv8tion.jda.api.managers.AudioManager;
//
//
//
//
//
//import com.google.cloud.texttospeech.v1.*;
//import com.google.protobuf.ByteString;
//import net.dv8tion.jda.api.*;
//import net.dv8tion.jda.api.audio.AudioReceiveHandler;
//import net.dv8tion.jda.api.audio.UserAudio;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//import java.io.FileOutputStream;
//import java.io.OutputStream;
//
//public class TTSBot extends ListenerAdapter {
//
//    private final String GOOGLE_CLOUD_API_KEY = "YOUR_GOOGLE_CLOUD_API_KEY"; // Replace with your actual API key
//
//    @Override
//    public void onMessageReceived(MessageReceivedEvent event) {
//        User author = event.getAuthor();
//        TextChannel channel = event.getTextChannel();
//        Message message = event.getMessage();
//        String content = message.getContentRaw();
//
//        if (content.startsWith("$tts ")) {
//            String textToSpeak = content.substring(5); // Extract the text after "$tts "
//
//            // Generate the speech------------------------------------------------------------------------------
//            try (TextToSpeechClient textToSpeechClient = TextToSpeechClient.create()) {
//                SynthesisInput input = SynthesisInput.newBuilder().setText(textToSpeak).build();
//                VoiceSelectionParams voice = VoiceSelectionParams.newBuilder()
//                        .setLanguageCode("en-US")
//                        .setSsmlGender(SsmlVoiceGender.NEUTRAL)
//                        .build();
//
//                AudioConfig audioConfig = AudioConfig.newBuilder()
//                        .setAudioEncoding(AudioEncoding.LINEAR16) // Use LINEAR16 for Opus audio
//                        .build();
//
//                SynthesizeSpeechResponse response = textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
//
//                ByteString audioContents = response.getAudioContent();
//                //--------------------------------
//                
//                
//                
//                
//                // Join the voice channel and play TTS audio
//                AudioManager audioManager = event.getGuild().getAudioManager();
//                audioManager.openAudioConnection(author.getVoiceState().getChannel());
//
//                AudioSendHandler audioSendHandler = ;
//                = new AudioSendHandlerImpl(audioContents.toByteArray());
//                AudioReceiveHandler audioReceiveHandler = new AudioReceiveHandlerImpl();
//                audioManager.setSendingHandler(audioSendHandler);
//                audioManager.setReceivingHandler(audioReceiveHandler);
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        // Initialize your JDA bot and add the TTSBot as an event listener
//        JDA jda = // Your JDA initialization code here
//        jda.addEventListener(new TTSBot());
//    }
//}
//
//
//
//
//
//
//
//public class TTSBot {
//
//    public static void main(String[] args) {
//        // Replace "YOUR_GOOGLE_CLOUD_API_KEY" with your actual Google Cloud API key or service account key file path
//        String googleApiKey = "YOUR_GOOGLE_CLOUD_API_KEY";
//
//        JDABuilder jdaBuilder = JDABuilder.createDefault("YOUR_BOT_TOKEN");
//        try {
//            JDA jda = jdaBuilder.build();
//            jda.awaitReady();
//
//            // Get the voice channel where you want the bot to join
//            VoiceChannel voiceChannel = jda.getVoiceChannelById("YOUR_VOICE_CHANNEL_ID");
//
//            if (voiceChannel != null) {
//                voiceChannel.getManager().queue(vc -> {
//                    try {
//                        // Join the voice channel
//                        AudioConnection audioConnection = vc.join().complete();
//
//                        // Text message to be synthesized into speech
//                        String textMessage = "Hello, this is a text-to-speech message using Google Cloud Text-to-Speech API.";
//
//                        // Create the text-to-speech client
//                        TextToSpeechClient textToSpeechClient = TextToSpeechClient.create();
//
//                        // Set up the input for the API
//                        SynthesisInput input = SynthesisInput.newBuilder().setText(textMessage).build();
//
//                        // Build the voice parameters
//                        VoiceSelectionParams voice =
//                                VoiceSelectionParams.newBuilder()
//                                        .setLanguageCode("en-US")
//                                        .setSsmlGender(SsmlVoiceGender.NEUTRAL)
//                                        .build();
//
//                        // Select the audio format
//                        AudioConfig audioConfig =
//                                AudioConfig.newBuilder().setAudioEncoding(AudioEncoding.MP3).build();
//
//                        // Perform the text-to-speech synthesis
//                        SynthesizeSpeechResponse response =
//                                textToSpeechClient.synthesizeSpeech(input, voice, audioConfig);
//
//                        // Get the audio content
//                        ByteString audioContent = response.getAudioContent();
//
//                        // Save the audio content to a temporary file
//                        Path tempAudioFile = Paths.get("tempAudio.mp3");
//                        Files.write(tempAudioFile, audioContent.toByteArray());
//
//                        // Play the audio in the voice channel
//                        audioConnection.getAudioSender().sendFile(tempAudioFile.toFile());
//
//                        // Delete the temporary file
//                        Files.delete(tempAudioFile);
//
//                        // Close the text-to-speech client
//                        textToSpeechClient.close();
//
//                        // Disconnect after sending the message
//                        audioConnection.disconnect();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                });
//            } else {
//                System.out.println("Voice channel not found!");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
