package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/terceros")
public class TerceroController {
    //Inyeccion del servicio para acceder a metodos y BD
    @Autowired
    private TerceroService terceroService;

    //Metodo para mostrar la lista de terceros y el formulario
    @GetMapping
    public String listarTerceros(Model model){
        //Pasa la lista de terceros de la BD a la vista
        model.addAttribute("terceros", terceroService.listarTodos());
        model.addAttribute("tercero", new Tercero()); // cambié terceros por tercero porque se sobrescribe el objeto del formulario
        return "terceros";
    }

    //Metodo para procesar envio del formulario y guardar en BD
    @PostMapping("/guardar")
    public String guardarTercero(@ModelAttribute("tercero") Tercero tercero){
        // Ejecuta el procedimiento de almacenado
        terceroService.guardarTercero(tercero);

        
        //Redirige a la lista
        return "redirect:/terceros";
    }

    //Metodo para actualizar datos desde la web
    @PostMapping("/actualizar") // lo añadí para evitar que sobrescriba el controlador general
    public String actualizarTercero(@ModelAttribute("tercero") Tercero tercero){
        terceroService.actualizarTercero(tercero);
        return "redirect:/terceros";
    }

    //Eliminar un tercero por su ID
    @GetMapping("/eliminar/{id}")
    public String eliminarTercero(@PathVariable("id") Long id){
        terceroService.eliminarTercero(id);
        return "redirect:/terceros";
    }
}
