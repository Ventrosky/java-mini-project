/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app.tui;

import javax.swing.JFrame;
import javax.swing.JFileChooser;
import archive.exception.SingleRoomFullException;
import archive.data.Settimana;

import archive.data.Prenotazioni;
import archive.data.PrenotazioneVolo;
import archive.data.Prenotazione;

import archive.exception.DoubleRoomNotAvailableException;
import archive.exception.DoubleRoomFullException;
import archive.data.Cliente;
import archive.data.Carta;
import archive.exception.AlternativeFlightException;
import java.util.*;
import java.io.*;

import archive.system.AgenziaSystem;
import archive.interfacce.GestioneOfferta;
import archive.interfacce.GestionePrenotazione;

import archive.system.OfferManager;

import archive.system.PRequestPacchettoManager;

import archive.system.PRequestVoloManager;

import archive.system.PrenotationManager;
import archive.interfacce.RichiestaPrenotazionePacchetto;
import archive.interfacce.RichiestaPrenotazioneVolo;
import archive.data.Volo;
import static java.lang.System.*;

public class Tui extends TextMenu {

    private Scanner in = new Scanner(System.in);

    private final JFrame frame = new JFrame();

    public Tui() {
        super("Gestione Offerta", "Richiesta Prenotazione", "Gestione Prenotazione", "Esci");
    }

    public String FileChosser() throws IOException {
        JFileChooser chooser = new JFileChooser();
        chooser.setSelectedFile(new File(new File(".txt").getCanonicalPath()));
        chooser.showOpenDialog(frame);
        File curFile = chooser.getSelectedFile();
        String s = curFile.getName();
        if (!(s.substring(s.length() - 4)).equals(".txt")) {
            out.println("File invalido");
        }
        frame.dispose();
        return curFile.getAbsolutePath();
    }

    private boolean checkInt(String str) {
        if (str == null) {
            return true;
        }
        return str.matches("[0-9]+");
    }

