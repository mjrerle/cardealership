package com.proj0.driver;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.proj0.logger.CarDealershipLogger;
import com.proj0.models.*;
import com.proj0.services.*;
import com.proj0.utils.DBConnector;

import org.apache.log4j.Logger;

public class Main {
    private Scanner scan;
    private User userInSession;
    private boolean loggedIn;
    public static Logger logger = CarDealershipLogger.logger;
    public static Connection conn = DBConnector.getConnection();

    public Main() {
        scan = new Scanner(System.in);
        loggedIn = false;
    }

    public static void main(String[] args) {
        Main main = new Main();
        clearScreen();

        main.mainScreen();
    }

    private void setUserInSession(User user) {
        this.userInSession = user;
    }

    private User getUserInSession() {
        return this.userInSession;
    }

    public void welcomeMsg() {
        System.out.println("\nWelcome to Gen and Eric's Car Dealership!");
        errorMsg("Press ^C To Quit");
    }

    public void infoMsg(String string) {
        System.out.println(Utils.colorStringBlue(string));
    }

    public void successMsg(String string) {
        System.out.println(Utils.colorStringGreen(string));
    }

    public void errorMsg(String string) {
        System.out.println(Utils.colorStringRed(string));
    }

    private static void clearScreen() {
        System.out.print("\033[H\033[2J" + Utils.colorStringBlack() + Utils.colorBackgroundWhite());
        System.out.flush();
    }

    public void quit() {
        clearScreen();
        successMsg("Thank you for using Gen and Eric's Car Dealership DBMS!");
        System.exit(0);
    }

    public void logout() {
        this.loggedIn = false;
    }

    private boolean validateUsername(String username) {
        return username.matches("[a-zA-Z0-9]+") && username.length() < 64;
    }

    private boolean validatePassword(String password) {
        return validateUsername(password);
    }

    private boolean breakOutToMain() {
        System.out.printf("Go back to previous screen? (y)es/(n)o: ");
        String back = scan.nextLine();
        if (back.equals("yes") || back.equals("y")) {
            clearScreen();
            return false;
        }
        return true;
    }

    private Offer findAcceptedOffer(List<Offer> offers) {
        for (Offer offer : offers) {
            if (getUserInSession().getUid() == offer.getUid()) {
                if (offer.getStatus().equals("accepted")) {
                    return offer;
                }
            }
        }
        return null;
    }

    private int captureIndexInput() {
        System.out.printf(Utils.colorStringBlue("Select an option: "));
        int option = 0;
        try {
            option = this.scan.nextInt();
            this.scan.nextLine();
        } catch (InputMismatchException e) {
            this.scan.nextLine();
            errorMsg("\n\nPlease enter a valid option(positive whole number).\n\n");
            return -1;
        }
        return option;
    }

    private int captureUserOptions(int size) {
        int option = captureIndexInput();
        if (option >= 0 && option < size) {
            return option;
        } else {
            return -1;
        }
    }

    private boolean handleCreds(String username, String password) {
        if (!validateUsername(username) && !validatePassword(password) || username.isEmpty() || username.length() == 0
                || password.isEmpty() || password.length() == 0) {
            return false;
        } else {
            setUserInSession(UserService.login(username, password));
            if (getUserInSession() == null) {
                return false;
            }
        }
        this.loggedIn = true;
        return true;
    }

    private boolean handlePasswordChange(String password) {
        String username = getUserInSession().getUsername();
        if (username.isEmpty() || username.length() == 0 || password.isEmpty() || password.length() == 0) {
            return false;
            // username does not exist in the database
        } else if (UserService.getUser(username) == null) {
            if (UserService.changePassword(new User(username, password, getUserInSession().getRole()))) {
                setUserInSession(UserService.getUser(username));
                return true;
            }
            return false;
        }
        return false;
    }

    private boolean handleRegisterCreds(String username, String password) {
        if (username.isEmpty() || username.length() == 0 || password.isEmpty() || password.length() == 0) {
            return false;
        }
        return UserService.register(new User(username, password, "customer"));
    }

