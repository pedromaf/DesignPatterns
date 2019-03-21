package model;
import view.*;

public class CriadorFuncionario {

    public Funcionario criarFuncionario(int cargo) {
		
		boolean entradaValida = false;
		String nome;
        String email;
        String cpf;
        String telefone;
        double salario = 0;

        Console.solicitarNome();
        nome = Input.lerString();

        Console.solicitarEmail();
        email = Input.lerString();

        Console.solicitarCpf();
        cpf = Input.lerString();

        Console.solicitarTelefone();
        telefone = Input.lerString();

        do {
            Console.solicitarSalario();
            try {
                salario = Input.lerDouble();
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        } while(!entradaValida);		

		switch(cargo) {
			case 2:
				Console.solicitarCrm();
                String crm = Input.lerString();
				return new Medico(nome,email,cpf,telefone,salario,cargo, crm);

			case 5:
				Console.solicitarNumeroHabilitacao();
                String habilitacao = Input.lerString();
                return new Motorista(nome,email,cpf,telefone,salario,cargo,habilitacao);

			case 8:
				entradaValida = false;
                int tipo = 0;
                do {
                    Console.solicitarTipoJogador();
                    try {
                        tipo = Input.validarOpcao(1,7);
                        entradaValida = true;
                    } catch(NumberFormatException exception) {
                        Erro.entradaInvalida();
                    }
                }while(!entradaValida);
                return new Jogador(nome,email,cpf,telefone,salario,cargo,tipo);

			default:
				return new Funcionario(nome,email,cpf,telefone,salario,cargo);
		}
    }
}
