import java.util.Scanner;
import java.util.Arrays;

class App {
	public static void start() {
		System.out.println("Здравствуйте, вас привествует Money Check");
		System.out.println("Это приложение создано для того, чтобы вы смогли смотреть все ваши доходы и расходы, тем самым регулировать все ваши финансы");
		System.out.println("Для ознакомления с функционалом, введите \"Действие\"");
		System.out.println("Для того чтобы узнать свой счет, введите \"Счет\"");
		System.out.println("Чтобы ознакомиться со всеми расходами и доходами, введите \"Операции\"");
		System.out.println("Желаем приятного пользования :3");
	}

	public static void getCommand() {
		System.out.println("Старт работы блока действия");
	}

	public static int getTotalCurrency() {
		System.out.println("Ваш счет состовляет: ");
		return 52;
	}

	public static int toOperation() {
		System.out.println("Все ваши операции:");
		return 52;
	}

	public static int conversion(String currentCurrency, String newCurrency, int currentTotal) {
		float dollars = 92.8f;
		float euro = 103.54f;
		return 52;
	}

	public static void main(String[] args) {
		start();
		Scanner sc = new Scanner(System.in);
		while(true) {
			String text = sc.nextLine();
			if (text.equals("Действие")) getCommand();
			else if (text.equals("Счет")) getTotalCurrency();
			else if (text.equals("Операции")) toOperation();
		}
		
	}
}