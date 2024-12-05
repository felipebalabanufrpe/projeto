package com.miromori.sympla.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class Evento {
    private String descricao;
    private LocalDateTime hora;
    private LocalDateTime data;
    private String local;
    private Double preco;
    private Integer inscricoesMaximas;
    private List<String> feedbacks;
}
