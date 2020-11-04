package com.timbiriche.Utils;

import java.util.Scanner;

public class UserInteractions
{
    public void showMessage(String message){
         System.out.println( message + "\n");
    }

    public void showErrorMessage(String message){
        System.out.println( "Please verify: \n" + message );
    }


    public int requestNumber(String requestMessage){
        Scanner input = new Scanner(System.in);
        System.out.println(requestMessage);
        int number = input.nextInt();
        input.close();
        return number;
    }

    public char requestChar(String requestMessage){
        Scanner input = new Scanner(System.in);
        System.out.println(requestMessage);
        char character = input.nextLine().charAt( 0 );
        input.close();
        return character;
    }
}
