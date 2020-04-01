

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
public class PersistenciaPassageiros{
    public static final String SAMPLE_CSV_FILE_PATH = "passageiros.dat";
    public List<Passageiro> passageiros;

    public PersistenciaPassageiros(){
        passageiros = new ArrayList<Passageiro>();
    }

    public boolean addPassageiro(Passageiro p){
        if(passageiros.add(p)){
            return true;
        }
        else{
            return false;
        }
    }

    public int size(){
        return passageiros.size();
    }

    public Passageiro getPorPosicao(int pos){ // para se quiser imprimir no app
        if (pos>=0 && pos<passageiros.size()){
			return passageiros.get(pos);
		}else{
			return null;
		}
    }

    public List<Passageiro> carrega(){
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        ) {
            int flag = 0;
            for (CSVRecord csvRecord : csvParser) {
                String cpf = csvRecord.get(0);
                String nome = csvRecord.get(1);

                FormaPagamento pagamento;
                if(csvRecord.get(3) == "CARTAO"){
                    pagamento = FormaPagamento.CARTAO;
                }
                else if(csvRecord.get(3) == "DINHEIRO"){
                    pagamento = FormaPagamento.DINHEIRO;
                }
                else{
                    pagamento = FormaPagamento.TODAS;
                }
                
                String nroCartao = csvRecord.get(3);

                if(flag == 1){
                    Passageiro novoPassageiro = new Passageiro(cpf, nome, pagamento, nroCartao);
                    passageiros.add(novoPassageiro);
                } else {
                    flag++;
                }
            }
            return passageiros;
        } catch (IOException e){
            System.err.format("Erro de E/S: %s%n", e);
        }
        return null;
    }
    public boolean persiste(){
        String fileName = "passageiros.dat";
        String currDir = "../src/main/java/com/PedroPedroVitor";
        String nameComplete = currDir + "\\" + fileName;
        Path path = Paths.get(nameComplete);
        try(PrintWriter w = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            String linha = "cpf,nome,pagamento,cartao";
            w.println(linha);
            for(Passageiro p : passageiros){
                linha = p.getCPF() + "," + p.getNome() + "," + p.getFormaPagto() + "," + p.getNroCartao();
                w.println(linha);
            }
        return true;
        } catch (IOException e) {
            System.err.format("Erro de E/S: sn", e);
        }
        return false;
    }
}