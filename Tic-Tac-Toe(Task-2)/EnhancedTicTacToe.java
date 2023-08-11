import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class EnhancedTicTacToe implements ActionListener {
    private JFrame frame;
    private JPanel titlePanel, buttonPanel;
    private JLabel textField, scoreLabel;
    private JButton[] buttons;
    private String[] board;
    private boolean player1Turn;
    private int turnCount;
    private int player1Score, player2Score;
    private String player1Name, player2Name;
    private boolean playWithAI;

    public EnhancedTicTacToe() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 500);
        frame.getContentPane().setBackground(new Color(238, 238, 238));
        frame.setLayout(new BorderLayout());

        player1Name = "Player 1";
        player2Name = "Player 2";

        playWithAI = JOptionPane.showConfirmDialog(frame, "Play with AI?", "Game Mode",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;

        if (!playWithAI) {
            player1Name = JOptionPane.showInputDialog(frame, "Enter Player 1's Name:", player1Name);
            player2Name = JOptionPane.showInputDialog(frame, "Enter Player 2's Name:", player2Name);
        }

        textField = new JLabel(player1Name + "'s Turn");
        textField.setFont(new Font("Serif", Font.BOLD, 24));
        textField.setForeground(new Color(106, 57, 6));
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setOpaque(true);

        scoreLabel = new JLabel(player1Name + ": 0   " + player2Name + ": 0");
        scoreLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        scoreLabel.setForeground(new Color(64, 37, 10));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);

        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 400, 100);
        titlePanel.add(textField, BorderLayout.NORTH);
        titlePanel.add(scoreLabel, BorderLayout.CENTER);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));
        buttons = new JButton[9];
        board = new String[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Serif", Font.BOLD, 60));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(190, 190, 190));
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
            board[i] = "";
        }

        JButton restartButton = new JButton("Restart");
        restartButton.setFont(new Font("Serif", Font.BOLD, 18));
        restartButton.setFocusable(false);
        restartButton.addActionListener(e -> resetGame());

        JButton modeButton = new JButton("Change Mode");
        modeButton.setFont(new Font("Serif", Font.BOLD, 18));
        modeButton.setFocusable(false);
        modeButton.addActionListener(e -> changeGameMode());

        JPanel buttonPanelBottom = new JPanel(new GridLayout(1, 2));
        buttonPanelBottom.add(restartButton);
        buttonPanelBottom.add(modeButton);
        titlePanel.add(buttonPanelBottom, BorderLayout.SOUTH);

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.setVisible(true);

        player1Turn = true;
        turnCount = 0;
        player1Score = 0;
        player2Score = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int index = -1;
        for (int i = 0; i < buttons.length; i++) {
            if (buttons[i] == clickedButton) {
                index = i;
                break;
            }
        }

        if (index != -1 && board[index].equals("")) {
            board[index] = player1Turn ? "X" : "O";
            clickedButton.setText(player1Turn ? "X" : "O");
            clickedButton.setForeground(player1Turn ? new Color(255, 0, 0) : new Color(0, 0, 255));
            turnCount++;

            if (checkForWin()) {
                String winner = player1Turn ? player1Name : player2Name;
                JOptionPane.showMessageDialog(frame, winner + " wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                if (player1Turn) {
                    player1Score++;
                } else {
                    player2Score++;
                }
                updateScoreLabels();
                resetGame();
            } else if (turnCount == 9) {
                JOptionPane.showMessageDialog(frame, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            } else {
                player1Turn = !player1Turn;
                textField.setText(player1Turn ? player1Name + "'s Turn" : player2Name + "'s Turn");

                if (playWithAI && !player1Turn) {
                    performAIMove();
                }
            }
        }
    }

    private void performAIMove() {
        int bestScore = Integer.MIN_VALUE;
        int bestMove = -1;

        for (int i = 0; i < 9; i++) {
            if (board[i].equals("")) {
                board[i] = "O";
                int score = minimax(board, 0, false);
                board[i] = "";
                if (score > bestScore) {
                    bestScore = score;
                    bestMove = i;
                }
            }
        }

        if (bestMove != -1) {
            buttons[bestMove].doClick();
        }
    }

   private int minimax(String[] newBoard, int depth, boolean isMaximizing) {
        if (checkForWin()) { // Use the existing checkForWin method
            return isMaximizing ? -1 : 1; // Return -1 for "X" and 1 for "O"
        } else if (isBoardFull(newBoard)) {
            return 0;
        }
    
        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 9; i++) {
                if (newBoard[i].equals("")) {
                    newBoard[i] = "O";
                    int score = minimax(newBoard, depth + 1, false);
                    newBoard[i] = "";
                    bestScore = Math.max(bestScore, score);
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 9; i++) {
                if (newBoard[i].equals("")) {
                    newBoard[i] = "X";
                    int score = minimax(newBoard, depth + 1, true);
                    newBoard[i] = "";
                    bestScore = Math.min(bestScore, score);
                }
            }
            return bestScore;
        }
    }

    private boolean checkForWin() {
        String[] board = new String[9];
        for (int i = 0; i < 9; i++) {
            board[i] = buttons[i].getText();
        }

        // Define winning combinations
        int[][] winCombinations = {
                { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
                { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
                { 0, 4, 8 }, { 2, 4, 6 } // Diagonals
        };

        for (int[] combination : winCombinations) {
            if (board[combination[0]].equals(board[combination[1]]) &&
                    board[combination[0]].equals(board[combination[2]]) &&
                    !board[combination[0]].equals("")) {
                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull(String[] newBoard) {
        for (String cell : newBoard) {
            if (cell.equals("")) {
                return false; 
            }
        }
        return true;
    }

    private void updateScoreLabels() {
        scoreLabel.setText(player1Name + ": " + player1Score + "   " + player2Name + ": " + player2Score);
    }
    
    
    

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(new Color(150, 150, 150));
            board[i] = "";
        }
        player1Turn = true;
        turnCount = 0;
        textField.setText(player1Name + "'s Turn");
    }

    private void changeGameMode() {
        frame.dispose();
        new EnhancedTicTacToe();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EnhancedTicTacToe();
        });
    }
}
