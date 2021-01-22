package converter;

import java.util.*;

public class Converter {
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            String sourceRadixStr = sc.next();
            String num = sc.next().toLowerCase();
            String targetRadixStr = sc.next();
            String intPart;
            String fractPart = "";
            if (checkInputFormat(sourceRadixStr, num, targetRadixStr)) {
                int sourceRadix = Integer.parseInt(sourceRadixStr);
                int targetRadix = Integer.parseInt(targetRadixStr);
                String[] splitNum = num.split("[.,]");
                intPart = convertIntegerPart(sourceRadix, splitNum[0], targetRadix);
                if (splitNum.length > 1) {
                    fractPart = convertFractionalPart(sourceRadix, splitNum[1], targetRadix);
                }
                System.out.println(intPart + fractPart);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Error: not enough arguments");
        }
    }

    static boolean checkInputFormat(String sourceRadixStr, String num, String targetRadixStr) {
        boolean isValid = true;
        try {
            int sourceRadix = Integer.parseInt(sourceRadixStr);
            int targetRadix = Integer.parseInt(targetRadixStr);
            if (sourceRadix < 1 || sourceRadix > 36 || targetRadix < 1 || targetRadix > 36) {
                System.out.println("Error: radix should be from 1 to 36");
                isValid = false;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid radix");
            isValid = false;
        }

        if (!num.matches("[a-z0-9]+[.,]?[a-z0-9]*")) {
            System.out.println("Error: invalid number");
            isValid = false;
        }
        return isValid;
    }

    static String convertIntegerPart(int sourceRadix, String num, int targetRadix) {
        String convertedNum = "";
        try {
            if (sourceRadix == 1 && targetRadix == 1) {
                convertedNum = num;
            } else if (sourceRadix == 1) {
                convertedNum = Long.toString(num.length(), targetRadix);
            } else if (targetRadix == 1) {
                convertedNum = "1".repeat((int) Long.parseLong(num, sourceRadix));
            } else {
                convertedNum = Long.toString(Long.parseLong(num, sourceRadix), targetRadix);
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: invalid number");
        }
        return convertedNum;
    }

    static String convertFractionalPart(int sourceRadix, String num, int targetRadix) {
        StringBuilder convertedNum = new StringBuilder(".");
        double dec_val = 0;
        int digit;
        // convert source base to decimal
        for (int i = 0; i < num.length(); i++) {
            digit = num.charAt(i) <= 57 ? num.charAt(i) - 48 : num.charAt(i) - 87;
            dec_val += digit / Math.pow(sourceRadix, i + 1);
        }
        // convert decimal to target base
        for (int i = 0; i < 5; i++) {
            digit = (int) (dec_val * targetRadix);
            convertedNum.append(digit < 10 ? (char) (digit + 48) : (char) (digit + 87));
            dec_val = (dec_val * targetRadix) - digit;
        }
        return convertedNum.toString();
    }
}
