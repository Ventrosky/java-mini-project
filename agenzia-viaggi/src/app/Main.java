/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package app;

import app.gui.Gui;
import app.tui.Tui;
import archive.system.AgenziaSystem;
import java.io.*;
import javax.swing.JFrame;

/**
 *
 * @author Ventrone Salvatore 
 * @author Maurizi Simone
 * @author Stronati Andrea
// */
public class Main {
    public static JFrame gui;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        char ui;
        if (args.length < 2) {
            ui = 'G';
        }else{
            ui = args[1].toUpperCase().charAt(0);
        }
        switch (ui) {
            case 'T':
                Tui tui = new Tui();
                AgenziaSystem agenzia = new AgenziaSystem(tui.FileChosser());
                tui.run();
                agenzia.save();
                break;
            default:
                gui=new Gui();
                gui.setVisible(true);
                /*java.awt.EventQueue.invokeLater(new Runnable() {

                    public void run() {
                        new Gui().setVisible(true);
                    }
                });
                   //Gui.agenzia.save();
                 * 
                 */
                break;
        }
       

    }
}
