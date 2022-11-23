/*
 * Used this solution as a base to display the Sudoku board
 * https://stackoverflow.com/questions/10809514/using-the-coordinate-plane-in-the-jframe
 */
package com.the.sudoku;

import java.awt.Graphics;
import java.awt.Color;
import javax.swing.*;


/**
 * @author Veres Zoltán
 */
public class Board
{
    public void displayBoard(int puzzle_size, int[][] grid)
    {
        JFrame frame = new JFrame("Sudoku - Veres Zoltan - DZAE6I");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(30, 30, 600, 600);
        //Here comes the drawing magick...
        frame.getContentPane().add(new DrawGameBoard(puzzle_size, grid));
        frame.setVisible(true);
    }
}

class DrawGameBoard extends JComponent
{
    private final int field_size = 30;
    private final int self_puzzle_size;
    private final int[][] self_grid;

    //Constructor...
    DrawGameBoard(int puzzle_size, int[][] grid)
    {
        self_puzzle_size = puzzle_size;
        self_grid = grid;
    }
    
    @Override
    public void paint(Graphics graphics)
    {
        graphics.setColor(Color.BLACK);
                
        for (int row_index = 1; row_index <= (self_puzzle_size * self_puzzle_size); row_index++)
        {
            for (int column_index = 1; column_index <= (self_puzzle_size * self_puzzle_size); column_index++)
            {
                graphics.drawString (
                    Integer.toString(self_grid[row_index][column_index]) + " ",
                    field_size * (row_index),
                    field_size * (column_index)
                );
            }
        }
        
        //Lets draw the separator lines
        int top_left_x = field_size / 2;
        int top_left_y = field_size / 2;
        int line_length = field_size * self_puzzle_size * self_puzzle_size + field_size;
        
        graphics.drawLine(top_left_x, top_left_y, top_left_x, line_length); //left side
        graphics.drawLine(top_left_x, top_left_y, line_length, top_left_y); //top side
        graphics.drawLine(line_length, top_left_y, line_length, line_length); //right side
        graphics.drawLine(top_left_x, line_length, line_length, line_length); //bottom side
        
        for (int index = 1; index <= self_puzzle_size; index++)
        {
            graphics.drawLine(
                    (line_length / self_puzzle_size * index), 
                    top_left_y, 
                    (line_length / self_puzzle_size * index), 
                    line_length
            );
            graphics.drawLine(
                    top_left_y,
                    (line_length / self_puzzle_size * index),
                    line_length,
                    (line_length / self_puzzle_size * index)
            );
        }
    }
}