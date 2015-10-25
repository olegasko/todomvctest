package com.luxoft.lesson3;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.yandex.qatools.allure.annotations.Step;

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
public class TodoMVCTest extends AtTodoMVCPageWithClearedDataAfterEachTest{


    @Test
    public void todoMVCTest() {

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
        assertNoTasks();

    }

    @Test
    public void countActiveTest(){
        createTasks("1","2","3");
        assertLeftTasks(3);
        toggleTask("1");
        moveToActive();
        assertLeftTasks(2);
        deleteTask("2");
        moveToCompleted();
        assertLeftTasks(1);
    }
    static ElementsCollection tasks = $$("#todo-list li");

    @Step
    private void moveToActive() {
        $(By.linkText("Active")).click();
    }

    @Step
    private void moveToCompleted() {
        $(By.linkText("Completed")).click();
    }

    @Step
    private void moveToAll() {
        $(By.linkText("All")).click();
    }

    @Step
    private void clearCompleted(){
        $("#clear-completed").click();
    }

    @Step
    private void assertNoTasks() {
        tasks.shouldBe(empty);
    }

    @Step
    private void createTasks(String... names){
        for(String name : asList(names)) {
            $("#new-todo").setValue(name).pressEnter();
        }
    }

    @Step
    private void assertVisibleTasks(String... names){
        tasks.filter(visible).shouldHave(exactTexts(names));
    }

    @Step
    private void deleteTask(String name){
        tasks.find(exactText(name)).hover().find(".destroy").click();
    }

    @Step
    private void toggleTask(String name){
        tasks.find(exactText(name)).find(".toggle").click();
    }

    @Step
    private static SelenideElement startEditing(String name, String newName){
        tasks.find(exactText(name)).find("label").doubleClick();
        return tasks.find(cssClass("editing")).find(".edit").setValue(newName);
    }

    @Step
    private void assertLeftTasks(int count){
        $("#todo-count strong").shouldHave(exactText(Integer.toString(count)));
    }

    @Step
    private void toggleAll(){
        $("#toggle-all").click();
    }

}
