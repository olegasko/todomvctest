package com.luxoft.lesson5;

import org.junit.After;
import org.junit.Before;

import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.Selenide.refresh;

/**
 * Created by oleg on 25.10.2015.
 */
public class AtTodoMVCPageWithClearedDataAfterEachTest extends BaseTest {

    @Before
    public void openPage(){
        open("https://todomvc4tasj.herokuapp.com/");

    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear()");

    }

}
