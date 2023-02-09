package br.com.carlos.cm.model;


import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean minado;
    private boolean aberto;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>();

    private List<CampoObservador> observadores = new ArrayList<>();
    // private List<BiConsumer<Campo, CampoEvento>> observadores = new ArrayList<>(); // Recebe dois parametros e nao retorna nada (BiConsumer)

    Campo(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    }
    public void registrarObservador(CampoObservador observador) {
        observadores.add(observador);
    }
    private void notificarObservadores(CampoEvento e) {
        observadores.stream().forEach(o -> o.eventoOcorreu(this, e));
    }
     boolean adicionarVizinho(Campo vizinho) {
        boolean linhaDiferente = this.linha != vizinho.linha;
        boolean colunaDiferente = this.coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        int deltaLinha = Math.abs(this.linha - vizinho.linha);
        int deltaColuna = Math.abs(this.coluna - vizinho.coluna);
        int deltaGeral = deltaLinha + deltaColuna;

        if (deltaGeral == 1 && !diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal) {
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }
    public void alterarBandeira() {
        if(!this.aberto) {
            this.marcado = !this.marcado; // Se não estiver aberto, altera a situação da marcação (se aberto, fechado)

            if(this.marcado) {
                notificarObservadores(CampoEvento.MARCAR);
            } else {
                notificarObservadores(CampoEvento.DESMARCAR);
            }
        }
    }
    public boolean abrir() {
        if (!this.aberto && !this.marcado) {
            if (minado) {
                notificarObservadores(CampoEvento.EXPLODIR);
                return true;
            }
            setAberto(true);
            if (vizinhancaSegura()) {
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }
    public boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }
    void minar() {
     this.minado = true;
    }

    public boolean isMarcado() {
        return this.marcado;
    }
    void setAberto(boolean aberto) {
        this.aberto = aberto;

        if(aberto) {
            notificarObservadores(CampoEvento.ABRIR);
        }
    }
    public boolean isAberto() {
        return this.aberto;
    }
    public boolean isFechado() {
        return !isAberto();
    }
    public int getLinha() {
        return this.linha;
    }

    public int getColuna() {
        return this.coluna;
    }

    boolean objetivoAlcancado() {
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    public int minasNaVizinhanca() {
        return (int) vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar() {
        this.aberto = false;
        this.minado = false;
        this.marcado = false;
        notificarObservadores(CampoEvento.REINICIAR);
    }
    public boolean isMinado() {
        return minado;
    }
}
