package com.proyecto.principal;

import java.awt.image.BufferedImage;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Bernardo Bonilla on 20/8/14.
 */
public class Cache {
    private int tamañoDeCache;
    private ArrayList<ItemDeCache> tabla;
    public Cache(){
        setTabla(new ArrayList<ItemDeCache>());
    }

    public void agregar(String nombreArchivo,BufferedImage imagen,String pagina){
        for (int i = 0; i < tabla.size(); i++) {
            if(tabla.get(i).isEmpty()){
                tabla.set(i,new ItemDeCache(nombreArchivo,imagen,pagina));
                return;
            }
        }
    }

    public void limpiarCache(){
        for (int i = 0; i < tabla.size(); i++) {
            tabla.set(i,new ItemDeCache());
        }
    }

    public boolean tieneEspacio(){
        for (int i = 0; i < tabla.size(); i++) {
            if(tabla.get(i).isEmpty()){
                return true;
            }
        }
        return false;
    }


    public ItemDeCache buscarImagen(String find){
        for (int i = 0; i < tabla.size(); i++) {
            if(find.equals(tabla.get(i).getNombreArchivo())){
                return tabla.get(i);
            }
        }
        return null;
    }
    public void sumarVisita(String find){
        for (int i = 0; i < tabla.size(); i++) {
            if(find.equals(tabla.get(i).getNombreArchivo())){
                tabla.get(i).setNumeroDeAccesos(tabla.get(i).getNumeroDeAccesos()+1);
                java.util.Date date= new java.util.Date();
                Timestamp timeStamp = new Timestamp(date.getTime());
                tabla.get(i).setHoraUltimoAccesso(timeStamp);
            }
        }
    }

    public String imprimir(){
        String out ="";
        //out+= getNombreArchivo() +"\t|\t" + getTipoArchivo() +"\t|\t" + getHash() +"\t|\t" + getTamaño() +"\t|\t"  + getNumeroDeAccesos() +"\t|\t"  + getHoraUltimoAccesso() +"\t|\t";
        out+= "id\t|\t\t\tNombre\t\t\t|\tTipo\t|\t\t\t\tHash\t\t\t\t\t|\t\t\tPagina\t\t\t|\tTamaño\t|\t# Accesos\t|\tUltimo Acceso\t\t\t|\n";
        out+="------------------------------------------------------------------------------------------------------------------------------------------------------------------------|\n";
        for (int i = 0; i < tabla.size(); i++) {
            out+=i+"\t|\t"+tabla.get(i).toString();
        }
        out+="------------------------------------------------------------------------------------------------------------------------------------------------------------------------|\n";
        return out;
    }

    public int getTamañoDeCache() {
        return tamañoDeCache;
    }

    public void setTamañoDeCache(int tamañoDeCache) {
        for (int i = 0; i < tamañoDeCache; i++) {
            tabla.add(new ItemDeCache());
        }
        this.tamañoDeCache = tamañoDeCache;
    }

    public void swap(String nombreArchivo,BufferedImage imagen,String pagina, int indice){
        tabla.set(indice,new ItemDeCache(nombreArchivo,imagen,pagina));
    }

    public int masViejo(){
        java.util.Date date= new java.util.Date();
        Timestamp vijea = new Timestamp(date.getTime());
        int x = 0;
        for (int i = 0; i < tabla.size(); i++) {
            if(tabla.get(i).getHoraUltimoAccesso().before(vijea)){
                vijea = tabla.get(i).getHoraUltimoAccesso();
                x = i;
            }
        }
        return x;
    }
    public int masNuevo(){
        Timestamp nuevo=tabla.get(0).getHoraUltimoAccesso();
        int x = 0;
        for (int i = 1; i < tabla.size(); i++) {
            if(tabla.get(i).getHoraUltimoAccesso().after(nuevo)){
                nuevo = tabla.get(i).getHoraUltimoAccesso();
                x = i;
            }
        }
        return x;
    }

    public int menosUsado(){
        int menosUsado = 0;
        for (int i = 0; i < tabla.size(); i++) {
            if(tabla.get(i).getNumeroDeAccesos()<tabla.get(menosUsado).getNumeroDeAccesos()){
                menosUsado=i;
            }
        }
        return menosUsado;
    }



    public ArrayList<ItemDeCache> getTabla() {
        return tabla;
    }

    public void setTabla(ArrayList<ItemDeCache> tabla) {
        this.tabla = tabla;
    }
}
