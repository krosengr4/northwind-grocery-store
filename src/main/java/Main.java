import logic.UILogic;

public class Main {

    public static void main(String[] args) {

        System.out.println("\n\t\t----------------------WELCOME TO NORTHWIND GROCERIES!----------------------\n");
        UILogic.setDataSource();
        UILogic.processHomeScreen();

        System.out.println("\n\nHave a Nice Day! :)");
    }
}
