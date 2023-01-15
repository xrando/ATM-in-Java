import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        int LanguageChoice;
        //select language
        do {
            System.out.println("Select language: 1) English     2) Chinese");
            LanguageChoice = input.nextInt();
        } while (LanguageChoice < 1 || LanguageChoice > 2);

        DisplayText DT = new DisplayText(LanguageChoice);

        System.out.println(DT.getText(3));

        LogManager.LOGGER.info("hihi");

        input.close();
    }
}