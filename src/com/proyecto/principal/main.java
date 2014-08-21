package com.proyecto.principal;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

/**
 * Created by Bernardo Bonilla on 19/8/14.
 */
public class main {
    public static void main(String[] args) {
        escribir("********* Programa Empezado *********");
        Scanner scan = new Scanner(System.in);
        Cache cache = new Cache();
        System.out.println("tamaño de cache (n elementos):");
        cache.setTamañoDeCache(Integer.parseInt(scan.next()));
        escribir("Tamaño de cache definida: "+cache.getTamañoDeCache());
        while(true){
            System.out.println("Opciones:\n1) Accesar pagina\t2) Ver cache\t3) Limpiar Cache\t4) Salir");
            int opcion = Integer.parseInt(scan.next());
            switch(opcion){
                case 1:
                    escribir("Accesar Pagina");
                    System.out.println("URL: \nb) bernardobc.github.io\tg) google.com\tn) nacion.com\tc) cancelar");
                    String s = scan.next();
                    if(s.equals("b")){
                        s = "bernardobc.github.io";
                    }
                    else if(s.equals("g")){
                        s = "google.com";
                    }
                    else if(s.equals("n")){
                        s = "nacion.com";
                    }else if(s.equals("c")){
                        break;
                    }
                    String urlInput = s;
                    String url = "http://"+urlInput;
                    escribir("Direccion: "+urlInput);
                    String directory = System.getProperty("user.dir")+"/descarga/"+urlInput+"/";
                    //url = "http://github.com/bernardobc";
                    File outputfolder = new File(System.getProperty("user.dir")+"/descarga/"+urlInput);
                    if(!outputfolder.exists()){
                        mkdir(urlInput);
                        escribir("Directorio creado");
                    }else{
                        escribir("Directorio encontrado");
                    }


                    HttpConnection httpConnect = new HttpConnection(url);
                    String html = httpConnect.GET();
                    ArrayList<String> parsed = parser(html+"\n\n");
                    ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
                    for (int i = 0; i < parsed.size(); i++) {
                        //System.out.println(parsed.get(i));
                        escribir("imagen encontrada en pagina web: "+parsed.get(i));
                        images.add(httpConnect.getImage(parsed.get(i)));
                        escribir("buscando imagen en cache");
                        ItemDeCache imagen = cache.buscarImagen(parsed.get(i));
                        if(imagen != null){
                            escribir("imagen encontrada en cache");
                            escribir("comparando...");
                            if(igual(images.get(i),imagen)){
                                escribir("las imagenes son iguales.");
                                cache.sumarVisita(parsed.get(i));
                            }else{
                                escribir("las imagenes son diferentes");
                            }
                        }else{
                            escribir("imagen no econtrada en cache");
                            if(cache.tieneEspacio()){
                                escribir("cache tiene espacio libre. agregando imagen a cache");
                                cache.agregar(parsed.get(i),images.get(i),urlInput);
                            }
                            escribir("buscando imagen en \"descarga\"");
                            File outputfile = new File(directory+parsed.get(i));
                            if(outputfile.exists()){
                                escribir("imagen encontrada en \"descarca\"");
                                BufferedImage imagenGuardada=null;
                                try{
                                    imagenGuardada = ImageIO.read(outputfile);
                                }catch(Exception e){
                                    escribir("Imagen fallo en cargar: "+e);
                                }
                                escribir("comparando hash");
                                if(!hash(images.get(i)).equals(hash(imagenGuardada))){
                                    escribir("hash no es igual. Escribiendo en \"descarga\"");
                                    try{
                                        if(parsed.get(i).contains("jpg")){
                                            ImageIO.write(images.get(i), "jpg", outputfile);
                                        }
                                        else if(parsed.get(i).contains("png")){
                                            ImageIO.write(images.get(i), "png", outputfile);
                                        }
                                        else if(parsed.get(i).contains("gif")){
                                            ImageIO.write(images.get(i), "gif", outputfile);
                                        }
                                    }catch(Exception e){
                                        System.out.println(e);
                                    }
                                }
                            }
                            else{
                                try{
                                    escribir("imagen no encontrada en descarga");
                                    outputfile.getParentFile().mkdirs();
                                    if(parsed.get(i).contains("jpg")){
                                        ImageIO.write(images.get(i), "jpg", outputfile);
                                    }
                                    else if(parsed.get(i).contains("png")){
                                        ImageIO.write(images.get(i), "png", outputfile);
                                    }
                                    else if(parsed.get(i).contains("gif")){
                                        ImageIO.write(images.get(i), "gif", outputfile);
                                    }
                                    escribir("imagen guardada en "+outputfile.toString());

                                }catch(Exception e){
                                    System.out.println(e);
                                }
                            }
                        }
                    }
                    break;
                //ver cache
                case 2:
                    escribir("Ver Cache\n");
                    System.out.println(cache.imprimir());
                    break;
                //limpiar cache
                case 3:
                    escribir("Limpiar Cache");
                    cache.limpiarCache();
                    break;
                //salir
                case 4:
                    escribir("Programa Terminado");
                    System.exit(0);
                    break;
                default:
                    break;
            }
        }
    }

