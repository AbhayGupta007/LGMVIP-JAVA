import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class converter extends JFrame implements ActionListener {
    JComboBox<String> fromCountry, toCountry;
    JButton convert, reset, exit;
    JLabel fromUnit, toUnit;
    JTextField fromText, answer;

    String[] currencyUnits = {
            "Select Unit", "Indian Rupee (INR)", "US Dollar (USD)", "Canadian Dollar (CAD)", "Brazilian Real (BRL)",
            "Indonesian Rupiah (IDR)", "Philippine Peso (PHP)"
    };

    double[] conversionRates = {
            0.0, 95.21, 1.31, 1.71, 5.47, 19554.94, 71.17
    };

    converter() {
        setTitle("Creative Currency Converter");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(null);

        JLabel mainTitle = new JLabel("Currency Converter");
        mainTitle.setBounds(150, 30, 500, 60);
        mainTitle.setFont(new Font("Arial", Font.BOLD, 36));
        mainTitle.setForeground(new Color(30, 144, 255));
        add(mainTitle);

        JLabel fromLabel = new JLabel("From:");
        fromLabel.setBounds(70, 150, 100, 30);
        fromLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(fromLabel);

        fromCountry = new JComboBox<>(currencyUnits);
        fromCountry.setBounds(180, 150, 200, 30);
        fromCountry.setFont(new Font("Arial", Font.PLAIN, 14));
        add(fromCountry);

        JLabel toLabel = new JLabel("To:");
        toLabel.setBounds(70, 200, 100, 30);
        toLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(toLabel);

        toCountry = new JComboBox<>(currencyUnits);
        toCountry.setBounds(180, 200, 200, 30);
        toCountry.setFont(new Font("Arial", Font.PLAIN, 14));
        add(toCountry);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(70, 250, 100, 30);
        amountLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        add(amountLabel);

        fromText = new JTextField();
        fromText.setBounds(180, 250, 200, 30);
        fromText.setFont(new Font("Arial", Font.PLAIN, 14));
        add(fromText);

        convert = new JButton("Convert");
        convert.setBounds(120, 320, 120, 40);
        convert.setFont(new Font("Arial", Font.BOLD, 16));
        convert.setBackground(new Color(30, 144, 255));
        convert.setForeground(Color.WHITE);
        convert.addActionListener(this);
        add(convert);

        reset = new JButton("Reset");
        reset.setBounds(260, 320, 120, 40);
        reset.setFont(new Font("Arial", Font.BOLD, 16));
        reset.setBackground(new Color(255, 69, 0));
        reset.setForeground(Color.WHITE);
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                resetFields();
            }
        });
        add(reset);

        exit = new JButton("Exit");
        exit.setBounds(200, 390, 120, 40);
        exit.setFont(new Font("Arial", Font.BOLD, 16));
        exit.setBackground(new Color(255, 0, 0));
        exit.setForeground(Color.WHITE);
        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                System.exit(0);
            }
        });
        add(exit);

        fromUnit = new JLabel();
        fromUnit.setBounds(400, 150, 300, 30);
        fromUnit.setFont(new Font("Arial", Font.PLAIN, 14));
        add(fromUnit);

        toUnit = new JLabel();
        toUnit.setBounds(400, 200, 300, 30);
        toUnit.setFont(new Font("Arial", Font.PLAIN, 14));
        add(toUnit);

        JLabel answerLabel = new JLabel("Converted Amount:");
        answerLabel.setBounds(70, 440, 150, 30);
        answerLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        add(answerLabel);

        answer = new JTextField();
        answer.setBounds(220, 440, 200, 30);
        answer.setFont(new Font("Arial", Font.PLAIN, 14));
        answer.setEditable(false);
        add(answer);
    }

    private void resetFields() {
        fromCountry.setSelectedIndex(0);
        toCountry.setSelectedIndex(0);
        fromText.setText("");
        answer.setText("");
    }

    private double convertCurrency(double amount, int fromIndex, int toIndex) {
        double fromRate = conversionRates[fromIndex];
        double toRate = conversionRates[toIndex];
        return (amount / fromRate) * toRate;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convert) {
            int fromIndex = fromCountry.getSelectedIndex();
            int toIndex = toCountry.getSelectedIndex();
            String amountText = fromText.getText();

            if (fromIndex == 0 || toIndex == 0 || amountText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Invalid input! Please enter valid values.",
                        "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    double amount = Double.parseDouble(amountText);
                    double convertedAmount = convertCurrency(amount, fromIndex, toIndex);
                    answer.setText(String.format("%.2f", convertedAmount));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid input! Please enter a valid numeric amount.",
                            "Error: Invalid Input", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new converter().setVisible(true);
            }
        });
    }
}