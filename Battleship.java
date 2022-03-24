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

public class Battleship {
    
    private char attack[][] = new char [10][10];
    public char defend[][] = new char [10][10];
    
    public Battleship(char[][] board1, char[][] board2){
        attack = board1;
        defend = board2;
        
    }
    /**
     * This method is in charge of deciding whether the move played is a hit or not and returns a value based on that
     * @param r
     * @param c
     * @param letter
     * @return 
     */
    public boolean checkIfHit(int r, int c, char letter){
        if (letter != '/' && letter !='x' & letter != 'o'){ //if the letter on the board at the spot is a dhip not hit then it coutns as a hit
            return true;
        } else {
            return false;
        }
    }
    /**
     * This method is in charge of the firing of the move, so when given 2 coords it will play a hit or miss accordingly
     * @param r
     * @param c 
     * @throws java.lang.InterruptedException 
     */
    public void playTurn(int r, int c) throws InterruptedException{
        char letter = getShip(r,c);
        if (checkIfHit(r, c, letter) == true){ //checks to see if this is true and then plays the hitting move
            defend[r][c] = 'x';
            attack[r][c] = letter;
        } else {
            defend[r][c] = 'o';
            attack[r][c] = 'o';
        }
        if (checkIfSunk(r,c, letter) == true){ //check to see if the ship was sunk
            System.out.println("The " + letter + " ship has been sunk!");
            Thread.sleep(1000);
        }
        
        
    }

    public char getShip (int row, int col){
            return defend[row][col]; //returns a ship 
    }
    
    /**
     * This method compares every index to see if there is a ship left and if there isn't that means the object won
     * @return 
     */
    public boolean checkIfWin(){
        for (int i = 0; i < 10; i ++){
            for (int x = 0; x < 10; x++){
                if (defend[i][x] != 'x' && defend[i][x] != 'o' && defend[i][x] != '/' ){ //checks to see if there are no ships left
                    return false;
                }
            }
        }
        return true;
    }
    /**
     * Checks to see if the ship has been sunk
     * @param r
     * @param c
     * @param let
     * @return 
     */
    public boolean checkIfSunk(int r, int c, char let){
        for (int i = 0; i < 10; i ++){
            for (int x = 0; x < 10; x++){
                if (defend[i][x] == let){ //checks to see if there are any of the letters left of thehit ship and if not then it is considered sunk
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Checks to see if the move is valid by first looking at the index and then by seeing if at that spot it is a blank space
     * @param r
     * @param c
     * @return 
     */
    public boolean isValid(int r, int c){
        if ((r >=0 && c>= 0) && (r < 10 && c <10)){ //amkes sure the row and column are between 0-9
            if (attack[r][c] == '/'){ //checks for a blank space
                return true;
            } 
        }
        return false;
    }
    
    /**
     * Displays the board in a nice battleship kind of way
     * @param arr 
     */
    public void displayBoard(char[][] arr){
        System.out.println("  | 0 | 1 | 2 | 3 | 4 | 5 | 6 | 7 | 8 | 9");
        for(int i = 0; i < 10; i ++){
            System.out.println("-----------------------------------------");
            System.out.println(i +" | " + arr[i][0] + " | " + arr[i][1] + " | " + arr[i][2] + " | " + arr[i][3] + " | " + arr[i][4] + " | " + arr[i][5] + " | " + arr[i][6] + " | " + arr[i][7] + " | " + arr[i][8] + " | " + arr[i][9]);
            
        }
    }
    
}
