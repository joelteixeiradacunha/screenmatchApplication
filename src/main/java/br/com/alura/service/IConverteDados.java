package br.com.alura.service;

public interface IConverteDados {
    // Retorna alguma coisa do TIPO genérico
    <T> T obterDados(String json, Class<T> classe);
}
