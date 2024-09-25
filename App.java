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

    public static String getDataBase() {
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

    public static void writeDataBase() {
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

        try(OutputStream os = new FileOutputStream(newFile, true)) {
        	//
        	//
        	//

            String newData = "\n26.09.2024 Расход 2000";
            os.write(newData.getBytes());
        } catch (FileNotFoundException e) {
            System.out.println("Файл не существует");
        } catch (IOException e){
            System.out.println("Ошибка записи файла: " + e.getMessage());
        }
    }

    public static int getCommand() {
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

    public static void getTotalCurrency(int userTotalCurrent) {
        System.out.println("Ваш счет составляет: " + userTotalCurrent + " рублей");
    }

    public static void main(String[] args) {
        int userTotalCurrent = 0;

        String str = getDataBase();
        System.out.println("Содержимое файла:");
        System.out.println(str);
        String[] words = str.split("\n");
        
        for (String word : words) {
            if (word.trim().isEmpty()) {
                continue; // Пропустить пустые строки
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
                System.out.println("Добавлен " + datas[1] + ": " + cash);
            } catch (NumberFormatException e) {
                System.out.println("Ошибка в конвертации суммы в строке: " + word);
            }
        }

        System.out.println("Общий счет: " + userTotalCurrent);

        start();
        retryInfo();
        Scanner sc = new Scanner(System.in);
        boolean check = true;
        while (check) {
            String userText = sc.nextLine();
            switch (userText) {
                case "func":
                    int result = getCommand();
                    userTotalCurrent += result;
                    // Запись новой операции в файл
                    try (OutputStream os = new FileOutputStream("./Data/Database.txt", true)) {
                        String newOperation = "\n" + java.time.LocalDate.now() + " " + (result > 0 ? "Доход" : "Расход") + " " + Math.abs(result);
                        os.write(newOperation.getBytes());
                    } catch (IOException e) {
                        System.out.println("Ошибка записи файла: " + e.getMessage());
                    }
                    break;
                case "status":
                    getTotalCurrency(userTotalCurrent);
                    break;
                case "oper":
                    System.out.println(getDataBase());
                    break;
                default:
                    check = false;
                    break;
            }
        }
    }
}