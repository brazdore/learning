package com.brazdore.services;

import com.brazdore.entities.Item;
import com.brazdore.entities.Sale;
import com.brazdore.exceptions.InvalidDataFormatException;
import com.brazdore.repositories.SaleRepository;
import com.brazdore.services.strategies.IDataService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service(value = "003")
public class SaleService implements IDataService {

    private final SaleRepository repository;

    public SaleService(SaleRepository saleRepository) {
        this.repository = saleRepository;
    }

    public List<Sale> getList() {
        return repository.getList();
    }

    public Boolean save(String[] dataArray) {
        String[] itemListArray = dataArray[2].replace("[", "").replace("]", "").split(",");
        List<Item> itemList = new ArrayList<>();

        Arrays.stream(itemListArray)
                .forEach(itemEntry -> {
                    try {
                        var itemArray = itemEntry.split("-");
                        var item = new Item(itemArray[0], Long.parseLong(itemArray[1]), new BigDecimal(itemArray[2]));
                        itemList.add(item);
                    } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                        throw new InvalidDataFormatException(e.getMessage());
                    }
                });

        return repository.save(new Sale(dataArray[1], itemList, sumItemList(itemList), dataArray[3]));
    }

    public void clear() {
        repository.clear();
    }

    public AbstractMap.SimpleEntry<BigDecimal, List<String>> getWorstSalesmanName() {
        var saleMapByName = getList().stream().collect(Collectors.groupingBy(Sale::getSalesmanName)); // Group by SalesmanName.

        List<AbstractMap.SimpleEntry<String, BigDecimal>> nameRevenueMapList = new ArrayList<>();
        List<String> names = new ArrayList<>();

        final BigDecimal[] lowestSalesmanRevenue = {BigDecimal.valueOf(Integer.MAX_VALUE)};

        saleMapByName.forEach((name, value) -> {
            var totalRevenue = value.stream()
                    .parallel()
                    .map(Sale::getRevenue)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            lowestSalesmanRevenue[0] = totalRevenue.compareTo(lowestSalesmanRevenue[0]) < 0 ? totalRevenue : lowestSalesmanRevenue[0];

            nameRevenueMapList.add(new AbstractMap.SimpleEntry<>(name, totalRevenue));
        });

        nameRevenueMapList.stream()
                .filter(x -> x.getValue().compareTo(lowestSalesmanRevenue[0]) == 0)
                .forEach(y -> names.add(y.getKey()));

        return new AbstractMap.SimpleEntry<>(lowestSalesmanRevenue[0], names);
    }

    public AbstractMap.SimpleEntry<BigDecimal, List<String>> getMostExpensiveSaleID() {
        var saleList = getList();
        List<String> idList = new ArrayList<>();

        var biggestSale = saleList.stream()
                .parallel()
                .map(Sale::getRevenue)
                .max(Comparator.naturalOrder())
                .orElse(BigDecimal.ZERO);

        saleList.stream()
                .filter(x -> x.getRevenue().compareTo(biggestSale) == 0)
                .forEach(y -> idList.add(y.getId()));

        return new AbstractMap.SimpleEntry<>(biggestSale, idList);
    }

    private BigDecimal sumItemList(List<Item> itemList) {
        return itemList.stream()
                .parallel()
                .map(x -> x.getPrice().multiply(BigDecimal.valueOf(x.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
