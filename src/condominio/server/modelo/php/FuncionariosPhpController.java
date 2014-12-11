package condominio.server.modelo.php;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.dao.RequestFactory;
import condominio.server.modelo.php.js.CadastroMoradorHelper;
import condominio.server.modelo.php.js.FuncionariosHelper;
import condominio.server.modelo.php.js.OperadoraHelper;

public class FuncionariosPhpController {

	private static final String TABLE_NAME = "funcionarios";

	public FUNCIONARIOS create(FUNCIONARIOS funcionario) throws SQLException{
		RequestFactory rf;
		if(funcionario.getId() != null){
			rf = new RequestFactory(RequestFactory.editarUrl, TABLE_NAME, getEditValues(funcionario), "ID = '"+funcionario.getId()+"'");
		}else{
			rf = new RequestFactory(RequestFactory.salvarUrl, TABLE_NAME, funcionario.getAtribNames(), getValues(funcionario));			
		}
		rf.doPost();
		return null;
	}

	private String getValues(FUNCIONARIOS val) {
		return "'"+val.getBairro()+"', '"+val.getCargaHoraria()+"', '"+val.getDataAdmicao()+"', '"+val.getTelefone()+"', '"+val.getCelular()+"', '"+val.getEmail()+"', '"+val.getFuncao()+"', '"+val.getNome()+"', '"+val.getEndereco()+"', '"+val.getId()+"', '"+val.getCep()+"', '"+val.getCidade()+"', '"+val.getUf()+"'";
	}

	private String getEditValues(FUNCIONARIOS funcionario) {
		return "BAIRRO = '"+funcionario.getBairro()+"', CARGAHORARIA = '"+funcionario.getCargaHoraria()+"', DATAADMICAO = '"+funcionario.getDataAdmicao()+"', TELEFONE = '"+funcionario.getTelefone()+"', CELULAR = '"+funcionario.getCelular()+"', EMAIL = '"+funcionario.getEmail()+"', FUNCAO = '"+funcionario.getFuncao()+"', NOME = '"+funcionario.getNome()+"', ENDERECO = '"+funcionario.getEndereco()+"', ID = '"+funcionario.getId()+"', CEP = '"+funcionario.getCep()+"', CIDADE = '"+funcionario.getCidade()+"', UF = '"+funcionario.getUf()+"'";
	}

	public FUNCIONARIOS findFUNCIONARIOS(Long id) {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID ='"+id+"'");
		List<FUNCIONARIOS> funcionariosList = FuncionariosHelper.toFuncionariosList(rf.doPost());
		if(funcionariosList != null && !funcionariosList.isEmpty()){
			return funcionariosList.get(0);
		}
		return new FUNCIONARIOS();
	}

	public List<FUNCIONARIOS> findFUNCIONARIOSEntities() {
		RequestFactory rf = new RequestFactory(RequestFactory.carregarUrl, TABLE_NAME, "ID = ID");
		return FuncionariosHelper.toFuncionariosList(rf.doPost());
	}

	public void destroy(Long id) throws NonexistentEntityException, SQLException{
		RequestFactory rf = new RequestFactory(RequestFactory.removerUrl, TABLE_NAME, "ID = '"+id+"'");
		rf.doPost();
	}	
}
