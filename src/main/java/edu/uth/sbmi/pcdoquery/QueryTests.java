package edu.uth.sbmi.pcdoquery;

import edu.uth.sbmi.pcdoquery.ontology.PCDOHandler;
import edu.uth.sbmi.pcdoquery.query.QueryStore;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;

/**
 * 
 *
 */
public class QueryTests 
{
    private PCDOHandler owl_handler;
    private QueryStore query_store;
    private OntModel ontologyModel;

    
    private Map<Object, Object> uri_drug_resources;
    
    public QueryTests(){
        
        owl_handler = PCDOHandler.getInstance();
        query_store = QueryStore.getInstance();
        uri_drug_resources = new HashMap<Object, Object>();
        
        getURIResources();
    }
    
    public void getURIResources(){
        InputStream inputstream = getClass().getClassLoader().getResourceAsStream("uri_resources.properties");
        Properties properties = new Properties();
        try {
            properties.load(inputstream);
            
            properties.forEach((key,value)->{
                
                uri_drug_resources.put(key, value);
                
            });
            
        } catch (IOException ex) {
            Logger.getLogger(QueryTests.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(uri_drug_resources.size());
    }
            
    public void setOntology(String url){
       owl_handler.setOntologyURL(url);
       
       ontologyModel = owl_handler.getOntologyModel();
    }
    
    public String queryAll(){
        
        StringBuilder content = new StringBuilder();
        
        Map<String, String> queries = query_store.getQueries();
        queries.forEach((key,value)->{
            
            String query_string = value;
            
            //System.out.println(query_string);
            
            
            int b =query_string.indexOf("#");
            int e = query_string.indexOf("\n");
            //System.out.println(query_string.substring(b, e));
            String question = query_string.substring(b, e);
            
            content.append("\n\n" + question + "\n\n");
            content.append("\\begin{verbatim}\n");
            QueryExecution query_execution =QueryExecutionFactory.create(query_string, this.ontologyModel);
            
            ResultSet result_set = query_execution.execSelect();
            
            while(result_set.hasNext()){
                QuerySolution solution = result_set.nextSolution();
                Iterator<String> varNames = solution.varNames();
                while(varNames.hasNext()){
                    String variable = varNames.next();
                    String answer = solution.getResource(variable).getLocalName();
                    //System.out.print(answer + " \t ");
                    content.append(answer+"   ");
                }
                
                //System.out.println(); 
                content.append("\n");
            }
            content.append("\\end{verbatim}\n");
            
        
        });
        
        return content.toString();
    }
    
    public Map<Object, Object> getUri_drug_resources() {
        return uri_drug_resources;
    }
    
    public static void main( String[] args )
    {
        


        QueryTests app = new QueryTests();
        
        app.getUri_drug_resources().forEach((key,value)->{
            
            System.out.println("***********");
            System.out.println(key);
            System.out.println("***********");
            
            app.setOntology((String)value);
            
            
            try {
                FileUtils.writeStringToFile(new File(key + ".txt"), app.queryAll(), StandardCharsets.UTF_8);
                
                //app.queryAll();
            } catch (IOException ex) {
                Logger.getLogger(QueryTests.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        });
        
    }
}
