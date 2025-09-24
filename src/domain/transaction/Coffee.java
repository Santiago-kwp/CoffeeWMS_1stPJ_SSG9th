package domain.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class Coffee {
  String id;
  String name;
  String origin;
  char decaf;
  String roasted;
  int price;
  String grade;
  String type;

}
