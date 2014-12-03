package condominio.core.utils.print;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import condominio.core.administrativo.funcionarios.FuncionariosCrud;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.OCORRENCIAS;

public class FuncionariosPrinter {
	
	private final String defaultStyle = "<P STYLE=\"margin-bottom: 0in; line-height: 0.25in\">";
	private final String h1Style = "<P STYLE=\"margin-bottom: 0in; line-height: 0.35in\">";
	private final String titleStyle = "<P ALIGN=CENTER STYLE=\"margin-bottom: 0in; line-height: 100%\">";
	private FuncionariosCrud fCrud = new FuncionariosCrud();
	//private String CAMINHO = "C:/Program Files/Moradas Palhoca 2/deploy/resources/";

	public void print(Long identificador){
		createSnapShot(identificador);
	}
	
	private void createSnapShot(Long identificador) {
		try {			
			FUNCIONARIOS funcionario = fCrud.buscarFuncionario(identificador);
			String inputFile = createhtmlContentForFuncionario(funcionario);
			if(inputFile == null){
				return;
			}
			String outputFile = "c:/resources/firstdoc.pdf";//CAMINHO + "firstdoc.pdf";
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
		
	private String createhtmlContentForFuncionario(FUNCIONARIOS funcionario) {
		
		String html = readFile("c:/resources/o_7f597819a846504c.html");

		StringBuffer buf = new StringBuffer();		
		
		buf.append(titleStyle + "FUNCIONÁRIO"+"</P>");
		
		buf.append(h1Style + preencherLinha("DADOS PESSOAIS")+"</P>");
		
		buf.append(defaultStyle + preencherLinha("Nome: "+funcionario.getNome())+"</P>");
		buf.append(defaultStyle + preencherLinha("Endereço: "+funcionario.getEndereco())+"</P>");
		buf.append(defaultStyle + preencherLinha("Bairro: "+funcionario.getBairro())+"</P>");
		buf.append(defaultStyle + preencherLinha("CEP: "+funcionario.getCep(), "Cidade: "+funcionario.getCidade(), "UF: "+ funcionario.getUf())+"</P>");
		buf.append(defaultStyle + preencherLinha("Telefone: "+funcionario.getTelefone(), "Celular: "+funcionario.getCelular())+"</P>");
		
		//Dados Profissionais
		buf.append(h1Style + preencherLinha("DADOS PROFISSIONAIS")+"</P>");
		
		buf.append(defaultStyle + preencherLinha("Função/Cargo: "+funcionario.getFuncao())+"</P>");
		buf.append(defaultStyle + preencherLinha("Horário de Trabalho Previsto: "+funcionario.getCargaHoraria())+"</P>");
		String data = "";
		if(funcionario.getDataAdmicao()!= null){
			SimpleDateFormat format = new SimpleDateFormat("dd MMM yyyy");
			data = format.format(new Date(funcionario.getDataAdmicao()));			
		}
		buf.append(defaultStyle + preencherLinha("Admitido em: "+data)+"</P>");
		
		//Ocorências
		buf.append(h1Style + preencherLinha("OCORRÊNCIAS")+"</P>");
		
		for (OCORRENCIAS ocorrencias : funcionario.getOcorrencias()) {
			buf.append(defaultStyle + preencherLinha(ocorrencias.getDescricao())+"</P>");			
		}
		buf.append(defaultStyle + preencherLinha("____________________________________________________________________ ")+" </P>");
		buf.append(defaultStyle + preencherLinha("____________________________________________________________________ ")+" </P>");
		
		buf.append("<br>");
		
		buf.append(defaultStyle + preencherLinha("____________________________", "____________________________")+" </P>");
		buf.append(defaultStyle + preencherLinha("ASSINATURA DO FUNCIONÁRIO", "ASSINATURA")+" </P>");		
		
		buf.append("</BODY> \n");
		buf.append("</HTML>");

		html = html+buf.toString();
		
		return html;
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
		System.out.println(file);
		try {
			encoded = Files.readAllBytes(Paths.get(file));

		} catch (IOException e) {
			System.out.println(file);
			e.printStackTrace();
		}
		return new String(encoded, Charset.defaultCharset());
	}
}
