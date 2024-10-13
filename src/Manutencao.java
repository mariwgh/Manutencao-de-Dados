//programa principal
import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

public class Manutencao {
    enum Ordens {porRa, porNome, porCurso, porMedia};
    private static Ordens ordemAtual = Ordens.porRa;
    private static Estudante[] estud;       // vetor de estudantes
    private static int quantosEstudantes;   // tamanho lógico do vetor estud
    private static BufferedReader arquivoDeEntrada;
    private static ManterEstudantes[] materias;
    private static BufferedWriter arquivoDeSaida;
    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma = true;
    static int onde; // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        estud = new Estudante[3];  // 50 - tamanho físico
        for (int ind=0; ind < 3; ind++)
            estud[ind] = new Estudante(); // criar objetos Estudante vazios no vetor
        quantosEstudantes = 0; // tamanho lógico (vetor vazio)
        preencherVetorPorArquivo();
        if (continuarPrograma) {
            seletorDeOpcoes();
            salvarVetorNoArquivo();
        }
        out.println("\nPrograma encerrado.");
    }

    public static void preencherVetorPorArquivo() {
        try {
            arquivoDeEntrada = new BufferedReader(
                    new FileReader("c:\\temp\\dadosEstudantes.txt")
            );
            String linhaDoArquivo = "";
            try
            {
                boolean parar = false;
                while (! parar)
                {
                    Estudante novoEstudante = new Estudante();
                    try
                    {
                        if (novoEstudante.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            estud[quantosEstudantes] = novoEstudante;
                            quantosEstudantes++;
                        }
                        else
                            parar = true;
                    }
                    catch (Exception erroDeLeitura)
                    {
                        out.println(erroDeLeitura.getMessage());
                        parar = true;
                    }
                }
                arquivoDeEntrada.close();
            }
            catch (IOException erroDeIO)
            {
                out.println(erroDeIO.getMessage());
                continuarPrograma = false;
            }
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
            continuarPrograma = false;
        }
    }

    public static void salvarVetorNoArquivo() throws IOException {
        arquivoDeSaida = new BufferedWriter(
                new FileWriter("c:\\temp\\dadosEstudantes.txt"));

        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();

        // percorro o vetor de estudantes para gravar, no arquivo de saída,
        // os objetos da classe Estudante armazenados no vetor estud
        for (int indice=0; indice < quantosEstudantes; indice++)
            arquivoDeSaida.write(estud[indice].formatoDeArquivo());
        arquivoDeSaida.close();
    }

    public static void seletorDeOpcoes() throws Exception {
        int opcao = 0;
        do {
            out.println("Opções:\n");
            out.println("0 - Terminar programa");
            out.println("1 - Incluir estudante");
            out.println("2 - Listar estudantes");   // Tem que mostrar as siglas das materias
            out.println("3 - Excluir estudante");
            out.println("4 - Listar situações");    // Tem que mostrar as siglas das materias
            out.println("5 - Digitar notas de estudante");
            out.println("6 - Ordenar por curso");
            out.println("7 - Ordenar por nome");
            out.println("8 - Ordenar por média");
            out.println("9 - Disciplina com maior número de estudantes aprovados");
            out.println("10 - Disciplina com maior número de estudantes retidos");
            out.println("11 - Estudante com maior média");
            out.println("12 - Disciplinas maior e menor nota (estudante com maior média)");
            out.println("13 - Média aritimética por disciplina");
            out.println("14 - Maior nota na disciplina com menor média");
            out.println("15 - Menor nota na disciplina com maior média");
            out.print("\nSua opção: ");
            opcao = leitor.nextInt();
            leitor.nextLine();      // necessário após nextInt() para poder ler strings a seguir
            switch(opcao) {
                case 1 : incluirEstudante(); break;
                case 2 : listarEstudantes(); break;
                case 3 : excluirEstudante(); break;
                case 4 : listarSituacoes();  break;
                case 5 : digitarNotas(); break;
                case 6 : ordenarPorCurso(); break;
                case 7 : ordenarPorNome(); break;
                case 8 : ordenarPorMedia(); break;
                case 9: disciplinaMaiorAprovacao(); break;
                case 10: disciplinaMaiorRetencao(); break;
                case 11: estudanteMaiorMediaNotas(); break;
                case 12: maiorEMenorNotaEstudanteDestaque(); break;
                case 13: mediaAritmeticaAluno(); break;
                case 14: maiorNotaMenorMedia(); break;
                case 15: menorNotaMaiorMedia(); break;
                default: out.println("Opção inválida!");
            }
        }
        while (opcao != 0);
    }

    // compareTo()   == 0 primeiro dado igual outro dado
    // compareTo()   < 0  primeiro dado < outro dado
    // compareTo()   > 0  dado this > outro dado
    // esse método guarda no atributo "onde" o índice de inclusão ou
    // o índice em que o estudante procurado foi encontrado

    public static boolean existeEstudante(Estudante estProcurado) {
        int inicio = 0;
        int fim = quantosEstudantes - 1;
        boolean achou = false;
        while (! achou && inicio <= fim) {
            onde = (inicio + fim) / 2;
            String raDoMeioDoTrechoDoVetor = estud[onde].getRa();
            String raDoProcurado = estProcurado.getRa();
            if (raDoMeioDoTrechoDoVetor.compareTo(raDoProcurado) == 0)
                achou = true;
            else
                if (raDoProcurado.compareTo(raDoMeioDoTrechoDoVetor) < 0)
                    fim = onde - 1;
                else
                    inicio = onde + 1;
            }
        if (!achou)
            onde = inicio;   // posição de inclusao do RA em ordem crescente
        return achou;
    }

    public static void incluirEstudante() throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();

        out.println("Incluir Estudante\n");
        out.print("Curso : ");
        String curso = leitor.nextLine();
        out.print("RA    : ");
        String ra = leitor.nextLine();
        out.print("Nome  : ");
        String nome = leitor.nextLine();
        Estudante umEstudante = new Estudante(curso, ra, nome);
        if (existeEstudante(umEstudante))  // ajusta a variável onde
          JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else
        {
            incluirEmOrdem(umEstudante);  // última posição usada
        }
    }

    private static void expandirVetor() {
        Estudante[] novoVetor = new Estudante[estud.length * 2];
        for (int indice=0; indice<quantosEstudantes; indice++)
            novoVetor[indice] = estud[indice];
        estud = novoVetor;
    }

    private static void incluirEmOrdem(Estudante novo) {
        if (quantosEstudantes >= estud.length)
            expandirVetor();
        // desloco para a direita os estudantes com RA > RA do novo
        for (int indice = quantosEstudantes; indice > onde; indice--)
            estud[indice] = estud[indice-1];
        estud[onde] = novo;
        quantosEstudantes++;  // temos mais um estudante no vetor
    }

    public static void excluirEstudante() throws Exception {
        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();
        out.println("Excluir Estudante\n");
        out.print("RA    : ");
        String ra = leitor.nextLine();
        Estudante umEstudante = new Estudante(" ", ra, " ");
        if (!existeEstudante(umEstudante))  // ajusta a variável onde
            JOptionPane.showMessageDialog(null,"Estudante não encontrado!");
        else  // achou o estudante procurado, no índice "onde" do vetor
        {
            excluir(onde);  // última posição usada
        }
    }

    private static void excluir(int indiceDeExclusao) {
        quantosEstudantes--;
        for (int indice=indiceDeExclusao; indice < quantosEstudantes; indice++)
            estud[indice] = estud[indice+1];
    }

    public static void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");
        int contLinha = 0;  // contador de linhas
        for (int ind = 0; ind < quantosEstudantes; ind++)
        {
            out.println(estud[ind]);

            if (++contLinha >= 20) {       // se exibiu 20 linhas, espera Enter
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;      // reinicia o contador de linhas
            }
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    public static void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        String situacao = "";
        for (int indice = 0; indice < quantosEstudantes; indice++)
        {
            double mediaDesseEstudante = estud[indice].mediaDasNotas();
            if (mediaDesseEstudante < 5)
                situacao = "Não promovido(a)";
            else
                situacao = "Promovido(a)    ";

            out.printf(
                    "%4.1f %16s "+estud[indice]+"\n", mediaDesseEstudante,
                    situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    private static void trocar(int indMaior, int indMenor) {
        Estudante auxiliar = estud[indMaior];
        estud[indMaior] = estud[indMenor];
        estud[indMenor] = auxiliar;
    }

    private static void ordenarPorCurso() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getCurso().compareTo(estud[rapido].getCurso()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    private static void ordenarPorRa() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getRa().compareTo(estud[rapido].getRa()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }

    private static void ordenarPorNome() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (estud[lento].getNome().compareTo(estud[rapido].getNome()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    private static void ordenarPorMedia() {
        for (int lento=0; lento < quantosEstudantes; lento++) {
            double mediaAtual = estud[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mediaAtual > estud[rapido].mediaDasNotas())
                    trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
    }

    private static void digitarNotas() {
        out.println("Digitação de notas de estudante:\n");
        out.print("Digite o RA do(a) estudante desejado(a): ");
        String raEstudante = leitor.nextLine();
        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!existeEstudante(estProc))
                out.println("Não há um(a) estudante com esse RA!");
            else {  // se RA foi encontrado, variável onde contém seu índice
                out.print("Quantidade de notas a serem digitadas: ");
                int quant = leitor.nextInt(); // depois de ler int, ler nextline()
                leitor.nextLine(); // necessário após nextInt() para poder ler strings a seguir

                estud[onde].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    estud[onde].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }

    public static void disciplinaMaiorAprovacao(){

    }

    public static void disciplinaMaiorRetencao(){

    }

    public static void estudanteMaiorMediaNotas(){

    }

    public static void maiorEMenorNotaEstudanteDestaque(){

    }

    public static void mediaAritmeticaAluno(){

    }

    public static void maiorNotaMenorMedia(){

    }

    public static void menorNotaMaiorMedia(){

    }

}
