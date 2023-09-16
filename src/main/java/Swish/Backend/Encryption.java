package Swish.Backend;

public class Encryption {

    private static int key = 14; //Encryption key

    public static String encrypt(String p){ //Method to encrypt the password.
        String encryptedPass = "";
        char[] chars = p.toCharArray(); //Converts the password from a string to a charArray.

        for(char c : chars){ //Loop to increase each value of the charArray on the ascii chart by the value of key.
            c += key;
            encryptedPass += c;
        }
        return encryptedPass; //Returns a string with the encrypted version of the password.
    }

    public static String decrypt(String p){
        String decryptedPass = "";

        char[] chars = p.toCharArray();

        for(char c : chars){
            c -= key;
            decryptedPass += c;

        }
        return decryptedPass;
    }
}


