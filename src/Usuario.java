//QMP 2


//Primer Requerimiento

AccuWeatherAPI apiClima = new AccuWeatherAPI();
  apiClima.getWeather("Buenos Aires, Argentina");
  estadoDelTiempo.get(0).get("Temperature");

//Segundo Requerimiento
import org.junit.jupiter.api.BeforeEach;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Atuendo {
    private Prenda superior;
    private Prenda inferior;
    private Prenda calzado;
    private Prenda accesorio;

    public Atuendo(Prenda prendaSuperior, Prenda prendaInferior, Prenda prendaCalzado, Prenda prendaAccesorio) {
        this.superior = prendaSuperior;
        this.inferior = prendaInferior;
        this.calzado = prendaCalzado;
        this.accesorio = prendaAccesorio;
    }
}

//Tercer Requerimiento

public class Usuario{
    private Guardarropas guardarropas;
    public Atuendo sugerirAtuendo(){
        return guardarropas.sugerirAtuendo();
    };
}

public class Guardarropas{

    private List<Prenda> prendas = new ArrayList<Prenda>();
    private AccuWeatherAPI apiClima;
    private GeneradorSugerencias generadorSugerencias;

    public Atuendo sugerirAtuendo(){
        List<Map<String,Object>> estadoDelClima = apiClima.getWeather("Buenos Aires, Argentina");
        BigDecimal temperatura = (BigDecimal) estadoDelClima.get(0).get("Temperature");
        return obtenerAtuendoPorTemperatura(temperatura);
    }
    public Atuendo obtenerAtuendoPorTemperatura(BigDecimal temperatura){
        return generadorSugerencias.generarAtuendoDesde(prendas.stream().filter(prenda->prenda.adecuadoParaElClima(temperatura)));
    }
}
public class Prenda {
    private BigDecimal TemperaturaMaxima;
    public Boolean adecuadoParaElClima(BigDecimal temperatura){
        return this.TemperaturaMaxima > temperatura;
    }
}

//Cuarto Requerimiento

public class Usuario{
    private Guardarropas guardarropas;
    private ServicioMeteorologico servicioMeteorologico;


    public Atuendo sugerirAtuendo(){
        return guardarropas.sugerirAtuendo();
    };
}
public class EstadoClima {
    private BigDecimal temperatura;
    private BigDecimal humedad;

    public EstadoClima(BigDecimal temperatura,BigDecimal humedad){
        this.humedad=humedad;
        this.temperatura=temperatura;
    }

    public BigDecimal getTemperatura() {
        return temperatura;
    }
}
public interface ServicioMeteorologico {
    EstadoClima getEstadoClima(String direccion);

}
public class ServicioMeteorologicoAccuWeather implements ServicioMeteorologico {
    private AccuWeatherAPI api;
    public ServicioMeteorologico(AccuWeatherAPI api){
        this.api=api;
    }
    @Override
    public EstadoClima getEstadoClima(String direccion){
        Map<String,Object> clima = consultarApi(direccion);
        return new EstadoClima(BigDecimal.valueOf(clima.get("Temperature").get(Unit).equals("F") ? temperatura :
                                BigDecimal.valueOf((Long) clima.get("Humidity"))));
    }
    private Map<String,Object> consultarApi(String direccion){
        return try{
            this.api.getWeather(direccion).get(0);
        } catch{
            //Excepcion
        }
    }
}

//Quinto Requerimiento

    //Mockito

@BeforeEach
void setUpFixture(){
    Usuario marian = new Usuario(...);

}
@test
void sugerirRemeraMangaCortaSinHacerCalor(){
    Atuendo atuendo = marian.sugerirAtuendo();
    assertTrue(atuendo.getListaPrendas().anyMatch(prenda->prenda.getTipo() == TipoPrenda.Remera && prenda.adecuadoParaElClima(30.0)));
}



