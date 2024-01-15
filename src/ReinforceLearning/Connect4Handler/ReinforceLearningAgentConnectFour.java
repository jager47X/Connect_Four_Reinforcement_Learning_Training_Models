package ReinforceLearning.Connect4Handler;

import ReinforceLearning. AbstractReinforceLearningAgent2D;
import dao.QTableDao;
import dto.Connect4Dto;
import dto.QTableDto;
import target.Board;
import target.Connect4;
import target.RuleBasedAI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class  ReinforceLearningAgentConnectFour extends AbstractReinforceLearningAgent2D {
    int TotalReward;

    public ReinforceLearningAgentConnectFour (Connect4Dto connect4dto) {
        super(connect4dto);
        TotalReward=0;
    }
    @Override
    public void trainAgent() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai

        QTableDto qTableDto=new QTableDto(Environment);
        //  for (int i = 0; i <episodes ; i++) {//set how many times to play
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);
        loadQTableFromCSV(QTable);
        //USE AI
        resetBoard(Environment);
        do {
            if(isGameOver(Environment)){
                Environment.setWinner(0);
                saveQTableToCSV(qTableDto);
                break;
            }
            //update environment/state
            Environment = connect4Dto.getGame();

            if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
            }
                   while(!Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()))) {//update move
                       Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()));
                    }
            // calculateReward(board.getGame(),);
            if (Environment.winCheck()) {//check winner 1 or 2
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else if(Environment.getWinner()==0){//withdraw
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else{
                QTable.addLine(Environment.getTurn(),qTableDto);
            }
        }while(!isGameOver(Environment));

    }

    @Override
    public void testAgent() {//use multi-thread //if epsodes=1 then vs human if not ai vs ai

        QTableDto qTableDto=new QTableDto(Environment);
        Scanner keyboard = new Scanner(System.in);
        //  for (int i = 0; i <episodes ; i++) {//set how many times to play
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);
        loadQTableFromCSV(QTable);
        //USE AI
        resetBoard(Environment);
        do {
            if(isGameOver(Environment)){
                Environment.setWinner(0);
                saveQTableToCSV(qTableDto);
                break;
            }
            //update environment/state
            Environment = connect4Dto.getGame();

            if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
            }
            while(!Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()))) {//update move
                Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()));
            }
            // calculateReward(board.getGame(),);
            if (Environment.winCheck()) {//check winner 1 or 2
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else if(Environment.getWinner()==0){//withdraw
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else{
                QTable.addLine(Environment.getTurn(),qTableDto);
            }
        }while(!isGameOver(Environment));


    }
    @Override
    public void trainAgent(int episodes) {
        QTableDto qTableDto=new QTableDto(Environment);
        //  for (int i = 0; i <episodes ; i++) {//set how many times to play
        Environment.setActivePlayer(Connect4.PLAYER2);
        Environment.setTurn(0);
        loadQTableFromCSV(QTable);
        //USE AI
        resetBoard(Environment);
        do {
            if(isGameOver(Environment)){
                Environment.setWinner(0);
                saveQTableToCSV(qTableDto);
                break;
            }
            //update environment/state
            Environment = connect4Dto.getGame();

            if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                Environment.setActivePlayer(Connect4.PLAYER2);
                Environment.setNonActivePlayer(Connect4.PLAYER1);
            } else {
                Environment.setActivePlayer(Connect4.PLAYER1);
                Environment.setNonActivePlayer(Connect4.PLAYER2);
            }
            while(!Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()))) {//update move
                Environment.playerDrop(RuleBasedAI.makeMove(Environment.getBoard(),Environment.getActivePlayer(),Environment.getNonActivePlayer()));
            }
            // calculateReward(board.getGame(),);
            if (Environment.winCheck()) {//check winner 1 or 2
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else if(Environment.getWinner()==0){//withdraw
                QTable.addLine(Environment.getTurn(),qTableDto);
                saveQTableToCSV(qTableDto);
                break;
            }else{
                QTable.addLine(Environment.getTurn(),qTableDto);
            }
        }while(!isGameOver(Environment));




    }
   // }

  
    @Override
    public void testAgent(int episode) {//self learning or with player //use multi-thread

        QTableDto qTableDto=new QTableDto(Environment);
        Scanner keyboard = new Scanner(System.in);
            Environment.setActivePlayer(Connect4.PLAYER2);
            Environment.setTurn(0);
            loadQTableFromCSV(QTable);
            //USE AI
            resetBoard(Environment);
            do {
                //update environment/state
                Environment = connect4Dto.getGame();
                printBoard(Environment);
                if (Environment.getActivePlayer() == Connect4.PLAYER1) {//switch player
                    Environment.setActivePlayer(Connect4.PLAYER2);
                    Environment.setNonActivePlayer(Connect4.PLAYER1);
                } else {
                    Environment.setActivePlayer(Connect4.PLAYER1);
                    Environment.setNonActivePlayer(Connect4.PLAYER2);
                }
                if(Environment.getActivePlayer() ==Connect4.PLAYER1){//player move
                    if(episode==0){
                        //human drop
                        Environment.playerDrop(keyboard.nextInt());
                    }else{// AI move
                        while(!Environment.playerDrop(selectAction())){
                            Environment.playerDrop(selectAction());
                        }
                    }
                }else{//AI move
                    while(!Environment.playerDrop(selectAction())){
                        Environment.playerDrop(selectAction());
                    }
                }

                // calculateReward(board.getGame(),);
                if (Environment.winCheck()) {//check winner 1 or 2
                    System.out.println((Environment.getActivePlayer() == Connect4.PLAYER1) ? "player1 Won!" : "player2 Won!");
                    QTable.addLine(Environment.getTurn(),qTableDto);
                    printBoard(Environment);
                    saveQTableToCSV(qTableDto);
                    break;
                }else if(Environment.getWinner()==0){//withdraw
                    System.out.println("Withdraw!");
                    QTable.addLine(Environment.getTurn(),qTableDto);
                    printBoard(Environment);
                    saveQTableToCSV(qTableDto);
                    break;
                }else{
                    QTable.addLine(Environment.getTurn(),qTableDto);
                }
            }while(!isGameOver(Environment));
        printBoard(Environment);
        }

    //}


    public void resetBoard(Connect4 game) {
        game.resetBoard();
    }


    @Override
    public boolean makeMove(int action) {
        return  Environment.playerDrop(action);
    }

    @Override
    public void calculateReward(Board board, int row, int col,char activePlayer) {
    //4^0//4^1//4^2//4^3//4^4
        int reward= switch (checkConnection( board, row,col,activePlayer)) {
            case 1 -> 4;
            case 2 -> 16;
            case 3 -> 64;
            case 4 -> 264;
            default -> 1;
        };
        TotalReward+=reward;
    }
    private int checkConnection(Board board, int row, int col,char activePlayer) {
        int totalConnection = 0;
        int connection = 0;
        for (int c = Math.max(0, col - 3); c <= Math.min(Environment.getCOLS_SIZE() - 4, col); c++) {
            if (board.getTile(c , row ).getValue() ==activePlayer) {
                connection++;
            } else {
                connection = 0;
            }
            totalConnection+=connection;
        }

        // Check vertically
        connection = 0;
        for (int r = Math.max(0, row - 3); r <= Math.min(Environment.getROWS_SIZE()- 4, row); r++) {
            if (board.getTile(col , r ).getValue()  == activePlayer) {
                connection++;
            } else {
                connection = 0;
            }
            totalConnection+=connection;
        }

        // Check diagonally (top-left to bottom-right)
        connection = 0;
        for (int r = Math.max(0, row - 3), c = Math.max(0, col - 3); r <= Math.min(Environment.getROWS_SIZE()- 4, row) && c <= Math.min(Environment.getCOLS_SIZE() - 4, col); r++, c++) {
            if (board.getTile(c , r ).getValue() == activePlayer) {
                connection++;

            } else {
                connection = 0;
            }
            totalConnection+=connection;
        }

        // Check diagonally (top-right to bottom-left)
        connection = 0;
        for (int r = Math.max(0, row - 3), c = Math.min(Environment.getCOLS_SIZE() - 1, col + 3); r <= Math.min(Environment.getROWS_SIZE()- 4, row) && c >= Math.max(3, col); r++, c--) {
            if (board.getTile(c, r).getValue() == activePlayer) {
                connection++;
            } else {
                connection = 0;
            }
            totalConnection+=connection;
        }


        return totalConnection;
    }

    private boolean isGameOver(Connect4 game) {
        return game.getWinner() != -1;
    }


    private void printBoard(Connect4 game) {
        game.displayBoard();
    }

    @Override
    public int[] getLegalActions() {
        List<Integer> legalActionsList = new ArrayList<>();
// Iterate over each column to check if it's a legal action
            for (int col = 0; col < Environment.getCOLS_SIZE(); col++) {
                if (Environment.isValidColumn(col)) {
                    legalActionsList.add(col);
                }
            }

        // Convert the list of legal actions to an array
        int[] legalActions = new int[legalActionsList.size()];
        for (int i = 0; i < legalActions.length; i++) {
            legalActions[i] = legalActionsList.get(i);
        }


        return legalActions;
    }

    @Override
    public String stateToIndex(Connect4Dto state) {
        return state.hashing();
    }



    @Override
    public int selectAction() {
        return super.selectAction();
    }

    @Override
    public void saveQTableToCSV(QTableDto dto) {
    dto.exportCSV();
    }

    @Override
    public void loadQTableFromCSV(QTableDao dao) {
        setQTable(dao);
    }


    // Additional Connect Four-specific methods and logic go here
}
