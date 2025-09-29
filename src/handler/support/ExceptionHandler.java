//package handler.support;
//
//import constant.support.BoardErrorCode;
//import constant.support.ValidCheck;
//import controller.support.CSMenu;
//import exception.support.InputException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
//
//public class ExceptionHandler {
//    ValidCheck validCheck = new ValidCheck();
//    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//
//    CSMenu csMenu = new CSMenu();
//
//     public String csMenuExp() {
//         try {
//             String choice = input.readLine();
//             validCheck.isFourMenuValid(choice);
//             return choice;
//         } catch (InputException e) {
//             System.out.println(e.getMessage());
//         } catch (IOException e) {
//             System.out.println(BoardErrorCode.NOT_INPUT_IO.getMessage());
//         } catch (NullPointerException e) {
//             System.out.println();
//         }
//         return null;
//     }
//
//}
