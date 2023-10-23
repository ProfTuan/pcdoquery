/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery.util;

/**
 *
 * @author mac
 */
public class TestReporter {
    
    private static TestReporter INSTANCE = null;
    
    private TestReporter(){
        
    }
    
    public static TestReporter getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TestReporter();
        }
        
        return INSTANCE;
    }
    
}
