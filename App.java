import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;

class App {
	public static void start() {
		System.out.println("Здравствуйте, вас привествует Money Check");
		System.out.println("Это приложение создано для того, чтобы вы смогли смотреть \n все ваши доходы и расходы, тем самым регулировать все ваши финансы");
		System.out.println("Желаем приятного пользования :3\n");
	}

	public static void retryInfo() {
		System.out.println("Для ознакомления с функционалом, введите \"func\"");
		System.out.println("Для того чтобы узнать свой счет, введите \"status\"");
		System.out.println("Чтобы ознакомиться со всеми расходами и доходами, введите \"oper\"");
	}

	public static void workDataBase() {
		String path = ".";
		File dir = new File(path);
		File dataFolder = new File(dir, "Data");
		if (!dataFolder.exists()){
			dataFolder.mkdir();
		}
		File newFile = new File(dataFolder, "Database.txt");
		if(!newFile.exists()){
			try {
				newFile.createNewFile();
				System.out.println("Файл создан");
			} catch (IOException e){
				System.out.println("Ошибка создания файла" + e.getMessage());
			}
		}
	}

	public static int getCommand() {
		Scanner sc = new Scanner(System.in);

		System.out.println("\nДля того чтобы добавить доход, введите \"income\", а затем сумму");
		System.out.println("Для того чтобы добавить расходом, введите \"expend\", а затем сумму");
		System.out.println("Для того чтобы вернуться назад, введите \"back\"");
		String userAction = sc.nextLine();
		if (!userAction.equals("back")) {
			int userInt = sc.nextInt();
			System.out.println("Вы добавили " + (userAction.equals("income") ? "доход" : "расход") + " на сумму " + userInt);
			return userAction.equals("income") ? userInt : -userInt;
		} 
		return 0;
	}

	public static void getTotalCurrency(int userTotalCurrent) {
		System.out.println("Ваш счет состовляет: " + userTotalCurrent + " рублей");
	}

	public static void toOperation() {
		System.out.println("Все ваши операции:");
	}

	public static int conversion(String currentCurrency, String newCurrency, int currentTotal) {
		float dollars = 92.8f;
		float euro = 103.54f;
		return 52;
	}

	public static void main(String[] args) {
		int userTotalCurrent = 0;

		start();
		retryInfo();
		workDataBase();
		Scanner sc = new Scanner(System.in);
		boolean check = true;
		while(check) {
			String userText = sc.nextLine();
			switch (userText) {
    			case "func":
    				int result = getCommand();
    				userTotalCurrent += result;
        			break;
    			case "status":
    				getTotalCurrency(userTotalCurrent);
        			break;
    			case "oper":
    				toOperation();
        			break;
    			default: 
        			check = false;
        			break;
			}
		}
		
	}
}