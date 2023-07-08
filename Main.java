import java.util.InputMismatchException;
import java.util.Scanner;


class Train {
    private String trainNumber;
    private String source;
    private String destination;
    private String trainType; // General or AC sleeper
    private int availableSeats;

    public Train(String trainNumber, String source, String destination, String trainType, int availableSeats) {
        this.trainNumber = trainNumber;
        this.source = source;
        this.destination = destination;
        this.trainType = trainType;
        this.availableSeats = availableSeats;
    }

    public String getTrainNumber() {
        return trainNumber;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getTrainType() {
        return trainType;
    }

    public int getAvailableSeats() {
        return availableSeats;
    }

    public void bookSeats(int numSeats) {
        if (availableSeats >= numSeats) {
            availableSeats -= numSeats;
        }
    }
}

class PaymentModule {
    private static final String DESIRED_UPI_ID = "sn@upi";
    private static final String DESIRED_USERNAME = "ks960";
    private static final String DESIRED_PASSWORD = "1805";
    private static final long DESIRED_CARD_NUMBER = 960321320;
    private static final String DESIRED_EXPIRY_DATE = "11/25";

    public static boolean processPayment() {
        System.out.println("--------------------------------Payment Portal--------------------------------\n\n");
        System.out.println("1. Credit/Debit Card\n");
        System.out.println("2. UPI\n");
        System.out.println("3. Net Banking\n");
        System.out.println("4. Cancel Payment\n");
		
        Scanner scanner = new Scanner(System.in);
        int paymentOption = scanner.nextInt();

        switch (paymentOption) {
            case 1:
                System.out.println("CREDIT/DEBIT CARD\n");
                System.out.print("Enter the Card Number:\n ");
                long cardNumber = scanner.nextLong();
                System.out.print("Enter the Expiry date (MM/YY):\n ");
                String expiryDate = scanner.next();
                System.out.print("Enter the CVV:\n");
                int cvv = scanner.nextInt();

                // Check if the entered card number and expiry date are valid
                if (isValidCardDetails(cardNumber, expiryDate)) {
                    // Process the card payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid card number or expiry date. Payment Failed.\n");
                    return false;
                }
            case 2:
                System.out.println("UPI");
                System.out.print("Enter the UPI ID:\n ");
                String upiId = scanner.next();

                // Check if the entered UPI ID is valid
                if (isValidUpiId(upiId)) {
                    // Process the UPI payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid UPI ID. Payment Failed.\n");
                    return false;
                }
            case 3:
                System.out.println("NET BANKING");
                System.out.print("Enter the username:\n ");
                String username = scanner.next();
                System.out.print("Enter the password:\n ");
                String password = scanner.next();

                // Check if the entered username and password are valid
                if (isValidCredentials(username, password)) {
                    // Process the net banking payment
                    System.out.println("Payment Successful!\n");
                    return true;
                } else {
                    System.out.println("Invalid username or password. Payment Failed.\n");
                    return false;
                }
            case 4:
                System.out.println("Payment Cancelled.\n");
                return false;
            default:
                System.out.println("Invalid payment option. Payment Failed.\n");
                return false;
        }
    }

    private static boolean isValidCardDetails(long cardNumber, String expiryDate) {
        // Validate card number and expiry date against desired values
        return cardNumber == DESIRED_CARD_NUMBER && expiryDate.equals(DESIRED_EXPIRY_DATE);
    }

    private static boolean isValidUpiId(String upiId) {
        // Validate UPI ID against desired value
        return upiId.equals(DESIRED_UPI_ID);
    }

