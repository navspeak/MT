package com.three;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regex {

    public static void main(String[] args) {
        System.out.println("gray".matches("gr[^ae]y") == true ? "yes" : "no" );
        // character classes: [ae], char range class: [a-eA-E] negated char classes [^aef], negated char class range[^a-e]
        System.out.println("gray".replaceAll("[ay]", "ai") );//graiai
        System.out.println("gray".replaceAll("(ay)", "ai") );//grai
        System.out.println("Enter the regex:");
        String regex = (new Scanner(System.in)).nextLine();
        System.out.println("Enter the Sentence");
        String sentence = (new Scanner(System.in)).nextLine();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sentence);
        while(matcher.find()){
            System.out.print("Start index: " + matcher.start());
            System.out.print(" End index: " + matcher.end() + " ");
            System.out.println(matcher.group());
        }

//        Short hand Metachar:
//\w [a-zA-Z0-9_] word char
//\s whitespace char
//\d [0-9] digit char
//\W [^a-zA-Z0-9_] non word char
//\S non-whitespace char
//\D [^0-9] non-digit char
//                . any char
//\n new line char
//\t tab char
//\r carriage return char
//
//                Anchor
//        word boundries - b4 and after a word char
//\b = word boundry
//        e.g. \bdog\b The dog is good => start4,
//                end 7 match dog
//\B = non word boundry
//        e.g \bdog\B The dog is good => no match
//        e.g \bdog\B The doggie is good => start4,
//                end 7 match dog
//
//        Quantifiers
//        admins?$ admin, admins
    }

}
