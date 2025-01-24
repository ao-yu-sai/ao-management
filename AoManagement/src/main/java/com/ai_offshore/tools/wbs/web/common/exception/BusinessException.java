package com.ai_offshore.tools.wbs.web.common.exception;

/**
 * ビジネスロジックの例外クラス
 */
public class BusinessException extends RuntimeException {
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private final Object model;
    private final String redirectPath;

    public BusinessException(String message, Object model, String redirectPath) {
        super(message);
        this.model = model;
        this.redirectPath = redirectPath;
    }
    
    public BusinessException(String message, Object model, String redirectPath, Throwable cause) {
        super(message, cause);
        this.model = model;
        this.redirectPath = redirectPath;
    }

    public Object getModel() {
        return model;
    }

    public String getRedirectPath() {
        return redirectPath;
    }
} 