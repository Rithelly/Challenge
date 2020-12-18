package br.com.challenge.starwars.exception.exceptionhandler;

import lombok.Getter;

@Getter
public enum ProblemType {

    ENTIDADE_EM_USO("Entidade em Uso", "/entidade-em-uso"),
    MENSAGEM_INCOMPREENSIVEL("Mensagem Incompreensível", "mensagem-incompreensivel"),
    PARAMETRO_INVALIDO("Parâmetro Inválido", "/parametro-invalido"),
    RECURSO_NAO_ENCONTRADO("Recurso não encontrado", "/recurso-nao-encontrado"),
    ERRO_DE_SISTEMA("Erro de sistema", "/erro-de-sistema"),
    DADOS_INVALIDOS("Dados Inválidos", "/dados-inválidos"),
    ERRO_DE_NEGOCIO("Erro de Negócio", "erro-de-negocio"),
    ACESSO_NEGADO("Acesso Negado", "/acesso-negado"),
    ERRO_SEMANTICO("Erro Semântico", "/erro-semantico");

    private String title;
    private String uri;

    ProblemType(String title, String path) {
        this.uri = path;
        this.title = title;
    }
}
