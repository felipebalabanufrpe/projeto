package com.miromori.sympla.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class Usuario {
    private String nome;
    private String senha;
    private List<Evento> eventos;
}
