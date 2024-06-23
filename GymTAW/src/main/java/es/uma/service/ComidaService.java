package es.uma.service;

import es.uma.dao.*;
import es.uma.dto.*;
import es.uma.entity.*;
import es.uma.ui.ComidaUI;
import es.uma.ui.DiaComida;
import es.uma.ui.IngredienteImplementandoUI;
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
    @Autowired
    private PlatosRepository platosRepository;
    @Autowired
    private TipoCantidadRepository tipoCantidadRepository;

    public void save(Comida comida){
        comidaRepository.save(comida);
    }

    //@author: Pablo Márquez Benítez
    public void guardarComida(ComidaDTO comidaDTO){
        Comida comida = comidaRepository.findById(comidaDTO.getId()).orElse(null);
        if(comida!=null){
            comida.setRealizado(comidaDTO.getRealizado());
            comidaRepository.save(comida);
        }
    }

    public void deleteByDiaDieta(DiaDietaDTO diaDietaDTO){
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        List<Comida> comidas = comidaRepository.findByDiaDieta(diaDieta.getId());
        for(Comida comida : comidas){
            comidaRepository.deleteById(comida.getId());
        }
    }

    public List<ComidaDTO> getByCustomer(Integer id){
        return comidaRepository.findByCustomer(id).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }
    public List<ComidaDTO> getByDietist(Integer id){
        return comidaRepository.findByDietist(id).stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    //@author: Pablo Márquez Benítez
    public ComidaDTO getComidaByID(Integer id){
        Comida comida = comidaRepository.findById(id).orElse(null);
        if(comida!=null){
            return convertEntityToDto(comida);
        }else{
            return null;
        }
    }

    //@author: Pablo Márquez Benítez
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<ComidaDTO> getComidasByDiaDietaAndTipoComida(DiaDietaDTO diaDietaDTO, TipoComidaDTO tipoComidaDTO)
    {
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaDTO);
        List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
        List<ComidaDTO> comidaDTOList = new ArrayList<>();
        if(comidaActual!=null){
            comidaDTOList = this.convertlistEntityToDto(comidaActual);
        }
        return comidaDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
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

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI initialSetUpComidaUI(DiaDietaDTO diaDietaDTO, TipoComidaDTO tipoComidaDTO)
    {
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaDTO);
        ComidaUI comidaUI = new ComidaUI();
        comidaUI.setPlatoExistente(false);
        ArrayList<Plato> platosComida = new ArrayList<>();
        List<Comida> comida = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
        if(!comida.isEmpty()) {
            platosComida.addAll(cantidadIngredientePlatoComidaRepository.findPlatosInComida(comida.getFirst().getId()));
        } else {
            Comida comidaAdd = new Comida();
            comidaAdd.setTipoComida(tipoComida);
            comidaAdd.setDiaDieta(diaDieta);
            comidaRepository.save(comidaAdd);
        }
        comidaUI.setListaPlatosComida(platosComida);
        ArrayList<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = new ArrayList<>();
        comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);
        comidaUI.setSelectedPlato(null);
        return comidaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI mostrarPlatoComidaSetUpComidaUI(ComidaUI comidaUI, DiaDietaDTO diaDietaDTO, TipoComidaDTO tipoComidaDTO)
    {
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaDTO);
        comidaUI.setPlatoExistente(false);
        List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
        List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato().getId(), comidaActual.getFirst().getId());
        comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);
        return comidaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI addPlatoToPlatoComida(ComidaUI comidaUI, DiaDietaDTO diaDietaDTO, TipoComidaDTO tipoComidaDTO)
    {
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaDTO);
        List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
        Plato addingPlato = comidaUI.getAddingPlato();
        List<Plato> platosComida = comidaUI.getListaPlatosComida();
        if(!platosComida.contains(addingPlato))
        {
            platosComida.add(addingPlato);
            comidaUI.setListaPlatosComida(platosComida);

            List<Ingrediente> ingredientesPlato = platosRepository.getIngredientesLinkedToPlato(addingPlato);
            for (Ingrediente i : ingredientesPlato)
            {
                CantidadIngredientePlatoComida cantidadIngredientePlatoComida = new CantidadIngredientePlatoComida();
                cantidadIngredientePlatoComida.setPlato(addingPlato);
                cantidadIngredientePlatoComida.setComida(comidaActual.getFirst());
                cantidadIngredientePlatoComida.setIngrediente(i);
                cantidadIngredientePlatoComida.setCantidad(0);
                List<TipoCantidad> tipoCantidadList = tipoCantidadRepository.findAll();
                cantidadIngredientePlatoComida.setTipoCantidad(tipoCantidadList.getFirst());
                cantidadIngredientePlatoComidaRepository.save(cantidadIngredientePlatoComida);
            }

            comidaUI.setSelectedPlato(addingPlato);
        } else {
            comidaUI.setPlatoExistente(true);
            comidaUI.setSelectedPlato(null);
        }
        List<CantidadIngredientePlatoComida> listaCantidadIngredientesPlatoSeleccionado = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato().getId(), comidaActual.getFirst().getId());
        comidaUI.setListaCantidadIngredientesPlatoSeleccionado(listaCantidadIngredientesPlatoSeleccionado);
        return comidaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI deletePlatoFromPlatoComida(ComidaUI comidaUI, DiaDietaDTO diaDietaDTO, TipoComidaDTO tipoComidaDTO)
    {
        DiaDieta diaDieta = diaDietaService.convertDtoToEntity(diaDietaDTO);
        TipoComida tipoComida = tipoComidaService.convertDtoToEntity(tipoComidaDTO);
        List<Comida> comidaActual = comidaRepository.findByDiaAndTipoComido(diaDieta, tipoComida);
        Plato selectedPlato = comidaUI.getSelectedPlato();
        List<Plato> platosComida = comidaUI.getListaPlatosComida();
        List<Plato> platosRemove = new ArrayList<>();
        for(Plato p : platosComida)
        {
            if(p.equals(selectedPlato))
            {
                platosRemove.add(p);
            }
        }
        platosComida.removeAll(platosRemove);
        comidaUI.setListaPlatosComida(platosComida);

        List<CantidadIngredientePlatoComida> cantidadesIngredientePlatoComida = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(selectedPlato.getId(), comidaActual.getFirst().getId());
        for (CantidadIngredientePlatoComida c : cantidadesIngredientePlatoComida)
        {
            cantidadIngredientePlatoComidaRepository.delete(c);
        }

        comidaUI.setPlatoExistente(false);
        comidaUI.setSelectedPlato(null);
        comidaUI.setListaCantidadIngredientesPlatoSeleccionado(new ArrayList<>());
        return comidaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI deleteIngredienteFromPlatoComida(ComidaUI comidaUI, Integer cantidadId)
    {
        CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.findById(cantidadId).orElse(null);
        Plato platoActual = c.getPlato();
        Comida comidaActual = c.getComida();
        cantidadIngredientePlatoComidaRepository.delete(c);
        List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(platoActual.getId(), comidaActual.getId());
        comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
        comidaUI.setPlatoExistente(false);
        return comidaUI;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public ComidaUI saveIngrediente(ComidaUI comidaUI, IngredienteImplementandoUI ingredienteImplementandoUI, List<ComidaDTO> listComidaDTO)
    {
        List<Comida> listaComida = this.convertlistDtoToEntity(listComidaDTO);
        Comida comidaActual = listaComida.getFirst();
        List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComida = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComidaIngrediente(comidaUI.getSelectedPlato(), comidaActual, ingredienteImplementandoUI.getIngrediente());
        boolean modoEdicion = !cantidadIngredientePlatoComida.isEmpty();
        CantidadIngredientePlatoComida cantidad;
        if(modoEdicion)
        {
            cantidad = cantidadIngredientePlatoComida.getFirst();
            cantidadIngredientePlatoComida.remove(cantidad);
        }
        else
        {
            cantidad = new CantidadIngredientePlatoComida();
            cantidad.setIngrediente(ingredienteImplementandoUI.getIngrediente());
            cantidad.setComida(comidaActual);
            cantidad.setPlato(comidaUI.getSelectedPlato());
        }
        cantidad.setCantidad(ingredienteImplementandoUI.getCantidad());
        cantidad.setTipoCantidad(ingredienteImplementandoUI.getTipoCantidad());
        cantidadIngredientePlatoComidaRepository.save(cantidad);

        if(modoEdicion)
        {
            List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = cantidadIngredientePlatoComidaRepository.findCantidadByPlatoComida(comidaUI.getSelectedPlato().getId(), comidaActual.getId());
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
        }
        else
        {
            List<CantidadIngredientePlatoComida> cantidadIngredientePlatoComidaList = comidaUI.getListaCantidadIngredientesPlatoSeleccionado();
            CantidadIngredientePlatoComida c = cantidadIngredientePlatoComidaRepository.getUltimaCantidadAdded();
            cantidadIngredientePlatoComidaList.add(c);
            comidaUI.setListaCantidadIngredientesPlatoSeleccionado(cantidadIngredientePlatoComidaList);
        }
        comidaUI.setPlatoExistente(false);
        return comidaUI;
    }

    public ComidaDTO convertEntityToDto(Comida comida){
        ComidaDTO comidaDTO = new ComidaDTO();
        comidaDTO.setId(comida.getId());
        comidaDTO.setTipoComida(tipoComidaService.convertEntityToDto(comida.getTipoComida()));
        comidaDTO.setDiaDieta(diaDietaService.convertEntityToDto(comida.getDiaDieta()));
        comidaDTO.setRealizado(comida.getRealizado());
        return comidaDTO;
    }

    //@author: Pablo Márquez Benítez
    public Comida convertDtoToEntity(ComidaDTO comidaDTO) {
        Comida comida = new Comida();
        comida.setId(comidaDTO.getId());
        comida.setTipoComida(tipoComidaService.convertDtoToEntity(comidaDTO.getTipoComida()));
        comida.setDiaDieta(diaDietaService.convertDtoToEntity(comidaDTO.getDiaDieta()));
        comida.setRealizado(comidaDTO.getRealizado());
        return comida;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<ComidaDTO> convertlistEntityToDto(List<Comida> comidaList){
        List<ComidaDTO> comidaDTOList = new ArrayList<>();
        for(Comida comida : comidaList){
            comidaDTOList.add(this.convertEntityToDto(comida));
        }
        return comidaDTOList;
    }

    //@author: Jaime Ezequiel Rodriguez Rodriguez
    public List<Comida> convertlistDtoToEntity(List<ComidaDTO> comidaDTOList){
        List<Comida> comidaList = new ArrayList<>();
        for(ComidaDTO comidaDTO : comidaDTOList){
            comidaList.add(this.convertDtoToEntity(comidaDTO));
        }
        return comidaList;
    }

}
