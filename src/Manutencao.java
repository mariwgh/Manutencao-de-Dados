import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;

//ele quer que no Manutencao só use o ManterEstudantes e nao use o vetor estud
//private static Estudante[] estud;             // vetor de estudantes

public class Manutencao {
    enum Ordens {porRa, porNome, porCurso, porMedia}
    private static Ordens ordemAtual = Ordens.porRa;

    //private static ManterEstudantes[] mantEstud;

    //private static int quantosEstudantes;           // tamanho lógico do vetor estud
    private static int indiceEstudanteDestaque;  //da onde veio isso

    //private static BufferedReader arquivoDeEntrada;
    //private static BufferedWriter arquivoDeSaida;

    private static String[] materias;

    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma = true;
    //static int onde;                            // índice resultante da pesquisa binária

    static ManterEstudantes objeto = new ManterEstudantes();

    public static void main(String[] args) throws Exception {
        objeto.ManterEstudantes(50);
        //mantEstud = new ManterEstudantes[3];                        // 50 - tamanho físico (mas pq 3???)

        for (int ind = 0; ind < 50; ind++) {
            //mantEstud[ind] = new ManterEstudantes(mantEstud[ind]); // criar objetos Estudante vazios no vetor
            objeto.incluirNoFinal(new Estudante());
        }

        objeto.qtosDados = 0;                                  // tamanho lógico (vetor vazio)

        objeto.leituraDosDados("DadosEstudantes.txt");

        if (continuarPrograma) {
            seletorDeOpcoes();
            objeto.gravarDados("DadosEstudantes.txt");
        }

        out.println("\nPrograma encerrado.");
    }

    //tirei preencher vetor ( e o mesmo leiturados dados)
    //tirei gsalvar vetor ( e o mesmo garvar dados)

