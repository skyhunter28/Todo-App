package io.github.todo.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.MongoClientURI;
import com.mongodb.client.*;
import io.github.todo.config.TodoConfig;
import io.github.todo.dto.TodoDTO;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.*;
@Component
public class TodoService {
    MongoDatabase database;
    MongoCollection collection;

    public TodoService() {
        TodoConfig todoConfig = new TodoConfig();
        MongoClientURI uri = new MongoClientURI(todoConfig.db_URL);
        try {
            MongoClient mongoClient = MongoClients.create(todoConfig.db_URL);
            this.database = mongoClient.getDatabase("todaDatabase");
        } catch (Exception e) {
            System.out.println(e + "Exception Ocurred while making the Mongo Client");
        }
    }


    public List<TodoDTO> addTodo(TodoDTO todoDTO) throws JsonProcessingException {
        collection = database.getCollection("todaData");
        System.out.println(todoDTO.toString());
        //   DBObject dbObject =(DBObject)todoDTO.toString();
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(todoDTO));
        Document myDoc = Document.parse(objectMapper.writeValueAsString(todoDTO));

        collection.insertOne(myDoc);
        // return "200 OK";
        return fetchTodo();


    }

    public List<TodoDTO> fetchTodo() {
        collection = database.getCollection("todaData");
        //  Document doc = (Document) collection.find().first();
        FindIterable<Document> docs = collection.find();
        List<TodoDTO> total_todo = new ArrayList<>();
        for (Document doc : docs) {
            System.out.println("Adding Doc to TodoDTO : "+ doc.toJson());
            TodoDTO returnTodo = TodoDTO.builder()
                    ._id(doc.get("_id").toString())
                    .text(doc.get("text").toString())
                    .done((Boolean)doc.get("done"))
                    .build();
            total_todo.add(returnTodo);
        }
        System.out.println("Inside Service : "+total_todo.toString());
        return total_todo;

        // Create TodoDTO Object from Document.

    }

    public TodoDTO fetchTodoById(String id) {
        collection = database.getCollection("todaData");
        Document doc = (Document) collection.find().first();

        TodoDTO returnTodo = TodoDTO.builder()
                ._id(doc.get("_id").toString())
                .text(doc.get("text").toString())
                .done(Boolean.getBoolean(doc.get("done").toString()))
                .build();

        return returnTodo;
    }

    public String updateTodo(String id) {
        collection = database.getCollection("todaData");


        try (MongoCursor<Document> cursor = collection.find().iterator()) {
         //   TodoDTO todoDTO =new TodoDTO();

            while(cursor.hasNext())
            {
                Document obj=cursor.next();
                String _id = (String )obj.get("_id");
                boolean done_status= (boolean) obj.get("done");
                System.out.println(done_status);
                if(_id.equals(id))
                {
                        boolean status = done_status ? false:true;
                         Bson filter = eq("_id", id);
                         Bson updateOperation = set("done",status);
                    System.out.println(collection.find(filter).first());
                          collection.findOneAndUpdate(filter,updateOperation);
                            System.out.println(updateOperation);
//                            BasicDBObject newDocument= new BasicDBObject();
//                            newDocument.append("$set",new BasicDBObject().append("done",status));
//                            BasicDBObject seachQuery= new BasicDBObject().append("_id",id);
//                            collection.findOneAndUpdate(seachQuery,newDocument);
                            cursor.close();
                            break;
                }
            }
        }

       // DBObject query = new BasicDBObject("done", new BasicDBObject("$eq", ));

            return "{200 : 0k}";
    }
}