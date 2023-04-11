/*
 * 
 */
package com.the.sudoku;

/**
 * This class is responsible to create the {@link com.the.sudoku.GridSquare}
 * classes and hold them together to assemble the final board.
 * 
 * @author Veres Zoltán
 */
public class Grid
{
    int game_size;
    int puzzle_difficulty;
    GridSquare[][] grid_squares;
    PuzzleGenerator puzzle_generator;
    
    /**
     * The constructor of the class.
     * 
     * @param game_size
     * @param puzzle_difficulty
     * @param puzzle_generator 
     */
    Grid(
        int game_size,
        int puzzle_difficulty,
        PuzzleGenerator puzzle_generator
    )
    {
        this.game_size = game_size;
        this.puzzle_difficulty = puzzle_difficulty;
        this.grid_squares = new GridSquare
            [(this.game_size * this.game_size) + 1]
            [(this.game_size * this.game_size) + 1] 
        ;
        this.puzzle_generator = puzzle_generator;
        
        for(int row_index = 1; row_index <= (this.game_size * this.game_size); row_index++)
        {
            for(int column_index = 1; column_index <= (this.game_size * this.game_size); column_index++)
            {
                this.grid_squares[row_index][column_index] = new GridSquare(
                    this,
                    this.game_size,
                    this.puzzle_difficulty,
                    row_index,
                    column_index,
                    this.puzzle_generator
                );
            }
        }
    }
}
