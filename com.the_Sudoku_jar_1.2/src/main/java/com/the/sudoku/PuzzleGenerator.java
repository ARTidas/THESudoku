/*
 * 
 */
package com.the.sudoku;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * This class is responsible to generate a wholly solved Sudoku puzzle
 * and then depending on the difficulty, create a playable puzzle.
 * 
 * @author Veres Zoltán
 */
public class PuzzleGenerator
{
    int game_size;
    int[][] game_puzzle_solution;
    
    /**
     * The constructor of the class.
     * 
     * @param game_size The size of the game. Eg: 3 in input means will generate a 9x9 board.
     * @throws Exception In case the program failed to generate a valid puzzle.
     */
    PuzzleGenerator(int game_size) throws Exception
    {
        this.game_size = game_size;
        this.game_puzzle_solution = new int
            [(this.game_size * this.game_size) + 1]
            [(this.game_size * this.game_size) + 1]
        ;
        
        if (!this.generatePuzzle()) {
            throw new Exception("Sorry, failed to generate a playable sudoku board...");
        }
    }
    
    /**
     * Generate a puzzle. This method acts as an entry point for the
     * <strong>generatePuzzleSolution()</strong> recursive method.
     * 
     * @return boolean True if successful, False in other cases.
     */
    private boolean generatePuzzle()
    {
        //This algorithm does not guarantee a playable puzzle, 
        //  so we have to make a few brute force tries before a solvable game.
        for (int bruteforce_iterations = 0; bruteforce_iterations < 100000; bruteforce_iterations++)
        {
            if(this.generatePuzzleSolution(this.game_puzzle_solution))
            {
                //SUCCESS!
                return true;
            }
        }
        
        //FAIL!
        return false;
    }
    
    /**
     * This method gets recursively called 
     * 
     * @param game_puzzle_solution The two dimensional array to fill up for a solution.
     * @return boolean True if successfully generated a valid puzzle. False otherwise.
     */
    boolean generatePuzzleSolution(int[][] game_puzzle_solution)
    {
        //Lets generate a two dimensional array
        //  to hold all the possible values for row-column fields
        int[][][] available_values = new int
            [(this.game_size * this.game_size) + 1]
            [(this.game_size * this.game_size) + 1]
            [(this.game_size * this.game_size)]
        ;

        //Now lets generate all the possible row-column values
        for (int row_index = 1; row_index <= (this.game_size * this.game_size); row_index++)
        {
            //Lets generate the possible grid-column values
            for (int column_index = 1; column_index <= (this.game_size * this.game_size); column_index++)
            {
                for (int index = 0; index < (this.game_size * this.game_size); index++)
                {
                    available_values[row_index][column_index][index] = index + 1;
                }
            }
        }

        //After generating the possible row-column values, lets start populating our grid
        for (int row_index = 1; row_index <= (this.game_size * this.game_size); row_index++)
        {
            for (int column_index = 1; column_index <= (this.game_size * this.game_size); column_index++)
            {
                //This is just to have the possibility to escape the harsh reality...
                int recursive_iteration_counter = 0;
                //Lets find a possible value...
                int choosen_random_number = chooseNumberforField(
                    row_index,
                    column_index,
                    available_values,
                    ++recursive_iteration_counter
                );
                
                //Set our field to the choosen number
                this.game_puzzle_solution[row_index][column_index] = choosen_random_number;
                
                //Remove possible values from columns list after each fill
                for (int index = 1; index <= (this.game_size * this.game_size); index++)
                {
                    for (int values_index = 0; values_index < available_values[index][column_index].length; values_index++)
                    {
                        if (available_values[index][column_index][values_index] == choosen_random_number)
                        {
                            available_values[index][column_index][values_index] = 0;
                        }
                    }
                }
                //Remove possible values from rows after each fill
                for (int index = 1; index <= (this.game_size * this.game_size); index++)
                {
                    for (int values_index = 0; values_index < available_values[row_index][index].length; values_index++)
                    {
                        if (available_values[row_index][index][values_index] == choosen_random_number)
                        {
                            available_values[row_index][index][values_index] = 0;
                        }
                    }
                }
                //Remove possible values from cubes after each fill
                for (int sub_row_index = (this.game_size - 1); sub_row_index >= 0; sub_row_index--)
                {
                    for (int sub_column_index = (this.game_size - 1); sub_column_index >= 0; sub_column_index--)
                    {
                        int row_coordinates = (int)Math.ceil((double)row_index / (double)this.game_size) * this.game_size - sub_row_index;
                        int column_coordinates = (int)Math.ceil((double)column_index / (double)this.game_size) * this.game_size - sub_column_index;
                        for (int values_index = 0; values_index < available_values[row_coordinates][column_coordinates].length; values_index++)
                        {
                            if (available_values[row_coordinates][column_coordinates][values_index] == choosen_random_number)
                            {
                                available_values[row_coordinates][column_coordinates][values_index] = 0;
                            }
                        }
                        //System.out.println(Integer.toString(row_coordinates) + ";" + Integer.toString(column_coordinates));
                    }
                }
                
                //Welcome to my debug nightmare...
                System.out.println(
                    Integer.toString(row_index) + "/" +
                    Integer.toString(column_index) + "--" +
                    Arrays.toString(available_values[row_index][column_index])
                );
            }
        }
        
        //Lets check if our puzzle grid is actually valid...
        for (int row_index = 1; row_index <= (this.game_size * this.game_size); row_index++)
        {
            for (int column_index = 1; column_index <= (this.game_size * this.game_size); column_index++)
            {
                if (this.game_puzzle_solution[row_index][column_index] == 0)
                {
                    return false;
                }
            }
        }
        
        //Hurray!!! We brute forced a good solution...
        return true;
    }
    
    /**
     * Chooses a possible value for a given field square in the grid.
     * 
     * @param row_index The row index of the game board grid.
     * @param column_index The column index of the game board grid.
     * @param available_values The possible values of the current field square.
     * @param recursive_iteration_counter A helping parameter to escape the infinite loop.
     * @return int A randomly selected possible value. 0 means issues happened.
     */
    public int chooseNumberforField(
        int row_index,
        int column_index,
        int[][][] available_values,
        int recursive_iteration_counter
    )
    {
        //This is just to escape the harsh reality.. Worst case scenario
        if (recursive_iteration_counter > 40)
        {
            return 0;
        }
        
        Random random = new Random();
        
        //Lets collect the possible numbers...
        List<Integer> possible_values_list = new ArrayList<>();
        for (int index = 0; index < available_values[row_index][column_index].length; index++)
        {
            if (available_values[row_index][column_index][index] != 0)
            {
                possible_values_list.add(available_values[row_index][column_index][index]);
            }
        }
        
        //Escaping reality...
        if (possible_values_list.size() < 1)
        {
            return 0;
        }
        
        return possible_values_list.get(random.nextInt(possible_values_list.size()));
    }
}
