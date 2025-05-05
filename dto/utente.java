package dto;

import java.util.Calendar;

public class Utente {
	
	 private String Nome;
	 private String Cognome;
	 private String Username;
	 private String Password;
	 private Calendar Data_nascita;
	 private String Luogo_domicilio;
	 private String ruolo;
	 private Ristorante[] preferiti;

     public String getNome() {
		return Nome;
	}
	public void setNome(String nome) {
		Nome = nome;
	}
	public String getCognome() {
		return Cognome;
	}
	public void setCognome(String cognome) {
		Cognome = cognome;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public Calendar getData_nascita() {
		return Data_nascita;
	}
	public void setData_nascita(Calendar data_nascita) {
		Data_nascita = data_nascita;
	}
	public String getLuogo_domicilio() {
		return Luogo_domicilio;
	}
	public void setLuogo_domicilio(String luogo_domicilio) {
		Luogo_domicilio = luogo_domicilio;
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
