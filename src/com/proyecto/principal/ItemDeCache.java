package com.proyecto.principal;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;

/**
 * Created by Bernardo Bonilla on 20/8/14.
 */
public class ItemDeCache {
    private String pagina;
    private String nombreArchivo;
    private String nombreSimple;
    private BufferedImage imagen;
    private int numeroDeAccesos;
    private Timestamp horaUltimoAccesso;
    private String tipoArchivo;
    private int tamaño;
    private String hash;
    private boolean empty;
    public ItemDeCache(){
        setEmpty(true);
    }

    public ItemDeCache(String nombreArchivo, BufferedImage imagen,String pagina){
        setEmpty(false);
        setPagina(pagina);
        java.util.Date date= new java.util.Date();
        setHoraUltimoAccesso(new Timestamp(date.getTime()));
        setImagen(imagen);
        setNombreArchivo(nombreArchivo);
        setNumeroDeAccesos(1);
        setHash(main.hash(imagen));
        try {
            setTamaño(main.tamañoImagen(imagen));
        }catch(Exception e){
            System.out.println(e);
        }
        setNombreSimple();
        if(nombreArchivo.contains("jpg")){
            setTipoArchivo("jpg");
        }else if(nombreArchivo.contains("png")){
            setTipoArchivo("png");
        }else if(nombreArchivo.contains("gif")){
            setTipoArchivo("gif");
        }
    }

    public String toString(){
        String out = "";
        if(isEmpty()){
            out+= "\t\t\t\t\t|\t\t\t|\t\t\t\t\t\t\t\t\t\t|\t\t\t\t\t\t\t|\t\t\t|\t\t\t\t|\t\t\t\t\t\t\t|\n";
        }else{
            out+= getNombreSimple()+"\t|\t" + getTipoArchivo() +"\t\t|\t" + getHash() +"\t|\t"+ getPagina() + "\t|\t" + getTamaño() +"\t|\t\t"  + getNumeroDeAccesos() +"\t\t|\t"  + getHoraUltimoAccesso() +"\t|\t\n";
        }
        return out;
    }

    public void setNombreSimple(){
        String nombre[]= getNombreArchivo().split("/");
        for (int i = 0; i < nombre.length; i++) {
            //System.out.println(nombre[i]);
            if(nombre[i].contains("jpg")){
                this.nombreSimple = nombre[i];
            }else if(nombre[i].contains("png")){
                this.nombreSimple = nombre[i];
            }else if(nombre[i].contains("gif")){
                this.nombreSimple = nombre[i];
            }
        }
        if(getNombreSimple().length()>20){
            nombreSimple = getNombreSimple().substring(nombreSimple.length()-18);
        }else{
            nombreSimple = getNombreSimple()+"               ";
            nombreSimple = getNombreSimple().substring(0, 18);
        }
    }

    public String getNombreSimple(){
        return nombreSimple;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public void setNombreArchivo(String nombreArchivo) {
        this.nombreArchivo = nombreArchivo;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public int getNumeroDeAccesos() {
        return numeroDeAccesos;
    }

    public void setNumeroDeAccesos(int numeroDeAccesos) {
        this.numeroDeAccesos = numeroDeAccesos;
    }

    public Timestamp getHoraUltimoAccesso() {
        return horaUltimoAccesso;
    }

    public void setHoraUltimoAccesso(Timestamp horaUltimoAccesso) {
        this.horaUltimoAccesso = horaUltimoAccesso;
    }

    public String getTipoArchivo() {
        return tipoArchivo;
    }

    public void setTipoArchivo(String tipoArchivo) {
        this.tipoArchivo = tipoArchivo;
    }

    public int getTamaño() {
        return tamaño;
    }

    public void setTamaño(int tamaño) {
        this.tamaño = tamaño;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void setEmpty(boolean empty) {
        this.empty = empty;
    }

    public String getPagina() {
        return pagina;
    }

    public void setPagina(String pagina) {
        this.pagina = pagina;
        if(getPagina().length()>20){
            this.pagina = getPagina().substring(0, 20);
        }else{
            this.pagina = getPagina()+"                         ";
            this.pagina = getPagina().substring(0, 20);
        }
    }
}
