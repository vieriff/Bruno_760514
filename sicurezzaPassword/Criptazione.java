package sicurezzaPassword;

public class Criptazione {

	private static final int CHIAVE = 6;

	public static String critta(String testoChiaro) {
		String risultato = new String();

		for (int i = 0; i < testoChiaro.length(); i++) {
			char c = testoChiaro.charAt(i);
			char carattereCriptato = (char) (c + CHIAVE);
			risultato = risultato + carattereCriptato;
		}

		return risultato.toString();
	}

	public static String decritta(String testoCriptato) {
		String risultato = new String();

		for (int i = 0; i < testoCriptato.length(); i++) {
			char c = testoCriptato.charAt(i);
			char carattereDecriptato = (char) (c - CHIAVE);
			risultato = risultato + carattereDecriptato;
		}

		return risultato.toString();
	}

}