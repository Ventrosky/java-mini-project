/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package archive.system;

import archive.utility.ClientMaker;
import archive.data.Volo;
import archive.data.Prenotazioni;
import archive.data.PrenotazioneVolo;
import archive.data.Pacchetto;
import archive.interfacce.FileExport;
import archive.interfacce.ObjectReader;
import archive.interfacce.ObjectWriter;
import archive.utility.PacchettoMaker;
import archive.utility.PrenVoloMaker;
import archive.utility.PrenotazioniMaker;
import archive.utility.StringInverter;
import archive.utility.VoloMaker;
import archive.data.Cliente;
import archive.data.Archivio;
import java.io.*;
import java.util.*;

public class FileSystem implements FileExport{

    private File fileSource;
    private Scanner fileScanner;
    private PrintStream wFile;
    
    public FileSystem(){    }
    public FileSystem(String path) throws FileNotFoundException{
        
        fileSource = new File(path);
        fileScanner = new Scanner(fileSource);
    }
    private void rewind() throws FileNotFoundException{
        fileScanner.close();
        fileScanner = new Scanner(fileSource);
    }
    private void switchState(String state, String s, Archivio archivio){
       ObjectReader or = null;
       if(state.equals("PACCHETTO")){
           or = new PacchettoMaker(archivio);
            }else
            if(state.equals("CLIENTI")){
                or = new ClientMaker(archivio);
            }else
            if(state.equals("PRENOTAZIONI")){
                or = new PrenotazioniMaker(archivio);
            }else
            if(state.equals("VOLI")){
                or = new VoloMaker(archivio);
            }else
            if(state.equals("PREN VOLI")){
                or = new PrenVoloMaker(archivio);
            }
       if (or == null) throw new NullPointerException();
       or.split(s);
       or.create();
    }
    public Archivio read() throws FileNotFoundException{
        Archivio archivio = new Archivio();
        rewind();
        String state="PACCHETTO",s="",appo;
        while(fileScanner.hasNextLine()){
            appo=fileScanner.nextLine();
            if(appo.equals("PACCHETTO")){
                if (appo.equals("")) s+= "\n";
                switchState(state,s,archivio);
                s="";
                state=appo;
            }else
            if(appo.equals("CLIENTI")){
                switchState(state,s,archivio);
                s="";
                state=appo;
            }else
            if(appo.equals("PRENOTAZIONI")){
                switchState(state,s,archivio);
                s="";
                state=appo;
            }else
            if(appo.equals("VOLI")){
                switchState(state,s,archivio);
                s="";
                state=appo;
            }else
            if(appo.equals("PREN VOLI")){
                switchState(state,s,archivio);
                s="";
                state=appo;
            }else{
                s+=appo+"\n";
            }
       }
        if(state.equals("PREN VOLI")){
                switchState(state,s,archivio);
                s="";
        }

        fileScanner.close();
        return archivio;
    }

    private void writeln(String[] s){
        if (wFile == null) throw new NullPointerException();
        for (int i = 0; i < s.length; i++){
            wFile.println(s[i]);
        }

    }

    public void write(String path) throws FileNotFoundException{
        FileOutputStream fos = new FileOutputStream(path, false);
        wFile = new PrintStream(fos, true);
        ObjectWriter ow;
        ow = new StringInverter<Pacchetto>();
        for (Pacchetto p: AgenziaSystem.archivio.getPacchetti()){
            wFile.println("PACCHETTO");
            String s[]=ow.split(p.toString());
            if (s.length == 106){
                s=Arrays.copyOf(s,107 );
                s[106]="";
            }
            writeln(s);
        }
        wFile.println("CLIENTI");
        ow = new StringInverter<Cliente>();
        writeln(ow.split(ow.merge(AgenziaSystem.archivio.getClienti())));
        ow = new StringInverter<Prenotazioni>();
        for (Prenotazioni p: AgenziaSystem.archivio.getPrenotazioni()){
            wFile.println("PRENOTAZIONI");
            writeln(ow.split(p.toString()));
        }
        wFile.println("VOLI");
        ow = new StringInverter<Volo>();
        writeln(ow.split(ow.merge(AgenziaSystem.archivio.getVoli())));
        wFile.println("PREN VOLI");
        ow = new StringInverter<PrenotazioneVolo>();
        writeln(ow.split(ow.merge(AgenziaSystem.archivio.getPrenVoli())));
        wFile.close();
    }
    public void exportPrenPacchetti(String path) throws FileNotFoundException{
        FileOutputStream fos = new FileOutputStream(path, false);
        wFile = new PrintStream(fos, true);
        ObjectWriter ow;
        ow = new StringInverter<Prenotazioni>();
        Set<Prenotazioni> setPren = AgenziaSystem.archivio.getPrenotazioni();
        for (Prenotazioni x : setPren){
            wFile.println(x.getNomeVillaggio());
            Object[] arrayP =x.getElenco().toArray();
            Arrays.sort(arrayP);
            for (int i = 0; i < arrayP.length; i++){
                wFile.println(arrayP[i].toString());
            } 
        }
    }
    public void exportPrenVoli(String path) throws FileNotFoundException{
        FileOutputStream fos = new FileOutputStream(path, false);
        wFile = new PrintStream(fos, true);
        ObjectWriter ow;
        ow = new StringInverter<PrenotazioneVolo>();
        wFile.println("PREN VOLI");
        ow = new StringInverter<PrenotazioneVolo>();
        writeln(ow.split(ow.merge(AgenziaSystem.archivio.getPrenVoli())));
        wFile.close();
    }
    
}
