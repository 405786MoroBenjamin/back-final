package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.habitacion.PostReservaDTO;
import ar.edu.utn.frc.tup.lciii.dtos.habitacion.ReservaDTO;
import ar.edu.utn.frc.tup.lciii.servicies.ReservaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping("/reserva/{id_reserva}")
    ReservaDTO getReserva(@PathVariable("id_reserva") Long idReserva) {
        ReservaDTO reserva = reservaService.getReservaById(idReserva);
        return reserva;
    }


    @PostMapping("/reserva")
    ReservaDTO postReserva(@RequestBody PostReservaDTO reservaDTO) {
        ReservaDTO reserva = reservaService.createReserva(reservaDTO);
        return reserva;
    }
}
