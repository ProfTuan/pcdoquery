/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.uth.sbmi.pcdoquery.query;

import edu.uth.sbmi.pcdoquery.model.KBDrugQueryModel;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tuan
 */
public class QueryStore {

    //private LinkedList<String> queries = null;
    private Map<String, String> queries = null;

    private static QueryStore INSTANCE = null;

    public static QueryStore getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new QueryStore();
        }

        return INSTANCE;
    }

    public Map<String, String> getQueries() {
        return queries;
    }

    public QueryStore() {
        queries = new HashMap<String, String>();

        try {
            importQueries();
        } catch (IOException ex) {
            Logger.getLogger(QueryStore.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Map<Integer, KBDrugQueryModel> addQueriesToStorage() {

        Map<Integer, KBDrugQueryModel> query_store = new HashMap<Integer, KBDrugQueryModel>();

        InputStreamReader readdir;
        BufferedReader br;

        try (InputStream dir = getClass().getClassLoader().getResourceAsStream("sparql")) {

            readdir = new InputStreamReader(dir, StandardCharsets.UTF_8);
            br = new BufferedReader(readdir);
            
            br.lines().forEach(line_file -> 
            {
                //System.out.println(line_file);
                StringBuilder content = new StringBuilder();
                InputStream stream = getClass().getClassLoader().getResourceAsStream("sparql/" + line_file);
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader bufreader = new BufferedReader(reader);
                bufreader.lines().forEach(content_line -> {

                    content.append(content_line + "\n");

                });

                KBDrugQueryModel q_model = new KBDrugQueryModel();
                
                q_model.setFileContent(content.toString());
                q_model.setSourceFileName(line_file);
                
                Random rand = new Random();
                int idx = rand.nextInt(1000);
                
                while(query_store.containsKey(idx)){
                    idx = rand.nextInt(1000);
                }
                
                query_store.put(idx, q_model);

                try {
                    stream.close();
                    reader.close();
                    bufreader.close();
                } catch (IOException ex) {
                    Logger.getLogger(QueryStore.class.getName()).log(Level.SEVERE, null, ex);
                }

            });

        } catch (IOException ex) {
            Logger.getLogger(QueryStore.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
        return query_store;
    }

    private void importQueries() throws IOException {
        InputStreamReader readdir;
        BufferedReader br;
        try (InputStream dir = getClass().getClassLoader().getResourceAsStream("sparql")) {
            readdir = new InputStreamReader(dir, StandardCharsets.UTF_8);
            br = new BufferedReader(readdir);
            br.lines().forEach(line_file -> {
                StringBuilder content = new StringBuilder();
                InputStream stream = getClass().getClassLoader().getResourceAsStream("sparql/" + line_file);
                InputStreamReader reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
                BufferedReader bufreader = new BufferedReader(reader);
                //String line;
                bufreader.lines().forEach(content_line -> {

                    content.append(content_line + "\n");

                });

                queries.put(line_file, content.toString());

                try {
                    stream.close();
                    reader.close();
                    bufreader.close();
                } catch (IOException ex) {
                    Logger.getLogger(QueryStore.class.getName()).log(Level.SEVERE, null, ex);
                }

            });
        }
        readdir.close();
        br.close();

    }

    public static void main(String[] args) {
        QueryStore q = QueryStore.getInstance();
        q.addQueriesToStorage();

    }
}
