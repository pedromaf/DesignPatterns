package model;

import view.Console;

public class Motorista extends Funcionario {

    String habilitacao;

    public Motorista(String nome, String email, String cpf, String telefone, double salario, int cargo, String habilitacao) {
        super(nome,email,cpf,telefone,salario,cargo);
        this.habilitacao = habilitacao;
    }

    @Override
    public void relatorio() {
        super.relatorio();
        Console.mostrar("Habilitacao: " + this.habilitacao);
    }
}
