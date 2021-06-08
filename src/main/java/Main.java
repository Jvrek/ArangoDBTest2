import com.arangodb.ArangoDB;
import org.apache.log4j.BasicConfigurator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        BasicConfigurator.configure();
        ArangoDB arango = ArangoDBInitializer.getDefaultConnectionDB();
        ArangoDBInitializer.createDataBase("cinema");
        ArangoDBInitializer.createCollection("cinema", "cinema");
        CinemaRepositoryAdapter serviceRepo = new CinemaRepositoryAdapter(arango, "cinema", "cinema");
        CinemaService cinemaService = new CinemaService(serviceRepo);
        Scanner scanner = new Scanner(System.in);
        int choose;

        do {
            System.out.println("0-wyjdz\t\t1-dodaj\t\t2-usun\t\t3-wypisz\t\t4-aktualizuj\t\t5-Szukaj po nazwie\t\t6-Srednia liczba pracownikow");
            choose = scanner.nextInt();
            switch (choose) {
                case 0:

                    break;
                case 1:
                    cinemaService.add();
                    break;
                case 2:
                    cinemaService.delete();
                    break;
                case 3:
                    cinemaService.searchByKey();
                    break;
                case 4:
                    cinemaService.update();
                    break;
                case 5:
                    cinemaService.searchByName();
                    break;
                case 6:
                    cinemaService.findAverageWorkers();
                    break;
                default:
                    throw new IllegalStateException("Brak opcji: " + choose);
            }
        } while (choose != 0);

    }
}
