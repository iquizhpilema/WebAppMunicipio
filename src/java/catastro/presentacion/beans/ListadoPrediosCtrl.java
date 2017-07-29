/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package catastro.presentacion.beans;

import catastro.coeficientes.entidades.CoefAcabadosCubierta;
import catastro.coeficientes.entidades.CoefBanios;
import catastro.coeficientes.entidades.CoefClosets;
import catastro.coeficientes.entidades.CoefCocina;
import catastro.coeficientes.entidades.CoefColumnas;
import catastro.coeficientes.entidades.CoefCubierta;
import catastro.coeficientes.entidades.CoefCubreventanas;
import catastro.coeficientes.entidades.CoefEntrepisos;
import catastro.coeficientes.entidades.CoefEscaleras;
import catastro.coeficientes.entidades.CoefParedes;
import catastro.coeficientes.entidades.CoefPisos;
import catastro.coeficientes.entidades.CoefPuertas;
import catastro.coeficientes.entidades.CoefRevExterior;
import catastro.coeficientes.entidades.CoefRevInterior;
import catastro.coeficientes.entidades.CoefTumbados;
import catastro.coeficientes.entidades.CoefVentanas;
import catastro.coeficientes.entidades.CoefVigas;
import catastro.coeficientes.entidades.CoeficienteCimientos;
import catastro.coeficientes.entidades.CoeficienteEstado;
import catastro.coeficientes.funciones.FCoefAcabBanios;
import catastro.coeficientes.funciones.FCoefAcabClosets;
import catastro.coeficientes.funciones.FCoefAcabCocina;
import catastro.coeficientes.funciones.FCoefAcabCubierta;
import catastro.coeficientes.funciones.FCoefAcabCubreventanas;
import catastro.coeficientes.funciones.FCoefAcabPisos;
import catastro.coeficientes.funciones.FCoefAcabPuertas;
import catastro.coeficientes.funciones.FCoefAcabTumbados;
import catastro.coeficientes.funciones.FCoefAcabVentanas;
import catastro.coeficientes.funciones.FCoefCimientos;
import catastro.coeficientes.funciones.FCoefColumnas;
import catastro.coeficientes.funciones.FCoefEntrepisos;
import catastro.coeficientes.funciones.FCoefEscaleras;
import catastro.coeficientes.funciones.FCoefEstado;
import catastro.coeficientes.funciones.FCoefEstructCubierta;
import catastro.coeficientes.funciones.FCoefParedes;
import catastro.coeficientes.funciones.FCoefRevExt;
import catastro.coeficientes.funciones.FCoefRevInt;
import catastro.coeficientes.funciones.FCoefVigas;
import catastro.logica.entidades.Bloque2;
import catastro.logica.entidades.Piso2;
import catastro.logica.entidades.PredioV2;
import catastro.logica.servicios.FuncionesBloque;
import catastro.logica.servicios.FuncionesPiso;
import catastro.logica.servicios.FuncionesPredio;
import catastro.reportes.entidades.ReportePredios;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.DefaultRequestContext;
import recursos.Util;

@ManagedBean
@ViewScoped
public class ListadoPrediosCtrl implements Serializable {

    private ArrayList<PredioV2> lstPredios;
    private PredioV2 objPredio;
    private String txtCriterio;
    private ArrayList<Bloque2> lstBloques;
    private ArrayList<Piso2> lstPisos;
    private Bloque2 objBloque;
    private Bloque2 bloqueSel;
    private Piso2 objPiso;
    private Piso2 pisoSel;
    private List<CoeficienteCimientos> lstCimientos;
    private List<CoefColumnas> lstColumnas;
    private List<CoefVigas> lstVigas;
    private List<CoefEntrepisos> lstEntrepisos;
    private List<CoefParedes> lstParedes;
    private List<CoefCubierta> lstCubierta;
    private List<CoefEscaleras> lstEscaleras;
    private List<CoefPisos> lstAcabPisos;
    private List<CoefPuertas> lstPuertas;
    private List<CoefVentanas> lstVentanas;
    private List<CoefCubreventanas> lstCubreVentanas;
    private List<CoefTumbados> lstTumbados;
    private List<CoefAcabadosCubierta> lstAcabCubierta;
    private List<CoefBanios> lstBanios;
    private List<CoefCocina> lstCocina;
    private List<CoefClosets> lstClosets;
    private List<CoefRevInterior> lstRevInterior;
    private List<CoefRevExterior> lstRevExterior;
    private List<CoeficienteEstado> lstEstadosConstruccion;

