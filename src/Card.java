public class Card {
    private String cardNumber;
    private double balance;
    private int pin;
    public Card(String cardNumber, double balance, int pin) {
        this.cardNumber = cardNumber;
        this.balance = balance;
        this.pin = pin;
    }
    public int getPin(){
        return pin;
    }
    public double getBalance() {
        return balance;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    // Метод для снятия средств
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            System.out.println("Средства успешно сняты. Новый баланс: " + balance);
        } else {
            System.out.println("Недостаточно средств на счете.");
        }
    }

    // Метод для пополнения баланса
    public void deposit(double amount) {
        if (amount > 0 && amount <= 1_000_000) {
            balance += amount;
            System.out.println("Баланс успешно пополнен. Новый баланс: " + balance);
        } else {
            System.out.println("Некорректная сумма для пополнения.");
        }
    }

    // Метод для проверки ПИН-кода
    public boolean validatePin(int enteredPin) {
        return enteredPin == pin;
    }
}
