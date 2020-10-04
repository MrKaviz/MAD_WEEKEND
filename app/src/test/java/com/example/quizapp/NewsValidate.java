package com.example.quizapp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import Teachers.NewsAdd;

public class NewsValidate {
    private NewsAdd mValidator;

    public NewsValidate() {}

    @Before
    public void setUp() throws
            Exception {

        mValidator = new NewsAdd();
    }

    /*********************************
     * email validation methods
     *********************************/

    @Test
    public void validate_newsadd_empty_returnsFalse() throws
            Exception {

        Assert.assertFalse(mValidator.hasValidationErrors("",""));
    }

    @Test
    public void validate_newsadd_null_returnsFalse() throws
            Exception {

        Assert.assertFalse(mValidator.hasValidationErrors(null,null));
    }

    @Test
    public void validate_newsadd_invalid_returnsTrue() throws
            Exception {

        Assert.assertFalse(mValidator.hasValidationErrors("@##$#%#","#@$%^$^$^"));
    }

    @Test
    public void validate_newsadd_valid_returnsTrue() throws
            Exception {

        Assert.assertFalse(mValidator.hasValidationErrors("invalidName","InvalidBrand"));
    }
}
