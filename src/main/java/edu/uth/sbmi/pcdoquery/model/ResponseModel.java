/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mac
 */
public class ResponseModel {
    
    private ArrayList<String> variable_answers;
    
    private Set<ArrayList<String>> statements;
    
    public ResponseModel(){
        variable_answers = new ArrayList<String>();
        statements = new HashSet<ArrayList<String>>();
    }
    
    public void addAnswer(String answer){
        
        variable_answers.add(answer);
    }
    
    public void compileAnswers(){
        
        statements.add(variable_answers);
        variable_answers = new ArrayList<String>();
    }
    
    public static void main(String[] args) {
        
    }
    
    public void printData(){
        statements.forEach(vs->{
        
            
            StringBuilder sb = new StringBuilder();
            vs.forEach(a->{
                sb.append(a+ " ");
            });
            System.out.println(sb.toString());
        
        });
        
    }
    
}
