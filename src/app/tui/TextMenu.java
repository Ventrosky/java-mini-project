package app.tui;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.*;
import static java.lang.System.*;
public abstract class TextMenu {
  private String[] menu;
  private int spacing=0;

  protected TextMenu(String...item) {
       int n = item.length;
       menu=Arrays.copyOf(item,n);
}

  private int input(Scanner in){
      int n=menu.length,index=0;
      while(true){
         try{
              index=Integer.parseInt(in.nextLine());
          }catch(NumberFormatException ex){}
        if(index>=1 && index<=n)
            return index;
        else
            out.println("Digitare un intero tra 1"+"e"+n);
      }
    }
private void printMenu(){
    int n=menu.length,maxlen=0;
    for(int i=0;i<n;i++)
         if(menu[i].length()>maxlen)
              maxlen=menu[i].length();
     maxlen+=8;
   for(int i=0;i<maxlen;i++)
          out.print('*');
    out.println();
   for(int i=0;i<n;i++){
       out.printf("* %2d. %s",(i+1), menu[i]);
       int s=maxlen-7-menu[i].length();
     for(int j=0;j<s;j++)
          out.print(' ');
       out.println('*');
   }
   for(int i=0;i<maxlen;i++)
         out.print('*');
    out.println();

}


    public void run() {
        int n=menu.length,index;
        boolean quit=false;
        Scanner in=new Scanner(System.in);
        while(!quit){
           printMenu();
           index=input(in);
         if(index<=n) doMenu(index);
            quit=true;
         }

   }


    protected abstract void doMenu(int k);
}
