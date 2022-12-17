package de.bitcoinclient.bitcoinapiplugin.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpUtil {

    public static void requestAPI() {

    }

    public static void login(String password, String channel) {
        try {
            URL url = new URL("updateserver.bitcoinclient.de:8080/users/user=api" + "/password=" + password + "/channel=" + channel);
            url.openConnection();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String requestAPIVersion(){
        return getHttp("updateserver.bitcoinclient.de:8080/version").split(",")[0].split("#")[1];
    }

    public static String requestPluginVersion(){
        return getHttp("updateserver.bitcoinclient.de:8080/version").split(",")[1].split("#")[1];
    }

    private static String getHttp(String url) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new URL(url).openStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {
                sb.append(line).append(System.lineSeparator());
            }
            br.close();
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
