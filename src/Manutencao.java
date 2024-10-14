import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

//ele quer que no Manutencao só use o ManterEstudantes e nao use o vetor estud

public class Manutencao {
    enum Ordens {porRa, porNome, porCurso, porMedia};
    private static Ordens ordemAtual = Ordens.porRa;

    private static ManterEstudantes[] mantEstud;
    private static Estudante[] estud;           // vetor de estudantes
    private static int quantosEstudantes;       // tamanho lógico do vetor estud
    private static double estudanteDestaque;

    private static BufferedReader arquivoDeEntrada;
    private static BufferedWriter arquivoDeSaida;

    private static ManterEstudantes[] materias;

    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma = true;
    static int onde;                            // índice resultante da pesquisa binária

    public static void main(String[] args) throws Exception {
        mantEstud = new ManterEstudantes[3];  // 50 - tamanho físico (mas pq 3???)
        for (int ind=0; ind < 3; ind++) {
            mantEstud[ind] = new ManterEstudantes(); // criar objetos Estudante vazios no vetor
        }
        quantosEstudantes = 0; // tamanho lógico (vetor vazio)

        preencherVetorPorArquivo();

        if (continuarPrograma) {
            seletorDeOpcoes();
            salvarVetorNoArquivo();
        }

        out.println("\nPrograma encerrado.");
    }

