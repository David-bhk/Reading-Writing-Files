import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

    public class TaskProgram {

        // Reads the tasks from the given file and returns an array of Strings
        public static String[] loadTasks(File file) {
            try {
                Scanner scanner = new Scanner(file);
                int numberOfTasks = scanner.nextInt();
                scanner.nextLine(); // Consume the newline after the number of tasks

                String[] tasks = new String[numberOfTasks];
                for (int i = 0; i < numberOfTasks; i++) {
                    tasks[i] = scanner.nextLine();
                }

                scanner.close();
                return tasks;
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Returning an empty array.");
                return new String[0];
            }

        }

        // Writes the array of tasks to the given file
        public static void saveTasks(File file, String[] tasks) {
            try {
                PrintWriter writer = new PrintWriter(file);
                writer.println(tasks.length);
                for (String task : tasks) {
                    writer.println(task);
                }
                writer.close();
                System.out.println("Tasks saved successfully.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Tasks not saved.");
            }
        }

        // Remove a task from the array
        public static String[] removeTask(String[] tasks, int indexToRemove) {
            if (indexToRemove >= 0 && indexToRemove < tasks.length) {
                String[] updatedTasks = new String[tasks.length - 1];
                int newIndex = 0;
                for (int i = 0; i < tasks.length; i++) {
                    if (i != indexToRemove) {
                        updatedTasks[newIndex++] = tasks[i];
                    }
                }
                return updatedTasks;
            } else {
                System.out.println("Invalid index. Task not removed.");
                return tasks;
            }
        }

        public static void main(String[] args) {
            File saveFile = new File("tasks.txt");
            String[] tasks = loadTasks(saveFile);

            System.out.println("Welcome to the Task Program!");
            System.out.println("I loaded " + tasks.length + " tasks:");

            for (int i = 0; i < tasks.length; i++) {
                System.out.println("* " + tasks[i]);
            }

            char addTaskChoice;

            Scanner scanner = new Scanner(System.in);

            do {
                System.out.print("Would you like to add a task (Y/N)? ");
                if (scanner.hasNext()) {
                    addTaskChoice = scanner.next().charAt(0);

                    if (addTaskChoice == 'Y' || addTaskChoice == 'y') {
                        scanner.nextLine(); // Consume the newline
                        System.out.print("> ");
                        String newTask = scanner.nextLine();

                        // Adding the new task to the array
                        tasks = Arrays.copyOf(tasks, tasks.length + 1);
                        tasks[tasks.length - 1] = newTask;
                    }
                } else {
                    addTaskChoice = 'N'; // No input, exit the loop
                }
            } while (addTaskChoice == 'Y' || addTaskChoice == 'y');

            // Save the updated tasks to the file
            saveTasks(saveFile, tasks);

            System.out.println("Goodbye!");

            char removeTaskChoice;

            do {
                System.out.print("Would you like to remove a task (Y/N)? ");
                if (scanner.hasNext()) {
                    removeTaskChoice = scanner.next().charAt(0);

                    if (removeTaskChoice == 'Y' || removeTaskChoice == 'y') {
                        System.out.println("Current Tasks:");
                        for (int i = 0; i < tasks.length; i++) {
                            System.out.println((i + 1) + ". " + tasks[i]);
                        }

                        System.out.print("Which task would you like to remove (or 0 to cancel): ");
                        if (scanner.hasNextInt()) {
                            int taskIndexToRemove = scanner.nextInt() - 1;

                            if (taskIndexToRemove >= 0 && taskIndexToRemove < tasks.length) {
                                tasks = removeTask(tasks, taskIndexToRemove);
                                System.out.println("Task removed successfully.");
                            } else {
                                System.out.println("Invalid index. Task not removed.");
                            }
                        } else {
                            System.out.println("Invalid input. Task not removed.");
                            scanner.next(); // Consume invalid input
                        }
                    }
                } else {
                    removeTaskChoice = 'N'; // No input, exit the loop
                }
            } while (removeTaskChoice == 'Y' || removeTaskChoice == 'y');

            scanner.close();
        }
    }

