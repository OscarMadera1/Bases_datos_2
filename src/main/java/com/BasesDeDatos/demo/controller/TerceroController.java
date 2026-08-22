package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/terceros")
public class TerceroController {
    //Inyección del servicio para acceder a métodos y BD
    @Autowired
    private TerceroService terceroService;

    //Método para mostrar la lista de terceros y el formulario
    @GetMapping
    public String listarTerceros(Model model){
        //Pasa la lista de terceros de la BD a la vista
        model.addAttribute("terceros", terceroService.listarTodos());
        model.addAttribute("tercero", new Tercero()); // cambié terceros por tercero porque se sobrescribe el objeto del formulario
        return "terceros";
    }

    //Método para procesar envío del formulario y guardar en BD
    @PostMapping("/guardar")
    public String guardarTercero(@ModelAttribute("tercero") Tercero tercero, RedirectAttributes redirectAttributes){

        // Ejecuta el procedimiento de almacenado
        try{
            terceroService.guardarTercero(tercero);
            redirectAttributes.addFlashAttribute("mensajeExito", "Tercero guardado correctamente.");
        } catch (JpaSystemException e) {
            String mensajeError = extraerMensajeOra(e);
            redirectAttributes.addFlashAttribute("mensajeError", mensajeError);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ocurrió un error inesperado al guardar.");
        }
        //Redirige a la lista
        return "redirect:/terceros";
    }

    @PostMapping("/actualizar")
    public String actualizarTercero(
            @ModelAttribute("tercero") Tercero tercero,
            RedirectAttributes redirectAttributes) {

        try {
            terceroService.actualizarTercero(tercero);
            redirectAttributes.addFlashAttribute("mensajeExito", "Tercero actualizado correctamente.");
        } catch (JpaSystemException e) {
            String mensajeError = extraerMensajeOra(e);
            redirectAttributes.addFlashAttribute("mensajeError", mensajeError);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ocurrió un error inesperado al actualizar.");
        }

        // Se redirige a la lista general /terceros para evitar la ruta inexistente /terceros/editar/id
        return "redirect:/terceros";
    }


    //Eliminar un tercero por su ID

    @GetMapping("/eliminar/{id}")
    public String eliminarTercero(@PathVariable("id") Long id, RedirectAttributes redirectAttributes){

        try {
            terceroService.eliminarTercero(id);
            redirectAttributes.addFlashAttribute("mensajeExito", "Tercero eliminado correctamente.");
        } catch (JpaSystemException e) {
            String mensajeError = extraerMensajeOra(e);
            redirectAttributes.addFlashAttribute("mensajeError", mensajeError);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Ocurrió un error inesperado al eliminar.");
        }

        return "redirect:/terceros";
    }


    private String extraerMensajeOra(Exception e) {
        Throwable cause = e.getCause();
        while (cause != null) {
            if (cause.getMessage() != null && cause.getMessage().contains("ORA-20502")) {
                String msg = cause.getMessage();
                int inicio = msg.indexOf("ORA-20502:") + "ORA-20502:".length();
                int fin = msg.indexOf("ORA-", inicio);

                if (fin != -1) {
                    return msg.substring(inicio, fin).trim();
                } else {
                    return msg.substring(inicio).split("\n")[0].trim();
                }
            }
            cause = cause.getCause();
        }
        return "No se permite la actualización fuera del horario o días laborales.";
    }
}
