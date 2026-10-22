package br.edu.pits.excecao;

/** Indica falha técnica ao consultar ou persistir dados do PITS. */
public final class RepositorioIndisponivelException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RepositorioIndisponivelException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