    public static void seletorDeOpcoes() throws Exception {
        int opcao;
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
                case 9 : disciplinaMaiorAprovacao(); break;
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

    /*public static boolean existeEstudante(Estudante estProcurado) {
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
    }*/

    public static void lerMaterias() {
        try {
            BufferedReader arquivo = new BufferedReader(new FileReader("Materias.txt"));

            int numMaterias = objeto.dados[0].getQuantasNotas();

            materias = new String[numMaterias];

            for (int i = 0; i < numMaterias; i++) {
                materias[i] = arquivo.readLine();
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //faz sentido estar no outro


    //tem no outro
    public static void incluirEstudante() throws Exception {
        if (ordemAtual != Ordens.porRa)
            objeto.ordenar();

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
        if (objeto.existe(umEstudante))  // ajusta a variável onde
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else {
            objeto.incluirNoFinal(umEstudante);  // última posição usada
        }
    }

    public static void listarEstudantes() {
        out.println("\n\nListagem de Estudantes\n");

        //int contLinha = 0;  // contador de linhas

        for (int ind = 0; ind < objeto.qtosDados; ind++) {
            out.println(objeto.valorDe(ind));


            /*if (++contLinha >= 20) {       // se exibiu 20 linhas, espera Enter
                out.print("\n\nTecle [Enter] para prosseguir: ");
                leitor.nextLine();
                contLinha = 0;      // reinicia o contador de linhas
            }*/
        }

        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

    public static void excluirEstudante() throws Exception {
        if (ordemAtual != Ordens.porRa)
            objeto.ordenar();
        out.println("Excluir Estudante\n");
        out.print("RA    : ");
        String ra = leitor.nextLine();

        Estudante umEstudante = new Estudante(" ", ra, " ");

        if (!objeto.existe(umEstudante)) {  // ajusta a variável onde
            JOptionPane.showMessageDialog(null, "Estudante não encontrado!");
        }
        else { // achou o estudante procurado, no índice "onde" do vetor
            objeto.excluir(objeto.getPosicaoAtual());
            out.println("Excluído com sucesso!");
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
        String situacao;
        //double estudanteDestaque = Double.MAX_VALUE;

        double[] aprovacoes = new double[materias.length];
        double[] retencoes = new double[materias.length];


        //for (int indice = 0; indice < quantosEstudantes; indice++) {
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


        for (int indice = 0; indice < objeto.qtosDados; indice++) {
            double[] notasDesseEstudante = objeto.dados[indice].getNotas(); // Obter notas do estudante atual

            // verifica cada matéria??
            for (int i = 0; i < notasDesseEstudante.length; i++) {
                if (notasDesseEstudante[i] >= 5) {
                    aprovacoes[i]++;
                } else {
                    retencoes[i]++;
                }
            }

            double mediaDesseEstudante = objeto.dados[indice].mediaDasNotas();

            if (mediaDesseEstudante < 5)
                situacao = "Não promovido(a)";
            else {
                situacao = "Promovido(a)    ";
//                if (mediaDesseEstudante > estudanteDestaque) {
//                    estudanteDestaque = mediaDesseEstudante;
//                }
            }

            out.printf("%4.1f %16s " + objeto.dados[indice] + "\n", mediaDesseEstudante, situacao);
        }
        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
    //}

    private static void digitarNotas() {
        out.println("Digitação de notas de estudante:\n");
        out.print("Digite o RA do(a) estudante desejado(a): ");
        String raEstudante = leitor.nextLine();

        try {
            Estudante estProc = new Estudante("00", raEstudante, "A");
            if (!objeto.existe(estProc))
                out.println("Não há um(a) estudante com esse RA!");
            else {  // se RA foi encontrado, variável onde contém seu índice
                out.print("Quantidade de notas a serem digitadas: ");
                int quant = leitor.nextInt(); // depois de ler int, ler nextline()
                leitor.nextLine(); // necessário após nextInt() para poder ler strings a seguir

                objeto.dados[objeto.getPosicaoAtual()].setQuantasNotas(quant);
                double nota;
                for (int indNota = 0; indNota < quant; indNota++) {
                    do {
                        out.printf("Digite a %da. nota:", indNota + 1);
                        nota = leitor.nextDouble();
                        if (nota >= 0 && nota <= 10)
                            break;  // sai do do-while
                        out.println("Nota inválida. Digite novamente:");
                    } while (true);
                    objeto.dados[objeto.getPosicaoAtual()].setNota(nota, indNota);
                }
            }
        }
        catch (Exception erro) {
            out.println("Não foi possivel criar objeto Estudante.");
            out.println(erro.getMessage());
        }
    }

    private static void ordenarPorCurso() {
        ManterEstudantes objeto = new ManterEstudantes();

        for (int lento=0; lento < objeto.qtosDados; lento++)
            for (int rapido = lento+1; rapido < objeto.qtosDados; rapido++)
                if (objeto.dados[lento].getCurso().compareTo(objeto.dados[rapido].getCurso()) > 0)
                    objeto.trocar(lento, rapido);
        ordemAtual = Ordens.porCurso;
    }

    //passa p outro arq
    /*private static void ordenarPorRa() {
        ManterEstudantes objeto = new ManterEstudantes();

        for (int lento=0; lento < quantosEstudantes; lento++)
            for (int rapido=lento+1; rapido < quantosEstudantes; rapido++)
                if (mantEstud[lento].getRa().compareTo(mantEstud[rapido].getRa()) > 0)
                    objeto.trocar(lento, rapido);
        ordemAtual = Ordens.porRa;
    }
*/
    private static void ordenarPorNome() {
        ManterEstudantes objeto = new ManterEstudantes();
        //nao entendi pq tem o metodo ordenar em manter estudantes se nao e utilizado aq
        for (int lento = 0; lento < objeto.qtosDados; lento++)
            for (int rapido=lento+1; rapido < objeto.qtosDados; rapido++)
                if (objeto.dados[lento].getNome().compareTo(objeto.dados[rapido].getNome()) > 0)
                    objeto.trocar(lento, rapido);
        ordemAtual = Ordens.porNome;
    }

    private static void ordenarPorMedia() {
        ManterEstudantes objeto = new ManterEstudantes();

        for (int lento=0; lento < objeto.qtosDados; lento++) {
            double mediaAtual = objeto.dados[lento].mediaDasNotas();
            for (int rapido=lento+1; rapido < objeto.qtosDados; rapido++)
                if (mediaAtual > objeto.dados[rapido].mediaDasNotas())
                    objeto.trocar(lento, rapido);
            ordemAtual = Ordens.porMedia;
        }
    }

    public static void disciplinaMaiorAprovacao() {
        int[] aprovadosPorDisciplina = new int[materias.length];

        for (int i = 0; i < objeto.qtosDados; i++) {
            Estudante estudante = objeto.dados[i];
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
                disciplinaMaiorAprovacao = materias[i];
            }
        }

        out.println("Disciplina com maior número de estudantes aprovados: " + disciplinaMaiorAprovacao);
    }

    public static void disciplinaMaiorRetencao() {
        int[] retidosPorDisciplina = new int[materias.length];

        for (int i = 0; i < objeto.qtosDados; i++) {
            Estudante estudante = objeto.dados[i];
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
                disciplinaMaiorRetidos = materias[i];
            }
        }

        out.println("Disciplina com maior número de estudantes retidos: " + disciplinaMaiorRetidos);
    }

    public static void estudanteMaiorMediaNotas() {
        double possivelMedia = 0;

        for (int i = 0; i < objeto.qtosDados; i++) {
            double mediaAgora = objeto.dados[i].mediaDasNotas();

            if (mediaAgora > possivelMedia) {
                possivelMedia = mediaAgora;
                indiceEstudanteDestaque = i;
            }
        }

        out.println("O estudante com maior média de notas é: " + objeto.dados[indiceEstudanteDestaque].getNome());
    }

    public static void maiorEMenorNotaEstudanteDestaque() {
        //pegar o indice do estudante destaque e fzr um substring com
        //maior = double.maxvalue e min = double.minvalue
        //e depois fazer if

        //nao entendi pq substring mas ta

        //materia com maior e a com  menor nota do estudante destaque

        double maiorNota = Double.MIN_VALUE;
        double menorNota = Double.MAX_VALUE;
        String materiaMaiorNota = "";
        String materiaMenorNota = "";

        double[] notas = objeto.dados[indiceEstudanteDestaque].getNotas();

        for (int i = 0; i < notas.length; i++) {
            double nota = notas[i];
            String materiaAtual = materias[i];

            if (nota >= maiorNota) {
                maiorNota = nota;
                materiaMaiorNota = materiaAtual;
            }

            if (nota <= menorNota) {
                menorNota = nota;
                materiaMenorNota = materiaAtual;
            }
        }

        out.println("A matéria que o estudante destaque tem a maior nota é: " + materiaMaiorNota);
        out.println("A matéria que o estudante destaque tem a menor nota é: " + materiaMenorNota);
    }

    public static void mediaAritmeticaAlunoEmDis() {
        double somaDis = 0.0;

        for (int qMat = 0; qMat < materias.length; qMat++) {
            String materia = String.valueOf(materias[qMat]);

            for (int estudante = 0; estudante < objeto.qtosDados; estudante++) {
                somaDis = objeto.dados[estudante].getNotas()[qMat];
            }

            double media = somaDis / objeto.qtosDados;
            out.println("A media da materia " + materia + " é: " + media);
        }
    }

    public static void maiorNotaMenorMedia() {
        double menorMedia = 0;
        int materiaMenorMedia = 0;
        double[] maioresNotas = new double[materias.length];

        for (int qMat = 0; qMat < materias.length; qMat++) {
            double somaDis = 0;
            maioresNotas[qMat] = 0;

            for (int estudante = 0; estudante < objeto.qtosDados; estudante++) {
                double notaAtual = objeto.dados[estudante].getNotas()[qMat];
                somaDis += notaAtual;

                if (notaAtual > maioresNotas[qMat]) {
                    maioresNotas[qMat] = notaAtual;
                }
            }

            double media = somaDis / objeto.qtosDados;

            if (media < menorMedia) {
                materiaMenorMedia = qMat;
            }
        }

        out.println("A maior nota da materia de menor media, que é " + materias[materiaMenorMedia] + ", é: " + maioresNotas[materiaMenorMedia]);
    }

    public static void menorNotaMaiorMedia(){
        double maiorMedia = 0;
        int materiaMaiorMedia = 0;
        double[] menoresNotas = new double[materias.length];

        for (int qMat = 0; qMat < materias.length; qMat++) {
            double somaDis = 0;
            menoresNotas[qMat] = 0;

            for (int estudante = 0; estudante < objeto.qtosDados; estudante++) {
                double notaAtual = objeto.dados[estudante].getNotas()[qMat];
                somaDis += notaAtual;

                if (notaAtual < menoresNotas[qMat]) {
                    menoresNotas[qMat] = notaAtual;
                }
            }

            double media = somaDis / objeto.qtosDados;

            if (media > maiorMedia) {
                materiaMaiorMedia = qMat;
            }
        }

        out.println("A menor nota da materia de maior media, que é " + materias[materiaMaiorMedia] + ", é: " + menoresNotas[materiaMaiorMedia]);
    }

}
