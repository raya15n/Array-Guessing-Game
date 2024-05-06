
/**@author Ryan Getz
 * An Array Guessing game 
 * 5/2/2024
 */

import java.util.*;

public class Game {
    // Fields
    private static int points = 10;
    private static int gamesPlayed = 0;

    // Constants
    private static final int SMALL_COST = 1;
    private static final int LARGE_COST = 4;
    private static final int SMALL_GAIN = 3;
    private static final int LARGE_GAIN = 14;
    private static final int ELEMENTS = 30;
    private static final int RANGE = 80;

    // Constructor calls
    public static Random rg = new Random();
    public static Scanner sc = new Scanner(System.in);

    // Main menu
    public static void main(String[] args) {
        while (true) {
            if (points < 1) {
                System.out.println("Not enough points to continue. GAME OVER");
                break;
            }
            int selection;
            System.out.println(
                    "\nChoose your game! Enter 1 for a small game, 2 for a large game, 3 to exit the main menu!");
            System.out.println(
                    "Keep in mind that a small game costs 1 point and a large game costs 4 points. \nYou currently have "
                            + points + " points");
            selection = getValidEntry("Select game: ");

            if (selection == 3) {
                System.out.println("Thank you for playing, aborting the game...");
                break;
            }

            else if (selection == 1) {
                if (checkSmallPoints(points)) {
                    points -= SMALL_COST;
                    System.out.println("Predict! Will the max mean value occur in the beginning " +
                            "(type 1), middle (type 2) or end (type 3) of the array?");
                    int predict = getValidEntry("Guess here: ");
                    boolean guess = smallGame(predict);
                    if (guess == true) {
                        System.out.println("Congratulations, you guessed correctly!");
                        points += SMALL_GAIN;
                    } else {
                        System.out.println("Incorrect, you lost a point.");
                    }
                    gamesPlayed++;
                } else {
                    System.out.println("Insufficient number of points, returning to the main menu...");
                }
            }

            else if (selection == 2) {
                if (checkLargePoints(points)) {
                    points -= LARGE_COST;
                    System.out.println("Predict! Will the max mean value occur in the first (type 1), " +
                            "second (type 2), third (type 3), fourth (type 4), fifth (type 5), " +
                            "or sixth partition of the array?");
                    int predict = getValidLargeEntry("Guess here: ");
                    boolean guess = largeGame(predict);
                    if (guess == true) {
                        System.out.println("Congratulations, you guessed correctly!");
                        points += LARGE_GAIN;
                    } else {
                        System.out.println("Incorrect, you lose four points.");
                    }
                    gamesPlayed++;
                } else {
                    System.out.println("Insufficient number of points, returning to the main menu...");
                }
            }
        }
        System.out.println("Total Score: " + calcScore(gamesPlayed, points));
    }

    // Calulates the total score given by the formula S = 5g + 2p
    /**
     * @param G games played as an integer
     * @param P total points accululated
     * @return score the total score
     */
    public static int calcScore(int G, int P) {
        int score = 5 * G + 2 * P;
        return score;
    }

    // Large game script. Checks if a predicted range of values in an array holds
    // the greatest mean.
    /**
     * @param prediction user inputted prediction corresponding to range within the
     *                   array
     * @return guessed true if the prediction guessed the highest mean, false
     *         otherwise
     */
    public static boolean largeGame(int prediction) {
        boolean guessed = false;
        int[] arr = new int[ELEMENTS];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rg.nextInt(RANGE) + 1;
        }
        double meanFirst = calcMean(arr, 0, arr.length / 6);
        double meanSecond = calcMean(arr, arr.length / 6, (arr.length / 6) * 2);
        double meanThird = calcMean(arr, (arr.length / 6) * 2, (arr.length / 6) * 3);
        double meanFourth = calcMean(arr, (arr.length / 6) * 3, (arr.length / 6) * 4);
        double meanFifth = calcMean(arr, (arr.length / 6) * 4, (arr.length / 6) * 5);
        double meanSixth = calcMean(arr, (arr.length / 6) * 5, arr.length);

