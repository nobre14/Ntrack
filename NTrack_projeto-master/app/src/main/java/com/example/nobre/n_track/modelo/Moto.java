package com.example.nobre.n_track.modelo;

import java.io.Serializable;

public class Moto implements Serializable{
    private Long id;
    private String marca;
    private String modelo;
    private int ano;
    private int cilndrada;

    public Moto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getCilndrada() {
        return cilndrada;
    }

    public void setCilndrada(int cilndrada) {
        this.cilndrada = cilndrada;
    }

    @Override
    public String toString() {
        return getId().toString() + " - " + getMarca() + " " + getModelo() + " " + getCilndrada();
    }
}
