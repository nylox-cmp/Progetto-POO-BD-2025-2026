package model;


import java.util.ArrayList;

public class Dipedente extends Utente{
    private ArrayList<Dipedente> subordinati;
    private ArrayList<Dipedente> superiori;
    private Ruolo ruolo;
    private Ristorante ristorante;
    private ArrayList<Ordine> ordini;


    public Dipedente(Utente utente, Ruolo ruolo, Ristorante ristorante) {
        super(utente.get_email(), utente.get_password(), utente.get_nickname(), utente.get_nome(), utente.get_cognome());
        this.ruolo = ruolo;
        this.ristorante = ristorante;
        this.subordinati = new ArrayList<>();
        this.superiori = new ArrayList<>();
        this.ordini = new ArrayList<>();
    }

    public Dipedente() {
        super();
        this.subordinati = new ArrayList<>();
        this.superiori = new ArrayList<>();
        this.ordini = new ArrayList<>();
    }

    @Override
    public String toString(){
        String identificativo = super.get_nickname() + " " + Ruolo.converti_ruolo_to_string(ruolo);
        return identificativo;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Dipedenti

    public boolean puo_eseguire(Ruolo ruolo_richiesto){
        return (ruolo.ordinal() <= ruolo_richiesto.ordinal());
    }

    public Dipedente accetta_dipedente(Utente utente){
        if(ristorante.get_richieste_assunzioni().contains(utente)){
            Dipedente nuovo_dipedente = new Dipedente();
            subordinati.add(nuovo_dipedente);
            return nuovo_dipedente;
        }
        return null;
    }

    public void rimuovi_dipedenti(Dipedente dipedente){
        if((dipedente.equals(dipedente) == false) && (puo_eseguire(Ruolo.GESTIONALE))){
            if(subordinati.contains(dipedente))
                subordinati.remove(dipedente);
        }
    }

    public void modifica_ruolo_dipente(Dipedente dipedente,Ruolo ruolo){
        if((dipedente.equals(dipedente) == false) && (puo_eseguire(Ruolo.MANAGER))){
            if(subordinati.contains(dipedente))
                dipedente.ruolo = ruolo;
        }
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ristorante

    public void modica_risorante(String nome,String indirizzo,Ristorante ristorante){
        ristorante.set_nome(nome);
        ristorante.set_inidirizzo(indirizzo);
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Ordine

    public void accetta_rider(Ordine ordine,Rider rider){
        if(ordini.contains(ordine)){
            if(ordine.get_rider_proposti().contains(rider))
                ordine.set_rider(rider);
        }
    }

    public void segnala_ordine_pronto_ritiro(Ordine ordine){
        if((ordine.get_stato_ordine() == StatoOrdine.PREPARAZIONE) && (ordini.contains(ordine)))
            ordine.set_stato_ordine(StatoOrdine.PRONTO_RITIRO);
    }

    public void cancella_ordine(Ordine ordine) {
        if (ordini.contains(ordine))
            ordine.set_stato_ordine(StatoOrdine.ANNULATO);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public ArrayList<Dipedente> get_subordinati() { return subordinati; }
    public void set_subordinati(ArrayList<Dipedente> subordinati) { this.subordinati = subordinati; }

    public ArrayList<Dipedente> get_superiori() { return superiori; }
    public void set_superiori(ArrayList<Dipedente> superiori) { this.superiori = superiori; }

    public Ruolo get_ruolo() { return ruolo; }
    public void set_ruolo(Ruolo ruolo) { this.ruolo = ruolo; }

    public Ristorante get_ristorante() { return ristorante; }
    public void set_ristorante(Ristorante ristorante) { this.ristorante = ristorante; }

    public ArrayList<Ordine> get_ordini() { return ordini; }
    public void set_ordini(ArrayList<Ordine> ordini) { this.ordini = ordini; }
}
