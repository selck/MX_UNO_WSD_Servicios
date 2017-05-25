package mx.com.amx.mx.uno.feed.ws.bo;

import java.util.ArrayList;
import java.util.List;

import mx.com.amx.mx.uno.feed.ws.dao.FeedDAO;
import mx.com.amx.mx.uno.feed.ws.dto.CategoriaDTO;
import mx.com.amx.mx.uno.feed.ws.dto.Categorias;
import mx.com.amx.mx.uno.feed.ws.dto.NoticiaExtraRSSDTO;
import mx.com.amx.mx.uno.feed.ws.dto.NoticiaRSSDTO;
import mx.com.amx.mx.uno.feed.ws.dto.Noticias;
import mx.com.amx.mx.uno.feed.ws.dto.RequestNotaDTO;
import mx.com.amx.mx.uno.feed.ws.dto.SeccionDTO;
import mx.com.amx.mx.uno.feed.ws.dto.Secciones;
import mx.com.amx.mx.uno.feed.ws.dto.TipoSeccionDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier("feedBO")
public class FeedBO {		
	/* --------------------------------------------------------------------------------- Fields */
	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private FeedDAO feedDAO;
	
	public Noticias consultarNoticiasMagazineJSON( String idMagazine ) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("idMagazine: " + idMagazine );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarNoticiasMagazineJSON(idMagazine);
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarNoticiasMagazineJSON: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	public Noticias consultarCarruselClarosportsJSON(int numRegistros) throws Exception {
		Noticias noticias = new Noticias();
		try{
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarCarruselClarosportsJSON(numRegistros);
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarCarruselClarosportsJSON: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	public Noticias consultarNoticiasMagazine( String idMagazine ) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("idMagazine: " + idMagazine );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarNoticiasMagazine(idMagazine);
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarNoticiasMagazine:",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Noticias consultarNoticiasViralesNoTeLoPierdas( String viralNoTeLoPierdas ) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("viralNoTeLoPierdas: " + viralNoTeLoPierdas );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarNoticiasViralesNoTeLoPierdas(viralNoTeLoPierdas);
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarNoticiasViralesNoTeLoPierdas: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Noticias consultarNoticias( String idCategoria ) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("idCategoria: " + idCategoria );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarNoticias( idCategoria);
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarNoticias: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Noticias consultarUltimasPorSeccion( String idSeccion ) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("idSeccion: " + idSeccion );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarUltimasPorSeccion( idSeccion );
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarUltimasPorSeccion: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Noticias consultarUltimasPorTipoSeccion( String idSeccion) throws Exception {
		Noticias noticias = new Noticias();
		try{
			LOG.info("idSeccion: " + idSeccion );
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lstNot = new ArrayList<NoticiaRSSDTO>();
			lstNot = feedDAO.consultarUltimasPorTipoSeccion( idSeccion );
			noticias.setNoticiasLst(lstNot);
		} catch (Exception e){
			LOG.error("Exception en consultarUltimasPorTipoSeccion: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByCategoria( String idSeccion, String fecha ) throws Exception {
		try{
			LOG.info("idSeccion: " + idSeccion );
			LOG.info("fecha: " + fecha );
			return feedDAO.consultarNotasExtraByCategoria( idSeccion, fecha );
		} catch (Exception e){
			LOG.error("Exception en consultarNotasExtraByCategoria: ",e );			
			throw new Exception(e.getMessage());
		}
		
	}
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByTipoSeccion( String idTipoSeccion, String fecha ) throws Exception {
		try{
			LOG.info("idTipoSeccion: " + idTipoSeccion );
			LOG.info("fecha: " + fecha );
			return feedDAO.consultarNotasExtraByTipoSeccion( idTipoSeccion, fecha );
		} catch (Exception e){
			LOG.error("Exception en consultarNotasExtraByTipoSeccion: ",e );			
			throw new Exception(e.getMessage());
		}
	}
	
	public List<NoticiaExtraRSSDTO> consultarNotasExtraBySeccion( String idSeccion, String fecha ) throws Exception {
		try{
			LOG.info("idSeccion: " + idSeccion );
			LOG.info("fecha: " + fecha );
			return feedDAO.consultarNotasExtraBySeccion(idSeccion, fecha);
		} catch (Exception e){
			LOG.error("Exception en consultarNotasExtraBySeccion: ",e );			
			throw new Exception(e.getMessage());
		}
	}
	public Categorias getCategorias( ) throws Exception {
		Categorias noticias = new Categorias();
		try{
			noticias.setMensaje("OK");
			List<CategoriaDTO> lst = new ArrayList<CategoriaDTO>();
			lst = feedDAO.getCategorias( );
			noticias.setCategotiasLst(lst);
		} catch (Exception e){
			LOG.error("Exception en getCategorias: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Secciones getSecciones( ) throws Exception {
		Secciones noticias = new Secciones();
		try{
			noticias.setMensaje("OK");
			List<SeccionDTO> lst = new ArrayList<SeccionDTO>();
			lst = feedDAO.getSecciones( );
			noticias.setSeccionesLst(lst);
		} catch (Exception e){
			LOG.error("Exception en getSecciones: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public Secciones getTipoSecciones( ) throws Exception {
		Secciones noticias = new Secciones();
		try{
			noticias.setMensaje("OK");
			List<TipoSeccionDTO> lst = new ArrayList<TipoSeccionDTO>();
			lst = feedDAO.getTipoSecciones( );
			noticias.setTipoSeccionesLst(lst);
		} catch (Exception e){
			LOG.error("Exception en getTipoSecciones: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	/**
	 * Obtiene la lista de notas magazine
	 * @param requestNotaDTO request
	 * @return Lista de notas magazine
	 */
	public Noticias getNotaMagazine(RequestNotaDTO requestNota) throws Exception {
		Noticias noticias = new Noticias();
		try{
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lst = new ArrayList<NoticiaRSSDTO>();
			lst = feedDAO.getNotaMagazine(requestNota);
			
			noticias.setNoticiasLst(lst);
		} catch (Exception e){
			LOG.error("Exception en getNotaMagazine: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	/**
	 * Obtiene la lista de notas
	 * @param requestNotaDTO request
	 * @return Lista de notas
	 */
	public Noticias getNota(RequestNotaDTO requestNota) throws Exception {
		Noticias noticias = new Noticias();
		try{
			noticias.setMensaje("OK");
			List<NoticiaRSSDTO> lst = new ArrayList<NoticiaRSSDTO>();
			lst = feedDAO.getNota(requestNota);
			noticias.setNoticiasLst(lst);
		} catch (Exception e){
			LOG.error("Exception en getNota: ",e);			
			throw new Exception(e.getMessage());
		}
		return noticias;
	}
	
	public FeedDAO getFeedDAO() {
		return feedDAO;
	}

	@Autowired
	public void setFeedDAO(FeedDAO feedDAO) {
		this.feedDAO = feedDAO;
	}
	
}