    private static boolean isValidCredentials(String username, String password) {
        // Validate username and password against desired values
        return username.equals(DESIRED_USERNAME) && password.equals(DESIRED_PASSWORD);
    }
}

class TrainBookingSystem {
    private Train[] trains;
    public TrainBookingSystem() {
        trains = new Train[10];
        trains[0] = new Train("SN1618", "GNT", "TPTY", "General", 50);
        trains[1] = new Train("NS1816", "GNT", "TPTY", "AC sleeper", 10);
        trains[2] = new Train("KS1805", "TPTY", "GNT", "AC sleeper", 7);
        trains[3] = new Train("K1618", "TPTY", "GNT", "General", 80);
        trains[4] = new Train("NS1105", "GNT", "SC", "AC sleeper", 12);
        trains[5] = new Train("GKS018", "GNT", "SC", "General", 60);
        trains[6] = new Train("N34511", "SC", "GNT", "AC sleeper", 9);
        trains[7] = new Train("S11543", "SC", "GNT", "General", 70);
        trains[8] = new Train("GKS1618", "GNT", "VIZAG", "AC sleeper", 11);
        trains[9] = new Train("S18054", "VIZAG", "GNT", "General", 65);
    }

    public void viewAvailableTrains() {
        System.out.println("Available Trains:\n");
        System.out.println("Train Number   Source          Destination     Type              Available Seats");
        System.out.println("---------------------------------------------------------------------------------");
        for (Train train : trains) {
            System.out.printf("%-15s %-15s %-15s %-20s %d\n", train.getTrainNumber(), train.getSource(),
                    train.getDestination(), train.getTrainType(), train.getAvailableSeats());
        }
        System.out.println();
    }

    public void bookGeneralTrain(String trainNumber, int numSeats) {
        for (Train train : trains) {
            if (train.getTrainNumber().equals(trainNumber) && train.getTrainType().equals("General")) {
                if (train.getAvailableSeats() >= numSeats) {
                    boolean paymentSuccessful = PaymentModule.processPayment(); // Process payment and check if it is successful
                    if (paymentSuccessful) {
                        train.bookSeats(numSeats); // Decrease available seats
                        System.out.println("Booking Successful! Enjoy your train journey.\n");
                    } else {
                        System.out.println("Payment cancelled. Booking failed. Seats remain unchanged.\n");
                        return; // Return without decreasing the available seats
                    }
                } else {
                    System.out.println("Not enough seats available on the selected train.\n");
                }
                return;
            }
        }
        System.out.println("Invalid train number or train type.\n");
    }

    public void bookACsleeperTrain(String trainNumber, int numSeats) {
        for (Train train : trains) {
            if (train.getTrainNumber().equals(trainNumber) && train.getTrainType().equals("AC sleeper")) {
                if (train.getAvailableSeats() >= numSeats) {
                    boolean paymentSuccessful = PaymentModule.processPayment(); // Process payment and check if it is successful
                    if (paymentSuccessful) {
                        train.bookSeats(numSeats); // Decrease available seats
                        System.out.println("Booking Successful! Enjoy your train journey.\n");
                    } else {
                        System.out.println("Payment cancelled. Booking failed. Seats remain unchanged.\n");
                        return; // Return without decreasing the available seats
                    }
                } else {
                    System.out.println("Not enough seats available on the selected train.\n");
                }
                return;
            }
        }
        System.out.println("Invalid train number or train type.\n");
    }
}

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TrainBookingSystem bookingSystem = new TrainBookingSystem();
        System.out.println("-----------------------------------Welcome-----------------------------------\n");
        System.out.println("1. View Available trains\n");
        System.out.println("2. Book General train\n");
        System.out.println("3. Book AC sleeper train\n");
        System.out.println("4. Exit\n");
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        bookingSystem.viewAvailableTrains();
                        break;
                    case 2:
                        System.out.print("Enter the train number: ");
                        String GeneralTrainNumber = scanner.next();
                        System.out.print("Enter the number of seats to book: ");
                        int numSeatsGeneral = scanner.nextInt();
                        bookingSystem.bookGeneralTrain(GeneralTrainNumber, numSeatsGeneral);
                        break;
                    case 3:
                        System.out.print("Enter the train number: ");
                        String ACsleeperTrainNumber = scanner.next();
                        System.out.print("Enter the number of seats to book: ");
                        int numSeatsACsleeper = scanner.nextInt();
                        bookingSystem.bookACsleeperTrain(ACsleeperTrainNumber, numSeatsACsleeper);
                        break;
                    case 4:
                        System.out.println("Thank you for using the SN Train Booking System. HAVE A SAFE JOURNEY!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice. Please enter a valid option.\n");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid choice.\n");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }
}