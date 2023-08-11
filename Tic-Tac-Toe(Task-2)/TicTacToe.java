import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe implements ActionListener {
    private JFrame frame;
    private JPanel titlePanel, buttonPanel;
    private JLabel textField;
    private JButton[] buttons;
    private boolean player1Turn;
    private int turnCount;

    public TicTacToe() {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLayout(new BorderLayout());

        textField = new JLabel("Tic Tac Toe");
        textField.setFont(new Font("Arial", Font.BOLD, 24));
        textField.setForeground(Color.PINK);
        textField.setHorizontalAlignment(JLabel.CENTER);
        textField.setOpaque(true);

        titlePanel = new JPanel();
        titlePanel.setLayout(new BorderLayout());
        titlePanel.setBounds(0, 0, 400, 100);
        titlePanel.add(textField);

        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3));

        buttons = new JButton[9];
        for (int i = 0; i < 9; i++) {
            buttons[i] = new JButton();
            buttons[i].setFont(new Font("Arial", Font.BOLD, 60));
            buttons[i].setFocusable(false);
            buttons[i].setBackground(new Color(150, 150, 150));
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.setVisible(true);

        player1Turn = true;
        turnCount = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        if (clickedButton.getText().equals("")) {
            if (player1Turn) {
                clickedButton.setText("X");
                clickedButton.setForeground(new Color(255, 0, 0));
                textField.setText("O's Turn");
            } else {
                clickedButton.setText("O");
                clickedButton.setForeground(new Color(0, 0, 255));
                textField.setText("X's Turn");
            }
            turnCount++;
            if (checkForWin()) {
                if (player1Turn) {
                    JOptionPane.showMessageDialog(frame, "X wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "O wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                }
                resetGame();
            } else if (turnCount == 9) {
                JOptionPane.showMessageDialog(frame, "It's a tie!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                resetGame();
            } else {
                player1Turn = !player1Turn;
            }
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
                highlightWinningButtons(combination);
                return true;
            }
        }

        return false;
    }

    private void highlightWinningButtons(int[] combination) {
        for (int index : combination) {
            buttons[index].setBackground(Color.GREEN);
        }
    }

    private void resetGame() {
        for (int i = 0; i < 9; i++) {
            buttons[i].setText("");
            buttons[i].setBackground(new Color(150, 150, 150));
        }
        player1Turn = true;
        turnCount = 0;
        textField.setText("Tic Tac Toe");
    }

    public static void main(String[] args) {
        new TicTacToe();
    }
}
