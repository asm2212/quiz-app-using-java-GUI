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

    // Quiz data
    private String[] questions = {"What is the capital of France?", "What is the largest mammal?", "Who painted the Mona Lisa?"};
    private String[][] options = {{"Paris", "Rome", "Berlin"}, {"Elephant", "Blue whale", "Giraffe"}, {"Leonardo da Vinci", "Pablo Picasso", "Vincent van Gogh"}};
    private int[] answers = {0, 1, 0}; // Index of correct option for each question
    private int currentQuestion = 0;
    private int score = 0;

    public Quiz() {
        setTitle("Quiz App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());

        questionLabel = new JLabel();
        add(questionLabel, BorderLayout.NORTH);

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(3, 1));
        optionButtons = new JRadioButton[3];
        buttonGroup = new ButtonGroup();
        for (int i = 0; i < 3; i++) {
            optionButtons[i] = new JRadioButton();
            optionsPanel.add(optionButtons[i]);
            buttonGroup.add(optionButtons[i]);
        }
        add(optionsPanel, BorderLayout.CENTER);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });
        add(submitButton, BorderLayout.SOUTH);

        feedbackArea = new JTextArea();
        add(new JScrollPane(feedbackArea), BorderLayout.EAST);

        displayQuestion();
    }

    private void displayQuestion() {
        questionLabel.setText(questions[currentQuestion]);
        for (int i = 0; i < 3; i++) {
            optionButtons[i].setText(options[currentQuestion][i]);
        }
    }

    private void checkAnswer() {
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
        if (currentQuestion < questions.length) {
            displayQuestion();
        } else {
            JOptionPane.showMessageDialog(this, "Quiz completed!\nYour score: " + score + "/" + questions.length);
            // You can add more actions here, like restarting the quiz
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Quiz().setVisible(true);
            }
        });
    }
}
