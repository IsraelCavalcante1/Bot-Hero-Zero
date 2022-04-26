package com.Automacao.Automacao.thread;

import com.Automacao.Automacao.SikuliDemo;
import com.Automacao.Automacao.missao.Missao;
import com.Automacao.Automacao.missao.MissaoUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Screen;

import java.util.Map;

import static com.Automacao.Automacao.SikuliDemo.*;

class ThreadMissao implements Runnable {

    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);

    private volatile boolean exit = false;

    private Screen  screen;
    private WebDriver driver;

    public ThreadMissao(Screen screen, WebDriver driver) {
        this.screen = screen;
        this.driver = driver;
    }

    public void run() {
        while (!exit) {
            LOGGER.debug("Iniciou a thread");
            try {
                login(screen);
                MissaoUtils.configInicial(screen);
                Missao.missao(driver, screen);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LOGGER.debug("Encerrou a thread das missoes");
    }

    public void parar() {
        LOGGER.debug("Parou Thread");
        thread.stop();
        exit = true;
    }

    public Screen iniciar() {
        thread = new Thread(this, "T1");
        thread.start();

        LOGGER.debug("Chamou iniciar Thread");
        return screen;
    }
}


