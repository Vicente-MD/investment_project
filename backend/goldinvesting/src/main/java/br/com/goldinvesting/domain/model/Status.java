package br.com.goldinvesting.domain.model;

public enum Status {
    ACTIVE,         // Ativo atualmente negociado
    INACTIVE,       // Ativo não mais negociado
    PENDING,        // Ativo aguardando alguma condição para ser ativo
    SOLD,           // Ativo que foi vendido
    PURCHASED,      // Ativo que foi comprado mas não está mais na carteira
    UNDER_REVIEW    // Ativo que está sendo analisado
}
