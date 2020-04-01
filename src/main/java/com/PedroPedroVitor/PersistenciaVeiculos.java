

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PersistenciaVeiculos {
    private static final String SAMPLE_CSV_FILE_PATH = "veiculos.dat";
    private List<Veiculo> veiculos;

    public PersistenciaVeiculos(){
        veiculos = new ArrayList<Veiculo>();
    }

    public boolean addVeiculo(Veiculo v){
        if(veiculos.add(v)){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int size(){
        return veiculos.size();
    }

    public Veiculo getPorPosicao(int pos){ // para se quiser imprimir no app
        if (pos>=0 && pos<veiculos.size()){
			return veiculos.get(pos);
		}else{
			return null;
		}
    }

    public List<Veiculo> carrega(){
        try (Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);) {
                int helper = 0; // para nao adicionar um veiculo com as descricoes da CSV file
                for (CSVRecord csvRecord : csvParser) {
                    String placa = csvRecord.get(0);
                    String marca = csvRecord.get(1);
                    String cor = csvRecord.get(2);
                    String categoria = csvRecord.get(3);

                    if(helper == 1){
                        Veiculo veiculo = new Veiculo(placa, marca, cor, categoria);
                        veiculos.add(veiculo);
                    }
                    else{
                        helper++;
                    }
                }
            return veiculos;
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
        }
        return null;
    }

    public boolean persiste(){
        String fName = "veiculos.dat";
        String currDir = "../src/main/java/com/PedroPedroVitor";
        String nameComplete = currDir+"\\"+fName;
        Path path = Paths.get(nameComplete);
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8))) {
            String linha = "placa,marca,cor,categoria"; // para manter o header da CSV file
            writer.println(linha);
            for(Veiculo veiculo : veiculos){
                linha = veiculo.getPlaca() + "," + veiculo.getMarca() + "," + veiculo.getCor() + "," + veiculo.getCategoria();
                writer.println(linha);
            }
        return true;
        }catch (IOException x){
            System.err.format("Erro de E/S: %s%n", x);
        }
        return false;
    }
}
