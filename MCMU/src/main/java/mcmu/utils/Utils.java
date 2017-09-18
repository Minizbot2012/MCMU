package mcmu.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Utils {
    public static byte[] getBytes(InputStream is) throws IOException {
        int size = 1024;
        byte[] buf;
        if ((is instanceof ByteArrayInputStream)) {
            size = is.available();
            buf = new byte[size];
        } else {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            buf = new byte[size];
            int len;
            while ((len = is.read(buf, 0, size)) != -1)
                bos.write(buf, 0, len);
            buf = bos.toByteArray();
        }
        return buf;
    }

    public static String MD5(InputStream fis) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] array = md.digest(getBytes(fis));
            StringBuilder sb = new StringBuilder();
            for (byte anArray : array) {
                sb.append(Integer.toHexString(anArray & 0xFF | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException ignored) {
        } catch (IOException ex) {
            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    public static String getString(String loc) {
        try {
            return new Scanner(new URL(loc).openStream(), "UTF-8").useDelimiter("\\A").next();
        } catch (IOException e) {
            System.out.println("Error in loading URL: "+loc);
        }
        return "";
    }
    public static byte[] getFile(String Addr) {
        byte[] bytes;
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(Addr).openConnection();
            conn.setInstanceFollowRedirects(true);
            conn.connect();
            switch (conn.getResponseCode()) {
                case 307:
                case 302:
                    String newURL = conn.getHeaderField("Location");
                    return getFile(newURL);
            }
            bytes = Utils.getBytes(conn.getInputStream());
        } catch (MalformedURLException ex) {
            System.out.println("Malformed URL in index: " + Addr);
            return null;
        } catch (IOException ex) {
            System.out.println("Unable to download mod at: " + Addr);
            System.out.println(ex.getMessage());
            return null;
        }
        return bytes;
    }
}