    public static ArrayList<String> parser(String toParse){
        ArrayList<String> parsed = new ArrayList<String>();
        ArrayList<String> returned = new ArrayList<String>();
        for (int i=0; i<toParse.length(); i++){
            String helper="";
            if(toParse.charAt(i)=='<'&&toParse.charAt(i+1)=='i'&&toParse.charAt(i+2)=='m'&&toParse.charAt(i+3)=='g'){
                while(toParse.charAt(i)!='>'){
                    helper += toParse.charAt(i);
                    i++;
                }
                helper += toParse.charAt(i);
                if(helper.contains("\"")){
                    helper = helper.substring(helper.indexOf("\"") + 1, helper.lastIndexOf("\""));
                }
                parsed.add(helper);
            }
        }
        for (int i = 0; i < parsed.size(); i++) {
            String segments[] = parsed.get(i).split("\"");
            for (int j = 0; j < segments.length; j++) {
                if(segments[j].endsWith("png")||segments[j].endsWith("jpg")||segments[j].endsWith("gif")){
                    returned.add(segments[j]);
                    //System.out.println(segments[j]);
                }
            }
        }
        return returned;
    }

    public static String hash(BufferedImage image){
        try{
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", outputStream);
            byte[] data = outputStream.toByteArray();
            //System.out.println("Start MD5 Digest");
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data);
            byte[] hash = md.digest();
            return toStringHash(hash);
        }catch(Exception e){
            System.out.println(e);
        }
        return null;
    }

    public static String toStringHash(byte[] digest) throws Exception{
        StringBuffer sb = new StringBuffer();
        for (byte b : digest) {
            sb.append(String.format("%02x", b & 0xff));
        }
        return sb.toString();
    }

    public static void mkdir(String folder){
        File Dir = new File(System.getProperty("user.dir")+"/descarga/"+folder);
        if (!Dir.exists()) {
            //System.out.println("creating directory: " + folder);
            try{
                Dir.getParentFile().mkdirs();
                Dir.mkdir();
                //FileWriter writer = new FileWriter(file);

            } catch(SecurityException se){
                System.out.println(se);
            }
        }
    }

    public static void escribir(String mensaje){
        log.write(mensaje);
        System.out.println(mensaje);
    }

    public static boolean igual(BufferedImage nueva, ItemDeCache vieja){
        String nuevaImagen = hash(nueva);
        escribir("nueva: "+nuevaImagen);
        String viejaImagen = vieja.getHash();
        escribir("vieja: "+viejaImagen);
        if(nuevaImagen.equals(viejaImagen)){
            return true;
        }else{
            return false;
        }
    }

    public static int tamañoImagen(BufferedImage imagen) throws Exception{
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(imagen, "jpg", outputStream);
        outputStream.close();
        return outputStream.size();
    }


}