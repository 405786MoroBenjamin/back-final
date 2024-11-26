package ar.edu.utn.frc.tup.lciii.servicies;

import ar.edu.utn.frc.tup.lciii.dtos.habitacion.PostReservaDTO;
import ar.edu.utn.frc.tup.lciii.dtos.habitacion.ReservaDTO;
import ar.edu.utn.frc.tup.lciii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.lciii.repositories.ReservaRepository;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ReservaServiceTest {

    @InjectMocks
    ReservaService reservaService;

    @Mock
    ReservaRepository reservaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createReserva() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .build();

        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .estadoReserva("EXITOSA")
                .precio(BigDecimal.valueOf(4252.500))
                .build();

        when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);

        ReservaDTO reserva = reservaService.createReserva(postReservaDTO);

        assertThat(reservaEntity.getPrecio(),  Matchers.comparesEqualTo(reserva.getPrecio()));
        assertEquals(reservaEntity.getEstadoReserva(), reserva.getEstadoReserva());
        assertEquals(reservaEntity.getFechaIngreso(), reserva.getFechaIngreso());
        assertEquals(reservaEntity.getFechaSalida(), reserva.getFechaSalida());
        assertEquals(reservaEntity.getIdCliente(), reserva.getIdCliente());
        assertEquals(reservaEntity.getIdHotel(), reserva.getIdHotel());
        assertEquals(reservaEntity.getMedioPago(), reserva.getMedioPago());
        assertEquals(reservaEntity.getTipoHabitacion(), reserva.getTipoHabitacion());
    }

    @Test
    void createReservaWithDebito() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("TARJETA_DEBITO")
                .build();

        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("TARJETA_DEBITO")
                .estadoReserva("EXITOSA")
                .precio(BigDecimal.valueOf(5103))
                .build();

        when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);

        ReservaDTO reserva = reservaService.createReserva(postReservaDTO);

        assertThat(reservaEntity.getPrecio(),  Matchers.comparesEqualTo(reserva.getPrecio()));
        assertEquals(reservaEntity.getEstadoReserva(), reserva.getEstadoReserva());
        assertEquals(reservaEntity.getFechaIngreso(), reserva.getFechaIngreso());
        assertEquals(reservaEntity.getFechaSalida(), reserva.getFechaSalida());
        assertEquals(reservaEntity.getIdCliente(), reserva.getIdCliente());
        assertEquals(reservaEntity.getIdHotel(), reserva.getIdHotel());
        assertEquals(reservaEntity.getMedioPago(), reserva.getMedioPago());
        assertEquals(reservaEntity.getTipoHabitacion(), reserva.getTipoHabitacion());
    }

    @Test
    void createReservaWithCredito() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("TARJETA_CREDITO")
                .build();

        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("TARJETA_CREDITO")
                .estadoReserva("EXITOSA")
                .precio(BigDecimal.valueOf(5670))
                .build();

        when(reservaRepository.save(reservaEntity)).thenReturn(reservaEntity);

        ReservaDTO reserva = reservaService.createReserva(postReservaDTO);

        assertThat(reservaEntity.getPrecio(),  Matchers.comparesEqualTo(reserva.getPrecio()));
        assertEquals(reservaEntity.getEstadoReserva(), reserva.getEstadoReserva());
        assertEquals(reservaEntity.getFechaIngreso(), reserva.getFechaIngreso());
        assertEquals(reservaEntity.getFechaSalida(), reserva.getFechaSalida());
        assertEquals(reservaEntity.getIdCliente(), reserva.getIdCliente());
        assertEquals(reservaEntity.getIdHotel(), reserva.getIdHotel());
        assertEquals(reservaEntity.getMedioPago(), reserva.getMedioPago());
        assertEquals(reservaEntity.getTipoHabitacion(), reserva.getTipoHabitacion());
    }

    @Test
    void createReservaWithInvalidDates() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, -3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .build();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservaService.createReserva(postReservaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Fecha de ingreso no puede ser posterior a la de salida", exception.getReason());
    }

    @Test
    void createReservaWithInvalidHotel() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(4L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .build();


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservaService.createReserva(postReservaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Hotel no encontrado", exception.getReason());
    }

    @Test
    void createReservaWithInvalidHabitacion() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(2L)
                .tipoHabitacion("CUADRUPLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .build();


        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservaService.createReserva(postReservaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Tipo de habitacion no encontrado", exception.getReason());
    }

    @Test
    void createReservaWithInvalidPago() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        PostReservaDTO postReservaDTO = PostReservaDTO.builder()
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("CHEQUE")
                .build();

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservaService.createReserva(postReservaDTO);
        });

        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertEquals("Medio de pago inválido", exception.getReason());
    }

    @Test
    void getReservaById() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idReserva(1L)
                .idCliente("3423")
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .medioPago("EFECTIVO")
                .estadoReserva("EXITOSA")
                .precio(BigDecimal.valueOf(4252.500))
                .build();



        when(reservaRepository.findById(1L)).thenReturn(java.util.Optional.of(reservaEntity));

        ReservaDTO reserva = reservaService.getReservaById(1L);

        assertThat(reservaEntity.getPrecio(),  Matchers.comparesEqualTo(reserva.getPrecio()));
        assertEquals(reservaEntity.getEstadoReserva(), reserva.getEstadoReserva());
        assertEquals(reservaEntity.getFechaIngreso(), reserva.getFechaIngreso());
        assertEquals(reservaEntity.getFechaSalida(), reserva.getFechaSalida());
        assertEquals(reservaEntity.getIdCliente(), reserva.getIdCliente());
        assertEquals(reservaEntity.getIdHotel(), reserva.getIdHotel());
        assertEquals(reservaEntity.getMedioPago(), reserva.getMedioPago());
        assertEquals(reservaEntity.getTipoHabitacion(), reserva.getTipoHabitacion());


    }

    @Test
    void getReservaByIdNull(){
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            reservaService.getReservaById(1L);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("Reserva no encontrada", exception.getReason());
    }

    @Test
    void isAvailableWhenNoReservations() {
        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(new Date())
                .fechaSalida(new Date())
                .build();

        when(reservaRepository.findByIdHotelAndTipoHabitacion(1L, "DOBLE")).thenReturn(Optional.empty());

        boolean available = reservaService.isAvailable(reservaEntity);

        assertTrue(available);
    }

    @Test
    void isAvailableTrue() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        ReservaEntity existingReserva = ReservaEntity.builder()
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .build();

        ReservaEntity newReserva = ReservaEntity.builder()
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .build();

        when(reservaRepository.findByIdHotelAndTipoHabitacion(1L, "DOBLE")).thenReturn(Optional.of(List.of(existingReserva)));

        boolean available = reservaService.isAvailable(newReserva);

        assertFalse(available);
    }

    @Test
    void isAvailableFalse() {
        Date fechaIngreso = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(fechaIngreso);
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date fechaSalida = calendar.getTime();

        ReservaEntity existingReserva = ReservaEntity.builder()
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(fechaIngreso)
                .fechaSalida(fechaSalida)
                .build();

        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date newFechaIngreso = calendar.getTime();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        Date newFechaSalida = calendar.getTime();

        ReservaEntity newReserva = ReservaEntity.builder()
                .idHotel(1L)
                .tipoHabitacion("DOBLE")
                .fechaIngreso(newFechaIngreso)
                .fechaSalida(newFechaSalida)
                .build();

        when(reservaRepository.findByIdHotelAndTipoHabitacion(1L, "DOBLE")).thenReturn(Optional.of(List.of(existingReserva)));

        boolean available = reservaService.isAvailable(newReserva);

        assertTrue(available);
    }


    @Test
    void validateDate() {

    }

    @Test
    void calculatePriceTotal() {
    }

    @Test
    void esTemporadaAlta() {

    }

    @Test
    void esTemporadaBaja() {
    }

    @Test
    void calculatePrice() {
    }

    @Test
    void pricePerHotel() {
    }

    @Test
    void mapPostReservaDtoToEntity() {
    }

    @Test
    void mapReservaEntityToDto() {
    }
}