package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Question {

    private String question;
    private int questionID;
    private ArrayList<Answer> answers = new ArrayList<>();
    private int numAnswers;

    public Question(String question, int questionID) throws SQLException {
        this.question = question;
        this.questionID = questionID;
        for (int i = 1; i <= 4; i++) {
            String a = "Choice_" + i;
            String SQL = "SELECT " + a + " ,Correct FROM Answers WHERE AnswerID=" + this.questionID;
            ResultSet result = Manager.getDb().query(SQL);

            while (result.next()) {
                String answer = result.getString("Choice_" + i);
                choice answer1 = new choice(a, false);
                String Correct = result.getString("Correct");

                if (answer1.choiceNum.equals(Correct)) {
                    answer1.correct = true;
                }
                boolean correct = answer1.correct;
                Answer answer2 = new Answer(answer, correct);
                answers.add(answer2);
            }
        }
    }

    private class choice {

        private String choiceNum;
        private boolean correct;

        private choice(String choiceNum, boolean correct) {
            this.choiceNum = choiceNum;
            this.correct = correct;
        }
    }

    @Override
    public String toString() {
        String display = "";
        String letters = "ABCD";
        for (int i = 0; i < answers.size(); i++) {
            display += answers.get(i).toString(letters.charAt(i)) + "\n";
        }
        return question + "\n\n" + display;
    }

    public boolean isCorrect(char letter) {
        String letters = "ABCD";
        int pos = letters.indexOf(letter + "");
        return answers.get(pos).isCorrect();
    }
}
