import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    private static final String ORIGIN_CITY_NAME = "Владивосток";
    private static final String DESTINATION_CITY_NAME = "Тель-Авив";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.err.println("Error: Please provide the path to the tickets.json file as a command-line argument.");
            return;
        }

        String filePath = args[0];
        if (!Files.exists(Paths.get(filePath))) {
            System.err.println("Error: File not found at the specified path: " + filePath);
            return;
        }

        try (Reader reader = new FileReader(filePath)) {
            Gson gson = new Gson();
            TicketsWrapper ticketsWrapper = gson.fromJson(reader, TicketsWrapper.class);

            List<Ticket> filteredTickets = ticketsWrapper.getTickets().stream()
                    .filter(t -> ORIGIN_CITY_NAME.equals(t.getOriginName()) && DESTINATION_CITY_NAME.equals(t.getDestinationName()))
                    .collect(Collectors.toList());

            if (filteredTickets.isEmpty()) {
                System.out.println("No tickets found for the route: " + ORIGIN_CITY_NAME + " -> " + DESTINATION_CITY_NAME);
                return;
            }

            calculateAndPrintMinFlightTimes(filteredTickets);
            calculateAndPrintPriceDifference(filteredTickets);

        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
        }
    }

    private static void calculateAndPrintMinFlightTimes(List<Ticket> tickets) {
        Map<String, Long> minFlightTimes = new HashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy H:mm");

        for (Ticket ticket : tickets) {
            LocalDateTime departure = LocalDateTime.parse(ticket.getDepartureDate() + " " + ticket.getDepartureTime(), formatter);
            LocalDateTime arrival = LocalDateTime.parse(ticket.getArrivalDate() + " " + ticket.getArrivalTime(), formatter);
            long durationMinutes = Duration.between(departure, arrival).toMinutes();

            minFlightTimes.merge(ticket.getCarrier(), durationMinutes, Math::min);
        }

        System.out.println("1. Minimum flight time for each carrier from " + ORIGIN_CITY_NAME + " to " + DESTINATION_CITY_NAME + ":");
        minFlightTimes.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> {
                    long totalMinutes = entry.getValue();
                    long hours = totalMinutes / 60;
                    long minutes = totalMinutes % 60;
                    System.out.printf("   - %s: %d h %d min\n", entry.getKey(), hours, minutes);
                });
        System.out.println();
    }

    private static void calculateAndPrintPriceDifference(List<Ticket> tickets) {
        List<Integer> prices = tickets.stream()
                .map(Ticket::getPrice)
                .sorted()
                .collect(Collectors.toList());

        double averagePrice = prices.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);

        double medianPrice;
        int size = prices.size();
        if (size % 2 == 0) {
            medianPrice = (prices.get(size / 2 - 1) + prices.get(size / 2)) / 2.0;
        } else {
            medianPrice = prices.get(size / 2);
        }

        double difference = averagePrice - medianPrice;

        System.out.println("2. Price difference for flights between " + ORIGIN_CITY_NAME + " and " + DESTINATION_CITY_NAME + ":");
        System.out.printf("   - Average price: %.1f\n", averagePrice);
        System.out.printf("   - Median price:  %.1f\n", medianPrice);
        System.out.printf("   - Difference:    %.1f\n", difference);
    }

    public static class TicketsWrapper {
        private List<Ticket> tickets;

        public List<Ticket> getTickets() {
            return tickets;
        }
    }


    public static class Ticket {
        @SerializedName("origin_name")
        private String originName;

        @SerializedName("destination_name")
        private String destinationName;

        @SerializedName("departure_date")
        private String departureDate;

        @SerializedName("departure_time")
        private String departureTime;

        @SerializedName("arrival_date")
        private String arrivalDate;

        @SerializedName("arrival_time")
        private String arrivalTime;

        private String carrier;
        private int price;

        public String getOriginName() { return originName; }
        public String getDestinationName() { return destinationName; }
        public String getDepartureDate() { return departureDate; }
        public String getDepartureTime() { return departureTime; }
        public String getArrivalDate() { return arrivalDate; }
        public String getArrivalTime() { return arrivalTime; }
        public String getCarrier() { return carrier; }
        public int getPrice() { return price; }
    }
}