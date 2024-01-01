package com.mateuspaz.enviosmtp.domain;

public record Email (String emailTo, String assunto, String mensagem) {}
