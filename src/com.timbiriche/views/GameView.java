package com.timbiriche.views;

import com.timbiriche.Utils.UserInteractions;
import com.timbiriche.controllers.GameController;

public class GameView
{
    private UserInteractions ui;
    private GameController gameController;


    private final String WELCOME_MESSAGE= "Bienvenidos a Timbiriche";
    private final String AUTORES= "Creado por: Nancy Chaverri";
    private final String INGRESAR= "INGRESAR";

    private final String SOLICITAR_OPCION_JUGAGOR="Ingrese lo siguiente:\n 1 - Crear nuevo jugador\n 2- Escoger jugador existente";

    public GameView()
    {
        this.ui = new UserInteractions();
        this.gameController = new GameController();
    }

    public void initializeGame(){

        // show welcome message
        this.ui.showMessage( WELCOME_MESSAGE +"\n"+ AUTORES);
        this.ui.showMessage( INGRESAR );

        //read players from file and show the available players as list
        this.gameController.intilizePlayers();
        this.ui.showMessage( "Jugadores Disponibles:\n"+ this.gameController.showAvailablePlayers() );

        //request option
        int playersOption= this.ui.requestNumber( SOLICITAR_OPCION_JUGAGOR );

        if ( playersOption == 1 ){

        }
    }

    public void requestInformation(){
        int row = ui.requestNumber( "Please provide rows number (between 2 and 10)" );

    }
}
