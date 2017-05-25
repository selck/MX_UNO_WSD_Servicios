package mx.com.amx.mx.uno.services;

import java.util.List;

import mx.com.amx.mx.uno.feed.ws.bo.FeedBO;
import mx.com.amx.mx.uno.feed.ws.dto.Categorias;
import mx.com.amx.mx.uno.feed.ws.dto.NoticiaExtraRSSDTO;
import mx.com.amx.mx.uno.feed.ws.dto.Noticias;
import mx.com.amx.mx.uno.feed.ws.dto.RequestNotaDTO;
import mx.com.amx.mx.uno.feed.ws.dto.Secciones;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("feedService")
public class FeedService {
	
	protected final Logger LOG = Logger.getLogger(this.getClass().getName());
	
	private FeedBO feedBO;
		
	/* --------------------------------------------------------------------------------- Methods */
	@RequestMapping(value = "/consultarNoticiasMagazineJSON", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarNoticiasMagazineJSON(@RequestHeader HttpHeaders headers, @RequestBody String idMagazine) {
		LOG.info("*********** WS consultarNoticiasMagazineJSON*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarNoticiasMagazineJSON(idMagazine);
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarNoticiasMagazineJSON ",e);
		}
		return noticias;
	}
	@RequestMapping(value = "/consultarCarruselClarosportsJSON", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarCarruselClarosportsJSON(@RequestHeader HttpHeaders headers, @RequestBody int numRegistros) {
		LOG.info("*********** WS consultarCarruselClarosportsJSON*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarCarruselClarosportsJSON(numRegistros);
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarCarruselClarosportsJSON ",e);
		}
		return noticias;
	}
	@RequestMapping(value = "/consultarNoticiasMagazine", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarNoticiasMagazine(@RequestHeader HttpHeaders headers, @RequestBody String idMagazine) {
		LOG.info("*********** WS consultarNoticiasMagazine*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarNoticiasMagazine(idMagazine);
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarNoticiasMagazine ",e);
		}
		return noticias;
	}
	
	@RequestMapping(value = "/consultarNoticiasViralesNoTeLoPierdas", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarNoticiasViralesNoTeLoPierdas(@RequestHeader HttpHeaders headers, @RequestBody String viralNoTeLoPierdas) {
		LOG.info("*********** WS consultarNoticiasViralesNoTeLoPierdas*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarNoticiasViralesNoTeLoPierdas(viralNoTeLoPierdas);
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarNoticiasViralesNoTeLoPierdas ",e);
		}
		return noticias;
	}
	
	@RequestMapping(value = "/consultarNoticias", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarNoticias(@RequestHeader HttpHeaders headers, @RequestBody String idCategoria) {
		LOG.info("*********** WS consultarNoticias*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarNoticias( idCategoria ) ;
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarNoticias ",e);
		}
		return noticias;
	}
	
	@RequestMapping(value = "/consultarUltimasPorTipoSeccion", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarUltimasPorTipoSeccion(@RequestHeader HttpHeaders headers, @RequestBody String idSeccion) {
		LOG.info("*********** WS consultarUltimasPorTipoSeccion*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarUltimasPorTipoSeccion( idSeccion) ;
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarUltimasPorTipoSeccion ",e);
		}
		return noticias;
	}
	
	@RequestMapping(value = "/consultarUltimasPorSeccion", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Noticias consultarUltimasPorSeccion(@RequestHeader HttpHeaders headers, @RequestBody String idSeccion) {
		LOG.info("*********** WS consultarUltimasPorSeccion*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.consultarUltimasPorSeccion( idSeccion) ;
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en consultarUltimasPorSeccion ",e);
		}
		return noticias;
	}
	
	@RequestMapping(value = "/consultarNotasExtraByCategoria", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByCategoria(@RequestHeader HttpHeaders headers, @RequestParam("idCategoria") String idCategoria, @RequestParam("fecha") String fecha) {
		LOG.info("*********** WS consultarNotasExtraByCategoria*******");
		List<NoticiaExtraRSSDTO> listRespuesta=null;
		try{
			listRespuesta= feedBO.consultarNotasExtraByCategoria(idCategoria, fecha);
		} catch (Exception e){
			LOG.error(" Error en consultarNotasExtraByCategoria ",e);
		}
		return listRespuesta;
	}
	
	@RequestMapping(value = "/consultarNotasExtraByTipoSeccion", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByTipoSeccion(@RequestHeader HttpHeaders headers, @RequestParam("idTipoSeccion") String idTipoSeccion, @RequestParam("fecha") String fecha) {
		LOG.info("*********** WS consultarNotasExtraByTipoSeccion*******");
		List<NoticiaExtraRSSDTO> listRespuesta=null;
		try{
			listRespuesta= feedBO.consultarNotasExtraByTipoSeccion(idTipoSeccion, fecha);
		} catch (Exception e){
			LOG.error(" Error en consultarNotasExtraByTipoSeccion ",e);
		}
		return listRespuesta;
	}
	
	@RequestMapping(value = "/consultarNotasExtraBySeccion", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public List<NoticiaExtraRSSDTO> consultarNotasExtraBySeccion(@RequestHeader HttpHeaders headers, @RequestParam("idSeccion") String idSeccion, @RequestParam("fecha") String fecha) {
		LOG.info("*********** WS consultarNotasExtraBySeccion*******");
		List<NoticiaExtraRSSDTO> listRespuesta=null;
		try{
			listRespuesta= feedBO.consultarNotasExtraBySeccion(idSeccion, fecha);
		} catch (Exception e){
			LOG.error(" Error en consultarNotasExtraBySeccion ",e);
		}
		return listRespuesta;
	}
	
	@RequestMapping(value = "/getCategorias", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Categorias getCategorias(@RequestHeader HttpHeaders headers ) {
		LOG.info("*********** WS getCategorias*******");
		Categorias categorias = new Categorias();
		try{
			categorias = feedBO.getCategorias(  ) ;
		} catch (Exception e){
			categorias.setMensaje(e.getMessage());
			categorias.setCategotiasLst(null);
			LOG.error(" Error en getCategorias ",e);
		}
		return categorias;
	}
	
	@RequestMapping(value = "/getSecciones", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Secciones getSecciones(@RequestHeader HttpHeaders headers ) {
		LOG.info("*********** WS getSecciones*******");
		Secciones secciones = new Secciones();
		try{
			secciones = feedBO.getSecciones(  ) ;
		} catch (Exception e){
			secciones.setMensaje(e.getMessage());
			secciones.setSeccionesLst(null);
			LOG.error(" Error en getSecciones ",e);
		}
		return secciones;
	}
	
	@RequestMapping(value = "/getTipoSecciones", method = RequestMethod.POST, headers = "Accept=application/json; charset=utf-8", produces = "application/json; charset=utf-8")
	@ResponseBody
	public Secciones getTipoSecciones(@RequestHeader HttpHeaders headers ) {
		LOG.info("*********** WS getTipoSecciones*******");
		Secciones secciones = new Secciones();
		try{
			secciones = feedBO.getTipoSecciones(  ) ;
		} catch (Exception e){
			secciones.setMensaje(e.getMessage());
			secciones.setSeccionesLst(null);
			LOG.error(" Error en getTipoSecciones ",e);
		}
		return secciones;
	}
	

	@RequestMapping(value = "/getNotaMagazine", method=RequestMethod.POST , headers="Accept=application/json; charset=utf-8")
	@ResponseBody
	public Noticias getNotaMagazine(@RequestBody RequestNotaDTO requestNota) {
		LOG.info("*********** WS getNotaMagazine*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.getNotaMagazine(requestNota) ;
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en getNotaMagazine ",e);
		}
		return noticias;
	}
	

	@RequestMapping(value="/getNota" , method=RequestMethod.POST , headers="Accept=application/json; charset=utf-8")
	@ResponseBody
	public Noticias getNota(@RequestBody RequestNotaDTO requestNota) {
		LOG.info("*********** WS getNota*******");
		Noticias noticias = new Noticias();
		try{
			noticias = feedBO.getNota(requestNota) ;
		} catch (Exception e){
			noticias.setMensaje(e.getMessage());
			noticias.setNoticiasLst(null);
			LOG.error(" Error en getNota ",e);
		}
		return noticias;
	}
	
	public FeedBO getFeedBO() {
		return feedBO;
	}

	@Autowired
	public void setFeedBO(FeedBO feedBO) {
		this.feedBO = feedBO;
	}	
}