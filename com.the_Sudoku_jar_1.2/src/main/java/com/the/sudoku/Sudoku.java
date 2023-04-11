/*
 * 
 */
package com.the.sudoku;

/**
 * The main class which is responsible to:
 * <ul>
 * <li>Handle the program call arguments.</li>
 * <li>Check if the arguments value is within acceptable range.</li>
 * <li>Handle the Exception throws from within the program.</li>
 * <li>Act as an entry point fo the new game loop.</li>
 * </ul>
 * 
 * @author Veres Zoltán
 * @version 1.1
 */
public class Sudoku
{
    /**
    * Defines the generated puzzle size
    * Value must be between 2 and 5
    * TODO: Sadly, at the moment it takes minutes to generate a valid 4x4 game...
    */
    public int game_size = 3; //Argument 1
    
    /**
    * Defines the percentage chance that a field will be empty
    * This will set the difficulty of the puzzle
    * Value must be at least 1
    */
    public int puzzle_difficulty = 50; //Argument 2
    
    /**
    * Defines the window size width
    */
    public int game_frame_width = 600; //Argument 3
    
    /**
    * Defines the window size width
    */
    public int game_frame_height = 600; //Argument 4
    
    
    /**
     * The constructor of the class.
     * 
     * @param game_arguments The passed initial starting parameters of the program from the <strong>>main()</strong> function.
     * @throws Exception When any of the initial starting parameters are out of bound or cannot be interpreted.
     */
    Sudoku(String[] game_arguments) throws Exception
    {
        try
        {
            if (game_arguments.length > 0)
            {
                if (game_arguments[0] != null)
                {
                    this.game_size = Integer.parseInt(game_arguments[0]);
                    if (this.game_size < 2 || this.game_size > 5)
                    {
                        throw new Exception("The given game size argument is invalid! Must be between 2 and 5.");
                    }
                }
                if (game_arguments[1] != null)
                {
                    this.puzzle_difficulty = Integer.parseInt(game_arguments[1]);
                    if (this.puzzle_difficulty < 1 || this.puzzle_difficulty > this.game_size * this.game_size)
                    {
                        throw new Exception("The given missing number of puzzle squares is invalid...");
                    }
                }
                if (game_arguments[2] != null)
                {
                    this.game_frame_width = Integer.parseInt(game_arguments[2]);
                    if (this.game_frame_width < 1 || this.game_frame_width > 6000)
                    {
                        throw new Exception("The given game frame width argument is invalid! Must be between 1 and 6000.");
                    }
                }
                if (game_arguments[3] != null)
                {
                    this.game_frame_height = Integer.parseInt(game_arguments[3]);
                    if (this.game_frame_height < 1 || this.game_frame_height > 6000)
                    {
                        throw new Exception("The given game frame height argument is invalid! Must be between 1 and 6000.");
                    }
                }
            }
        }
        catch (NumberFormatException exception)
        {
            System.out.println("ERROR: Incorrectly given game argument value, must be an integer!");
            System.out.println(exception.toString());
        }
        catch (Exception exception)
        {
            System.out.println("ERROR: Error encountered!");
            System.out.println(exception.toString());
        }
    }
    
    /**
     * <h4>MAIN</h4>
     * 
     * @param game_arguments The initial starting parameters of the program.
     */
    public static void main(String[] game_arguments)
    {
        try
        {
            new Sudoku(game_arguments).run();
        }
        catch (Exception exception)
        {
            System.out.println(exception.toString());
        }
    }
    
    /**
     * Assembles the <strong>Board</strong> class and 
     * initializes the display of the game.
     */
    public void run()
    {
        new Board(
            this.game_size,
            this.puzzle_difficulty,
            this.game_frame_width,
            this.game_frame_height
        ).display();
    }
}