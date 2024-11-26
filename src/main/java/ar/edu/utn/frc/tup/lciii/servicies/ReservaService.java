package ar.edu.utn.frc.tup.lciii.servicies;

import ar.edu.utn.frc.tup.lciii.dtos.habitacion.PostReservaDTO;
import ar.edu.utn.frc.tup.lciii.dtos.habitacion.ReservaDTO;
import ar.edu.utn.frc.tup.lciii.entities.ReservaEntity;
import ar.edu.utn.frc.tup.lciii.repositories.ReservaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    @Transactional
    public ReservaDTO createReserva(PostReservaDTO reservaDTO) {

        validateDate(reservaDTO);

        ReservaEntity reservaEntity = mapPostReservaDtoToEntity(reservaDTO);
        BigDecimal price = calculatePriceTotal(reservaDTO);
        reservaEntity.setPrecio(price);
        // Consultar disponibilidad
        if(isAvailable(reservaEntity)){
            reservaEntity.setEstadoReserva("EXITOSA");
        } else {
            reservaEntity.setEstadoReserva("PENDIENTE");
        }

        reservaRepository.save(reservaEntity);
        return mapReservaEntityToDto(reservaEntity);
    }

    public void validateDate(PostReservaDTO dto){
        if(dto.getFechaIngreso().after(dto.getFechaSalida())){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Fecha de ingreso no puede ser posterior a la de salida");
        }
    }

    public BigDecimal calculatePriceTotal(PostReservaDTO reservaDTO) {
        Integer mes = reservaDTO.getFechaIngreso().getMonth();
        BigDecimal price = calculatePrice(reservaDTO);

        if (esTemporadaAlta(mes)) {
            price = price.multiply(BigDecimal.valueOf(1.30));
        } else if (esTemporadaBaja(mes)) {
            price = price.multiply(BigDecimal.valueOf(0.90));
        }

        if ("EFECTIVO".equals(reservaDTO.getMedioPago())) {
            price = price.multiply(BigDecimal.valueOf(0.75));
        } else if ("TARJETA_DEBITO".equals(reservaDTO.getMedioPago())) {
            price = price.multiply(BigDecimal.valueOf(0.90));
        } else{
            if(!"TARJETA_CREDITO".equals(reservaDTO.getMedioPago())) {
                throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Medio de pago inválido");
            }
        }
        return price;
    }

    public boolean isAvailable(ReservaEntity reservaDTO) {
        Optional<List<ReservaEntity>> reservas = reservaRepository.findByIdHotelAndTipoHabitacion(reservaDTO.getIdHotel(), reservaDTO.getTipoHabitacion());

        if(reservas.isEmpty()) {
            return true;
        }

        for(ReservaEntity reserva : reservas.get()) {
            if(reservaDTO.getFechaIngreso().after(reserva.getFechaIngreso()) &&
                reservaDTO.getFechaIngreso().before(reserva.getFechaSalida())){
                return false;
            }
            if(reservaDTO.getFechaIngreso().equals(reserva.getFechaIngreso())){
                return false;
            }
        }

        return true;
    }

    public boolean esTemporadaAlta(Integer mes) {
        return mes == 1 || mes == 2 || mes == 7 || mes == 8;
    }

    public boolean esTemporadaBaja(Integer mes) {
        return mes == 3 || mes == 4 || mes == 10 || mes == 11;
    }

    public BigDecimal calculatePrice(PostReservaDTO dto){
        LocalDate fechaIngreso = dto.getFechaIngreso().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaSalida = dto.getFechaSalida().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        Long diffDays = ChronoUnit.DAYS.between(fechaIngreso, fechaSalida);

        BigDecimal pricePerDay = pricePerHotel(dto.getIdHotel(), dto.getTipoHabitacion());

        BigDecimal price  = pricePerDay.multiply(new BigDecimal(diffDays));

        return price;
    }

    public BigDecimal pricePerHotel(Long idHotel, String type){
        switch (type) {
            case "SIMPLE" -> {
                if (idHotel == 1) {
                    return new BigDecimal(1250);
                } else if (idHotel == 2) {
                    return new BigDecimal(370);
                } else if (idHotel == 3) {
                    return new BigDecimal(2200);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Hotel no encontrado");
                }
            }
            case "DOBLE" -> {
                if (idHotel == 1) {
                    return new BigDecimal(2100);
                } else if (idHotel == 2) {
                    return new BigDecimal(650);
                } else if (idHotel == 3) {
                    return new BigDecimal(3700);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Hotel no encontrado");
                }
            }
            case "TRIPLE" -> {
                if (idHotel == 1) {
                    return new BigDecimal(2850);
                } else if (idHotel == 2) {
                    return new BigDecimal(875);
                } else if (idHotel == 3) {
                    return new BigDecimal(4100);
                } else {
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Hotel no encontrado");
                }
            }
            default ->
                    throw new ResponseStatusException(HttpStatusCode.valueOf(400), "Tipo de habitacion no encontrado");
        }
    }

    public ReservaDTO getReservaById(Long idReserva){
        Optional<ReservaEntity> reservaEntity = reservaRepository.findById(idReserva);
        if(reservaEntity.isEmpty()){
            throw new ResponseStatusException(HttpStatusCode.valueOf(400),"Reserva no encontrada");
        }
        return mapReservaEntityToDto(reservaEntity.get());
    }


    public ReservaEntity mapPostReservaDtoToEntity(PostReservaDTO reservaDTO) {
        ReservaEntity reservaEntity = ReservaEntity.builder()
                .idHotel(reservaDTO.getIdHotel())
                .idCliente(reservaDTO.getIdCliente())
                .tipoHabitacion(reservaDTO.getTipoHabitacion())
                .fechaIngreso(reservaDTO.getFechaIngreso())
                .fechaSalida(reservaDTO.getFechaSalida())
                .medioPago(reservaDTO.getMedioPago())
                .build();

        return reservaEntity;
    }

    public ReservaDTO mapReservaEntityToDto(ReservaEntity entity){
        ReservaDTO reservaDTO = ReservaDTO.builder()
                .idReserva(entity.getIdReserva())
                .idHotel(entity.getIdHotel())
                .idCliente(entity.getIdCliente())
                .tipoHabitacion(entity.getTipoHabitacion())
                .fechaIngreso(entity.getFechaIngreso())
                .fechaSalida(entity.getFechaSalida())
                .estadoReserva(entity.getEstadoReserva())
                .medioPago(entity.getMedioPago())
                .precio(entity.getPrecio())
                .build();
        return reservaDTO;
    }
}
