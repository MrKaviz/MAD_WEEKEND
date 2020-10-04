package com.example.quizapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Teachers.TeachQuizAdd;

public class QuizAddUnitTest {

    private TeachQuizAdd quizValidate;

    public QuizAddUnitTest() {}

    @Before
    public void setUp() throws
            Exception {

        quizValidate = new TeachQuizAdd();
    }

    @Test
    public void validate_quiz_add_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("","","","","",""));
    }

    @Test
    public void validate_quiz_add_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate(null,null,null,null,null,null));
    }

    @Test
    public void validate_quiz_add_empty_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("","","",null,null,null));
    }

    @Test
    public void validate_quiz_add_null_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("","",null,null,null,null));
    }

    @Test
    public void validate_quiz_add_invalid_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("","","","validOptionC","validOptionD","validAnswer"));
    }

    @Test
    public void validate_quiz_add_invalid_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate(null,null,null,"validOptionC","validOptionD","validAnswer"));
    }

    @Test
    public void validate_quiz_add_invalid_null_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate(null,null,"","","validOptionD","validAnswer"));
    }

    @Test
    public void validate_quiz_add_invalid_returnsFalse() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("@##$#%#","#@$%^$^$^","@##$#%#","@##$#%#","@##$#%#","@##$#%#"));

    }

    @Test
    public void validate_quiz_add_valid_returnsTrue() throws
            Exception {

        Assert.assertFalse(quizValidate.quistionAddValidate("validQuestion","validOptionA","validOptionB","validOptionC","validOptionD","validAnswer"));
    }

}
