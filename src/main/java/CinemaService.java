import models.Cinema;

import java.util.List;
import java.util.Scanner;

public class CinemaService {

    private CinemaRepositoryAdapter cinemaRepositoryAdapter;

    public CinemaService(CinemaRepositoryAdapter cinemaRepositoryAdapter) {
        this.cinemaRepositoryAdapter = cinemaRepositoryAdapter;
    }

    public void add() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();

        System.out.println("Podaj nazwe:");
        String name = scanner.nextLine();

        System.out.println("Podaj liczbe pracownikow:");
        Double money = scanner.nextDouble();


        Cinema cinema = new Cinema(name, money);
        cinemaRepositoryAdapter.add(key, cinema);

    }

    public Double findAverageWorkers(){
        return cinemaRepositoryAdapter.findAverageWorkers();
    }

    public List<Cinema> searchByKey() {
        return cinemaRepositoryAdapter.getAll();
    }

    public List<Cinema> searchByName() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj nazwe:");
        String name = scanner.nextLine();

        return cinemaRepositoryAdapter.getByName(name);
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();

        cinemaRepositoryAdapter.delete(key);
    }

    public void update() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj klucz:");
        String key = scanner.nextLine();


        System.out.println("Podaj nazwe:");
        String name = scanner.nextLine();

        System.out.println("Podaj liczbe pracownikow:");
        Double workers = scanner.nextDouble();


        Cinema cinema = new Cinema(name, workers);
        cinemaRepositoryAdapter.updateDocument(key, cinema);
    }
}
