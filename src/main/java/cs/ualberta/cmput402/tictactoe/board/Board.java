package cs.ualberta.cmput402.tictactoe.board;

import cs.ualberta.cmput402.tictactoe.board.exceptions.InvalidMoveException;

/**
 * Created by snadi on 2018-07-16.
 */
public class Board {

    public enum Player {X, O, NONE};
    private Player currentPlayer;
    private Player winner;
    private Player board[][];

    public Board(){
        board = new Player[3][3];
        initBoard();
        winner = null;
        currentPlayer = Player.X;
    }

    private void initBoard(){
        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                board[i][j] = Player.NONE;

    }

    public void playMove(int row, int col) throws InvalidMoveException {

        if(row < 0 || row >= 3 || col <0 || col >=3)
            throw new InvalidMoveException("Input coordinates are outside the board. Try again");

        if(!isSquareAvailable(row, col)){
            //the given coordinates already contain a played move
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid Move: Square ");
            stringBuilder.append(row);
            stringBuilder.append(",");
            stringBuilder.append(col);
            stringBuilder.append(" already contains ");
            stringBuilder.append(getSymbol(board[row][col]));
            throw new InvalidMoveException(stringBuilder.toString());
        }else{
            board[row][col] = currentPlayer;

            if (hasWon(row, col))
                winner = currentPlayer;
            else if (isBoardFull()) {
                winner = Player.NONE;
            }
            else if(currentPlayer == Player.X)
                currentPlayer = Player.O;
            else
                currentPlayer = Player.X;
        }

    }

    private boolean isBoardFull() {
        boolean boardFull = true;
        for (int col = 0; col < 3; col++) {
            for (int row = 0; row < 3; row++) {
                if (isSquareAvailable(row, col)) {
                    boardFull = false;
                    break;
                }
            }
        }
        return boardFull;
    }


    private boolean isSquareAvailable(int row, int col){
        return (board[row][col] != Player.X && board[row][col] != Player.O);
    }

    public String getSymbol(Player player){
        switch(player){
            case X:
                return "X";
            case O:
                return "O";
            case NONE:
                return " ";
            default:
                return "UNKNOWN SYMBOL";
        }
    }

    public boolean hasWon(int lastColPlayed, int lastRowPlayed){

        //check horizontal
        if (board[lastColPlayed][0].equals(board[lastColPlayed][1]) && board[lastColPlayed][1].equals(board[lastColPlayed][2])){
            return true;
        }
        //check vertical
        else if(board[0][lastRowPlayed].equals(board[1][lastRowPlayed]) && board[1][lastRowPlayed].equals(board[2][lastRowPlayed])){
            return true;
        }
        //check diagonal
        else{
            if(isOnRightDiag(lastColPlayed, lastRowPlayed) && board[0][0].equals(board[1][1]) && board[1][1].equals(board[2][2]))
                return true;
            else if (isOnLeftDiag(lastColPlayed, lastRowPlayed) && board[0][2].equals(board[1][1]) && board[1][1].equals(board[2][0]))
                return true;
        }

        return false;
    }

    private boolean isOnRightDiag(int col, int row){
        return (col == 0 && row == 0) || (col == 1 && row == 1) || (col == 2 & row == 2);
    }

    private boolean isOnLeftDiag(int col, int row){
        return (col == 0 && row == 2) || (col == 1 && row == 1) || (col == 2 & row == 0);
    }

    public void printBoard(){
        for(int i  = 0; i < 3; i++){
            for(int j = 0 ; j < 3; j++){

               System.out.print(getSymbol(board[i][j]));

                if (j == 2)
                    System.out.println("");
                else
                    System.out.print(" | ");
            }
            System.out.println("----------");
        }
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Player getWinner() {
        return winner;
    }

    public Player getPlayerAtPos(int row, int col){
        return board[row][col];
    }


}