    public static void preencherVetorPorArquivo() {
        String caminho = "c:\\temp\\DadosEstudantes.txt";

        try {
            arquivoDeEntrada = new BufferedReader(new FileReader(caminho));
            String linhaDoArquivo = "";
            try {
                boolean parar = false;
                while (!parar) {
                    Estudante novoEstudante = new Estudante();
                    try {
                        if (novoEstudante.leuLinhaDoArquivo(arquivoDeEntrada) ) {
                            mantEstud[quantosEstudantes] = (ManterEstudantes) novoEstudante;
                            //mantEstud[quantosEstudantes] = new ManterEstudantes(novoEstudante);

                            //SÓ COLOQUEI O EXTENDS NO MANTERESTUDANTS E PAROU DE DAR ERRO
                            //precisaria transformar o novo estudante para o manter estudante,
                            //ou eu crio um leuLinhaDoArquivo no MANTEREstudantes
                            //mas eu não sei se pode
                            quantosEstudantes++;
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
                continuarPrograma = false;
            }
        }
        catch (FileNotFoundException erro) {
            out.println(erro.getMessage());
            continuarPrograma = false;
        }
    }

    public static void salvarVetorNoArquivo() throws IOException {
        arquivoDeSaida = new BufferedWriter(new FileWriter("c:\\temp\\dadosEstudantes.txt"));

        if (ordemAtual != Ordens.porRa)
            ordenarPorRa();

        // percorro o vetor de estudantes para gravar, no arquivo de saída,
        // os objetos da classe Estudante armazenados no vetor estud
        for (int indice=0; indice < quantosEstudantes; indice++)
            arquivoDeSaida.write(mantEstud[indice].formatoDeArquivo());     //teria que implementar essa funlção no ManterEstudantes
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
                case 13: mediaAritmeticaAlunoEmDis(); break;
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
            String raDoMeioDoTrechoDoVetor = mantEstud[onde].getRa();
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

    private static void expandirVetor() {
        ManterEstudantes[] novoVetor = new ManterEstudantes[mantEstud.length * 2];

        for (int indice=0; indice<quantosEstudantes; indice++) {
            novoVetor[indice] = mantEstud[indice];
        }

        //troquei, nao se usa o estud??
        mantEstud = novoVetor;
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
        //teria que reverter o umEstudante para ManterEstudantes
        //mas teria que ter um construtor no ManterEstudantes e armazenar
        //os mesmos dados
        if (existeEstudante(umEstudante))  // ajusta a variável onde
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else
        {
            incluirEmOrdem(umEstudante);  // última posição usada
        }
    }

    //ue p q esse
    private static void incluirEmOrdem(ManterEstudantes novo) { //oq q ta acontecendo aqui??
        if (quantosEstudantes >= estud.length)
            expandirVetor();
        // desloco para a direita os estudantes com RA > RA do novo
        for (int indice = quantosEstudantes; indice > onde; indice--)
            mantEstud[indice] = mantEstud[indice-1];
        mantEstud[onde] = novo;
        quantosEstudantes++;  // temos mais um estudante no vetor
    }

    public static void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");
        int contLinha = 0;  // contador de linhas
        for (int ind = 0; ind < quantosEstudantes; ind++) {
            out.println(mantEstud[ind]);

            if (++contLinha >= 20) {       // se exibiu 20 linhas, espera Enter
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;      // reinicia o contador de linhas
            }
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
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
            //excluir(onde);  // última posição usada
            quantosEstudantes--;
            for (int indice=onde; indice < quantosEstudantes; indice++)
                mantEstud[indice] = mantEstud[indice+1];
        }
    }

    //rafa so comentei pq talvez fique melhor tendo apenas metodos chamado no
    // seletor, to achando uma repeticao de metodos mt ggrande, mas tbm nai sei
    //mudo se quiser
    /*private static void excluir(int indiceDeExclusao) {
        quantosEstudantes--;
        for (int indice=indiceDeExclusao; indice < quantosEstudantes; indice++)
            mantEstud[indice] = mantEstud[indice+1];
    }*/

    public static void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        String situacao = "";
        estudanteDestaque = Double.MIN_NORMAL;

        double[] aprovacoes = new double[NUM_DISCIPLINAS];
        double[] retencoes = new double[NUM_DISCIPLINAS];

        for (int indice = 0; indice < quantosEstudantes; indice++) {
            //creio que vai ter que colocar um ngc de indice para verificar
            //em qual materia está e verificar dps com if e else as materias
            //com mais retenção e aprovação
            /*
            pelo que eu pensei:
                dá para um substring para ver as notas, pois elas vão estar todas em
                um mesmo indice do vetor e depois ver de quem é quem, tipo:
                double biologia = mantEstud[i].substring(3,6)
                e aí faz toda a análise para ver se ele foi aprvado ou não e se sim vai somando
                nas aprovações de biologia
             */


            for (int indice = 0; indice < quantosEstudantes; indice++) {
                double[] notasDesseEstudante = mantEstud[indice].getNotas(); // Obter notas do estudante atual
                situacao = "";

                // verifica cada matéria??
                for (int i = 0; i < notasDesseEstudante.length; i++) {
                    if (notasDesseEstudante[i] >= 5) {
                        aprovacoes[i]++;
                    } else {
                        retencoes[i]++;
                    }
                }

                double mediaDesseEstudante = estud[indice].mediaDasNotas();
                if (mediaDesseEstudante < 5)
                    situacao = "Não promovido(a)";
                else {
                    situacao = "Promovido(a)    ";
                    if (mediaDesseEstudante > estudanteDestaque) {
                        estudanteDestaque = mediaDesseEstudante;
                    }
                }
                out.printf(
                        "%4.1f %16s " + estud[indice] + "\n", mediaDesseEstudante,
                        situacao);
            }
            out.print("\n\nTecle [Enter] para prosseguir: ");
            leitor.nextLine();
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

                mantEstud[onde].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    mantEstud[onde].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }

    private static void ordenarPorCurso() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mantEstud[lento].getCurso().compareTo(mantEstud[rapido].getCurso()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    private static void ordenarPorRa() {
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mantEstud[lento].getRa().compareTo(mantEstud[rapido].getRa()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }

    private static void ordenarPorNome() {
        //nao entendi pq tem o metodo ordenar em manter estudantes se nao e utilizado aq
        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mantEstud[lento].getNome().compareTo(mantEstud[rapido].getNome()) > 0)
                    trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    private static void ordenarPorMedia() {
        for (int lento=0; lento < quantosEstudantes; lento++) {
            double mediaAtual = mantEstud[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mediaAtual > mantEstud[rapido].mediaDasNotas())
                    trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
    }

    public static void disciplinaMaiorAprovacao(){
        int[] aprovadosPorDisciplina = new int[materias.length];

        for (int i = 0; i < quantosEstudantes; i++) {
            Estudante estudante = mantEstud[i];
            for (int j = 0; j < estudante.getNotas().length; j++) {
                if (estudante.getNotas()[j] >= 5) {
                    aprovadosPorDisciplina[j]++;
                }
            }
        }

        //descobrir a quantidade de aprovados d disciplina com mais aprovacao e o nome da materia
        int maxAprovados = 0;
        String disciplinaMaiorAprovacao = "";

        for (int i = 0; i < aprovadosPorDisciplina.length; i++) {
            if (aprovadosPorDisciplina[i] > maxAprovados) {
                maxAprovados = aprovadosPorDisciplina[i];
                disciplinaMaiorAprovacao = materias[i].getNome();
            }
        }

        out.println("Disciplina com maior número de estudantes aprovados: " + disciplinaMaiorAprovacao);
    }

    public static void disciplinaMaiorRetencao(){
        int[] retidosPorDisciplina = new int[materias.length];

        for (int i = 0; i < quantosEstudantes; i++) {
            Estudante estudante = mantEstud[i];
            for (int j = 0; j < estudante.getNotas().length; j++) {
                if (estudante.getNotas()[j] < 5) {
                    retidosPorDisciplina[j]++;
                }
            }
        }

        //descobrir a quantidade de aprovados d disciplina com mais retencao e o nome da materia
        int maxRetidos = 0;
        String disciplinaMaiorRetidos = "";

        for (int i = 0; i < retidosPorDisciplina.length; i++) {
            if (retidosPorDisciplina[i] > maxRetidos) {
                maxRetidos = retidosPorDisciplina[i];
                disciplinaMaiorRetidos = materias[i].getNome();
            }
        }

        out.println("Disciplina com maior número de estudantes retidos: " + disciplinaMaiorRetidos);
    }

    public static void estudanteMaiorMediaNotas() {
        double possivelMedia = 0;

        for (int i = 0; i < quantosEstudantes; i++) {
            double mediaAgora = estud[i].mediaDasNotas();

            if (mediaAgora > possivelMedia) {
                possivelMedia = mediaAgora;
                estudanteDestaque = estud[i];
            }
        }

        out.println("O estudante com maior média de notas é: " + estudanteDestaque);
    }

    public static void maiorEMenorNotaEstudanteDestaque() {
        //pegar o indice do estudante destaque e fzr um substring com
        //maior = double.maxvalue e min = double.minvalue
        //e depois fazer if

        //nao entendi pq substring mas ta

        //materia com maior e a com  menor nota do estudante destaque

        double maiorNota = Double.MIN_VALUE;
        double menorNota = Double.MAX_VALUE;
        String materiaMaiorNota;
        String materiaMenorNota;

        for (int i = 0; i < estudanteDestaque.getNotas().length; i++) {
            double nota = estudanteDestaque.getNota()[i];
            if (nota > maiorNota) {
                maiorNota = nota;
                materiaMaiorNota = i.getNome();
            }
            if (nota < menorNota) {
                menorNota = nota;
                materiaMenorNota = i.getNome();
            }
        }

        out.println("A matéria que o estudante destaque tem a maior nota é: " + materiaMaiorNota);
        out.println("A matéria que o estudante destaque tem a menor nota é: " + materiaMenorNota);
    }

    public static void mediaAritmeticaAlunoEmDis() {
        Double somaDis;

        for (int qMat = 0; qMat < materias.length; qMat++) {
            String materia = String.valueOf(materias[qMat]);

            for (int estudante = 0; estudante < quantosEstudantes; estudante++) {
                somaDis = estudante.getNotas()[materias[qMat]];
            }

            Double media = somaDis / quantosEstudantes;
            out.println("A media da materia " + materia + " é: " + media);
        }
    }

    public static void maiorNotaMenorMedia(){
        double menorMedia = 0;
        int materiaMenorMedia = 0;
        double[] maioresNotas = new double[materias.length];

        for (int qMat = 0; qMat < materias.length; qMat++) {
            double somaDis = 0;
            maioresNotas[qMat] = 0;

            for (int estudante = 0; estudante < quantosEstudantes; estudante++) {
                double notaAtual = estudante.getNotas()[materias[qMat]];
                somaDis += notaAtual;

                if (notaAtual > maioresNotas[qMat]) {
                    maioresNotas[qMat] = notaAtual;
                }
            }

            double media = somaDis / quantosEstudantes;

            if (media < menorMedia) {
                materiaMenorMedia = qMat;
            }
        }

        out.println("A maior nota da materia de menor media, que é " + String.valueOf(materias[materiaMenorMedia]) + ", é: " + maioresNotas[materiaMenorMedia]);
    }

    public static void menorNotaMaiorMedia(){
        double maiorMedia = 0;
        int materiaMaiorMedia = 0;
        double[] menoresNotas = new double[materias.length];

        for (int qMat = 0; qMat < materias.length; qMat++) {
            double somaDis = 0;
            menoresNotas[qMat] = 0;

            for (int estudante = 0; estudante < quantosEstudantes; estudante++) {
                double notaAtual = estudante.getNotas()[materias[qMat]];
                somaDis += notaAtual;

                if (notaAtual < menoresNotas[qMat]) {
                    menoresNotas[qMat] = notaAtual;
                }
            }

            double media = somaDis / quantosEstudantes;

            if (media > maiorMedia) {
                materiaMaiorMedia = qMat;
            }
        }

        out.println("A menor nota da materia de maior media, que é " + String.valueOf(materias[materiaMaiorMedia]) + ", é: " + menoresNotas[materiaMaiorMedia]);
    }

}
