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

import java.util.Random;

public class Computer extends Battleship{
    
    private static boolean lastHit = false;
    private static int rowHold;
    private static int colHold;
    
    public Computer(char[][] board1, char[][] board2){
        super(board1,board2);
        rowHold = -1;
        colHold = -1;
    }
    /**
     * Checks to see if the move with the coords is valid and the checks to see if the index contains the same ship as passed in
     * @param xCord
     * @param yCord
     * @param letter
     * @return 
     */
    public boolean computerCheck(int xCord, int yCord, char letter){
        if ((xCord >=0 && yCord>= 0) && (xCord < 10 && yCord < 10)){ //checks if it is a valid index
            if (defend[xCord][yCord] == letter){ //checks if the letter passed in is the same as the current index
                return true;
            } 
        }
        return false;
    }
    
    /**
     * This recursive method is in charge of knowing how many times it has to fire in that direction and returns respectively
     * @param row
     * @param col
     * @param n
     * @param letter
     * @param direction
     * @return 
     */
    public int randomDirection(int row, int col, int n, char letter, char direction){
        //base case
        if (computerCheck(row,col, letter) == false){ //if the method call is false then the recursive method is done
             return n;
        } else {
            switch (direction) { //has a differnet method call for each type of direction passed in
                case 'N': //for upwards direction
                    row--;
                    n++; //incease the counter so the value can be returned 
                    return randomDirection(row, col, n, letter, direction);
                case 'E': //for right direction
                    col++;
                    n++; //incease the counter so the value can be returned 
                    return randomDirection(row, col, n, letter, direction);
                case 'S': //for down direction
                    row++;
                    n++; //incease the counter so the value can be returned 
                    return randomDirection(row, col, n, letter, direction);
                case 'W': //for left direction
                    col--;
                    n++; //incease the counter so the value can be returned 
                    return randomDirection(row, col, n, letter, direction);

                default:
                    return n; // if none of these run it will return n

            }

        }
    }
    
    /**
     * This method is in charge of the main logic behind the computer play
     * It first generates a random position and then checks if it hit a ship
     * It then checks every direction around that ship and fire that many times in each direction
     * Then calls itself if they haven't won
     * @throws InterruptedException 
     */
    public void computerPlay() throws InterruptedException{
        Random rand = new Random(); 
        int row= 0, col = 0;
        char letter=' ';
        int number;
        
        while (true) { //repeats till a valid move is generated randomnly
            row = rand.nextInt(10);
            col = rand.nextInt(10);
            if (isValid(row, col) == true && checkIfHit(row, col, getShip(row, col)) == true) { //checks to see if the move is valid and the ship was sunk
                System.out.println("Shooting at (" + row + ", " + col + ")");
                System.out.println("Hit");
                Thread.sleep(750);
                letter = getShip(row, col); //stores the ship that was hit
                playTurn(row, col); //plays the turn
                lastHit = true; //sets a variable in order to tell the if statement to run
                break;
            } else if (isValid(row, col) == true) {
                System.out.println("Shooting at (" + row + ", " + col + ")");
                System.out.println("Missed");
                Thread.sleep(750);
                playTurn(row, col); //plays the turn
                lastHit = false; 
                break;
            }
        }
        
        rowHold = row;
        colHold = col;   
        if (lastHit == true){ //checks to see if the computer hit a ship randomnly
            //North
            row--;
            number = randomDirection(row,col,0,letter, 'N');//calls the recursive method and stores the number of times thr ship appeared in that direction
            for(int i = 0; i < number; i ++){ //repats how many shots it has to take
                playTurn(row-i,col); //plays the turn
            }
            row = rowHold; //returns row back to the original value
            
            //East
            col++;
            number = randomDirection(row,col,0,letter, 'E'); //calls the recursive method and stores the number of times thr ship appeared in that direction
            for(int i = 0; i < number; i ++){ //repats how many shots it has to take
                playTurn(row,col+i);   
            }
            col = colHold;  //returns col back to the original value
            
            //South
            row++;
            number = randomDirection(row,col,0,letter, 'S'); //calls the recursive method and stores the number of times thr ship appeared in that direction
            for(int i = 0; i < number; i ++){  //repats how many shots it has to take        
                playTurn(row+i,col); 
            }
            row = rowHold; //returns row back to the original value
            
            //West
            col--;
            number = randomDirection(row, col, 0, letter, 'W'); //calls the recursive method and stores the number of times thr ship appeared in that direction
            for(int i = 0; i < number; i ++){  //repats how many shots it has to take
                playTurn(row,col-i);
            }
            
            if (checkIfWin() == false){ //checks to make sure the computer hasn't won so it can play another move
                System.out.println("Computer is going again......3");
                Thread.sleep(500);
                System.out.println("......2");
                Thread.sleep(500);
                System.out.println("......1");
                Thread.sleep(500);
                computerPlay(); //calls itself
            }
            
        }

    }
}
