package ar.edu.utn.frc.tup.lciii.controllers;

import ar.edu.utn.frc.tup.lciii.dtos.habitacion.PostReservaDTO;
import ar.edu.utn.frc.tup.lciii.dtos.habitacion.ReservaDTO;
import ar.edu.utn.frc.tup.lciii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.lciii.servicies.ReservaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservaControllerTest {

    @InjectMocks
    ReservaController reservaController;

    @Mock
    ReservaService reservaService;

    private PostReservaDTO postReservaDTO;
    private ReservaDTO reservaDTO;
    private static final Long RESERVA_ID = 1L;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .build();

        reservaDTO = ReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .estadoReserva("EXITOSA")
                .precio(BigDecimal.valueOf(4252.500))
                .build();
    }

    @Test
    void getReserva() {
        when(reservaService.getReservaById(RESERVA_ID)).thenReturn(reservaDTO);

        ReservaDTO reserva = reservaController.getReserva(RESERVA_ID);

        assertEquals(reservaDTO, reserva);
    }

    @Test
    void postReserva() {
        when(reservaService.createReserva(postReservaDTO)).thenReturn(reservaDTO);

        ReservaDTO reserva = reservaController.postReserva(postReservaDTO);

        assertEquals(reservaDTO, reserva);
    }
}