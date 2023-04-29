import java.io.Serializable;

public class Pacote implements Serializable {
    Mensagem mensagem;
    int coluna;
    int linha;
    int simbolo;

    public Pacote(Mensagem mensagem, Pacote j) {
        this.mensagem = mensagem;
        this.simbolo = j.simbolo;
        this.coluna = j.coluna;
        this.linha = j.linha;
    }

    public Pacote(Mensagem mensagem) {
        this.mensagem = mensagem;
        this.simbolo = 0;
        this.coluna = -1;
        this.linha = -1;
    }

    public Pacote(int linha, int coluna, String simbolo) {
        this.coluna = coluna - 1;
        this.linha = linha - 1;
        this.mensagem = Mensagem.SUA_VEZ;
        this.simbolo = switch (simbolo) {
            case "X" -> 1;
            case "O" -> -1;
            default -> 0;
        };
    }

    public String getStringLinha() {
        return String.valueOf(linha + 1);
    }

    public String getStringColuna() {
        return String.valueOf(coluna + 1);
    }

    public String getSimbolo() {
        String simbolo = switch (this.simbolo) {
            case 1 -> "X";
            case -1 -> "O";
            default -> "";
        };
        return simbolo;
    }
}
