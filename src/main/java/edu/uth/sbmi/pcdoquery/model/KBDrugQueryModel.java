/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery.model;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mac
 */
public class KBDrugQueryModel {

    private String id_file = "";

    private String file_content = "";

    private String nl_question = "";
    
    private Set<String> keywords;

    private double rank_score = 0.00;

    public KBDrugQueryModel() {
        keywords = new HashSet<String>();
    }
    
    public Set<String> getKeywords(){
        return keywords;
    }
    
    public void setKeyword(String key_word){
        keywords.add(key_word);
    }

    public void resetScore() {
        this.rank_score = 0.00;
    }

    public double getRankScore() {
        return this.rank_score;
    }

    public void setRankScore(double score) {
        this.rank_score = score;
    }

    public void setSourceFileName(String id_filename) {

        this.id_file = id_filename;

    }

    public void setFileContent(String content) {
        this.file_content = content;

        int b = file_content.indexOf("#");
        int e = file_content.indexOf("\n");
        String question = file_content.substring(b, e);
        
        setQuestion(question);

    }

    public void setQuestion(String question) {
        this.nl_question = question;
    }

    public String getSourceFileName() {

        return id_file;
    }

    public String getQueryFileContent() {
        return this.file_content;
    }

    public String getNLQuestion() {
        return this.nl_question;
    }

}
