package com.example.quizapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ReviewUnitTest {

    private TeacherReview reviewValidator;

    public ReviewUnitTest() {}

    @Before
    public void setUp() throws
            Exception {

        reviewValidator = new TeacherReview();
    }

    /*********************************
     * email validation methods
     *********************************/

    @Test
    public void validate_teachReview_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("",""));
    }

    @Test
    public void validate_teachReview_empty_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("",null));
    }

    @Test
    public void validate_teachReview_null_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate(null,""));
    }

    @Test
    public void validate_teachReview_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate(null,null));
    }

    @Test
    public void validate_teachReview_valid_empty_returnsTrue() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("validInput",""));
    }

    @Test
    public void validate_teachReview_valid_null_returnsTrue() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("validInput",null));
    }

    @Test
    public void validate_teachReview_empty_valid_returnsTrue() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("","validInput"));
    }

    @Test
    public void validate_teachReview_null_valid_returnsTrue() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate(null,"validInput"));
    }

    @Test
    public void validate_teachReview_valid_returnsTrue() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("@##$#%#","#@$%^$^$^"));
    }

    @Test
    public void validate_teachReview_valid_returnsTrue2() throws
            Exception {

        Assert.assertFalse(reviewValidator.reviewValidate("validTopic","validMessage"));
    }
}
