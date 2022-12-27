package de.bitcoinclient.crypter;

public class Crypter {

    //Verschlüssen 2.0
    public static String encode(String value) {
        String message = "";

        for(int i = 0;i < value.length(); i++) {
            String s = value.substring(i,i+1);

            for(CrypterTypes types : CrypterTypes.values()) {
                if(types.getLetter().equals(s)) {
                    message = message + types.getCrypt();
                }
            }

        }
        return message;
    }

    //Entschlüsseln 2
    public static String decode(String value) {
        String message = "";
        for(int i = 0; i < (value.length() / 3); i++) {
            if(i == 0) {
                String s = value.substring(0,3);
                for(CrypterTypes types : CrypterTypes.values()) {
                    if(String.valueOf(types.getCrypt()).equalsIgnoreCase(s)) {
                        message = message + types.getLetter();
                    }
                }
                continue;
            }
            String s = value.substring(3*i,(3*i+3));
            for(CrypterTypes types : CrypterTypes.values()) {
                if(String.valueOf(types.getCrypt()).equalsIgnoreCase(s)) {
                    message = message + types.getLetter();
                }
            }
        }
        return message;
    }
}
