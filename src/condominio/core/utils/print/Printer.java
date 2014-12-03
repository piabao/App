package condominio.core.utils.print;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.controlsfx.dialog.Dialogs;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import condominio.core.portaria.PortariaCrud;
import condominio.server.modelo.ANIMAIS;
import condominio.server.modelo.CADASTRO_MORADOR;
import condominio.server.modelo.TIPO_MORADOR;
import condominio.server.modelo.VEICULOS;

public class Printer {
	
	private final String defaultStyle = "<P STYLE=\"margin-bottom: 0in; line-height: 0.25in\">";
	private PortariaCrud pCrud = new PortariaCrud();
	private final String titleStyle = "<P ALIGN=CENTER STYLE=\"margin-bottom: 0in; line-height: 100%\">";
	
	public void print(String identificador){
		createSnapShot(identificador);
	}
	
	private void createSnapShot(String identificador) {
		try {
			List<CADASTRO_MORADOR> todosMoradores = pCrud.getMoradoresByIdentification(identificador);
			String inputFile = createhtmlContentForMorador(todosMoradores);
			if(inputFile == null){
				return;
			}
			String outputFile = "c:/resources/firstdoc.pdf";
			OutputStream os = new FileOutputStream(outputFile);
		
			Document doc = new Document(PageSize.A4);
			PdfWriter.getInstance(doc, os);
			doc.open();
			HTMLWorker hw = new HTMLWorker(doc);
			hw.parse(new StringReader(inputFile));
			doc.close();
			 
		    File arquivo = new File(outputFile);  
		    Desktop.getDesktop().open(arquivo); 
			
		} catch (DocumentException | IOException e) {
			e.printStackTrace();
		}

	}
		
	private String createhtmlContentForMorador(List<CADASTRO_MORADOR> todosMoradores) {
		
		String html = readFile("c:/resources/o_7f597819a846504c.html");

		StringBuffer buf = new StringBuffer();
		List<CADASTRO_MORADOR> proprietarios = new ArrayList<CADASTRO_MORADOR>();
		List<CADASTRO_MORADOR> moradores = new ArrayList<CADASTRO_MORADOR>();
		List<CADASTRO_MORADOR> inquilinos = new ArrayList<CADASTRO_MORADOR>();
		for (CADASTRO_MORADOR cadastro : todosMoradores) {
			if(cadastro.getTipo_morador().equals(TIPO_MORADOR.PROPRIETARIO)){
				proprietarios.add(cadastro);
			}else if(cadastro.getTipo_morador().equals(TIPO_MORADOR.INQUILINO)){
				inquilinos.add(cadastro);
			}else{
				moradores.add(cadastro);
			}
		}		
		if(proprietarios.size() == 0){
			Dialogs.create().title("Cadastro Incompleto").message("Não existe proprietário cadastrado para esta residência").showError();
			return null;
		}
		
		buf.append(titleStyle + preencherLinha("CADASTRO MORADOR "+"</P>"));
		
		String quadra = proprietarios.get(0).getIdentificador().substring(0,1).toUpperCase();
		String casa = proprietarios.get(0).getIdentificador().substring(1);
		buf.append(defaultStyle + preencherLinha("QUADRA: "+quadra," CASA: "+ casa , " GARAGEM: "+ proprietarios.get(0).getVaga())+"</P>");
		
		for (CADASTRO_MORADOR cadastroProprietarios : proprietarios) {
			adicionarProprietario(buf, cadastroProprietarios);			
		}
		for (CADASTRO_MORADOR cadastroInq : inquilinos) {
			adicionarInquilinos(buf, cadastroInq);			
		}
		
		if(moradores.size() > 0){
			buf.append(defaultStyle + preencherLinha("MORADORES: ")+" </P>");			
		}
		
		for (CADASTRO_MORADOR cadastroMor : moradores) {
			adicionarMoradores(buf, cadastroMor);			
		}
		buf.append("<br>");
		
		List<VEICULOS> veiculosByMorador = pCrud.getVeiculosByMorador(proprietarios.get(0).getIdentificador());
		
		if(veiculosByMorador.size()> 0){
			buf.append(defaultStyle + preencherLinha("VEÍCULOS: ")+" </P>");
		}
		
		for (VEICULOS veiculos : veiculosByMorador) {
			adicionarVeiculos(buf, veiculos);
		}
				
		buf.append("<br>");

		List<ANIMAIS> animaisByMorador = pCrud.getAnimaisByMorador(proprietarios.get(0).getIdentificador());
		
		if(animaisByMorador.size()> 0){
			buf.append(defaultStyle + preencherLinha("ANIMAL DE ESTIMAÇÃO: ")+" </P>");
		}
		
		for (ANIMAIS animais : animaisByMorador) {
			adicionarAnimais(buf, animais);
		}
		
		buf.append(defaultStyle + preencherLinha(" OBSERVAÇÕES: ")+" </P>");
		buf.append(defaultStyle + preencherLinha("____________________________________________________________________ ")+" </P>");
		buf.append(defaultStyle + preencherLinha("____________________________________________________________________ ")+" </P>");
		
		buf.append("<br>");
		
		buf.append(defaultStyle + preencherLinha("____________________________", "____________________________")+" </P>");
		buf.append(defaultStyle + preencherLinha("ASSINATURA DO PROPRIETÁRIO", "ASSINATURA")+" </P>");		
		

		buf.append("</BODY> \n");
		buf.append("</HTML>");

		html = html+buf.toString();
		//System.out.println(buf.toString());	
		
		return html;
	}

