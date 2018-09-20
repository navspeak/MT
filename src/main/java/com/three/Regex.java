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
//       System.out.println("Enter the regex:");
//        String regex = (new Scanner(System.in)).nextLine();
//        System.out.println("Enter the Sentence");
//        String sentence = (new Scanner(System.in)).nextLine();
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(sentence);
//        while(matcher.find()){
//            System.out.println("Found " + matcher.group() + " at start index: " + matcher.start() +
//                    " end index: " + matcher.end() + " ");
//
//        }

        Pattern p1 = Pattern.compile("admins?");
        Pattern p1_1 = Pattern.compile("admins?$"); // admin and admins 0 or 1 times
        Pattern p1_2 = Pattern.compile("admins*$"); // admin, admins and adminsss 0 or n times
        Pattern p2 = Pattern.compile("[^:;|=,<>]+"); // one or more instance
        Pattern p3 = Pattern.compile("(\\d{3}[-.])+\\d{4}");
        //(\d{3}[-.]) digit character extactly three times followed by - or .
        // repeated one or more times
        // followed by 4 digits
        System.out.println("admin".matches("admins?$")); //true
        System.out.println("admins".matches("admins?$")); //true
        System.out.println("admins".matches("admin|admins")); //true
        System.out.println("adminss".matches("admins?$")); //false
        System.out.println("adminss".matches("admins?")); //false
        System.out.println("adminstrator ".matches("admins?")); //false

        Matcher m1 = p1_1.matcher("adminstrator");
        while (m1.find()){ // no find
            System.out.println("Found " + m1.group() + " at start index: " + m1.start() +
                    " end index: " + m1.end() + " ");
        }

        System.out.println("402-583-5366".matches("(\\d{3}[-.])+\\d{4}")); //true
        System.out.println("402-583-5366999".matches("(\\d{3}[-.])+\\d{4}")); //false
        System.out.println("===================");
        System.out.println("expression!".matches("..........."));//true
        System.out.println("expression!".matches(".{11}"));//true
        System.out.println("expression!".matches(".*"));//true
        System.out.println("!e!e!e".matches("(!\\w)+"));//true
        System.out.println("!e!e!e".matches("(!\\w){3}"));//true
        System.out.println("===================");

        System.out.println("1-760-410-9010".matches("\\d?-?\\d{3}-\\d{4}|\\d{3,5}"));
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
// .* => any character 0 or more time
// .+ => any character 1 or more time
// .? => any character 0 or 1 time
// \d?-?\d{3}-\d{3}-\d{4}|\d{3,4} => 1-760-410-9010 or -760-410-9010 or 760-410-9010 or 9010 and also 9010888
// \b\d{3,4}\b or d{3,4}$
    }

}
