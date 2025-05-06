package dto;

import java.util.Calendar;

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

}
