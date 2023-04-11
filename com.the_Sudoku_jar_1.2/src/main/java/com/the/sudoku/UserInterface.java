/*
 * 
 */
package com.the.sudoku;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JTextField; //TODO: Maybe later I refact to a textfield input to avert the dropdown border issues.

/**
 * This class is responsible to display the game for the customer.
 * 
 * @author Veres Zoltán
 */
public class UserInterface extends JFrame
{
    /**
     * The size of the game. Eg: 3 in input means will generate a 9x9 board.
     */
    int game_size;
    /**
     * A percentage chance that a random square will be empty.
     */
    int puzzle_difficulty;
    /**
     * The window width size in pixels.
     */
    int game_frame_width;
    /**
     * The window height size in pixels.
     */
    int game_frame_height;
    /**
     * {@link com.the.sudoku.Grid}
     */
    Grid grid;
    
    /**
     * The constructor of the class.
     * 
     * @param game_size The size of the game. Eg: 3 in input means will generate a 9x9 board.
     * @param puzzle_difficulty A percentage chance that a random square will be empty.
     * @param game_frame_width The window width size in pixels.
     * @param game_frame_height The window height size in pixels.
     * @param grid {@link com.the.sudoku.Grid}
     */
    UserInterface(
        int game_size,
        int puzzle_difficulty,
        int game_frame_width,
        int game_frame_height,
        Grid grid
    ) {
        this.game_size = game_size;
        this.puzzle_difficulty = puzzle_difficulty;
        this.game_frame_width = game_frame_width;
        this.game_frame_height = game_frame_height;
        this.grid = grid;
    }
    
    /**
     * Display the user interface for the game-play.
     */
    public void display()
    {
        JFrame main_frame = new JFrame("Sudoku - Veres Zoltán - DZAE6I");
        main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel main_panel = new JPanel();
        main_panel.setBackground(Color.LIGHT_GRAY);
        //main_panel.setLayout(new FlowLayout(FlowLayout.LEFT));
        main_panel.setLayout(new BoxLayout(main_panel, BoxLayout.Y_AXIS));        
        
        
        JPanel game_panel = new JPanel();
        game_panel.setBackground(Color.GRAY);
        game_panel.setLayout(new GridLayout((this.game_size), (this.game_size), 3, 3));
        for (int sub_row_index = 1; sub_row_index <= this.game_size; sub_row_index++)
        {
            for (int sub_column_index = 1; sub_column_index <= this.game_size; sub_column_index++)
            {
                JPanel game_sub_panel = new JPanel();
                game_sub_panel.setLayout(new GridLayout((this.game_size), (this.game_size), 0, 0));
                for (int row_index = 1; row_index <= this.game_size; row_index++)
                {
                    for (int column_index = 1; column_index <= this.game_size; column_index++)
                    {
                        game_sub_panel.add(
                            this.grid.grid_squares
                                [row_index + ((sub_row_index - 1) * this.game_size)]
                                [column_index + ((sub_column_index - 1) * this.game_size)]
                                .selectable_values_box
                            /*new JTextField(
                                Integer.toString(
                                    row_index + ((sub_row_index - 1) * this.game_size)
                                ) + "," +
                                Integer.toString(
                                    column_index + ((sub_column_index - 1) * this.game_size)
                                )
                            )*/
                        );
                    }
                }
                game_panel.add(game_sub_panel);
            }
        }
        
        
        JPanel control_panel = new JPanel();
        //New game button
        JButton new_game_button = new JButton("Next game");
        new_game_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                //Setup a new game and dispose of the previous JFrame...
                Sudoku.main(
                    new String[]{
                        Integer.toString(game_size),
                        Integer.toString(++puzzle_difficulty),
                        Integer.toString(game_frame_width),
                        Integer.toString(game_frame_height)
                    }
                );
                
                main_frame.dispose();
            }
        });
        control_panel.add(new_game_button);
        control_panel.add(new JLabel("Difficulty: " + Integer.toString(this.puzzle_difficulty) + "%"));
        
        
        main_panel.add(game_panel);
        main_panel.add(control_panel);
        main_frame.add(main_panel);
        main_frame.setSize(
            this.game_frame_width, 
            this.game_frame_height
        );
        main_frame.pack();
        main_frame.setVisible(true);
        
        System.out.println(Integer.toString(this.puzzle_difficulty));
    }
}
