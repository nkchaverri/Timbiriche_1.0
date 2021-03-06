package com.timbiriche.Utils;
import com.timbiriche.models.Player;
import java.io.*;

/**
 * Class created to create and import players
 * from txt file
 * @author nchaverri
 */

public class FileUtils
{
    /**
     * Creates a player file that contains all players
     * object created and its information
     * @param players
     */
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
//            System.out.println("File Created");
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");

        }
    }

    /**
     * Read file created and initialize players
     * array
     * @param players
     */
    public void importPlayers( Player[] players)
    {
        try {
            FileInputStream fis= new FileInputStream("Players.txt");
            ObjectInputStream input = new ObjectInputStream(fis);
            boolean cont = true;
            int position = 0;
            while (cont) {
                   Player obj = (Player) input.readObject();

                   if (obj.getPlayerInitials() != null) {
                       players[position]= obj;
                       position ++;
                   }else {
                       cont= false;
                   }
        }
            input.close();
            fis.close();
        } catch ( ClassNotFoundException e ){
            //e.printStackTrace();
        }catch (FileNotFoundException e) {
            //e.printStackTrace();
        } catch (IOException e) {
            //e.printStackTrace();
        }
    }
}

