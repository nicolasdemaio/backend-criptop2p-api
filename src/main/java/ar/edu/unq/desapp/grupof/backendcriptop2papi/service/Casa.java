package ar.edu.unq.desapp.grupof.backendcriptop2papi.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
        return Double.valueOf(this.venta.replace(",","."));
    }
}
