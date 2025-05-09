package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter f(x) in the form of X*X (not x^2): ");
        String function = scanner.nextLine();
        function = function.replace("X", "x"); // Normalize variable name

        System.out.println("Please enter the value of a:");
        double a = scanner.nextDouble();

        System.out.println("Please enter the value of b:");
        double b = scanner.nextDouble();

        System.out.println("Please enter the value of h:");
        double h = scanner.nextDouble();

        CompositeMidpoint(function, a, b, h);
        CompositeTrapezoidal(function, a, b, h);
        CompositeSimpson(function, a, b, h);
    }

    public static double evaluateFunction(String function, double x) {
        Expression exp = new ExpressionBuilder(function)
                .variable("x")
                .build()
                .setVariable("x", x);
        return exp.evaluate();
    }

    public static void CompositeMidpoint(String function, double a, double b, double h) {
        double sum = 0;
        double xStar = a + h / 2;

        System.out.printf("\n%-10s | %-10s\n", "x*", "f(x*)");
        System.out.println("--------------------------");

        while (xStar <= b) {
            double fx = evaluateFunction(function, xStar);
            sum += fx;

            // Print current midpoint
            System.out.printf("%-10.4f | %-10.4f\n", xStar, fx);

            xStar += h;
        }
        System.out.printf("\nApproximate integral using Composite Midpoint Rule = %.4f%n", sum*h);
    }

    public static void CompositeTrapezoidal(String function, double a, double b, double h) {
        double sum = 0;
        double xStar = a;
        double fa = evaluateFunction(function,a);
        double fb = evaluateFunction(function,b);

        System.out.printf("\n%-10s | %-10s\n", "x*", "f(x*)");
        System.out.println("--------------------------");

        System.out.printf("%-10.4f | %-10.4f\n", a, fa);

        // Interior points only
        for (double x = a + h; x < b; x += h) {
            double fx = evaluateFunction(function,x);
            sum += fx;
            System.out.printf("%-10.4f | %-10.4f\n", x, fx);
        }

        System.out.printf("%-10.4f | %-10.4f\n", b, fb);

        System.out.printf("\nApproximate integral using Composite Trapezoidal Rule = %.4f%n", (h / 2) * (fa + 2 * sum + fb));

    }

    public static void CompositeSimpson(String function, double a, double b, double h) {
        double sumOdd = 0;
        double sumEven = 0;
        double fa = evaluateFunction(function,a);
        double fb = evaluateFunction(function,b);

        System.out.printf("\n%-10s | %-10s\n", "x", "f(x)");
        System.out.println("--------------------------");
        System.out.printf("%-10.4f | %-10.4f\n", a, fa);

        int index = 1; // to track the index for odd/even handling
        for (double x = a + h; x < b; x += h) {
            double fx = evaluateFunction(function, x);

            if (index % 2 == 0) {
                sumEven += fx;
            }

            else {
                sumOdd += fx;
            }

            // Print the current x and f(x) for the table
            System.out.printf("%-10.4f | %-10.4f\n", x, fx);
            index++;
        }

        System.out.printf("%-10.4f | %-10.4f\n", b, fb);
        System.out.printf("\nApproximate integral using Composite Simpson's Rule = %.4f%n", ((h / 3) * (fa + 4 * sumOdd + 2 * sumEven + fb)));

    }

}
