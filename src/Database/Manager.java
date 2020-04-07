package Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import quizdom.Category;
import quizdom.Quiz;
import quizdom.Score;

public class Manager {

    private static Database db;
    private ArrayList<Question> questions = new ArrayList<>();
    public static Quiz ManagerQuiz;
    private Question currentQ;
    public static char correctLetter;
    public static int score = 0;
    private int i = 0;
    private static String category = "Music";

    public Manager() throws SQLException {
        db = Database.getInstance();
    }

    public static void setCategory(String category) {
        Manager.category = category;
    }

    public static String getCategory() {
        return category;
    }

    public static int getRandom(int iMin, int iMax) {
        int iRand = 0;
        iRand = (int) Math.random() * (iMax - iMin) + iMin;
        return iRand;
    }

    public int getScore() {
        return score;
    }

    public static Database getDb() {
        return db;
    }

    public void addQuestion(String question, int questionID) throws SQLException {

        Question question1 = new Question(question, questionID);
        questions.add(question1);

    }

    public void populateQuestions() throws SQLException {

        if (Category.musicCount > 0) {
            category = "Music";
            Category.musicCount = 0;
        } else if (Category.moviesCount > 0) {
            category = "Movies";
            Category.moviesCount = 0;
        } else if (Category.sportCount > 0) {
            category = "Sports";
            Category.sportCount = 0;
        } else if (Category.historyCount > 0) {
            category = "History";
            Category.historyCount = 0;
        }

        String SQL = "SELECT QuestionID, Question FROM Questions WHERE Category = '" + category + "'";

        ResultSet result = db.query(SQL);
        while (result.next()) {
            int questionID = result.getInt("QuestionID");
            String question = result.getString("Question");
            addQuestion(question, questionID);
        }
        Collections.shuffle(questions);
    }

    public String getQuestion() {

        if (i < questions.size()) {
            currentQ = questions.get(i);
            i++;
        } else {
            ManagerQuiz.setVisible(false);
            Score score = new Score();
            score.setVisible(true);
        }
        return currentQ.toString();
    }

    public boolean answer(char answer) throws SQLException {
        if (currentQ.isCorrect(answer)) {
            score += 10;
            return true;
        } else {
            String letters = "ABCD";
            for (int i = 0; i < 4; i++) {
                if (currentQ.isCorrect(letters.charAt(i)) == true) {
                    Manager.correctLetter = letters.charAt(i);
                }
            }
            return false;
        }
    }
}
