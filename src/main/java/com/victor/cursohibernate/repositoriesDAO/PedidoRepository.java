package com.victor.cursohibernate.repositoriesDAO;

import com.victor.cursohibernate.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer>
{

}
