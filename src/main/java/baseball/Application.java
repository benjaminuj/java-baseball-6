package baseball;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.ArrayList;
import java.util.List;

public class Application {
    static List<Integer> computerNumbers;
    static List<Integer> playerNumbers ;

    public static void main(String[] args) {
        Application application = new Application();

        System.out.println("숫자 야구 게임을 시작합니다.");

        while(true) {
            computerNumbers = new ArrayList<>();
            boolean isGameTermination = false;

            application.setComputerNumber();
            do {
                playerNumbers = new ArrayList<>();

                application.inputPlayerNumber();

                int strikeCount = application.calculateStrikeCount();
                int ballCount = application.calculateBallCount();
                boolean isNothing = application.checkNothing(strikeCount, ballCount);

                application.printResult(isNothing, strikeCount, ballCount);
                isGameTermination = application.checkGameTermination(strikeCount);
                if (isGameTermination) {
                    int choice = application.executeRestartOrTerminate();

                    if (choice == 1) {
                        isGameTermination = false;
                    }
                }
            } while(!isGameTermination);
        }
    }

    public void setComputerNumber() {
        while (computerNumbers.size() < 3) {
            int randomNumber = Randoms.pickNumberInRange(1, 9);

            if (!computerNumbers.contains(randomNumber)) {
                computerNumbers.add(randomNumber);
            }
        }
    }

    public void inputPlayerNumber() {
        System.out.print("숫자를 입력해주세요 : ");
        String input = Console.readLine();

        checkPlayerNumberValidity(input);

        for (int i = 0; i < 3; i++) {
            playerNumbers.add(input.charAt(i) - '0');
        }
    }

    public int calculateStrikeCount() {
        int strikeCount = 0;

        for (int i = 0; i < 3; i++) {
            if (computerNumbers.get(i) == playerNumbers.get(i)) {
                strikeCount++;
            }
        }

        return strikeCount;
    }

    public int calculateBallCount() {
        int ballCount = 0;

        for (int i = 0; i < 3; i++) {
            for (int k = 0; k < 3; k++) {
                if (i == k) {
                    continue;
                }

                if (computerNumbers.get(i) == playerNumbers.get(k)) {
                    ballCount++;
                }
            }
        }

        return ballCount;
    }

    public boolean checkNothing(int strikeCount, int ballCount) {
        if (strikeCount == 0 && ballCount == 0) {
            return true;
        }

        return false;
    }

    public void printResult(boolean isNothing, int strikeCount, int ballCount) {
        if (!isNothing) {
            if (ballCount > 0) {
                System.out.print(ballCount + "볼 ");
            }

            if (strikeCount > 0) {
                System.out.print(strikeCount + "스트라이크");
            }

            System.out.println();
        } else if (isNothing) {
            System.out.println("낫싱");
        }
    }

    public boolean checkGameTermination(int strikeCount) {
        if (strikeCount == 3) {
            System.out.println("3개의 숫자를 모두 맞히셨습니다! 게임 종료");
            return true;
        }

        return false;
    }

    public int executeRestartOrTerminate() {
        String choice = null;

        do {
            System.out.println("게임을 새로 시작하려면 1, 종료하려면 2를 입력하세요.");
            choice = Console.readLine();
        } while (!isGameControlInputValid(choice));

        if (choice.equals("2")) {
            System.out.println("프로그램을 종료합니다.");
            System.exit(0);
        }

        return 1;
    }

    public void checkPlayerNumberValidity(String input) {
        if (isStringEmpty(input) || input.length() != 3 || !hasUniqueElementsOnly(input)) {
            throw new IllegalArgumentException("Invalid input: Input must be numbers between 1 and 9 only.");
        }

        for (int i = 0; i < 3; i++) {
            if (!isNumberInRange(input.charAt(i) - '0', 1, 9)) {
               throw new IllegalArgumentException("Invalid input: Input must be numbers between 1 and 9 only.");
            }
        }
    }

    public boolean isStringEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public boolean hasUniqueElementsOnly(String string) {
        char target1 = string.charAt(0);
        char target2 = string.charAt(1);
        char target3 = string.charAt(2);

        if (target1 != target2 && target1 != target3 && target2 != target3) {
            return true;
        }

         return false;
    }

    public boolean isNumberInRange(int number, int start, int end) {
        return number >= start && number <= end;
    }

    public boolean isGameControlInputValid(String choice) {
        if (isStringEmpty(choice)) {
            System.out.println("값이 입력되지 않았습니다.");
            return false;
        }

        if (choice.length() > 1) {
            System.out.println("하나의 값만 입력할 수 있습니다.");
            return false;
        }

        if (!isNumberInRange(choice.charAt(0) - '0', 1, 2)) {
            System.out.println("숫자 1 또는 2만 입력할 수 있습니다.");
            return false;
        }

        return true;
    }
}
