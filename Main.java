/**
 * By: Mitchell Dolny
 * Date: 6/2/2021
 * This is an application for the popular board game called battleship. The game 
 * includes one user playing the game against a computer. The computer is of 
 * hard difficulty as the only time is misses is when it doesn't hit it after 
 * firing in a random spot. This program has been made for easy advancement to it 
 * whether it is adding different levels of computer play, more/less ships, and quickly
 * adding a second player to play against
 */
package main;
//imports
import java.util.*;
import java.io.*;

public class Main {
    
    //create a random variable
    static Random rand = new Random();
    
   
    public static void main(String[] args) throws IOException, InterruptedException{
        //creates 4 boards, 2 for the players attacking and defending board, and 2 for the computers attacking and defending board
        char[][] playerBoard = new char[10][10];
        char[][] computerBoard = new char[10][10];
        char[][] attackBoardPlayer = new char[10][10];
        char[][] attackBoardComputer = new char[10][10];
        int row = 0, col = 0;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        
        Battleship player = new Battleship(attackBoardPlayer, computerBoard); //creates a player object for the plaer
        Computer computer = new Computer(attackBoardComputer, playerBoard); //creates a class for the computer using a child class
        //creates empty boards for all the boards
        emptyBoard(playerBoard);
        emptyBoard(computerBoard);
        emptyBoard(attackBoardPlayer);
        emptyBoard(attackBoardComputer);
        //creates random boards for the user and the computer
        randomBoard(playerBoard);
        randomBoard(computerBoard);
        //starts the game
        System.out.println("Welcome to Battleship\nIn this battleship you have 10 ships\n\t4, 2 ships\n\t3, 3 ships\n\t2, 4 ships\n\t1, 5 ship");
        Thread.sleep(3000);
        System.out.println("The Computer's AI is set to hard mode so because of that you will be given the size of ship you hit: A-D is a 2 Ship, E-G is a 3 ship, H&I is a 4 ship, and J is a 5 ship");
        Thread.sleep(3000);
        System.out.println("Here is your board:");
        computer.displayBoard(playerBoard);
        Thread.sleep(3000);
        System.out.println("Please type anything to read the rules");
        in.readLine();
        System.out.println("The rules of Battleship goes as follow\n\tYou first choose your row and column where the shot will be shot\n\tIf You hit a ship you then go again until you miss, but if you miss your turn ends\n\tFirst to sink all the opponents ships wins");
        System.out.println("Please type anything to start");
        in.readLine();
        
        while (true){ //repeats till the computer or the user is victorious 
           
            while (true){ //repeats till the player misses
               while (true){//repeats till the user correclty gives a valid coordinate to shoot
                    row = -1;
                    col = -1;
                    try{
                        System.out.println("Please enter the row that you would like to play");
                        row = Integer.valueOf(in.readLine());
                        System.out.println("Please enter the column that you would like to play");
                        col = Integer.valueOf(in.readLine());

                    } catch (NumberFormatException e){ //catches if they enter a non integer input
                        System.out.println("Integer Value.");
                    }

                    if (player.isValid(row, col) == true){ //checks to see if the move was valid
                        player.playTurn(row, col);//cals the play turn method from the player class which plays the move
                        System.out.println("Your Attacking Board!!!!");
                        player.displayBoard(attackBoardPlayer); //displays the computers attacking board
                        break;
                    }
                    System.out.println("Spot filled shoot again");
                } //end of checking input 
               if (player.getShip(row, col) == 'o'){ //checks to see if the player missed and if true displays a unpleasent message
                   System.out.println("You missed. You're bad.");
                   break;
               } else if (player.checkIfWin() == true){//checks playing loop as the player has won
                   break;
               }
            }
            if (player.checkIfWin() == true){ //breaks the loop as player has won
                break;
            }
            Thread.sleep(1500);
            
            computer.computerPlay();//cals the computer play method to simulate their turn
            System.out.println("Your Defending Board!");
            computer.displayBoard(playerBoard); //displays the board
            if (computer.checkIfWin() == true){
                break;
            }
        }
        
        if (player.checkIfWin() == true){ //checks to see if they player has won
            System.out.println("You have won the game. Here is your defending board");
            computer.displayBoard(playerBoard);
        } else { // if the player didn't win then that means the computer won
            System.out.println("The computer has won the game. Here is your attacking board");
            player.displayBoard(attackBoardPlayer);
        }
        
    }
    
