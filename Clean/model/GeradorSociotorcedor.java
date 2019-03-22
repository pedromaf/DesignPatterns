package model;

public class GeradorSociotorcedor {
	
	private Sociotorcedor socioJunior;
	private Sociotorcedor socioSenior;
	private Sociotorcedor socioElite;

	public GeradorSociotorcedor() {
		
		this.socioJunior = new Sociotorcedor(1);
		this.socioSenior = new Sociotorcedor(2);
		this.socioElite = new Sociotorcedor(3);
	}	

    public Sociotorcedor gerarNovoSocio(int tipo) {		

		String nome;
        String email;
        String cpf;
        String telefone;
        String endereco;

		Sociotorcedor novoSocio = null;

        Console.solicitarNome();
        nome = Input.lerString();

        Console.solicitarEmail();
        email = Input.lerString();

        Console.solicitarCpf();
        cpf = Input.lerString();

        Console.solicitarTelefone();
        telefone = Input.lerString();

        Console.solicitarEndereco();
        endereco = Input.lerString();

		switch(tipo) {
			case 1:
				novoSocio = (Sociotorcedor) this.socioJunior.copiar();
				break;
			case 2:
				novoSocio = (Sociotorcedor) this.socioSenior.copiar();
				break;
			case 3:
			default:
				novoSocio = (Sociotorcedor) this.socioElite.copiar();
				break;
		}

		novoSocio.inserirDados(nome, email, cpf, telefone, endereco);
		return novoSocio;
	}	
}

