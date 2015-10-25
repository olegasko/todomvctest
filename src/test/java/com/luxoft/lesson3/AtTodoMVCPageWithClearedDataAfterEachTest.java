package com.luxoft.lesson3;


import org.junit.After;
import org.junit.Before;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;

/**
 * Created by oleg on 25.10.2015.
 */
public class AtTodoMVCPageWithClearedDataAfterEachTest extends BaseTest{

    @Before
    public void openPage(){
        open("http://todomvc.com/examples/troopjs_require/");
    }

    @After
    public void clearData(){
        executeJavaScript("localStorage.clear");
    }

}
