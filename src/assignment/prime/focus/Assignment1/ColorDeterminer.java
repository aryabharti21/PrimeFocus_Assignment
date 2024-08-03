package assignment.prime.focus.Assignment1;

import java.util.Scanner;

/*
The task is to print the color based on the 2 coordinate input X and Y. According to image given, I have defined the color rages based on the
Y axis as it is determining the color. Taking the i/o from the user and checking the color range and deciding the color and returing the o/p
*/


public class ColorDeterminer {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter X coordinate (40-100): ");
        int x = scanner.nextInt();

        System.out.print("Enter Y coordinate (70-190): ");
        int y = scanner.nextInt();

        String color = determineColor(x, y);
        System.out.println("The color for coordinates (" + x + ", " + y + ") is: " + color);
        scanner.close();
    }

    public static String determineColor(int x, int y) {

        if (x < 40 || x > 100 || y < 70 || y > 190) {
            return "Out of bounds";
        }

        if (y > 140) {
            return "RED";
        } else if (y > 120) {
            return "YELLOW";
        } else if (y > 90) {
            return "GREEN";
        } else {
            return "BLUE";
        }
    }
}
