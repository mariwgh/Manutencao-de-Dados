# Projeto I - Manutenção de Dados

Este projeto consiste em um programa de manutenção de registros (dados relacionados) desenvolvido em Java. Ele utiliza classes que implementam interfaces para gerenciar operações como leitura, gravação, inclusão, exclusão e ordenação de dados. A aplicação foi projetada para lidar com uma coleção de registros de estudantes e realizar diversas operações sobre esses dados.
O projeto desenvolve uma aplicação em Java que permite gerenciar dados de estudantes, como suas notas em diferentes disciplinas. A aplicação é capaz de realizar operações como inclusão, exclusão, alteração e pesquisa de dados. Além disso, é possível gerar estatísticas relacionadas ao desempenho dos estudantes.

## Tabela de Conteúdos

1. [Descrição](#descrição)
2. [Detalhes](#detalhes)
3. [Autores](#autores)
4. [Funcionalidades](#funcionalidades)
5. [Tecnologias Utilizadas](#tecnologias-utilizadas)
6. [Pré-requisitos](#pré-requisitos)
7. [Instalação](#instalação)
8. [Links Úteis](#links-úteis)
9. [Convenções de Código](#convenções-de-código)

## Detalhes

- Início: 03/10/2024
- Término: 15/10/2024

## Autores

- Mariana Marietti - Desenvolvedor
- Rafaelly Silva - Desenvolvedor

## Funcionalidades

- Operações CRUD (Create, Read, Update, Delete)**: Permite leitura, gravação, inclusão, exclusão e atualização dos dados dos estudantes.
- Estatísticas:
  - Disciplina com maior número de aprovações.
  - Disciplina com maior número de retenções.
  - Estudante com a maior média de notas.
  - Análise das disciplinas com maior e menor notas.
  - Cálculo da média aritmética por disciplina.
  - Identificação do estudante com maior e menor nota em disciplinas específicas.
- Interface de Vetores: Implementação de métodos como leituraDosDados, gravarDados, existe, incluirNoFinal, excluir, ordenar, entre outros.

## Tecnologias Utilizadas

- Java 11
- Swing para interface gráfica
- AWT para manipulação de gráficos

## Pré-requisitos

- JDK (Java Development Kit): Instalação da versão 11 ou superior.
- Conhecimentos Necessários:
  - Orientação a Objetos: Entendimento de conceitos como herança, polimorfismo e encapsulamento.
  - Manipulação de arquivos: Leitura e escrita de arquivos de texto.
  - Manipulação de eventos: ActionListener e MouseListener.
  - Estruturas de dados: Implementação de vetores, listas e métodos para organizar e buscar dados.

## Instalação

1. Instalar o JDK (Java Development Kit):
  - Passo 1: Acesse o site oficial da Oracle ou Adoptium (para versões OpenJDK).
  - Passo 2: Baixe e instale o JDK mais recente. (Durante a instalação, certifique-se de adicionar o Java ao PATH do sistema.)

2. Instalar o IntelliJ IDEA (ou outra IDE):
  - Passo 1: Acesse o site oficial do IntelliJ IDEA.
  - Passo 2: Baixe e instale a versão Community (gratuita) ou a versão Ultimate (paga).
  - Passo 3: Durante a instalação, aceite as opções padrão e selecione o JDK instalado na etapa anterior como o SDK padrão.

3. Instalar o Git:
  - Passo 1: Acesse o site oficial do Git e baixe o instalador:
  - Passo 2: Execute o instalador e siga as etapas de instalação. Durante a instalação, você pode manter as opções padrão. Certifique-se de marcar a opção para adicionar o Git ao PATH do sistema.
  - Passo 3: Verifique a instalação.
      Abra o Prompt de Comando e digite: git --version

4. Conectar o Github:
   - Passo 1: Vá para File > Settings > Version Control > GitHub > e conecte sua conta.
    
5. Abrir o repositório no Intellij:
  - Passo 1: Abra o IntelliJ e selecione Get from VCS para abrir o repositório do projeto (selecione por Github).
  - Passo 2: Certifique de selecionar pelo GitHub e que o directory esteja em um local seguro (caso o Intellij não o reconheça como, digite no terminal (View > Tool Windows > Terminal): git config --global --add safe.directory <caminho_do_projeto>
  - Passo 3: Faça as mudanças necessárias e de commit selecionando os arquivos, não esqueça de dar push também. (Logo, os arquivos irão aparecer para todos os colaboradores no projeto no GitHub.)
  - Passo 4: Caso peça para fazer login no Git, coloque seu nome e o e-mail da Unicamp.
  - Passo 5: Para executar o projeto, apenas clique no botão de play.

## Links Úteis

- [Programação gráfica em Java com Swing](https://docs.oracle.com/javase/tutorial/uiswing/)
- [Uso de JFileChooser para abrir e salvar arquivos](https://docs.oracle.com/javase/tutorial/uiswing/components/filechooser.html)

## Convenções de Código
- Utilize indentação de 4 espaços.
- Nomeie variáveis e métodos em inglês.
- Utilize o padrão camelCase para métodos e variáveis.
