package model;

import exception.Error;
import java.util.ArrayList;

public class Menu {
    private String nome;
    private ArrayList<String> categorie;
    private ArrayList<Prodotto> prodotti;

    public Menu(String nomeMenu) {
        this.nome = nomeMenu;
        this.categorie = new ArrayList<>();
        this.prodotti = new ArrayList<>();
    }

    //____________________________________________________________________________________
    //Gestione prodotti

    public Error crea_prodotto(String nome, double prezzo_unitario, int quantita, String categoria) {
        for (Prodotto p : prodotti) {
            if (p.get_nome().equalsIgnoreCase(nome)) {
                return Error.INPUT_NON_UNIVOCO;
            }
        }

        if (!categorie.contains(categoria)) {
            return Error.INPUT_NON_VALIDO;
        }

        Prodotto nuovoProdotto = new Prodotto(nome, prezzo_unitario, categoria, quantita);
        prodotti.add(nuovoProdotto);
        return Error.NESSUN_ERRORE;
    }

    public Error elimina_prodotto(String nomeProdotto) {
        if (nomeProdotto == null || nomeProdotto.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }

        for (int i = 0; i < prodotti.size(); i++) {
            if (prodotti.get(i).get_nome().equalsIgnoreCase(nomeProdotto)) {
                prodotti.remove(i); // Prodotto trovato ed eliminato!
                return Error.NESSUN_ERRORE;
            }
        }
        return Error.INPUT_NON_VALIDO;
    }

    //____________________________________________________________________________________
    //Gestione Menu

    public void modifica_menu(String nome){
        this.nome = nome;
    }

    //____________________________________________________________________________________
    //Gestione categorie

    public Error crea_categoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }
        for (String cat : categorie) {
            if (cat.equalsIgnoreCase(nomeCategoria)) {
                return Error.INPUT_NON_UNIVOCO;
            }
        }
        categorie.add(nomeCategoria);
        return Error.NESSUN_ERRORE;
    }

    public Error elimina_categoria(String nomeCategoria) {
        if (nomeCategoria == null || nomeCategoria.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }
        boolean categoriaTrovata = false;
        for (int i = 0; i < categorie.size(); i++) {
            if (categorie.get(i).equalsIgnoreCase(nomeCategoria)) {
                categorie.remove(i);
                categoriaTrovata = true;
                break;
            }
        }
        if (!categoriaTrovata) {
            return Error.INPUT_NON_VALIDO;
        }
        for (int i = prodotti.size() - 1; i >= 0; i--) {
            if (prodotti.get(i).get_categoria().equalsIgnoreCase(nomeCategoria)) {
                prodotti.remove(i);
            }
        }
        return Error.NESSUN_ERRORE;
    }

    //____________________________________________________________________________________
    //Get and Set

    public String get_nome(){
        return nome;
    }
    public void set_nome(String nome) { this.nome = nome; }

    public ArrayList<String> get_categoria() { return categorie; }
    public void set_categorie(ArrayList<String> categorie) {this.categorie = categorie; }

    public ArrayList<Prodotto> get_prodotti() { return prodotti; }
    public void set_prodotti(ArrayList<Prodotto> prodotti) { this.prodotti = prodotti; }
}