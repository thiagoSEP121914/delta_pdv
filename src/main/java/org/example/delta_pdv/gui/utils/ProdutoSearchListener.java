package org.example.delta_pdv.gui.utils;

import org.example.delta_pdv.entities.Produto;

import java.util.Optional;

public interface ProdutoSearchListener {

    Optional<Produto> onBuscarProduto(String nomeBusca);

}
