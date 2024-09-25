import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

class App {
    public static void start() { // Correct
        System.out.println("Здравствуйте, вас привествует Money Check");
        System.out.println("Это приложение создано для того, чтобы вы смогли смотреть \n все ваши доходы и расходы, тем самым регулировать все ваши финансы");
        System.out.println("Желаем приятного пользования :3\n");
    }

    public static void retryInfo() { // Correct
        System.out.println("\n\"func\" - сделать операцию, \"status\" - просмотреть счет, \"oper\" - вывести все операции\n");
    }

    public static String getDataBase() { // Correct
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
                System.out.println("Ошибка создания файла: " + e.getMessage());
            }
        }

        try(InputStream is = new FileInputStream(newFile)) {
            byte[] bytes = new byte[(int) newFile.length()];
            int len = is.read(bytes);
            return new String(bytes, 0, len);

        } catch (FileNotFoundException e) {
            return("File не существует");
        } catch (IOException e){
            return("Ошибка чтения файла: " + e.getMessage());
        }
    }

    public static void writeDataBase(int result) { // Correct
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
                System.out.println("Ошибка создания файла: " + e.getMessage());
            }
        }
		// Запись новой операции в файл
        try(OutputStream os = new FileOutputStream(newFile, true)) {
        	if (result != 0){
        		String newOperation = "\n" + java.time.LocalDate.now() + " " + (result > 0 ? "Доход" : "Расход") + " " + Math.abs(result);
        		os.write(newOperation.getBytes());
        	}  
        } catch (FileNotFoundException e) {
            System.out.println("Файл не существует");
        } catch (IOException e){
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    public static int getCommand() { // Correct
        Scanner sc = new Scanner(System.in);

        System.out.println("\nДля того чтобы добавить доход, введите \"income\", а затем сумму");
        System.out.println("Для того чтобы добавить расход, введите \"expend\", а затем сумму");
        System.out.println("Для того чтобы вернуться назад, введите \"back\"");
        String userAction = sc.nextLine();
        if (!userAction.equals("back")) {
            int userInt = sc.nextInt();
            sc.nextLine(); // Чтобы прочитать остаток строки и избежать проблем с последующим вводом
            System.out.println("Вы добавили " + (userAction.equals("income") ? "доход" : "расход") + " на сумму " + userInt);
            return userAction.equals("income") ? userInt : -userInt;
        } 
        return 0;
    }

    public static void getTotalCurrency(int userTotalCurrent) { // Correct
        System.out.println("\nВаш счет составляет: " + userTotalCurrent + " рублей");
    }

    public static int checkCurrense() { // Correct
    	int userTotalCurrent = 0;

        String str = getDataBase();
        String[] words = str.split("\n");
        
        for (String word : words) {
            if (word.trim().isEmpty()) {
                continue;
            }

            String[] datas = word.split(" ");
            if (datas.length < 3) {
                System.out.println("Неверный формат строки: " + word);
                continue;
            }

            try {
                Integer cash = Integer.valueOf(datas[2]);
                if (datas[1].equals("Доход")) {
                    userTotalCurrent += cash;
                } else if (datas[1].equals("Расход")) {
                    userTotalCurrent -= cash;
                } else {
                    System.out.println("Неизвестный тип операции: " + datas[1]);
                }
                // System.out.println("Добавлен " + datas[1] + ": " + cash);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка в конвертации суммы в строке: " + word);
            }
        }
        return userTotalCurrent;
    }

    public static void main(String[] args) {

		int userTotalCurrent = checkCurrense();
        start();
        Scanner sc = new Scanner(System.in);
        boolean check = true;

        while (check) {
        	retryInfo();
            String userText = sc.nextLine();
            switch (userText) {
                case "func":
                    int result = getCommand();
                    userTotalCurrent += result;
                    writeDataBase(result);
                    break;
                case "status":
                    getTotalCurrency(userTotalCurrent);
                    break;
                case "oper":
                	System.out.println("\nВсе ваши операции:");
                    System.out.println(getDataBase());
                    break;
                default:
                    check = false;
                    break;
            }
        }
    }
}