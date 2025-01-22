package com.ai_offshore.tools.web.wbs.common.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.ai_offshore.tools.web.wbs.common.exception.BusinessException;

/**
 * 共通の例外ハンドラー
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * ビジネス例外のハンドリング
     */
    @ExceptionHandler(BusinessException.class)
    public RedirectView handleBusinessException(BusinessException e, RedirectAttributes redirectAttributes) {
        log.warn(e.getMessage());
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        // モデル属性が存在する場合は保持
        if (e.getModel() != null) {
            redirectAttributes.addFlashAttribute("project", e.getModel());
        }
        return new RedirectView(e.getRedirectPath());
    }
    
    /**
     * システム例外のハンドリング
     */
    @ExceptionHandler(Exception.class)
    public RedirectView handleException(Exception e, RedirectAttributes redirectAttributes) {
        log.error("予期せぬエラーが発生しました", e);
        redirectAttributes.addFlashAttribute("errorMessage", "システムエラーが発生しました");
        return new RedirectView("/error");
    }
} 