import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    private static List<Card> cards = new ArrayList<>();
    private static Card currentCard;
    private static final String FILENAME = "cards.txt";

    public static void main(String[] args) {
        loadCardsFromFile();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1. Создать карту");
            System.out.println("2. Войти в систему");
            System.out.println("3. Выход");
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    createCard();
                    break;
                case 2:
                    login();
                    if (currentCard != null) {
                        handleMenuChoice();
                    }
                    break;
                case 3:
                    System.out.println("bye)");
                    System.exit(0);
                default:
                    System.out.println("Некорректный выбор.");
            }
            saveCardsToFile();
        }

    }
    private static void loadCardsFromFile() {
        try (Scanner fileScanner = new Scanner(new File(FILENAME))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(" ");
                String cardNumber = parts[0];
                double balance = Double.parseDouble(parts[1]);
                int pin = Integer.parseInt(parts[2]);
                cards.add(new Card(cardNumber, balance, pin));
            }
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден. Создайте новый файл при необходимости.");
        }
    }
    private static void saveCardsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILENAME))) {
            for (Card card : cards) {
                writer.println(card.getCardNumber() + " " + card.getBalance() + " " + card.getPin());
            }
        } catch (IOException e) {
            System.out.println("Ошибка при сохранении данных в файл.");
        }
    }
    private static boolean isValidCardNumber(String cardNumber) {
        String regex = "^\\d{4}-\\d{4}-\\d{4}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cardNumber);
        return matcher.matches();
    }

    private static boolean isValidPin(int pin) {
        String regex = "^\\d{4}$";
        String pinStr = pin + "";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(pinStr);
        return matcher.matches();
    }
    private static void createCard() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("номер карты: ");
        String cardNumber = scanner.nextLine();
        while(!isValidCardNumber(cardNumber)){

            System.out.print("некоректный ввод. номер карты: ");
            cardNumber = scanner.nextLine();
        }
        System.out.print("баланс: ");
        double balance = scanner.nextDouble();
        System.out.print("ПИН-код: ");
        int pin = scanner.nextInt();
        while(!isValidPin(pin)){

            System.out.print("некоректный ввод.ПИН-код: ");
            cardNumber = scanner.nextLine();
        }

        Card newCard = new Card(cardNumber, balance, pin);
        cards.add(newCard);
        System.out.println("Карта создана.");
    }

    private static void login() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите номер карты: ");
        String cardNumber = scanner.nextLine();
        System.out.print("Введите ПИН-код: ");
        int enteredPin = scanner.nextInt();

        for (Card card : cards) {
            if (card.getCardNumber().equals(cardNumber) && card.validatePin(enteredPin)) {
                currentCard = card;
                System.out.println("Авторизация успешна.");
                return;
            }
        }
        System.out.println("Неправильный номер карты или ПИН-код.");
    }

    private static void showMenu() {
        System.out.println("1. Проверить баланс");
        System.out.println("2. Снять наличные");
        System.out.println("3. Пополнить счет");
        System.out.println("4. Выход");
    }
    private static void handleMenuChoice() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            showMenu();
            System.out.print("Выберите действие: ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    checkBalance();
                    break;
                case 2:
                    withdrawCash();
                    break;
                case 3:
                    depositFunds();
                    break;
                case 4:
                    System.out.println("Exit");
                    currentCard = null;
                    return;
                default:
                    System.out.println("Error");
            }
        }
    }

    private static void checkBalance() {
        System.out.println("Баланс: " + currentCard.getBalance());
    }

    private static void withdrawCash() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("сумма для снятия: ");
        double amount = scanner.nextDouble();

        if (currentCard != null) {
            currentCard.withdraw(amount);
        } else {
            System.out.println("Авторизуйтесь, чтобы выполнить действие.");
        }
    }

    private static void depositFunds() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("сумма для пополнения: ");
        double amount = scanner.nextDouble();

        if (currentCard != null) {
            currentCard.deposit(amount);
        } else {
            System.out.println("Авторизуйтесь, чтобы выполнить действие.");
        }
    }
}

