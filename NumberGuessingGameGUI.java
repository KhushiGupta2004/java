import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class NumberGuessingGameGUI extends JFrame {

    private JTextField guessField;
    private JLabel messageLabel;
    private JLabel scoreLabel;
    private int minRange = 1;
    private int maxRange = 100;
    private int maxAttempts = 5;
    private int rounds = 3;
    private int currentRound = 1;
    private int attemptsLeft;
    private int secretNumber;
    private int score = 0;

    public NumberGuessingGameGUI() {
        setTitle("Number Guessing Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
        setLayout(new BorderLayout());

        // Create components
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new FlowLayout());
        JLabel guessLabel = new JLabel("Enter your guess:");
        guessField = new JTextField(10);
        JButton guessButton = new JButton("Guess");
        inputPanel.add(guessLabel);
        inputPanel.add(guessField);
        inputPanel.add(guessButton);

        messageLabel = new JLabel("Round " + currentRound + ": Enter your guess.");
        scoreLabel = new JLabel("Score: 0");

        // Add components to the frame
        add(messageLabel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scoreLabel, BorderLayout.SOUTH);

        // Start a new round
        startRound();

        // Add action listener to the guess button
        guessButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int guess = Integer.parseInt(guessField.getText());
                    attemptsLeft--;

                    if (guess == secretNumber) {
                        messageLabel.setText("Congratulations! You guessed the number in " + (maxAttempts - attemptsLeft + 1) + " attempts.");
                        score += attemptsLeft + 1; 
                        scoreLabel.setText("Score: " + score);
                        guessField.setText("");

                        // Display popup message with score and quit option
                        int option = JOptionPane.showConfirmDialog(
                                null,
                                "Congratulations! You guessed the number!\nYour score for this round is: " + (attemptsLeft + 1) +
                                        "\nDo you want to play another round?",
                                "Round Won!",
                                JOptionPane.YES_NO_OPTION,
                                JOptionPane.INFORMATION_MESSAGE
                        );

                        if (option == JOptionPane.YES_OPTION) {
                            if (currentRound < rounds) {
                                currentRound++;
                                startRound();
                            } else {
                                // Display "Do you want to replay?" popup
                                int replayOption = JOptionPane.showConfirmDialog(
                                        null,
                                        "Game Over! Your final score is: " + score + "\nDo you want to replay?",
                                        "Game Over",
                                        JOptionPane.YES_NO_OPTION,
                                        JOptionPane.INFORMATION_MESSAGE
                                );

                                if (replayOption == JOptionPane.YES_OPTION) {
                                    // Reset game state for replay
                                    currentRound = 1;
                                    score = 0;
                                    scoreLabel.setText("Score: 0");
                                    startRound();
                                } else {
                                    System.exit(0); // Quit the game
                                }
                            }
                        } else {
                            System.exit(0); // Quit the game
                        }

                    } else if (guess < secretNumber) {
                        messageLabel.setText("Too low! Attempts left: " + attemptsLeft);
                    } else {
                        messageLabel.setText("Too high! Attempts left: " + attemptsLeft);
                    }

                    if (attemptsLeft == 0) {
                        messageLabel.setText("Sorry, you've reached the maximum number of attempts. The number was: " + secretNumber);
                        guessField.setText("");
                        if (currentRound < rounds) {
                            currentRound++;
                            startRound();
                        } else {
                            // Display "Do you want to replay?" popup
                            int replayOption = JOptionPane.showConfirmDialog(
                                    null,
                                    "Game Over! Your final score is: " + score + "\nDo you want to replay?",
                                    "Game Over",
                                    JOptionPane.YES_NO_OPTION,
                                    JOptionPane.INFORMATION_MESSAGE
                            );

                            if (replayOption == JOptionPane.YES_OPTION) {
                                // Reset game state for replay
                                currentRound = 1;
                                score = 0;
                                scoreLabel.setText("Score: 0");
                                startRound();
                            } else {
                                System.exit(0); // Quit the game
                            }
                        }
                    }

                } catch (NumberFormatException ex) {
                    messageLabel.setText("Invalid input. Please enter a number.");
                }
            }
        });

        setVisible(true);
    }

    private void startRound() {
        Random random = new Random();
        secretNumber = random.nextInt(maxRange - minRange + 1) + minRange;
        attemptsLeft = maxAttempts;
        messageLabel.setText("Round " + currentRound + ": Enter your guess.");
        guessField.setText("");
    }

    public static void main(String[] args) {
        new NumberGuessingGameGUI();
    }
}