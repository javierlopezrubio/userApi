package users.services;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import users.entities.CustomUserIds;


@Service
public class NextIdService {
    @Autowired
    private MongoOperations mongo;
    public int getNextId(String id){
        CustomUserIds count;
        count = mongo.findAndModify(
                query(where("_id").is(id)),
                new Update().inc("seq", 1),
                options().returnNew(true).upsert(true),
                CustomUserIds.class
        );
        return count.getSeq();
    }
}
