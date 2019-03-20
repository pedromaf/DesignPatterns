package control;

import model.*;
import view.*;

import java.util.ArrayList;

class Isoccer {

    private static Isoccer instance;

    private Login admin;
    private ArrayList<Funcionario> listaFuncionarios;
    private ArrayList<Sociotorcedor> listaSociostorcedores;

    private ArrayList<RecursoFisico> listaRecursosFisicos;

    private double valorContribuicaoSocioJunior;
    private double valorContribuicaoSocioSenior;
    private double valorContribuicaoSocioElite;

    private Isoccer() {
        this.admin = Login.getInstance();
        this.listaFuncionarios = new ArrayList<>();
        this.listaSociostorcedores = new ArrayList<>();
        this.listaRecursosFisicos = new ArrayList<>();
        this.valorContribuicaoSocioJunior = 10;
        this.valorContribuicaoSocioSenior = 20;
        this.valorContribuicaoSocioElite = 30;
    }

    static Isoccer getInstance() {

        if(instance == null) {
            instance = new Isoccer();
        }

        return instance;
    }

    void entrar() {

        String usuario;
        String senha;

        Console.solicitarUsuario();
        usuario = Input.lerString();

        Console.solicitarSenha();
        senha = Input.lerString();

        if(admin.autenticarUsuario(usuario)) {
            if(admin.autenticarSenha(senha)) {
                menuPrincipal();
            } else {
                Erro.senhaInvalida();
            }
        } else {
            Erro.usuarioInvalido();
        }
    }

    private void menuPrincipal() {

        int opcao;
        boolean voltar = false;

        do {
            Console.menuPrincipal();
            try {
                opcao = Input.validarOpcao(1,8);

                switch(opcao) {
                    case 1:
                        adicionarFuncionario();
                        break;
                    case 2:
                        alterarEstadoDeJogador();
                        break;
                    case 3:
                        adicionarSociotorcedor();
                        break;
                    case 4:
                        alterarValorContribuicao();
                        break;
                    case 5:
                        alterarEstadoDePagamentoSocio();
                        break;
                    case 6:
                        menuRecursos();
                        break;
                    case 7:
                        menuRelatorio();
                        break;
                    case 8:
                    default:
                        voltar = true;
                }
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }

        }while(!voltar);
    }

    private void alterarEstadoDeJogador() {

        String cpf;

        Console.solicitarCpf();
        cpf = Input.lerString();

        for(Funcionario atual: this.listaFuncionarios) {
            if(atual instanceof Jogador) {
                if(atual.getCpf().equals(cpf)) {
                    ((Jogador)atual).alterarAptidao();
                    Console.estadoJogadorAlterado();
                    return;
                }
            }
        }
        Erro.jogadorNaoEncontrado();
    }

