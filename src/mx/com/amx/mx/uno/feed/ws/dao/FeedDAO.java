package mx.com.amx.mx.uno.feed.ws.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mx.com.amx.mx.uno.feed.ws.dto.CategoriaDTO;
import mx.com.amx.mx.uno.feed.ws.dto.NoticiaExtraRSSDTO;
import mx.com.amx.mx.uno.feed.ws.dto.NoticiaRSSDTO;
import mx.com.amx.mx.uno.feed.ws.dto.RequestNotaDTO;
import mx.com.amx.mx.uno.feed.ws.dto.SeccionDTO;
import mx.com.amx.mx.uno.feed.ws.dto.TipoSeccionDTO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Qualifier("feedDAO")
public class FeedDAO {

	private final Logger LOG = Logger.getLogger(this.getClass().getName());
	private JdbcTemplate jdbcTemplate;
	
	/**
	 * M�todo que consulta las noticias del magazine para generar un json
	 * @param idMagazine
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarNoticiasMagazineJSON(String idMagazine) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		
		try {
			
			sbQuery.append(" SELECT  ");
			sbQuery.append(" CASE   ");
			sbQuery.append(" WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN    ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))   ");
			sbQuery.append(" ELSE   ");
			sbQuery.append(" ('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))   ");
			sbQuery.append(" END as fcLink,   ");
			sbQuery.append(" N.FC_ID_CONTENIDO, ");
			sbQuery.append(" N.FC_ID_CATEGORIA, ");
			sbQuery.append(" N.FC_TITULO,  ");
			sbQuery.append(" N.FC_IMAGEN_PRINCIPAL, ");
			sbQuery.append(" N.CL_RTF_CONTENIDO, ");
			sbQuery.append(" VARCHAR_FORMAT(INM.FD_FECHA_REGISTRO,'YYYY-MM-DD HH24:MI:SS') AS FD_FECHA_REGISTRO ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA C, WPDB2INS.UNO_MX_C_SECCION S,  ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION TS, WPDB2INS.UNO_MX_N_NOTA N ");
			sbQuery.append(" LEFT OUTER JOIN WPDB2INS.UNO_MX_I_NOTA_MAGAZINE INM ON N.FC_ID_CONTENIDO = INM.FC_ID_CONTENIDO   ");
			sbQuery.append(" WHERE  FC_ID_MAGAZINE = ? ");
			sbQuery.append(" AND N.FC_ID_CATEGORIA = C.FC_ID_CATEGORIA AND C.FC_ID_SECCION = S.FC_ID_SECCION   ");
			sbQuery.append(" AND S.FC_ID_TIPO_SECCION = TS.FC_ID_TIPO_SECCION  ");
			sbQuery.append(" ORDER BY INM.FI_ORDEN ASC ");
			
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new Object[]{idMagazine}, new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_REGISTRO"));
							dto.setFcLink(rs.getString("fcLink"));
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarNoticiasMagazineJSON: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las noticias del magazine para generar un json
	 * @param idMagazine
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarCarruselClarosportsJSON(int numRegistros) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		
		try {
			
			sbQuery.append(" SELECT CN.FC_ID AS FC_ID_CONTENIDO ");
			sbQuery.append(" ,CN.FC_ID_CATEGORIA ");
			sbQuery.append(" ,CN.FC_TITULO ");
			sbQuery.append(" ,CN.FC_IMG_PRINCIPAL AS FC_IMAGEN_PRINCIPAL ");
			sbQuery.append(" ,CN.CL_RTF_CONTENIDO ");
			sbQuery.append(" ,CN.FC_FECHA_MODIFICACION AS FD_FECHA_MODIFICACION ");
			sbQuery.append(" ,('http://www.clarosports.com/'||COALESCE(CC.FC_ID_SECCION,'')||'/'||COALESCE(CC.FC_ID_CATEGORIA,'')||'/detalle/'|| COALESCE(CN.FC_NOMBRE,'')) AS FC_LINK ");
			sbQuery.append(" FROM WPDB2INS.CS_MX_N_NOTA CN ");
			sbQuery.append(" ,WPDB2INS.CS_MX_C_CATEGORIA CC ");
			sbQuery.append(" WHERE CC.FC_ID_CATEGORIA = CN.FC_ID_CATEGORIA ");
			sbQuery.append(" AND CC.FC_ID_SECCION != 'programas' ");
			/*sbQuery.append(" UNION ALL ");
			sbQuery.append(" SELECT NN.FC_ID ");
			sbQuery.append(" ,NN.FC_TIPO_CONTENIDO ");
			sbQuery.append(" ,NN.FC_TITULO ");
			sbQuery.append(" ,NN.FC_IMG_PRINCIPAL ");
			sbQuery.append(" ,NN.CL_RTF_CONTENIDO ");
			sbQuery.append(" ,NN.FD_FECHA_MODIFICACION ");
			sbQuery.append(" ,('http://nba.clarosports.com/'||COALESCE(NN.FC_NAME_SITE_AREA,'')||'/detalle/'|| COALESCE(NN.FC_NOMBRE,'')) ");
			sbQuery.append(" FROM WPDB2INS.NBA_MX_N_NOTA NN ");*/
			sbQuery.append(" ORDER BY FD_FECHA_MODIFICACION DESC ");
			sbQuery.append(" FETCH FIRST " + numRegistros + " ROWS ONLY ");
			
			LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFcLink(rs.getString("FC_LINK"));
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarCarruselClarosportsJSON: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las noticias de tipo Viral o Note lo Pierdas
	 * @param viralNoTeLoPierdas
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarNoticiasMagazine(String idMagazine) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		
		//LOG.info("Inicia consulta RSS Magazine: "+idMagazine);
		
