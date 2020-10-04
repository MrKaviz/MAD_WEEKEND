package Models;

public class Questions {
    String qustID;
    String question;
    String answerA;
    String answerB;
    String answerC;
    String answerD;
    int corectAns;

    public Questions() {

    }

    public Questions(String qustID, String question, String answerA, String answerB, String answerC, String answerD, int corectAns) {
        this.qustID = qustID;
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.corectAns = corectAns;
    }

    public Questions(String question, String answerA, String answerB, String answerC, String answerD, int corectAns) {
        this.question = question;
        this.answerA = answerA;
        this.answerB = answerB;
        this.answerC = answerC;
        this.answerD = answerD;
        this.corectAns = corectAns;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswerA() {
        return answerA;
    }

    public void setAnswerA(String answerA) {
        this.answerA = answerA;
    }

    public String getAnswerB() {
        return answerB;
    }

    public void setAnswerB(String answerB) {
        this.answerB = answerB;
    }

    public String getAnswerC() {
        return answerC;
    }

    public void setAnswerC(String answerC) {
        this.answerC = answerC;
    }

    public String getAnswerD() {
        return answerD;
    }

    public void setAnswerD(String answerD) {
        this.answerD = answerD;
    }

    public int getCorectAns() {
        return corectAns;
    }

    public void setCorectAns(int corectAns) {
        this.corectAns = corectAns;
    }

    public String getQustID() {
        return qustID;
    }

    public void setQustID(String qustID) {
        this.qustID = qustID;
    }


}
