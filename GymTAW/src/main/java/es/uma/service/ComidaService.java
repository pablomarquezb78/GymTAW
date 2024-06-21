package es.uma.service;

import es.uma.dao.CantidadIngredientePlatoComidaRepository;
import es.uma.dao.ComidaRepository;
import es.uma.dao.DiaDietaRepository;
import es.uma.dao.TipoComidaRepository;
import es.uma.dto.ComidaDTO;
import es.uma.dto.PlatoDTO;
import es.uma.dto.UserDTO;
import es.uma.entity.Comida;
import es.uma.entity.DiaDieta;
import es.uma.entity.TipoComida;
import es.uma.entity.User;
import es.uma.ui.DiaComida;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
public class ComidaService {

    @Autowired
    DiaDietaRepository diaDietaRepository;
    @Autowired
    ComidaRepository comidaRepository;
    @Autowired
    DiaDietaService diaDietaService;
    @Autowired
    TipoComidaService tipoComidaService;
    @Autowired
    private TipoComidaRepository tipoComidaRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CantidadIngredientePlatoComidaRepository cantidadIngredientePlatoComidaRepository;
    @Autowired
    private PlatoService platoService;

    public void save(Comida comida){
        comidaRepository.save(comida);
    }

    public void guardarComida(ComidaDTO comidaDTO){
        Comida comida = comidaRepository.findById(comidaDTO.getId()).orElse(null);
        if(comida!=null){
            comida.setRealizado(comidaDTO.getRealizado());
            comidaRepository.save(comida);
        }
    }

    public ComidaDTO getComidaByID(Integer id){
        Comida comida = comidaRepository.findById(id).orElse(null);
        if(comida!=null){
            return convertEntityToDto(comida);
        }else{
            return null;
        }
    }

    public List<ComidaDTO> getComidasByDiaDieta(Integer diaDietaID){
        if(diaDietaID!=null){
            return comidaRepository.findByDiaDieta(diaDietaID)
                    .stream()
                    .map(this::convertEntityToDto)
                    .collect(Collectors.toList());
        }else{
            return new ArrayList<>();
        }
    }

    //Puede ser un Map que la clave sea DiaXComidaX y los values sean las listas de Plato
    public Pair<List<LocalDate>, Map<String, List<PlatoDTO>>> obtenerTablaComidas(UserDTO clienteDTO, UserDTO dietistaDTO, DiaComida diaComida)
    {
        User cliente = userService.convertDtoToEntity(clienteDTO);
        User dietista = userService.convertDtoToEntity(dietistaDTO);

        Map<String, List<PlatoDTO>> dataMap = new TreeMap<>();
        List<LocalDate> listaFechas = new ArrayList<>();
        List<TipoComida> tiposComida = tipoComidaRepository.findAll();

        for (int i = 1; i<=7; ++i) //Sabemos que vamos a mostrar los dias de 1 a los 7 siguientes
        {
            LocalDate fecha;
            boolean leapYear = Year.isLeap(diaComida.getYear());
            if(diaComida.getDay() + i - 1 <= Month.of(diaComida.getMonth()).length(leapYear)) //Caso normal en el que sumar dias no supone cambiar de mes
            {
                fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth(), diaComida.getDay() + i - 1);
            } else if (diaComida.getMonth() < 12) //Caso en el que sumar dias cambia de mes
            {
                int dayOfMonth = (diaComida.getDay() + i - 1) % Month.of(diaComida.getMonth()).length(leapYear);
                fecha = LocalDate.of(diaComida.getYear(), diaComida.getMonth() + 1 , dayOfMonth);
            } else //Caso en el que sumar dias implica cambiar de año
            {

                int dayOfMonth = (diaComida.getDay() + i - 1) % Month.of(diaComida.getMonth()).length(leapYear);
                fecha = LocalDate.of(diaComida.getYear() + 1, 1, dayOfMonth);
            }
            DiaDieta diaDieta = diaDietaRepository.findByFecha(dietista, cliente, fecha); //Obtenemos el dia de la dieta (columna de la tabla)
            listaFechas.add(fecha);
            List<Comida> comidasDelDia = new ArrayList<>();
            if(diaDieta != null) comidasDelDia = comidaRepository.findByDiaDieta(diaDieta.getId());
            int j = 1; //Sabemos que alojamos 5 tipos de comidas al dia
            for(TipoComida tipoComida : tiposComida) //Recorremos cada fila de la columna
            {
                List<PlatoDTO> listaPlatosComida = new ArrayList<>();
                Comida comida = null;
                for(Comida c : comidasDelDia) //En caso de que haya alguna comida asignada a ese tramo de comida lo guardamos
                {
                    if(c.getTipoComida().equals(tipoComida)) comida = c;
                }
                if (comida != null)
                {
                    //Como sabemos que esa comida existe sacamos los platos a mostrar
                    listaPlatosComida = cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getId())
                            .stream()
                            .map(platoService::convertEntityToDto)
                            .collect(Collectors.toList());
                }
                String key = "Dia" + i + "Comida" + j;
                //Si la lista de platos esta empty se muestra un mensaje en la tabla
                //Si la lista tiene datos se muestran los datos
                dataMap.put(key, listaPlatosComida);
                ++j;
            }
        }
        Pair<List<LocalDate>,Map<String, List<PlatoDTO>>> par = new Pair<>(listaFechas, dataMap);
        return par;
    }

    public ComidaDTO convertEntityToDto(Comida comida){
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setTipoComida(tipoComidaService.convertEntityToDto(comida.getTipoComida()));
        comidaDTO.setDiaDieta(diaDietaService.convertEntityToDto(comida.getDiaDieta()));
        comidaDTO.setRealizado(comida.getRealizado());
        return comidaDTO;
    }

    public Comida convertDtoToEntity(ComidaDTO comidaDTO) {
        Comida comida = new Comida();
        comida.setId(comidaDTO.getId());
        comida.setTipoComida(tipoComidaService.convertDtoToEntity(comidaDTO.getTipoComida()));
        comida.setDiaDieta(diaDietaService.convertDtoToEntity(comidaDTO.getDiaDieta()));
        comida.setRealizado(comidaDTO.getRealizado());
        return comida;
    }

}
