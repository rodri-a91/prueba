package ceu.api.envios.server.service;

import ceu.api.envios.server.model.Envio;
import ceu.api.envios.server.exception.*;
import java.util.List;

public interface EnviosService {

    /**
     * 1. PROCESAMIENTO DE ALERTAS CLIMÁTICAS
     * Busca todos los envíos en tránsito que hayan superado un límite de temperatura crítico.
     * 
     * CASUÍSTICA DE EXCEPCIÓN:
     * - Si el límite de temperatura proporcionado es absurdo para la cadena de frío 
     *   (ej. inferior a -50°C o superior a 50°C), lanza IllegalArgumentException.
     * 
     * @param limiteTemperatura Grados Celsius máximos permitidos.
     * @return Lista de envíos que están en riesgo.
     * @throws IllegalArgumentException si la temperatura está fuera de rangos lógicos de control.
     */
    List<Envio> detectarEnviosEnRiesgoTermico(double limiteTemperatura);

    /**
     * 2. ACTUALIZACIÓN MASIVA INTELIGENTE
     * Recibe una lista de códigos de seguimiento y cambia su estado al unísono,
     * pero solo si el envío fue registrado en las últimas 24 horas.
     * 
     * CASUÍSTICAS DE EXCEPCIÓN:
     * - Si alguno de los códigos enviados en la lista no existe en la base de datos, 
     *   lanza EnvioNoEncontradoException (para evitar actualizaciones parciales a ciegas).
     * - Si la lista de códigos está vacía, lanza IllegalArgumentException.
     * 
     * @param codigos Lista de códigos de seguimiento únicos a actualizar.
     * @param nuevoEstado El estado al que pasarán los envíos válidos.
     * @return Cantidad de envíos que cumplieron el criterio y fueron actualizados con éxito.
     * @throws EnvioNoEncontradoException si algún código no corresponde a un envío real.
     * @throws IllegalArgumentException si la lista de códigos viene vacía.
     */
    long actualizarEstadoLoteReciente(List<String> codigos, String nuevoEstado) 
            throws EnvioNoEncontradoException;

   /**
     * 3. REGISTRO Y VALIDACIÓN DE SEGURIDAD OPERATIVA
     * Registra un nuevo envío en el sistema, autogenerando un código de seguimiento único 
     * interno y validando que el paquete cumpla con los estándares de seguridad de la empresa.
     * 
     * CASUÍSTICAS DE EXCEPCIÓN:
     * - Si el peso supera los 1500 kg (límite máximo de los camiones ligeros de la flota),
     *   lanza PoliticaTransporteInvalidaException.
     * - Si el estado inicial proporcionado no es "REGISTRADO" o "EN_ALMACEN" (ej. intentan 
     *   crearlo directamente como "ENTREGADO"), lanza EstadoEnvioInvalidoException.
     * 
     * @param nuevoEnvio Objeto con los datos del envío a registrar.
     * @return El envío guardado con su ID autoincremental y código de seguimiento asignados.
     * @throws PoliticaTransporteInvalidaException si el peso excede la capacidad máxima permitida.
     * @throws EstadoEnvioInvalidoException si el estado inicial no es coherente para un nuevo registro.
     * @throws IllegalArgumentException si los datos numéricos básicos son incorrectos.
     */
    Envio registrarNuevoEnvio(Envio nuevoEnvio) 
            throws PoliticaTransporteInvalidaException, EstadoEnvioInvalidoException;

    /**
     * 4. CONTROL DE EFICIENCIA / BALANCEO
     * Encuentra el envío más pesado que se encuentre "PENDIENTE" de asignación.
     * 
     * CASUÍSTICA DE EXCEPCIÓN:
     * - Si no hay absolutamente ningún envío en la base de datos con estado "PENDIENTE", 
     *   puedes lanzar EstadoEnvioInvalidoException 
     * 
     * @return El envío con mayor peso en estado pendiente, o un Optional vacío.
     * @throws EstadoEnvioInvalidoException si no hay envíos pendientes
     */
    Envio obtenerEnvioPesadoPrioritario() throws EstadoEnvioInvalidoException;
}
