import java.io.*;
import static java.lang.System.out;

public class ManterEstudantes implements ManterDados {
    int qtosDados, posicaoAtual, tamanhoFis;
    Estudante[] dados;
    //int tamanhoLogico;
    String[] materias;
    Situacao situacao;


    private void expandirVetor() {
        Estudante[] novoVetor = new Estudante[dados.length * 2];

        for (int indice = 0; indice < qtosDados; indice++) {
            novoVetor[indice] = dados[indice];
        }

        dados = novoVetor;
    }

    public void inicializador(int tamanhoFisico) {
        tamanhoFis = tamanhoFisico;
        dados = new Estudante[tamanhoFisico];
    }

    public void leituraDosDados(String nomeArquivo) {
        try {
            posicaoAtual = 0;
            BufferedReader arquivoDeEntrada = new BufferedReader(new FileReader(nomeArquivo));
            try {
                boolean parar = false;
                while (! parar) {
                    Estudante novoDado = new Estudante();

                    try {
                        if (novoDado.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            incluirNoFinal(novoDado);
                        }
                        else {
                            parar = true;
                        }
                    }

                    catch (Exception erroDeLeitura) {
                        out.println(erroDeLeitura.getMessage());

                        parar = true;
                    }
                }
                arquivoDeEntrada.close();
            }
            catch (IOException erroDeIO) {
                out.println(erroDeIO.getMessage());
            }
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
        }
    }

    public void gravarDados(String nomeArquivo) throws IOException {
        // qtosDados = 3;
        BufferedWriter arquivoDeSaida = new BufferedWriter(new FileWriter(nomeArquivo));
        for (int indice=0; indice < qtosDados; indice++){
            arquivoDeSaida.write(dados[indice].formatoDeArquivo());
            qtosDados++;
        }
        arquivoDeSaida.close();
    }

    public Boolean existe(Estudante dadoProcurado) {
        for (int i = 0 ; i <= qtosDados-1 ; i++){
            if (dados[i].getRa().compareTo(dadoProcurado.getRa()) == 0){
                return true;
            }
        }
        return false;
    }

    public void incluirNoFinal(Estudante novoDado) {
        if (qtosDados >= dados.length) {
            //expandir vetor
            Estudante[] novoVetor = new Estudante[dados.length * 2];

            for (int indice = 0; indice < qtosDados; indice++) {
                novoVetor[indice] = dados[indice];
            }

            dados = novoVetor;
        }

        dados[qtosDados] = novoDado;
        qtosDados++;
    }

    public void incluirEm(Estudante novoDado, int posicaoDeInclusao) {
        try{
            Estudante[] tempDados = new Estudante[dados.length + 1];
            for (int i = 0; i < dados.length; i++) {
                tempDados[i] = dados[i];
            }
            dados = tempDados;

            for (int i = qtosDados; i > posicaoDeInclusao; i--) {
                dados[i] = dados[i - 1];
            }

            qtosDados++;
            dados[posicaoDeInclusao] = novoDado;
        }
        catch (Exception erro){
            out.println("Ocorreu um erro: " + erro);
        }

    }

    public void excluir(int posicaoDeExclusao) {
        for (int indice = posicaoDeExclusao; indice < qtosDados ; indice++) {
            dados[indice] = dados[indice + 1];
        }

        qtosDados -= 1;
    }

    public Estudante valorDe(int indiceDeAcesso) {
        return dados[indiceDeAcesso];
    }

    public void alterar(int posicaoDeAlteracao, Estudante novoDado) {
        try{
            dados[posicaoDeAlteracao] = novoDado;
            out.println("Alterado com sucesso!");
        }
        catch (Exception erro){
            out.println("Falha ao alterar: " + erro);
        }

    }

    public void trocar(int origem, int destino) {
        Estudante auxiliar = dados[origem];
        dados[origem] = dados[destino];
        dados[destino] = auxiliar;
    }

    public void ordenar() {
    //ordenar por ra
        for (int i = 0; i < qtosDados - 1; i++) {
            for (int ii = i + 1; ii < qtosDados; ii++) {
                if (dados[i].getRa().compareTo(dados[ii].getRa()) > 0) {
                    trocar(i, ii); // Chama o método de troca
                }
            }
        }
    }

    public Boolean estaVazio() {
        if (qtosDados == 0) {
            return true;
        }
        return false;
    }

    public Boolean estaNoInicio() {
        return false;
    }

    public Boolean estaNoFim() {
        return false;
    }

    public void irAoInicio() {
        posicaoAtual = 0;
    }

    public void irAoFim() {
        posicaoAtual = qtosDados - 1;
    }

    public void irAoAnterior() {
        posicaoAtual--;
    }

    public void irAoProximo() {
        posicaoAtual++;
    }

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public void setPosicaoAtual(int novaPosicao) {
        posicaoAtual = novaPosicao;
    }

    public Situacao getSituacao() {
        return situacao;
    }

    public void setSituacao(Situacao novaSituacao) {
        situacao = novaSituacao;
    }

}
