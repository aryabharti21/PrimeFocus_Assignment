package org.example;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import org.bson.Document;

public class AtomicCounter {
    private final MongoCollection<Document> collection;
    private final String counterName;

    public AtomicCounter(MongoCollection<Document> collection, String counterName) {
        this.collection = collection;
        this.counterName = counterName;
    }

    public long getNextSequence() {
        Document query = new Document("_id", counterName);
        Document update = new Document("$inc", new Document("seq", 1));
        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .upsert(true)
                .returnDocument(ReturnDocument.AFTER);

        Document result = collection.findOneAndUpdate(query, update, options);
        Object seqObj = result.get("seq");

        if (seqObj instanceof Integer) {
            return ((Integer) seqObj).longValue();
        } else if (seqObj instanceof Long) {
            return (Long) seqObj;
        } else {
            throw new IllegalStateException("Unexpected sequence type: " + seqObj.getClass());
        }
    }
}