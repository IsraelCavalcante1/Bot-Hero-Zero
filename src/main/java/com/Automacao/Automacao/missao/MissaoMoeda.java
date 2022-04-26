package com.Automacao.Automacao.missao;

import com.Automacao.Automacao.SikuliDemo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MissaoMoeda {

    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);

    static Integer paginaMissao;
    static Integer maiorResultadoXp;
    static Integer maiorResultadoMoeda;

    public Integer calcularMelhorMissao(String[] valoresLidosOcr, Integer contador) {

        int resultado = Integer.parseInt(valoresLidosOcr[1]) / Integer.parseInt(valoresLidosOcr[2]);
        if (paginaMissao == null || resultado > maiorResultadoMoeda) {
            paginaMissao = contador;
            maiorResultadoMoeda = resultado;
            if (maiorResultadoXp == null) {
                maiorResultadoXp = Integer.parseInt(valoresLidosOcr[3]) / Integer.parseInt(valoresLidosOcr[2]);
            }
        } else if (resultado == maiorResultadoMoeda) {
            int resultadoxp = Integer.parseInt(valoresLidosOcr[3]) / Integer.parseInt(valoresLidosOcr[2]);
            if (resultadoxp > maiorResultadoXp) {
                maiorResultadoXp = resultadoxp;
                paginaMissao = contador;
                maiorResultadoMoeda = resultado;
            }
        }
        LOGGER.debug("\n");
        LOGGER.debug("Melhores resultados:");
        LOGGER.debug("Pagina: " + paginaMissao);
        LOGGER.debug("Moeda: " + Integer.parseInt(valoresLidosOcr[1]));
        LOGGER.debug("Resultado conta moeda: " + maiorResultadoMoeda);
        LOGGER.debug("XP: " + Integer.parseInt(valoresLidosOcr[3]));
        LOGGER.debug("Resultado conta XP: " + maiorResultadoXp + "\n");

        return paginaMissao;
    }

    public Integer getPaginaMissao() {
        return paginaMissao;
    }

    public void setPaginaMissao(Integer paginaMissao) {
        MissaoMoeda.paginaMissao = paginaMissao;
    }

    public Integer getMaiorResultadoXp() {
        return maiorResultadoXp;
    }

    public void setMaiorResultadoXp(Integer maiorResultadoXp) {
        MissaoMoeda.maiorResultadoXp = maiorResultadoXp;
    }

    public Integer getMaiorResultadoMoeda() {
        return maiorResultadoMoeda;
    }

    public void setMaiorResultadoMoeda(Integer maiorResultadoMoeda) {
        MissaoMoeda.maiorResultadoMoeda = maiorResultadoMoeda;
    }
}
