package com.runasimi_edu.backend.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.runasimi_edu.backend.model.Grado;
import com.runasimi_edu.backend.repository.GradoRepository;
import com.runasimi_edu.backend.service.GradoService;

@RestController
@RequestMapping("/api/grados")
public class GradoController {

    private final GradoService gradoService;
    private final GradoRepository gradoRepository;

    public GradoController(GradoService gradoService, GradoRepository gradoRepository) {
        this.gradoService = gradoService;
        this.gradoRepository = gradoRepository;
    }

        // 1. Listar todos los grados ordenados
        @GetMapping
    public ResponseEntity<?> listarTodos() {
        System.out.println("Entró al método listarTodos()");
        try {
            List<Grado> grados = gradoRepository.findByOrderByOrdenAsc();
            System.out.println("Grados encontrados: " + grados.size());
            
            // Crear una respuesta simple para evitar problemas de serialización
            List<Map<String, Object>> respuestaSimple = grados.stream()
                .map(grado -> {
                    Map<String, Object> gradoMap = new HashMap<>();
                    gradoMap.put("id", grado.getId());
                    gradoMap.put("nombre", grado.getNombre());
                    gradoMap.put("descripcion", grado.getDescripcion());
                    gradoMap.put("orden", grado.getOrden());
                    return gradoMap;
                })
                .collect(Collectors.toList());
            
            System.out.println("Respuesta preparada: " + respuestaSimple.size() + " grados");
            return ResponseEntity.ok(respuestaSimple);
        } catch (Exception e) {
            System.err.println("Error al listar grados: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }


    // 2. Obtener un grado por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Grado> obtenerPorId(@PathVariable Long id) {
        return gradoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // 3. Crear un nuevo grado
    @PostMapping
    public ResponseEntity<Grado> crear(@RequestBody Grado grado) {
        System.out.println("Entró al método crear()");
        System.out.println("Se recibió solicitud para crear grado: " + grado.getNombre());
        try {
            Grado nuevo = gradoService.crearGrado(grado);
            return ResponseEntity.ok(nuevo);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(null);
        }
    }

    // 4. Actualizar un grado existente
    @PutMapping("/{id}")
    public ResponseEntity<Grado> actualizar(@PathVariable Long id, @RequestBody Grado grado) {
        try {
            Grado actualizado = gradoService.actualizarGrado(id, grado);
            return ResponseEntity.ok(actualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 5. Eliminar un grado por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            gradoService.eliminarGrado(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // 6. Buscar grados por coincidencia de nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<Grado>> buscarPorNombre(@RequestParam String nombre) {
        List<Grado> resultados = gradoService.buscarPorNombre(nombre);
        return ResponseEntity.ok(resultados);
    }
}
