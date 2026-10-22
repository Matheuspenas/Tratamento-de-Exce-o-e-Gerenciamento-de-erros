package br.edu.pits.excecao;

/** Indica que não foi possível garantir a gravação da trilha de auditoria. */
public final class FalhaAuditoriaException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public FalhaAuditoriaException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
