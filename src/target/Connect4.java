package target;

public class Connect4{
        protected static final int COLS_SIZE = 7;
        protected static final int ROWS_SIZE = 6;
    public   static final char PLAYER1 = 'X';
    public  static final char PLAYER2 = 'O';
    public static final char EMPTY = '_';
    private static final int SIZE_OF_BOARD=COLS_SIZE*ROWS_SIZE;
        private char activePlayer;
        private char nonActivePlayer;

        private  Board board;
        private int turn;
        private int winner;
        private int totalRewardP1;
        private int totalRewardP2;
        private int location;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public int getSIZE_OF_BOARD() {
        return SIZE_OF_BOARD;
    }

    public char getNonActivePlayer() {
        return nonActivePlayer;
    }

    public void setNonActivePlayer(char nonActivePlayer) {
        this.nonActivePlayer = nonActivePlayer;
    }

    public Connect4() {
        super();
        this.activePlayer = PLAYER1;
        this.turn=0;
        this.winner=-1;
        this.totalRewardP1 =0;
        this.totalRewardP2 =0;
        this.board = new Board(COLS_SIZE, ROWS_SIZE,EMPTY,PLAYER1,PLAYER2);
    }

    public int getTurn() {
        return turn;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getWinner() {
        return winner;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public char getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(char activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean playerDrop(int column_selection) {
        int selection=column_selection;
            column_selection--;//adjust to index of board
            if (isEmpty() && isValidColumn(column_selection)) {

                for (int row = ROWS_SIZE - 1; row >= 0; row--) {

                    if (board.getTile(row,column_selection).getValue() == EMPTY) {
                        setLocation(row,column_selection);

                  //      System.out.println("selection:"+selection);
                  //      System.out.println("location:"+location+" = col_size:7 *row:"+row+" +col:"+column_selection);
                        board.getTile(row,column_selection).setValue(activePlayer);
                        turn++;
                        calculateReward(getActivePlayer());
                        return true;
                    }
                }
            } else {
        //        System.out.println("(!) Invalid Selection");
            }

            return false;
        }

    public int getCOLS_SIZE() {
        return COLS_SIZE;
    }

    public int getROWS_SIZE() {
        return ROWS_SIZE;
    }

    public boolean isValidColumn(int column) {
            return column >= 0 && column < COLS_SIZE &&board.getTile(0,column).getValue() == EMPTY;
        }

    public int getTotalRewardP1() {
        return totalRewardP1;
    }

    public void setTotalRewardP1(int totalRewardP1) {
        this.totalRewardP1 = totalRewardP1;
    }

    public int getTotalRewardP2() {
        return totalRewardP2;
    }

    public void setTotalRewardP2(int totalRewardP2) {
        this.totalRewardP2 = totalRewardP2;
    }

    public int getLocation() {
        return location;
    }

    public void setLocation(int column,int row) {
        this.location = COLS_SIZE*row+column;
    }
    private void calculateReward(char activePlayer) {
        int totalConnection=0;
        int baseNumber=2;
        int connection = -1;

        for (int row = 0; row < ROWS_SIZE; row++) {
            for (int column = 0; column <= COLS_SIZE - 4; column++) {
                // Check if within array bounds
                if (board.getTile(row,column ).getValue() == activePlayer ){
                    connection++;
                    if( board.getTile( row,column + 1).getValue() == activePlayer ){
                        connection++;
                        if(board.getTile( row,column + 2).getValue() == activePlayer){
                            connection++;
                            if(board.getTile(row,column + 3).getValue() == activePlayer){
                                connection++;
                            }
                        }
                    }
                }

            }
        }

        totalConnection += (int) Math.pow(baseNumber,connection);
        connection = -1;
        for (int row = 0; row <= ROWS_SIZE - 4; row++) {
            for (int column = 0; column < COLS_SIZE; column++) {
                // Check if within array bounds
                if (board.getTile(row,column).getValue() == activePlayer ){
                    connection++;
                    if( board.getTile( row+1,column ).getValue() == activePlayer ){
                        connection++;
                        if(board.getTile( row+2,column ).getValue() == activePlayer){
                            connection++;
                            if(board.getTile(row+3,column ).getValue() == activePlayer){
                                connection++;
                            }
                        }
                    }
                }

            }
        }
        totalConnection += (int) Math.pow(baseNumber,connection);
        connection = -1;
        for (int row = 0; row <= ROWS_SIZE- 4; row++) {
            for (int column = 0; column <= COLS_SIZE - 4; column++) {
                // Check if within array bounds
                if (board.getTile(row,column ).getValue() == activePlayer ){
                    connection++;
                    if( board.getTile( row+1,column + 1).getValue() == activePlayer ){
                        connection++;
                        if(board.getTile( row+2,column + 2).getValue() == activePlayer){
                            connection++;
                            if(board.getTile(row+3,column + 3).getValue() == activePlayer){
                                connection++;
                            }
                        }
                    }
                }

            }
        }
        totalConnection += (int) Math.pow(baseNumber,connection);
        connection = 0;
        for (int row = 0; row < ROWS_SIZE- 4; row++) {
            for (int column = 3; column <= COLS_SIZE - 4; column++) {
                // Check if within array bounds
                if (board.getTile(row,column ).getValue() == activePlayer ){
                    connection++;
                    if( board.getTile( row+1,column - 1).getValue() == activePlayer ){
                        connection++;
                        if(board.getTile( row+2,column - 2).getValue() == activePlayer){
                            connection++;
                            if(board.getTile(row+3,column-3).getValue() == activePlayer){
                                connection++;
                            }
                        }
                    }
                }

            }
        }
        totalConnection += (int) Math.pow(baseNumber,connection);


        if(activePlayer==PLAYER1){
           this.totalRewardP1 =totalConnection;
        }else{
            this.totalRewardP2 =totalConnection;
        }


    }


    public void displayBoard() {
        System.out.println("---------------");
        for (int row = 0; row < ROWS_SIZE; row++) {
            for (int col = 0; col < COLS_SIZE; col++) {
                System.out.print(board.getTile(row,col).getValue() + " ");
            }
            System.out.println();
        }
        System.out.println("---------------");
    }

        public void resetBoard() {
          board.initializeBoard();
        }


        public boolean winCheck() {
            // Check for horizontal wins
            for (int row = 0; row < ROWS_SIZE; row++) {
                for (int column = 0; column <= COLS_SIZE - 4; column++) {
                    // Check if within array bounds
                    if (board.getTile(row,column ).getValue() == activePlayer &&
                            board.getTile( row,column + 1).getValue() == activePlayer &&
                            board.getTile(row,column + 2).getValue() == activePlayer &&
                            board.getTile(row,column + 3).getValue() == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for vertical wins
            for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                for (int column = 0; column < COLS_SIZE; column++) {
                    // Check if within array bounds
                    if (board.getTile(row,column).getValue() == activePlayer &&
                            board.getTile(row + 1,column).getValue() == activePlayer &&
                            board.getTile(row + 2,column).getValue() == activePlayer &&
                            board.getTile(row + 3,column).getValue() == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for diagonal wins (left to right)
            for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                for (int column = 0; column <= COLS_SIZE - 4; column++) {
                    // Check if within array bounds
                    if (board.getTile(row,column).getValue() == activePlayer &&
                            board.getTile(row + 1,column + 1).getValue() == activePlayer &&
                            board.getTile(row + 2,column + 2).getValue() == activePlayer &&
                            board.getTile(row + 3,column + 3).getValue() == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for diagonal wins (right to left)
            for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                for (int column = 3; column < COLS_SIZE; column++) {
                    // Check if within array bounds
                    if (board.getTile(row,column).getValue() == activePlayer &&
                            board.getTile(row + 1,column - 1).getValue() == activePlayer &&
                            board.getTile(row + 2,column - 2).getValue() == activePlayer &&
                            board.getTile(row + 3,column - 3).getValue() == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            if (!isEmpty()) { // no winner
                winner = 0;
            } else { // game will resume
                winner = -1;
            }
            return false;
        }
        public boolean isEmpty() {//check if any row is empty
            int full = 0;
            for (int row = 0; row < ROWS_SIZE ; row++) {
                if (board.getTile(row,0).getValue()!= EMPTY)
                    full++;
            }
            if (full >= COLS_SIZE) {
                System.out.println("Board is full.");
                return false;
            }
            return true;
        }



}//end of class



