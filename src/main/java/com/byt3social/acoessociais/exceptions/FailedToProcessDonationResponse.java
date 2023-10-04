package com.byt3social.acoessociais.exceptions;

public class FailedToProcessDonationResponse extends RuntimeException {
    public FailedToProcessDonationResponse() {
        super("Não foi possível processar os detalhes da sua doação");
    }
}
