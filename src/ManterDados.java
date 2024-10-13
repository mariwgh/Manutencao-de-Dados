//interface
import java.io.IOException;

public interface ManterDados {
        enum Situacao {navegando, incluindo, excluindo, alterando, editando,
            buscando
        };
        void leituraDosDados(String nomeArquivo);
        void gravarDados(String nomeArquivo) throws IOException;
        Boolean existe(Estudante dadoProcurado);                // pesquisa binária
        void incluirNoFinal(Estudante novoDado);
        void incluirEm(Estudante novoDado, int posicaoDeInclusao);
        void excluir(int posicaoDeExclusao);
        Estudante valorDe(int indiceDeAcesso);
        void alterar(int posicaoDeAlteracao, Estudante novoDado);
        void trocar(int origem, int destino);
        void ordenar();
        Boolean estaVazio();
        Boolean estaNoInicio();
        Boolean estaNoFim();
        void irAoInicio();                      // posicaoAtual fica no início do vetor
        void irAoFim();                         // posiçãoAtual fica no fim do vetor
        void irAoAnterior();                    // posiçãoAtual retrocede à posição anterior
        void irAoProximo();
        Situacao getSituacao();
        void setSituacao(Situacao novaSituacao);
}
