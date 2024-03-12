package org.example.cardealer;

import org.example.cardealer.services.car.CarService;
import org.example.cardealer.services.customer.CustomerService;
import org.example.cardealer.services.sale.SaleService;
import org.example.cardealer.services.seed.SeedService;
import org.example.cardealer.services.supplier.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class ConsoleRunner implements CommandLineRunner {
    private final SeedService seedService;
    private final CustomerService customerService;
    private final CarService carService;
    private final SupplierService supplierService;
    private final SaleService saleService;

    @Autowired
    public ConsoleRunner(SeedService seedService,
                         CustomerService customerService,
                         CarService carService,
                         SupplierService supplierService,
                         SaleService saleService) {

        this.seedService = seedService;
        this.customerService = customerService;
        this.carService = carService;
        this.supplierService = supplierService;
        this.saleService = saleService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Car Dealer Import Data
        this.seedService.seedAll();

        // Query 1 – Ordered Customers
//        this.customerService.getAllOrderByBirthdate();

        // Query 2 – Cars from Make Toyota
//        this.carService.getAllByMakeOrderByModelTravelledDistanceDesc("Toyota");

        // Query 3 – Local Suppliers
//        this.supplierService.getAllByIsImporterIsFalse();

        // Query 4 – Cars with Their List of Parts
//        this.carService.getAllCars();

        // Query 5 – Total Sales by Customer
//        this.customerService.getAllByAtLEastOneCarBought();

        // Query 6 – Sales with Applied Discount
//       this.saleService.findAllSales();
    }
}
