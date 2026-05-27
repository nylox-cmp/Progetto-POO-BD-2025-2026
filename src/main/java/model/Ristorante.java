package model;

import exception.Error;

import java.net.URI;
import java.util.ArrayList;
import java.util.UUID;

public class Ristorante {
    private String nome;
    private String indirizzo;
    private String codice_autenticazione; //Unique ReadOnly
    private ArrayList<Menu> menu;

    private ArrayList<Dipedente> dipendenti;
    private ArrayList<Utente> richieste_assunzione;

    public Ristorante(String nome, String indirizzo) {
        this.nome = nome;
        this.indirizzo = indirizzo;
        this.codice_autenticazione = genera_codice_univoco();
    }

    public static String genera_codice_univoco(){
        return UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Menu

    public Error crea_menu(String nomeMenu){
        for(Menu m : menu) {
            if(m.get_nome().equalsIgnoreCase(nomeMenu)){
                return Error.INPUT_NON_UNIVOCO;
            }
        }

        Menu nuovoMenu = new Menu(nomeMenu);
        this.menu.add(nuovoMenu);
        return Error.NESSUN_ERRORE;
    }

    public void cancella_menu(Menu menu_eliminiare){
        menu.remove(menu_eliminiare);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public void modifica_ristorante(String nome,String indirizzo){
        this.nome = nome;
        this.indirizzo = indirizzo;
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_indirizzo(){ return indirizzo; }
    public void set_inidirizzo(String indirizzo){ this.indirizzo = indirizzo; }

    public ArrayList<Menu> get_menu(){ return menu; }
    public void set_menu(ArrayList<Menu> menu){ this.menu = menu; }

    public ArrayList<Utente> get_richieste_assunzioni(){ return richieste_assunzione; }
    public void set_richieste_assunzioni(ArrayList<Utente> richieste_assunzione){ this.richieste_assunzione = richieste_assunzione; }

    public String get_codice_autenticazione(){
        return codice_autenticazione;
    }

}