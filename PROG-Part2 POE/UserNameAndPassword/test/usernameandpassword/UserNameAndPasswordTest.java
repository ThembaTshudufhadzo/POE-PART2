/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package usernameandpassword;



import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class UserNameAndPasswordTest 
{

@Test
public void testValidTaskDescriptionLength() {
    String validDescription = "Task description within limit";
    boolean result = UserNameAndPassword.checkTaskDescription(validDescription);
    Assertions.assertTrue(result);
}

    @Test
    public void testInvalidTaskDescriptionLength() 
    {
        String invalidDescription = "This is a task description that exceeds the 50 character limit.";
        Assertions.assertFalse(UserNameAndPassword.checkTaskDescription(invalidDescription));
    }

    @Test
    private void testTaskIdGeneration() 
    {
        String taskName = "Feature";
        String developerName = "John Doe";
        String expectedId = "FE:0:DOE";

        String generatedId = UserNameAndPassword.createTaskID(taskName, developerName);
        Assertions.assertEquals(expectedId, generatedId);
    }

    @Test
    public void testTaskIdIncrement() 
    {
        String taskName1 = "Task1";
        String developerName1 = "Dev1";
        String taskName2 = "Task2";
        String developerName2 = "Dev2";

        String id1 = UserNameAndPassword.createTaskID(taskName1, developerName1);
        String id2 = UserNameAndPassword.createTaskID(taskName2, developerName2);

        Assertions.assertNotEquals(id1, id2); // IDs should be different due to incrementing number
    }

    @Test
    public void testTotalHoursCalculation() 
    {
        
        List<Tasks> tasks = new ArrayList<>(tasks);
        tasks.add(new Task("Task1", "Description", "Dev1", 8, "ID1", "To Do"));
        tasks.add(new Task("Task2", "Description", "Dev2", 10, "ID2", "Doing"));

        int totalHours = UserNameAndPassword.returnTotalHours(tasks);
        Assertions.assertEquals(18, totalHours);
    }

    @Test
    public void testEmptyTaskList() 
    {
        List<Tasks> tasks = new ArrayList<>();
        int totalHours = UserNameAndPassword.returnTotalHours(tasks);
        Assertions.assertEquals(0, totalHours);
    }
}


