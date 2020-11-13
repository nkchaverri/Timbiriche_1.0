package com.timbiriche.views;
import com.timbiriche.Utils.ArtificialIntelligence;
import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameController;
import com.timbiriche.models.Box;
import com.timbiriche.models.Player;
import java.util.Scanner;

public class GameView
{
    Scanner input;
    private GameController gameController;
    private Player player1;
    private Player player2;

    private Box[][] boxMatrix;

    private Box requestedBox;

    private int[] rowsColsAvailableRange = {2,3,4,5,6,7,8,9,10};

    private int rows;
    private int cols;

    private final String WELCOME_MESSAGE= "Bienvenidos a Timbiriche \nCreado por: Nancy Chaverri\nINGRESAR";
    private final String REQUEST_PLAYER="Escoja alguna de las siguientes opciones:\n 1-Crear nuevo jugador\n 2-Escoger jugador existente";
    private final String GAME_INSTRUCTIONS="Instrucciones de Juego:\n" +
                                            " A continuacion se le presentara un tablero conformado por puntos\n" +
                                            " El objetivo es marcar lineas en turnos alternados por jugadores hasta completar todos los lados de una caja\n" +
                                            " Gana el jugador que logre completar la mayor cantidad de cajas con sus iniciales";
    private final String GAME_OPTION="Escoja alguna de las siguientes opciones::\n 1- Dos jugadores.\n 2- Fácil.Contra la computadora.\n" +
                                        " 3- Medio.Contra la computadora.\n 4- Dificil.Contra la computadora.\n 5- Finalizar Juego.";

    public GameView()
    {
        this.gameController = new GameController();
        this.input =  new Scanner(System.in);

    }

    public void initializeGame(){

        // show welcome message
        this.showMessage( WELCOME_MESSAGE );

        //read players from file and show the available players as list
        this.gameController.intilizePlayers();
        this.showMessage( "Jugadores Disponibles:\n"+ this.gameController.showAvailablePlayers() );

        //request option for players
        char[] validValues = {'1','2'};
        char playersOption= this.requestChar( REQUEST_PLAYER , validValues );
        //if opc = 1 ... creates a new player
        if ( playersOption  == '1' ){
            int idPlayer = Integer.parseInt( this.requestNumber( "Ingrese un ID numerico para el jugador" ) );

            if ( !this.gameController.getPlayerController().isValidId( idPlayer ) ){
                this.showMessage( "El id debe tener mas de 4  digitos y menor o igual a 8" );
            }else {
                if ( this.gameController.getPlayerController().playerExist( idPlayer ) ){
                    this.showMessage( "Ya existe un jugador con ese ID, ingrese otro" );
                }else{
                    int[] length = {1,2};
                    String playerInitials = this.requestString( "Ingrese las iniciales del jugador: \n 1 o 2 Letras máximo " +
                                                                                "\nDiferentes a los existentes"  , length ).toUpperCase();

                    Player playerCreated = this.gameController.getPlayerController().createNewPlayer( idPlayer, playerInitials );
                    this.player1 = playerCreated;
                    this.showMessage( "Jugador creado:\n" + playerCreated.toString() );
                }
            }
        }// if option is 2 choose a player from the available list
        if ( playersOption  == '2' ){

            this.showMessage( this.gameController.showAvailablePlayers() );
            do{
                int playerId = Integer.parseInt( this.requestNumber( "Ingrese el ID del jugador que desea utilizar") );
                this.player1 = this.gameController.getPlayerController().getPlayerById( playerId);
            }while ( this.player1 == null );
            this.showMessage( "Jugador seleccionado:\n" + this.player1.toString() );
        }

        this.showMessage( GAME_INSTRUCTIONS );
        this.rows = Integer.parseInt( this.requestNumberInRange( "Ingrese  la cantidad de filas: ", this.rowsColsAvailableRange ) );
        this.cols = Integer.parseInt( this.requestNumberInRange( "Ingrese  la cantidad de columnas: ", this.rowsColsAvailableRange ) );

        this.showMessage( "Filas: " + this.rows + " Columnas: " + this.cols );
        this.gameController.getPlayerController().getAvailabePlayersList();

        this.gameController.initAndFillGameBoard( this.rows, this.cols );
        this.boxMatrix = BoxController.boxMatrix;

        this.gameController.printMatrix();

        //request option for players
        char[] gameValidValues = {'1','2','3','4','5'};
        char gameOption= this.requestChar( GAME_OPTION , gameValidValues );

        if ( gameOption =='1' ){
            this.showMessage( this.gameController.getPlayerController().getAvailableSecondPlayers( this.player1 ) );
            do{
                int playerId = Integer.parseInt( this.requestNumber( "Ingrese el ID del jugador que desea utilizar") );
                this.player2 = this.gameController.getPlayerController().getPlayerById( playerId);
            }while ( this.player2 == null );

            this.play( this.player1,this.player2, false, false );
        }else if ( gameOption == '2' ){
            this.player2 = this.gameController.getPlayerController().getComputerPlayer();
            this.play( this.player1, this.player2, true, false );
        }else if(gameOption == '3' ){
            this.player2 = this.gameController.getPlayerController().getComputerPlayer();
            this.play( this.player1, this.player2, false, true );
        }
    }

