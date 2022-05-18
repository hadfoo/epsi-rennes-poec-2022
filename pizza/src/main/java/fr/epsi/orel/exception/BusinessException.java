package fr.epsi.orel.exception;

public class BusinessException extends Exception {
    private static final long serialVersionUID = 1L;

    private String code;

    public BusinessException(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }
}