    private void handleManageUserChoice(User user, String choice) {
        switch (choice) {
        case "m":
        case "modify":
            modifyUser(user);
            break;
        case "r":
        case "remove":
            removeUser(user);
            break;
        case "g":
        case "give car":
            giveCar(user);
            break;
        case "d":
        case "demand payment":
            demandPayment(user);
            break;
        }
    }

    private void handleTransferCar(List<Car> carsOnLot, int carOption, User user) {
        Car car = carsOnLot.get(carOption);
        car.setUid(user.getUid());
        System.out.println("Giving the " + car.getModel() + " to " + user.getUsername() + ".");
        System.out.printf("Are you sure? (y)es/(n)o: ");
        String back = scan.nextLine();
        if (back.equals("yes") || back.equals("y")) {
            if (CarService.updateCar(car)) {
                successMsg("Successfully transferred the " + car.getModel() + " to " + user.getUsername()
                        + " the database.");
            } else {
                errorMsg("Something went wrong! We will look into this error.");
            }
        } else {
            infoMsg("Ok, we will not modify this user.");
        }
    }

    private void printCapabilities(ArrayList<String> capabilities) {
        for (int i = 0; i < capabilities.size(); i++) {
            System.out.println(i + ": " + capabilities.get(i));
        }
        System.out.println();
    }

