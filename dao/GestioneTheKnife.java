package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import dto.Recensione;
import dto.Ristorante;
import mapper.Mapper;

public class GestioneTheKnife {

	private static String fileRistorantiPath = "dati/ristoranti.txt";
	private static String fileRecensioniPath = "dati/recensioni.txt";
	
	
	public static void setFileRistorantiPath(String path) {
        fileRistorantiPath = path;
    }

    public static void setFileRecensioniPath(String path) {
        fileRecensioniPath = path;
    }
	

	public static boolean aggiungiRistorante(String nome, String usernameRistoratore, String nazione, String città, String indirizzo, int latitudine,
	        int longitudine, int prezzo, boolean disponibilità_delivery, boolean disponibilità_prenotazione,
	        String tipo_Cucina) {
		
		if (nome == null || usernameRistoratore == null || nazione == null || città == null || indirizzo == null || tipo_Cucina == null)
	        return false;

	    if (latitudine < 0 || longitudine < 0 || prezzo <= 0)
	        return false;

	    Ristorante nuovoRistorante = new Ristorante(nome, usernameRistoratore, nazione, città, indirizzo, latitudine,
	            longitudine, prezzo, disponibilità_delivery, disponibilità_prenotazione, tipo_Cucina);

	    String riga = Mapper.mapStrRistorante(nuovoRistorante);

	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRistorantiPath, true))) {
	        writer.write(riga);
	        writer.newLine();
	        return true;
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}

    public void visualizzaRiepilogo() {

        LinkedList<String> righeRecensioni = new LinkedList<>();

        try (BufferedReader recReader = new BufferedReader(new FileReader(fileRecensioniPath))) {
            String linea;
            while ((linea = recReader.readLine()) != null) {
                righeRecensioni.add(linea);
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura delle recensioni");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(fileRistorantiPath))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                String nomeRistorante = campi[0];

                int sommaVoti = 0;
                int conteggio = 0;
                for (String recLine : righeRecensioni) {
                    String[] recCampi = recLine.split(",", -1);
                    if (recCampi[1].equalsIgnoreCase(nomeRistorante)) {
                        try {
                            sommaVoti += Integer.parseInt(recCampi[2]);
                            conteggio++;
                        } catch (NumberFormatException e) {
                            // ignora voti non validi
                        }
                    }
                }
                System.out.println("Ristorante: " + nomeRistorante);
                if (conteggio > 0) {
                    double media = (double) sommaVoti / conteggio;
                    System.out.println("Numero recensioni: " + conteggio);
                    System.out.printf("Media voti: %.2f%n", media);
                } else {
                    System.out.println("Nessuna recensione disponibile.");
                }
                System.out.println("-----------------------------");
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file ristoranti");
        }
    }

    public static void visualizzaRecensioni(String usernameLoggato) {

        if (fileRistorantiPath == null || fileRecensioniPath == null) {
            System.err.println("Errore: i path dei file non sono stati configurati.");
            return;
        }

        List<String> ristorantiGestiti = new LinkedList<>();

        try (BufferedReader brRistoranti = new BufferedReader(new FileReader(fileRistorantiPath))) {
            String linea;
            while ((linea = brRistoranti.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi[1].equals(usernameLoggato)) {
                    ristorantiGestiti.add(campi[0]);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file dei ristoranti");
            return;
        }

        if (ristorantiGestiti.isEmpty()) {
            System.out.println("Non gestisci alcun ristorante.");
            return;
        }

        boolean trovate = false;

        try (BufferedReader brRecensioni = new BufferedReader(new FileReader(fileRecensioniPath))) {
            String linea;
            while ((linea = brRecensioni.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (ristorantiGestiti.contains(campi[1])) {
                    trovate = true;
                    System.out.println("== Recensione per: " + campi[1] + " ==");
                    System.out.println("Utente: " + campi[0]);
                    System.out.println("Valutazione: " + campi[2] + "/5");
                    System.out.println("Testo: " + campi[3]);
                    System.out.println("Risposta: " + (campi[4].isEmpty() ? "Nessuna" : campi[4]));
                    System.out.println("----------------------------------------");
                }
            }
        } catch (IOException e) {
            System.err.println("Errore nella lettura del file delle recensioni");
        }

        if (!trovate) {
            System.out.println("Non ci sono recensioni per i tuoi ristoranti.");
        }
    }

    public static boolean rispondiARecensione(String usernameLoggato, String nomeRistorante, String usernameCliente,
            String risposta) {
        if (fileRecensioniPath == null || fileRistorantiPath == null) {
            return false;
        }

        // Verifica che il ristorante appartenga al ristoratore
        boolean ristoranteTrovato = false;
        try (BufferedReader br = new BufferedReader(new FileReader(fileRistorantiPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi[0].equals(nomeRistorante) && campi[1].equals(usernameLoggato)) {
                    ristoranteTrovato = true;
                    break;
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (!ristoranteTrovato) {
            return false;
        }

        // Leggi le recensioni e aggiorna solo quella giusta
        LinkedList<String> righeAggiornate = new LinkedList<>();
        boolean recensioneAggiornata = false;

        try (BufferedReader br = new BufferedReader(new FileReader(fileRecensioniPath))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] campi = linea.split(",", -1);
                if (campi[0].equals(usernameCliente) && campi[1].equals(nomeRistorante)) {
                    if (campi[4] != null && !campi[4].trim().isEmpty()) {
                        // Recensione già risposta
                        return false;
                    }
                    campi[4] = risposta;
                    String nuovaLinea = String.join(",", campi);
                    righeAggiornate.add(nuovaLinea);
                    recensioneAggiornata = true;
                } else {
                    righeAggiornate.add(linea);
                }
            }
        } catch (IOException e) {
            return false;
        }

        if (!recensioneAggiornata) {
            return false;
        }

        // Sovrascrivi il file recensioni
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileRecensioniPath))) {
            for (String riga : righeAggiornate) {
                writer.write(riga);
                writer.newLine();
            }
        } catch (IOException e) {
            return false;
        }

        return true;
    }

}
