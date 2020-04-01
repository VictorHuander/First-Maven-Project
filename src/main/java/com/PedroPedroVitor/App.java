

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

public class App{
    public static void main(String args[]){
        Scanner in = new Scanner(System.in);
        Random ra = new Random();

        PersistenciaVeiculos pv = new PersistenciaVeiculos();
        pv.carrega();
        PersistenciaMotoristas pm = new PersistenciaMotoristas();
        pm.carrega();
        PersistenciaPassageiros pp = new PersistenciaPassageiros();
        pp.carrega();

        int menu = 0;
        while(menu == 0){
            for(int i = 0; i < pp.size(); i++){
                System.out.println(pp.getPorPosicao(i).getNome());
            }
            System.out.println("digite o nome do passageiro que quer encontrar um motorista");
            String name = in.nextLine();
            for(int i = 0; i < pp.size(); i++){
                if(pp.getPorPosicao(i).getNome().equals(name)){
                    ArrayList<Motorista> compativeis = new ArrayList<Motorista>();
                    for(int j = 0; j < pm.size(); j++){
                        if(pm.getPorPosicao(j).getPagamento() == pp.getPorPosicao(j).getFormaPagto()){
                            compativeis.add(pm.getPorPosicao(j));
                        }
                    } 
                    System.out.println("o motorista mais proximo ao correspondente passageiro eh: " + compativeis.get(ra.nextInt(compativeis.size())).getNome());
                    menu = 1;
                }
            }
            if(menu != 1){
                System.out.println("passageiro nao encontrado");
            }
            menu = 1;
            System.out.println("para adicionar, digite: 2(veiculo), 3(motorista), 4(passageiro) ou 1 para nao adicionar nada");
            menu = in.nextInt();
            if(menu == 2){
                System.out.println("digite a placa do veiculo");
                String a1 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite a marca do veiculo");
                String a2 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite a cor do veiculo");
                String a3 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite a categoria do veiculo");
                String a4 = in.nextLine().toUpperCase();
                in.next();

                Veiculo v = new Veiculo(a1, a2, a3, a4);
                if(pv.addVeiculo(v)){
                    System.out.println("veiculo adicionado");
                }
                else{
                    System.out.println("veiculo recusado");
                }
            }
            else if(menu == 3){
                System.out.println("digite o cpf do motorista");
                String a1 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite o nome do motorista");
                String a2 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite o id do veiculo do motorista (1-12)");
                String a3 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite a forma de pagamento do motorista");
                String a4 = in.nextLine().toUpperCase();
                in.next();

                FormaPagamento pagamento;
                if(a4 == "CARTAO"){
                    pagamento = FormaPagamento.CARTAO;
                }
                else if(a4 == "DINHEIRO"){
                    pagamento = FormaPagamento.DINHEIRO;
                }
                else{
                    pagamento = FormaPagamento.TODAS;
                }

                Veiculo veiculo = pv.getPorPosicao(Integer.parseInt(a3));

                Motorista m = new Motorista(a1, a2, veiculo, pagamento);
                if(pm.addMotorista(m)){
                    System.out.println("motorista adicionado");
                }
                else{
                    System.out.println("motorista recusado");
                }
            }
            else if(menu == 4){
                System.out.println("digite o cpf do passageiro");
                String a1 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite o nome do passageiro");
                String a2 = in.nextLine().toUpperCase();
                in.next();
                System.out.println("digite a forma de pagamento do passageiro");
                String a3 = in.nextLine().toUpperCase();
                in.next();

                FormaPagamento pagamento;
                if(a3 == "CARTAO"){
                    pagamento = FormaPagamento.CARTAO;
                }
                else if(a3 == "DINHEIRO"){
                    pagamento = FormaPagamento.DINHEIRO;
                }
                else{
                    pagamento = FormaPagamento.TODAS;
                }

                System.out.println("digite o numero do cartao do passageiro");
                String a4 = in.nextLine().toUpperCase();
                in.next();

                Passageiro p = new Passageiro(a1, a2, pagamento, a4);
                if(pp.addPassageiro(p)){
                    System.out.println("passageiro adicionado");
                }
                else{
                    System.out.println("passageiro recusado");
                }
            }
            menu = 1;
        }
        // pv.persiste();
        // pm.persiste();
        // pp.persiste();
        System.out.println("~fim~");
    }
}