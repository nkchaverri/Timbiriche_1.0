package com.timbiriche.views;
import com.timbiriche.Utils.ArtificialIntelligence;
import com.timbiriche.controllers.BoxController;
import com.timbiriche.controllers.GameController;
import com.timbiriche.models.Box;
import com.timbiriche.models.Player;
import javafx.scene.control.ComboBox;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.util.Scanner;

/**
 * Class that handle user communication and game interaccions
 * with main controllers
 * @author nchaverri
 */

public class GameView extends JFrame
{

    // JFrame
    static JFrame welcome, playerWindow, gameWindow;

    // JButton
    static JButton b;

    static JTextArea welcomeMessage;


    Scanner input;

    private GameController gameController;
    private Player player1;
    private Player player2;
    private Player computer;

    private Box[][] boxMatrix;
    private Box requestedBox;

    int idPlayer;
    String playerInitials;

    boolean validId, validInitials;

    private int[] rowsColsAvailableRange = {2,3,4,5,6,7,8,9,10};

    int matrixRows;
    int matrixCols;

    private final String WELCOME_MESSAGE= "Bienvenidos a Timbiriche \nCreado por: Nancy Chaverri";
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

    public void welcomeUI(){
        // create a new frame to store text field and button
        welcome = new JFrame("Ventana Bienvenida");
        welcome.setMinimumSize( new Dimension( 600,300 ) );
        welcome.getContentPane().setLayout( new BoxLayout(welcome.getContentPane(), BoxLayout.Y_AXIS));
        JPanel pMesagge = new JPanel();
        pMesagge.setAlignmentX( Component.CENTER_ALIGNMENT );
        JPanel button = new JPanel( );
        button.setAlignmentX( Component.CENTER_ALIGNMENT );

        // create a label to display text
        welcomeMessage = new JTextArea(WELCOME_MESSAGE);
        welcomeMessage.setFont(new Font("Arial", Font.BOLD, 16));
        welcomeMessage.setAlignmentX( Component.CENTER_ALIGNMENT );
        welcomeMessage.setEditable( false );

        b = new JButton("INGRESAR");
        pMesagge.add(welcomeMessage);
        button.add(b);

        welcome.getContentPane().add(pMesagge);
        welcome.getContentPane().add(button);

        welcome.pack();
        welcome.setLocationRelativeTo(null);
        welcome.setVisible(true);

        b.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                if(e.getSource().toString().contains( "INGRESAR")){
                    welcome.setVisible( false );
                    choosePlayers();
                }
            }
        });
       closingEvent( welcome );

    }

    public void choosePlayers(){
        playerWindow = new JFrame("Escoger Jugadores");
        playerWindow.setMinimumSize( new Dimension( 800,400 ) );
        playerWindow.getContentPane().setLayout( new BoxLayout(playerWindow.getContentPane(), BoxLayout.Y_AXIS));
        this.gameController.intilizePlayers();
        this.computer = this.gameController.getPlayerController().createNewPlayer( 99999999, "COM" );
        this.computer.setComputer(true);

        //Create panel for players
        JPanel players = new JPanel();
        players.setAlignmentX(Component.CENTER_ALIGNMENT);
        players.setMaximumSize( new Dimension( 800,200 ) );
        players.setBorder(BorderFactory.createTitledBorder("Jugadores Disponibles"));

        JTextArea playersAvailable = new JTextArea(this.gameController.showAvailablePlayers());
        playersAvailable.setEditable( false );
        players.add(playersAvailable);

        //Create a panel for actions
        JPanel playerButtons = new JPanel();
        playerButtons.setBorder(BorderFactory.createTitledBorder("Qué desea hacer?"));
        playerButtons.setMaximumSize( new Dimension( 800,200 ) );

        JButton createPlayer = new JButton( "Crear jugador" );
        JButton choosePlayer = new JButton( "Escoger jugador" );

        playerButtons.add( createPlayer );
        playerButtons.add( choosePlayer );

        createPlayer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
//                System.out.println("SOurce create" + e.getSource().toString());
                choosePlayer.setVisible( false );

                JButton source = (JButton) e.getSource();
                source.setEnabled(false);
                source.setBackground(Color.lightGray);

                if ( e.getSource().toString().contains( "Crear jugador" ) ){
//                    System.out.println("crear jugador");
                    requestFirstPlayer( playerButtons);
                }
            }
        });
        choosePlayer.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
