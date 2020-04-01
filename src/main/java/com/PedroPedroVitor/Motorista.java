

public class Motorista{

 private String cpf;
 private String nome;
 private Veiculo veiculo;
 private FormaPagamento pagamento;

    public Motorista(String cpf, String nome, Veiculo veiculo, FormaPagamento pagamento){
        this.cpf = cpf;
        this.nome = nome;
        this.veiculo = veiculo;
        this.pagamento = pagamento;

    }

    public String getCPF(){
        return cpf;
    }

    public String getNome(){
        return nome;
    }

    public Veiculo getVeiculo(){
        return veiculo;
    }

    public FormaPagamento getPagamento(){
        return pagamento;
    }

    
}