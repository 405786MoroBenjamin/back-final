package ar.edu.utn.frc.tup.lciii.dtos.habitacion;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostReservaDTO {
    @JsonProperty("id_hotel")
    private Long idHotel;

    @JsonProperty("id_cliente")
    private String idCliente;

    @JsonProperty("tipo_habitacion")
    private String tipoHabitacion;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_ingreso")
    private Date fechaIngreso;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("fecha_salida")
    private Date fechaSalida;

    @JsonProperty("medio_pago")
    private String medioPago;
}
