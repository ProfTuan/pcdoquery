/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery.ontology;

import org.apache.jena.ontology.OntModel;
import static org.apache.jena.ontology.OntModelSpec.OWL_MEM;
import org.apache.jena.rdf.model.ModelFactory;

/**
 *
 * @author tuan
 */
public class PCDOHandler {
    
    //https://raw.githubusercontent.com/UTHealth-Ontology/PcDO/main/pcdo.owl
    
    private String StringURL = "";
    private OntModel ontologyModel = null;
    
    private static PCDOHandler INSTANCE = null;
    
    public static PCDOHandler getInstance(){
        if(INSTANCE == null){
            INSTANCE = new PCDOHandler();
        }
        
        return INSTANCE;
    }
    
    private PCDOHandler(){
        
    }
    
    public void setOntologyURL(String url){
        this.StringURL = url;
        
        ontologyModel = ModelFactory.createOntologyModel(OWL_MEM);
        ontologyModel.read(url, "RDF/XML");
        
        System.out.println("Size: "+ ontologyModel.size());
    }

    public OntModel getOntologyModel() {
        return ontologyModel;
    }
    
    
    
    public PCDOHandler(String url){
        this.StringURL = url;
        
        ontologyModel = ModelFactory.createOntologyModel(OWL_MEM);
        ontologyModel.read(url, "RDF/XML");
        
        System.out.println("Size: "+ ontologyModel.size());
    }
    
    
    public static void main(String[] args) {
        PCDOHandler o = new PCDOHandler("https://raw.githubusercontent.com/UTHealth-Ontology/PcDO/main/pcdo.owl");
        
    }
    
}
