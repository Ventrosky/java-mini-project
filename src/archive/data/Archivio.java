/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.data;
import archive.utility.SetArray;
import java.util.*;

public class Archivio {
    private Set<Cliente> clienti = new SetArray();
    private Set<Pacchetto> pacchetti = new SetArray();
    private Set<Prenotazioni> prenotazioni = new SetArray();
    private Set<Volo> voli = new SetArray();
    private Set<PrenotazioneVolo> prenVoli = new SetArray();

    public void addCliente(Cliente c){
        if(!clienti.add(c)) throw new IllegalArgumentException();
    }
    public void addPacchetto(Pacchetto p){
        if(!pacchetti.add(p)) throw new IllegalArgumentException();
    }
    public boolean addPrenotazioni(Prenotazioni p){
         return prenotazioni.add(p);
    }
    public void addPrenotazione(Prenotazione p, String nomeVillaggio){
        for (Prenotazioni x : prenotazioni){
            if (x.getNomeVillaggio().equals(nomeVillaggio))
                x.add(p);
        }
    }
    public void addVolo(Volo v){
        if(!voli.add(v)) throw new IllegalArgumentException();
    }
    public void addPrenVolo(PrenotazioneVolo pv){
        if(!prenVoli.add(pv)) throw new IllegalArgumentException();
        for(Volo x: voli){
            if(x.getSigla().equals(pv.getSigla()))
                x.subRimasti(1);
        }
    }
  
    public void removeCliente(Cliente c){
        if(!clienti.remove(c)) throw new IllegalArgumentException();
    }
    public void removePacchetto(Pacchetto p){
        if(!pacchetti.remove(p)) throw new IllegalArgumentException();
    }
    public void removePrenotazioni(Prenotazioni p){
        if(!prenotazioni.remove(p)) throw new IllegalArgumentException();
    }
    public void removeVolo(Volo v){
        if(!voli.remove(v)) throw new IllegalArgumentException();
    }
    public boolean removePrenVolo(PrenotazioneVolo pv){
        if(!prenVoli.remove(pv)) return false;
        for(Volo x: voli){
            if(x.getSigla().equals(pv.getSigla()))
                x.addRimasti(1);
        }
        return true;
    }
    public boolean removePrenotazione(Prenotazione p,  String nomeVillaggio){
        for (Prenotazioni x : prenotazioni){
            if (x.getNomeVillaggio().equals(nomeVillaggio))
                return x.getElenco().remove(p);
        }
        return false;
    }
    public boolean removePrenotazione(String cognomeCliente, int numWeek,  String nomeVillaggio){
        Prenotazione p = new Prenotazione(numWeek,cognomeCliente,"");
        return removePrenotazione(p,nomeVillaggio);
    }
    public Pacchetto getPacchetto(String nome){
        if (nome == null) throw new NullPointerException();
        for(Pacchetto x:pacchetti){
            if( x!=null && nome.equals(x.getNome())) return x;
        }
        return null;
    }
    public Cliente getCliente(String cognome){
        if (cognome == null) throw new NullPointerException();
        for(Cliente x: clienti){
            if( x!=null && cognome.equals(x.getCognome())) return x;
        }
        return null;
    }
    public Prenotazioni getOnePrenotazioni(String nome){
        if (nome == null) throw new NullPointerException();
        for(Prenotazioni p: prenotazioni){
            if ( p != null && nome.equals(p.getNomeVillaggio())) return p;
        }
        return null;
    }
    public Volo getVolo(String sigla){
        if (sigla == null) throw new NullPointerException();
        for(Volo x : voli){
            if (x.getSigla().equals(sigla)) return x;
        }
        return null;
    }
    public Set<Pacchetto> getPacchetti(){ return Collections.unmodifiableSet(pacchetti);}
    public Set<Cliente> getClienti() { return Collections.unmodifiableSet(clienti);}
    public Set<Prenotazioni> getPrenotazioni() { return Collections.unmodifiableSet(prenotazioni);}
    public Set<Volo> getVoli() { return Collections.unmodifiableSet(voli);}
    public Set<PrenotazioneVolo> getPrenVoli() { return Collections.unmodifiableSet(prenVoli);}
    public Set<PrenotazioneVolo> getPrenVoli(String sigla){
        if (sigla == null) throw new NullPointerException();
        Set<PrenotazioneVolo> pV = new SetArray<PrenotazioneVolo>();
        for(PrenotazioneVolo x : prenVoli){
            if (x.getSigla().equals(sigla)) pV.add(x);
        }
        return pV;
    }
    public PrenotazioneVolo getonePrenVoli(String sigla){
        if (sigla == null) throw new NullPointerException();
        Set<PrenotazioneVolo> pV = new SetArray<PrenotazioneVolo>();
        for(PrenotazioneVolo x : prenVoli){
            if (x.getSigla().equals(sigla))
                return x;
        }
        return null;
    }
    public boolean hasGotPrenotation(Cliente c, int n){
        for(Prenotazioni p: prenotazioni){
            if(p.isPresentClient(c, n))return true;
        }
        return false;
    }
}
