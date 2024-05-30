/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package usernameandpassword;

/**
 *
 * @author lab_services_student
 */
import java.util.Scanner;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class UserNameAndPassword 
{

    // Method to validate the username
    static boolean isValidUserName(String userName) 
    {
        // Check if the username is less than or equal to 5 characters
        // and contains an underscore
        return userName.length() <= 5 && userName.contains("_");
    }

    // Method to validate the password
    static boolean isValidPassword(String password) 
    {
        boolean hasUppercase = false; // Flag to indicate if the password has an uppercase letter
        boolean hasNumber = false;   // Flag to indicate if the password has a number
        boolean hasSpecial = false;  // Flag to indicate if the password has a special character

        // Loop through each character in the password
        for (char c : password.toCharArray()) 
        {
            if (Character.isUpperCase(c)) 
            {
                hasUppercase = true;
            } else if (Character.isDigit(c)) 
            {
                hasNumber = true;
            } else if (!Character.isLetterOrDigit(c)) 
            {
                hasSpecial = true;
            }
        }

        // Check if the password is less than or equal to 8 characters
        // and has at least one uppercase letter, one number, and one special character
        return password.length() <= 8 && hasUppercase && hasNumber && hasSpecial;
    }

    // Method to validate the entered username and password against stored credentials
    static boolean loginUser(String userName, String password, String storedUser, String storedPass) 
    {
        return userName.equals(storedUser) && password.equals(storedPass);
    }

    // Constants for minimum username and password lengths and maximum task description length
    private static final int MIN_USER_LENGTH = 5;
    private static final int MIN_PASS_LENGTH = 8;
    private static final int MAX_TASK_DESC_LENGTH = 50;

    // Static variables for task management
    static int taskNumber = 0; // Auto-incrementing task ID counter
    static List<Task> tasks = new ArrayList<>(); // List to store tasks

    // Main method
    public static void main(String[] args) 
    {
        String userName, password;
        String firstName, lastName;
        String storedUser, storedPass;

        Scanner input = new Scanner(System.in);

        // Username input with validation
        System.out.print("Enter Username must be less than 5 characters with an underscore: ");
        userName = input.nextLine();
        while (!isValidUserName(userName)) 
        {
            System.out.println("Invalid Username. Please try again.");
            System.out.println("Enter Username: ");
            userName = input.nextLine();
        }
        System.out.println("Username successfully captured");

        // Password input with validation
        System.out.print("Enter Password must be at least 8 characters with a capital letter, number, and special character: ");
        password = input.nextLine();
        while (!isValidPassword(password)) 
        {
            System.out.println("Invalid Password. Please try again.");
            System.out.println("Enter Password: ");
            password = input.nextLine();
        }
        System.out.println("Password successfully captured");

        // User details input
        System.out.println("Enter first Name:");
        firstName = input.nextLine();
        System.out.println("Enter Last Name:");
        lastName = input.nextLine();

        storedUser = userName;
        storedPass = password;

        boolean logInSuccesful = false;
        while (!logInSuccesful) 
        {
            System.out.println("Log in details");
            System.out.println("Re-enter User Name:");
            userName = input.nextLine();
            System.out.println("Re-nter password:");
            password = input.nextLine();
            logInSuccesful = loginUser(userName, password, storedUser, storedPass);
            if (logInSuccesful) 
            {
                System.out.println("Log in successful");
                showMenu(); // Show menu after successful login
            } 
            else 
            {
                System.out.println("Log in unsuccessful. Please try again");
            }
        }
    }

    // Method to display the main menu
    private static void showMenu() 
    {
        JOptionPane.showMessageDialog(null, "Welcome to EasyKanban");
        while (true) 
        {
            int choice = Integer.parseInt(JOptionPane.showInputDialog(null, "Choose an option:\n1. Add Task\n2. Show Report (Coming Soon)\n3. Quit"));
            switch (choice) 
            {
                case 1:
                    addTask();
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Coming Soon");
                    break;
                case 3:
                    System.exit(0);
                default:
                    JOptionPane.showMessageDialog(null, "Invalid option. Please try again.");
            }
        }
    }

    // Method to add a new task
    private static void addTask() 
    {
        int numTasks = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter the number of tasks you want to add:"));
        for (int i = 0; i < numTasks; i++) 
        {
            String taskName = JOptionPane.showInputDialog(null, "Enter Task Name:");
            String taskDescription = JOptionPane.showInputDialog(null, "Enter Task Description (max 50 characters):");
            while (!checkTaskDescription(taskDescription)) 
            {
                taskDescription = JOptionPane.showInputDialog(null, "Task description too long. Please enter less than 50 characters:");
            }
            String developerName = JOptionPane.showInputDialog(null, "Enter Developer Name (First and Last):");
            int taskDuration = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter Task Duration (hours):"));
            String taskId = createTaskID(taskName, developerName);
            String taskStatus = (String) JOptionPane.showInputDialog(null, "Select Task Status:", "Choose Task Status", JOptionPane.QUESTION_MESSAGE, null, new String[]{"To Do", "Doing", "Done"}, "To Do");
            tasks.add(new Task(taskName, taskDescription, developerName, taskDuration, taskId, taskStatus));
            JOptionPane.showMessageDialog(null, "Task successfully added!");
        }
        printTaskDetails();
        int totalHours = returnTotalHours();
        JOptionPane.showMessageDialog(null, "Total estimated hours: " + totalHours);
    }

    // Method to check if the task description is within the maximum length limit
    public static boolean checkTaskDescription(String taskDescription) 
    {
        return taskDescription.length() <= 50;
    }

    // Method to generate a unique task ID based on task name and developer name
    public static String createTaskID(String taskName, String developerName) 
    {
        String firstTwoLetters = taskName.substring(0, 2).toUpperCase();
        String lastThreeLetters = developerName.split(" ")[1].substring(0, 3).toUpperCase();
        return firstTwoLetters + ":" + taskNumber++ + ":" + lastThreeLetters;
    }

    // Method to print the details of all tasks
    private static void printTaskDetails() 
    {
        String message = "";
        for (Task task : tasks) {
            message += task.toString() + "\n";
        }
        JOptionPane.showMessageDialog(null, message);
    }

    // Method to calculate and return the total estimated hours for all tasks
    private static int returnTotalHours() 
    {
        int totalHours = 0;
        for (Task task : tasks) 
        {
            totalHours += task.getDuration();
        }
        return totalHours;
    }

    // Class representing a task
    private static class Task 
    {
        String taskName;
        String taskDescription;
        String developerName;
        int duration;
        String taskId;
        String taskStatus;

        public Task(String taskName, String taskDescription, String developerName, int duration, String taskId, String taskStatus) 
        {
            this.taskName = taskName;
            this.taskDescription = taskDescription;
            this.developerName = developerName;
            this.duration = duration;
            this.taskId = taskId;
            this.taskStatus = taskStatus;
        }

        public String toString() 
        {
            return "Task Status: " + taskStatus + "\n" +
                    "Developer Details: " + developerName + "\n" +
                    "Task Number: " + taskId + "\n" +
                    "Task Name: " + taskName + "\n" +
                    "Task Description: " + taskDescription + "\n" +
                    "Task ID: " + taskId.toUpperCase() + "\n" +
                    "Duration: " + duration + " hours";
        }

        public int getDuration() 
        {
            return duration;
        }
    }
}