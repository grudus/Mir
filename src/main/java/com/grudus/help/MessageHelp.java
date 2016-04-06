package com.grudus.help;


import java.util.ArrayList;

public class MessageHelp {

    public static ArrayList<String> findTags(final String message) {
        ArrayList<String> tags = new ArrayList<>();

        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '#') {
                for (int j = ++i; j < message.length(); j++) {
                    if (message.substring(j, j+1).matches("\\s")) {
                        tags.add(message.substring(i, j));
                        break;
                    }
                    if (j == message.length()-1) tags.add(message.substring(i, j+1));
                }
            }
        }

        return tags;

    }

}