    private void endGame(){
        this.input.close();
    }

    private final String TRY_AGAIN_ERROR = "Opción inválida!\n";

    public void showMessage(String message){
        System.out.println( message );
    }

    public void showErrorMessage(String message){
        System.out.println( "Please verify: \n" + message );
    }

    public String requestNumber(String requestMessage){
        String intValue = " ";
        String tryAgainError = "";
        do {
            System.out.println(tryAgainError+requestMessage);
            tryAgainError = TRY_AGAIN_ERROR;
            String textInput = this.input.nextLine();
            if (!textInput.isEmpty()) {
                intValue =  textInput ;
            }
        } while (!isStringInt( intValue ));
        return intValue;
    }

    public String requestNumberInRange(String requestMessage, int[] validRange){
        String intValue = " ";
        String tryAgainError = "";
        do {
            System.out.println(tryAgainError+requestMessage);
            tryAgainError = TRY_AGAIN_ERROR;
            String textInput = this.input.nextLine();
            if (!textInput.isEmpty()) {
                intValue =  textInput ;
            }
        } while (!isStringInt( intValue ) || !numberIsInRange( validRange, intValue ));
        return intValue;
    }

    public String requestString(String requestMessage, int[] validLength){
        String string = "";
        String tryAgainError = "";
        do {
            System.out.println(tryAgainError+requestMessage);
            tryAgainError = TRY_AGAIN_ERROR;
            String textInput = this.input.nextLine();
            if (!textInput.isEmpty()) {
                string = textInput;
            }
        } while (!stringValidLenth(validLength, string) || this.gameController.getPlayerController().playerInitialsExist( string ));
        return string;
    }

    public char requestChar(String requestMessage, char[] validValues){
        char character = ' ';
        String tryAgainError = "";
        do {
            System.out.println(tryAgainError+requestMessage);
            tryAgainError = TRY_AGAIN_ERROR;
            String textInput = this.input.nextLine();
            if (!textInput.isEmpty()) {
                character = Character.toUpperCase(textInput.charAt( 0 ));
            }
        } while (!arrayContainsValue(validValues, character));
        return character;
    }

    private boolean arrayContainsValue(char[] validValues, char value) {
        boolean isOnArray = false;
        for (int i = 0; i < validValues.length; i++) {
            if (validValues[i] == value) {
                isOnArray = true;
                break;
            }
        }
        return isOnArray;
    }

    private boolean numberIsInRange(int[] validValues, String value) {
        boolean isOnArray = false;
        //TODO: validate if number
        int intValue = Integer.parseInt( value );
        for (int i = 0; i < validValues.length; i++) {
            if (validValues[i] == intValue) {
                isOnArray = true;
                break;
            }
        }
        return isOnArray;
    }

    private boolean stringValidLenth(int[] validValues, String value) {
        boolean isValidValue = false;
        for (int i = 0; i < validValues.length; i++) {
            if (value.length() == validValues[i]) {
                isValidValue = true;
                break;
            }
        }
        return isValidValue;
    }

    private boolean isStringInt(String s)
    {
        try
        {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex)
        {
            //ex.printStackTrace();
            return false;
        }
    }

