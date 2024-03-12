package org.example.cardealer.constants;

import java.nio.file.Path;

public enum Paths {
    ;
    public static final Path SUPPLIERS_JSON_PATH = Path.of("src", "main", "resources", "dbContent","jsons", "suppliers.json");
    public static final Path PARTS_JSON_PATH = Path.of("src", "main", "resources", "dbContent","jsons", "parts.json");
    public static final Path CARS_JSON_PATH = Path.of("src", "main", "resources", "dbContent","jsons", "cars.json");
    public static final Path CUSTOMERS_JSON_PATH = Path.of("src", "main", "resources","jsons", "dbContent", "customers.json");

    public static final Path ORDERER_CUSTOMERS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "ordered-customers.json");
    public static final Path TOYOTA_CARS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "toyota-cars.json");
    public static final Path LOCAL_SUPPLIERS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "local-suppliers.json");
    public static final Path CARS_WITH_PARTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "cars-and-parts.json");
    public static final Path CUSTOMERS_TOTAL_SALES_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "customers-total-sales.json");
    public static final Path SALES_WITH_APPLIED_DISCOUNTS_JSON_PATH =
            Path.of("src", "main", "resources", "outputs","jsons", "sales-discounts.json");

    public static final Path SUPPLIERS_XML_PATH = Path.of("src", "main", "resources", "dbContent","xmls", "suppliers.xml");
    public static final Path PARTS_XML_PATH = Path.of("src", "main", "resources", "dbContent","xmls", "parts.xml");
    public static final Path CARS_XML_PATH = Path.of("src", "main", "resources", "dbContent","xmls", "cars.xml");
    public static final Path CUSTOMERS_XML_PATH = Path.of("src", "main", "resources", "dbContent","xmls", "customers.xml");
    public static final Path ORDERDER_CUSTOMERS_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "ordered-customers.xml");
    public static final Path TOYOTA_CARS_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "toyota-cars.xml");
    public static final Path LOCAL_SUPPLIERS_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "local-suppliers.xml");
    public static final Path CARS_WITH_PARTS_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "cars-and-parts.xml");
    public static final Path CUSTOMERS_TOTAL_SALES_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "customers-total-sales.xml");
    public static final Path SALES_WITH_APPLIED_DISCOUNTS_XML_PATH =
            Path.of("src", "main", "resources", "outputs","xmls", "sales-discounts.xml");
}
