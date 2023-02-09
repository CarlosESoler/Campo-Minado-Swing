package br.com.carlos.cm.view;

import br.com.carlos.cm.model.Campo;
import br.com.carlos.cm.model.CampoEvento;
import br.com.carlos.cm.model.CampoObservador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

    private final Color BG_PADRAO = new Color(184, 184, 184);
    private final Color BG_MARCAR = new Color(8, 179, 247);
    private final Color BG_EXPLODIR = new Color(189, 66, 68);
    private final Color TEXTO_VERDE = new Color(0, 100, 0);
    private Campo campo;
    public BotaoCampo(Campo campo){
        this.campo = campo;
        setBackground(BG_PADRAO);
        setOpaque(true);
        setBorder(BorderFactory.createBevelBorder(0));

        campo.registrarObservador(this);
        addMouseListener(this);
    }
    @Override
    public void eventoOcorreu(Campo c, CampoEvento e) {
        switch (e) {
            case ABRIR -> aplicarEstiloAbrir();
            case MARCAR -> aplicarEstiloMarcar();
            case EXPLODIR -> aplicarEstiloExplodir();
            default -> aplicarEstiloPadrao();
        }
    }

    private void aplicarEstiloPadrao() {
        setBackground(BG_PADRAO);
        setBorder(BorderFactory.createBevelBorder(0));
        setText("");
    }

    private void aplicarEstiloExplodir() {
        setBackground(BG_EXPLODIR);
        setForeground(Color.white);
        setText("X");
    }

    private void aplicarEstiloMarcar() {
        setBackground(BG_MARCAR);
        setForeground(Color.black);
        setText("M");
    }

    private void aplicarEstiloAbrir() {

        setBorder(BorderFactory.createLineBorder(Color.gray));
        if(campo.isMinado()) {
            setBackground(BG_EXPLODIR);
            return;
        }
        setBackground(BG_PADRAO);

        switch (campo.minasNaVizinhanca()) {
            case 1 -> setForeground(TEXTO_VERDE);
            case 2 -> setForeground(Color.blue);
            case 3 -> setForeground(Color.YELLOW);
            case 4, 5, 6 -> setForeground(Color.RED);
            default -> setForeground(Color.pink);
        }
        String valor = !campo.vizinhancaSegura() ? campo.minasNaVizinhanca() + " " : " ";
        setText(valor);
    }

    // Interface dos eventos do mouse
    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == 1) {
            campo.abrir();
        }
        else {
            campo.alterarBandeira();
        }
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    @Override
    public void mouseExited(MouseEvent e) {
    }
}