package model.inventory;

import domain.transaction.Coffee;

import java.util.List;

public interface CoffeeDAO {
    List<Coffee> getAllCoffees();

}
