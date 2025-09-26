package domain.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Coffee {
  private String coffeeId;
  private String coffeeName;
  private String coffeeOrigin;
  private String decaf;
  private String roasted;
  private int price;
  private String coffeeGrade;
  private String coffeeType;

  @Override
  public String toString() {
    return String.format("| %-20s\t | %-10s\t | %-5s\t | %-5s\t | %-10s\t | %-10s\t |",
        coffeeName, coffeeOrigin, decaf, roasted, coffeeGrade, price);
  }

  public String getCoffeeId() {
    return coffeeId;
  }

  public void setCoffeeId(String coffeeId) {
    this.coffeeId = coffeeId;
  }

  public String getCoffeeName() {
    return coffeeName;
  }

  public void setCoffeeName(String coffeeName) {
    this.coffeeName = coffeeName;
  }

  public String getCoffeeOrigin() {
    return coffeeOrigin;
  }

  public void setCoffeeOrigin(String coffeeOrigin) {
    this.coffeeOrigin = coffeeOrigin;
  }

  public String getDecaf() {
    return decaf;
  }

  public void setDecaf(String decaf) {
    this.decaf = decaf;
  }

  public String getRoasted() {
    return roasted;
  }

  public void setRoasted(String roasted) {
    this.roasted = roasted;
  }

  public int getPrice() {
    return price;
  }

  public void setPrice(int price) {
    this.price = price;
  }

  public String getCoffeeGrade() {
    return coffeeGrade;
  }

  public void setCoffeeGrade(String coffeeGrade) {
    this.coffeeGrade = coffeeGrade;
  }

  public String getCoffeeType() {
    return coffeeType;
  }

  public void setCoffeeType(String coffeeType) {
    this.coffeeType = coffeeType;
  }



}