        if ((prediction == 1 && (meanFirst > meanSecond && meanFirst > meanThird &&
                meanFirst > meanFourth && meanFirst > meanFifth && meanFirst > meanSixth)) ||
                (prediction == 2 && (meanSecond > meanFirst && meanSecond > meanThird &&
                        meanSecond > meanFourth && meanSecond > meanFifth && meanSecond > meanSixth))
                ||
                (prediction == 3 && (meanThird > meanFirst && meanThird > meanSecond &&
                        meanThird > meanFourth && meanThird > meanFifth && meanThird > meanSixth))
                ||
                (prediction == 4 && (meanFourth > meanFirst && meanFourth > meanSecond &&
                        meanFourth > meanThird && meanFourth > meanFifth && meanFourth > meanSixth))
                ||
                (prediction == 5 && (meanFifth > meanFirst && meanFifth > meanSecond &&
                        meanFifth > meanThird && meanFifth > meanFourth && meanFifth > meanSixth))
                ||
                (prediction == 6 && (meanSixth > meanFirst && meanSixth > meanSecond &&
                        meanSixth > meanThird && meanSixth > meanFourth && meanSixth > meanFifth))) {
            guessed = true;
        }
        return guessed;
    }

    // Large game script. Checks if a predicted range of values in an array holds
    // the greatest mean.
    /**
     * @param prediction user inputted prediction corresponding to range within the
     *                   array
     * @return guessed true if the prediction guessed the highest mean, false
     *         otherwise
     */
    public static boolean smallGame(int prediction) {
        boolean guessed = false;
        int[] arr = new int[ELEMENTS];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = rg.nextInt(RANGE) + 1;
        }
        double meanBeg = calcMean(arr, 0, arr.length / 3);
        double meanMid = calcMean(arr, arr.length / 3, (arr.length / 3) * 2);
        double meanLar = calcMean(arr, (arr.length / 3) * 2, arr.length);

        if ((prediction == 1 && (meanBeg > meanMid && meanBeg > meanLar)) ||
                prediction == 2 && (meanMid > meanBeg && meanMid > meanLar) ||
                prediction == 3 && (meanLar > meanBeg && meanLar > meanMid)) {
            guessed = true;
        }
        return guessed;
    }

    // Calculates mean value of some partition of an array.
    /**
     * @param array the array being analyzed
     * @param start the start of the partition
     * @param end   the end of the partition
     * @return the mean of the elements within the given array partition.
     */
    public static double calcMean(int[] array, int start, int end) {
        double sum = 0;
        for (int i = start; i < end; i++) {
            sum += array[i];
        }
        return sum / (end - start);
    }

    // Checks for sufficient point balance
    /**
     * @param points current point balance
     * @return sufficientPoints true if the player's points are sufficient to pay
     *         for the small game, false otherwise.
     */
    public static boolean checkSmallPoints(int points) {
        boolean sufficientPoints = true;
        if (points < SMALL_COST) {
            sufficientPoints = false;
        }
        return sufficientPoints;
    }

    // Checks for sufficient point balance
    /**
     * @param points current point balance
     * @return sufficientPoints true if the player's points are sufficient to pay
     *         for the large game, false otherwise.
     */
    public static boolean checkLargePoints(int points) {
        boolean sufficientPoints = true;
        if (points < LARGE_COST) {
            sufficientPoints = false;
        }
        return sufficientPoints;
    }

    // Checks for valid user entry
    /**
     * @param prompt a message to encourage user input
     * @return entry a valid user inputted integer 1-3
     */
    public static int getValidEntry(String prompt) {
        int entry;
        do {
            System.out.print(prompt);
            while (!sc.hasNextInt()) {
                System.out.print("Your entry is invalid, please try again: ");
                sc.next();
            }
            entry = sc.nextInt();
        } while (!(entry == 1 || entry == 2 || entry == 3));
        return entry;
    }

    // Checks for valid user entry
    /**
     * @param prompt a message to encourage user input
     * @return entry a valid user inputted integer 1-6
     */
    public static int getValidLargeEntry(String prompt) {
        int entry;
        do {
            System.out.print(prompt);
            while (!sc.hasNextInt()) {
                System.out.print("Your entry is invalid, please try again: ");
                sc.next();
            }
            entry = sc.nextInt();
        } while (!(entry >= 1 && entry <= 6));
        return entry;
    }
}
