package com.cpen321.quizzical.Utils;

import com.cpen321.quizzical.Data.CourseCategory;
import com.cpen321.quizzical.Data.Questions.IQuestion;
import com.cpen321.quizzical.Data.Questions.QuestionsMC;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class QuestionPackage {

    private static Random rng = new Random();
    /**
     * This class provides an interface for questions and quizzes
     * When an instructor creates a quiz, addQuestions will be called once a question is created
     * When a student/instructor do a quiz, GetQuestions will be called
     */

    List<IQuestion> questionList;

    public QuestionPackage() {
        questionList = new ArrayList<IQuestion>();
    }

    public List<IQuestion> getQuestionList() {
        return this.questionList;
    }

    public List<IQuestion> getQuestionsByCategory(CourseCategory category, int questionNumber) {
        Collections.shuffle(this.questionList, rng);
        List<IQuestion> questions = new ArrayList<>();
        for (IQuestion q : questionList) {
            if (q.getCategory().equals(category))
                questions.add(q);
            if (questions.size() >= questionNumber)
                break;
        }

        return questions;
    }

    public void AddMCQuestion(CourseCategory category, String question,
                              boolean hasPic, String picSrc,
                              List<ChoicePair> choices, int correctAnsNum) throws Exception {
        if (correctAnsNum < 0 || correctAnsNum >= choices.size())
            throw new Exception("correct answer does not match any of the answers");
        QuestionsMC q = new QuestionsMC(category, question, hasPic, picSrc, choices, correctAnsNum);
        questionList.add(q);
    }


}