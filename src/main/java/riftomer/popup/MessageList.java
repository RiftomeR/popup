package riftomer.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MessageList {
    public static final List<String> messages = new ArrayList<>();
    public static final List<String> yesTexts = new ArrayList<>();
    public static final List<String> noTexts = new ArrayList<>();
    private static Random random;

    public static void init(){
        random = new Random(System.nanoTime());
        messages.add("You are about to quit. Are you sure?");
        messages.add("Are you sure?");
        messages.add("Do you want to quit?");
        yesTexts.add("Yes");
        yesTexts.add("Yes...");
        noTexts.add("No");
        noTexts.add("Cancel");
    }

    public static String getRandomMSG(){
        return messages.get(random.nextInt(messages.size()-1));
    }

    public static String getRandomYesMSG(){
        return yesTexts.get(random.nextInt(messages.size()-1));
    }

    public static String getRandomNoMSG(){
        return noTexts.get(random.nextInt(messages.size()-1));
    }
}
