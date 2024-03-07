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

    private String[] questions = {"What is the capital of France?", "What is the largest mammal?", "Who painted the Mona Lisa?"};
    private String[][] options = {{"Paris", "Rome", "Berlin"}, {"Elephant", "Blue whale", "Giraffe"}, {"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh"}};
    private int[] answers = {0, 1, 0};
    private int currentQuestion = 0;
    private int score = 0;
    private int totalQuestions = 3; // Total number of questions
    private boolean quizCompleted = false;

    public Quiz() {
        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.BLACK);

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
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        optionsPanel.setBackground(Color.BLACK);
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        submitButton.setForeground(Color.WHITE);
        submitButton.setBackground(Color.BLACK);
        add(submitButton, BorderLayout.SOUTH);

        feedbackArea = new JTextArea();
        feedbackArea.setForeground(Color.WHITE);
        feedbackArea.setBackground(Color.BLACK);
        add(new JScrollPane(feedbackArea), BorderLayout.EAST);

        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestion < totalQuestions) {
            questionLabel.setText(questions[currentQuestion]);
            for (int i = 0; i < 3; i++) {
                optionButtons[i].setText(options[currentQuestion][i]);
                optionButtons[i].setSelected(false);
            }
        } else {
            quizCompleted = true;
            JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score + "/" + totalQuestions);
        }
    }

    private void checkAnswer() {
        if (!quizCompleted) {
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
            displayQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "Quiz has been completed. You cannot submit more answers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Quiz quiz = new Quiz();
                quiz.getContentPane().setBackground(Color.BLACK);
                quiz.setVisible(true);
            }
        });
    }
}
