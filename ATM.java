import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class BankAccount {
    private double balance;

    public BankAccount(double initialBalance) {
        balance = initialBalance;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        } else {
            JOptionPane.showMessageDialog(null, "Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
        } else {
            JOptionPane.showMessageDialog(null, "Insufficient funds or invalid withdrawal amount.");
        }
    }
}

class ATM extends JFrame implements ActionListener {
    private BankAccount account;
    private JTextField amountField;
    private JTextArea displayArea;

    public ATM(BankAccount account) {
        this.account = account;
        setTitle("ATM Interface");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        // Create components
        JLabel amountLabel = new JLabel("Amount:");
        amountField = new JTextField(10);
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton checkBalanceButton = new JButton("Check Balance");
        displayArea = new JTextArea(10, 20);
        displayArea.setEditable(false);

        // Add components to panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(depositButton);
        buttonPanel.add(withdrawButton);
        buttonPanel.add(checkBalanceButton);

        JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        inputPanel.add(amountLabel);
        inputPanel.add(amountField);

        // Add panels to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(new JScrollPane(displayArea), BorderLayout.SOUTH);

        // Add action listeners
        depositButton.addActionListener(this);
        withdrawButton.addActionListener(this);
        checkBalanceButton.addActionListener(this);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String action = e.getActionCommand();

        if (action.equals("Check Balance")) {
            displayArea.append("Current balance: " + account.getBalance() + "\n");
            return; // Exit the method without further processing
        }

        double amount;

        try {
            amount = Double.parseDouble(amountField.getText());
        } catch (NumberFormatException ex) {
            displayArea.append("Invalid amount.\n");
            return;
        }

        switch (action) {
            case "Deposit":
                account.deposit(amount);
                displayArea.append("Deposit successful. New balance: " + account.getBalance() + "\n");
                break;
            case "Withdraw":
                account.withdraw(amount);
                displayArea.append("Withdrawal successful. New balance: " + account.getBalance() + "\n");
                break;
        }
    }

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000.0); // Initial balance
        new ATM(account);
    }
}