//                System.out.println("ESCOGER JUGADOR");
                createPlayer.setVisible( false );

                JButton source = (JButton) e.getSource();
                source.setEnabled(false);
                source.setBackground(Color.lightGray);

                chooseFirstPlayer(playerWindow.getContentPane());
            }
        });
        playerWindow.getContentPane().add(players);
        playerWindow.getContentPane().add(playerButtons);
        refreshWindow( playerWindow );
        closingEvent( playerWindow );
    }

    private void chooseFirstPlayer( Container pane){
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JTextArea playerId = new JTextArea("Ingrese el ID del jugador que desea utilizar" );
        playerId.setEditable( false );
        playerId.setAlignmentX( Component.CENTER_ALIGNMENT );

        JTextField number = new JTextField(8);
        number.setAlignmentX( Component.CENTER_ALIGNMENT );

        pane.add( playerId );
        pane.add( number );

        refreshWindow(playerWindow);
        //request player ID
        number.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
                idPlayer = Integer.parseInt( requestNumber( number.getText()));
                if ( gameController.getPlayerController().playerExist( idPlayer ) ){
                    number.setEditable( false );
                    player1 = gameController.getPlayerController().getPlayerById( idPlayer);
                    JOptionPane.showMessageDialog( null,"Jugador escogido: "+ player1.toString(),"Jugador escogido!", JOptionPane.INFORMATION_MESSAGE );
                    initializeGame();
                }else{
                    JOptionPane.showMessageDialog( null,"Por favor ingrese un ID existente","Error!", JOptionPane.WARNING_MESSAGE );
                }
            }
        });
    }

    private void requestFirstPlayer( Container pane){

        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));

        JTextArea requestID = new JTextArea("Ingrese un ID numerico para el jugador" );
        requestID.setEditable( false );
        requestID.setAlignmentX( Component.CENTER_ALIGNMENT );

        JTextArea requestInitials = new JTextArea("Ingrese las iniciales del jugador. 2 digitos maximo." );
        requestInitials.setEditable( false );
        requestInitials.setAlignmentX( Component.CENTER_ALIGNMENT );

        JTextField requestedId = new JTextField(8);
        requestedId.setAlignmentX( Component.CENTER_ALIGNMENT );

        JTextField requestedInitials = new JTextField(2);
        requestedInitials.setAlignmentX( Component.CENTER_ALIGNMENT );
        requestedInitials.setEditable( false );

        pane.add( requestID );
        pane.add( requestedId );
        pane.add( requestInitials );
        pane.add( requestedInitials );
        refreshWindow(playerWindow);
        //request player ID
        requestedId.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {

                if ( requestedId.getText().length() <4 || requestedId.getText().length() >8){
                    JOptionPane.showMessageDialog( null,"El id debe tener mas de 4  digitos y menor o igual a 8","Error!", JOptionPane.WARNING_MESSAGE );
                }else {
                    idPlayer = Integer.parseInt( requestNumber( requestedId.getText()));
                    if ( gameController.getPlayerController().playerExist( idPlayer ) ){
                        JOptionPane.showMessageDialog( null,"Ya existe otro jugador con ese ID, ingrese uno diferente","Error!", JOptionPane.WARNING_MESSAGE );

                    }else{
                        JOptionPane.showMessageDialog( null,"El id " + idPlayer + " se encuentra disponible","Perfecto!", JOptionPane.INFORMATION_MESSAGE );
                        validId = true;
                        requestedId.setEditable( false );
                        requestedInitials.setEditable( true );
                    }
                }
            }
        });

        requestedInitials.addActionListener( new ActionListener(){
            public void actionPerformed(ActionEvent e)
            {
//                System.out.println("Info Added: "+requestedId.getText());

                if ( requestedInitials.getText().length() >2){
                    JOptionPane.showMessageDialog( null,"Por favor ingrese solo 2 letras","Error!", JOptionPane.WARNING_MESSAGE );
                }else {
                    if ( gameController.getPlayerController().playerInitialsExist( requestedInitials.getText().toUpperCase() ) ){
                        JOptionPane.showMessageDialog( null,"Ya existe otro jugador con esas iniciales, ingrese unos diferentes","Error!", JOptionPane.WARNING_MESSAGE );

                    }else{
                        validInitials = true;
                        playerInitials = requestedInitials.getText().toUpperCase();
                        JOptionPane.showMessageDialog( null,"Las iniciales " + playerInitials + " se encuentran disponible","Perfecto!", JOptionPane.INFORMATION_MESSAGE );
                        requestedInitials.setEditable( false );
                        createPlayer();
                    }
                }
            }
        });

    }

    private void createPlayer(){
        if(validId && validInitials){
            Player playerCreated = this.gameController.getPlayerController().createNewPlayer( idPlayer, playerInitials );
            this.player1 = playerCreated;
            JOptionPane.showMessageDialog( null,"Jugador Creado: "+ this.player1.toString(),"Jugador Creado!", JOptionPane.INFORMATION_MESSAGE );
            initializeGame();
        }
    }

    private void refreshWindow(JFrame jFrame){
        jFrame.getContentPane();
        jFrame.pack();
        jFrame.setLocationRelativeTo( null );
        jFrame.setVisible(true);
    }

    /**
     * This is the method used to itiate everything related with the game
     */
    public void initializeGame(){
        playerWindow.setVisible( false );

        gameWindow = new JFrame( "Ventanad de Juego");
        gameWindow.setSize( new Dimension( 1000,1200 ) );
        gameWindow.setBackground( Color.white );

        JPanel onePanel = new JPanel( );
        onePanel.setPreferredSize( new Dimension( 800,400 ) );
        onePanel.setBorder(BorderFactory.createTitledBorder("A Jugar ... "));

        JTextArea gameInstructions = new JTextArea( GAME_INSTRUCTIONS );
        gameInstructions.setEditable( false );
        gameInstructions.setAlignmentX( Component.CENTER_ALIGNMENT );

        JLabel rows = new JLabel("Cantidad Filas");
        JLabel cols = new JLabel("Cantidad Columnas");

        JComboBox<Integer> rowList = new JComboBox<>(intArrayToInteger( rowsColsAvailableRange ));
        JComboBox<Integer> colList = new JComboBox<>(intArrayToInteger( rowsColsAvailableRange ));

        onePanel.add( gameInstructions );
        onePanel.add( rows );
        onePanel.add( rowList );
        onePanel.add( cols );
        onePanel.add( colList );

        gameWindow.getContentPane().add( onePanel );

        refreshWindow( gameWindow );
        closingEvent( gameWindow );

        rowList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
//                System.out.println("Row" + combo.getSelectedItem());
                Integer row = (Integer ) combo.getSelectedItem();
                matrixRows = row;

                combo.setEnabled( false );

            }
        });

        colList.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent event) {
                JComboBox<Integer> combo = (JComboBox<Integer>) event.getSource();
//                System.out.println("Cols" + combo.getSelectedItem());
                Integer cols = (Integer ) combo.getSelectedItem();
                matrixCols = cols;

                combo.setEnabled( false );

                if ( (matrixRows>1)&&(matrixCols>1) ){
                    JOptionPane.showMessageDialog( null, "A jugar en modo consola...", "Jugar consola", JOptionPane.INFORMATION_MESSAGE );
                    gameWindow.setVisible( false );
                    continueConsole();
                }
            }
        });

    }

    private void continueConsole(){
        char[] gameValidValues = {'1','2','3','4','5'};
        char gameOption;
        char agreedOption;
        do{
            gameController.initAndFillGameBoard( matrixRows, matrixCols );
            boxMatrix = BoxController.boxMatrix;
            System.lineSeparator();
            //request option for players
            gameOption= this.requestChar( GAME_OPTION , gameValidValues );

            if ( gameOption =='1' ){
                this.showMessage( this.gameController.getPlayerController().getAvailableSecondPlayers( this.player1 ) );
                do{
                    int playerId = Integer.parseInt( this.requestNumberConsole( "Ingrese el ID del jugador que desea utilizar") );
                    this.player2 = this.gameController.getPlayerController().getPlayerById( playerId);
                }while ( this.player2 == null );

                this.play( this.player1,this.player2, false, false,false );
            }else if ( gameOption == '2' ){
                this.player2 = this.gameController.getPlayerController().getComputerPlayer();
                this.play( this.player1, this.player2, true, false, false );
            }else if(gameOption == '3' ){
                this.player2 = this.gameController.getPlayerController().getComputerPlayer();
                this.play( this.player1, this.player2, false, true , false);
            }else {
                this.player2 = this.gameController.getPlayerController().getComputerPlayer();
                this.play( this.player1, this.player2, false, false , true);
            }
            this.gameController.getPlayerController().createPlayerFile();

            System.lineSeparator();

            char[] agreedResponses = {'1','2'};
            agreedOption = this.requestChar( "Desea volver a jugar?: \n 1 = Si \t\t\t 2 = No ", agreedResponses );

        }while ( agreedOption == '1' && gameOption !='5' );

        this.endGame();
    }

    private void endGame(){
        this.showMessage( "Muchas gracias por jugar. Hasta luego" );
        this.gameController.getPlayerController().createPlayerFile();
        this.input.close();
    }

    private final String TRY_AGAIN_ERROR = "Opción inválida!\n";

    public void showMessage(String message){
        System.out.println( message );
    }
    public String requestNumberConsole(String requestMessage){
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

    public String requestNumber(String textInfo){
        String intValue = " ";
        do {
            if (!textInfo.isEmpty()) {
                intValue =  textInfo ;
            }else{
                return intValue;
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

    private Integer[] intArrayToInteger(int[] intArray){
        Integer [] integerArray = new Integer[intArray.length];
        for ( int i = 0; i <integerArray.length ; i++ )
        {
            integerArray[i] = Integer.valueOf( intArray[i] );
        }

        return integerArray;
    }
    /**
     * This is the play method that receives players and easy modes
     * to handle players switch and scores
     * @param player1
     * @param player2
     * @param isEasy
     * @param isMedium
     * @param isHard
     */

    public void play(Player player1, Player player2, boolean isEasy, boolean isMedium, boolean isHard){

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
                    System.out.print( "COM-FAC escogio Posicion: " + position + "\t\t\t");
                    this.requestedBox = BoxController.searchBoxById(position);
                    side = ArtificialIntelligence.aiEasyGetLineChar( this.requestedBox );
                    System.out.print( "Lado: " + GameController.convertCharToName( side ) + "\n" );
                }else if ( isMedium ){
                        position =ArtificialIntelligence.aiIntermediateGetBoxId(this.boxMatrix);
                            System.out.print( "COM-MED escogio Posicion: " + position + "\t\t\t");
                        this.requestedBox = BoxController.searchBoxById(position);
                        side = ArtificialIntelligence.aiIntermediateGetLineChar( this.requestedBox );
                            System.out.print( "Lado: " + GameController.convertCharToName( side )+ "\n");
                }else{
                    position =ArtificialIntelligence.aiIntermediateGetBoxId(this.boxMatrix);
                    System.out.print( "COM-DIF escogio Posicion: " + position + "\t\t\t");
                    this.requestedBox = BoxController.searchBoxById(position);
                    side = ArtificialIntelligence.aiIntermediateGetLineChar( this.requestedBox );
                    System.out.print( "Lado: " + GameController.convertCharToName( side )+ "\n");
                }
            }
            if ( !isHard ){
                if ( this.gameController.createMove( firstPlayer, this.requestedBox, position, side ) ){
                    awaitPlayer = firstPlayer;
                    firstPlayer = secondPlayer;
                    secondPlayer = awaitPlayer;
                }else{
                    this.showMessage( "Jugador :" + firstPlayer.getPlayerInitials() + " puede realizar otro movimiento." );
                }
            }else{
                if ( this.gameController.createHardMove( firstPlayer, this.requestedBox, position, side ) ){
                    awaitPlayer = firstPlayer;
                    firstPlayer = secondPlayer;
                    secondPlayer = awaitPlayer;
                }
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
        System.lineSeparator();

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

    private void closingEvent( JFrame window){
        window.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing( WindowEvent e) {
                int safe = JOptionPane.showConfirmDialog(null, "Está seguro que desea salir?!",  "Confirmacion", JOptionPane.YES_NO_OPTION);

                if(safe == JOptionPane.YES_OPTION){
                    if ( window.getTitle().contains( "Bienvenida" ) ){
                        window.setDefaultCloseOperation(EXIT_ON_CLOSE);//yes
                    }else{
                        window.setDefaultCloseOperation( WindowConstants.DISPOSE_ON_CLOSE );
                        welcomeUI();
                    }

                }else{
                    window.setDefaultCloseOperation( DO_NOTHING_ON_CLOSE );
                }
            }
        });
    }
}