	private void adicionarAnimais(StringBuffer buf, ANIMAIS animais) {
		buf.append(defaultStyle + preencherLinha("NOME: "+animais.getNome())+" </P>");
		buf.append(defaultStyle + preencherLinha("TIPO DE ANIMAL/RAÇA: "+ animais.getTipo())+" </P>");
		buf.append(defaultStyle + preencherLinha("PORTE: "+ animais.getPorte(), " COR: "+ animais.getCor())+" </P>");
		buf.append("<br>");
	}

	private void adicionarVeiculos(StringBuffer buf, VEICULOS veiculos) {
		buf.append(defaultStyle + preencherLinha("PLACA: "+veiculos.getPlaca(), " TIPO DE VEÍCULO: "+ veiculos.getTipo(), " COR: "+ veiculos.getCor())+" </P>");
		buf.append(defaultStyle + preencherLinha("MODELO: "+ veiculos.getModelo())+" </P>");
	}

	private void adicionarMoradores(StringBuffer buf, CADASTRO_MORADOR cadastro) {
		buf.append(defaultStyle + preencherLinha("NOME: "+ cadastro.getNome(), " E-MAIL: "+ cadastro.getEmail())+" </P>");
	}

	private void adicionarProprietario(StringBuffer buf,CADASTRO_MORADOR cadastro) {
		String telefones = pCrud.getTelefonesByIdMorador(cadastro.getId()).toString().replace("[", "").replace("]", "");
		buf.append(defaultStyle + preencherLinha("PROPRIETÁRIO: "+ cadastro.getNome())+" </P>");
		buf.append(defaultStyle + preencherLinha("CPF: "+cadastro.getCpf())+ "</P>");
		buf.append(defaultStyle + preencherLinha("TELEFONES: "+telefones) +"</P>");
		buf.append(defaultStyle + preencherLinha("E-MAIL: "+ cadastro.getEmail())+ "</P>");

		buf.append("<br>");
	}

	private void adicionarInquilinos(StringBuffer buf, CADASTRO_MORADOR cadastro) {
		String telefones = pCrud.getTelefonesByIdMorador(cadastro.getId()).toString().replace("[", "").replace("]", "");
		buf.append(defaultStyle + preencherLinha("INQUILINO: "+ cadastro.getNome())+" </P>");
		buf.append(defaultStyle + preencherLinha("CPF: "+cadastro.getCpf())+ "</P>");
		buf.append(defaultStyle + preencherLinha("TELEFONES: "+telefones) +"</P>");
		buf.append(defaultStyle + preencherLinha("E-MAIL: "+ cadastro.getEmail())+ "</P>");
		buf.append(defaultStyle + preencherLinha("IMOBILIARIA RESPONSÁVEL: ")+ "</P>");

		buf.append("<br>");
	}

	private String preencherLinha(String... valores) {
		String linha = "";
		int media = makeMedia(110, valores.length);
		for (int i = 0; i < valores.length; i++) {
			int cont = valores[i].length();
			if(cont < media){
				linha = linha + makeValor(valores[i], media);
			}else {
				linha = linha + valores[i];
				media = makeMedia(118-cont, valores.length-(i+1));
			}
		}
		return linha += "";
	}
		
	private String makeValor(String val, int media) {
		for (int i = val.length(); i < media; i++) {
			val = val+ "&nbsp;";
		}
		return val;
	}
		
	private int makeMedia(int valor, int quantidade) { 
		return valor / quantidade;
	}
		
	private String readFile(String file) { 
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(file));

		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(encoded, Charset.defaultCharset());
	}
}
