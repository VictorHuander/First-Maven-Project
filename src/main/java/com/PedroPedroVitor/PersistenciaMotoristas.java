

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

public class PersistenciaMotoristas{
    public static final String SAMPLE_CSV_FILE_PATH = "motoristas.dat";
    public List<Motorista> motoristas;
    public PersistenciaVeiculos pv;

    public PersistenciaMotoristas(){
        motoristas = new ArrayList<Motorista>();
        pv = new PersistenciaVeiculos();
    }

    public boolean addMotorista(Motorista m){
        if(motoristas.add(m)){
            return true;
        }
        else{
            return false;
        }
    }

    public int size(){
        return motoristas.size();
    }

    public Motorista getPorPosicao(int pos){ // para se quiser imprimir no app
        if (pos>=0 && pos<motoristas.size()){
			return motoristas.get(pos);
		}else{
			return null;
		}
    }

    public List<Motorista> carrega(){
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
                
                if(flag == 1){
                    Veiculo veiculo = pv.getPorPosicao(Integer.parseInt(csvRecord.get(2)));
                    Motorista novoMotorista = new Motorista(cpf, nome, veiculo, pagamento);
                    motoristas.add(novoMotorista);
                } else {
                    flag++;
                }
            }
            return motoristas;
        } catch (IOException e){
            System.err.format("Erro de E/S: %s%n", e);
        }
        return null;
    }

    public boolean persiste(){
        String fileName = "motoristas.dat";
        String currDir = "../src/main/java/com/PedroPedroVitor";
        String nameComplete = currDir + "\\" + fileName;
        Path path = Paths.get(nameComplete);
        try(PrintWriter w = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            String linha = "cpf,nome,veiculo,pagamento";
            w.println(linha);
            for(Motorista m : motoristas){
                linha = m.getCPF() + "," + m.getNome() + "," + m.getVeiculo() + "," + m.getPagamento();
                w.println(linha);
            }
        return true;
        } catch (IOException e) {
            System.err.format("Erro de E/S: sn", e);
        }
        return false;
    }
}