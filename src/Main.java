import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println(calc(in.nextLine()));
    }

    public static String calc(String input)  {
        String[] operandsAndOperator = input.split(" ");
        Integer[] numbers;
        boolean flagRoman = false;

        try {

            checkStringInPlace(operandsAndOperator);
            checkLengthOperands(operandsAndOperator);
            if (!orRomanArabic(operandsAndOperator)) {
                numbers = convertToArabic(operandsAndOperator[0], operandsAndOperator[2]);
                checkUpToTen(numbers);
                flagRoman = true;
            } else {
                numbers = convertStringToNumbers(operandsAndOperator[0], operandsAndOperator[2]);
                checkUpToTen(numbers);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new RuntimeException("Error: Wrong example");
        }
        try {
            Integer result = resultTwoNumbersArabic( numbers[0], operandsAndOperator[1], numbers[1]);
            if (flagRoman) {
                checkPlusNumber(result);
                return convertToRoman(resultTwoNumbersArabic(numbers[0], operandsAndOperator[1], numbers[1]));
            }
            else
                return resultTwoNumbersArabic( numbers[0], operandsAndOperator[1], numbers[1]).toString();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        throw new RuntimeException("Error: Wrong example");
    }

    private static void checkUpToTen(Integer[] numbers) {
        if ((numbers[0] > 10) | (numbers[0] < 1) | (numbers[1] > 10) | (numbers[1] < 1))
            throw new RuntimeException("Числа должны быть от 1 до 10 включительно");
    }

    private static void checkLengthOperands(String[] operandsAndOperator) throws Exception {
        if(operandsAndOperator.length > 3)
            throw new Exception("throws Exception //т.к. формат математической операции не удовлетворяет заданию - " +
                    "два операнда и один оператор (+, -, /, *)");
    }

    private static void checkStringInPlace(String[] operandsAndOperator) throws Exception {
        if(operandsAndOperator.length <= 2)
            throw new Exception("throws Exception //т.к. строка не является математической операцией");
    }

    private static void checkPlusNumber(Integer result) throws Exception {
        if(result < 0) throw new Exception("throws Exception //т.к. в римской системе нет отрицательных чисел");
    }

    private static String convertToRoman(Integer resultTwoNumbersArabic) {
        StringBuilder resultString = new StringBuilder();
        ArrayList<String> resultArray = new ArrayList<>();
        while (resultTwoNumbersArabic > 0) {
            if ((resultTwoNumbersArabic - 10) >= 0) {
                resultArray.add("X");
                resultTwoNumbersArabic -= 10;
                continue;
            }
            if ((resultTwoNumbersArabic - 5) >= 0) {
                resultArray.add("V");
                resultTwoNumbersArabic -= 5;
                continue;
            }
            if ((resultTwoNumbersArabic - 1) >= 0) {
                resultArray.add("I");
                resultTwoNumbersArabic -= 1;
                continue;
            }
        }
        for (String s : resultArray)
            resultString.append(s);
        return String.valueOf(resultString);
    }

    private static Integer[] convertStringToNumbers(String firstNumber, String secondNumber) {
        try {
            return new Integer[]{Integer.parseInt(firstNumber), Integer.parseInt(secondNumber)};
        } catch (Exception e) {
            throw new RuntimeException("Incorrect numbers");
        }
    }

    private static Integer[] convertToArabic(String firstNumber, String secondNumber) {
        Integer first = convertToArabic(firstNumber);
        Integer second = convertToArabic(secondNumber);
        return new Integer[]{first, second};
    }

    private static Integer convertToArabic(String number) {
        char[] arrayArabicSymbol = number.toCharArray();
        ArrayList<Integer> arrayRoman = new ArrayList<>();
        int result = 0;

        for (char c : arrayArabicSymbol) {
            switch (c) {
                case 'I' -> arrayRoman.add(1);
                case 'V' -> arrayRoman.add(5);
                case 'X' -> arrayRoman.add(10);
                default -> throw new RuntimeException("throws Exception //т.к. используются одновременно разные системы счисления");
            }
        }
        for (int i = 0; i < arrayRoman.size() - 1; i++) {
            if (arrayRoman.get(i) >= arrayRoman.get(i + 1)) {
                result += arrayRoman.get(i);
            } else {
                result -= arrayRoman.get(i);
            }
        }
        return result + arrayRoman.get(arrayRoman.size() - 1);
    }

    private static boolean orRomanArabic(String[] operandAndOperator) {
        return !(operandAndOperator[0].contains("I") |
                operandAndOperator[0].contains("V") |
                operandAndOperator[0].contains("X"));
    }

    private static Integer resultTwoNumbersArabic(Integer firstOperand, String operator, Integer secondOperand) {
        switch (operator) {
            case "+" -> {
                return (firstOperand + secondOperand);
            }
            case "-" -> {
                return (firstOperand - secondOperand);
            }
            case "*" -> {
                return (firstOperand * secondOperand);
            }
            case "/" -> {
                return (firstOperand / secondOperand);
            }
        }
        throw new RuntimeException("Illegal operator");
    }
}