		try {
			
			sbQuery.append(" SELECT A.FC_ID_CONTENIDO, A.FC_ID_CATEGORIA, A.FC_NOMBRE, A.FC_TITULO, ");
			sbQuery.append(" A.FC_DESCRIPCION, A.FC_ESCRIBIO, A.FC_LUGAR, A.FC_FUENTE, A.FC_ID_TIPO_NOTA,");
			sbQuery.append(" A.FC_IMAGEN_PRINCIPAL, A.FC_IMAGEN_PIE, A.FC_VIDEO_YOUTUBE, A.FC_ID_VIDEO_CONTENT, ");
			sbQuery.append(" A.FC_ID_VIDEO_PLAYER, A.CL_GALERIA_IMAGENES, A.FC_INFOGRAFIA, A.CL_RTF_CONTENIDO, ");
			sbQuery.append(" TO_CHAR(A.FD_FECHA_PUBLICACION, 'MM/DD/YY HH:MI AM') AS FD_FECHA_PUBLICACION, ");
			sbQuery.append(" A.FD_FECHA_MODIFICACION, A.FC_TAGS, A.FC_KEYWORDS, ");
			sbQuery.append(" A.FI_BAN_INFINITO_HOME, A.FI_BAN_VIDEO_VIRAL, A.FI_BAN_NO_TE_LO_PIERDAS, ");
			sbQuery.append(" A.FI_BAN_PATROCINIO, A.FC_PATROCINIO_BACKGROUND, A.FC_PATROCINIO_COLOR_TEXTO, ");
			sbQuery.append(" B.FC_FRIENDLY_URL AS FRIENDLY_CAT, D.FC_FRIENDLY_URL AS FRIENDLY_TIPO_SEC, ");
			sbQuery.append(" C.FC_FRIENDLY_URL AS FRIENDLY_SEC, C.FC_ID_TIPO_SECCION, B.FI_ID_APPS AS FI_ID_APPS_CAT, ");
			sbQuery.append(" C.FI_ID_APPS AS FI_ID_APPS_SEC, D.FI_ID_APPS AS FI_ID_APPS_TIPOSEC, ");
			sbQuery.append(" B.FC_DESCRIPCION AS FC_DESCRIPCION_CATEGORIA, ");
			sbQuery.append(" 'Magazine' AS fC_TITULO_RSS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA B, WPDB2INS.UNO_MX_C_SECCION C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION D, WPDB2INS.UNO_MX_N_NOTA A    ");
			sbQuery.append(" LEFT OUTER JOIN WPDB2INS.UNO_MX_I_NOTA_MAGAZINE INM ON A.FC_ID_CONTENIDO = INM.FC_ID_CONTENIDO  ");
			sbQuery.append(" WHERE  FC_ID_MAGAZINE = ? ");
			sbQuery.append(" AND A.FC_ID_CATEGORIA = B.FC_ID_CATEGORIA AND B.FC_ID_SECCION = C.FC_ID_SECCION  ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = D.FC_ID_TIPO_SECCION ");
			sbQuery.append(" ORDER BY INM.FI_ORDEN ASC ");
			
			//LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new Object[]{idMagazine}, new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_NOMBRE(rs.getString("FC_NOMBRE"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_DESCRIPCION(rs.getString("FC_DESCRIPCION"));
							dto.setFC_ESCRIBIO(rs.getString("FC_ESCRIBIO"));
							dto.setFC_LUGAR(rs.getString("FC_LUGAR"));
							dto.setFC_FUENTE(rs.getString("FC_FUENTE"));
							dto.setFC_ID_TIPO_NOTA(rs.getString("FC_ID_TIPO_NOTA"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setFC_IMAGEN_PIE(rs.getString("FC_IMAGEN_PIE"));
							dto.setFC_VIDEO_YOUTUBE(rs.getString("FC_VIDEO_YOUTUBE"));
							dto.setFC_ID_VIDEO_CONTENT(rs.getString("FC_ID_VIDEO_CONTENT"));
							dto.setFC_ID_VIDEO_PLAYER(rs.getString("FC_ID_VIDEO_PLAYER"));
							dto.setCL_GALERIA_IMAGENES(rs.getString("CL_GALERIA_IMAGENES"));
							dto.setFC_INFOGRAFIA(rs.getString("FC_INFOGRAFIA"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFC_TAGS(rs.getString("FC_TAGS"));
							dto.setFC_KEYWORDS(rs.getString("FC_KEYWORDS"));
							dto.setFI_BAN_INFINITO_HOME(rs.getString("FI_BAN_INFINITO_HOME"));
							dto.setFI_BAN_VIDEO_VIRAL(rs.getString("FI_BAN_VIDEO_VIRAL"));
							dto.setFI_BAN_NO_TE_LO_PIERDAS(rs.getString("FI_BAN_NO_TE_LO_PIERDAS"));
							dto.setFI_BAN_PATROCINIO(rs.getString("FI_BAN_PATROCINIO"));
							dto.setFC_PATROCINIO_BACKGROUND(rs.getString("FC_PATROCINIO_BACKGROUND"));
							dto.setFC_PATROCINIO_COLOR_TEXTO(rs.getString("FC_PATROCINIO_COLOR_TEXTO"));
							
							String urlNota = rs.getString("FRIENDLY_TIPO_SEC") + "/" + rs.getString("FRIENDLY_SEC") 
											+ "/" + rs.getString("FRIENDLY_CAT") + "/detalle/" + rs.getString("FC_NOMBRE");
							dto.setFcLink(urlNota);
							dto.setFC_CATEGORIA(rs.getString("FRIENDLY_CAT"));
							dto.setFC_SECCION(rs.getString("FRIENDLY_SEC"));
							dto.setFI_ID_APPS_CAT(rs.getInt("FI_ID_APPS_CAT"));
							dto.setFI_ID_APPS_SEC(rs.getInt("FI_ID_APPS_SEC"));
							dto.setFI_ID_APPS_TIPOSEC(rs.getInt("FI_ID_APPS_TIPOSEC"));
							
							dto.setFC_DESCRIPCION_CATEGORIA(rs.getString("FC_DESCRIPCION_CATEGORIA"));
							dto.setFC_TITULO_RSS(rs.getString("fC_TITULO_RSS"));
							//LOG.info("rs.getDate('FD_FECHA_PUBLICACION'): "+rs.getString("FD_FECHA_PUBLICACION"));
							//LOG.info("dto.fechaPub: "+dto.getFD_FECHA_PUBLICACION());
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarNoticiasMagazine: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta las noticias de tipo Viral o Note lo Pierdas
	 * @param viralNoTeLoPierdas
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarNoticiasViralesNoTeLoPierdas(String viralNoTeLoPierdas) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		//LOG.info("Inicia consulta RSS viralNoTeLoPierdas: "+viralNoTeLoPierdas);
		
		try {
			
			sbQuery.append(" SELECT A.FC_ID_CONTENIDO, A.FC_ID_CATEGORIA, A.FC_NOMBRE, A.FC_TITULO, ");
			sbQuery.append(" A.FC_DESCRIPCION, A.FC_ESCRIBIO, A.FC_LUGAR, A.FC_FUENTE, A.FC_ID_TIPO_NOTA,");
			sbQuery.append(" A.FC_IMAGEN_PRINCIPAL, A.FC_IMAGEN_PIE, A.FC_VIDEO_YOUTUBE, A.FC_ID_VIDEO_CONTENT, ");
			sbQuery.append(" A.FC_ID_VIDEO_PLAYER, A.CL_GALERIA_IMAGENES, A.FC_INFOGRAFIA, A.CL_RTF_CONTENIDO, ");
			sbQuery.append(" TO_CHAR(A.FD_FECHA_PUBLICACION, 'MM/DD/YY HH:MI AM') AS FD_FECHA_PUBLICACION, ");
			sbQuery.append(" A.FD_FECHA_MODIFICACION, A.FC_TAGS, A.FC_KEYWORDS, ");
			sbQuery.append(" A.FI_BAN_INFINITO_HOME, A.FI_BAN_VIDEO_VIRAL, A.FI_BAN_NO_TE_LO_PIERDAS, ");
			sbQuery.append(" A.FI_BAN_PATROCINIO, A.FC_PATROCINIO_BACKGROUND, A.FC_PATROCINIO_COLOR_TEXTO, ");
			sbQuery.append(" B.FC_FRIENDLY_URL AS FRIENDLY_CAT, D.FC_FRIENDLY_URL AS FRIENDLY_TIPO_SEC, ");
			sbQuery.append(" C.FC_FRIENDLY_URL AS FRIENDLY_SEC, C.FC_ID_TIPO_SECCION, B.FI_ID_APPS AS FI_ID_APPS_CAT, ");
			sbQuery.append(" C.FI_ID_APPS AS FI_ID_APPS_SEC, D.FI_ID_APPS AS FI_ID_APPS_TIPOSEC, ");
			sbQuery.append(" B.FC_DESCRIPCION AS FC_DESCRIPCION_CATEGORIA, ");
			if(viralNoTeLoPierdas.equalsIgnoreCase("virales"))
				sbQuery.append(" 'Videos virales' AS fC_TITULO_RSS ");
			else if(viralNoTeLoPierdas.equalsIgnoreCase("no-te-lo-pierdas"))
				sbQuery.append(" 'No te lo pierdas' AS fC_TITULO_RSS ");
			
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA A, WPDB2INS.UNO_MX_C_CATEGORIA B, WPDB2INS.UNO_MX_C_SECCION C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION D  ");
			sbQuery.append(" WHERE A.FC_ID_CATEGORIA = B.FC_ID_CATEGORIA AND B.FC_ID_SECCION = C.FC_ID_SECCION ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = D.FC_ID_TIPO_SECCION ");
			if(viralNoTeLoPierdas.equalsIgnoreCase("virales"))
				sbQuery.append(" AND A.FI_BAN_VIDEO_VIRAL = 1 ");
			else if(viralNoTeLoPierdas.equalsIgnoreCase("no-te-lo-pierdas"))
				sbQuery.append(" AND A.FI_BAN_NO_TE_LO_PIERDAS = 1 ");
			
			sbQuery.append(" ORDER BY A.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 10 ROWS ONLY OPTIMIZE FOR 10 ROWS ");
			
			//LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_NOMBRE(rs.getString("FC_NOMBRE"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_DESCRIPCION(rs.getString("FC_DESCRIPCION"));
							dto.setFC_ESCRIBIO(rs.getString("FC_ESCRIBIO"));
							dto.setFC_LUGAR(rs.getString("FC_LUGAR"));
							dto.setFC_FUENTE(rs.getString("FC_FUENTE"));
							dto.setFC_ID_TIPO_NOTA(rs.getString("FC_ID_TIPO_NOTA"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setFC_IMAGEN_PIE(rs.getString("FC_IMAGEN_PIE"));
							dto.setFC_VIDEO_YOUTUBE(rs.getString("FC_VIDEO_YOUTUBE"));
							dto.setFC_ID_VIDEO_CONTENT(rs.getString("FC_ID_VIDEO_CONTENT"));
							dto.setFC_ID_VIDEO_PLAYER(rs.getString("FC_ID_VIDEO_PLAYER"));
							dto.setCL_GALERIA_IMAGENES(rs.getString("CL_GALERIA_IMAGENES"));
							dto.setFC_INFOGRAFIA(rs.getString("FC_INFOGRAFIA"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFC_TAGS(rs.getString("FC_TAGS"));
							dto.setFC_KEYWORDS(rs.getString("FC_KEYWORDS"));
							dto.setFI_BAN_INFINITO_HOME(rs.getString("FI_BAN_INFINITO_HOME"));
							dto.setFI_BAN_VIDEO_VIRAL(rs.getString("FI_BAN_VIDEO_VIRAL"));
							dto.setFI_BAN_NO_TE_LO_PIERDAS(rs.getString("FI_BAN_NO_TE_LO_PIERDAS"));
							dto.setFI_BAN_PATROCINIO(rs.getString("FI_BAN_PATROCINIO"));
							dto.setFC_PATROCINIO_BACKGROUND(rs.getString("FC_PATROCINIO_BACKGROUND"));
							dto.setFC_PATROCINIO_COLOR_TEXTO(rs.getString("FC_PATROCINIO_COLOR_TEXTO"));
							
							String urlNota = rs.getString("FRIENDLY_TIPO_SEC") + "/" + rs.getString("FRIENDLY_SEC") 
											+ "/" + rs.getString("FRIENDLY_CAT") + "/detalle/" + rs.getString("FC_NOMBRE");
							dto.setFcLink(urlNota);
							
							dto.setFI_ID_APPS_CAT(rs.getInt("FI_ID_APPS_CAT"));
							dto.setFI_ID_APPS_SEC(rs.getInt("FI_ID_APPS_SEC"));
							dto.setFI_ID_APPS_TIPOSEC(rs.getInt("FI_ID_APPS_TIPOSEC"));
							
							dto.setFC_DESCRIPCION_CATEGORIA(rs.getString("FC_DESCRIPCION_CATEGORIA"));
							dto.setFC_TITULO_RSS(rs.getString("fC_TITULO_RSS"));
							//LOG.info("rs.getDate('FD_FECHA_PUBLICACION'): "+rs.getString("FD_FECHA_PUBLICACION"));
							//LOG.info("dto.fechaPub: "+dto.getFD_FECHA_PUBLICACION());
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarNoticiasViralesNoTeLoPierdas: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta las noticias de un categoria
	 * @param idCategoria
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarNoticias(String idCategoria) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		//LOG.info("Inicia consulta RSS noticias");
		
		try {
			
			sbQuery.append(" SELECT A.FC_ID_CONTENIDO, A.FC_ID_CATEGORIA, A.FC_NOMBRE, A.FC_TITULO, ");
			sbQuery.append(" A.FC_DESCRIPCION, A.FC_ESCRIBIO, A.FC_LUGAR, A.FC_FUENTE, A.FC_ID_TIPO_NOTA,");
			sbQuery.append(" A.FC_IMAGEN_PRINCIPAL, A.FC_IMAGEN_PIE, A.FC_VIDEO_YOUTUBE, A.FC_ID_VIDEO_CONTENT, ");
			sbQuery.append(" A.FC_ID_VIDEO_PLAYER, A.CL_GALERIA_IMAGENES, A.FC_INFOGRAFIA, A.CL_RTF_CONTENIDO, ");
			sbQuery.append(" TO_CHAR(A.FD_FECHA_PUBLICACION, 'MM/DD/YY HH:MI AM') AS FD_FECHA_PUBLICACION, ");
			sbQuery.append(" A.FD_FECHA_MODIFICACION, A.FC_TAGS, A.FC_KEYWORDS, ");
			sbQuery.append(" A.FI_BAN_INFINITO_HOME, A.FI_BAN_VIDEO_VIRAL, A.FI_BAN_NO_TE_LO_PIERDAS, ");
			sbQuery.append(" A.FI_BAN_PATROCINIO, A.FC_PATROCINIO_BACKGROUND, A.FC_PATROCINIO_COLOR_TEXTO, ");
			sbQuery.append(" B.FC_FRIENDLY_URL AS FRIENDLY_CAT, D.FC_FRIENDLY_URL AS FRIENDLY_TIPO_SEC, ");
			sbQuery.append(" C.FC_FRIENDLY_URL AS FRIENDLY_SEC, C.FC_ID_TIPO_SECCION, B.FI_ID_APPS AS FI_ID_APPS_CAT, ");
			sbQuery.append(" C.FI_ID_APPS AS FI_ID_APPS_SEC, D.FI_ID_APPS AS FI_ID_APPS_TIPOSEC, ");
			sbQuery.append(" B.FC_DESCRIPCION AS FC_DESCRIPCION_CATEGORIA, ");
			sbQuery.append(" B.FC_DESCRIPCION AS fC_TITULO_RSS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA A, WPDB2INS.UNO_MX_C_CATEGORIA B, WPDB2INS.UNO_MX_C_SECCION C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION D  ");
			sbQuery.append(" WHERE A.FC_ID_CATEGORIA = B.FC_ID_CATEGORIA AND B.FC_ID_SECCION = C.FC_ID_SECCION ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = D.FC_ID_TIPO_SECCION ");
			sbQuery.append(" AND A.FC_ID_CATEGORIA = ? ");
			listObjects.add(idCategoria);
			sbQuery.append(" ORDER BY A.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 10 ROWS ONLY OPTIMIZE FOR 10 ROWS ");
			
			//LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), listObjects.toArray(), new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_NOMBRE(rs.getString("FC_NOMBRE"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_DESCRIPCION(rs.getString("FC_DESCRIPCION"));
							dto.setFC_ESCRIBIO(rs.getString("FC_ESCRIBIO"));
							dto.setFC_LUGAR(rs.getString("FC_LUGAR"));
							dto.setFC_FUENTE(rs.getString("FC_FUENTE"));
							dto.setFC_ID_TIPO_NOTA(rs.getString("FC_ID_TIPO_NOTA"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setFC_IMAGEN_PIE(rs.getString("FC_IMAGEN_PIE"));
							dto.setFC_VIDEO_YOUTUBE(rs.getString("FC_VIDEO_YOUTUBE"));
							dto.setFC_ID_VIDEO_CONTENT(rs.getString("FC_ID_VIDEO_CONTENT"));
							dto.setFC_ID_VIDEO_PLAYER(rs.getString("FC_ID_VIDEO_PLAYER"));
							dto.setCL_GALERIA_IMAGENES(rs.getString("CL_GALERIA_IMAGENES"));
							dto.setFC_INFOGRAFIA(rs.getString("FC_INFOGRAFIA"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFC_TAGS(rs.getString("FC_TAGS"));
							dto.setFC_KEYWORDS(rs.getString("FC_KEYWORDS"));
							dto.setFI_BAN_INFINITO_HOME(rs.getString("FI_BAN_INFINITO_HOME"));
							dto.setFI_BAN_VIDEO_VIRAL(rs.getString("FI_BAN_VIDEO_VIRAL"));
							dto.setFI_BAN_NO_TE_LO_PIERDAS(rs.getString("FI_BAN_NO_TE_LO_PIERDAS"));
							dto.setFI_BAN_PATROCINIO(rs.getString("FI_BAN_PATROCINIO"));
							dto.setFC_PATROCINIO_BACKGROUND(rs.getString("FC_PATROCINIO_BACKGROUND"));
							dto.setFC_PATROCINIO_COLOR_TEXTO(rs.getString("FC_PATROCINIO_COLOR_TEXTO"));
							
							String urlNota = rs.getString("FRIENDLY_TIPO_SEC") + "/" + rs.getString("FRIENDLY_SEC") 
											+ "/" + rs.getString("FRIENDLY_CAT") + "/detalle/" + rs.getString("FC_NOMBRE");
							dto.setFcLink(urlNota);
							
							dto.setFI_ID_APPS_CAT(rs.getInt("FI_ID_APPS_CAT"));
							dto.setFI_ID_APPS_SEC(rs.getInt("FI_ID_APPS_SEC"));
							dto.setFI_ID_APPS_TIPOSEC(rs.getInt("FI_ID_APPS_TIPOSEC"));
							
							dto.setFC_DESCRIPCION_CATEGORIA(rs.getString("FC_DESCRIPCION_CATEGORIA"));
							dto.setFC_TITULO_RSS(rs.getString("fC_TITULO_RSS"));
							//LOG.info("rs.getDate('FD_FECHA_PUBLICACION'): "+rs.getString("FD_FECHA_PUBLICACION"));
							//LOG.info("dto.fechaPub: "+dto.getFD_FECHA_PUBLICACION());
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarNoticias: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las noticias de un categoria
	 * @param idCategoria
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByCategoria(String idCategoria, String fecha) throws Exception {
		ArrayList<NoticiaExtraRSSDTO> resultado = new ArrayList<NoticiaExtraRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		
		try {
			
			sbQuery.append("SELECT ");  
			sbQuery.append("CASE  ");
			sbQuery.append("WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN   ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("ELSE  ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("END as url_nota,  ");
			sbQuery.append("N.FC_NOMBRE as nombre,    ");
			sbQuery.append("N.FC_TITULO as titulo,    ");
			sbQuery.append("N.FC_DESCRIPCION as descripcion,     ");
			sbQuery.append("N.FC_FUENTE as fuente,   ");
			sbQuery.append("N.FC_IMAGEN_PRINCIPAL as imagen_principal,    ");
			sbQuery.append("N.FC_IMAGEN_PIE as pie_imagen,   ");
			sbQuery.append("C.FC_DESCRIPCION as descripcion_categoria, ");
			sbQuery.append("S.FC_DESCRIPCION as descripcion_seccion, ");
			sbQuery.append("TS.FC_DESCRIPCION as descripcion_tipo_seccion ");
			sbQuery.append("FROM WPDB2INS.UNO_MX_H_NOTA N,     ");
			sbQuery.append("WPDB2INS.UNO_MX_C_TIPO_SECCION TS,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_SECCION S,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_CATEGORIA  C    ");
			sbQuery.append("WHERE    ");
			sbQuery.append("C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA    ");
			sbQuery.append("AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION    ");
			sbQuery.append("AND N.FC_ID_CATEGORIA=?  ");
			listObjects.add(idCategoria);
			if(fecha!= null && !fecha.trim().equals("")){
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				listObjects.add(fecha);
			}
			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 4 ROWS ONLY OPTIMIZE FOR 4 ROWS ");
			
			resultado = (ArrayList<NoticiaExtraRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), listObjects.toArray(), new RowMapper(){
						public NoticiaExtraRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaExtraRSSDTO dto = new NoticiaExtraRSSDTO();
							dto.setDescripcion(rs.getString("descripcion"));
							dto.setDescripcion_categoria(rs.getString("descripcion_categoria"));
							dto.setDescripcion_seccion(rs.getString("descripcion_seccion"));
							dto.setDescripcion_tipo_seccion(rs.getString("descripcion_tipo_seccion"));
							dto.setFuente(rs.getString("fuente"));
							dto.setImagen_principal(rs.getString("imagen_principal"));
							dto.setNombre(rs.getString("nombre"));
							dto.setPie_imagen(rs.getString("pie_imagen"));
							dto.setTitulo(rs.getString("titulo"));
							dto.setUrl_nota(rs.getString("url_nota"));
							return dto;
						}
					});
		} catch (Exception e) {
			LOG.error("Exception en consultarNoticias: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta las ultimas noticias por tipo secci�n
	 * @param idSeccion
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarUltimasPorTipoSeccion(String idTipoSeccion ) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		//LOG.info("Inicia consultarUltimasPorTipoSeccion");
		
		try {
			sbQuery.append(" SELECT A.FC_ID_CONTENIDO, A.FC_ID_CATEGORIA, A.FC_NOMBRE, A.FC_TITULO, ");
			sbQuery.append(" A.FC_DESCRIPCION, A.FC_ESCRIBIO, A.FC_LUGAR, A.FC_FUENTE, A.FC_ID_TIPO_NOTA,");
			sbQuery.append(" A.FC_IMAGEN_PRINCIPAL, A.FC_IMAGEN_PIE, A.FC_VIDEO_YOUTUBE, A.FC_ID_VIDEO_CONTENT, ");
			sbQuery.append(" A.FC_ID_VIDEO_PLAYER, A.CL_GALERIA_IMAGENES, A.FC_INFOGRAFIA, A.CL_RTF_CONTENIDO, ");
			sbQuery.append(" TO_CHAR(A.FD_FECHA_PUBLICACION, 'MM/DD/YY HH:MI AM') AS FD_FECHA_PUBLICACION, ");
			sbQuery.append(" A.FD_FECHA_MODIFICACION, A.FC_TAGS, A.FC_KEYWORDS, ");
			sbQuery.append(" A.FI_BAN_INFINITO_HOME, A.FI_BAN_VIDEO_VIRAL, A.FI_BAN_NO_TE_LO_PIERDAS, ");
			sbQuery.append(" A.FI_BAN_PATROCINIO, A.FC_PATROCINIO_BACKGROUND, A.FC_PATROCINIO_COLOR_TEXTO, ");
			sbQuery.append(" B.FC_FRIENDLY_URL AS FRIENDLY_CAT, D.FC_FRIENDLY_URL AS FRIENDLY_TIPO_SEC, ");
			sbQuery.append(" C.FC_FRIENDLY_URL AS FRIENDLY_SEC, C.FC_ID_TIPO_SECCION, B.FI_ID_APPS AS FI_ID_APPS_CAT, ");
			sbQuery.append(" C.FI_ID_APPS AS FI_ID_APPS_SEC, D.FI_ID_APPS AS FI_ID_APPS_TIPOSEC, ");
			sbQuery.append(" C.FC_DESCRIPCION AS FC_DESCRIPCION_CATEGORIA, ");
			sbQuery.append(" D.FC_DESCRIPCION AS fC_TITULO_RSS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA A, WPDB2INS.UNO_MX_C_CATEGORIA B, WPDB2INS.UNO_MX_C_SECCION C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION D  ");
			sbQuery.append(" WHERE A.FC_ID_CATEGORIA = B.FC_ID_CATEGORIA AND B.FC_ID_SECCION = C.FC_ID_SECCION ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = D.FC_ID_TIPO_SECCION ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = ? ");
			listObjects.add(idTipoSeccion);
			sbQuery.append(" ORDER BY A.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 10 ROWS ONLY OPTIMIZE FOR 10 ROWS ");
		
			//LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), listObjects.toArray(), new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_NOMBRE(rs.getString("FC_NOMBRE"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_DESCRIPCION(rs.getString("FC_DESCRIPCION"));
							dto.setFC_ESCRIBIO(rs.getString("FC_ESCRIBIO"));
							dto.setFC_LUGAR(rs.getString("FC_LUGAR"));
							dto.setFC_FUENTE(rs.getString("FC_FUENTE"));
							dto.setFC_ID_TIPO_NOTA(rs.getString("FC_ID_TIPO_NOTA"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setFC_IMAGEN_PIE(rs.getString("FC_IMAGEN_PIE"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFC_VIDEO_YOUTUBE(rs.getString("FC_VIDEO_YOUTUBE"));
							dto.setFC_ID_VIDEO_CONTENT(rs.getString("FC_ID_VIDEO_CONTENT"));
							dto.setFC_ID_VIDEO_PLAYER(rs.getString("FC_ID_VIDEO_PLAYER"));
							dto.setCL_GALERIA_IMAGENES(rs.getString("CL_GALERIA_IMAGENES"));
							dto.setFC_INFOGRAFIA(rs.getString("FC_INFOGRAFIA"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFC_TAGS(rs.getString("FC_TAGS"));
							dto.setFC_KEYWORDS(rs.getString("FC_KEYWORDS"));
							dto.setFI_BAN_INFINITO_HOME(rs.getString("FI_BAN_INFINITO_HOME"));
							dto.setFI_BAN_VIDEO_VIRAL(rs.getString("FI_BAN_VIDEO_VIRAL"));
							dto.setFI_BAN_NO_TE_LO_PIERDAS(rs.getString("FI_BAN_NO_TE_LO_PIERDAS"));
							dto.setFI_BAN_PATROCINIO(rs.getString("FI_BAN_PATROCINIO"));
							dto.setFC_PATROCINIO_BACKGROUND(rs.getString("FC_PATROCINIO_BACKGROUND"));
							dto.setFC_PATROCINIO_COLOR_TEXTO(rs.getString("FC_PATROCINIO_COLOR_TEXTO"));
							
							String urlNota = rs.getString("FRIENDLY_TIPO_SEC") + "/" + rs.getString("FRIENDLY_SEC") 
											+ "/" + rs.getString("FRIENDLY_CAT") + "/detalle/" + rs.getString("FC_NOMBRE");
							
							dto.setFcLink(urlNota);

							dto.setFI_ID_APPS_CAT(rs.getInt("FI_ID_APPS_CAT"));
							dto.setFI_ID_APPS_SEC(rs.getInt("FI_ID_APPS_SEC"));
							dto.setFI_ID_APPS_TIPOSEC(rs.getInt("FI_ID_APPS_TIPOSEC"));
							
							dto.setFC_DESCRIPCION_CATEGORIA(rs.getString("FC_DESCRIPCION_CATEGORIA"));
							dto.setFC_TITULO_RSS(rs.getString("fC_TITULO_RSS"));
							//LOG.info("rs.getDate('FD_FECHA_PUBLICACION'): "+rs.getString("FD_FECHA_PUBLICACION"));
							//LOG.info("dto.fechaPub: "+dto.getFD_FECHA_PUBLICACION());
							
							return dto;
						}
					});
			
		} catch (Exception e) {
			LOG.error("Exception en consultarUltimasPorTipoSeccion: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las ultimas noticias por tipo secci�n
	 * @param idSeccion
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaExtraRSSDTO> consultarNotasExtraByTipoSeccion(String idSeccion, String fecha ) throws Exception {
		ArrayList<NoticiaExtraRSSDTO> resultado = new ArrayList<NoticiaExtraRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		
		try {
			sbQuery.append("SELECT ");  
			sbQuery.append("CASE  ");
			sbQuery.append("WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN   ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("ELSE  ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("END as url_nota,  ");
			sbQuery.append("N.FC_NOMBRE as nombre,    ");
			sbQuery.append("N.FC_TITULO as titulo,    ");
			sbQuery.append("N.FC_DESCRIPCION as descripcion,     ");
			sbQuery.append("N.FC_FUENTE as fuente,   ");
			sbQuery.append("N.FC_IMAGEN_PRINCIPAL as imagen_principal,    ");
			sbQuery.append("N.FC_IMAGEN_PIE as pie_imagen,   ");
			sbQuery.append("C.FC_DESCRIPCION as descripcion_categoria, ");
			sbQuery.append("S.FC_DESCRIPCION as descripcion_seccion, ");
			sbQuery.append("TS.FC_DESCRIPCION as descripcion_tipo_seccion ");
			sbQuery.append("FROM WPDB2INS.UNO_MX_H_NOTA N,     ");
			sbQuery.append("WPDB2INS.UNO_MX_C_TIPO_SECCION TS,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_SECCION S,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_CATEGORIA  C    ");
			sbQuery.append("WHERE    ");
			sbQuery.append("C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA    ");
			sbQuery.append("AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION    ");
			sbQuery.append("AND S.FC_ID_TIPO_SECCION=?  ");
			
			listObjects.add(idSeccion);
			if(fecha!= null && !fecha.trim().equals("")){
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				listObjects.add(fecha);
			}

			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 4 ROWS ONLY OPTIMIZE FOR 4 ROWS ");
			
			resultado = (ArrayList<NoticiaExtraRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), listObjects.toArray(), new RowMapper(){
						public NoticiaExtraRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaExtraRSSDTO dto = new NoticiaExtraRSSDTO();
							dto.setDescripcion(rs.getString("descripcion"));
							dto.setDescripcion_categoria(rs.getString("descripcion_categoria"));
							dto.setDescripcion_seccion(rs.getString("descripcion_seccion"));
							dto.setDescripcion_tipo_seccion(rs.getString("descripcion_tipo_seccion"));
							dto.setFuente(rs.getString("fuente"));
							dto.setImagen_principal(rs.getString("imagen_principal"));
							dto.setNombre(rs.getString("nombre"));
							dto.setPie_imagen(rs.getString("pie_imagen"));
							dto.setTitulo(rs.getString("titulo"));
							dto.setUrl_nota(rs.getString("url_nota"));
							return dto;
						}
					});
			
		} catch (Exception e) {
			LOG.error("Exception en consultarUltimasPorTipoSeccion: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las ultimas noticias por secci�n
	 * @param idSeccion
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaExtraRSSDTO> consultarNotasExtraBySeccion(String idSeccion, String fecha ) throws Exception {
		ArrayList<NoticiaExtraRSSDTO> resultado = new ArrayList<NoticiaExtraRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		
		try {
			sbQuery.append("SELECT ");  
			sbQuery.append("CASE  ");
			sbQuery.append("WHEN TS.FC_ID_TIPO_SECCION ='especiales' THEN   ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("ELSE  ");
			sbQuery.append("('http://www.unotv.com/'||COALESCE(TS.FC_ID_TIPO_SECCION,'')||'s/'||COALESCE(S.FC_FRIENDLY_URL,'')||'/'||COALESCE(C.FC_FRIENDLY_URL,'')||'/detalle/'|| COALESCE(N.FC_NOMBRE,''))  ");
			sbQuery.append("END as url_nota,  ");
			sbQuery.append("N.FC_NOMBRE as nombre,    ");
			sbQuery.append("N.FC_TITULO as titulo,    ");
			sbQuery.append("N.FC_DESCRIPCION as descripcion,     ");
			sbQuery.append("N.FC_FUENTE as fuente,   ");
			sbQuery.append("N.FC_IMAGEN_PRINCIPAL as imagen_principal,    ");
			sbQuery.append("N.FC_IMAGEN_PIE as pie_imagen,   ");
			sbQuery.append("C.FC_DESCRIPCION as descripcion_categoria, ");
			sbQuery.append("S.FC_DESCRIPCION as descripcion_seccion, ");
			sbQuery.append("TS.FC_DESCRIPCION as descripcion_tipo_seccion ");
			sbQuery.append("FROM WPDB2INS.UNO_MX_H_NOTA N,     ");
			sbQuery.append("WPDB2INS.UNO_MX_C_TIPO_SECCION TS,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_SECCION S,    ");
			sbQuery.append("WPDB2INS.UNO_MX_C_CATEGORIA  C    ");
			sbQuery.append("WHERE    ");
			sbQuery.append("C.FC_ID_CATEGORIA=N.FC_ID_CATEGORIA    ");
			sbQuery.append("AND C.FC_ID_SECCION=S.FC_ID_SECCION AND S.FC_ID_TIPO_SECCION=TS.FC_ID_TIPO_SECCION    ");
			sbQuery.append("AND S.FC_ID_SECCION=?  ");
			
			listObjects.add(idSeccion);
			
			if(fecha!= null && !fecha.trim().equals("")){
				sbQuery.append(" AND N.FD_FECHA_PUBLICACION < ? ");
				listObjects.add(fecha);
			}

			sbQuery.append(" ORDER BY N.FD_FECHA_PUBLICACION DESC ");
			sbQuery.append(" FETCH FIRST 4 ROWS ONLY OPTIMIZE FOR 4 ROWS ");
			
			resultado = (ArrayList<NoticiaExtraRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), listObjects.toArray(), new RowMapper(){
						public NoticiaExtraRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaExtraRSSDTO dto = new NoticiaExtraRSSDTO();
							dto.setDescripcion(rs.getString("descripcion"));
							dto.setDescripcion_categoria(rs.getString("descripcion_categoria"));
							dto.setDescripcion_seccion(rs.getString("descripcion_seccion"));
							dto.setDescripcion_tipo_seccion(rs.getString("descripcion_tipo_seccion"));
							dto.setFuente(rs.getString("fuente"));
							dto.setImagen_principal(rs.getString("imagen_principal"));
							dto.setNombre(rs.getString("nombre"));
							dto.setPie_imagen(rs.getString("pie_imagen"));
							dto.setTitulo(rs.getString("titulo"));
							dto.setUrl_nota(rs.getString("url_nota"));
							return dto;
						}
					});
			
		} catch (Exception e) {
			LOG.error("Exception en consultarNotasExtraBySeccion: ",e);
		}
		
		return resultado;
	}
	/**
	 * M�todo que consulta las ultimas noticias por secci�n
	 * @param idSeccion
	 * @return List<NoticiaRSSDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<NoticiaRSSDTO> consultarUltimasPorSeccion(String idSeccion ) throws Exception {
		ArrayList<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		StringBuffer sbQuery = new StringBuffer();
		List<Object> listObjects = new ArrayList<Object>();
		//LOG.info("Inicia consultarUltimasPorSeccion");
		
		try {
			sbQuery.append(" SELECT A.FC_ID_CONTENIDO, A.FC_ID_CATEGORIA, A.FC_NOMBRE, A.FC_TITULO, ");
			sbQuery.append(" A.FC_DESCRIPCION, A.FC_ESCRIBIO, A.FC_LUGAR, A.FC_FUENTE, A.FC_ID_TIPO_NOTA,");
			sbQuery.append(" A.FC_IMAGEN_PRINCIPAL, A.FC_IMAGEN_PIE, A.FC_VIDEO_YOUTUBE, A.FC_ID_VIDEO_CONTENT, ");
			sbQuery.append(" A.FC_ID_VIDEO_PLAYER, A.CL_GALERIA_IMAGENES, A.FC_INFOGRAFIA, A.CL_RTF_CONTENIDO, ");
			sbQuery.append(" TO_CHAR(A.FD_FECHA_PUBLICACION, 'MM/DD/YY HH:MI AM') AS FD_FECHA_PUBLICACION, ");
			sbQuery.append(" A.FD_FECHA_MODIFICACION, A.FC_TAGS, A.FC_KEYWORDS, ");
			sbQuery.append(" A.FI_BAN_INFINITO_HOME, A.FI_BAN_VIDEO_VIRAL, A.FI_BAN_NO_TE_LO_PIERDAS, ");
			sbQuery.append(" A.FI_BAN_PATROCINIO, A.FC_PATROCINIO_BACKGROUND, A.FC_PATROCINIO_COLOR_TEXTO, ");
			sbQuery.append(" B.FC_FRIENDLY_URL AS FRIENDLY_CAT, D.FC_FRIENDLY_URL AS FRIENDLY_TIPO_SEC, ");
			sbQuery.append(" C.FC_FRIENDLY_URL AS FRIENDLY_SEC, C.FC_ID_TIPO_SECCION, B.FI_ID_APPS AS FI_ID_APPS_CAT, ");
			sbQuery.append(" C.FI_ID_APPS AS FI_ID_APPS_SEC, D.FI_ID_APPS AS FI_ID_APPS_TIPOSEC, ");
			sbQuery.append(" B.FC_DESCRIPCION AS FC_DESCRIPCION_CATEGORIA, ");
			sbQuery.append(" C.FC_DESCRIPCION AS fC_TITULO_RSS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_N_NOTA A, WPDB2INS.UNO_MX_C_CATEGORIA B, WPDB2INS.UNO_MX_C_SECCION C, ");
			sbQuery.append(" WPDB2INS.UNO_MX_C_TIPO_SECCION D  ");
			sbQuery.append(" WHERE A.FC_ID_CATEGORIA = B.FC_ID_CATEGORIA AND B.FC_ID_SECCION = C.FC_ID_SECCION ");
			sbQuery.append(" AND C.FC_ID_TIPO_SECCION = D.FC_ID_TIPO_SECCION ");
			sbQuery.append(" AND C.FC_ID_SECCION = ? ");
			listObjects.add(idSeccion);
			sbQuery.append(" ORDER BY A.FD_FECHA_PUBLICACION DESC ");
			
			sbQuery.append(" FETCH FIRST 10 ROWS ONLY OPTIMIZE FOR 10 ROWS ");
			
			//LOG.info("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<NoticiaRSSDTO>) this.jdbcTemplate.query(
					sbQuery.toString(),listObjects.toArray(), new RowMapper(){
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							dto.setFC_ID_CONTENIDO(rs.getString("FC_ID_CONTENIDO"));
							dto.setFC_ID_CATEGORIA(rs.getString("FC_ID_CATEGORIA"));
							dto.setFC_NOMBRE(rs.getString("FC_NOMBRE"));
							dto.setFC_TITULO(rs.getString("FC_TITULO"));
							dto.setFC_DESCRIPCION(rs.getString("FC_DESCRIPCION"));
							dto.setFC_ESCRIBIO(rs.getString("FC_ESCRIBIO"));
							dto.setFC_LUGAR(rs.getString("FC_LUGAR"));
							dto.setFC_FUENTE(rs.getString("FC_FUENTE"));
							dto.setFC_ID_TIPO_NOTA(rs.getString("FC_ID_TIPO_NOTA"));
							dto.setFC_IMAGEN_PRINCIPAL(rs.getString("FC_IMAGEN_PRINCIPAL"));
							dto.setFC_IMAGEN_PIE(rs.getString("FC_IMAGEN_PIE"));
							dto.setFC_VIDEO_YOUTUBE(rs.getString("FC_VIDEO_YOUTUBE"));
							dto.setFC_ID_VIDEO_CONTENT(rs.getString("FC_ID_VIDEO_CONTENT"));
							dto.setFC_ID_VIDEO_PLAYER(rs.getString("FC_ID_VIDEO_PLAYER"));
							dto.setCL_GALERIA_IMAGENES(rs.getString("CL_GALERIA_IMAGENES"));
							dto.setFC_INFOGRAFIA(rs.getString("FC_INFOGRAFIA"));
							dto.setCL_RTF_CONTENIDO(rs.getString("CL_RTF_CONTENIDO"));
							dto.setFD_FECHA_PUBLICACION(rs.getString("FD_FECHA_PUBLICACION"));
							dto.setFD_FECHA_MODIFICACION(rs.getDate("FD_FECHA_MODIFICACION"));
							dto.setFC_TAGS(rs.getString("FC_TAGS"));
							dto.setFC_KEYWORDS(rs.getString("FC_KEYWORDS"));
							dto.setFI_BAN_INFINITO_HOME(rs.getString("FI_BAN_INFINITO_HOME"));
							dto.setFI_BAN_VIDEO_VIRAL(rs.getString("FI_BAN_VIDEO_VIRAL"));
							dto.setFI_BAN_NO_TE_LO_PIERDAS(rs.getString("FI_BAN_NO_TE_LO_PIERDAS"));
							dto.setFI_BAN_PATROCINIO(rs.getString("FI_BAN_PATROCINIO"));
							dto.setFC_PATROCINIO_BACKGROUND(rs.getString("FC_PATROCINIO_BACKGROUND"));
							dto.setFC_PATROCINIO_COLOR_TEXTO(rs.getString("FC_PATROCINIO_COLOR_TEXTO"));
							
							String urlNota = rs.getString("FRIENDLY_TIPO_SEC") + "/" + rs.getString("FRIENDLY_SEC") 
											+ "/" + rs.getString("FRIENDLY_CAT") + "/detalle/" + rs.getString("FC_NOMBRE");
							
							dto.setFcLink(urlNota);

							dto.setFI_ID_APPS_CAT(rs.getInt("FI_ID_APPS_CAT"));
							dto.setFI_ID_APPS_SEC(rs.getInt("FI_ID_APPS_SEC"));
							dto.setFI_ID_APPS_TIPOSEC(rs.getInt("FI_ID_APPS_TIPOSEC"));
							
							dto.setFC_DESCRIPCION_CATEGORIA(rs.getString("FC_DESCRIPCION_CATEGORIA"));
							dto.setFC_TITULO_RSS(rs.getString("fC_TITULO_RSS"));
							//LOG.info("rs.getDate('FD_FECHA_PUBLICACION'): "+rs.getString("FD_FECHA_PUBLICACION"));
							//LOG.info("dto.fechaPub: "+dto.getFD_FECHA_PUBLICACION());
							
							return dto;
						}
					});
			
		} catch (Exception e) {
			LOG.error("Exception en consultarUltimasPorSeccion: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta las categorias
	 * @return List<CategoriaDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<CategoriaDTO> getCategorias( ) throws Exception {
		ArrayList<CategoriaDTO> resultado = new ArrayList<CategoriaDTO>();
		StringBuffer sbQuery = new StringBuffer();
		LOG.debug("Inicia consulta categorias");
		
		try {
			sbQuery.append(" SELECT FC_ID_CATEGORIA, FC_ID_SECCION, FC_DESCRIPCION, ");
			sbQuery.append(" FC_FRIENDLY_URL, FI_REGISTROS, FI_ESTATUS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_CATEGORIA ");
			
			resultado = (ArrayList<CategoriaDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new BeanPropertyRowMapper(CategoriaDTO.class) );
			
		} catch (Exception e) {
			LOG.error("Exception en getCategorias: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta los tipos de secciones
	 * @return List<SeccionDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SeccionDTO> getSecciones( ) throws Exception {
		ArrayList<SeccionDTO> resultado = new ArrayList<SeccionDTO>();
		StringBuffer sbQuery = new StringBuffer();
		
		try {
			sbQuery.append(" SELECT FC_ID_SECCION, FC_ID_TIPO_SECCION, FC_DESCRIPCION, ");
			sbQuery.append(" FC_FRIENDLY_URL, FI_ESTATUS ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_SECCION ");
			
			LOG.debug("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<SeccionDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new BeanPropertyRowMapper(SeccionDTO.class) );
			
		} catch (Exception e) {
			LOG.error("Exception en getSecciones: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * M�todo que consulta los tipos de secciones
	 * @return List<TipoSeccionDTO>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<TipoSeccionDTO> getTipoSecciones( ) throws Exception {
		ArrayList<TipoSeccionDTO> resultado = new ArrayList<TipoSeccionDTO>();
		StringBuffer sbQuery = new StringBuffer();
		
		try {
			sbQuery.append(" SELECT FC_ID_TIPO_SECCION, FC_DESCRIPCION, FI_ESTATUS, FC_FRIENDLY_URL ");
			sbQuery.append(" FROM WPDB2INS.UNO_MX_C_TIPO_SECCION ");
			
			LOG.debug("sbQuery: "+sbQuery.toString());
			resultado = (ArrayList<TipoSeccionDTO>) this.jdbcTemplate.query(
					sbQuery.toString(), new BeanPropertyRowMapper(TipoSeccionDTO.class) );
			
		} catch (Exception e) {
			LOG.error("Exception en getTipoSecciones: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * Obtiene la lista de notas magazine
	 * @param requestNotaDTO request
	 * @return Lista de notas magazine
	 */
	
	public List<NoticiaRSSDTO> getNotaMagazine(RequestNotaDTO requestNotaDTO){
		List<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		ArrayList<String> array = new ArrayList<String>();
		
		StringBuffer sb = new StringBuffer();
		String ESTATUS = "1";
		
		sb.append("SELECT N.FC_ID_CONTENIDO ");
		sb.append(",N.FC_ID_CATEGORIA ");
		sb.append(",C.FC_FRIENDLY_URL AS FRIENDLY_CAT ");
		sb.append(",S.FC_FRIENDLY_URL AS FRIENDLY_SEC ");
		sb.append(",C.FC_DESCRIPCION AS FC_CATEGORIA_DESCRIPCION ");
		sb.append(",N.FC_NOMBRE ");
		sb.append(",N.FC_TITULO ");
		sb.append(",N.FC_DESCRIPCION ");
		sb.append(",N.FC_ESCRIBIO ");
		sb.append(",N.FC_LUGAR ");
		sb.append(",N.FC_FUENTE ");
		sb.append(",N.FC_ID_TIPO_NOTA ");
		sb.append(",N.FC_IMAGEN_PRINCIPAL ");
		sb.append(",N.FC_IMAGEN_PIE ");
		sb.append(",N.FC_VIDEO_YOUTUBE ");
		sb.append(",N.FC_ID_VIDEO_CONTENT ");
		sb.append(",N.FC_ID_VIDEO_PLAYER ");
		sb.append(",N.CL_GALERIA_IMAGENES ");
		sb.append(",N.FC_INFOGRAFIA ");
		sb.append(",N.CL_RTF_CONTENIDO ");
		sb.append(",N.FD_FECHA_PUBLICACION ");
		sb.append(",N.FD_FECHA_MODIFICACION ");
		sb.append(",N.FC_TAGS ");
		sb.append(",N.FC_KEYWORDS ");
		sb.append(",N.FI_BAN_INFINITO_HOME ");
		sb.append(",N.FI_BAN_VIDEO_VIRAL ");
		sb.append(",N.FI_BAN_NO_TE_LO_PIERDAS ");
		sb.append(",N.FI_BAN_PATROCINIO ");
		sb.append(",N.FC_PATROCINIO_BACKGROUND ");
		sb.append(",N.FC_PATROCINIO_COLOR_TEXTO ");
		sb.append(",CASE ");
		sb.append("  WHEN NM.FC_URL_EXTERNA IS NOT NULL AND NM.FC_URL_EXTERNA != '' THEN NM.FC_URL_EXTERNA ");
		sb.append("  ELSE TS.FC_FRIENDLY_URL || '/' || S.FC_FRIENDLY_URL || '/' || C.FC_FRIENDLY_URL || '/detalle/' || N.FC_NOMBRE ");
		sb.append("END AS URL_NOTA ");
		sb.append(",C.FC_DESCRIPCION as FC_DESCRIPCION_CATEGORIA ");
		sb.append("FROM WPDB2INS.UNO_MX_C_TIPO_SECCION TS ");
		sb.append(",WPDB2INS.UNO_MX_C_SECCION S ");
		sb.append(",WPDB2INS.UNO_MX_C_CATEGORIA C ");
		sb.append(",WPDB2INS.UNO_MX_C_MAGAZINE M ");
		sb.append(",WPDB2INS.UNO_MX_I_NOTA_MAGAZINE NM ");
		sb.append(",WPDB2INS.UNO_MX_N_NOTA N ");
		sb.append("WHERE TS.FC_ID_TIPO_SECCION = S.FC_ID_TIPO_SECCION ");
		sb.append("AND S.FC_ID_SECCION = C.FC_ID_SECCION ");
		sb.append("AND C.FC_ID_CATEGORIA = N.FC_ID_CATEGORIA ");
		sb.append("AND M.FC_ID_MAGAZINE = NM.FC_ID_MAGAZINE ");
		sb.append("AND NM.FC_ID_CONTENIDO = N.FC_ID_CONTENIDO ");
		sb.append("AND M.FC_ID_MAGAZINE = ? ");
		sb.append("AND TS.FI_ESTATUS = ? ");
		sb.append("AND S.FI_ESTATUS = ? ");
		sb.append("AND C.FI_ESTATUS = ? ");
		sb.append("ORDER BY NM.FI_ORDEN ");
		
		try {
			
			String query = sb.toString();
			
			array.add(requestNotaDTO.getIdMagazine());
			array.add(ESTATUS);
			array.add(ESTATUS);
			array.add(ESTATUS);
			
			//LOG.debug("idMagazine:" + requestNotaDTO.getIdMagazine() + " | query:" + query);
			
			resultado = (ArrayList<NoticiaRSSDTO>) jdbcTemplate.query(
					query, 
					array.toArray(), 
					new RowMapper<NoticiaRSSDTO>() {
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							
							dto.setFC_ID_CONTENIDO((rs.getString("FC_ID_CONTENIDO") != null && rs.getString("FC_ID_CONTENIDO").trim().length() > 0) ? rs.getString("FC_ID_CONTENIDO") : "");
							dto.setFC_ID_CATEGORIA((rs.getString("FC_ID_CATEGORIA") != null && rs.getString("FC_ID_CATEGORIA").trim().length() > 0) ? rs.getString("FC_ID_CATEGORIA") : "");
							dto.setFC_NOMBRE((rs.getString("FC_NOMBRE") != null && rs.getString("FC_NOMBRE").trim().length() > 0) ? rs.getString("FC_NOMBRE") : "");
							dto.setFC_TITULO((rs.getString("FC_TITULO") != null && rs.getString("FC_TITULO").trim().length() > 0) ? rs.getString("FC_TITULO") : "");
							dto.setFC_DESCRIPCION((rs.getString("FC_DESCRIPCION") != null && rs.getString("FC_DESCRIPCION").trim().length() > 0) ? rs.getString("FC_DESCRIPCION") : "");
							dto.setFC_ESCRIBIO((rs.getString("FC_ESCRIBIO") != null && rs.getString("FC_ESCRIBIO").trim().length() > 0) ? rs.getString("FC_ESCRIBIO") : "");
							dto.setFC_LUGAR((rs.getString("FC_LUGAR") != null && rs.getString("FC_LUGAR").trim().length() > 0) ? rs.getString("FC_LUGAR") : "");
							dto.setFC_FUENTE((rs.getString("FC_FUENTE") != null && rs.getString("FC_FUENTE").trim().length() > 0) ? rs.getString("FC_FUENTE") : "");
							dto.setFC_ID_TIPO_NOTA((rs.getString("FC_ID_TIPO_NOTA") != null && rs.getString("FC_ID_TIPO_NOTA").trim().length() > 0) ? rs.getString("FC_ID_TIPO_NOTA") : "");
							dto.setFC_IMAGEN_PRINCIPAL((rs.getString("FC_IMAGEN_PRINCIPAL") != null && rs.getString("FC_IMAGEN_PRINCIPAL").trim().length() > 0) ? rs.getString("FC_IMAGEN_PRINCIPAL") : "");
							dto.setFC_IMAGEN_PIE((rs.getString("FC_IMAGEN_PIE") != null && rs.getString("FC_IMAGEN_PIE").trim().length() > 0) ? rs.getString("FC_IMAGEN_PIE") : "");
							dto.setFC_VIDEO_YOUTUBE((rs.getString("FC_VIDEO_YOUTUBE") != null && rs.getString("FC_VIDEO_YOUTUBE").trim().length() > 0) ? rs.getString("FC_VIDEO_YOUTUBE") : "");
							dto.setFC_ID_VIDEO_CONTENT((rs.getString("FC_ID_VIDEO_CONTENT") != null && rs.getString("FC_ID_VIDEO_CONTENT").trim().length() > 0) ? rs.getString("FC_ID_VIDEO_CONTENT") : "");
							dto.setFC_ID_VIDEO_PLAYER((rs.getString("FC_ID_VIDEO_PLAYER") != null && rs.getString("FC_ID_VIDEO_PLAYER").trim().length() > 0) ? rs.getString("FC_ID_VIDEO_PLAYER") : "");
							dto.setCL_GALERIA_IMAGENES((rs.getString("CL_GALERIA_IMAGENES") != null && rs.getString("CL_GALERIA_IMAGENES").trim().length() > 0) ? rs.getString("CL_GALERIA_IMAGENES") : "");
							dto.setFC_INFOGRAFIA((rs.getString("FC_INFOGRAFIA") != null && rs.getString("FC_INFOGRAFIA").trim().length() > 0) ? rs.getString("FC_INFOGRAFIA") : "");
							dto.setCL_RTF_CONTENIDO((rs.getString("CL_RTF_CONTENIDO") != null && rs.getString("CL_RTF_CONTENIDO").trim().length() > 0) ? rs.getString("CL_RTF_CONTENIDO") : "");
							dto.setFD_FECHA_PUBLICACION((rs.getString("FD_FECHA_PUBLICACION") != null && rs.getString("FD_FECHA_PUBLICACION").trim().length() > 0) ? rs.getString("FD_FECHA_PUBLICACION") : "");
							//dto.setFD_FECHA_MODIFICACION((rs.getString("FD_FECHA_MODIFICACION") != null && rs.getString("FD_FECHA_MODIFICACION").trim().length() > 0) ? rs.getString("FD_FECHA_MODIFICACION") : "");
							dto.setFC_TAGS((rs.getString("FC_TAGS") != null && rs.getString("FC_TAGS").trim().length() > 0) ? rs.getString("FC_TAGS") : "");
							dto.setFC_KEYWORDS((rs.getString("FC_KEYWORDS") != null && rs.getString("FC_KEYWORDS").trim().length() > 0) ? rs.getString("FC_KEYWORDS") : "");
							dto.setFI_BAN_INFINITO_HOME((rs.getString("FI_BAN_INFINITO_HOME") != null && rs.getString("FI_BAN_INFINITO_HOME").trim().length() > 0) ? rs.getString("FI_BAN_INFINITO_HOME") : "");
							dto.setFI_BAN_VIDEO_VIRAL((rs.getString("FI_BAN_VIDEO_VIRAL") != null && rs.getString("FI_BAN_VIDEO_VIRAL").trim().length() > 0) ? rs.getString("FI_BAN_VIDEO_VIRAL") : "");
							dto.setFI_BAN_NO_TE_LO_PIERDAS((rs.getString("FI_BAN_NO_TE_LO_PIERDAS") != null && rs.getString("FI_BAN_NO_TE_LO_PIERDAS").trim().length() > 0) ? rs.getString("FI_BAN_NO_TE_LO_PIERDAS") : "");
							dto.setFI_BAN_PATROCINIO((rs.getString("FI_BAN_PATROCINIO") != null && rs.getString("FI_BAN_PATROCINIO").trim().length() > 0) ? rs.getString("FI_BAN_PATROCINIO") : "");
							dto.setFC_PATROCINIO_BACKGROUND((rs.getString("FC_PATROCINIO_BACKGROUND") != null && rs.getString("FC_PATROCINIO_BACKGROUND").trim().length() > 0) ? rs.getString("FC_PATROCINIO_BACKGROUND") : "");
							dto.setFC_PATROCINIO_COLOR_TEXTO((rs.getString("FC_PATROCINIO_COLOR_TEXTO") != null && rs.getString("FC_PATROCINIO_COLOR_TEXTO").trim().length() > 0) ? rs.getString("FC_PATROCINIO_COLOR_TEXTO") : "");
							dto.setFcLink((rs.getString("URL_NOTA") != null && rs.getString("URL_NOTA").trim().length() > 0) ? rs.getString("URL_NOTA") : "");
							dto.setFC_CATEGORIA((rs.getString("FRIENDLY_CAT") != null && rs.getString("FRIENDLY_CAT").trim().length() > 0) ? rs.getString("FRIENDLY_CAT") : "");
							dto.setFC_SECCION((rs.getString("FRIENDLY_SEC") != null && rs.getString("FRIENDLY_SEC").trim().length() > 0) ? rs.getString("FRIENDLY_SEC") : "");
							dto.setFC_DESCRIPCION_CATEGORIA((rs.getString("FC_DESCRIPCION_CATEGORIA") != null && rs.getString("FC_DESCRIPCION_CATEGORIA").trim().length() > 0) ? rs.getString("FC_DESCRIPCION_CATEGORIA") : "");
							return dto;
						}
				
					});
		} catch (Exception e) {
			LOG.error("Excepcion en ["+this.getClass().getName()+".getNota] error: ",e);
		}
		
		return resultado;
	}
	
	/**
	 * Obtiene la lista de notas
	 * @param requestNotaDTO request
	 * @return Lista de notas
	 */
	public List<NoticiaRSSDTO> getNota(RequestNotaDTO requestNotaDTO){
		List<NoticiaRSSDTO> resultado = new ArrayList<NoticiaRSSDTO>();
		ArrayList<String> array = new ArrayList<String>();
		
		StringBuffer sb = new StringBuffer();
		String ESTATUS = "1";
		
		sb.append("SELECT N.FC_ID_CONTENIDO ");
		sb.append(",C.FC_FRIENDLY_URL AS FRIENDLY_CAT ");
		sb.append(",S.FC_FRIENDLY_URL AS FRIENDLY_SEC ");
		sb.append(",N.FC_ID_CATEGORIA ");
		sb.append(",C.FC_DESCRIPCION AS FC_CATEGORIA_DESCRIPCION ");
		sb.append(",N.FC_NOMBRE ");
		sb.append(",N.FC_TITULO ");
		sb.append(",N.FC_DESCRIPCION ");
		sb.append(",N.FC_ESCRIBIO ");
		sb.append(",N.FC_LUGAR ");
		sb.append(",N.FC_FUENTE ");
		sb.append(",N.FC_ID_TIPO_NOTA ");
		sb.append(",N.FC_IMAGEN_PRINCIPAL ");
		sb.append(",N.FC_IMAGEN_PIE ");
		sb.append(",N.FC_VIDEO_YOUTUBE ");
		sb.append(",N.FC_ID_VIDEO_CONTENT ");
		sb.append(",N.FC_ID_VIDEO_PLAYER ");
		sb.append(",N.CL_GALERIA_IMAGENES ");
		sb.append(",N.FC_INFOGRAFIA ");
		sb.append(",N.CL_RTF_CONTENIDO ");
		sb.append(",N.FD_FECHA_PUBLICACION ");
		sb.append(",N.FD_FECHA_MODIFICACION ");
		sb.append(",N.FC_TAGS ");
		sb.append(",N.FC_KEYWORDS ");
		sb.append(",N.FI_BAN_INFINITO_HOME ");
		sb.append(",N.FI_BAN_VIDEO_VIRAL ");
		sb.append(",N.FI_BAN_NO_TE_LO_PIERDAS ");
		sb.append(",N.FI_BAN_PATROCINIO ");
		sb.append(",N.FC_PATROCINIO_BACKGROUND ");
		sb.append(",N.FC_PATROCINIO_COLOR_TEXTO ");
		sb.append(",TS.FC_FRIENDLY_URL || '/' || S.FC_FRIENDLY_URL || '/' || C.FC_FRIENDLY_URL || '/detalle/' || N.FC_NOMBRE AS URL_NOTA ");
		sb.append(",C.FC_DESCRIPCION as FC_DESCRIPCION_CATEGORIA ");
		sb.append("FROM WPDB2INS.UNO_MX_C_TIPO_SECCION TS ");
		sb.append(",WPDB2INS.UNO_MX_C_SECCION S ");
		sb.append(",WPDB2INS.UNO_MX_C_CATEGORIA C ");
		sb.append(",WPDB2INS.UNO_MX_N_NOTA N ");
		sb.append("WHERE TS.FC_ID_TIPO_SECCION = S.FC_ID_TIPO_SECCION ");
		sb.append("AND S.FC_ID_SECCION = C.FC_ID_SECCION ");
		sb.append("AND C.FC_ID_CATEGORIA = N.FC_ID_CATEGORIA ");
		
		//Nivel de la nota
		if(!requestNotaDTO.getIdTipoSeccion().equalsIgnoreCase("na")){
			sb.append("AND TS.FC_ID_TIPO_SECCION  = ? ");
		}
		
		if(!requestNotaDTO.getIdSeccion().equalsIgnoreCase("na")){
			sb.append("AND S.FC_ID_SECCION  = ? ");
		}
		
		if(!requestNotaDTO.getIdCategoria().equalsIgnoreCase("na")){
			sb.append("AND C.FC_ID_CATEGORIA = ? ");
		}
		
		//Bandera de la nota
		if(!requestNotaDTO.getNomBandera().equalsIgnoreCase("na")){
			sb.append("AND " + requestNotaDTO.getNomBandera() + " = 1 ");
		}

		sb.append("AND TS.FI_ESTATUS = ? ");
		sb.append("AND S.FI_ESTATUS = ? ");
		sb.append("AND C.FI_ESTATUS = ? ");
		sb.append("ORDER BY N.FD_FECHA_PUBLICACION DESC ");

		//Numero de registros
		if(requestNotaDTO.getNumRegistrosFetch() != 0){
			sb.append("FETCH FIRST " + requestNotaDTO.getNumRegistrosFetch() + " ROWS ONLY ");
		}
		
		try {
			
			String query = sb.toString();
			
			if(!requestNotaDTO.getIdTipoSeccion().equalsIgnoreCase("na")){
				array.add(requestNotaDTO.getIdTipoSeccion());
			}
			
			if(!requestNotaDTO.getIdSeccion().equalsIgnoreCase("na")){
				array.add(requestNotaDTO.getIdSeccion());
			}
			
			if(!requestNotaDTO.getIdCategoria().equalsIgnoreCase("na")){
				array.add(requestNotaDTO.getIdCategoria());
			}
			
			array.add(ESTATUS);
			array.add(ESTATUS);
			array.add(ESTATUS);
			
			LOG.debug("numReg:" + requestNotaDTO.getNumRegistros() + " query:" + query);
			
			resultado = (ArrayList<NoticiaRSSDTO>) jdbcTemplate.query(
					query, 
					array.toArray(), 
					new RowMapper<NoticiaRSSDTO>() {
						public NoticiaRSSDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
							NoticiaRSSDTO dto = new NoticiaRSSDTO();
							
							dto.setFC_ID_CONTENIDO((rs.getString("FC_ID_CONTENIDO") != null && rs.getString("FC_ID_CONTENIDO").trim().length() > 0) ? rs.getString("FC_ID_CONTENIDO") : "");
							dto.setFC_ID_CATEGORIA((rs.getString("FC_ID_CATEGORIA") != null && rs.getString("FC_ID_CATEGORIA").trim().length() > 0) ? rs.getString("FC_ID_CATEGORIA") : "");
							dto.setFC_NOMBRE((rs.getString("FC_NOMBRE") != null && rs.getString("FC_NOMBRE").trim().length() > 0) ? rs.getString("FC_NOMBRE") : "");
							dto.setFC_TITULO((rs.getString("FC_TITULO") != null && rs.getString("FC_TITULO").trim().length() > 0) ? rs.getString("FC_TITULO") : "");
							dto.setFC_DESCRIPCION((rs.getString("FC_DESCRIPCION") != null && rs.getString("FC_DESCRIPCION").trim().length() > 0) ? rs.getString("FC_DESCRIPCION") : "");
							dto.setFC_ESCRIBIO((rs.getString("FC_ESCRIBIO") != null && rs.getString("FC_ESCRIBIO").trim().length() > 0) ? rs.getString("FC_ESCRIBIO") : "");
							dto.setFC_LUGAR((rs.getString("FC_LUGAR") != null && rs.getString("FC_LUGAR").trim().length() > 0) ? rs.getString("FC_LUGAR") : "");
							dto.setFC_FUENTE((rs.getString("FC_FUENTE") != null && rs.getString("FC_FUENTE").trim().length() > 0) ? rs.getString("FC_FUENTE") : "");
							dto.setFC_ID_TIPO_NOTA((rs.getString("FC_ID_TIPO_NOTA") != null && rs.getString("FC_ID_TIPO_NOTA").trim().length() > 0) ? rs.getString("FC_ID_TIPO_NOTA") : "");
							dto.setFC_IMAGEN_PRINCIPAL((rs.getString("FC_IMAGEN_PRINCIPAL") != null && rs.getString("FC_IMAGEN_PRINCIPAL").trim().length() > 0) ? rs.getString("FC_IMAGEN_PRINCIPAL") : "");
							dto.setFC_IMAGEN_PIE((rs.getString("FC_IMAGEN_PIE") != null && rs.getString("FC_IMAGEN_PIE").trim().length() > 0) ? rs.getString("FC_IMAGEN_PIE") : "");
							dto.setFC_VIDEO_YOUTUBE((rs.getString("FC_VIDEO_YOUTUBE") != null && rs.getString("FC_VIDEO_YOUTUBE").trim().length() > 0) ? rs.getString("FC_VIDEO_YOUTUBE") : "");
							dto.setFC_ID_VIDEO_CONTENT((rs.getString("FC_ID_VIDEO_CONTENT") != null && rs.getString("FC_ID_VIDEO_CONTENT").trim().length() > 0) ? rs.getString("FC_ID_VIDEO_CONTENT") : "");
							dto.setFC_ID_VIDEO_PLAYER((rs.getString("FC_ID_VIDEO_PLAYER") != null && rs.getString("FC_ID_VIDEO_PLAYER").trim().length() > 0) ? rs.getString("FC_ID_VIDEO_PLAYER") : "");
							dto.setCL_GALERIA_IMAGENES((rs.getString("CL_GALERIA_IMAGENES") != null && rs.getString("CL_GALERIA_IMAGENES").trim().length() > 0) ? rs.getString("CL_GALERIA_IMAGENES") : "");
							dto.setFC_INFOGRAFIA((rs.getString("FC_INFOGRAFIA") != null && rs.getString("FC_INFOGRAFIA").trim().length() > 0) ? rs.getString("FC_INFOGRAFIA") : "");
							dto.setCL_RTF_CONTENIDO((rs.getString("CL_RTF_CONTENIDO") != null && rs.getString("CL_RTF_CONTENIDO").trim().length() > 0) ? rs.getString("CL_RTF_CONTENIDO") : "");
							dto.setFD_FECHA_PUBLICACION((rs.getString("FD_FECHA_PUBLICACION") != null && rs.getString("FD_FECHA_PUBLICACION").trim().length() > 0) ? rs.getString("FD_FECHA_PUBLICACION") : "");
							//dto.setFD_FECHA_MODIFICACION((rs.getString("FD_FECHA_MODIFICACION") != null && rs.getString("FD_FECHA_MODIFICACION").trim().length() > 0) ? rs.getString("FD_FECHA_MODIFICACION") : "");
							dto.setFC_TAGS((rs.getString("FC_TAGS") != null && rs.getString("FC_TAGS").trim().length() > 0) ? rs.getString("FC_TAGS") : "");
							dto.setFC_KEYWORDS((rs.getString("FC_KEYWORDS") != null && rs.getString("FC_KEYWORDS").trim().length() > 0) ? rs.getString("FC_KEYWORDS") : "");
							dto.setFI_BAN_INFINITO_HOME((rs.getString("FI_BAN_INFINITO_HOME") != null && rs.getString("FI_BAN_INFINITO_HOME").trim().length() > 0) ? rs.getString("FI_BAN_INFINITO_HOME") : "");
							dto.setFI_BAN_VIDEO_VIRAL((rs.getString("FI_BAN_VIDEO_VIRAL") != null && rs.getString("FI_BAN_VIDEO_VIRAL").trim().length() > 0) ? rs.getString("FI_BAN_VIDEO_VIRAL") : "");
							dto.setFI_BAN_NO_TE_LO_PIERDAS((rs.getString("FI_BAN_NO_TE_LO_PIERDAS") != null && rs.getString("FI_BAN_NO_TE_LO_PIERDAS").trim().length() > 0) ? rs.getString("FI_BAN_NO_TE_LO_PIERDAS") : "");
							dto.setFI_BAN_PATROCINIO((rs.getString("FI_BAN_PATROCINIO") != null && rs.getString("FI_BAN_PATROCINIO").trim().length() > 0) ? rs.getString("FI_BAN_PATROCINIO") : "");
							dto.setFC_PATROCINIO_BACKGROUND((rs.getString("FC_PATROCINIO_BACKGROUND") != null && rs.getString("FC_PATROCINIO_BACKGROUND").trim().length() > 0) ? rs.getString("FC_PATROCINIO_BACKGROUND") : "");
							dto.setFC_PATROCINIO_COLOR_TEXTO((rs.getString("FC_PATROCINIO_COLOR_TEXTO") != null && rs.getString("FC_PATROCINIO_COLOR_TEXTO").trim().length() > 0) ? rs.getString("FC_PATROCINIO_COLOR_TEXTO") : "");
							dto.setFcLink((rs.getString("URL_NOTA") != null && rs.getString("URL_NOTA").trim().length() > 0) ? rs.getString("URL_NOTA") : "");
							dto.setFC_CATEGORIA((rs.getString("FRIENDLY_CAT") != null && rs.getString("FRIENDLY_CAT").trim().length() > 0) ? rs.getString("FRIENDLY_CAT") : "");
							dto.setFC_SECCION((rs.getString("FRIENDLY_SEC") != null && rs.getString("FRIENDLY_SEC").trim().length() > 0) ? rs.getString("FRIENDLY_SEC") : "");
							dto.setFC_DESCRIPCION_CATEGORIA((rs.getString("FC_DESCRIPCION_CATEGORIA") != null && rs.getString("FC_DESCRIPCION_CATEGORIA").trim().length() > 0) ? rs.getString("FC_DESCRIPCION_CATEGORIA") : "");
							return dto;
						}
				
					});
		} catch (Exception e) {
			LOG.error("Excepcion en ["+this.getClass().getName()+".getNota] error: ",e);
		}
		
		return resultado;
	}
	
	/*Setter y Getter*/
	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	@Autowired
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

}//FIN CLASE
