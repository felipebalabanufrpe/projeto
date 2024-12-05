package com.miromori.sympla.repository;

import com.miromori.sympla.entity.Evento;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class EventosRepository {
    private List<Evento> eventos;
}
