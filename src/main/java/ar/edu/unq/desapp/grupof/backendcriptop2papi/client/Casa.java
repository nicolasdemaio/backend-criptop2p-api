package ar.edu.unq.desapp.grupof.backendcriptop2papi.client;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static ar.edu.unq.desapp.grupof.backendcriptop2papi.utils.DoubleFormatter.f;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Casa {
    private String compra;
    private String venta;
    private String agencia;
    @Getter
    private String nombre;
    private String variacion;
    private String ventaCero;
    private String decimales;
    public Double getSellingPrice(){
        return f(Double.valueOf(this.venta.replace(",",".")));
    }
}
