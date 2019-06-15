package mEncryptServerGUI;

import java.util.Arrays;

public class EncryptAndDecrypt {

    public String encrypt(String message, String key) {
        System.out.println("The Message is: " + message);
        System.out.println("The Key is: " + key);

        String cypherBinary = "";
        String cypherText = "";


        String[] cypherValue = new String[message.length()];
        String[] str = new String[message.length()];
        int[] cypherInt = new int[message.length()];


        for (int i = 0; i < message.length(); i++) {

//            System.out.println("Message: " + (int) message.charAt(i) +
//                    " Binary = " + Integer.toBinaryString((int) message.charAt(i)));
//            System.out.println("Key: " + (int) key.charAt(i) +
//                    " Binary = " + Integer.toBinaryString((int) key.charAt(i)));

            cypherValue[i] = Integer.toBinaryString(message.charAt(i) ^ key.charAt(i));

            if (i == cypherValue.length - 1) {
                cypherBinary += cypherValue[i];
            } else {
                cypherBinary += cypherValue[i] + ",";
            }

        }

        System.out.println();
        System.out.println("The CypherBinary text: " + cypherBinary);
        System.out.println(Arrays.toString(cypherValue));


        for (int i = 0; i < message.length(); i++) {

            cypherInt[i] = Integer.parseInt(cypherValue[i], 2);

            cypherInt[i] = cypherInt[i];  // add the char to be greater than or equal to 33

            str[i] = Character.toString((char) cypherInt[i]);

            cypherText += str[i];

        }
        System.out.println();
        System.out.println(Arrays.toString(cypherInt));
        System.out.println(Arrays.toString(str));

        System.out.println();
        System.out.println("The Cypher text: " + cypherText);


        System.out.println("----------------------------------------");

        return cypherText;
    }

}
