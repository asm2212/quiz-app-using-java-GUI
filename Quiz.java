import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Quiz extends JFrame {

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private JButton submitButton;
    private ButtonGroup buttonGroup;
    private JTextArea feedbackArea;
    private JLabel timerLabel;

    private String[] questions = {"What is the capital of France?", "What is the largest mammal?", "Who painted the Mona Lisa?",
            "What does CPU stand for?","Which programming language is used for Android app development?",
            "What does HTML stand for?","What is the purpose of a firewall in computer networks?",
            "Which technology is used for wireless communication between devices?",
            "What is the purpose of an SSD (Solid State Drive)?","What does VPN stand for?"};
    private String[][] options = {{"Paris", "Rome", "Berlin"}, {"Elephant", "Blue whale", "Giraffe"}, {"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh"},
            {"Central Processing Unit",  "Computer Processing Unit","Central Power Unit"},{"java","python","c++"},
            {"Hyper Text Markup Language","Home Tool Markup Language","Hyperlinks and Text Markup Language"},
            {"To prevent unauthorized access","To speed up internet connection","To encrypt data transmissions"},
            {"Bluetooth","Ethernet","USB"},{"Store and retrieve data","Display images and videos","Provide internet access"},
            {"Virtual Private Network","Very Personal Network","Virtual Public Network"}};

    private int[] answers = {0, 1, 0,0,0,0,0,0,0,0};
    private int currentQuestion = 0;
    private int score = 0;
    private int totalQuestions = 10;
    private boolean quizCompleted = false;
    private Timer timer;
    private int secondsLeft = 5 * 60;

    public Quiz() {
        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.ORANGE);

        questionLabel = new JLabel();
        questionLabel.setForeground(Color.WHITE);
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1));
        optionButtons = new JRadioButton[3];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            optionButtons[i] = new JRadioButton();
            optionButtons[i].setForeground(Color.WHITE);
            optionButtons[i].setBackground(Color.ORANGE);
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        optionsPanel.setBackground(Color.ORANGE);
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(Color.ORANGE);
        add(submitButton, BorderLayout.SOUTH);

        feedbackArea = new JTextArea();
        feedbackArea.setForeground(Color.WHITE);
        feedbackArea.setBackground(Color.ORANGE);
        add(new JScrollPane(feedbackArea), BorderLayout.EAST);

        timerLabel = new JLabel();
        timerLabel.setForeground(Color.WHITE);
        timerLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        add(timerLabel, BorderLayout.NORTH);

        startTimer();
        displayQuestion();
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondsLeft--;
                if (secondsLeft <= 0) {
                    timer.stop();
                    JOptionPane.showMessageDialog(Quiz.this, "Time's up! Quiz completed.");
                    quizCompleted = true;
                    displayQuestion();
                }
                updateTimerLabel();
            }
        });
        timer.start();
    }

    private void updateTimerLabel() {
        int minutes = secondsLeft / 60;
        int seconds = secondsLeft % 60;
        String timeString = String.format("%02d:%02d", minutes, seconds);
        timerLabel.setText("Time left: " + timeString);
    }

    private void displayQuestion() {
        if (currentQuestion < totalQuestions && !quizCompleted) {
            questionLabel.setText(questions[currentQuestion]);
            for (int i = 0; i < 3; i++) {
                optionButtons[i].setText(options[currentQuestion][i]);
                optionButtons[i].setSelected(false);
            }
        } else {
            if (!quizCompleted) {
                quizCompleted = true;
                timer.stop();
                JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score + "/" + totalQuestions);
            }
            submitButton.setEnabled(false);
        }
    }

    private void checkAnswer() {
        if (!quizCompleted) {
            timer.stop(); // Stop the timer
    
            for (int i = 0; i < 3; i++) {
                if (optionButtons[i].isSelected()) {
                    if (i == answers[currentQuestion]) {
                        feedbackArea.append("Question " + (currentQuestion + 1) + ": Correct!\n");
                        score++;
                    } else {
                        feedbackArea.append("Question " + (currentQuestion + 1) + ": Incorrect!\n");
                    }
                    break;
                }
            }
    
            currentQuestion++;
    
            if (currentQuestion < totalQuestions) {
                // Reset timer and start again
                secondsLeft = 5 * 60;
                startTimer();
                displayQuestion();
            } else {
                // Quiz completed
                quizCompleted = true;
                timer.stop();
                JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score + "/" + totalQuestions);
                submitButton.setEnabled(false);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Quiz has been completed. You cannot submit more answers.");
        }
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Quiz quiz = new Quiz();
                quiz.getContentPane().setBackground(Color.ORANGE);
                quiz.setVisible(true);
            }
        });
    }
}