package condominio.core.administrativo.funcionarios;

import java.sql.SQLException;
import java.util.List;

import condominio.core.acesso.model.VagasPreenchidasModel;
import condominio.server.modelo.FUNCIONARIOS;
import condominio.server.modelo.HISTORICO_ACESSO;
import condominio.server.modelo.dao.FuncionariosJpaController;
import condominio.server.modelo.dao.HistoricoAcessoJpaController;
import condominio.server.modelo.dao.exceptions.NonexistentEntityException;
import condominio.server.modelo.php.FuncionariosPhpController;

public class FuncionariosCrud {
	
//	FuncionariosJpaController funcionarios = new FuncionariosJpaController();
	FuncionariosPhpController funcionarios = new FuncionariosPhpController();

	public FUNCIONARIOS salvarFuncionario(FUNCIONARIOS funcionario) {
		try {
			return this.funcionarios.create(funcionario);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}
	
	public FUNCIONARIOS buscarFuncionario(Long id) {
		return this.funcionarios.findFUNCIONARIOS(id);	
	}
	
	public List<FUNCIONARIOS> buscarTodosFuncionarios() {
		return funcionarios.findFUNCIONARIOSEntities();	
	}
	
	public void removerFuncionario(Long id){
		try {
			funcionarios.destroy(id);
		} catch (NonexistentEntityException | SQLException e) {
			e.printStackTrace();
		}
	}
}
