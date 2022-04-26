package com.Automacao.Automacao.missao;

import com.Automacao.Automacao.SikuliDemo;
import com.Automacao.Automacao.TestandoOCR;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.sikuli.script.FindFailed;
import org.sikuli.script.Match;
import org.sikuli.script.Screen;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

public class Missao {

    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    private static final String CAMINHO_IMAGENS = currentWorkingDir.normalize() + "\\imagens\\";

    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);


    public static void missao(WebDriver driver, Screen screen) throws FindFailed, InterruptedException {
        MissaoMoeda missaoMoeda = new MissaoMoeda();

        while (true) {
            Integer quantidadeDePaginas = 0;

            LOGGER.debug("ENTROU NO LOOP");
            MissaoUtils.voltarTelaMissao(screen);

            int contador = 1;
            boolean existeSetaAvancar = false;

            while (!existeSetaAvancar) {
                quantidadeDePaginas += 1;

                try {
                    TimeUnit.MILLISECONDS.sleep(500);
                    MissaoUtils.clicarBotao(screen, "lutamissao.png");
                    LOGGER.debug("clicou luta missão " + contador);

                    Match posNecessario = screen.find(CAMINHO_IMAGENS + "necessario.png");
                    MissaoUtils.screenshot(driver, CAMINHO_IMAGENS + "printslog\\logs.png", posNecessario, 528, 100, 15, 60);
                    LOGGER.debug("Pegou os dados " + contador);

                    String[] valoresLidosOcr = TestandoOCR.OCR().split("-");

                    missaoMoeda.calcularMelhorMissao(valoresLidosOcr, contador);

                    MissaoUtils.clicarBotao(screen, "fecharmissao.png");
                    LOGGER.debug("Fechou missão " + contador);
                    contador += 1;

                    screen.click(CAMINHO_IMAGENS + "setaavancarmissao.png");
                    LOGGER.debug("Avançou tela " + contador);
                } catch (Exception e) {
                    LOGGER.error("Deu erro");
                    existeSetaAvancar = true;
                }
            }

            MissaoUtils.irParaTelaDaMissao(screen, quantidadeDePaginas, missaoMoeda.getPaginaMissao());
            TimeUnit.MILLISECONDS.sleep(500);
            MissaoUtils.clicarBotao(screen,  "lutamissao.png");

            MissaoUtils.clicarBotao(screen,  "iniciar.png");
            boolean energiaInsuficiente = MissaoUtils.checarSeImagemExiste(screen, "energiainsuficiente.png", 1000);

            if (energiaInsuficiente == true) { // todo testar isso aqui, considerar que não tem moeda
                boolean comprarEnergiaMoeda = MissaoUtils.checarSeImagemExiste(screen,  "comprarenergiamoeda.png", 1000);
                if (comprarEnergiaMoeda == true) {
                    MissaoUtils.clicarBotao(screen,  "comprarenergiamoeda.png");
                } else {
                    break;
                }
            }
            MissaoUtils.clicarBotao(screen,  "pularmissao.png");
            MissaoUtils.clicarSeExisteTresVezes(screen, "okmissao.png");
            TimeUnit.SECONDS.sleep(1);
            MissaoUtils.clicarSeExistir(screen, "descontardepois.png");
            MissaoUtils.clicarSeExistir(screen, "pegarbonusdiario.png");
            MissaoUtils.clicarSeExistir(screen, "descontardepois.png");
            MissaoUtils.clicarSeExistir(screen, "okmissao.png" );
            MissaoUtils.clicarSeExistir(screen, "telamissao.png");
        }
    }
}
