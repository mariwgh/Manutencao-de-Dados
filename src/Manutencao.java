import javax.swing.*;
import java.io.*;
import java.util.Scanner;
import static java.lang.System.*;


public class Manutencao {
    enum Ordens {porRa, porNome, porCurso, porMedia}
    private static Ordens ordemAtual = Ordens.porRa;

    private static int indiceEstudanteDestaque;

    private static String[] materias;

    static Scanner leitor = new Scanner(in);
    static boolean continuarPrograma = true;

    static ManterEstudantes objeto = new ManterEstudantes();

    public static void main(String[] args) throws Exception {
        objeto.inicializador(50);

        for (int ind = 0; ind < 50; ind++) {
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


    public static void seletorDeOpcoes() throws Exception {
        int opcao;
        do {
            out.println("\nOpções:\n");
            out.println("0 - Terminar programa");
            out.println("1 - Incluir estudante");
            out.println("2 - Listar estudantes");
            out.println("3 - Excluir estudante");
            out.println("4 - Listar situações");
            out.println("5 - Digitar notas de estudante");
            out.println("6 - Ordenar por curso");
            out.println("7 - Ordenar por nome");
            out.println("8 - Ordenar por média");
            out.println("9 - Estatísticas");
            out.print("\nSua opção: \n");
            opcao = leitor.nextInt();
            leitor.nextLine();      // necessário após nextInt() para poder ler strings a seguir

            switch(opcao) {
                case 1 : incluirEstudante(); break;
                case 2 : listarEstudantes(); break;
                case 3 : excluirEstudante(); break;
                case 4 : lerMaterias(); listarSituacoes(); break;
                case 5 : digitarNotas(); break;
                case 6 : ordenarPorCurso(); break;
                case 7 : ordenarPorNome(); break;
                case 8 : ordenarPorMedia(); break;
                case 9 : lerMaterias();estatisticas(); break;
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

    public static void lerMaterias() throws IOException {
        materias = new String[15];
        BufferedReader leitor = new BufferedReader(new FileReader("Materias.txt"));
        String linha = "";
        int indice = 0;
        while (linha != null) {
            linha = leitor.readLine();
            if (linha != null){
                materias[indice] = linha;
                indice++;
            }
        }
    }

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

        if (objeto.existe(umEstudante))
            JOptionPane.showMessageDialog(null,"Estudante repetido!");
        else {
            objeto.incluirNoFinal(umEstudante);  // última posição usada
        }
    }

    public static void listarEstudantes() {
        out.println("\nListagem de Estudantes\n");
        out.println("\nCurso RA Nome                Materias   Biolog Matema Portug TecPro Quimic\n");

        int contLinha = 0;  // contador de linhas

        for (int ind = 0; ind < objeto.qtosDados; ind++) {
            out.println(objeto.valorDe(ind));

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

    public static void listarSituacoes() {
        out.println("\n\nSituação estudantil\n");
        out.println("\nMédia Situação      Curso RA Nome                Materias   Biolog Matema Portug TecPro Quimic\n");

        String situacao;

        double[] aprovacoes = new double[materias.length];
        double[] retencoes = new double[materias.length];

        for (int indice = 0; indice < objeto.qtosDados; indice++) {
            double[] notasDesseEstudante = objeto.dados[indice].getNotas(); // obter notas do estudante atual

            // verifica cada matéria
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
            }

            out.printf("%4.1f %16s " + objeto.dados[indice] + "\n", mediaDesseEstudante, situacao);
        }

        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }

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
        out.println(objeto.qtosDados);
        Estudante aux = null;
        //o quantos dados está como zero
        for (int lento=0; lento <= objeto.qtosDados-1; lento++){
            for (int rapido = lento+1; rapido <= objeto.qtosDados-1; rapido++){
                double cursoLento = Double.parseDouble(objeto.dados[lento].getCurso());
                double cursoRapido = Double.parseDouble(objeto.dados[rapido].getCurso());
                if (cursoRapido < cursoLento) {
                    objeto.trocar(rapido, lento);
                }
            }
        }

        ordemAtual = Ordens.porCurso;
    }

    private static void ordenarPorNome() {
        for (int lento = 0; lento < objeto.qtosDados; lento++){
            for (int rapido=lento+1; rapido < objeto.qtosDados; rapido++){
                String nomeLento = objeto.dados[lento].getNome();
                String nomeRapido = objeto.dados[rapido].getNome();
                if (nomeLento.compareTo(nomeRapido) > 0)
                    objeto.trocar(lento, rapido);
            }
        }
        ordemAtual = Ordens.porNome;
    }

    private static void ordenarPorMedia() {
        for (int lento=0; lento <= objeto.qtosDados-1; lento++) {
            for (int rapido=lento+1; rapido <= objeto.qtosDados-1; rapido++){
                double mediaLenta = objeto.dados[lento].mediaDasNotas();
                double mediaRapida = objeto.dados[rapido].mediaDasNotas();
                if (mediaLenta < mediaRapida)
                    objeto.trocar(rapido, lento);
            }

            ordemAtual = Ordens.porMedia;
        }
    }


    public static void estatisticas() {
        //disciplinaMaiorAprovacao e Retencao
        //descobrir a quantidade de aprovados d disciplina com mais aprovacao e o nome da materia
        //descobrir a quantidade de aprovados d disciplina com mais retencao e o nome da materia

        int maxRetidos = 0;
        String disciplinaMaiorRetidos = "";

        int maxAprovados = 0;
        String disciplinaMaiorAprovacao = "";

        int[] aprovadosPorDisciplina = new int[materias.length];
        int[] retidosPorDisciplina = new int[materias.length];

        double possivelMedia = 0;

        for (int i = 0; i < objeto.qtosDados; i++) {
            Estudante estudante = objeto.dados[i];

            for (int j = 0; j < estudante.getNotas().length; j++) {
                if (estudante.getNotas()[j] >= 5) {
                    aprovadosPorDisciplina[j]++;
                    if (aprovadosPorDisciplina[j] > maxAprovados) {
                        maxAprovados = aprovadosPorDisciplina[j];
                        disciplinaMaiorAprovacao = materias[j];
                    }
                }
                if (estudante.getNotas()[j] < 5) {
                    retidosPorDisciplina[j]++;
                    if (retidosPorDisciplina[j] > maxRetidos) {
                        maxRetidos = retidosPorDisciplina[j];
                        disciplinaMaiorRetidos = materias[j];
                    }
                }
            }

            //estudanteMaiorMediaNotas

            double mediaAgora = objeto.dados[i].mediaDasNotas();

            if (mediaAgora > possivelMedia) {
                possivelMedia = mediaAgora;
                indiceEstudanteDestaque = i;
            }
        }

        out.println("\nDisciplina com maior número de estudantes aprovados: " + disciplinaMaiorAprovacao);
        out.println("Disciplina com maior número de estudantes retidos: " + disciplinaMaiorRetidos);
        out.println("O estudante com maior média de notas é: " + objeto.dados[indiceEstudanteDestaque].getNome());


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

        out.println("\nA matéria que o estudante destaque tem a maior nota é: " + materiaMaiorNota);
        out.println("A matéria que o estudante destaque tem a menor nota é: " + materiaMenorNota + "\n");


        //mediaAritmeticaAlunoEmDis
        //maiorNotaMenorMedia
        //menorNotaMaiorMedia

        double somaDis;
        double menorMedia = 0;
        double maiorMedia = 0;
        int indMateriaMenorMedia = 0;
        int indMateriaMaiorMedia = 0;
        double[] maioresNotas = new double[materias.length];
        double[] menoresNotas = new double[materias.length];

        for (int qMat = 0; qMat < materias.length; qMat++) {
            String materia = materias[qMat];
            somaDis = 0.0;  //reseta a soma para cada disciplina

            for (int estudante = 0; estudante < objeto.qtosDados; estudante++) {
                double notaAtual = objeto.dados[estudante].getNotas()[qMat];
                somaDis += notaAtual;

                if (notaAtual > maioresNotas[qMat]) {
                    maioresNotas[qMat] = notaAtual;
                }

                if (notaAtual < menoresNotas[qMat]) {
                    menoresNotas[qMat] = notaAtual;
                }
            }

            double media = somaDis / objeto.qtosDados;

            if (media < menorMedia) {
                menorMedia = media;
                indMateriaMenorMedia = qMat;
            }

            if (media > maiorMedia) {
                maiorMedia = media;
                indMateriaMaiorMedia = qMat;
            }

            out.println("A media de " + materia + " é: " + media);
        }

        out.println("\nA maior nota da materia de menor media, que é " + materias[indMateriaMenorMedia] + ", é: " + maioresNotas[indMateriaMenorMedia]);
        out.println("A menor nota da materia de maior media, que é " + materias[indMateriaMaiorMedia] + ", é: " + menoresNotas[indMateriaMaiorMedia]);

        out.print("\n\nTecle [Enter] para prosseguir: ");
        leitor.nextLine();
    }
}