    public static void emptyBoard(char[][] arr){
        for (int i = 0; i < 10; i ++){
            for (int x = 0; x< 10; x++){
                arr[i][x] = '/';//fills every spot in the array with forward slashes
            }
        }
    }
    
    public static void randomVert(int n, char letter, char[][] arr){ //places a random vertical ship of a certain size and letter
        int row;
        int col;       
        while (true){ //repeats till a ship has been placed
            row = rand.nextInt(10);
            col = rand.nextInt(10);
            
            if (checkVert(n, arr, row, col) == true){ //checks to see if the shup can be placed in the direction given
                placeVert(arr, letter, n, row, col);//places the ship
                break;
            } 
        }
    }
    
    public static void randomHorz(int n, char letter, char[][] arr){ //places a random horizontal ship of a certain size and letter
        int row;
        int col;
        while (true){ //repeats till a ship has been placed
            row = rand.nextInt(10);
            col = rand.nextInt(10);
            
            if (checkHorz(n, arr, row, col) == true){ //checks if a horzontial ship can be placed
                placeHorz(arr, letter, n, row, col); //places the ship horz
                break;
            } 
        }
    }
    
    public static void randomBoard(char[][] arr){

       char letter = 'a';
       //places all ships of all sizes and letters and directions
       randomVert(2,letter,arr);
       letter++;
       randomVert(2,letter,arr);
       letter++;
       randomHorz(2,letter,arr);
       letter++;
       randomHorz(2,letter,arr);
       letter++;
       randomVert(3,letter,arr);
       letter++;
       randomHorz(3,letter,arr);
       letter++;
       randomHorz(3,letter,arr);
       letter++;
       randomVert(4,letter,arr);
       letter++;
       randomHorz(4,letter,arr);
       letter++;
       randomVert(5,letter,arr); 
    }
     
    public static void placeVert(char[][] arr, char letter, int n, int row, int col){
       
        for (int i = 0; i < n; i ++){ //repeats for however long the ship is
            if (row < n - 1){ // if the cord is less than the ship size it cant place the ship upwards without error so it has to go down
                arr[row + i][col] = letter; //places ship down from cords
            } else {
                arr[row - i][col] = letter; //places ships up from cords
            }
               
        }
    }
    
    public static void placeHorz(char[][] arr, char letter, int n, int row, int col){
        
 
        for (int i = 0; i < n; i ++){//repeats for however long the ship is
            if (col < n -1){// if the cord is less than the ship size it cant place the ship left without error so it has to go right
                arr[row][col + i] = letter;
            } else {
                arr[row][col - i] = letter; //places to the left
            }
        }    
       
    }
    
    public static boolean checkVert(int n, char[][] arr, int row, int col){
        for (int i = 0; i < n; i ++){ //repeats ship size
            if (row < n -1 ) {
                if (arr[row + i][col] != '/'){ //checks to see if the coordinates for the ship can be placed all at once and if it cant then it returns false
                    return false;
                }
                
            } else {
                if (arr[row - i][col] != '/'){ //checks to see if the coordinates for the ship can be placed all at once and if it cant then it returns false
                    return false;
                }  
                
            }
        }
        return true; //if it hasn't been returned false yet that means the ship can be placed
    }
    
    public static boolean checkHorz(int n, char[][] arr, int row, int col){
        for (int i = 0; i < n; i ++){
            if (col < n - 1 ) { 
                if (arr[row][col+i] != '/'){//checks to see if the coordinates for the ship can be placed all at once and if it cant then it returns false
                   return false; 
                }  
            } else {
                if (arr[row][col - i] != '/'){ //checks to see if the coordinates for the ship can be placed all at once and if it cant then it returns false
                    return false;
                }  
                
            }
        }
        return true; //if it hasn't been returned false yet that means the ship can be placed
    }
    
}
