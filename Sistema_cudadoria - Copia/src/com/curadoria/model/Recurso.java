package com.curadoria.model;
public class Recurso {
    public int id;
    public String titulo;
    public String autor;
    public String categoria;
    public String tipo;
    public String cadastradoPor;

    
    public Recurso(int id, String t, String a, String c, String tp, String cp) {
        this.id = id; this.titulo = t; this.autor = a; 
        this.categoria = c; this.tipo = tp; this.cadastradoPor = cp;
    }

   
    public Recurso(String t, String a, String c, String tp, String cp) {
        this(0, t, a, c, tp, cp);
    }
}
