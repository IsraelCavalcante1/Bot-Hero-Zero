package com.Automacao.Automacao;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacpp.*;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.bytedeco.javacpp.lept.*;
import static org.bytedeco.javacpp.tesseract.*;


public  class TestandoOCR {

    static Path currentWorkingDir = Paths.get("").toAbsolutePath();
    private static final String CAMINHO_LOG = currentWorkingDir.normalize().toString() + "\\imagens\\printslog\\logs.png";
    private static final Logger LOGGER = LogManager.getLogger(SikuliDemo.class);

    public static String OCR(){
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        // Initialize tesseract-ocr with English, without specifying tessdata path
        if (api.Init(null, "por") != 0) {
            LOGGER.error("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        PIX image = pixRead(CAMINHO_LOG);
        api.SetImage(image);
        // Get OCR result
        outText = api.GetUTF8Text();
        LOGGER.debug("Valor antes do replace: " + outText.getString());
        String valores = outText.getString().replaceAll("[^.0-9\\s]", "").replace(".", "").replace("-", "");
        String valorReplace = valores.trim().replaceAll("\\s+","-");
        LOGGER.debug("Valor depois do replace: " + valorReplace);
        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
        return valorReplace;
    }


}

