package ar.edu.utn.frc.tup.lciii.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "reservas")
public class ReservaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @Column(name = "id_cliente")
    private String idCliente;

    @Column(name = "id_hotel")
    private Long idHotel;

    @Column(name = "tipo_habitacion")
    private String tipoHabitacion;

    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;

    @Column(name = "fecha_salida")
    private Date fechaSalida;

    @Column(name = "estado_reserva")
    private String estadoReserva;

    @Column(name = "medio_pago")
    private String medioPago;

    @Column(name = "precio")
    private BigDecimal precio;
}