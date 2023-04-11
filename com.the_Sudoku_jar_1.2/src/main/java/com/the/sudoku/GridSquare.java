/*
 * 
 */
package com.the.sudoku;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * This class is responsible to hold the information together about each and
 * every individual square on the game-board.
 * 
 * @author Veres Zoltán
 */
public class GridSquare
{
    /**
     * The grid managing class.
     */
    Grid grid;
    
    /**
     * The size of the game. Eg: 3 in input means will generate a 9x9 board.
     */
    int game_size;
    
    /**
     * A percentage chance that a random square will be empty.
     */
    int puzzle_difficulty;
    
    /**
     * The row index of the game board grid.
     */
    int row_index;
    
    /**
     * The column index of the game board grid.
     */
    int column_index;
    
    /**
     * The possible values of the current field square.
     */
    String[] selectable_values;
    
    /**
     * {@link javax.swing.JTextField}
     */
    JTextField selectable_values_box;
    
    /**
     * {@link com.the.sudoku.PuzzleGenerator}
     */
    PuzzleGenerator puzzle_generator;
    
    /**
     * The constructor of the class.
     * 
     * @param grid The grid managing object.
     * @param game_size The size of the game. Eg: 3 in input means will generate a 9x9 board.
     * @param puzzle_difficulty A percentage chance that a random square will be empty.
     * @param row_index The row index of the game board grid.
     * @param column_index The column index of the game board grid.
     * @param puzzle_generator {@link com.the.sudoku.PuzzleGenerator}
     */
    public GridSquare(
        Grid grid,
        int game_size,
        int puzzle_difficulty,
        int row_index,
        int column_index,
        PuzzleGenerator puzzle_generator
    ) {
        this.grid = grid;
        this.game_size = game_size;
        this.puzzle_difficulty = puzzle_difficulty;
        this.row_index = row_index;
        this.column_index = column_index;
        this.selectable_values = new String[(this.game_size * this.game_size)];
        this.puzzle_generator = puzzle_generator;
                
        for(int i = 0; i < (this.game_size * this.game_size); i++)
        {
            this.selectable_values[i] = Integer.toString(i + 1);
        }
        
        this.selectable_values_box = new JTextField(
            Integer.toString(
                this.puzzle_generator.game_puzzle_solution
                    [this.row_index]
                    [this.column_index]
            ),
            2
        );
        
        this.selectable_values_box.setBorder(
            javax.swing.BorderFactory.createLineBorder(Color.GRAY)
        );
        this.selectable_values_box.setSize(60, 60);
        this.selectable_values_box.setMinimumSize(new Dimension(60, 60));   
        this.selectable_values_box.setFont(new Font("Ariel", Font.BOLD, 30));
        this.selectable_values_box.setHorizontalAlignment(JTextField.CENTER);
        this.selectable_values_box.setBackground(Color.LIGHT_GRAY);
        this.selectable_values_box.setDisabledTextColor(Color.BLACK);
        
        //This is where we empty out the square to have a random puzzle...
        if (Math.floor(Math.random() * 100) > this.puzzle_difficulty)
        {
            this.selectable_values_box.setEnabled(false);
        }
        else
        {
            this.selectable_values_box.setText("");
        }
        
        this.selectable_values_box.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                fieldChanged();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                fieldChanged();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                fieldChanged();
            }

            public void fieldChanged() {
                try {
                    //TODO: What to do when the user enters an incorrect value?
                    /*if (Integer.parseInt(selectable_values_box.getText()) <= 0) {
                        selectable_values_box.setText("");
                    }*/
                    if (
                        Integer.parseInt(selectable_values_box.getText()) != 
                        puzzle_generator.game_puzzle_solution[row_index][column_index]
                    ) {
                        findOtherGridSqaresAndWarnThem(
                            row_index,
                            column_index,
                            Color.RED
                        );
                    }
                    else {
                        findOtherGridSqaresAndWarnThem(
                            row_index,
                            column_index,
                            Color.LIGHT_GRAY
                        );
                    }
                }
                catch (NumberFormatException e) {
                    //TODO: What to do when the user enters an incorrect value?
                }
                catch (Exception e) {
                    //TODO: What to do when the user enters an incorrect value?
                }
            }
        });
    }
    
    /**
     * A function to iterate through every square on the grid and
     *  highlight them in a way...
     * 
     * @param row_index The index of the row on to which check the other squares.
     * @param column_index The index of the column on to which check the other squares.
     * @param color The color to apply on the affected squares.
     */
    public void findOtherGridSqaresAndWarnThem(
        int row_index,
        int column_index,
        Color color
    )
    {
        //Highlight each sqare with the same row index and column index
        for (int i = 1; i <= (this.game_size * this.game_size); i++)
        {
            this.grid.grid_squares[row_index][i].selectable_values_box.setBackground(color);
            this.grid.grid_squares[i][column_index].selectable_values_box.setBackground(color);
        }
        
        //Highligh each sqare inside the same subsquare
        for (int i = 0; i < this.game_size; i++)
        {
            for (int j = 0; j < this.game_size; j++)
            {
                this.grid.grid_squares
                    [(int)Math.ceil((double)row_index / (double)this.game_size) * this.game_size - (this.game_size - 1) + i]
                    [(int)Math.ceil((double)column_index / (double)this.game_size) * this.game_size - (this.game_size - 1) + j]
                    .selectable_values_box.setBackground(color);
            }
        }
    };
}