    @Override
    protected void doMenu(int k) {

        switch (k) {
            case 1:
                final TextMenu menu1 = new TextMenu("Pacchetto", "Voli", "Torna Indietro") {

                    private String nome;

                    @Override
                    protected void doMenu(int k) {
                        switch (k) {
                            case 1:
                                final TextMenu menuOfferta = new TextMenu("Modifica Archivio Pacchetti", "Esportare/Salvare", "Torna Indietro") {

                                    private String nweek;

                                    @Override
                                    protected void doMenu(int k) {
                                        final OfferManager offerManager = new OfferManager(true);
                                        switch (k) {
                                            case 1:
                                                final String[] s = offerManager.showList();
                                                final TextMenu menuVillaggi = new TextMenu(s) {

                                                    @Override
                                                    protected void doMenu(int k) {
                                                        nome = s[k - 1];
                                                    }
                                                };
                                                menuVillaggi.run();
                                                final String Exvillagio = nome;
                                                do {
                                                    out.println("Inserisci il numero della settimana");
                                                    nweek = in.next();
                                                } while (!checkInt(nweek));
                                                final Settimana sett = AgenziaSystem.archivio.getPacchetto(nome).getSettimana(Integer.parseInt(nweek));
                                                final TextMenu menuModifica = new TextMenu("Modifica numero doppie", "Modifica prezzo doppia", "Modifica numero suite", "Modifica prezzo suite", "Modifica Corsi", "Torna Indientro") {

                                                    private String modifiche = "";

                                                    ;

                                                    @Override
                                                    protected void doMenu(int k) {
                                                        switch (k) {
                                                            case 1:
                                                                //String mod = "";
                                                                do {
                                                                    out.println("Numero Stanze Doppie: " + sett.getNSD());
                                                                    out.println("Aumentare o diminure numero doppie +/-");
                                                                    modifiche = in.next();
                                                                } while (!(modifiche.equals("+") || modifiche.equals("-")));
                                                                modifiche = "D" + ";" + modifiche;
                                                                try {
                                                                    if (offerManager.modArchive(nome, Integer.parseInt(nweek), modifiche)) {
                                                                        out.println("Modifica Effettuata");

                                                                    }
                                                                } catch (DoubleRoomFullException ex) {
                                                                    out.println("Vuoi spostare la prenotazione in un'altro villagio? SI/NO");
                                                                    String risp = in.next();
                                                                    if (risp.toUpperCase().equals("SI")) {
                                                                        menuVillaggi.run();
                                                                        if (offerManager.movePrenotation(Exvillagio, nome, Integer.parseInt(nweek), true, 'D')) {
                                                                            out.println("Prenotazione Spostata");
                                                                        } else {
                                                                            out.println("Impossibile spostare la prenotazione");
                                                                        }

                                                                    }
                                                                } catch (SingleRoomFullException ex) {
                                                                }
                                                                run();
                                                                break;
                                                            case 2:
                                                                modifiche = "";
                                                                out.println("Prezzo Doppia: " + sett.getPSD());
                                                                out.println("Inserisci nuovo prezzo doppia");
                                                                String pD = in.next();
                                                                modifiche = "X" + ";" + pD;
                                                                try {
                                                                    if (offerManager.modArchive(nome, Integer.parseInt(nweek), modifiche)) {
                                                                        out.println("Modifica Effetuata");
                                                                    }
                                                                    run();
                                                                } catch (DoubleRoomFullException ex) {
                                                                } catch (SingleRoomFullException ex) {
                                                                }
                                                                run();
                                                                break;
                                                            case 3:
                                                                modifiche = "";
                                                                do {
                                                                    out.println("Numero Stanze Suite: " + sett.getNSU());
                                                                    out.println("Aumentare o diminure numero suite +/-");
                                                                    modifiche = in.next();
                                                                } while (!(modifiche.equals("+") || modifiche.equals("-")));
                                                                modifiche = "S" + ";" + modifiche;
                                                                try {
                                                                    if (offerManager.modArchive(nome, Integer.parseInt(nweek), modifiche)) {
                                                                        out.println("Modifica Effetuata");
                                                                    }
                                                                } catch (DoubleRoomFullException ex) {
                                                                } catch (SingleRoomFullException ex) {
                                                                    out.println("Vuoi spostare la prenotazione in un'altro villagio? SI/NO");
                                                                    String risp = in.next();
                                                                    if (risp.toUpperCase().equals("SI")) {
                                                                        menuVillaggi.run();
                                                                        if (offerManager.movePrenotation(Exvillagio, nome, Integer.parseInt(nweek), true, 'S')) {
                                                                            out.println("Prenotazione Spostata");
                                                                        } else {
                                                                            out.println("Impossibile spostare la prenotazione");
                                                                        }
                                                                    }
                                                                }
                                                                run();
                                                                break;
                                                            case 4:
                                                                modifiche = "";
                                                                out.println("Prezzo Suite: " + sett.getPSU());
                                                                out.println("Inserisci nuovo prezzo suite");
                                                                String pS = in.next();
                                                                modifiche = "Y" + ";" + pS;
                                                                try {
                                                                    if (offerManager.modArchive(nome, Integer.parseInt(nweek), modifiche)) {
                                                                        out.println("Modifica Effetuata");
                                                                    }


                                                                } catch (DoubleRoomFullException ex) {
                                                                } catch (SingleRoomFullException ex) {
                                                                }
                                                                run();
                                                                break;
                                                            case 5:
                                                                modifiche = "";
                                                                out.println("Corsi: " + sett.stampaCorsi());
                                                                out.println("Inserisci i corsi VETSB");
                                                                String corso = in.next();
                                                                modifiche = "C" + ";" + corso;
                                                                try {
                                                                    if (offerManager.modArchive(nome, Integer.parseInt(nweek), modifiche)) {
                                                                        out.println("Modifica Effetuata");
                                                                    }
                                                                } catch (DoubleRoomFullException ex) {
                                                                } catch (SingleRoomFullException ex) {
                                                                }
                                                                run();
                                                                break;
                                                            case 6:
                                                                Tui.super.run();
                                                                break;
                                                        }
                                                    }
                                                };
                                                menuModifica.run();
                                                break;
                                            case 2:
                                                out.println("Inserisci nome del file");
                                                String path = in.next();
                                                try {
                                                    if (offerManager.exportPrenotation(path)) {
                                                        out.println("Esportazione effetuata");
                                                    }

                                                } catch (FileNotFoundException ex) {
                                                }
                                                run();
                                                break;
                                            case 3:
                                                Tui.super.run();
                                                break;

                                        }
                                    }
                                };
                                menuOfferta.run();
                                break;
                            case 2:
                                final TextMenu menuGestioneVolo = new TextMenu("Rimuovi Volo", "Esportare/Salvare", "Torna Indietro") {

                                    private String sigla;

                                    @Override
                                    protected void doMenu(int k) {
                                        GestioneOfferta gestioneOfferta = new OfferManager(false);
                                        switch (k) {
                                            case 1:
                                                final String[] s = gestioneOfferta.showList();
                                                final TextMenu menuVoli = new TextMenu(s) {

                                                    @Override
                                                    protected void doMenu(int k) {
                                                        sigla = s[k - 1].substring(0, 4);
                                                    }
                                                };
                                                menuVoli.run();
                                                out.println("Vuoi cancellare il volo? SI/NO");
                                                String risp = in.next();
                                                if (risp.toUpperCase().equals("SI")) {
                                                    try {
                                                        gestioneOfferta.modArchive(sigla);
                                                        out.println("Volo cancellato");
                                                        run();
                                                    } catch (AlternativeFlightException ex) {
                                                        out.println("Questo volo ha delle prenotazioni vuoi cancellarne un'altro? SI/NO");
                                                        risp = in.next();
                                                        if (risp.toUpperCase().equals("SI")) {
                                                            doMenu(1);
                                                        } else {
                                                            run();
                                                        }
                                                    }
                                                }
                                                break;
                                            case 2:
                                                out.println("Inserisci nome del file");
                                                String path = in.next();
                                                try {
                                                    if (gestioneOfferta.exportPrenotation(path)) {
                                                        out.println("Esportazione effetuata");
                                                    }
                                                } catch (FileNotFoundException ex) {
                                                }
                                                run();
                                                break;
                                            case 3:
                                                Tui.super.run();
                                                break;
                                        }
                                    }
                                };

                                menuGestioneVolo.run();
                                break;
                            case 3:
                                Tui.super.run();
                                break;

                        }

                    }
                };
                menu1.run();
                break;
            case 2:
                TextMenu menuRichPrenotazione = new TextMenu("Pacchetto", "Voli", "Torna Indietro") {

                    private String nsett, nome;

                    @Override
                    protected void doMenu(int k) {
                        switch (k) {
                            case 1:
                                final RichiestaPrenotazionePacchetto reqPrenPacchetto = new PRequestPacchettoManager();
                                final String[] s = reqPrenPacchetto.showList();
                                TextMenu menuVillaggi = new TextMenu(s) {

                                    @Override
                                    protected void doMenu(int k) {
                                        nome = s[k - 1];
                                    }
                                };
                                menuVillaggi.run();
                                do {
                                    out.println("Inserisci il numero della settimana");
                                    nsett = in.next();
                                } while (!checkInt(nsett));
                                try {
                                    reqPrenPacchetto.isAvaible(Integer.parseInt(nsett), nome);
                                } catch (IllegalArgumentException e) {
                                    out.println("Settimana passata");
                                    run();

                                }
                                TextMenu menuReqPren = new TextMenu("Dettagli", "Prenotare", "Torna Indietro") {

                                    @Override
                                    protected void doMenu(int k) {
                                        switch (k) {
                                            case 1:
                                                printMatrix(reqPrenPacchetto.showDetails(nome, Integer.parseInt(nsett)));
                                                run();
                                                break;
                                            case 2:
                                                String[] x = reqPrenPacchetto.showDetails(nome, Integer.parseInt(nsett));
                                                in = new Scanner(System.in);
                                                out.print("\nInserisci Cognome Cliente: ");
                                                String ccliente = in.nextLine();
                                                out.print("\nInserisci Nome Cliente: ");
                                                String ncliente = in.nextLine();
                                                out.print("\nInserisci numero di telefono: ");
                                                String telefono = in.nextLine();
                                                out.print("\nInserisci carta di credito: ");
                                                String carta = in.nextLine();
                                                out.print("\nInserisci tipo stanza D/S: ");
                                                String tipo = in.nextLine();
                                                out.print("\nInserisci corsi: ");
                                                String corsi = in.nextLine();
                                                try {
                                                    if (reqPrenPacchetto.doPrenotation(Integer.parseInt(nsett), ccliente, ncliente, telefono, new Carta(carta), tipo, corsi)) {
                                                        out.println("Prenotazione effettuata");
                                                    }
                                                } catch (IllegalArgumentException e) {
                                                    out.println("Carta non valida");
                                                } catch (NoSuchElementException e) {
                                                    out.println("Stanze doppie non disponibili vuoi aggiungere in lista di attessa? SI/NO");
                                                    String risp = in.next();
                                                    if (risp.toUpperCase().equals("SI")) {
                                                        reqPrenPacchetto.requestInformation(ccliente, ncliente, telefono, new Carta(carta));
                                                        if (reqPrenPacchetto.addInListaAttesa(Integer.parseInt(nsett), ccliente)) {
                                                            out.println("Aggiunto in lista di attessa");
                                                        } else {
                                                            out.println("Non aggiunto in lista di attessa (il cliente potrebbe essere gia presente)");
                                                        }
                                                    }
                                                }
                                                run();
                                                break;
                                            case 3:
                                                Tui.super.run();
                                                break;
                                        }
                                    }
                                };
                                menuReqPren.run();
                                break;
                            case 2:
                                final RichiestaPrenotazioneVolo reqPrenVolo = new PRequestVoloManager();
                                printMatrix(reqPrenVolo.showList());
                                out.print("Inserisci luogo di partenza: ");
                                String part = in.nextLine();
                                out.print("\nInserisci luogo di arrivo: ");
                                String arr = in.nextLine();
                                out.print("\nInserisci orario: ");
                                String or = in.nextLine();
                                final Volo v = reqPrenVolo.isAvaible(part, arr, or);
                                if (v == null) {
                                    out.println("Volo non presente");
                                    run();
                                } else {
                                    TextMenu menuPren = new TextMenu("Prenotare", "Torna Indietro") {

                                        @Override
                                        protected void doMenu(int k) {
                                            switch (k) {
                                                case 1:
                                                    out.print("\nInserisci Cognome Cliente: ");
                                                    String ccliente = in.nextLine();
                                                    out.print("\nInserisci Nome Cliente: ");
                                                    String ncliente = in.nextLine();
                                                    out.print("\nInserisci numero di telefono: ");
                                                    String telefono = in.nextLine();
                                                    out.print("\nInserisci carta di credito: ");
                                                    String carta = in.nextLine();
                                                    try {
                                                        if (reqPrenVolo.doPrenotation(v, ccliente, ncliente, telefono, new Carta(carta))) {
                                                            out.println("Prenotazione effettuata");
                                                        }
                                                    } catch (IllegalArgumentException e) {
                                                        out.println("Carta non valida");
                                                    }
                                                    run();
                                                    break;
                                                case 2:
                                                    Tui.super.run();
                                                    break;
                                            }
                                        }
                                    };
                                    menuPren.run();
                                }
                                break;
                            case 3:
                                Tui.super.run();
                                break;

                        }
                    }
                };
                menuRichPrenotazione.run();
                break;
            case 3:
                final TextMenu menuGestPrenotazione = new TextMenu("Pacchetto", "Voli", "Torna Indietro") {

                    private String nweek, nome;
                    Cliente c = null;

                    @Override
                    protected void doMenu(int k) {

                        switch (k) {
                            case 1:

                                out.println("Inserire il cognome del cliente");
                                final String cognome = in.nextLine();
                                final PrenotationManager gestionePrenotazione = new PrenotationManager(AgenziaSystem.archivio.getPrenotazioni());
                                try {
                                    c = gestionePrenotazione.requestInformation(cognome);
                                } catch (NoSuchElementException e) {
                                    out.println("Il cliente non è presente nell'archivio");
                                    run();
                                }
                                Set<Prenotazioni> s = gestionePrenotazione.showList(c);
                                for (Prenotazioni x : s) {
                                    out.println(x);
                                }
                                out.println("Inserisci il nome dell Villagio ");
                                nome = in.nextLine();
                                gestionePrenotazione.setVillaggio(nome);
                                do {
                                    out.println("Inserisci il numero della settimana ");
                                    nweek = in.next();
                                } while (!checkInt(nweek));
                                TextMenu menuModifica = new TextMenu("Cancella Prenotazione", "Modifica  Prenotazione", "Torna Indietro") {

                                    @Override
                                    protected void doMenu(int k) {

                                        switch (k) {
                                            case 1:
                                                if (gestionePrenotazione.delPrenotation(AgenziaSystem.archivio.getOnePrenotazioni(nome).getPrenotazione(cognome, Integer.parseInt(nweek)))) {
                                                    out.println("Cancellata");
                                                }
                                                Tui.super.run();
                                                break;
                                            case 2:
                                                Prenotazione oldPren = AgenziaSystem.archivio.getOnePrenotazioni(nome).getPrenotazione(cognome, Integer.parseInt(nweek));
                                                Prenotazione appoPren = new Prenotazione(oldPren.getNumeroSett(), oldPren.getCognome(), oldPren.getNome());
                                                appoPren.setTipo(oldPren.getTipo());
                                                appoPren.setCorsi(oldPren.getCorsi());
                                                try {
                                                    out.println("Modifica Prenotazione:\n");
                                                    do {
                                                        out.println("n° settimana: " + oldPren.getNumeroSett());
                                                        out.println("Inserire numero settimana: ");
                                                    } while (!gestionePrenotazione.modPrenotation(appoPren, "N;" + in.nextInt()));
                                                    out.println("Tipo stanza: " + oldPren.getTipo());
                                                    out.println("Inserire tipo stanza D/S: ");
                                                    gestionePrenotazione.modPrenotation(appoPren, "T;" + in.next());
                                                    out.println("Corsi: " + oldPren.getCorsi());
                                                    out.println("Disponibili: " + AgenziaSystem.archivio.getPacchetto(nome).getSettimana(appoPren.getNumeroSett()).getCorsi());
                                                    out.println("Inserire corsi: ");
                                                    gestionePrenotazione.modPrenotation(appoPren, "S;" + in.next());
                                                } catch (DoubleRoomNotAvailableException ex) {
                                                    out.println("Stanze Doppie non disponibili vuoi essere aggiunto in lista d'attesa? SI/NO");
                                                    String risp = in.next();
                                                    if (risp.toUpperCase().equals("SI")) {
                                                        gestionePrenotazione.addInQueue(true);
                                                    }
                                                }
                                                try {
                                                    gestionePrenotazione.modPrenotation(appoPren, "C;save");
                                                    run();
                                                } catch (DoubleRoomNotAvailableException ex) {
                                                }
                                                break;
                                            case 3:
                                                Tui.super.run();
                                                break;
                                        }
                                    }
                                };
                                menuModifica.run();
                                break;

                            case 2:
                                out.println("Inserire il cognome del cliente");
                                String cog = in.nextLine();
                                final GestionePrenotazione gestionePrenVoli = new PrenotationManager(AgenziaSystem.archivio.getPrenVoli());
                                try {
                                    c = gestionePrenVoli.requestInformation(cog);
                                } catch (NoSuchElementException e) {
                                    out.println("Il cliente non è presente nell'archivio");
                                    run();

                                }
                                Set<PrenotazioneVolo> s1 = gestionePrenVoli.showList(c);
                                for (PrenotazioneVolo x : s1) {
                                    out.println(x);
                                }
                                out.println("Inserisci sigla Volo");
                                final String sigla = in.next();
                                TextMenu menuModVolo = new TextMenu("Cancella Prenotazione Volo", "Modifica Orario", "Torna Indietro") {

                                    @Override
                                    protected void doMenu(int k) {
                                        switch (k) {
                                            case 1:
                                                if (gestionePrenVoli.delPrenotation(AgenziaSystem.archivio.getonePrenVoli(sigla))) {
                                                    out.println("Cancellazione Effettuata");
                                                }
                                                run();
                                                break;
                                            case 2:
                                                out.println("Inserisci l'orario");
                                                String or = in.next();
                                                try {
                                                    if (gestionePrenVoli.modPrenotation(AgenziaSystem.archivio.getonePrenVoli(sigla), or)) {
                                                        out.println("Modifica Effettuata");
                                                    } else {
                                                        out.println("Non ci sono voli in questo orario ");
                                                    }

                                                } catch (DoubleRoomNotAvailableException ex) {
                                                }
                                                run();
                                                break;
                                            case 3:
                                                Tui.super.run();
                                                break;
                                        }
                                    }
                                };
                                menuModVolo.run();
                                break;
                            case 3:
                                Tui.super.run();
                                break;

                        }
                    }
                };


                menuGestPrenotazione.run();
                break;
            case 4:

                break;

        }

    }

    private void printMatrix(String[] s) {
        for (int i = 0; i < s.length; i++) {
            out.println(s[i]);
        }
        out.println();
    }
}
