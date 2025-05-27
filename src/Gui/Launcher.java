package Gui;

/**
 * Classe di avvio dell'applicazione.
 * Contiene il metodo main che lancia l'interfaccia grafica.
 */
public class Launcher {

    /**
     * Punto di ingresso dell'applicazione.
     * Avvia l'interfaccia grafica Swing nel thread dell'Event Dispatch Thread.
     *
     * @param args argomenti della linea di comando (non usati)
     */
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}