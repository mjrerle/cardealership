package com.proj0.driver;

public final class Utils {
    private static final String carTableFormat = "   |%1$-5s|%2$-20s|%3$-5s|%4$-10s|%5$-5s|%6$-10s|";
    private static final String offerTableFormat = "   |%1$-5s|%2$-10s|%3$-20s|%4$-20s|%5$-20s|%6$-5s|%7$-5s|";
    private static final String paymentTableFormat = "   |%1$-5s|%2$-15s|%3$-20s|%4$-5s|%5$-5s|";
    private static final String userTableFormat = "   |%1$-20s|%2$-20s|%3$-20s|%4$-5s|";

    private static final String userTableColumns = String.format(Utils.userTableFormat, "username", "password", "role",
            "u_id");
    private static final String carTableColumns = String.format(Utils.carTableFormat, "c_id", "model", "year", "price",
            "u_id", "location");
    private static final String offerTableColumns = String.format(Utils.offerTableFormat, "o_id", "amount",
            "months_offered", "status", "down_payment", "u_id", "c_id");

    private static final String paymentTableColumns = String.format(Utils.paymentTableFormat, "p_id", "amount_paid",
            "months_left", "c_id", "u_id");
    private static final String foregroundAnsi = "\033[38;2;<r>;<g>;<b>m ";
    private static final String backgroundAnsi = "\033[48;2;<r>;<g>;<b>m";

    public static String replacePlaceholders(String ansiEscapeCode, int red, int green, int blue) {
        return ansiEscapeCode.replaceAll("<r>", Integer.toString(red)).replaceAll("<g>", Integer.toString(green))
        .replaceAll("<b>", Integer.toString(blue));
    }

    private static String convertRgbFG(int red, int green, int blue) {
        return replacePlaceholders(foregroundAnsi, red, green, blue);
    }

    private static String convertRgbBG(int red, int green, int blue) {
        return replacePlaceholders(backgroundAnsi, red, green, blue);
    }

    public static String colorStringRed(String string) {
        return convertRgbFG(255, 0, 0) + string + convertRgbFG(0, 0, 0);
    }

    public static String colorStringBlue(String string) {
        return convertRgbFG(0, 0, 255) + string + convertRgbFG(0, 0, 0);
    }

    public static String colorStringGreen(String string) {
        return convertRgbFG(0, 0, 255) + string + convertRgbFG(0, 0, 0);
    }

    public static String colorStringBlack(String string) {
        return convertRgbFG(0, 0, 0) + string + convertRgbFG(0, 0, 0);
    }

    public static String colorBackgroundRed(String string) {
        return convertRgbBG(255, 0, 0) + string;
    }

    public static String colorBackgroundBlue(String string) {
        return convertRgbBG(0, 0, 255) + string;
    }

    public static String colorBackgroundGreen(String string) {
        return convertRgbBG(0, 0, 255) + string;
    }

    public static String colorBackgroundWhite() {
        return convertRgbBG(255, 255, 255);
    }

    public static String colorStringBlack() {
        return convertRgbFG(0, 0, 0);
    }

    private static String getNDashes(int length) {
        StringBuffer outputBuffer = new StringBuffer(length);
        for (int i = 0; i < length; i++) {
            outputBuffer.append("-");
        }
        return outputBuffer.toString();
    }

    public static String getCarTableColumns() {

        return carTableColumns + "\n" + getNDashes(carTableColumns.length());
    }

    public static String getOfferTableColumns() {
        return offerTableColumns + "\n" + getNDashes(offerTableColumns.length());
    }

    public static String getPaymentTableColumns() {
        return paymentTableColumns + "\n" + getNDashes(paymentTableColumns.length());
    }

    public static String getUserTableColumns() {
        return userTableColumns + "\n" + getNDashes(userTableColumns.length());
    }
}
