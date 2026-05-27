package controller;

import exception.Error;
import gui.*;
import model.*;
import java.time.LocalDate;

import java.util.ArrayList;

public class Controller {
    public Utente utente;
    public Ristorante ristorante;
    public AutenticazioneGui autenticazione_gui;
    public ClienteGui cliente_gui;
    public DipedenteGui dipedente_gui;
    public RiderGui rider_gui;

    private ArrayList<Ordine> ordini_globali = new ArrayList<>();

    //________________________________________________________________________________________________________________________________________________
    // Utente Controller

    public Error sign_in(String email, String password, String nickname, String nome, String cognome) {
        if (email == null || password == null || nickname == null || nome == null || cognome == null ||
                email.trim().isEmpty() || password.trim().isEmpty() || nickname.trim().isEmpty() || nome.trim().isEmpty() || cognome.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }
        this.utente = new Utente(email, password, nickname, nome, cognome);
        return Error.NESSUN_ERRORE;
    }

    public Error login(String email, String password) {
        if (email == null || password == null || email.trim().isEmpty() || password.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }
        return Error.NESSUN_ERRORE;
    }

    public void logout() {
        this.utente = null;
    }

    public Cliente get_cliente() {
        if (this.utente == null)
            return null;
        return new Cliente(this.utente);
    }

    public Rider get_rider() {
        if (this.utente == null)
            return null;
        return new Rider(this.utente);
    }

    public Dipedente get_dipendente() {
        if (this.utente == null)
            return null;
        return new Dipedente(this.utente, Ruolo.BASE, ristorante);
    }

    //________________________________________________________________________________________________________________________________________________
    // Dipendenti Controller

    public Error modifica_info_ristorante(Dipedente operatore, String nuovo_nome, String nuovo_indirizzo) {
        if (operatore == null || nuovo_nome == null || nuovo_indirizzo == null || nuovo_nome.trim().isEmpty() || nuovo_indirizzo.trim().isEmpty())
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() != Ruolo.MANAGER)
            return Error.PERMESSI_NON_SUFFICIENTI;