    private void printEntityTable(List<?> list, String tableColumns) {
        if (list.size() > 0) {
            System.out.println(tableColumns);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(i + ": " + list.get(i));
            }
        }
    }

    private void printPaymentsTable(List<Payment> payments) {
        printEntityTable(payments, Utils.getPaymentTableColumns());
    }

    private void printUsersTable(List<User> users) {
        printEntityTable(users, Utils.getUserTableColumns());
    }

    private void printCarsTable(List<Car> cars) {
        printEntityTable(cars, Utils.getCarTableColumns());
    }

    private void printCustomPaymentsTable(List<Payment> payments, Car car) {
        double sum = 0;
        System.out.println(Utils.getPaymentTableColumns());
        for (int i = 0; i < payments.size(); i++) {
            System.out.println(i + ": " + payments.get(i));
            sum += payments.get(i).getAmountPaid();
        }
        System.out.printf("To date you have made %d payments amounting to $%.2f in total payments.\n", payments.size(),
                sum);
        System.out.printf("Amount still owed: $%.2f\n",
                PaymentService.getRemainingPaymentsLumpSum(this.getUserInSession(), car));
    }

    private void printPreparePayment(Payment payment, Car car) {
        payment.setMonthsLeft(payment.getMonthsLeft() - 1);
        System.out.printf("Is this payment ok: [$%.2f] with %d months left? (y)es/(n)o: ", payment.getAmountPaid(),
                payment.getMonthsLeft());
        String back = scan.nextLine();
        if (back.equals("yes") || back.equals("y")) {
            if (PaymentService.createPayment(payment)) {
                successMsg("Successfully created payment on your " + car.getModel() + ".");
            } else {
                errorMsg("Something went wrong! We will look into this error.");
            }
        } else {
            infoMsg("Ok, we will not submit this offer.");
        }
    }

    private void printConfirmOffer(Car car, int months, double downPayment) {
        double price = car.getPrice();
        if (months <= 0) {
            months = 1;
        }
        if (downPayment > price) {
            downPayment = price;
        }
        double amount = (price - downPayment) / months;
        if (downPayment >= price) {
            System.out.printf(
                    "Your down payment [$%.2f] request exceeds or is equal to the price of the requested vehicle [$%.2f].\n",
                    downPayment, price);
        } else {
            System.out.printf(
                    "You would be financing this vehicle for %d months at $%.2f per month until the full price [$%.2f] is paid off.\n",
                    months, amount, price);
        }
        System.out.printf("Is this amount per month ok: [$%.2f] with [$%.2f] as the down payment? (y)es/(n)o: ", amount,
                downPayment);
        String back = scan.nextLine();
        if (back.equals("yes") || back.equals("y")) {
            Offer offer = new Offer(amount, months, "unevaluated", downPayment, car.getCid(), getUserInSession().getUid());
            if (OfferService.createOffer(offer)) {
                successMsg("Successfully created a new offer on the " + car.getModel()
                        + " model! We will review the offer soon.");
            } else {
                errorMsg("Something went wrong! We will look into this error.");
            }
        } else {
            infoMsg("Ok, we will not submit this offer.");
        }
    }

    public void mainScreen() {
        boolean stay = true;
        int next = -1;
        while (stay) {
            clearScreen();
            welcomeMsg();
            ArrayList<String> capabilities = Capabilities.getUserCapabilities();
            printCapabilities(capabilities);
            next = captureUserOptions(capabilities.size());
            switch (next) {
            case 0:
                login();
                stay = userScreen();
                break;
            case 1:
                register();
                break;
            case 2:
                changePassword();
                break;
            case 3:
                logout();
                break;
            case 4:
                stay = false;
            }
        }
        quit();
    }

    public boolean userScreen() {
        switch (getUserInSession().getRole()) {
        case "customer":
            customerScreen();
            return true;
        case "admin":
            adminScreen();
            return true;
        case "employee":
            employeeScreen();
            return true;
        default:
            return false;
        }
    }

    public void customerScreen() {
        boolean stay = true;
        int next = -1;
        while (stay) {
            clearScreen();
            infoMsg("Welcome " + getUserInSession().getUsername() + "! Here are some available options.");
            ArrayList<String> capabilities = Capabilities.getCustomerCapabilities();

            printCapabilities(capabilities);

            next = captureUserOptions(capabilities.size());
            switch (next) {
            case 0:
                makeAnOffer();
                break;
            case 1:
                makeAPayment();
                break;
            case 2:
                viewCarsOnLot();
                break;
            case 3:
                viewOwnedCars();
                break;
            case 4:
                viewRemainingPayments();
                break;
            case 5:
                changePassword();
                break;
            case 6:
                stay = false;
                break;
            case 7:
                logout();
                stay = false;
                break;
            case 8:
                quit();
            }
        }
        clearScreen();
    }

    public void adminScreen() {
        boolean stay = true;
        int next = -1;
        while (stay) {
            clearScreen();
            infoMsg("Welcome " + getUserInSession().getUsername() + "! Here are some available options.");
            ArrayList<String> capabilities = Capabilities.getAdminCapabilities();

            printCapabilities(capabilities);

            next = captureUserOptions(capabilities.size());
            switch (next) {
            case 0:
                makeAnOffer();
                break;
            case 1:
                makeAPayment();
                break;
            case 2:
                addCarToLot();
                break;
            case 3:
                viewCarsOnLot();
                break;
            case 4:
                viewOwnedCars();
                break;
            case 5:
                viewRemainingPayments();
                break;
            case 6:
                viewAllPayments();
                break;
            case 7:
                removeCarFromLot();
                break;
            case 8:
                evaluateOffers();
                break;
            case 9:
                manageUsers();
                break;
            case 10:
                changePassword();
                break;
            case 11:
                stay = false;
                break;
            case 12:
                logout();
                stay = false;
                break;
            case 13:
                quit();
            }
        }
        clearScreen();
    }

    public void employeeScreen() {
        boolean stay = true;
        int next = -1;
        while (stay) {
            clearScreen();
            infoMsg("Welcome " + getUserInSession().getUsername() + "! Here are some available options.");
            ArrayList<String> capabilities = Capabilities.getEmployeeCapabilities();

            printCapabilities(capabilities);

            next = captureUserOptions(capabilities.size());
            switch (next) {
            case 0:
                removeCarFromLot();
                break;
            case 1:
                addCarToLot();
                break;
            case 2:
                evaluateOffers();
                break;
            case 3:
                viewAllPayments();
                break;
            case 4:
                manageUsers();
                break;
            case 5:
                changePassword();
                break;
            case 6:
                stay = false;
                break;
            case 7:
                logout();
                stay = false;
                break;
            case 8:
                quit();
            }
        }
        clearScreen();
    }

    public void login() {
        boolean stay = true;
        boolean loginFailed = false;
        if (!this.loggedIn) {
            while (stay) {
                clearScreen();
                infoMsg("Login to Gen and Eric's Car Dealership");
                System.out.println("\n");
                // flag for mismatched username / pass
                if (loginFailed) {
                    errorMsg("Login failed. Try again? Or press ^C to quit.");
                    System.out.println();
                    loginFailed = false;
                }
                System.out.printf("Username: ");
                String username = scan.nextLine();
                System.out.printf("Password: ");
                String password = scan.nextLine();
                if (handleCreds(username, password)) {
                    stay = false;
                } else {
                    loginFailed = true;
                }
            }
        }
    }

    public void register() {
        boolean stay = true;
        boolean registerFailed = false;
        boolean badPassword = false;
        while (stay) {
            clearScreen();
            infoMsg("Register for harmless hourly newsletters.");
            System.out.println();
            if (registerFailed) {
                errorMsg("Failed to register. Try making a more unique name.");
            } else if (badPassword) {
                errorMsg("Password entered does not match the guideline or does not match the confirmed password.");
            }
            System.out.printf("Username: ");
            String username = scan.nextLine();
            infoMsg("Passwords must be alphanumeric to preserve security. We care about your data.");
            System.out.printf("Password: ");
            String password = scan.nextLine();
            System.out.printf("Confirm Password: ");
            String confirmedPassword = scan.nextLine();

            if (password.equals(confirmedPassword) && validatePassword(password) && validateUsername(username)) {
                if (handleRegisterCreds(username, password)) {
                    successMsg("Successfully created user " + username + "! Have a wonderful time.");
                } else {
                    registerFailed = true;
                    continue;
                }
            } else {
                badPassword = true;
                continue;
            }
            stay = breakOutToMain();
        }
    }

    public void changePassword() {

        boolean stay = true;
        if (!this.loggedIn) {
            clearScreen();
            errorMsg("You must be logged in to change password.");
            stay = breakOutToMain();
            return;
        }

        boolean changePasswordFailed = false;
        while (stay) {
            clearScreen();
            infoMsg("Hello " + getUserInSession().getUsername() + ". Change your password to something surely more secure.");
            System.out.println();
            if (changePasswordFailed) {
                errorMsg("Passwords do not match or do not match the guidelines.");
            }

            System.out.println("Passwords must be alphanumeric to preserve security. We care about your data.");
            System.out.printf("Password: ");
            String password = scan.nextLine();
            System.out.printf("Confirm Password: ");
            String confirmedPassword = scan.nextLine();

            if (password.equals(confirmedPassword) && validatePassword(password)) {
                System.out.println("Attempting to change password for " + userInSession.getUsername());
                System.out.printf("Are you sure? (y)es/(n)o: ");
                String back = scan.nextLine();
                if (back.equals("yes") || back.equals("y")) {
                    if (handlePasswordChange(password)) {
                        successMsg("Successfully changed password for " + userInSession.getUsername());
                    }
                } else {
                    infoMsg("Ok, we will not submit this offer.");
                }
            } else {
                changePasswordFailed = true;
            }
            stay = breakOutToMain();
        }
    }

    public void makeAnOffer() {
        boolean stay = true;
        boolean inputFailed = false;
        List<Car> carsOnLot = CarService.getCarsOnLot();
        while (stay) {
            clearScreen();
            infoMsg("\nMake An Offer On A Car\n");
            if (carsOnLot.size() == 0) {
                errorMsg("No cars found on the lot!");
                stay = breakOutToMain();
                continue;
            }
            printCarsTable(carsOnLot);
            if (inputFailed) {
                errorMsg(
                        "\nManual input failed. Selected Option, down payment and months financed must both be whole numbers greater than 0.");
                inputFailed = false;
            }
            int carOption = captureUserOptions(carsOnLot.size());
            if (carOption >= carsOnLot.size() || carOption < 0) {
                inputFailed = true;
                continue;
            }
            Car car = carsOnLot.get(carOption);
            double downPayment = 0;
            int months = 0;
            try {
                System.out.printf("How much do you want to put for a down payment? ");
                downPayment = scan.nextDouble();
                scan.nextLine();
                System.out.printf("How many months do you want to finance for? ");
                months = scan.nextInt();
                scan.nextLine();

            } catch (InputMismatchException e) {
                inputFailed = true;
                continue;
            }

            if (downPayment > 0 && (months > 0 || downPayment >= car.getPrice())) {
                printConfirmOffer(car, months, downPayment);
            } else {
                inputFailed = true;
                continue;
            }
            stay = breakOutToMain();
        }
    }

    public void makeAPayment() {
        boolean stay = true;
        boolean inputFailed = false;
        boolean offerNotFound = false;
        boolean noMorePayments = false;
        List<Car> ownedCars = CarService.getOwnedCars(getUserInSession());

        while (stay) {
            clearScreen();
            infoMsg("Your Owned Cars");

            if (ownedCars.size() == 0) {
                errorMsg("No cars found! To own a car, try making an offer on a car.");
                stay = breakOutToMain();
                continue;
            }
            printCarsTable(ownedCars);
            if (inputFailed) {
                errorMsg("Hmm. It seems as though you entered an invalid option.");
                inputFailed = false;
            } else if (offerNotFound) {
                errorMsg("No suitable offer found for the model. We are currently assessing our offers.");
                offerNotFound = false;
            } else if (noMorePayments) {
                errorMsg("No more payments are needed for this car. Enjoy driving without Gen and Eric debt! :)");
                noMorePayments = false;
            }
            int carOption = captureUserOptions(ownedCars.size());
            Car car = ownedCars.get(carOption);
            List<Payment> payments = PaymentService.getPaymentsOnCarForUser(getUserInSession(), car);
            Payment payment = null;
            if (payments.size() == 0) {
                List<Offer> offers = OfferService.evaluateOffersForACar(car.getCid());
                Offer acceptedOffer = findAcceptedOffer(offers);
                // find accepted offer
                if (acceptedOffer != null) {
                    double amountPaid = acceptedOffer.getAmount();
                    int monthsLeft = acceptedOffer.getMonthsOffered();
                    payment = new Payment(amountPaid, monthsLeft, car.getCid(), getUserInSession().getUid());
                } else {
                    offerNotFound = true;
                    continue;
                }
            } else {
                // get an old payment and find the lowest months left value
                int minMonthsLeft = Integer.MAX_VALUE;
                for (Payment pay : payments) {
                    if (minMonthsLeft > pay.getMonthsLeft()) {
                        minMonthsLeft = pay.getMonthsLeft();
                    }
                }
                payment = payments.get(0);
                payment.setMonthsLeft(minMonthsLeft);
            }
            // decrement the months left field
            if (payment.getMonthsLeft() > 0) {
                printPreparePayment(payment, car);
            } else {
                noMorePayments = true;
                continue;
            }

            stay = breakOutToMain();
        }
    }

    public void viewCarsOnLot() {
        boolean stay = true;
        List<Car> carsOnLot = CarService.getCarsOnLot();

        while (stay) {
            clearScreen();
            infoMsg("Cars On The Lot");
            if (carsOnLot.size() > 0) {
                printCarsTable(carsOnLot);
            } else {
                errorMsg("No cars on the lot!");
            }
            stay = breakOutToMain();
        }
    }

    public void viewOwnedCars() {
        boolean stay = true;
        List<Car> ownedCars = CarService.getOwnedCars(getUserInSession());

        while (stay) {
            clearScreen();
            infoMsg("View Owned Cars");

            if (ownedCars.size() > 0) {
                printCarsTable(ownedCars);
            } else {
                errorMsg("No cars found! To own a car, try making an offer on a car.");
            }
            stay = breakOutToMain();
        }
    }

    public void viewRemainingPayments() {
        boolean inputFailed = false;
        boolean stay = true;
        List<Car> ownedCars = CarService.getOwnedCars(getUserInSession());

        while (stay) {
            clearScreen();
            infoMsg("Your Owned Cars");
            if (ownedCars.size() > 0) {
                printCarsTable(ownedCars);
                if (inputFailed) {
                    System.out.println("Hmm. It seems as though you entered an invalid option.");
                    inputFailed = false;
                }
                int carOption = captureUserOptions(ownedCars.size());
                if (carOption >= ownedCars.size() || carOption < 0) {
                    inputFailed = true;
                    continue;
                }
                Car car = ownedCars.get(carOption);
                infoMsg("Payments made on your " + car.getYear() + " " + car.getModel() + " model.");
                List<Payment> payments = PaymentService.viewAllPaymentsForCar(car.getCid());
                if (payments.size() > 0) {
                    printCustomPaymentsTable(payments, car);
                } else {
                    errorMsg("No payments have been made on this car yet.");
                }
            } else {
                errorMsg("No cars found! To own a car, try making an offer on a car.");
                errorMsg(
                        "Since we have no records of your owned cars in our database, we cannot pull up your payment records. We apologize for the inconvenience.");
            }
            stay = breakOutToMain();
        }
    }

    public void viewAllPayments() {
        boolean stay = true;
        List<Payment> allPayments = PaymentService.viewAllPayments();

        while (stay) {
            clearScreen();
            infoMsg("All Payments");

            if (allPayments.size() > 0) {
                printPaymentsTable(allPayments);
            } else {
                errorMsg("No payments found!");
            }
            stay = breakOutToMain();
        }
    }

    public void manageUsers() {
        boolean stay = true;
        boolean inputFailed = false;
        List<User> allUsers = UserService.getUsers();
        while (stay) {
            clearScreen();
            infoMsg("All Users");
            printUsersTable(allUsers);
            if (inputFailed) {
                errorMsg("Hmm. It seems as though you entered an invalid option.");
                inputFailed = false;
            }
            int userOption = captureUserOptions(allUsers.size());
            if (userOption >= allUsers.size() || userOption < 0) {
                inputFailed = true;
                continue;
            }
            User user = allUsers.get(userOption);
            System.out.println("What do you wish to do to this user? Your options are:");
            System.out.println("(m)odify");
            System.out.println("(r)emove");
            System.out.println("(g)ive car");
            System.out.println("(d)emand payment");
            String choice = "";
            try {
                choice = scan.nextLine();
            } catch (InputMismatchException e) {
                inputFailed = true;
                continue;
            }
            handleManageUserChoice(user, choice);
            successMsg("We hope you had a wonderful time doing tedious user management! Love, management.");

            stay = breakOutToMain();
        }
    }

    public void demandPayment(User user) {
        boolean stay = true;
        while (stay) {
            clearScreen();
            errorMsg("Hey " + user.getUsername() + " we need your payment!!! Now!!!");
            stay = breakOutToMain();
        }
    }

    public void giveCar(User user) {
        boolean stay = true;
        boolean inputFailed = false;
        while (stay) {
            clearScreen();
            infoMsg("Giving car to " + user.getUsername());
            System.out.println(Utils.getUserTableColumns());
            System.out.println("   " + user);

            System.out.println();
            infoMsg("Cars available");
            List<Car> carsOnLot = CarService.getCarsOnLot();
            if (carsOnLot.size() > 0) {
                printCarsTable(carsOnLot);
            } else {
                errorMsg("No cars are on the lot!");
                stay = breakOutToMain();
                continue;
            }
            if (inputFailed) {
                errorMsg("Hmm. It seems as though you entered an invalid string for the password or role.");
                inputFailed = false;
            }
            System.out.println("Which car do you want to transfer?");
            int carOption = captureUserOptions(carsOnLot.size());
            if (carOption >= carsOnLot.size() || carOption < 0) {
                inputFailed = true;
                continue;
            }
            handleTransferCar(carsOnLot, carOption, user);
            stay = breakOutToMain();
        }
    }

    public void modifyUser(User user) {
        boolean stay = true;
        boolean inputFailed = false;
        while (stay) {
            clearScreen();
            infoMsg("Modify " + user.getUsername());
            System.out.println(Utils.getUserTableColumns());
            System.out.println("   " + user);
            if (inputFailed) {
                errorMsg("Hmm. It seems as though you entered an invalid string for the password or role.");
                inputFailed = false;
            }
            String password, role;
            try {
                System.out.printf("Password: ");
                password = scan.nextLine();
                System.out.println("Role: ");
                role = scan.nextLine();
            } catch (InputMismatchException e) {
                inputFailed = true;
                continue;
            }
            if (validatePassword(password) && validatePassword(role)) {
                user.setPassword(password);
                user.setRole(role);
                System.out.println("Updating " + user.getUsername() + " with some sweet new fields.");
                System.out.printf("Are you sure? (y)es/(n)o: ");
                String back = scan.nextLine();
                if (back.equals("yes") || back.equals("y")) {
                    if (UserService.updateUser(user)) {
                        successMsg("Successfully updated " + user.getUsername() + " [" + user.getUid()
                                + "] to the database.");
                    } else {
                        errorMsg("Something went wrong! We will look into this error.");
                    }
                } else {
                    infoMsg("Ok, we will not modify this user.");
                }
            } else {
                inputFailed = true;
                continue;
            }
            stay = breakOutToMain();
        }
    }

    public void removeUser(User user) {
        boolean stay = true;
        while (stay) {
            clearScreen();
            infoMsg("Remove " + user.getUsername());
            System.out.println(Utils.getUserTableColumns());
            System.out.println("   " + user);

            System.out.println("Deleting " + user.getUsername() + " from the table.");
            System.out.printf("Are you sure? (y)es/(n)o: ");
            String back = scan.nextLine();
            if (back.equals("yes") || back.equals("y")) {
                if (UserService.removeUser(user.getUsername())) {
                    successMsg("Successfully deleted " + user.getUsername() + " [" + user.getUid()
                            + "] from the database.");
                } else {
                    errorMsg("Something went wrong! We will look into this error.");
                }
            } else {
                infoMsg("Ok, we will not delete this user.");
            }
            stay = breakOutToMain();
        }
    }

    public void evaluateOffers() {
        boolean stay = true;
        boolean inputFailed = false;
        List<Offer> allOffers = OfferService.evaluateOffers();
        while (stay) {
            clearScreen();
            infoMsg("\nAll Offers Available");
            System.out.println();
            if (allOffers.size() == 0) {
                errorMsg("No offers found!");
                stay = breakOutToMain();
                continue;
            } else {
                printOffersTable(allOffers);
            }
            if (inputFailed) {
                errorMsg("\nManual input failed. Selected Option must be a whole number greater than 0.");
                inputFailed = false;
            }
            int offerOption = captureUserOptions(allOffers.size());
            if (offerOption >= allOffers.size() || offerOption < 0) {
                inputFailed = true;
                continue;
            }
            Offer offer = allOffers.get(offerOption);
            System.out.println("How do you wish to evaluate this offer?");
            System.out.printf("Select an option: (a)ccept, (r)eject, (i)gnore, (u)nevaluate: ");
            String status = "unevaluated";
            String statusOption = "";
            try {
                statusOption = scan.nextLine();
            } catch (InputMismatchException e) {
                inputFailed = true;
                continue;
            }
            switch (statusOption) {
            case "a":
            case "accept":
                status = "accepted";
                break;
            case "r":
            case "reject":
                status = "rejected";
                break;
            case "u":
            case "unevaluate":
                status = "unevaluated";
                break;
            case "i":
            case "ignore":
            default:
                status = offer.getStatus();
            }
            offer.setStatus(status);
            System.out.println(
                    "Evaluating offer [" + offer.getOid() + "] and set status equal to " + offer.getStatus() + ".");
            System.out.printf("Are you sure? (y)es/(n)o: ");
            String back = scan.nextLine();
            if (back.equals("yes") || back.equals("y")) {
                if (OfferService.evaluateOffer(offer)) {
                    successMsg("Successfully evaluated offer number " + offer.getOid() + " as " + offer.getStatus()
                            + " to the database.");
                    if (offer.getStatus().equals("accepted")) {
                        // if the offer is accepted, transfer the car ownership to the new owner
                        Car transferCar = CarService.getCar(offer.getCid());
                        transferCar.setUid(offer.getUid());
                        transferCar.setLocation("garage");
                        CarService.updateCar(transferCar);
                        allOffers = OfferService.evaluateOffersForACar(transferCar.getCid());
                        // set the status of all other offers to be rejected
                        for (Offer off : allOffers) {
                            if (!off.getStatus().equals("accepted")) {
                                off.setStatus("rejected");
                                OfferService.evaluateOffer(off);
                            }
                        }
                        successMsg("Successfully transferred ownership of the " + transferCar.getModel() + " to "
                                + UserService.getUser(transferCar.getUid()).getUsername()
                                + ". All other offers on this vehicle have been rejected.");
                    }

                } else {
                    errorMsg("Something went wrong! We will look into this error.");
                }
            } else {
                infoMsg("Ok, we will not evaluate this offer.");
            }
            stay = breakOutToMain();
        }
    }

    public void printOffersTable(List<Offer> offers) {
        printEntityTable(offers, Utils.getOfferTableColumns());
    }

    public void addCarToLot() {
        boolean stay = true;
        boolean inputFailed = false;
        List<Car> carsOnLot = CarService.getCarsOnLot();
        while (stay) {
            clearScreen();
            infoMsg("Cars On The Lot");
            System.out.println(Utils.getCarTableColumns());
            if (carsOnLot.size() > 0) {
                printCarsTable(carsOnLot);
            } else {
                System.out.println("No cars on the lot!");
            }

            if (inputFailed) {
                System.out.println("Hmm. It seems as though you entered an invalid entry.");
                inputFailed = false;
            }

            System.out.println("Enter car details\n");
            String model = "";
            int year = 1970;
            double price = 0;
            try {
                System.out.printf("Model: ");
                model = scan.nextLine();
                System.out.printf("Year: ");
                year = scan.nextInt();
                scan.nextLine();
                System.out.printf("Price: ");
                price = scan.nextDouble();
                scan.nextLine();
            } catch (InputMismatchException e) {
                inputFailed = true;
                continue;
            }

            Car car = new Car(model, year, price, 0, "lot");
            System.out.println("Adding a new car to the lot: [" + car.getYear() + " " + car.getModel() + " for $"
                    + car.getPrice() + "].");
            System.out.printf("Are you sure? (y)es/(n)o: ");
            String back = scan.nextLine();
            if (back.equals("yes") || back.equals("y")) {
                if (CarService.addCarToLot(car)) {
                    successMsg("Successfully added " + car.getModel() + " [" + car.getCid() + "] to the database.");
                } else {
                    errorMsg("Something went wrong! We will look into this error.");
                }
            } else {
                infoMsg("Ok, we will not add this car to the lot.");
            }

            stay = breakOutToMain();
        }
    }

    public void removeCarFromLot() {
        boolean stay = true;
        boolean inputFailed = false;
        List<Car> carsOnLot = CarService.getCarsOnLot();
        while (stay) {
            clearScreen();
            infoMsg("Cars On The Lot");
            if (carsOnLot.size() > 0) {
                printCarsTable(carsOnLot);
            } else {
                errorMsg("No cars on the lot!");
                stay = breakOutToMain();
                continue;
            }
            if (inputFailed) {
                errorMsg(
                        "Hmm. It seems as though you entered an invalid option. Type in a whole number corresponding to a car.");
                inputFailed = false;
            }
            int carOption = captureUserOptions(carsOnLot.size());
            if (carOption >= carsOnLot.size() || carOption < 0) {
                inputFailed = true;
                continue;
            }
            Car car = carsOnLot.get(carOption);
            System.out.println("Deleting " + car.getModel() + " with c_id equal to " + car.getCid());
            System.out.printf("Are you sure? (y)es/(n)o: ");
            String back = scan.nextLine();
            if (back.equals("yes") || back.equals("y")) {
                if (CarService.removeCarFromLot(car)) {
                    successMsg("Successfully removed " + car.getModel() + " [" + car.getCid() + "] from the database.");
                } else {
                    errorMsg("Something went wrong! We will look into this error.");
                }
            } else {
                infoMsg("Ok, we will not delete this car.");
            }

            stay = breakOutToMain();
        }
    }

}
