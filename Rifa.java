package com.mycompany.rifa;

import java.util.Random;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Rifa {
    private String nome;
    private double saldo;
    private String senha;
    private String email;
    private String cpf;
    private int[] codigo = new int[3];
    private int indice = 0;
    private static int cotas_disponiveis = 100;
    private boolean podeComprar;
    private Scanner scanner;
    
    public Rifa(String nome,String senha, String cpf, String email){
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.email = email;
        this.podeComprar = false;
    }
    public Rifa(Scanner scanner){
        this.scanner = scanner;
    }
    
    public String getNome(){
        return nome;
    }
    public double getSaldo(){
        return saldo;
    }
    public String getSenha(){
        return senha;
    }
    public String getEmail(){
        return email;
    }
    public String getCpf(){
        return cpf;
    }
    public int[] getCodigo(){  
        return codigo;
        
    }
    public boolean getPodeComprar(){
        return podeComprar;
    }
    public int getCotasDisponiveis(){
        return cotas_disponiveis;
    }
    public void setSenha(String novaSenha){
        this.senha = novaSenha;
    }
    public void adicionarSaldo(double saldo){
        if(saldo >= 0.01){
            double novoSaldo = this.saldo + saldo;
            if(novoSaldo <= 15.00){
                this.saldo += novoSaldo;
                LocalDateTime data_deposito = LocalDateTime.now();
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
                System.out.println("Depósito de R$" + saldo +" feito na conta do titular: " + this.nome + " e feito na data: " + formatador.format(data_deposito));
                if(this.saldo >= 5.00){
                    this.podeComprar = true;
                }
            }
            else{
                System.out.println("Saldo excedeu o limite de 15 reais, tente adicionar menos.");
            }
        } else{
            System.out.println("Saldo abaixo do mínimo, deposite ao menos 1 centavo.");
        }   
    }
    public void sortearNumero(int quantidade){
        Random sorteador = new Random();
        for(int i=0;i < quantidade;i++){
            codigo[indice] = sorteador.nextInt(100)+ 1;
            System.out.println("O Número sorteado foi: " +  codigo[indice] + "! Boa Sorte no dia do sorteio");
            this.indice += 1;
        }
        
    }
    public void comprar(int quantidade){
        if(quantidade > 3 || this.indice > 2 || cotas_disponiveis <= 0){
            System.out.println("Cada pessoa só pode comprar 3 números!");
        } else if(quantidade > 0 && quantidade <= cotas_disponiveis && indice < 3){
            double valor = quantidade * 5.00;
            if(valor <= saldo){
                saldo -= valor;
                LocalDateTime dataCompra = LocalDateTime.now();
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
                System.out.println("Nome do Comprador: "+ this.nome + " Valor: R$" + valor + " na data e horário: " + formatador.format(dataCompra));
                sortearNumero(quantidade);
                cotas_disponiveis -= quantidade;
            } else{
                System.out.println("Você não tem dinheiro suficiente, adicione mais saldo e tente novamente");
            }
        }
        else if(quantidade + indice > 3){
            System.out.println("Quantidade total de cotas fica acima do limite de 3 por pessoa, compre a quantidade máxima pra ter apenas 3 rifas");
        }
        if(cotas_disponiveis == 0){
            System.out.println("As cotas foram esgotadas ou a rifa acabou, procure informações nas redes sociais da rifa");
        }
    }
    public void venderAcaoAtual(){
        if(cotas_disponiveis == 0){
            System.out.println("Se as cotas foram esgotadas ou a rifa acabou não serão mais permitidas vendas, procure informações no site ou nas redes sociais da rifa");
        }
        if(indice > 0 && codigo[indice - 1] != 0){
            codigo[indice - 1] = 0;
            if(saldo <= 10.00){
                this.saldo += 5.00;
            }
            else{
                this.saldo += 15.00 - saldo;
            }
                this.indice--;
                LocalDateTime dataVenda = LocalDateTime.now();
                DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss a");
                System.out.println("Número Vendido, Valor devolvido: R$5.00, data e horário da venda: "+ formatador.format(dataVenda) + " você está permitido a comprar outra ação no lugar dessa.");
            }else{
             System.out.println("Erro na venda! você não tem nenhuma compra");   
            }
    }
    public void sortearVencedor(ArrayList<Rifa> usuarios){
            boolean confirmarVencedor = false;
            Random sorteioCodigo = new Random();
            int numeroSorteado = sorteioCodigo.nextInt(100) + 1;
            for(Rifa r: usuarios){
            if(numeroSorteado == r.codigo[0] || numeroSorteado == r.codigo[1] || numeroSorteado == r.codigo[2]){
                System.out.println("O código sorteado é: "+ numeroSorteado + "! e o vencedor foi: " + r.getNome());
                confirmarVencedor = true;
                cotas_disponiveis = 0;
            } 
            }
            if(confirmarVencedor == false){
                System.out.println("O número sorteado foi: " + numeroSorteado + " porém ninguém possui esse número, o sorteio será executado de novo em breve");
            }
        }
    }
