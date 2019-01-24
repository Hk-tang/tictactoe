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
    private Integer oWins; //Overall wins for player O
    private Integer xWins; //Overall wins for player X
    private Integer ties; //Number of games resulting in ties

    
    public TicTacToeGame(){
        board = new Board();
        playing = true;   
        oWins = 0; //Initialize scores to be zero
        xWins = 0;
        ties = 0;
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
        calcScoreBoard(board.getWinner());
        printScoreBoard();

        if (board.getWinner() == Player.NONE) {
            System.out.println("It is a tie!\n");
        } else {
            System.out.println("Player " + board.getWinner() + " has won the game!\n");
        }

    }

    public void printScoreBoard(){
        System.out.println("--------------------------------");
        System.out.println("| X-wins: "+xWins+" O-wins: "+oWins+" Draws: "+ties+" |");
        System.out.println("| X-loss: "+oWins+" O-loss: "+xWins+" |");
        System.out.println("--------------------------------");
    }

    public void calcScoreBoard(Board.Player winner){
        if(winner == Player.O){
            oWins++;
        }
        else if(winner == Player.X){
            xWins++;
        }
        else{
            ties++;
        }
    }

    private Boolean checkResponse(String response) throws InvalidResponseException {
        if (response.trim().length() > 1){
            throw new InvalidResponseException("Response string too  long");
        }
        
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
