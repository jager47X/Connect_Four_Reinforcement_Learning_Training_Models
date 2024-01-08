package target;

public class Connect4 {
        private static final int COLS_SIZE = 7;
        private static final int ROWS_SIZE = 6;
        public final char PLAYER1 = 'X';
        public final char PLAYER2 = 'O';
        public final char EMPTY = '_';
        private static final int SIZE_OF_BOARD=COLS_SIZE*ROWS_SIZE;
        private char activePlayer;

        public static final char[] board = new char[SIZE_OF_BOARD];
        private int turn = 0;
        private int winner = -1;
        private int position;

        public Connect4() {
            ResetBoard();
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

    public boolean PlayerDrop(int dropChoice) {
            dropChoice--;//adjust to index of board
            if (CheckCells() && isValidColumn(dropChoice)) {

                for (int row = ROWS_SIZE - 1; row >= 0; row--) {
                     SetPosition(dropChoice,row);
                    if (board[position] == EMPTY) {
                        System.out.println("position:"+position+" = col_size:7 *row:"+row+" +col:"+dropChoice);
                        board[position] = activePlayer;
                        turn++;
                        return true;
                    }
                }
            } else {
                System.out.println("(!) Invalid Selection");
            }

            return false;
        }

        private boolean isValidColumn(int column) {
            return column >= 0 && column < COLS_SIZE;
        }

        public void SetPosition(int col, int row) {
            position= row * COLS_SIZE + col;
        }
        public int getPosition() {
            return position;
        }
        public void DisplayBoard() {
            for (int cell = 0; cell < SIZE_OF_BOARD; cell++) {
                System.out.print(board[cell]);
                if ((cell + 1) % COLS_SIZE == 0) {
                    System.out.println();
                }
            }

            System.out.println();
        }

//_0_1_2_3_4_5_6
//_7_8_9_10_11_12_13
//_14_15_16_17_18_19_20
//_21_22_23_24_25_26_27
//_28_29_30_31_32_33_34
//_35_36_37_38_39_40_41
        public void ResetBoard() {
            for (int BOARD_COLUMN = 0; BOARD_COLUMN <= COLS_SIZE - 1; BOARD_COLUMN++) {
                for (int BOARD_ROW = 0; BOARD_ROW <= ROWS_SIZE - 1; BOARD_ROW++) {
                    SetPosition(BOARD_COLUMN, BOARD_ROW);
                    board[position] = EMPTY;
                }
            }
        }


        public boolean WinCheck() {
            // Check for horizontal wins
            for (int row = 0; row < ROWS_SIZE; row++) {
                for (int col = 0; col <= COLS_SIZE - 4; col++) {
                     SetPosition(col, row);
                    if (board[position] == activePlayer &&
                            board[position + 1] == activePlayer &&
                            board[position + 2] == activePlayer &&
                            board[position + 3] == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for vertical wins
            for (int col = 0; col < COLS_SIZE; col++) {
                for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                     SetPosition(col, row);
                    if (board[position] == activePlayer &&
                            board[position + COLS_SIZE] == activePlayer &&
                            board[position + 2 * COLS_SIZE] == activePlayer &&
                            board[position + 3 * COLS_SIZE] == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for diagonal wins (bottom-left to top-right)
            for (int col = 0; col <= COLS_SIZE - 4; col++) {
                for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                    SetPosition(col, row);
                    if (board[position] == activePlayer &&
                            board[position + COLS_SIZE + 1] == activePlayer &&
                            board[position + 2 * COLS_SIZE + 2] == activePlayer &&
                            board[position + 3 * COLS_SIZE + 3] == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            // Check for diagonal wins (bottom-right to top-left)
            for (int col = 3; col < COLS_SIZE; col++) {
                for (int row = 0; row <= ROWS_SIZE - 4; row++) {
                   SetPosition(col, row);
                    if (board[position] == activePlayer &&
                            board[position + COLS_SIZE - 1] == activePlayer &&
                            board[position + 2 * COLS_SIZE - 2] == activePlayer &&
                            board[position + 3 * COLS_SIZE - 3] == activePlayer) {
                        winner = (activePlayer == PLAYER1) ? 1 : 2;
                        return true;
                    }
                }
            }

            if (!CheckCells()) { // no winner
                winner = 0;
            } else { // game will resume
                winner = -1;
            }
            return false;
        }
        public boolean CheckCells() {//check if any row is empty
            int full = 0;
            for (int BOARD_ROW = 0; BOARD_ROW < ROWS_SIZE ; BOARD_ROW++) {
                SetPosition(0, BOARD_ROW);
                if (board[position] != EMPTY)
                    full++;
            }
            if (full >= 7) {
                System.out.println("Board is full. Withdraw.");
                return false;
            }
            return true;
        }



    }//end of class



