package dto;

import java.util.Calendar;
import java.util.Scanner;
import java.util.GregorianCalendar;


public class Utente {

	private String nome;
	private String cognome;
	private String username;
	private String password;
	private Calendar data_nascita;
	private String luogo_domicilio;
	private String ruolo;
	private Ristorante[] preferiti;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		cognome = cognome;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		password = password;
	}

	public Calendar getData_nascita() {
		return data_nascita;
	}

	public void setData_nascita(Calendar data_nascita) {
		data_nascita = data_nascita;
	}

	public String getLuogo_domicilio() {
		return luogo_domicilio;
	}

	public void setLuogo_domicilio(String luogo_domicilio) {
		luogo_domicilio = luogo_domicilio;
	}

	public String getRuolo() {
		return ruolo;
	}

	public void setRuolo(String ruolo) {
		this.ruolo = ruolo;
	}

	public Ristorante[] getPreferiti() {
		return preferiti;
	}

	public void setPreferiti(Ristorante[] preferiti) {
		this.preferiti = preferiti;
	}
        
        public static Utente registrazione() {
    Scanner scanner = new Scanner(System.in);
    Utente nuovoUtente = new Utente();

    System.out.println("Registrazione nuovo utente:");

    
    String nome;
    do {
        System.out.print("Nome: ");
        nome = scanner.nextLine().trim();
        if (nome.isEmpty()) System.out.println("Il nome non può essere vuoto.");
    } while (nome.isEmpty());
    nuovoUtente.setNome(nome);

    
    String cognome;
    do {
        System.out.print("Cognome: ");
        cognome = scanner.nextLine().trim();
        if (cognome.isEmpty()) System.out.println("Il cognome non può essere vuoto.");
    } while (cognome.isEmpty());
    nuovoUtente.setCognome(cognome);

   
    String username;
    do {
        System.out.print("Username: ");
        username = scanner.nextLine().trim();
        if (username.isEmpty()) System.out.println("Lo username non può essere vuoto.");
    } while (username.isEmpty());
    nuovoUtente.setUsername(username);

    
    String password;
    do {
        System.out.print("Password: ");
        password = scanner.nextLine();
        if (password.isEmpty()) System.out.println("La password non può essere vuota.");
    } while (password.isEmpty());
    nuovoUtente.setPassword(password);

    
    String domicilio;
    do {
        System.out.print("Luogo di domicilio: ");
        domicilio = scanner.nextLine().trim();
        if (domicilio.isEmpty()) System.out.println("Il luogo di domicilio non può essere vuoto.");
    } while (domicilio.isEmpty());
    nuovoUtente.setLuogo_domicilio(domicilio);

    
    String ruolo;
    do {
        System.out.print("Ruolo (cliente o ristoratore): ");
        ruolo = scanner.nextLine().trim().toLowerCase();
        if (!ruolo.equals("cliente") && !ruolo.equals("ristoratore")) {
            System.out.println("Ruolo non valido. Inserire 'cliente' o 'ristoratore'.");
        }
    } while (!ruolo.equals("cliente") && !ruolo.equals("ristoratore"));
    nuovoUtente.setRuolo(ruolo);

    
    System.out.println("Inserire data di nascita:");
    int anno = 0, mese = 0, giorno = 0;
    int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);

    do {
        System.out.print("Anno: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Inserisci un numero valido per l'anno: ");
            scanner.next();
        }
        anno = scanner.nextInt();
        if (anno < 1900 || anno > annoCorrente - 18) {
            System.out.println("Anno non valido. Devi avere almeno 18 anni.");
        }
    } while (anno < 1900 || anno > annoCorrente - 18);

    
    do {
        System.out.print("Mese (1-12): ");
        while (!scanner.hasNextInt()) {
            System.out.print("Inserisci un numero valido per il mese: ");
            scanner.next();
        }
        mese = scanner.nextInt();
        if (mese < 1 || mese > 12) {
            System.out.println("Mese non valido.");
        }
    } while (mese < 1 || mese > 12);

    
    boolean giornoValido = false;
    do {
        System.out.print("Giorno: ");
        while (!scanner.hasNextInt()) {
            System.out.print("Inserisci un numero valido per il giorno: ");
            scanner.next();
        }
        giorno = scanner.nextInt();

        try {
            Calendar dataNascita = new GregorianCalendar(anno, mese - 1, giorno);
            Calendar oggi = Calendar.getInstance();
            Calendar maggioreEta = (Calendar) oggi.clone();
            maggioreEta.add(Calendar.YEAR, -18);

            if (dataNascita.after(oggi)) {
                System.out.println("La data di nascita non può essere nel futuro.");
            } else if (dataNascita.after(maggioreEta)) {
                System.out.println("Devi avere almeno 18 anni.");
            } else {
                nuovoUtente.setData_nascita(dataNascita);
                giornoValido = true;
            }
        } catch (Exception e) {
            System.out.println("Giorno non valido.");
        }
    } while (!giornoValido);

    System.out.println("Registrazione completata");

    return nuovoUtente;
}
}
