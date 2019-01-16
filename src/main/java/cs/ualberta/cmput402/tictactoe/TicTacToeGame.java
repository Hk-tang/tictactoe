package cs.ualberta.cmput402.tictactoe;

import cs.ualberta.cmput402.tictactoe.board.Board;
import cs.ualberta.cmput402.tictactoe.board.Board.Player;
import cs.ualberta.cmput402.tictactoe.board.exceptions.InvalidMoveException;
import cs.ualberta.cmput402.tictactoe.board.exceptions.InvalidResponseException;

import java.util.Scanner;

/**
 * Created by snadi on 2018-07-18.
 */
public class TicTacToeGame {

    private Board board;
    private Boolean playing;
    private Integer oScore = 0; //Overall score for player O
    private Integer xScore = 0; //Overall score for player X
    private Integer dScore = 0; //Number of games resulting in draw

    
    public TicTacToeGame(){
        board = new Board();
        playing = true;    
    }

    public void promptNextPlayer(){
        switch(board.getCurrentPlayer()){
            case X:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer()) + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;
            case O:
                System.out.println("It's player " + board.getSymbol(board.getCurrentPlayer()) + "'s turn. Please enter the coordinates of your next move as x,y: ");
                break;

        }
    }

    public void playGame(){
        Scanner keyboardScanner = new Scanner(System.in);

        while (board.getWinner() == null){
            board.printBoard();
            promptNextPlayer();
            String line = keyboardScanner.nextLine();
            String input[] = line.split(",");
            try {
                board.playMove(Integer.parseInt(input[0]), Integer.parseInt(input[1]));
            } catch (InvalidMoveException e) {
                System.out.println("Invalid coordinates. Try again");
                promptNextPlayer();
            }
        }

        board.printBoard();
        System.out.println("Player " + board.getWinner() + " has won the game!\n");
        calcScoreBoard(board.getWinner());
        printScoreBoard();
    }

    public void printScoreBoard(){
        System.out.println("--------------------------------");
        System.out.println("| X-wins: "+xScore+" O-wins: "+oScore+" Draws: "+dScore+" |");
        System.out.println("| X-loss: "+oScore+" O-loss: "+xScore+" |");
        System.out.println("--------------------------------");
    }

    public void calcScoreBoard(Board.Player winner){
        if(winner == Player.O){
            oScore++;
        }
        else if(winner == Player.X){
            xScore++;
        }
        else{
            dScore++;
        }
    }

    private Boolean checkResponse(String response) throws InvalidResponseException {
        switch(response.trim().toLowerCase().charAt(0)){
            case 'y':
                return true;
            case 'n':
                return false;
            default:
                throw new InvalidResponseException("Invalid character provided!");
            }
    }

    public static void main(String args[]){
        TicTacToeGame game = new TicTacToeGame();
        Scanner keyboardScanner = new Scanner(System.in);

        while (game.playing){
            game.board = new Board();
            game.playGame();            
            while (true){
                System.out.println("Would you like to play again? (Y/N):");                        
                String response = keyboardScanner.nextLine();
                try {
                    game.playing = game.checkResponse(response);
                    break;       
                } catch (InvalidResponseException e) {
                    System.out.println("Invalid selection. Please try again"); 
                }
            }
            
        }
    }
}
