package com.oracle.demosjon.demojson;

import java.sql.Connection;

import oracle.soda.OracleCollection;
import oracle.soda.OracleCursor;
import oracle.soda.OracleDatabase;
import oracle.soda.OracleDocument;
import oracle.soda.rdbms.OracleRDBMSClient;

public class SodaController {

    private Connection conn;

    public SodaController(Connection conn) {
        this.conn = conn;
    }

    public void doSodaWork() {
        try {

            OracleRDBMSClient cl = new OracleRDBMSClient();
            OracleDatabase db = cl.getDatabase(conn);

            // Create a collection with the name "MyJSONCollection".
            OracleCollection col = db.admin().createCollection("MyJSONCollection");

            // Create a JSON document.
            OracleDocument doc = db.createDocumentFromString("{ \"name\" : \"Borja\" }");

            // Insert the document into a collection, and get back its
            // auto-generated key.
            System.out.println("Before inserting document");
            String k = col.insertAndGet(doc).getKey();

            // Find a document by its key. The following line
            // fetches the inserted document from the collection
            // by its unique key, and prints out the document's content
            System.out.println("Inserted content:" + col.find().key(k).getOne().getContentAsString() + "with Key: "+ k);

            // Find all documents in the collection matching a query-by-example (QBE).
            // The following lines find all JSON documents in the collection that have
            // a field "name" that starts with "A".
            OracleDocument f = db.createDocumentFromString("{\"name\" : { \"$startsWith\" : \"A\" }}");

            OracleCursor c = col.find().filter(f).getCursor();

            while (c.hasNext()) {
                // Get the next document.
                OracleDocument resultDoc = c.next();

                // Print the document key and content.
                System.out.println("Key:         " + resultDoc.getKey());
                System.out.println("Content:     " + resultDoc.getContentAsString());
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }
}