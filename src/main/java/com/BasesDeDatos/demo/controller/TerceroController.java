package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        model.addAttribute("terceros", new Tercero());
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
    
}
