import java.io.*;
import static java.lang.System.out;

public class ManterEstudantes extends Estudante implements ManterDados {
    int qtosDados, posicaoAtual;
    Estudante[] dados;
    int tamanhoLogico;
    String[] materias;
    Situacao situacao;

    /*public ManterEstudantes(){        --> tenho quase certeza que vai precisa de um desse
        super(getCurso , getRa , getNome);
    }*/

    public void leituraDosDados(String nomeArquivo) {
        try {
            posicaoAtual = 0;
            BufferedReader arquivoDeEntrada = new BufferedReader(new FileReader(nomeArquivo));
            String linhaDoArquivo = "";
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
        BufferedWriter arquivoDeSaida = new BufferedWriter(new FileWriter(nomeArquivo));
        for (int indice=0; indice < qtosDados; indice++){
            arquivoDeSaida.write(dados[indice].formatoDeArquivo());
            tamanhoLogico++;
        }
        arquivoDeSaida.close();
    }

    public Boolean existe(Estudante dadoProcurado) {
        for (int i = 0 ; i <= tamanhoLogico-1 ; i++){
            if (dados[i] == dadoProcurado){
                return true;
            }
        }
        return false;
    }

    public void lerMaterias() throws IOException {
        materias = new String[15];
        BufferedReader leitor = new BufferedReader(new FileReader("Materias.txt"));
        String linha = "";
        int indice = 0;
        while (linha != null){
            linha = leitor.readLine();
            if (linha != null){
                materias[indice] = linha;
                indice++;
            }
        }
    }

    public void incluirNoFinal(Estudante novoDado) {
        /*
        marietti, querida, eu não entendi direito o que esse metodo faria,
        então, fiz o que achei que era.
        Porém também pensei que ele quisesse que expandisse o vetor e
        incluísse nos espaços no final mas eu acho que não então
        fiz usando tamanho logico+1.
        Perguntei p clara e ela ddisse que tinha que verificar se tava cheio
        e se nao tiver, incluir, se tiver, expandir e incluir apos o ultimo
        indice usado.
        */
        if(tamanhoLogico == dados.length){
            //expandir vetor para o dobro
            int tamanhoNovo = dados.length *2;
            //tamanhoLogico = 0;  limpar para contar de novo
            Estudante[] tempDados = new Estudante[tamanhoNovo];
            for (int i = 0 ; i <= tamanhoLogico-1; i++){
                tempDados[i] = dados[i];
            }
            dados = tempDados;

            try{
                dados[tamanhoLogico+1] = novoDado;
                out.println("Dado incluído com sucesso!");
            }
            catch (Exception erro){
                out.println(erro.getMessage());
            }

        }
        else{
            try{
                dados[tamanhoLogico+1] = novoDado;
                out.println("Dado incluído com sucesso!");
            }
            catch (Exception erro){
                out.println("Falha ao inserir: " + erro);
            }
        }

    }

    public void incluirEm(Estudante novoDado, int posicaoDeInclusao) {
        try{
            dados[posicaoDeInclusao] = novoDado;
            out.println("Incluído com sucesso!");
        }
        catch (Exception erro){
            out.println("Ocorreu um erro: " + erro);
        }

    }

    public void excluir(int posicaoDeExclusao) {
        tamanhoLogico -= 1;
        for (int indice=posicaoDeExclusao; indice < tamanhoLogico; indice++)
            dados[indice] = dados[indice+1];
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

    public void ordenar() { //mds chico explica ordenar pelo que?

    }

    public Boolean estaVazio() {
        if (tamanhoLogico == 0){
            return true;
        }
        return false;
    }


    public Boolean estaNoInicio() {
        return false;
        //pesquisa binaria? telepatia? nao sei
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
        situacao = novaSituacao;
    }

    /*public String getCurso(){
        return curso;
    }*/
}
