package com.Automacao.Automacao.missao;

import com.Automacao.Automacao.SikuliDemo;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class MissaoUtils {

    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    private static final String CAMINHO_IMAGENS = currentWorkingDir.normalize() + "\\imagens\\";
    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);


    public static boolean checarSeImagemExiste(Screen screen, String nomeArquivo, int dormir) {
        LOGGER.debug("Entrou no checarSeImagemExiste");
        try {
            TimeUnit.MILLISECONDS.sleep(dormir);
            screen.find(CAMINHO_IMAGENS + nomeArquivo);
            return true;
        } catch (Exception e) {
            LOGGER.error("Não encontrou a imagem: " + nomeArquivo);
        }
        return false;
    }

    public static void clicarBotao(Screen screen, String nomeArquivo) {
        LOGGER.debug("Entrou no clicarBotao");
        boolean clicado = false;
        while (!clicado) {
            try {
                screen.click(CAMINHO_IMAGENS + nomeArquivo);
                clicado = true;
                LOGGER.debug("Clicou no botão " + nomeArquivo);
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                LOGGER.error("Não encontrou o botão para clicar: " + nomeArquivo);
            }
        }
    }

    public static void clicarSeExisteTresVezes(Screen screen, String nomeArquivo) throws InterruptedException {
        LOGGER.debug("Entrou no repetirTresVezes: " + nomeArquivo);
        for (int i = 0; i < 3; i++){
            clicarSeExistir(screen, nomeArquivo);
        }
    }

    public static void clicarSeExistir(Screen screen, String nomeArquivo) throws InterruptedException {
        LOGGER.debug("Entrou no clicarSeExistir");
        TimeUnit.SECONDS.sleep(1);
        boolean existeImagem = MissaoUtils.checarSeImagemExiste(screen, nomeArquivo, 500);
        if (existeImagem == true) {
            MissaoUtils.clicarBotao(screen, nomeArquivo);
        }
    }

    public static void voltarTelaMissao(Screen screen) {
        LOGGER.debug("Entrou no voltarTelaMissao");
        boolean existeSetaVoltar = false;
        while (!existeSetaVoltar) {
            try {
                screen.click(CAMINHO_IMAGENS + "setavoltarmissao.png");
            } catch (Exception e) {
                existeSetaVoltar = true;
            }
        }
    }

    public static void irParaTelaDaMissao(Screen screen, Integer totalDeTelas, Integer telaMissao) {
        LOGGER.debug("\n=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-");
        LOGGER.debug("Indo para a tela da missao");
        LOGGER.debug("Total de telas: " + totalDeTelas);
        LOGGER.debug("Pagina da missao: " + telaMissao);
        Integer voltarTelas = totalDeTelas - telaMissao;
        LOGGER.debug("Quantidade de telas a voltar: " + voltarTelas);
        LOGGER.debug("=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n");
        for (int i = 0; i < voltarTelas; i++) {
            try {
                screen.click(CAMINHO_IMAGENS + "setavoltarmissao.png");
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("Não existe mais seta para voltar");
            }
        }
    }

    public static void screenshot(WebDriver webdriver, String fileWithPath, Match posicao, int largura, int altura, int subX, int subY) throws Exception {
        TakesScreenshot scrShot = ((TakesScreenshot) webdriver);
        LOGGER.debug("Tirou print");
        File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
        File DestFile = new File(fileWithPath);

        FileUtils.copyFile(SrcFile, DestFile);
        BufferedImage f = ImageIO.read(DestFile);
        BufferedImage cImage = f.getSubimage(posicao.getX() - subX, posicao.getY() - subY, largura, altura);
        ImageIO.write(cImage, "png", DestFile);
        FileUtils.copyFile(DestFile, new File("tutorialspointlogo.png"));
    }

    public static void configInicial(Screen screen) throws InterruptedException {
        LOGGER.debug("Entrou na config inicial");
        TimeUnit.SECONDS.sleep(2);
        MissaoUtils.clicarSeExistir(screen, "pularmissao.png");
        MissaoUtils.clicarSeExisteTresVezes(screen, "okmissao.png");
        TimeUnit.SECONDS.sleep(2);
        for(int i = 0; i < 2 ; i++ ){
            MissaoUtils.clicarSeExistir(screen, "descontardepois.png");
        }
        MissaoUtils.clicarBotao(screen, "iconechat.png");
        MissaoUtils.clicarBotao(screen,  "telamissao.png");
        MissaoUtils.clicarSeExistir(screen,  "pegarbonusdiario.png");
    }

}
