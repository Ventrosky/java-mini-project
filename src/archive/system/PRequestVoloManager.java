/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package archive.system;

import archive.data.Volo;
import archive.data.PrenotazioneVolo;
import archive.interfacce.ObjectWriter;
import archive.utility.RequestInfo;
import archive.interfacce.RichiestaPrenotazioneVolo;
import archive.utility.StringInverter;
import archive.data.Cliente;
import archive.data.Carta;
import java.util.Set;

public class PRequestVoloManager extends RequestInfo  implements RichiestaPrenotazioneVolo{

    public String[] showList() {
        ObjectWriter ow = new StringInverter<Volo>();
        String[] s = ow.split(ow.merge(AgenziaSystem.archivio.getVoli()));
        return s;
    }

    public Volo isAvaible(String partenza, String arrivo, String orario) {
        Set<Volo> voli = AgenziaSystem.archivio.getVoli();
        for(Volo v: voli){
            if (v.getLArrivo().equals(arrivo) && v.getLPartenza().equals(partenza) && v.getOPartenza().equals(orario) && (v.getRimasti() > 0))
                return v;
        }
        return null;
    }
    
    public boolean doPrenotation(Volo v, String c, String n, String t, Carta carta) {
        if ( v == null) throw new NullPointerException();
        try{
            Cliente client = requestInformation(c, n, t,carta);
            PrenotazioneVolo newPrenVol = new PrenotazioneVolo(client.getCognome(),client.getNome(),v.getSigla(),v.getLPartenza(),v.getLArrivo());
            AgenziaSystem.archivio.addPrenVolo(newPrenVol);
        }catch(IllegalArgumentException e){
              throw new IllegalArgumentException();
        }
        return true;
    }

}
