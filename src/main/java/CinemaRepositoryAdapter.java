import com.arangodb.ArangoCursor;
import com.arangodb.ArangoDB;
import com.arangodb.ArangoDBException;
import com.arangodb.entity.BaseDocument;
import models.Cinema;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public final class CinemaRepositoryAdapter {

    private final ArangoDB arangoDB;
    private final String databaseName;
    private final String collectionName;

    public CinemaRepositoryAdapter(ArangoDB arangoDB, String databaseName, String collectionName) {
        this.arangoDB = arangoDB;
        this.databaseName = databaseName;
        this.collectionName = collectionName;
    }

    public void add(String key, Cinema cinema) {
        BaseDocument documentObject = new BaseDocument(key);
        documentObject.addAttribute("name", cinema.getName());
        documentObject.addAttribute("workers", cinema.getWorkers().toString());

        try {
            arangoDB.db(databaseName).collection(collectionName).insertDocument(documentObject);
            System.out.println("\tDocument created");
        } catch (ArangoDBException e) {
            System.err.println("Failed to create document. " + e.getMessage());
        }
    }

    public List<Cinema> getAll() {
        List<Cinema> cinemas = new LinkedList<>();
        try {
            String query = "FOR t IN cinema RETURN t";
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, null, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String name2 = aDocument.getAttribute("name").toString();
                Double workers = Double.valueOf(aDocument.getAttribute("workers").toString());
                Cinema cinema = new Cinema(name2, workers);
                cinemas.add(cinema);
            });
            System.out.println("Lista kin:\t");
            for (Cinema cinema : cinemas) {
                System.out.println(cinema);
            }
            return cinemas;
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
        return null;
    }

    public void updateDocument(String key, Cinema cinema) {
        try {

            BaseDocument updatedDocument = arangoDB.db(databaseName).collection(collectionName).getDocument(key, BaseDocument.class);
            updatedDocument.updateAttribute("name", cinema.getName());
            updatedDocument.updateAttribute("workers", cinema.getWorkers());
            arangoDB.db(databaseName).collection(collectionName).updateDocument(key, updatedDocument);
        } catch (ArangoDBException e) {
            System.err.println("Failed to update document. " + e.getMessage());
        }
    }

    public void delete(String key) {
        try {
            arangoDB.db(databaseName).collection(collectionName).deleteDocument(key);
        } catch (ArangoDBException e) {
            System.err.println("Failed to delete document. " + e.getMessage());
        }
    }

    public List<Cinema> getByName(String name) {
        List<Cinema> cinemas = new LinkedList<>();
        try {
            String query = "FOR t IN cinema FILTER t.name == @name RETURN t";
            Map<String, Object> bindedValues = Collections.singletonMap("name", name);
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, bindedValues, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String name2 = aDocument.getAttribute("name").toString();
                Double workers = Double.valueOf(aDocument.getAttribute("workers").toString());
                Cinema cinema = new Cinema(name2, workers);
                cinemas.add(cinema);
            });
            System.out.println("Kino o nazwie\t");
            for (Cinema cinema : cinemas) {
                System.out.println(cinema);
            }
            return cinemas;
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }
        return null;
    }

    public Double findAverageWorkers() {
        List<Cinema> cinemas = new LinkedList<>();
        try {
            String query = "FOR t IN cinema RETURN t";
            ArangoCursor<BaseDocument> cursor = arangoDB.db(databaseName).query(query, null, null, BaseDocument.class);
            cursor.forEachRemaining(aDocument -> {
                String name = aDocument.getAttribute("name").toString();
                Double workers = Double.valueOf(aDocument.getAttribute("workers").toString());
                Cinema cinema = new Cinema(name, workers);
                cinemas.add(cinema);
            });
        } catch (ArangoDBException e) {
            System.err.println("Failed to execute query. " + e.getMessage());
        }

        AtomicReference<Double> workersSum = new AtomicReference<>(0.0);
        cinemas.forEach(cinema -> workersSum.updateAndGet(v -> v + cinema.getWorkers()));
        Double average = workersSum.get() / cinemas.size();
            System.out.println("Srednia ilośc pracowników w kinach wynosi wynosi: " + average + ".");

        return workersSum.get() / cinemas.size();
    }
}