    public void play(Player player1, Player player2, boolean isEasy, boolean isMedium){

        Player firstPlayer;
        Player secondPlayer;
        Player awaitPlayer;

        int position = 0;
        char side = ' ';

        int id1 =player1.getPlayerID();
        int id2 =player2.getPlayerID();

        int randomPlayerId = this.gameController.getPlayerController().getRandomPlayerId( id1,id2 );

        firstPlayer = player1.getPlayerID() == randomPlayerId ? player1:player2;
        secondPlayer = player1.getPlayerID() != randomPlayerId ? player1:player2;

        do{
            this.showMessage( "****************************************************************************************" );
            this.showMessage( "\t\t Jugador en turno: " + firstPlayer.getPlayerInitials() +"\t\t" +
                                "Marcador para: " +firstPlayer.getPlayerInitials() + "\t"+BoxController.boxesByPlayer( firstPlayer )  +"\t\t" +
                                    "Marcador para: " +secondPlayer.getPlayerInitials() + "\t"+BoxController.boxesByPlayer( secondPlayer ) );
            this.showMessage( "****************************************************************************************" );

            this.gameController.printMatrix();

            if ( !firstPlayer.isComputer() ){
                position = this.getPositionRequested();
                this.requestedBox  = BoxController.searchBoxById(position);
                side = this.getSideRequested();
                side = GameController.convertChar( side );
            }

            if ( firstPlayer.isComputer() ){
                if ( isEasy ){
                    position =ArtificialIntelligence.aiEasyGetBoxId(this.boxMatrix);
                    System.out.println( "COM escogio Posicion: " + position + "\t\t\t");
                    this.requestedBox = BoxController.searchBoxById(position);
                    side = ArtificialIntelligence.aiEasyGetLineChar( this.requestedBox );
                    System.out.print( "Lado: " + GameController.convertCharToName( side ) + "\n" );
                }else if ( isMedium ){
                        position =ArtificialIntelligence.aiIntermediateGetBoxId(this.boxMatrix);
                            System.out.print( "COM-MED escogio Posicion: " + position + "\t\t\t");
                        this.requestedBox = BoxController.searchBoxById(position);
                        side = ArtificialIntelligence.aiIntermediateGetLineChar( this.requestedBox );
                            System.out.print( "Lado: " + GameController.convertCharToName( side )+ "\n");
                }
            }

            if ( this.gameController.createMove( firstPlayer, this.requestedBox, position, side ) ){
                awaitPlayer = firstPlayer;
                firstPlayer = secondPlayer;
                secondPlayer = awaitPlayer;
            }
        }while (this.gameController.areAvailablePositions());

        this.showMessage( "-------------------------------------------------------------------------------------------------------------------------------" );
        this.gameController.printMatrix();
        this.showMessage( "MARCADOR FINAL: \n"  );
        this.showMessage( "Marcador para: " +firstPlayer.getPlayerInitials() + "\t"+BoxController.boxesByPlayer( firstPlayer ) );
        this.showMessage( "Marcador para: " +secondPlayer.getPlayerInitials() + "\t"+BoxController.boxesByPlayer( secondPlayer ) );
        if (BoxController.boxesByPlayer( firstPlayer ) >  BoxController.boxesByPlayer( secondPlayer )  ){
            firstPlayer.setPoints( firstPlayer.getPoints()+1 );
            firstPlayer.setWonGames( firstPlayer.getWonGames() + 1 );
            this.showMessage( "GANDOR: " + firstPlayer.getPlayerInitials());
        }else if (  BoxController.boxesByPlayer( secondPlayer ) >  BoxController.boxesByPlayer( firstPlayer ) ){
            secondPlayer.setPoints( secondPlayer.getPoints() +1 );
            secondPlayer.setWonGames( secondPlayer.getWonGames() + 1 );
            this.showMessage( "GANDOR: " + secondPlayer.getPlayerInitials());
        }else {
            firstPlayer.setPoints( firstPlayer.getPoints()+0.5 );
            secondPlayer.setPoints( secondPlayer.getPoints() +0.5 );
            this.showMessage( "EMPATE" );
        }
    }

    public int getPositionRequested(){
        this.showMessage( this.gameController.availablePositionsList() );
        int[] boxPositions = this.gameController.getBoxesPositions();
        int position =  Integer.parseInt( this.requestNumberInRange( "Ingrese el numero de la posicion que desea marcar: ", boxPositions ) );

        return position;
    }

    public char getSideRequested(){
        this.showMessage( this.gameController.showAvailableSides( this.requestedBox ) );
        char[] sides = this.gameController.getPosiblePositions();
        char side =  this.requestChar( "Escoja el lado que desea marcar: ", sides ) ;
        return side;
    }
}