    private void menuRelatorio() {

        int opcao = 0;
        boolean entradaValida = false;

        do {
            Console.menuRelatorio();
            try {
                opcao = Input.validarOpcao(1,5);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        } while(!entradaValida);

        switch(opcao) {
            case 1:
                Console.mostrar(">>> Relatorio Geral <<<\n");
                relatorioGeral();
                break;
            case 2:
                Console.mostrar(">>> Relatorio de Funcionarios <<<\n");
                relatorioFuncionarios();
                break;
            case 3:
                Console.mostrar(">>> Relatorio de Recursos Fisicos <<<\n");
                relatorioRecursosFisicos();
                break;
            case 4:
                Console.mostrar(">>> Relatorio de Socios-torcedores <<<\n");
                relatorioSocios();
            default:
        }
    }

    private void relatorioGeral() {

        relatorioFuncionarios();
        relatorioRecursosFisicos();
        relatorioSocios();
    }

    private void relatorioFuncionarios() {

        Console.mostrar("\n\t\t[Time]\n");
        for(Funcionario atual: this.listaFuncionarios) {
            if(atual.getCargo().equals(Funcionario.Cargo.TECNICO)) {
                atual.relatorio();
                Console.mostrar("---------------");
            }
        }
        for(Funcionario atual: this.listaFuncionarios) {
            if(atual.getCargo().equals(Funcionario.Cargo.JOGADOR)) {
                atual.relatorio();
                Console.mostrar("---------------");
            }
        }
        Console.mostrar("\n\t\t[Servicos gerais]\n");
        for(Funcionario atual: this.listaFuncionarios) {
            if(!atual.getCargo().equals(Funcionario.Cargo.TECNICO) && !atual.getCargo().equals(Funcionario.Cargo.JOGADOR)) {
                atual.relatorio();
                Console.mostrar("---------------");
            }
        }
    }

    private void relatorioRecursosFisicos() {

        Console.mostrar("\n\t\t[Transporte]\n");
        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof Onibus) {
                ((Onibus)atual).relatorio();
                Console.mostrar("---------------");
            }
        }
        Console.mostrar("\n\t\t[Estadio]\n");
        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof Estadio) {
                ((Estadio)atual).relatorio();
                Console.mostrar("---------------");
                break;
            }
        }
        Console.mostrar("\n\t\t[Centro de Treinamento]\n");
        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof CentroTreinamento) {
                ((CentroTreinamento)atual).relatorio();
                Console.mostrar("---------------");
                break;
            }
        }
    }

    private void relatorioSocios() {

        int inadimplentes = 0;
        int total = this.listaSociostorcedores.size();

        Console.mostrar("Total de socios-torcedores: " + total);

        for(Sociotorcedor atual: this.listaSociostorcedores) {
            if(atual.getInadimplente()) {
               inadimplentes++;
            }
        }

        Console.mostrar("Total de socios adimplentes: " + (total - inadimplentes));
        Console.mostrar("Total de socios inadimplentes: " + inadimplentes);
        Console.mostrar("---------------");

        for(Sociotorcedor atual: this.listaSociostorcedores) {
            atual.relatorio();
        }
    }

    private void adicionarFuncionario() {

        int cargo = 0;
        boolean entradaValida = false;
        boolean solicitarDados = true;

        do {
            Console.menuAdicionarFuncionario();
            try {
                cargo = Input.validarOpcao(1,9);
                if(cargo == 9) { //VOLTAR
                    solicitarDados = false;
                }
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        }while(!entradaValida);

        entradaValida = false;

        if(solicitarDados) {

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

            if(cargo == 2) { //MEDICO
                Console.solicitarCrm();
                String crm = Input.lerString();
                this.listaFuncionarios.add(new Medico(nome,email,cpf,telefone,salario,cargo, crm));
            } else if(cargo == 5) { //MOTORISTA
                Console.solicitarNumeroHabilitacao();
                String habilitacao = Input.lerString();
                this.listaFuncionarios.add(new Motorista(nome,email,cpf,telefone,salario,cargo,habilitacao));
            } else if(cargo == 8) { //JOGADOR
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
                this.listaFuncionarios.add(new Jogador(nome,email,cpf,telefone,salario,cargo,tipo));
            } else {
                this.listaFuncionarios.add(new Funcionario(nome,email,cpf,telefone,salario,cargo));
            }

            Console.funcionarioAdicionado();
        }
    }

    private void adicionarSociotorcedor() {

        String nome;
        String email;
        String cpf;
        String telefone;
        String endereco;
        int tipo = 0;

        boolean entradaValida = false;

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

        do {
            Console.solicitarTipoSocio();
            try {
                tipo = Input.validarOpcao(1,3);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        }while(!entradaValida);

        this.listaSociostorcedores.add(new Sociotorcedor(nome,email,cpf,telefone,endereco,tipo));
        Console.sociotorcedorAdicionado();
    }

    private void alterarValorContribuicao() {

        int opcao = 0;
        boolean entradaValida = false;
        boolean voltar = false;

        do {
            Console.menuAlterarValorContribuicao();
            try {
                opcao = Input.validarOpcao(1,4);
                entradaValida = true;
                if(opcao == 4) {
                    voltar = true;
                }
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        } while(!entradaValida);

        if(!voltar) {

            double valorNovo = 0;

            switch(opcao) {
                case 1:
                    Console.mostrar("Valor de contribuicao Junior atual: R$" + this.valorContribuicaoSocioJunior + ".");
                    break;
                case 2:
                    Console.mostrar("Valor de contribuicao Senior atual: R$" + this.valorContribuicaoSocioSenior + ".");
                    break;
                case 3:
                default:
                    Console.mostrar("Valor de contribuicao Elite atual: R$" + this.valorContribuicaoSocioElite + ".");
            }

            entradaValida = false;
            do {
                Console.solicitarNovoValorContribuicao();
                try {
                    valorNovo = Input.lerDouble();
                    entradaValida = true;
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            }while(!entradaValida);

            switch(opcao) {
                case 1:
                    this.valorContribuicaoSocioJunior = valorNovo;
                    break;
                case 2:
                    this.valorContribuicaoSocioSenior = valorNovo;
                    break;
                case 3:
                default:
                    this.valorContribuicaoSocioElite = valorNovo;
            }

            Console.valorContribuicaoAlterado(opcao);
        }
    }

    private void alterarEstadoDePagamentoSocio() {

        if(!this.listaSociostorcedores.isEmpty()) {
            String cpfSocio;

            Console.solicitarCpf();
            cpfSocio = Input.lerString();

            for(Sociotorcedor atual : this.listaSociostorcedores) {
                if(atual.getCpf().equals(cpfSocio)) {
                    atual.alterarInadimplencia();
                    Console.estadoDePagamentoAlterado();
                    return;
                }
            }
            Erro.socioNaoEncontrado();
        } else {
            Erro.naoHaSociosCadastrados();
        }
    }

    private void menuRecursos() {

        int opcao = 0;
        boolean entradaValida = false;

        do {
            Console.menuGerenciarRecursos();
            try {
                opcao = Input.validarOpcao(1,4);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        }while(!entradaValida);

        switch(opcao) {
            case 1:
                menuGerenciarOnibus();
                break;
            case 2:
                menuGerenciarEstadio();
                break;
            case 3:
                menuGerenciarCentroTreinamento();
                break;
            default:
        }
    }

    private void menuGerenciarCentroTreinamento() {

        CentroTreinamento centro = centroTreinamentoDisponivel();

        if(centro != null) {
            Console.informacoesCentroTreinamento(centro.getNumeroDormitorios());
        } else {
            boolean entradaValida = false;
            int opcao = 0;

            do {
                Console.menuCentroTreinamento();
                try {
                    opcao = Input.validarOpcao(1,2);
                    entradaValida = true;
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            } while(!entradaValida);

            if(opcao == 1) {
                String identificacao = validarIdentificacao();
                int numeroDormitorios = 0;

                entradaValida = false;
                do {
                    Console.solicitarNumeroDormitorios();
                    try {
                        numeroDormitorios = Input.lerInt();
                        entradaValida = true;
                    } catch(NumberFormatException exception) {
                        Erro.entradaInvalida();
                    }
                } while(!entradaValida);

                this.listaRecursosFisicos.add(new CentroTreinamento(identificacao, numeroDormitorios));
                Console.centroTreinamentoAdicionado();
            }
        }
    }

    private CentroTreinamento centroTreinamentoDisponivel() {

        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof CentroTreinamento) {
                return (CentroTreinamento) atual;
            }
        }
        return null;
    }

    private Estadio estadioDisponivel() {

        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof Estadio) {
                return (Estadio)atual;
            }
        }
        return null;
    }

    private void menuGerenciarEstadio() {

        Estadio estadio = estadioDisponivel();

        if(estadio != null) {
            menuEstadioDisponivel(estadio);
        } else {
            menuEstadioIndisponivel();
        }
    }

    private void menuEstadioDisponivel(Estadio estadio) {

        boolean entradaValida = false;
        int opcao = 0;
        int novoValor = 0;

        do {
            Console.menuEstadioDisponivel(estadio.getNumeroTorcedores(), estadio.getNumeroBanheiros(), estadio.getNumeroLanchonetes());
            try {
                opcao = Input.validarOpcao(1,4);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        } while(!entradaValida);

        if(opcao != 4) {
            entradaValida = false;
            do {
                Console.solicitarNovoValor();
                try {
                    novoValor = Input.lerInt();
                    if(novoValor > 0) {
                        entradaValida = true;
                    } else {
                        Erro.entradaInvalida();
                    }
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            } while(!entradaValida);
        }

        switch(opcao) {
            case 1:
                estadio.setNumeroTorcedores(novoValor);
                break;
            case 2:
                estadio.setNumeroBanheiros(novoValor);
                break;
            case 3:
                estadio.setNumeroLanchonetes(novoValor);
            default:
        }
    }

    private void menuEstadioIndisponivel() {

        boolean entradaValida = false;
        int opcao = 0;

        do {
            Console.menuEstadioIndisponivel();
            try {
                opcao = Input.validarOpcao(1,2);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        } while(!entradaValida);

        if(opcao == 1) {
            String identificacao = validarIdentificacao();
            int numeroTorcedores = 0;
            int numeroBanheiros = 0;
            int numeroLanchonetes = 0;

            entradaValida = false;
            do {
                Console.solicitarNumeroTorcedores();
                try {
                    numeroTorcedores = Input.lerInt();
                    entradaValida = true;
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            } while(!entradaValida);

            entradaValida = false;
            do {
                Console.solicitarNumeroBanheiros();
                try {
                    numeroBanheiros = Input.lerInt();
                    entradaValida = true;
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            } while(!entradaValida);

            entradaValida = false;
            do {
                Console.solicitarNumeroLanchonetes();
                try {
                    numeroLanchonetes = Input.lerInt();
                    entradaValida = true;
                } catch(NumberFormatException exception) {
                    Erro.entradaInvalida();
                }
            } while(!entradaValida);

            this.listaRecursosFisicos.add(new Estadio(identificacao, numeroTorcedores, numeroBanheiros, numeroLanchonetes));
            Console.estadioAdicionado();
        }
    }

    private void menuGerenciarOnibus() {

        boolean entradaValida = false;
        int opcao = 0;

        Console.onibusDisponiveis(onibusDisponiveis());

        do {
            Console.menuOnibus();
            try {
                opcao = Input.validarOpcao(1,3);
                entradaValida = true;
            } catch(NumberFormatException exception) {
                Erro.entradaInvalida();
            }
        }while(!entradaValida);

        switch(opcao) {
            case 1:
                adicionarOnibus();
                break;
            case 2:
                alterarDisponibilidadeOnibus();
            default:
        }
    }

    private int onibusDisponiveis() {

        int quantidade = 0;

        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof Onibus) {
                if(((Onibus)atual).getDisponivel()) {
                    quantidade++;
                }
            }
        }
        return quantidade;
    }

    private boolean checarIdentificacao(String identificacao) {

        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual.getIdentificacao().equals(identificacao)) {
                Erro.identificacaoInvalida();
                return false;
            }
        }
        return true;
    }

    private String validarIdentificacao() {

        String identificacao;

        do {
            Console.solicitarIdentificacaoAdicionar();
            identificacao = Input.lerString();
        }while(!checarIdentificacao(identificacao));

        return identificacao;
    }

    private void adicionarOnibus() {

        String identificacao;
        String placa;

        identificacao = validarIdentificacao();

        Console.solicitarPlaca();
        placa = Input.lerString();

        this.listaRecursosFisicos.add(new Onibus(identificacao, placa));
        Console.onibusAdicionado();
    }

    private void alterarDisponibilidadeOnibus() {

        Console.solicitarIdentificacaoBuscar();

        Onibus onibus = getOnibusPorIdentificacao(Input.lerString());
        if(onibus != null) {
            onibus.alterarDisponibilidade();
            Console.disponibilidadeAlterada();
        }
    }

    private Onibus getOnibusPorIdentificacao(String identificacao) {

        for(RecursoFisico atual: this.listaRecursosFisicos) {
            if(atual instanceof Onibus) {
                if(atual.getIdentificacao().equals(identificacao)) {
                    return (Onibus)atual;
                }
            }
        }
        Erro.identificacaoInvalida();
        return null;
    }
}















