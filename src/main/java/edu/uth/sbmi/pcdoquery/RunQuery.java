/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery;

import edu.uth.sbmi.pcdoquery.model.KBDrugQueryModel;
import edu.uth.sbmi.pcdoquery.model.ResponseModel;
import edu.uth.sbmi.pcdoquery.ontology.PCDOHandler;
import edu.uth.sbmi.pcdoquery.query.QueryStore;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 *
 * @author mac
 */
public class RunQuery {
    
    public static RunQuery INSTANCE = null;
    
    private PCDOHandler pcdo_handler;
        
    private QueryStore query_store;
    
    private Map<Object, Object> uri_drug_resources;
    
    private OntModel pcdo_ontology;
    
    private RunQuery(){
        pcdo_handler = PCDOHandler.getInstance();
        
        query_store = QueryStore.getInstance();
        
        uri_drug_resources = new HashMap<Object, Object>();
        
        getURIResources();
        
    }
    
    private void getURIResources(){
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("uri_resources.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            
            properties.forEach((key,value)->{
                
                uri_drug_resources.put(key, value);
                
            });
            
        } catch (IOException ex) {
            Logger.getLogger(RunQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(uri_drug_resources.size());
    }
    
    public static RunQuery getInstance()
    {
        if(INSTANCE == null){
            INSTANCE = new RunQuery();
        }
        
        return INSTANCE;
    }
    
    public ResponseModel queryGetResource(KBDrugQueryModel kbm, String drug_resource)
    {
        setTargetOntology(drug_resource);
        
        ResponseModel response = new ResponseModel();
        
        QueryExecution query_execution =QueryExecutionFactory.create(kbm.getQueryFileContent(), this.pcdo_ontology);
        
        ResultSet result_set = query_execution.execSelect();
        
        while(result_set.hasNext())
        {
            QuerySolution solution = result_set.nextSolution();
            Iterator<String> varNames = solution.varNames();
            while(varNames.hasNext())
            {
                String variable = varNames.next();
                String answer = solution.getResource(variable).getLocalName();
                
                //insert answer
                response.addAnswer(answer);
            }
            //compile new statement
            response.compileAnswers();
        }
        
        //return response
        response.printData();
        
        return response;
    }
    
    
    public void setTargetOntology(String urlOntology)
    {
        
        pcdo_handler.setOntologyURL(urlOntology);
        
        pcdo_ontology = pcdo_handler.getOntologyModel();
    }
    
    public static void main(String[] args) {
        
        RunQuery rq = RunQuery.getInstance();
        //set ontology resource
        
        //rq.setTargetOntology("https://raw.githubusercontent.com/UTHealth-Ontology/PcDO/main/instance/metformin.owl");
        
        //query ontology resource (Selection)
        QueryStore qs = QueryStore.getInstance();
        Map<Integer, KBDrugQueryModel> queries = qs.addQueriesToStorage();
        
        Object[] query_array = queries.keySet().toArray();
        int idx = (int)query_array[0];
        
        
        rq.queryGetResource(queries.get(idx), "https://raw.githubusercontent.com/UTHealth-Ontology/PcDO/main/instance/metformin.owl");
    }
}
