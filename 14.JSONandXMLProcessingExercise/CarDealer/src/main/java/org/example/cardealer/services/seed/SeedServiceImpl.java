package org.example.cardealer.services.seed;

import org.example.cardealer.domain.dtos.car.wrappers.CarsImportWrapperDto;
import org.example.cardealer.domain.dtos.customer.wrappers.CustomersImportWrapperDto;
import org.example.cardealer.domain.dtos.part.wrappers.PartsImportWrapperDto;
import org.example.cardealer.domain.dtos.sale.SaleImportDto;
import org.example.cardealer.domain.dtos.supplier.wrappers.SuppliersImportWrapperDto;
import org.example.cardealer.domain.entities.*;
import org.example.cardealer.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.example.cardealer.constants.Paths.*;
import static org.example.cardealer.constants.Utils.MODEL_MAPPER;

@Service
public class SeedServiceImpl implements SeedService {
    private final SupplierRepository supplierRepository;
    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final SaleRepository saleRepository;

    @Autowired
    public SeedServiceImpl(SupplierRepository supplierRepository,
                           PartRepository partRepository,
                           CarRepository carRepository,
                           CustomerRepository customerRepository, SaleRepository saleRepository) {

        this.supplierRepository = supplierRepository;
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.saleRepository = saleRepository;
    }

    @Override
    public void seedSuppliers() throws IOException, JAXBException {
        if (this.supplierRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(SUPPLIERS_JSON_PATH.toFile());
//
//            List<PartSupplier> suppliers = Arrays.stream(GSON.fromJson(fileReader, SupplierImportDto[].class))
//                    .map(supplierImportDto -> MODEL_MAPPER.map(supplierImportDto, PartSupplier.class))
//                    .toList();

            //  for XML :
            final FileReader fileReader = new FileReader(SUPPLIERS_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(SuppliersImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final SuppliersImportWrapperDto suppliersImportWrapperDto = (SuppliersImportWrapperDto) unmarshaller.unmarshal(fileReader);

            final List<PartSupplier> suppliers = suppliersImportWrapperDto.getSuppliers()
                    .stream()
                    .map(supplierImportDto -> MODEL_MAPPER.map(supplierImportDto, PartSupplier.class))
                    .collect(Collectors.toList());

            this.supplierRepository.saveAllAndFlush(suppliers);
            fileReader.close();
        }
    }

    @Override
    public void seedParts() throws IOException, JAXBException {
        if (this.partRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(PARTS_JSON_PATH.toFile());
//
//            List<Part> parts = Arrays.stream(GSON.fromJson(fileReader, PartImportDto[].class))
//                    .map(partImportDto -> MODEL_MAPPER.map(partImportDto, Part.class))
//                    .map(this::setRandomSupplier)
//                    .toList();

            //  for XML :
            final FileReader fileReader = new FileReader(PARTS_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(PartsImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final PartsImportWrapperDto partsImportWrapperDto = (PartsImportWrapperDto) unmarshaller.unmarshal(fileReader);

            final List<Part> parts = partsImportWrapperDto.getParts()
                    .stream()
                    .map(partImportDto -> MODEL_MAPPER.map(partImportDto, Part.class))
                    .map(this::setRandomSupplier)
                    .collect(Collectors.toList());

            this.partRepository.saveAllAndFlush(parts);
            fileReader.close();
        }
    }

    private Part setRandomSupplier(Part part) {
        PartSupplier supplier = this.supplierRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);

        part.setSupplier(supplier);

        return part;
    }

    @Override
    public void seedCars() throws IOException, JAXBException {
        if (this.carRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(CARS_JSON_PATH.toFile());
//
//            final List<Car> cars = Arrays.stream(GSON.fromJson(fileReader, CarImportDto[].class))
//                    .map(carImportDto -> MODEL_MAPPER.map(carImportDto, Car.class))
//                    .map(this::setRandomParts)
//                    .toList();
            // for XML:
            final FileReader fileReader = new FileReader(CARS_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(CarsImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            final CarsImportWrapperDto carsImportWrapperDto = (CarsImportWrapperDto) unmarshaller.unmarshal(fileReader);

            final List<Car> cars = carsImportWrapperDto.getCars()
                    .stream()
                    .map(carImportDto -> MODEL_MAPPER.map(carImportDto, Car.class))
                    .map(this::setRandomParts)
                    .toList();

            this.carRepository.saveAllAndFlush(cars);
            fileReader.close();
        }
    }

    private Car setRandomParts(Car car) {
        Random random = new Random();

        int partsCount = random.nextInt(10, 21);

        List<Part> parts = new ArrayList<>();

        IntStream.range(0, partsCount)
                .forEach(num -> {
                    Part part = this.partRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
                    parts.add(part);
                });

        car.setParts(parts);
        return car;
    }

    @Override
    public void seedCustomers() throws IOException, JAXBException {
        if (this.customerRepository.count() == 0) {
            // for JSON :
//            FileReader fileReader = new FileReader(CUSTOMERS_JSON_PATH.toFile());
//
//            List<Customer> customers = Arrays.stream(GSON.fromJson(fileReader, CustomerImportDto[].class))
//                    .map(customerImportDto -> MODEL_MAPPER.map(customerImportDto, Customer.class))
//                    .toList();

            // for XML:
            final FileReader fileReader = new FileReader(CUSTOMERS_XML_PATH.toFile());

            final JAXBContext context = JAXBContext.newInstance(CustomersImportWrapperDto.class);
            final Unmarshaller unmarshaller = context.createUnmarshaller();

            CustomersImportWrapperDto customersImportWrapperDto = (CustomersImportWrapperDto) unmarshaller.unmarshal(fileReader);

            List<Customer> customers = customersImportWrapperDto.getCustomers()
                    .stream()
                    .map(customerImportDto -> MODEL_MAPPER.map(customerImportDto, Customer.class))
                    .toList();

            this.customerRepository.saveAllAndFlush(customers);
            fileReader.close();
        }
    }

    @Override
    public void seedSales() {
        int[] discounts = new int[]{0, 5, 10, 15, 20, 30, 40, 50};
        int count = 150;
        if (this.saleRepository.count() == 0) {

            while (count > 0) {
                Car car = this.carRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
                Customer customer = this.customerRepository.getRandomEntity().orElseThrow(NoSuchElementException::new);
                int discountPercentage = getRandomDiscount(discounts);

                SaleImportDto saleImportDto = new SaleImportDto((double) discountPercentage, car, customer);
                Sale sale = MODEL_MAPPER.map(saleImportDto, Sale.class);
                this.saleRepository.saveAndFlush(sale);

                count--;
            }

        }
    }

    private int getRandomDiscount(int[] discounts) {
        Random random = new Random();
        int index = random.nextInt(0, discounts.length);

        return discounts[index];
    }
}
