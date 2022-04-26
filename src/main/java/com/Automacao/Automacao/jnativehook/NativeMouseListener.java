package com.Automacao.Automacao.jnativehook;

import com.Automacao.Automacao.thread.ThreadControl;
import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

public class NativeMouseListener implements NativeKeyListener {

    public void nativeKeyPressed(NativeKeyEvent e) {
        String teclaPressionada = NativeKeyEvent.getKeyText(e.getKeyCode());
//        System.out.println("Key Pressed: " + teclaPressionada);

        if (teclaPressionada.equalsIgnoreCase("F7")) { // iniciar o loop
            ThreadControl.iniciar();
        } else if (teclaPressionada.equalsIgnoreCase("F8")) { // parar o loop
            ThreadControl.parar();

        }

        if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            try {
                GlobalScreen.unregisterNativeHook();
            } catch (NativeHookException nativeHookException) {
                nativeHookException.printStackTrace();
            }
        }
    }

    public static void init() {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }

        GlobalScreen.addNativeKeyListener(new NativeMouseListener());
    }
}