    public ListadoPrediosCtrl() {
        objPredio = new PredioV2();
        objBloque = new Bloque2();
        bloqueSel = new Bloque2();
        objPiso = new Piso2();
        pisoSel = new Piso2();
        obtenerPredios();

        {//obtener listas para los coeficientes
            this.obtenerEstados();
            this.obtenerCimientos();
            this.obtenerColumnas();
            this.obtenerVigas();
            this.obtenerEntrepisos();
            this.obtenerParedes();
            this.obtenerCubierta();
            this.obtenerEscaleras();
            this.obtenerAcabPisos();
            this.obtenerAcabPuertas();
            this.obtenerAcabVentanas();
            this.obtenerAcabCubreventanas();
            this.obtenerAcabTumbados();
            this.obtenerAcabCubierta();
            this.obtenerAcabBanios();
            this.obtenerAcabCocina();
            this.obtenerAcabClosets();
            this.obtenerRevExterior();
            this.obtenerRevInterior();
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Obtener bloques dado predio ">    
    public void obtenerBloquesDadoPredio() {
        try {
            //this.lstBloques = FuncionesBloque.obtenerBloquesDadoPredio(objPredio.getIdPredio());
            this.lstBloques = FuncionesBloque.obtenerBloquesDadoClaveCatastral(objPredio.getClaveCatastral());
        } catch (Exception e) {
            Util.addErrorMessage("public void obtenerBloquesDadoPredio() dice: " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para seleccionar los predios">    
    public void obtenerPredios() {
        try {
            this.setLstPredios(FuncionesPredio.seleccionarPredios());
        } catch (Exception e) {
            Util.addErrorMessage("public void obtenerPredios() dice: " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para registrar un bloque">    
    public void registrarBloque() {
        try {
            objBloque.setPredio(objPredio);
            String msg = FuncionesBloque.registrarBloque(objBloque);
            Util.addSuccessMessage(msg);
            resetearFitrosTabla("frmBloques:tblBloques");
            objBloque = new Bloque2();
            obtenerBloquesDadoPredio();
        } catch (Exception e) {
            Util.addErrorMessage("public void registrarBloque() dice: " + e.getMessage());

        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Metodo para resetear el datatable">    
    public void resetearFitrosTabla(String id) {
        DataTable table = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(id);
        table.reset();
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para obtener los pisos dado bloque ">    
    public void obtenerPisosDadoBloque() {
        try {
            this.lstPisos = FuncionesPiso.obtenerPisoDadoBloque(bloqueSel.getIdBloque());
        } catch (Exception e) {
            Util.addErrorMessage("public void obtenerPisosDadoBloque() dice: " + e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para insertar un piso">    
    public void insertarPiso() {
        try {
            objPiso.setBloque(bloqueSel);
            String msg = FuncionesPiso.insertarPiso(objPiso);
            Util.addSuccessMessage(msg);
            objPiso = new Piso2();
            obtenerPisosDadoBloque();
            DefaultRequestContext.getCurrentInstance().execute("PF('wdlgRegistrarPiso').hide()");
            obtenerBloquesDadoPredio();
            resetearFitrosTabla("frmBloques:tblBloques");
        } catch (Exception e) {
            Util.addErrorMessage("public void insertarPiso() dice: " + e.getMessage());
        }
    }
    //</editor-fold>

    public void verReportePdfPorId() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        ReportePredios reporte = new ReportePredios();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        String ruta = servletContext.getRealPath("/reportes/fichaPredial.jasper");
        reporte.getReportePdfPorId(ruta, objPredio.getIdPredio());
        FacesContext.getCurrentInstance().responseComplete();
    }

    //<editor-fold defaultstate="collapsed" desc="Método para obtener predios dado parámetro">    
    public void encontrarPredioDadoParametro() {
        try {
            this.lstPredios = FuncionesPredio.encontrarPredio(txtCriterio);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Predios Encontrados", String.valueOf(lstPredios.size()));
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Error", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para actualizar el bloque">    
    public void eliminarBloque() {
        try {
            String msg = FuncionesBloque.eliminarBloque(bloqueSel.getIdBloque());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", msg);
            FacesContext.getCurrentInstance().addMessage(null, message);
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEliminarBloque').hide()");
            resetearFitrosTabla("frmBloques:tblBloques");
            bloqueSel = new Bloque2();
            obtenerBloquesDadoPredio();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Errror", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para actualizar el bloque">    
    public void actualizarBloque() {
        try {
            String msg = FuncionesBloque.actualizarBloque(bloqueSel);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", msg);
            FacesContext.getCurrentInstance().addMessage(null, message);
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEditarBloque').hide()");
            resetearFitrosTabla("frmBloques:tblBloques");
            bloqueSel = new Bloque2();
            obtenerBloquesDadoPredio();
        } catch (Exception e) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Errror", e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para actualizar un piso">    
    public void actualizarPiso() {
        try {
            String msg = FuncionesPiso.actualizarPiso(pisoSel);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Actualización Exitosa", msg);
            FacesContext.getCurrentInstance().addMessage(null, message);
            pisoSel = new Piso2();
            obtenerPisosDadoBloque();
            DefaultRequestContext.getCurrentInstance().execute("PF('').hide()");
            resetearFitrosTabla("frmBloques:tblBloques");
        } catch (Exception e) {
            System.out.println("public void actualizarPiso() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Método para eliminar un piso">    
    public void eliminarPiso() {
        try {
            String msg = FuncionesPiso.eliminarPiso(pisoSel.getIdPiso());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminación Exitosa", msg);
            FacesContext.getCurrentInstance().addMessage(null, message);
            pisoSel = new Piso2();
            obtenerPisosDadoBloque();
            resetearFitrosTabla("frmListadoPiso:tblPisos");
            DefaultRequestContext.getCurrentInstance().execute("PF('dlgEliminarPiso').hide()");
            obtenerBloquesDadoPredio();
            resetearFitrosTabla("frmBloques:tblBloques");
        } catch (Exception e) {
            System.out.println("public void eliminarPiso() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Obtención de Coeficientes">  
    public void obtenerEstados() {
        try {
            this.setLstEstadosConstruccion(FCoefEstado.coeficientesEstado());
        } catch (Exception e) {
            System.out.println("public void obtenerEstados() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerCimientos() {
        try {
            this.setLstCimientos(FCoefCimientos.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerCimientos() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerColumnas() {
        try {
            this.setLstColumnas(FCoefColumnas.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerColumnas() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerVigas() {
        try {
            this.setLstVigas(FCoefVigas.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerVigas() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerEntrepisos() {
        try {
            this.setLstEntrepisos(FCoefEntrepisos.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerEntrepisos() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerParedes() {
        try {
            this.setLstParedes(FCoefParedes.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerParedes() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerCubierta() {
        try {
            this.setLstCubierta(FCoefEstructCubierta.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerCubierta() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerEscaleras() {
        try {
            this.setLstEscaleras(FCoefEscaleras.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerEscaleras() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabPisos() {
        try {
            this.setLstAcabPisos(FCoefAcabPisos.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabPisos() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabPuertas() {
        try {
            this.setLstPuertas(FCoefAcabPuertas.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabPuertas() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabVentanas() {
        try {
            this.setLstVentanas(FCoefAcabVentanas.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabVentanas() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabCubreventanas() {
        try {
            this.setLstCubreVentanas(FCoefAcabCubreventanas.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabCubreventanas() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabTumbados() {
        try {
            this.setLstTumbados(FCoefAcabTumbados.obtenerCoeficientes());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabTumbados() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabCubierta() {
        try {
            this.setLstAcabCubierta(FCoefAcabCubierta.obtenerCoefAcabadosCubierta());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabCubierta() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabBanios() {
        try {
            this.setLstBanios(FCoefAcabBanios.obtenerCoefAcabadosBanios());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabBanios() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabCocina() {
        try {
            this.setLstCocina(FCoefAcabCocina.obtenerCoefAcabados());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabCocina() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerAcabClosets() {
        try {
            this.setLstClosets(FCoefAcabClosets.obtenerCoefAcabadosClosets());
        } catch (Exception e) {
            System.out.println("public void obtenerAcabClosets() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerRevInterior() {
        try {
            this.setLstRevInterior(FCoefRevInt.obtenerCoefAcabadosRevInterior());
        } catch (Exception e) {
            System.out.println("public void obtenerRevInterior() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }

    public void obtenerRevExterior() {
        try {
            this.setLstRevExterior(FCoefRevExt.obtenerCoefAcabadosRevExterior());
        } catch (Exception e) {
            System.out.println("public void obtenerRevExterior() dice: " + e.getMessage());
            Util.addErrorMessage(e.getMessage());
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Métodos Getters y Setters "> 
    /**
     * @return the lstCimientos
     */
    public List<CoeficienteCimientos> getLstCimientos() {
        return lstCimientos;
    }

    /**
     * @param lstCimientos the lstCimientos to set
     */
    public void setLstCimientos(List<CoeficienteCimientos> lstCimientos) {
        this.lstCimientos = lstCimientos;
    }

    /**
     * @return the lstColumnas
     */
    public List<CoefColumnas> getLstColumnas() {
        return lstColumnas;
    }

    /**
     * @param lstColumnas the lstColumnas to set
     */
    public void setLstColumnas(List<CoefColumnas> lstColumnas) {
        this.lstColumnas = lstColumnas;
    }

    /**
     * @return the lstVigas
     */
    public List<CoefVigas> getLstVigas() {
        return lstVigas;
    }

    /**
     * @param lstVigas the lstVigas to set
     */
    public void setLstVigas(List<CoefVigas> lstVigas) {
        this.lstVigas = lstVigas;
    }

    /**
     * @return the lstEntrepisos
     */
    public List<CoefEntrepisos> getLstEntrepisos() {
        return lstEntrepisos;
    }

    /**
     * @param lstEntrepisos the lstEntrepisos to set
     */
    public void setLstEntrepisos(List<CoefEntrepisos> lstEntrepisos) {
        this.lstEntrepisos = lstEntrepisos;
    }

    /**
     * @return the lstParedes
     */
    public List<CoefParedes> getLstParedes() {
        return lstParedes;
    }

    /**
     * @param lstParedes the lstParedes to set
     */
    public void setLstParedes(List<CoefParedes> lstParedes) {
        this.lstParedes = lstParedes;
    }

    /**
     * @return the lstCubierta
     */
    public List<CoefCubierta> getLstCubierta() {
        return lstCubierta;
    }

    /**
     * @param lstCubierta the lstCubierta to set
     */
    public void setLstCubierta(List<CoefCubierta> lstCubierta) {
        this.lstCubierta = lstCubierta;
    }

    /**
     * @return the lstEscaleras
     */
    public List<CoefEscaleras> getLstEscaleras() {
        return lstEscaleras;
    }

    /**
     * @param lstEscaleras the lstEscaleras to set
     */
    public void setLstEscaleras(List<CoefEscaleras> lstEscaleras) {
        this.lstEscaleras = lstEscaleras;
    }

    /**
     * @return the lstAcabPisos
     */
    public List<CoefPisos> getLstAcabPisos() {
        return lstAcabPisos;
    }

    /**
     * @param lstAcabPisos the lstAcabPisos to set
     */
    public void setLstAcabPisos(List<CoefPisos> lstAcabPisos) {
        this.lstAcabPisos = lstAcabPisos;
    }

    /**
     * @return the lstPuertas
     */
    public List<CoefPuertas> getLstPuertas() {
        return lstPuertas;
    }

    /**
     * @param lstPuertas the lstPuertas to set
     */
    public void setLstPuertas(List<CoefPuertas> lstPuertas) {
        this.lstPuertas = lstPuertas;
    }

    /**
     * @return the lstVentanas
     */
    public List<CoefVentanas> getLstVentanas() {
        return lstVentanas;
    }

    /**
     * @param lstVentanas the lstVentanas to set
     */
    public void setLstVentanas(List<CoefVentanas> lstVentanas) {
        this.lstVentanas = lstVentanas;
    }

    /**
     * @return the lstCubreVentanas
     */
    public List<CoefCubreventanas> getLstCubreVentanas() {
        return lstCubreVentanas;
    }

    /**
     * @param lstCubreVentanas the lstCubreVentanas to set
     */
    public void setLstCubreVentanas(List<CoefCubreventanas> lstCubreVentanas) {
        this.lstCubreVentanas = lstCubreVentanas;
    }

    /**
     * @return the lstTumbados
     */
    public List<CoefTumbados> getLstTumbados() {
        return lstTumbados;
    }

    /**
     * @param lstTumbados the lstTumbados to set
     */
    public void setLstTumbados(List<CoefTumbados> lstTumbados) {
        this.lstTumbados = lstTumbados;
    }

    /**
     * @return the lstAcabCubierta
     */
    public List<CoefAcabadosCubierta> getLstAcabCubierta() {
        return lstAcabCubierta;
    }

    /**
     * @param lstAcabCubierta the lstAcabCubierta to set
     */
    public void setLstAcabCubierta(List<CoefAcabadosCubierta> lstAcabCubierta) {
        this.lstAcabCubierta = lstAcabCubierta;
    }

    /**
     * @return the lstBanios
     */
    public List<CoefBanios> getLstBanios() {
        return lstBanios;
    }

    /**
     * @param lstBanios the lstBanios to set
     */
    public void setLstBanios(List<CoefBanios> lstBanios) {
        this.lstBanios = lstBanios;
    }

    /**
     * @return the lstCocina
     */
    public List<CoefCocina> getLstCocina() {
        return lstCocina;
    }

    /**
     * @param lstCocina the lstCocina to set
     */
    public void setLstCocina(List<CoefCocina> lstCocina) {
        this.lstCocina = lstCocina;
    }

    /**
     * @return the lstClosets
     */
    public List<CoefClosets> getLstClosets() {
        return lstClosets;
    }

    /**
     * @param lstClosets the lstClosets to set
     */
    public void setLstClosets(List<CoefClosets> lstClosets) {
        this.lstClosets = lstClosets;
    }

    /**
     * @return the lstRevInterior
     */
    public List<CoefRevInterior> getLstRevInterior() {
        return lstRevInterior;
    }

    /**
     * @param lstRevInterior the lstRevInterior to set
     */
    public void setLstRevInterior(List<CoefRevInterior> lstRevInterior) {
        this.lstRevInterior = lstRevInterior;
    }

    /**
     * @return the lstRevExterior
     */
    public List<CoefRevExterior> getLstRevExterior() {
        return lstRevExterior;
    }

    /**
     * @param lstRevExterior the lstRevExterior to set
     */
    public void setLstRevExterior(List<CoefRevExterior> lstRevExterior) {
        this.lstRevExterior = lstRevExterior;
    }

    /**
     * @return the lstEstadosConstruccion
     */
    public List<CoeficienteEstado> getLstEstadosConstruccion() {
        return lstEstadosConstruccion;
    }

    /**
     * @param lstEstadosConstruccion the lstEstadosConstruccion to set
     */
    public void setLstEstadosConstruccion(List<CoeficienteEstado> lstEstadosConstruccion) {
        this.lstEstadosConstruccion = lstEstadosConstruccion;
    }

    /**
     * @return the objPredio
     */
    public PredioV2 getObjPredio() {
        return objPredio;
    }

    /**
     * @return the objPiso
     */
    public Piso2 getObjPiso() {
        return objPiso;
    }

    /**
     * @param objPiso the objPiso to set
     */
    public void setObjPiso(Piso2 objPiso) {
        this.objPiso = objPiso;
    }

    /**
     * @return the pisoSel
     */
    public Piso2 getPisoSel() {
        return pisoSel;
    }

    /**
     * @param pisoSel the pisoSel to set
     */
    public void setPisoSel(Piso2 pisoSel) {
        this.pisoSel = pisoSel;
    }

    /**
     * @return the lstBloques
     */
    public ArrayList<Bloque2> getLstBloques() {
        return lstBloques;
    }

    /**
     * @param lstBloques the lstBloques to set
     */
    public void setLstBloques(ArrayList<Bloque2> lstBloques) {
        this.lstBloques = lstBloques;
    }

    /**
     * @return the objBloque
     */
    public Bloque2 getObjBloque() {
        return objBloque;
    }

    /**
     * @param objBloque the objBloque to set
     */
    public void setObjBloque(Bloque2 objBloque) {
        this.objBloque = objBloque;
    }

    /**
     * @return the bloqueSel
     */
    public Bloque2 getBloqueSel() {
        return bloqueSel;
    }

    /**
     * @param bloqueSel the bloqueSel to set
     */
    public void setBloqueSel(Bloque2 bloqueSel) {
        this.bloqueSel = bloqueSel;
    }

    /**
     * @param objPredio the objPredio to set
     */
    public void setObjPredio(PredioV2 objPredio) {
        this.objPredio = objPredio;
    }

    /**
     * @return the lstPredios
     */
    public ArrayList<PredioV2> getLstPredios() {
        return lstPredios;
    }

    /**
     * @param lstPredios the lstPredios to set
     */
    public void setLstPredios(ArrayList<PredioV2> lstPredios) {
        this.lstPredios = lstPredios;
    }

    /**
     * @return the txtCriterio
     */
    public String getTxtCriterio() {
        return txtCriterio;
    }

    /**
     * @return the lstPisos
     */
    public ArrayList<Piso2> getLstPisos() {
        return lstPisos;
    }

    /**
     * @param lstPisos the lstPisos to set
     */
    public void setLstPisos(ArrayList<Piso2> lstPisos) {
        this.lstPisos = lstPisos;
    }

    /**
     * @param txtCriterio the txtCriterio to set
     */
    public void setTxtCriterio(String txtCriterio) {
        this.txtCriterio = txtCriterio;
    }
    //</editor-fold>

}
