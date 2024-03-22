package softuni.exam.constants;

public enum Messages {
    ;
    public static final String INVALID_TOWN = "Invalid town";
    public static final String SUCCESSFULLY_IMPORTED_TOWN = "Successfully imported town %s - %d";
    public static final String INVALID_AGENT = "Invalid agent";
    public static final String SUCCESSFULLY_IMPORTED_AGENT = "Successfully imported agent %s - %s";
    public static final String INVALID_APARTMENT = "Invalid apartment";
    public static final String SUCCESSFULLY_IMPORTED_APARTMENT = "Successfully imported apartment %s - %.2f";
    public static final String INVALID_OFFER = "Invalid offer";
    public static final String SUCCESSFULLY_IMPORTED_OFFER = "Successfully imported offer %.2f";
    public static final String EXPORT_FORMAT = """
            Agent %s %s with offer №%d:
               -Apartment area: %.2f
               --Town: %s
               ---Price: %.2f$
            """;
}
