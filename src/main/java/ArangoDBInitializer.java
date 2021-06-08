import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.CollectionEntity;

public final class ArangoDBInitializer {
    private static final String HOST = "127.0.0.1";
    private static final Integer PORT = 8529;
    private static ArangoDB arangoDB;

    public static void createDataBase(String name) {
        try {
            arangoDB.createDatabase(name);
            System.out.println("Database created: " + name);
        } catch (ArangoDBException e) {
            System.err.println("Failed to create database: " + name + "; " + e.getMessage());
        }
    }

    public static void createCollection(String dbName, String collectionName) {
        try {
            CollectionEntity myArangoCollection = arangoDB.db(dbName).createCollection(collectionName);
            System.out.println("Collection created: " + myArangoCollection.getName());
        } catch (ArangoDBException e) {
            System.err.println("Failed to create collection: " + collectionName + "; " + e.getMessage());
        }
    }

    public static ArangoDB getDefaultConnectionDB() {
        if (arangoDB == null) {
            arangoDB = new ArangoDB.Builder()
                    .host(HOST, PORT)
                    .build();
        }
        return arangoDB;
    }


}
