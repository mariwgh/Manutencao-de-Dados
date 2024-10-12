package src;

import java.io.*;
import static java.lang.System.out;

public class ManterEstudantes implements ManterDados {
    int qtosDados,
            posicaoAtual;
    Estudante[] dados;
    int tamanhoLogico;
    Situacao situacao;
    public void leituraDosDados(String nomeArquivo) {
        try {
            posicaoAtual = 0;
            BufferedReader arquivoDeEntrada = new BufferedReader(
                    new FileReader("c:\\temp\\dadosEstudantes.txt"));
            String linhaDoArquivo = "";
            try {
                boolean parar = false;
                while (! parar) {
                    Estudante novoDado = new Estudante();

                    try {

                        if (novoDado.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            incluirNoFinal(novoDado);
                        }
                        else

                            parar = true;
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
        BufferedWriter arquivoDeSaida = new BufferedWriter(
                new FileWriter("c:\\temp\\dadosEstudantes.txt"));
        for (int indice=0; indice < qtosDados; indice++){
            arquivoDeSaida.write(dados[indice].formatoDeArquivo());
            tamanhoLogico++;
        }
        arquivoDeSaida.close();
    }

    public Boolean existe(Estudante dadoProcurado) {
        return false;
    }

    public void incluirNoFinal(Estudante novoDado) {

    }
    public void incluirEm(Estudante novoDado, int posicaoDeInclusao) {
        dados[posicaoDeInclusao] = novoDado;
        out.println("Incluído com sucesso");
    }

    public void excluir(int posicaoDeExclusao) {

    }
    public Estudante valorDe(int indiceDeAcesso) {
        return new Estudante();
    }
    public void alterar(int posicaoDeAlteracao, Estudante novoDado) {

    }
    public void trocar(int origem, int destino) {

    }
    public void ordenar() {

    }
    public Boolean estaVazio() {
        return false;
    }
    public Boolean estaNoInicio() {
        return false;
    }
    public Boolean estaNoFim() {
        return false;
    }
    public void irAoInicio() {

    }
    public void irAoFim() {

    }
    public void irAoAnterior() {

    }
    public void irAoProximo() {

    }
    public int getPosicaoAtual() {
        return 1;
    }
    public void setPosicaoAtual(int novaPosicao) {

    }
    public Situacao getSituacao() {
        return situacao;
    }
    public void setSituacao(Situacao novaSituacao) {

    }
}
