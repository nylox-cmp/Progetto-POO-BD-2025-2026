package exception;

public enum Error{
    NESSUN_ERRORE,
    LOGIN_FALLITO,
    INPUT_NON_VALIDO,
    INPUT_NON_UNIVOCO,
    PERMESSI_NON_SUFFICIENTI;


    public String conver_error_to_message(Error error){
        String messaggio = "";
        switch (error){
            case NESSUN_ERRORE:
                break;
            case LOGIN_FALLITO:
                messaggio = "email o password errata ";
                break;
            case INPUT_NON_VALIDO:
                messaggio = "l'input inserito non è valido";
                break;
            case INPUT_NON_UNIVOCO:
                messaggio = "l'input deve essere univoco per essere accettabile ";
                break;
            case PERMESSI_NON_SUFFICIENTI:
                messaggio = "l'operazione selezionata non è eseguibile, con i permessi di questo ruolo";
        }
        return messaggio;
    }
}
