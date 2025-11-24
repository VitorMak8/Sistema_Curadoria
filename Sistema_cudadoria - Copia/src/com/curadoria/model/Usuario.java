package com.curadoria.model;
public class Usuario {
    public int id; // Novo campo ID
    public String username;
    public String senha;
    public String nome;
    public int idade;
    public boolean admin;
    public boolean ativo;
    public String interesse1;
    public String interesse2;

    // Construtor Completo (Vindo do Banco)
    public Usuario(int id, String u, String s, String n, int i, boolean a, boolean at, String i1, String i2) {
        this.id = id; this.username = u; this.senha = s; this.nome = n;
        this.idade = i; this.admin = a; this.ativo = at; this.interesse1 = i1; this.interesse2 = i2;
    }

    // Construtor Simples (Novo Cadastro - ID 0)
    public Usuario(String u, String s, String n, int i, boolean a, boolean at, String i1, String i2) {
        this(0, u, s, n, i, a, at, i1, i2);
    }
}