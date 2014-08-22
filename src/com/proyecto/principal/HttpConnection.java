package com.proyecto.principal;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Bernardo Bonilla on 19/8/14.
 */
public class HttpConnection {
    private String urlString;

    public HttpConnection(){}
    public HttpConnection(String url){
        setUrl(url);
    }

    public String GET(){
        String content = null;
        URLConnection connection = null;
        try {
            connection =  new URL(urlString).openConnection();
            Scanner scanner = new Scanner(connection.getInputStream());
            scanner.useDelimiter("\\Z");
            content = scanner.next();
        }catch ( Exception ex ) {
            ex.printStackTrace();
        }
        return content;
    }

    public BufferedImage getImage(String source){
        //Image image = null;
        try {
            String url ="";
            URL connection;
            if(source.contains("http:")){
                url=source;
            }else if(source.startsWith("//")){
                url="http:"+source;
            }else{
               url=urlString+"/"+source;
            }
            connection = new URL(url);
            System.out.println(url);
            BufferedImage img = ImageIO.read(connection);
            /*image = ImageIO.read(connection);
            int w = image.getWidth(null);
            int h = image.getHeight(null);
            int type = BufferedImage.TYPE_INT_RGB;
            BufferedImage dest = new BufferedImage(w, h, type);
            Graphics2D g2 = dest.createGraphics();
            g2.drawImage(image, 0, 0, null);
            g2.dispose();*/
            return img;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }

    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrl(String url) {
        this.urlString = url;
    }

}
