import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private final JButton[][] buttons = new JButton[3][3];
    private char currentPlayer = 'X';
    private TicTacToe gameLogic;

    public TicTacToeFrame() {
        gameLogic = new TicTacToe();

        setTitle("Tic Tac Toe");
        setLayout(new BorderLayout());

        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        initializeBoard(boardPanel);

        add(boardPanel, BorderLayout.CENTER);

        // Quit Button
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(e -> System.exit(0));
        add(quitButton, BorderLayout.SOUTH);

        JButton declareTieButton = new JButton("Declare Tie");
        declareTieButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            promptPlayAgain();
        });
        add(declareTieButton, BorderLayout.NORTH);

        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initializeBoard(JPanel boardPanel) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                buttons[row][col].setFont(new Font("Arial", Font.PLAIN, 60));
                boardPanel.add(buttons[row][col]);

                final int r = row;
                final int c = col;
                buttons[row][col].addActionListener(e -> handleButtonClick(r, c));
            }
        }
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
    }

    private void resetBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setText("");
            }
        }
        gameLogic.reset();
    }
    private void handleButtonClick(int row, int col) {
        if (!buttons[row][col].getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Invalid move! Try again.");
            return;
        }

        buttons[row][col].setText(String.valueOf(currentPlayer));
        gameLogic.makeMove(row, col, currentPlayer);

        if (gameLogic.checkWin()) {
            JOptionPane.showMessageDialog(this, "Player " + currentPlayer + " wins!");
            promptPlayAgain();
        } else if (gameLogic.checkTie()) {
            JOptionPane.showMessageDialog(this, "It's a tie!");
            promptPlayAgain();
        } else {
            switchPlayer();
        }
    }

    private void promptPlayAgain() {
        int response = JOptionPane.showConfirmDialog(this, "Would you like to play again?", "Play Again", JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            resetBoard();
        } else {
            System.exit(0);
        }
    }
    public class TicTacToe {
        private char[][] board; // 3x3 board for Tic Tac Toe
        private int movesCount; // To track the number of moves made

        // Constructor: Initialize the board and reset the game
        public TicTacToe() {
            board = new char[3][3];
            reset(); // Initialize the board to empty at the start
        }

        // Make a move on the board
        public boolean makeMove(int row, int col, char player) {
            // Ensure the row and column are within bounds and the cell is empty
            if (row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ') {
                board[row][col] = player; // Mark the move for the player ('X' or 'O')
                movesCount++; // Increment the moves counter
                return true; // Successful move
            }
            return false; // Invalid move (spot is already taken)
        }

        // Check if the current player has won the game
        public boolean checkWin() {
            // Check rows for a win
            for (int row = 0; row < 3; row++) {
                if (board[row][0] == board[row][1] && board[row][1] == board[row][2] && board[row][0] != ' ') {
                    return true;
                }
            }

            // Check columns for a win
            for (int col = 0; col < 3; col++) {
                if (board[0][col] == board[1][col] && board[1][col] == board[2][col] && board[0][col] != ' ') {
                    return true;
                }
            }

            // Check diagonals for a win
            if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
                return true;
            }
            if (board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != ' ') {
                return true;
            }

            return false; // No win detected
        }

        // Check if the game is a tie (i.e., the board is full without a winner)
        public boolean checkTie() {
            // If all cells are filled and there is no winner, it's a tie
            return movesCount == 9 && !checkWin();
        }

        // Reset the board for a new game
        public void reset() {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    board[row][col] = ' '; // Set all cells to empty
                }
            }
            movesCount = 0; // Reset the move counter
        }

        // (Optional) Display the current board (for debugging or logging)
        public void printBoard() {
            for (int row = 0; row < 3; row++) {
                for (int col = 0; col < 3; col++) {
                    System.out.print(board[row][col] + " ");
                }
                System.out.println();
            }
        }
    }
}