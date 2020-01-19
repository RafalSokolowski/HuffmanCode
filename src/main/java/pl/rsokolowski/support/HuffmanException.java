package pl.rsokolowski.support;

public class HuffmanException extends RuntimeException {
    private final String exceptionMessage;

    public HuffmanException(String exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        Support.logger.error(exceptionMessage);
        throw new IllegalArgumentException(exceptionMessage);
    }
}
