package com.luxoft.lesson2;


        import com.codeborne.selenide.ElementsCollection;
        import com.codeborne.selenide.SelenideElement;
        import org.junit.Test;
        import org.openqa.selenium.By;
        import org.openqa.selenium.Keys;

        import static com.codeborne.selenide.CollectionCondition.empty;
        import static com.codeborne.selenide.CollectionCondition.exactTexts;
        import static com.codeborne.selenide.Condition.cssClass;
        import static com.codeborne.selenide.Condition.exactText;
        import static com.codeborne.selenide.Condition.visible;
        import static com.codeborne.selenide.Selenide.*;
        import static java.util.Arrays.asList;


/**
 * Created by oleg on 29.09.2015.
 */
public class TodoMVCTest {



    @Test
    public void todoMVCTest() {


        open("http://todomvc.com/examples/troopjs_require/");

        createTasks("1", "2");

        startEditing("2", "4").pressEnter();
        deleteTask("4");
        assertVisibleTasks("1");

        moveToActive();
        createTasks("2", "3");
        startEditing("1", "").pressEnter();
        startEditing("3", "something").sendKeys(Keys.ESCAPE);
        assertVisibleTasks("2", "3");

        toggleTask("3");
        assertVisibleTasks("2");

        toggleAll();

        moveToCompleted();
        assertVisibleTasks("2", "3");
        toggleTask("2");
        assertVisibleTasks("3");

        toggleAll();
        assertVisibleTasks("2", "3");
        toggleAll();

        moveToAll();
        assertVisibleTasks("2", "3");
        toggleAll();
        clearCompleted();
        assertEmpty();

    }

    static ElementsCollection tasks = $$("#todo-list li");

    private void moveToActive() {
        $(By.linkText("Active")).click();
    }

    private void moveToCompleted() {
        $(By.linkText("Completed")).click();
    }

    private void moveToAll() {
        $(By.linkText("All")).click();
    }

    private void clearCompleted(){
        $("#clear-completed").click();
    }

    private void assertEmpty() {
        tasks.shouldBe(empty);
    }

    private void createTasks(String... names){
        for(String name : asList(names)) {
            $("#new-todo").setValue(name).pressEnter();
        }
    }

    private void assertVisibleTasks(String... names){
        tasks.filter(visible).shouldHave(exactTexts(names));
    }

    private void deleteTask(String name){
        tasks.find(exactText(name)).hover().find(".destroy").click();
    }

    private void toggleTask(String name){
        tasks.find(exactText(name)).find(".toggle").click();
    }

    private static SelenideElement startEditing(String name, String newName){
        tasks.find(exactText(name)).find("label").doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newName);
    }

    private void assertLeftTasks(int count){
        $("#todo-count strong").shouldHave(exactText(Integer.toString(count)));
    }

    private void toggleAll(){
        $("#toggle-all").click();
    }

}