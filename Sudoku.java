/*
 * To execute this package, please run the following commands in a Windows CMD:
 * cd C:\Users\Admin\Desktop\projects\THE\Magasszintu programozasi nyelvek\Sudoku\Sudoku\src\main\java\com\the\sudoku
 * java Sudoku.java
 * 
 * CAUTION! This software uses some (lol...) brute force methodology... use with caution.
 * 
 * Iteration over a list of list in search of an element: https://stackoverflow.com/questions/55365092/how-to-find-an-element-in-a-list-of-lists
 */
package com.the.sudoku;

import java.util.*; //Lets import EVERYTHING!

/**
 * @author Veres Zoltán
 */
public class Sudoku
{
    public final int puzzle_size = 3; //Define the generated puzzle size
    //Used +1 so I would not have to deal with index:0
    public int[][] grid = new int[puzzle_size * puzzle_size + 1][puzzle_size * puzzle_size + 1]; //Placeholder for the main sudoku puzzle grid
    
    public static void main(String[] args)
    {
        try
        {
            new Sudoku().runGame();
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
        }
    }
    
    public boolean runGame() throws Exception
    {
        for (int row_index = 1; row_index <= (puzzle_size * puzzle_size); row_index++)
        {   
            for (int column_index = 1; column_index <= (puzzle_size * puzzle_size); column_index++)
            {
                grid[row_index][column_index] = 0;
            }
        }
        
        Sudoku sudoku = new Sudoku();
        Board sudoku_board = new Board();
        
        //Let the Conquering of Rome begin!
        for (int bruteforce_iterations = 0; bruteforce_iterations < 2000; bruteforce_iterations++)
        {
            if(sudoku.generateGrid(puzzle_size, grid))
            {
                sudoku_board.displayBoard(puzzle_size, grid);
                
                return true;
            }
        }
        
        throw new Exception("Sorry, failed to generate a playable sudoku board...");
    }
    
    public boolean generateGrid(int puzzle_size, int[][] grid)
    {
        //Lets generate list-lists to hold all the possible values for row-column fields
        int[][][] available_values = new int[puzzle_size * puzzle_size + 1][puzzle_size * puzzle_size + 1][puzzle_size * puzzle_size];

        //Now lets generate the row-column possible values
        for (int row_index = 1; row_index <= (puzzle_size * puzzle_size); row_index++)
        {
            //Lets generate the possible grid-column values
            for (int column_index = 1; column_index <= (puzzle_size * puzzle_size); column_index++)
            {
                for (int index = 0; index < (puzzle_size * puzzle_size); index++)
                {
                    available_values[row_index][column_index][index] = index + 1;
                }
            }
        }

        //After generating the possible row-column values, lets start populating our grid
        for (int row_index = 1; row_index <= (puzzle_size * puzzle_size); row_index++)
        {
            for (int column_index = 1; column_index <= (puzzle_size * puzzle_size); column_index++)
            {
                //This is just to have the possibility to escape the harsh reality...
                int recursive_iteration = 0;
                //Lets find a possible value...
                int choosen_random_number = chooseNumberforField(
                    row_index,
                    column_index,
                    available_values,
                    ++recursive_iteration
                );
                
                //Set our field to the choosen number
                grid[row_index][column_index] = choosen_random_number;
                
                //Remove possible values from columns list after each fill
                for (int index = 1; index <= (puzzle_size * puzzle_size); index++)
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
                for (int index = 1; index <= (puzzle_size * puzzle_size); index++)
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
                for (int sub_row_index = 2; sub_row_index >= 0; sub_row_index--)
                {
                    for (int sub_column_index = 2; sub_column_index >= 0; sub_column_index--)
                    {
                        int row_coordinates = (int)Math.ceil((double)row_index / (double)puzzle_size) * puzzle_size - sub_row_index;
                        int column_coordinates = (int)Math.ceil((double)column_index / (double)puzzle_size) * puzzle_size - sub_column_index;
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
        for (int row_index = 1; row_index <= (puzzle_size * puzzle_size); row_index++)
        {
            for (int column_index = 1; column_index <= (puzzle_size * puzzle_size); column_index++)
            {
                if (grid[row_index][column_index] == 0)
                {
                    return false;
                }
            }
        }
        
        //Hurray!!! We brute forced a good solution...
        return true;
    }
    
    public int chooseNumberforField(
        int row_index,
        int column_index,
        int[][][] available_values,
        int recursive_iteration
    )
    {
        //This is just to escape the harsh reality.. Worst case scenario
        if (recursive_iteration > 40)
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