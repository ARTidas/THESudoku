/*
 * 
 */
package com.the.sudoku;

/**
 * This class is responsible to assemble and call the display of the game-board.
 * 
 * @author Veres Zoltán
 */
public class Board
{
    int game_size;
    int puzzle_difficulty;
    int game_frame_width;
    int game_frame_height;
    
    /**
     * The constructor of the class.
     * 
     * @param game_size The size of the game. Eg: 3 in input means will generate a 9x9 board.
     * @param puzzle_difficulty A percentage chance that a random square will be empty.
     * @param game_frame_width The window width size in pixels.
     * @param game_frame_height The window height size in pixels.
     */
    Board(
        int game_size,
        int puzzle_difficulty,
        int game_frame_width,
        int game_frame_height
    ) {
        this.game_size = game_size;
        this.puzzle_difficulty = puzzle_difficulty;
        this.game_frame_width = game_frame_width;
        this.game_frame_height = game_frame_height;
    }
    
    /**
     * Assembles the <strong>UserInterface</strong> and
     * displays the game board.
     */
    public void display()
    {
        try
        {
            new UserInterface(
                this.game_size,
                this.puzzle_difficulty,
                this.game_frame_width,
                this.game_frame_height,
                new Grid(
                    this.game_size,
                    this.puzzle_difficulty,
                    new PuzzleGenerator(this.game_size)
                )
            )
                .display();
        }
        catch (Exception exception)
        {
            System.out.println("ERROR: Was not able to generate or display a solvable game puzzle!");
            System.out.println(exception.toString());
        }
    }
}