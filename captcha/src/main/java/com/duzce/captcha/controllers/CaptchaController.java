package com.duzce.captcha.controllers;


import com.duzce.captcha.model.Captcha;
import com.duzce.captcha.service.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/image")
    public ResponseEntity<byte[]> getCaptchaImage(HttpSession session) {
        Captcha captcha = captchaService.getNewCaptcha(session);
        if (captcha == null) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(captcha.getImage(), headers, HttpStatus.OK);
    }
    @PostMapping("/validate")
    public ResponseEntity<Boolean> validateCaptcha(HttpSession session, @RequestParam String code) {
        System.out.println(code);
        boolean isValid = captchaService.validateCaptcha(session, code);
        return ResponseEntity.ok(isValid);
    }

}