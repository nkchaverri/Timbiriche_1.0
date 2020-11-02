package com.timbiriche.Utils;

import com.timbiriche.models.Player;

import java.io.*;

public class FileUtils
{
    public void createFile( Player[] players){
        try {
            FileOutputStream f = new FileOutputStream(new File("Players.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file

            for ( int i = 0; i <players.length ; i++ )
            {
                o.writeObject(players[i]);
            }

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");

        }
    }

    public void importPlayers( Player[] players){
        try {
            FileInputStream fis = new FileInputStream("Players.txt");
            ObjectInputStream input = new ObjectInputStream(fis);
            boolean cont = true;
            int position = 0;
            while (cont) {
               try
               {
                   Player obj = (Player) input.readObject();

                   System.out.println( obj );

                   if (obj.getPlayerInitials() != null) {
                       players[position]= obj;
                       position ++;
                   }else {
                       cont= false;
                       input.close();
                       fis.close();
                   }
               } catch ( ClassNotFoundException e ){
                   e.printStackTrace();
               }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }
    }
}
