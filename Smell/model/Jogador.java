package model;

import view.Console;

public class Jogador extends Funcionario {

    public enum Tipo {

        VOLANTE, ZAGUEIRO, MEIA, GOLEIRO, ATACANTE, LATERALESQUERDO, LATERALDIREITO
    }

    private Tipo tipo;
    private boolean apto;

    public Jogador(String nome, String email, String cpf, String telefone, double salario, int cargo, int tipo) {
        super(nome,email,cpf,telefone,salario,cargo);
        this.tipo = getTipoPorNumero(tipo);
        this.apto = true;
    }

    public void alterarAptidao() {

        this.apto = !this.apto;
    }

    private Tipo getTipoPorNumero(int tipo) {

        switch(tipo) {
            case 1:
                return Tipo.VOLANTE;
            case 2:
                return Tipo.ZAGUEIRO;
            case 3:
                return Tipo.MEIA;
            case 4:
                return Tipo.GOLEIRO;
            case 5:
                return Tipo.ATACANTE;
            case 6:
                return Tipo.LATERALESQUERDO;
            case 7:
            default:
                return Tipo.LATERALDIREITO;
        }
    }

    private String getTipoString() {

        switch(this.tipo) {
            case VOLANTE:
                return "Volante";
            case ZAGUEIRO:
                return "Zagueiro";
            case MEIA:
                return "Meia";
            case GOLEIRO:
                return "Goleiro";
            case ATACANTE:
                return "Atacante";
            case LATERALESQUERDO:
                return "Lateral esquerdo";
            case LATERALDIREITO:
            default:
                return "Lateral direito";
        }
    }

    @Override
    public void relatorio() {
        super.relatorio();
        Console.mostrar("Tipo: " + getTipoString());
        if(this.apto) {
            Console.mostrar("Jogador apto para jogar.");
        } else {
            Console.mostrar("Jogador inapto para jogar.");
        }
    }
}
