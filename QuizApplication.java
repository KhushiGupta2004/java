import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class QuizApplication {

    private JFrame frame;
    private JLabel questionLabel;
    private JRadioButton[] options;
    private ButtonGroup buttonGroup;
    private JButton nextButton;
    private JLabel timerLabel;
    private int currentQuestionIndex = 0;
    private int score = 0;
    private List<Question> questions;
    private int timeRemaining;
    private Timer timer;
    private List<Integer> incorrectAnswers = new ArrayList<>(); 

    public QuizApplication() {
        // Sample questions (replace with your own data)
        questions = new ArrayList<>();
        questions.add(new Question("What is the capital of France?", "Paris", "Berlin", "London", "Rome", 1));
        questions.add(new Question("Which planet is known as the Red Planet?", "Mars", "Jupiter", "Venus", "Saturn", 1));
        questions.add(new Question("Who painted the Mona Lisa?", "Leonardo da Vinci", "Michelangelo", "Raphael", "Donatello", 1));

        initializeGUI();
    }

    private void initializeGUI() {
        frame = new JFrame("Quiz Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create question label
        questionLabel = new JLabel();
        questionLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        frame.add(questionLabel, BorderLayout.NORTH);

        // Create option buttons
        options = new JRadioButton[4];
        buttonGroup = new ButtonGroup();
        JPanel optionPanel = new JPanel(new GridLayout(4, 1));
        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            buttonGroup.add(options[i]);
            optionPanel.add(options[i]);
        }
        frame.add(optionPanel, BorderLayout.CENTER);

        // Create next button
        nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
                nextQuestion();
            }
        });
        frame.add(nextButton, BorderLayout.SOUTH);

        // Create timer label
        timerLabel = new JLabel("Time Remaining: 30s");
        frame.add(timerLabel, BorderLayout.EAST);

        // Start the quiz
        startQuiz();

        frame.pack();
        frame.setVisible(true);
    }

    private void startQuiz() {
        timeRemaining = 30; // Set initial time for each question
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeRemaining--;
                timerLabel.setText("Time Remaining: " + timeRemaining + "s");
                if (timeRemaining <= 0) {
                    timer.stop();
                    nextQuestion(); // Move to the next question if time runs out
                }
            }
        });
        timer.start();
        loadQuestion();
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question question = questions.get(currentQuestionIndex);
            questionLabel.setText(question.getQuestion());
            options[0].setText(question.getOption1());
            options[1].setText(question.getOption2());
            options[2].setText(question.getOption3());
            options[3].setText(question.getOption4());
            buttonGroup.clearSelection();
            timeRemaining = 30; // Reset timer for each question
            timer.start();
        } else {
            showResult();
        }
    }

    private void checkAnswer() {
        Question currentQuestion = questions.get(currentQuestionIndex);
        int selectedOption = -1;
        for (int i = 0; i < 4; i++) {
            if (options[i].isSelected()) {
                selectedOption = i + 1;
                break;
            }
        }
        if (selectedOption != currentQuestion.getCorrectAnswer()) {
            incorrectAnswers.add(currentQuestionIndex);
        } else {
            score++;
        }
    }

    private void nextQuestion() {
        timer.stop();
        currentQuestionIndex++;
        loadQuestion();
    }

    private void showResult() {
        String message = "Your Score: " + score + "/" + questions.size() + "\n\n";
        if (!incorrectAnswers.isEmpty()) {
            message += "Incorrect Answers:\n";
            for (int index : incorrectAnswers) {
                Question question = questions.get(index);
                message += "- Question " + (index + 1) + ": " + question.getQuestion() + "\n";
                message += "    Your Answer: " + getOptionText(index + 1) + "\n";
                message += "    Correct Answer: " + getOptionText(question.getCorrectAnswer()) + "\n\n";
            }
        } else {
            message += "Congratulations! You answered all questions correctly!";
        }
        JOptionPane.showMessageDialog(frame, message, "Quiz Result", JOptionPane.INFORMATION_MESSAGE);
        frame.dispose();
    }

    private String getOptionText(int optionIndex) {
        switch (optionIndex) {
            case 1:
                return options[0].getText();
            case 2:
                return options[1].getText();
            case 3:
                return options[2].getText();
            case 4:
                return options[3].getText();
            default:
                return "Invalid Option";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new QuizApplication();
        });
    }
}

class Question {
    private String question;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private int correctAnswer;

    public Question(String question, String option1, String option2, String option3, String option4, int correctAnswer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public String getOption4() {
        return option4;
    }

    public int getCorrectAnswer() {
        return correctAnswer;
    }
}