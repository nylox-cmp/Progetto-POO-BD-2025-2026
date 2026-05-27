package model;

import exception.Error;

public class Prodotto {
    private String nome;
    private double prezzo_unitario;
    private String categoria = "Prodotti";
    private boolean disponibile;
    private int quantita;

    public Prodotto(String nome, double prezzo_unitario, String categoria, int quantita) {
        this.nome = nome;
        this.categoria = categoria;
        set_prezzo_unitario(prezzo_unitario);
        set_quantita(quantita);
    }

    //____________________________________________________________________________________
    //

    public boolean is_disponibile() {
        // Aggiorna in automatico lo stato in base alla quantità
        this.disponibile = (this.quantita >= 1);
        return this.disponibile;
    }

    //____________________________________________________________________________________
    //Get and Set

    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome){
        this.nome = nome;
    }

    public double get_prezzo_unitario(){
        return prezzo_unitario;
    }
    public Error set_prezzo_unitario(double prezzo_unitario){
        if(prezzo_unitario > 0) {
            this.prezzo_unitario = prezzo_unitario;
            return Error.NESSUN_ERRORE;
        }
        return Error.INPUT_NON_VALIDO;
    }

    public String get_categoria() {
        return categoria;
    }
    public void set_categoria(String categoria) {
        this.categoria = categoria;
    }

    public int get_quantita(){
        return quantita;
    }
    public Error set_quantita(int quantita){
        if(quantita >= 0){
            this.quantita = quantita;
            this.is_disponibile();
            return Error.NESSUN_ERRORE;
        }
        return Error.INPUT_NON_VALIDO;
    }

    public boolean get_disponibile(){
        return is_disponibile();
    }
}