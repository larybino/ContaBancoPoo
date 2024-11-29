package controle;

import servico.BaseServico;

public interface BaseControle <T>{
    BaseServico <T> getServico();
    // default T inserir(T entidade){
    //     return getServico().inserir(entidade);
    // }

    default void excluir(Long id){
        getServico().excluir(id);
    }

}
