package com.Automacao.Automacao.thread;

import org.openqa.selenium.WebDriver;
import org.sikuli.script.Screen;

import java.util.Map;

import static com.Automacao.Automacao.SikuliDemo.iniciarDriver;

public class ThreadControl {
    static WebDriver driver;
    static Screen screen;

    public static void iniciar() {
        Map<String, Object> driverScreen = iniciarDriver();
        screen  = (Screen) driverScreen.get("screen");
        driver = (WebDriver) driverScreen.get("driver");

        ThreadMissao threadMissao = new ThreadMissao(screen, driver);
        threadMissao.iniciar();
    }

    public static void parar() {
        ThreadMissao threadMissao = new ThreadMissao(screen, driver);
        threadMissao.parar();
        driver.quit();
    }
}
