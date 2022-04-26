package com.Automacao.Automacao;

import com.Automacao.Automacao.jnativehook.NativeMouseListener;
import com.Automacao.Automacao.missao.MissaoUtils;
import com.Automacao.Automacao.thread.ThreadControl;
import org.openqa.selenium.*;
import org.sikuli.script.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class SikuliDemo {

    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    private static final String CAMINHO_IMAGENS = currentWorkingDir.normalize() + "\\imagens\\";
    private static final String CAMINHO_GECKO_DRIVER = currentWorkingDir.normalize() + "\\geckodriver.exe";
    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);

    static WebDriver driver;
    public static Thread thread;


    public static void main(String[] args) throws InterruptedException {

        NativeMouseListener.init();
        ThreadControl.iniciar();
    }

    public static Map<String, Object> iniciarDriver() {
        LOGGER.debug("iniciar geckodriver");
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.setLockingKeyState(KeyEvent.VK_CAPS_LOCK, false);

        Screen screen = new Screen();
        System.setProperty("webdriver.gecko.driver", CAMINHO_GECKO_DRIVER);
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://br22.herozerogame.com/?client=html5");

        Map<String, Object> driverScreen = new HashMap<>();
        driverScreen.put("driver", driver);
        driverScreen.put("screen", screen);

        return driverScreen;
    }

    public static void login(Screen screen) {
        LOGGER.debug("Entrou no login");
        String email = System.getenv("EMAIL") != null ? System.getenv("EMAIL") : "leopontorua@gmail.com";
        String senha = System.getenv("SENHA") != null ? System.getenv("SENHA") : "israelyy1";

        MissaoUtils.clicarBotao(screen, "login.png");
        screen.type(CAMINHO_IMAGENS + "email.png", email);
        screen.type(CAMINHO_IMAGENS + "senha.png", senha);
        MissaoUtils.clicarBotao(screen, "entrar.png");

    }
}


