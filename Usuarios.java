package com.mycompany.rifa;

import java.util.ArrayList;
import java.util.Scanner;

public class Usuarios {
    ArrayList<Rifa> usuarios = new ArrayList<>();
    Scanner sc = new Scanner(System.in);
    Rifa scanner = new Rifa(sc);
    
    public void cadastro(){
        boolean confirmar = false;
        while(confirmar == false){
        System.out.println("Digite seu nome: ");
        String nome = sc.nextLine();
        System.out.println("Digite seu E-Mail: ");
        String email = sc.nextLine();
        System.out.println("Digite seu cpf: ");
        String cpf = sc.nextLine();
        System.out.println("Informe sua senha: ");
        String senha = sc.nextLine();
        System.out.println("Confirme sua senha: ");
        String confirmar_senha = sc.nextLine();
        if(senha.equals(confirmar_senha)){
            usuarios.add(new Rifa(nome, senha, cpf, email));
            System.out.println("A conta do usuário " + nome + " foi registrada, faça o login pra concluir a criação da conta");
            confirmar = true;
        }
        else{
            System.out.println("As senhas não batem, digite 1 pra tentar novamente e 2 pra voltar pra teka de cadastro/login");
            int opcao = sc.nextInt();
            sc.nextLine();
            switch(opcao){
                case 1:
                    System.out.println("Voltando ao cadastro...");
                    break;
                case 2:
                    System.out.println("Voltando ao início...");
                    confirmar = true;
                    break;
                default:
                    System.out.println("Como a opção é inválida você vai voltar pra o cadastro");
            }
        }
        }
    }
    
    public Rifa fazerLogin(){
        boolean confirmar = false;
        boolean ativo = true;
        while(ativo){
        System.out.println("Digite o nome da sua conta: ");
        String nome = sc.nextLine();
        System.out.println("Digite sua senha: ");
        String senha = sc.nextLine();
        for(Rifa r : usuarios){
            if(r.getNome().equals(nome) && r.getSenha().equals(senha)){
                confirmar = true;
                System.out.println("Login realizado com sucesso!");
                return r;
            }
        }
        if(confirmar == false){
            System.out.println("Nome e/ou senha inválidos, se não fez o cadastro realize no menu anterior ou se errou algu dado corrija");
            System.out.println("1: Voltar ao menu pra realizar o cadastro | 2: Continuar no login");
            int opcao = sc.nextInt();
            sc.nextLine();
            switch(opcao){
                case 1:
                    ativo = false;
                    break;
                case 2:
                    System.out.println("Voltando...");
                    break;
                default:
                    System.out.println("Opção inválida, você voltará ao início do login");
                    break;
            }
        }
        }
        return null;
    }
    public int alterarSenha(Rifa login){
        boolean ativo = true;
        boolean senhaConfirmada = false;
        while(ativo){
            System.out.println("Para trocar a senha confirme seu cpf: ");
            String cpfDigitado = sc.nextLine();
            if(login.getCpf().equals(cpfDigitado)){
                while(senhaConfirmada == false){
                System.out.println("Digite a nova senha: ");
                String novaSenha = sc.nextLine();
                System.out.println("Confirme a nova senha: ");
                String confirmarNovaSenha = sc.nextLine();
                if(novaSenha.equals(confirmarNovaSenha)){
                    login.setSenha(novaSenha);
                    System.out.println("Senha trocada para a conta " + login.getNome() + " com sucesso, refaça o login pra confirrmar a troca");
                    ativo = false;
                    senhaConfirmada = true;
                }
                else{
                    System.out.println("As senhas são diferentes, tente de novo");
                }
                }
            }
            else{
                System.out.println("Cpf inválido, tente novamente digitando 1 ou volte para as configurações gerais digitando 2");
                int opcao = sc.nextInt();
                sc.nextLine();
                switch(opcao){
                    case 1:
                        System.out.println("Voltando...");
                        break;
                    case 2:
                        System.out.println("Retornando ao menu de configurações...");
                        return 0;
                    default:
                        System.out.println("Já que o número é inválido você voltará para a inserção do cpf");
                        break;
                }
            }
            
        }
        return 0;
    }
    public boolean configuracoes(Rifa login){
        boolean ativo = true;
        while(ativo){
            System.out.println("1: Alterar senha | 2: Sair da conta");
            int opcao = sc.nextInt();
            sc.nextLine();
            switch(opcao){
                case 1:
                    alterarSenha(login);
                    return false;
                case 2:
                    System.out.println("Saindo...");
                    return false;
                default:
                    System.out.println("Opção inválida, volte as configurações e tente de novo");
                    break;
            }
            
        }
        return true;
    }
    
    public boolean menu(Rifa login){
        int opcao;
        boolean ativo = true;
        while(ativo){
            System.out.println("---MENU RIFA---");
            System.out.println("1 : Adicionar Saldo | 2: Consultar Conta | 3: Comprar Rifa | 4: Vender a última rifa comprada | 5: Consultar número de cotas disponíveis | 6: Dia do Sorteio | 7: Configurações | 8: Sair do programa");
            opcao = sc.nextInt();
            sc.nextLine();
            switch(opcao){
                case 1:
                    System.out.println("Coloque a quantidade de saldo que você quer adicionar entre R$0.01 e R$15.00");
                    double saldo = sc.nextDouble();
                    login.adicionarSaldo(saldo);
                    break;
                case 2:
                    System.out.println("Nome: " + login.getNome() + " e-mail: " + login.getEmail() + " Saldo: R$" + login.getSaldo());
                    break;
                case 3:
                    System.out.println("Escolha a quantidade, lembrando que só é possível comprar 3 rifas por pessoa: ");
                    int qtd = sc.nextInt();
                    login.comprar(qtd);
                    break;
                case 4:
                    login.venderAcaoAtual();
                    break;
                case 5:
                    System.out.println("O Número de cotas disponíveis é: "+login.getCotasDisponiveis());
                    break;
                case 6: 
                    login.sortearVencedor(this.usuarios);
                    break;
                case 7:
                    ativo = configuracoes(login);
                    if(ativo == false){
                      return false;
                        }
                case 8:
                    System.out.println("Saindo...");
                    return true;
                default:
                    System.out.println("Opção inválida, tente de novo");
                    break;
            }
            
        }
        return false;
    }
    public static void main(String[] args){
        Usuarios usuario = new Usuarios();
        boolean sair_do_programa = false;
        boolean ativo = true;
        Rifa login = null;
        while(ativo){
        System.out.println("1 - Cadastro | 2 - Login");
        int escolha = usuario.sc.nextInt();
        usuario.sc.nextLine();
        switch(escolha){
            case 1:
                usuario.cadastro();
                break;
            case 2:
                login = usuario.fazerLogin();
                break;
            default:
                System.out.println("Opção inválida, tente de novo");
                break;
        }
        if(login != null){
            sair_do_programa = usuario.menu(login);
        }
        if(sair_do_programa){
            ativo = false;
        }
        login = null;
        }
    }
}