        this.ristorante.modifica_ristorante(nuovo_nome, nuovo_indirizzo);
        return Error.NESSUN_ERRORE;
    }

    public Error accetta_dipendente(Dipedente operatore, Utente candidato) {
        if (operatore == null || candidato == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        Dipedente nuovo_assunto = operatore.accetta_dipedente(candidato);
        if (nuovo_assunto == null)
            return Error.INPUT_NON_VALIDO;

        return Error.NESSUN_ERRORE;
    }

    public Error licenzia_dipendente(Dipedente operatore, Dipedente dipendente_da_rimuovere) {
        if (operatore == null || dipendente_da_rimuovere == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        if (operatore.equals(dipendente_da_rimuovere))
            return Error.INPUT_NON_VALIDO;

        operatore.rimuovi_dipedenti(dipendente_da_rimuovere);
        return Error.NESSUN_ERRORE;
    }

    public Error assegna_ruolo(Dipedente operatore, Dipedente dipendente, Ruolo nuovo_ruolo) {
        if (operatore == null || dipendente == null || nuovo_ruolo == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() != Ruolo.MANAGER)
            return Error.PERMESSI_NON_SUFFICIENTI;

        operatore.modifica_ruolo_dipente(dipendente, nuovo_ruolo);
        return Error.NESSUN_ERRORE;
    }

    public Error aggiungi_menu(Dipedente operatore, String nome_menu) {
        if (operatore == null || nome_menu == null || nome_menu.trim().isEmpty())
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        this.ristorante.crea_menu(nome_menu);
        return Error.NESSUN_ERRORE;
    }

    public Error elimina_menu(Dipedente operatore, Menu menu_da_rimuovere) {
        if (operatore == null || menu_da_rimuovere == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        this.ristorante.cancella_menu(menu_da_rimuovere);
        return Error.NESSUN_ERRORE;
    }

    public Error rinomina_menu(Dipedente operatore, Menu menu, String nuovo_nome) {
        if (operatore == null || menu == null || nuovo_nome == null || nuovo_nome.trim().isEmpty())
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        menu.modifica_menu(nuovo_nome);
        return Error.NESSUN_ERRORE;
    }

    public Error aggiungi_categoria(Dipedente operatore, Menu menu, String nome_categoria) {
        if (operatore == null || menu == null || nome_categoria == null || nome_categoria.trim().isEmpty()) {
            return Error.INPUT_NON_VALIDO;
        }
        if (operatore.get_ruolo() == Ruolo.BASE) {
            return Error.PERMESSI_NON_SUFFICIENTI;
        }

        menu.crea_categoria(nome_categoria);
        return Error.NESSUN_ERRORE;
    }

    public Error aggiungi_prodotto(Dipedente operatore, Menu menu, String nome, double prezzo_unitario, int qta_iniziale, String categoria) {
        if (operatore == null || menu == null || nome == null || nome.trim().isEmpty() || prezzo_unitario <= 0 || qta_iniziale < 0 || categoria == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;


        for (Prodotto p : menu.get_prodotti()) {
            if (p.get_nome().equalsIgnoreCase(nome))
                return Error.INPUT_NON_UNIVOCO;

        }

        menu.crea_prodotto(nome, prezzo_unitario, qta_iniziale, categoria);
        return Error.NESSUN_ERRORE;
    }

    public Error aggiorna_prezzo(Dipedente operatore, Prodotto prodotto, double nuovo_prezzo) {
        if (operatore == null || prodotto == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        return prodotto.set_prezzo_unitario(nuovo_prezzo);
    }

    public Error aggiorna_scorte(Dipedente operatore, Prodotto prodotto, int nuova_quantita) {
        if (operatore == null || prodotto == null)
            return Error.INPUT_NON_VALIDO;

        if (operatore.get_ruolo() == Ruolo.BASE)
            return Error.PERMESSI_NON_SUFFICIENTI;

        return prodotto.set_quantita(nuova_quantita);
    }

    //________________________________________________________________________________________________________________________________________________
    // Ordine Controller
    public class OrdineController {
    private Ordine ordine;

    public OrdineController(String indirizzo, String nota) {
        this.ordine = new Ordine(indirizzo, nota);
    }

    public void aggiungiProdotto(Prodotto prodotto, int quantita) {
        if (prodotto != null && quantita > 0) {
            ordine.aggiungi_riga(prodotto, quantita);
        }
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        ordine.rimuovi_riga(prodotto);
    }

    public Error applicaSconto(int puntiFedelta) {
        return ordine.applica_sconto(puntiFedelta);
    }

    public void aggiungiRiderProposto(Rider rider) {
        ordine.aggiungi_rider_proposto(rider);
    }

    public void rimuoviRiderProposto(Rider rider) {
        ordine.rimuovi_rider_proposto(rider);
    }

    public double getCostoOrdine() {
        return ordine.get_costo();
    }

    public StatoOrdine getStatoOrdine() {
        return ordine.get_stato_ordine();
    }

    public void setStatoOrdine(StatoOrdine stato) {
        ordine.set_stato_ordine(stato);
    }

    public String getIndirizzo() {
        return ordine.get_indirizzo();
    }

    public void setIndirizzo(String indirizzo) {
        ordine.set_indirizzo(indirizzo);
    }

    public String getNotaLuogoConsegna() {
        return ordine.get_nota_luogo_consegna();
    }

    public void setNotaLuogoConsegna(String nota) {
        ordine.set_nota_luogo_consegna(nota);
    }

    public ArrayList<Rider> getRiderProposti() {
        return ordine.get_rider_proposti();
    }

    public Rider getRider() {
        return ordine.get_rider();
    }

    public void setRider(Rider rider) {
        ordine.set_rider(rider);
    }

    public ArrayList<RigaOrdine> getRigheOrdine() {
        return ordine.get_righe_ordine();
    }

    public void setRigheOrdine(ArrayList<RigaOrdine> righe) {
        ordine.set_righe_ordine(righe);
    }

    public LocalDate getData() {
        return ordine.get_data();
    }

    public String getCodiceOrdine() {
        return ordine.get_codice_ordine();
    }

    public Ordine getOrdine() {
        return this.ordine;
    }
}



    //________________________________________________________________________________________________________________________________________________
    // Get and Set

    public Utente get_utente() {
        return utente;
    }

    public void set_utente(Utente utente) {
        this.utente = utente;
    }

    public Ristorante get_ristorante() {
        return ristorante;
    }

    public void set_ristorante(Ristorante ristorante) {
        this.ristorante = ristorante;
    }

    public AutenticazioneGui get_autenticazione_gui() {
        return autenticazione_gui;
    }

    public void set_autenticazione_gui(AutenticazioneGui autenticazione_gui) {
        this.autenticazione_gui = autenticazione_gui;
    }

    public ClienteGui get_cliente_gui() {
        return cliente_gui;
    }

    public void set_cliente_gui(ClienteGui cliente_gui) {
        this.cliente_gui = cliente_gui;
    }

    public DipedenteGui get_dipedente_gui() {
        return dipedente_gui;
    }

    public void set_dipedente_gui(DipedenteGui dipedente_gui) {
        this.dipedente_gui = dipedente_gui;
    }

    public RiderGui get_rider_gui() {
        return rider_gui;
    }

    public void set_rider_gui(RiderGui rider_gui) {
        this.rider_gui = rider_gui;
    }

    public ArrayList<Ordine> get_ordini_globali() {
        return ordini_globali;
    }

    public void set_ordini_globali(ArrayList<Ordine> ordini_globali) {
        this.ordini_globali = ordini_globali;
    }
}
