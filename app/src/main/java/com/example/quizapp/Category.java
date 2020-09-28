package com.example.quizapp;

public class Category {

    private String id;
    private String name;
    private String noOfSets;

    private String topicNo;


    public Category(String id, String name, String noOfSets, String topicNo) {
        this.id = id;
        this.name = name;
        this.noOfSets = noOfSets;
        this.topicNo=topicNo;
    }

    public Category(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNoOfSets() {
        return noOfSets;
    }

    public void setNoOfSets(String noOfSets) {
        this.noOfSets = noOfSets;
    }

    public String getTopicNo() {
        return topicNo;
    }

    public void setTopicNo(String topicNo) {
        this.topicNo = topicNo;
    }

}
