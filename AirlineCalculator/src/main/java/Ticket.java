import com.google.gson.annotations.SerializedName;

public class Ticket {
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