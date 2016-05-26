package com.example.csit451sapien.jigsaw;

/**
 * Created by Brian on 4/18/2016.
 */
public class SharedValues {
    public static int timesPlayed;
    public static int timesWon;
    public static String username;
    public static int moves;

    public SharedValues() {
        moves =timesPlayed = timesWon = 0;
        username = "";
    }
}
