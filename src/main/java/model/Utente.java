package model;

public class Utente {
    private String email; //Unique (sul database)
    private String password;
    private String nickname; //Unique (sul database)
    private String nome;
    private String cognome;

    public Utente(String email, String password, String nickname, String nome, String cognome) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
    }

    public Utente(){}

    //________________________________________________________________________________________________________________________________________________
    //Override

    @Override
    public boolean equals(Object o){
        if(o.getClass().getName().equals("utente")) {
            Utente utente = (Utente) o;
            return (this == utente);
        }
        return false;
    }

    @Override
    public String toString(){
        return this.nickname;
    }

    //________________________________________________________________________________________________________________________________________________
    //Gestione Utente

    public void login(String email,String password){
        this.email = email;
        this.password = password;
    }

    public void sign_in(String email,String password,String nickname,String nome,String cognome){
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nome = nome;
        this.cognome = cognome;
    }

    //________________________________________________________________________________________________________________________________________________
    //

    public void richiedi_assunzione_ristorante(Utente utente,Ristorante ristorante,String codice_autenticazione){
        if(ristorante.get_codice_autenticazione().equals(codice_autenticazione))
            ristorante.richieste_assunzione.add(utente);
    }

    public Rider diventa_rider(String mezzo_trasporto){
        Rider rider = new Rider();
        rider.mezzo_trasporto = mezzo_trasporto;
        return rider;
    }

    public Ristorante registra_ristorante(String nome,String indirizzo){
        return new Ristorante(nome,indirizzo);
    }

    //________________________________________________________________________________________________________________________________________________
    //Get and Set

    public String get_email(){ return email; }
    public void set_email(String email){ this.email = email; }

    public String get_password(){ return password; }
    public void set_password(String password){ this.password = password; }

    public String get_nickname(){ return this.nickname; }
    public void set_nickname(String nickname){ this.nickname = nickname; }

    public String get_nome(){ return nome; }
    public void set_nome(String nome){ this.nome = nome; }

    public String get_cognome(){ return cognome; }
    public void set_cognome(String cognome){ this.cognome = cognome; }
}
