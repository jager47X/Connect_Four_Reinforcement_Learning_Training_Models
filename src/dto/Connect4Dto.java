package dto;

import target.Connect4;

import java.util.List;
public class Connect4Dto extends BaseDto {
    Connect4 board;

    public Connect4Dto(Connect4 board) {
        super(board);//do nothing
        this.board=board;
    }
    public Connect4 getBoard() {
        return board;
    }

    public void setBoard(Connect4 board) {
        board = board;
    }

    public void addLine(String Line){

    }
    @Override
    public void exportCSV() {
        // Implement export logic for Connect4Dto


    }

    @Override
    public Comparable import_CSV() {
        return null;
    }


    public String hashing() {
        StringBuilder hashcode = new StringBuilder("");
        //add activeplayer 1 or 2
        String player ="";
        if (board.getActivePlayer() == board.PLAYER2) player = "1";
        else player = "2";
        hashcode.append("P");
        hashcode.append(player);

        String location=Integer.toString(board.getPosition());
        hashcode.append("L");
        hashcode.append(location);

        String winner=Integer.toString(board.getWinner());
        hashcode.append("W");
        hashcode.append(winner);
        return hashcode.toString();
    }
}


