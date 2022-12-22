import java.io.IOException;
import  java.util.Scanner;
public class Main {
    public static void main(String[] args) throws IOException {
        String calcResult;
        Scanner strInput = new Scanner(System.in);
        String input;
        do {
            System.out.println("Введите оператор и числа");
            input = strInput.nextLine();
            calcResult = calc(input);
            System.out.println(calcResult);
        } while (!"exit".equals(input));
        System.out.println("Вы молодец");
    }

    public static String calc(String input) {
        String result = "";
        int resultInt;
        String romanType = "roman";
        String arabicType = "arabic";
        String [] strings = input.split(" ");
        if (!isCorrectLength(strings)) {
            try {
                throw new IOException();
            }catch (IOException e) {
                System.err.println("Неправильный формат строки!");
                result = "невозможно";
            }
        } else {
            String typeOfNumber = isSameType(strings[0], strings[2]);
            if (typeOfNumber == arabicType) {
                int number1 = toNumber(strings[0]);
                int number2 = toNumber(strings[2]);
                String operator = strings[1];
                if (isCorrectValue(number1) && isCorrectValue(number2) && isValidOperator(operator)) {
                    resultInt = actualCalculating(number1, number2, operator);
                    result = String.valueOf(resultInt);
                } else {
                    try {
                        throw new IOException();
                    }catch (IOException e) {
                        System.err.println("Выход за пределы допустимых значений или недопустимый оператор!");
                        result = "невозможно";
                    }
                }
            } else if (typeOfNumber == romanType) {
                String ArabicStringNumber1 = romanToArabic(strings[0]);
                String ArabicStringNumber2 = romanToArabic(strings[2]);
                int number1 = toNumber(ArabicStringNumber1);
                int number2 = toNumber(ArabicStringNumber2);
                String operator = strings[1];
                if (isCorrectValue(number1) && isCorrectValue(number2) && isValidOperator(operator)) {
                    resultInt = actualCalculating(number1, number2, operator);
                    if (isRomanResultPositive(resultInt)) {
                        result = fromArabicToRoman(resultInt);
                    } else {
                        try {
                            throw new IOException();
                        }catch (IOException e) {
                            System.err.println("В римский цифрах ответ только позитивный!");
                            result = "невозможно";
                        }
                    }
                } else {
                    try {
                        throw new IOException();
                    }catch (IOException e) {
                        System.err.println("Выход за пределы допустимых значений или недопустимый оператор!");
                        result = "невозможно";
                    }
                }
            } else {
                result = "невозможен";
            }
        }
        return result;
    };

    static String fromArabicToRoman(int intResult) {
        var keys = new String[] { "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I" };
        var vals = new int[] { 100, 90, 50, 40, 10, 9, 5, 4, 1 };
        StringBuilder resultString = new StringBuilder();
        int i = 0;

        while (i < keys.length) {
            while (intResult >= vals[i]) {
                int res = intResult / vals[i];
                intResult = intResult % vals[i];
                for (int j = 0; j < res; j++ ) {
                    resultString.append(keys[i]);
                }
            }
            i++;
        }
        return resultString.toString();
    };
    static boolean isRomanResultPositive(int res) {
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    };
    static boolean isValidOperator(String operator) {
        if (operator.equals("+") || operator.equals("-") || operator.equals("*") || operator.equals("/")) {
            return true;
        } else {
            return false;
        }
    }
    static int actualCalculating(int num1, int num2, String operator ) {
        int calculatingResult = 0;
        switch(operator) {
            case "+":
                calculatingResult = num1 + num2;
                break;
            case "-":
                calculatingResult = num1 - num2;
                break;
            case "*":
                calculatingResult = num1 * num2;
                break;
            case "/":
                calculatingResult = num1 / num2;
                break;
        }
        return calculatingResult;
    };
    static boolean isCorrectValue(int num) {
        if (num >= 1 && num <= 10) {
            return true;
        } else {
            return false;
        }
    };
    static String isSameType(String str1, String str2 ) {
        String romanType = "roman";
        String arabicType = "arabic";
        String resultType = null;
        if (isNumber(str1) && isNumber(str2)) {
            resultType = arabicType;
        } else if (isRoman(str1) && isRoman(str2)) {
            resultType = romanType;
        } else {
            try {
                throw new IOException();
            }catch (IOException e) {
                System.err.println("Неправильный формат строки!");
            }
        }
        return resultType;
    };
    static boolean isRoman(String str) {
        try {
            RomanNumbers.valueOf(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    };
    static boolean isNumber(String str) {
        int num = 0;
        try {
            num = Integer.valueOf(str);
            return true;
        }catch (NumberFormatException e) {
            return false;
        }
    };
    static String romanToArabic(String str) {
        RomanNumbers roman = RomanNumbers.valueOf(str);
        String strArabic = "";
        try {
            strArabic = roman.getTranslation();
        } catch (IllegalArgumentException e) {
            System.err.println("Неправильный формат строки!");
        }
    return strArabic;
    };
    static int toNumber(String str) {
        int num = 0;
        try {
            num = Integer.valueOf(str);
        }catch (NumberFormatException e) {
            System.err.println("Неправильный формат строки!");
        }
        return num;
    };
    static boolean isCorrectLength(String[] str) {
        if (str.length == 3) {
            return true;
        } else {
            return false;
        }
    };
};